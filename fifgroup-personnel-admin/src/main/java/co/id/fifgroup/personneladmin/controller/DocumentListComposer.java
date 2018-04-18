package co.id.fifgroup.personneladmin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.personneladmin.constant.ActionControl;
import co.id.fifgroup.personneladmin.constant.FieldPermissionsPersonnelAdmin;
import co.id.fifgroup.personneladmin.constant.ReferencePersonnelAdministration;
import co.id.fifgroup.personneladmin.dto.DocumentDTO;
import co.id.fifgroup.personneladmin.service.DocumentService;
import co.id.fifgroup.personneladmin.util.FileUtil;

import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;

@VariableResolver(DelegatingVariableResolver.class)
public class DocumentListComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(DocumentListComposer.class);
	
	private DocumentListComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient LookupService lookupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient DocumentService documentServiceImpl;
	
	@Wire
	private Listbox cmbDocument;
	@Wire
	private Button btnNew;
	@Wire
	private Listbox lbxDocuments;
	
	private PersonalInfoMainComposer parentComposer;
	private ActionControl control;
	private List<DocumentDTO> listDocuments;
	private ListModelList<DocumentDTO> listModelDocuments;
	private boolean isDisabled;
	private boolean isDownloadDisabled;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		parentComposer = (PersonalInfoMainComposer) arg.get("parentPage");
		control = (ActionControl) arg.get("actionControl");
		initComponent();
		populateData();
	}
	
	public void initComponent() {
		getDocument();
		disabledComponent();		
	}
	
	public void disabledComponent() {
		if (control.equals(ActionControl.VIEW)) {
			btnNew.setDisabled(true);
			isDisabled = true;
			isDownloadDisabled = false;
		} else if (control.equals(ActionControl.NEW_HISTORY)) {
			btnNew.setDisabled(true);
			isDisabled = true;
			isDownloadDisabled = false;
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.DOCUMENT_NEW_EDIT_ENABLED)) {
				btnNew.setDisabled(false);
				isDisabled = false;
			}
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.DOCUMENT_LINK_DOWNLOAD_DISABLED)) {
				isDownloadDisabled = true;
			}
		}
	}
	
	public void populateData() {
		try {
			KeyValue keyValueDocument = (KeyValue) cmbDocument.getSelectedItem().getValue();
			String documentType = keyValueDocument.getKey() != null ? keyValueDocument.getKey().toString() : null;
			listDocuments = documentServiceImpl.getDocumentsByPersonIdAndCompanyId(parentComposer.getPersonMainDTO().getPersonDTO().getPersonId(),
					parentComposer.getPersonMainDTO().getPersonDTO().getCompanyId(), documentType);
			listModelDocuments = new ListModelList<DocumentDTO>(listDocuments);
			lbxDocuments.setModel(listModelDocuments);
			lbxDocuments.setItemRenderer(getItemRenderer());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}		
	}
	
	@Listen("onClick = #btnFind")
	public void doSearch() {
		populateData();
	}
	
	@Listen("onClick = #btnNew")
	public void doUploadDocument() {
		params = new HashMap<String, Object>();
		params.put("parent", thisComposer);
		params.put("parentPage", parentComposer);
		params.put("isDisabled", isDisabled);
		Window window = (Window) Executions.createComponents("~./hcms/personneladmin/document_upload.zul", 
				getSelf().getParent(), params);
		window.setClosable(true);
		window.setMaximized(false);
		window.setWidth("50%");
		window.setBorder("normal");
		window.setTitle(Labels.getLabel("pea.uploadDocument"));
		window.doModal();
	}
	
	public ListitemRenderer<DocumentDTO> getItemRenderer() {
		ListitemRenderer<DocumentDTO> listitemRenderer = new SerializableListItemRenderer<DocumentDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final DocumentDTO data,
					final int index) throws Exception {
				KeyValue documentType = lookupServiceImpl.getLookup(
						ReferencePersonnelAdministration.LOOKUP_DOCUMENTS
								.getReference(), data.getDocumentType(),
						securityServiceImpl.getSecurityProfile()
								.getWorkspaceBusinessGroupId(),
						securityServiceImpl.getSecurityProfile()
								.getWorkspaceCompanyId());
				
				// 14040715241425_CR HCMS – Recruitment_RAH
				if (documentType == null) {
					documentType = lookupServiceImpl.getLookup(
							ReferencePersonnelAdministration.LOOKUP_REC_DOCUMENTS
									.getReference(), data.getDocumentType(),
							securityServiceImpl.getSecurityProfile()
									.getWorkspaceBusinessGroupId(),
							securityServiceImpl.getSecurityProfile()
									.getWorkspaceCompanyId());
				}
				
				new Listcell(documentType.getDescription().toString())
						.setParent(item);
				A link = new A(data.getFileName());
				link.setStyle("color:blue");
				link.addEventListener("onClick",
						new SerializableEventListener<Event>() {

							private static final long serialVersionUID = 1L;

							@Override
							public void onEvent(Event arg0) throws Exception {
								FileUtil.downloadFile(data.getFilePath(),
										"application/file");
							}
						});

				Listcell lc = new Listcell();
				link.setDisabled(isDownloadDisabled);
				link.setParent(lc);
				lc.setParent(item);
				new Listcell(FormatDateUI.formatDate(data.getUploadDate()))
						.setParent(item);

				if (data.isEditable() && !control.equals(ActionControl.VIEW)) {
					A linkEdit = new A(Labels.getLabel("common.detail"));
					linkEdit.setDisabled(isDisabled);
					linkEdit.addEventListener("onClick",
							new SerializableEventListener<Event>() {

								private static final long serialVersionUID = 1L;

								@Override
								public void onEvent(Event arg0)
										throws Exception {
									params = new HashMap<String, Object>();
									params.put("document", data);
									params.put("parentPage", parentComposer);
									params.put("parent", thisComposer);
									Window window = (Window) Executions
											.createComponents(
													"~./hcms/personneladmin/document_upload.zul",
													getSelf().getParent(),
													params);
									window.setClosable(true);
									window.setMaximized(false);
									window.setWidth("50%");
									window.setBorder("normal");
									window.setTitle(Labels
											.getLabel("pea.uploadDocument"));
									window.doModal();
								}
							});
					Listcell lc2 = new Listcell();
					linkEdit.setParent(lc2);
					lc2.setParent(item);
				} else {
					Listcell lc2 = new Listcell();
					Label label = new Label();
					label.setParent(lc2);
					lc2.setParent(item);
				}
			}
		};
		
		return listitemRenderer;
	}
	
	public void getDocument() {
		List<KeyValue> documents = lookupServiceImpl.getLookups(
				ReferencePersonnelAdministration.LOOKUP_DOCUMENTS
						.getReference(), securityServiceImpl
						.getSecurityProfile().getWorkspaceBusinessGroupId(),
				securityServiceImpl.getSecurityProfile()
						.getWorkspaceCompanyId(), true);
		// 14040715241425_CR HCMS – Recruitment_RAH
		List<KeyValue> recDocuments = lookupServiceImpl.getLookups(
				ReferencePersonnelAdministration.LOOKUP_REC_DOCUMENTS
						.getReference(), securityServiceImpl
						.getSecurityProfile().getWorkspaceBusinessGroupId(),
				securityServiceImpl.getSecurityProfile()
						.getWorkspaceCompanyId(), true);
		
		KeyValue select = new KeyValue(null,
				Labels.getLabel("common.pleaseSelect"),
				Labels.getLabel("common.pleaseSelect"));
		ListModelList<KeyValue> listModel = new ListModelList<KeyValue>();
		listModel.add(select);
		listModel.addAll(documents);
		listModel.addAll(recDocuments);
		listModel.addToSelection(select);
		cmbDocument.setModel(listModel);
		cmbDocument.renderAll();
	}
}
