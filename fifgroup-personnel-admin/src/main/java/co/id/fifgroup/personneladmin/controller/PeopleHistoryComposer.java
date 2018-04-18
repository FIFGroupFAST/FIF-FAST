package co.id.fifgroup.personneladmin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.personneladmin.constant.EventType;
import co.id.fifgroup.personneladmin.constant.FieldPermissionsPersonnelAdmin;
import co.id.fifgroup.personneladmin.constant.ReferencePersonnelAdministration;
import co.id.fifgroup.personneladmin.service.PeopleHistoryService;
import co.id.fifgroup.personneladmin.util.FileUtil;

import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.personneladmin.domain.PeopleHistory;
import co.id.fifgroup.personneladmin.domain.PeopleHistoryExample;

@VariableResolver(DelegatingVariableResolver.class)
public class PeopleHistoryComposer extends SelectorComposer<Window> {

	
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(PeopleHistoryComposer.class);
	
	private PeopleHistoryComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private PeopleHistoryService peopleHistoryServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private LookupService lookupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private SecurityService securityServiceImpl;
	
	@Wire
	private Listbox lbxPeopleHistory;
	
	private PersonalInfoMainComposer parentComposer;
	private boolean isDisabled;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		parentComposer = (PersonalInfoMainComposer) arg.get("parentPage");
		initComponent();
		getFieldPermissionsComponent();
		populateData();
	}
	
	public void initComponent() {
		lbxPeopleHistory.setMold("paging");
		lbxPeopleHistory.setPageSize(GlobalVariable.getMaxRowPerPage());
	}
	
	private void getFieldPermissionsComponent() {
		if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.PEOPLE_HISTORY_LINK_DOWNLOAD_DISABLED)) {
			isDisabled = true;
		}
	}
	
	public void populateData() {
		try {
			PeopleHistoryExample example = new PeopleHistoryExample();
			example.createCriteria().andPersonIdEqualTo(parentComposer.getPersonMainDTO().getPersonDTO().getPersonId()).andCompanyIdEqualTo(parentComposer.getPersonMainDTO().getPersonDTO().getCompanyId());
			example.setOrderByClause("HISTORY_ID DESC");
			List<PeopleHistory> peopleHistories = peopleHistoryServiceImpl.selectByExample(example);
			ListModelList<PeopleHistory> listModel = new ListModelList<PeopleHistory>(peopleHistories);
			lbxPeopleHistory.setModel(listModel);
			lbxPeopleHistory.setItemRenderer(getItemRenderer());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public ListitemRenderer<PeopleHistory> getItemRenderer() {
		ListitemRenderer<PeopleHistory> listitemRenderer = new SerializableListItemRenderer<PeopleHistory>() {

			private static final long serialVersionUID = 5214400621517909857L;

			@Override
			public void render(Listitem item, final PeopleHistory data,
					final int index) throws Exception {

				new Listcell(FormatDateUI.formatDate(data.getCreationDate()))
						.setParent(item);
				A eventName = new A(EventType.getDescription(data
						.getEventName()));
				eventName.setStyle("color:blue");
				eventName.addEventListener("onClick",
						new SerializableEventListener<Event>() {

							private static final long serialVersionUID = 1L;

							@Override
							public void onEvent(Event arg0) throws Exception {
								Messagebox.show(data.getMessageDescription());
							}
						});

				Listcell lc = new Listcell();
				eventName.setParent(lc);
				lc.setParent(item);

				if (data.getDocumentType() != null) {
					KeyValue documentType = lookupServiceImpl.getLookup(
							ReferencePersonnelAdministration.LOOKUP_DOCUMENTS
									.getReference(), data.getDocumentType(),
							securityServiceImpl.getSecurityProfile()
									.getWorkspaceBusinessGroupId(),
							securityServiceImpl.getSecurityProfile()
									.getWorkspaceCompanyId());

					// 14040715241425_CR HCMS – Recruitment_RAH
					if (documentType == null) {
						documentType = lookupServiceImpl
								.getLookup(
										ReferencePersonnelAdministration.LOOKUP_REC_DOCUMENTS
												.getReference(), data
												.getDocumentType(),
										securityServiceImpl
												.getSecurityProfile()
												.getWorkspaceBusinessGroupId(),
										securityServiceImpl
												.getSecurityProfile()
												.getWorkspaceCompanyId());
					}

					String detail = documentType.getDescription().toString();
					A link = new A(detail);
					link.setStyle("color:blue");
					link.addEventListener("onClick",
							new SerializableEventListener<Event>() {

								private static final long serialVersionUID = 1L;

								@Override
								public void onEvent(Event arg0)
										throws Exception {
									String documentPath = data
											.getDocumentPath();
									FileUtil.downloadFile(documentPath,
											"application/file");
								}
							});
					link.setDisabled(isDisabled);
					Listcell lc2 = new Listcell();
					link.setParent(lc2);
					lc2.setParent(item);
				} else {
					Label label = new Label();
					Listcell lc2 = new Listcell();
					label.setParent(lc2);
					lc2.setParent(item);
				}
			}
		};

		return listitemRenderer;
	}

}
