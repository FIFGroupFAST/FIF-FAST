package co.id.fifgroup.personneladmin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.personneladmin.constant.PeopleSource;
import co.id.fifgroup.personneladmin.dto.PeopleTypeDTO;
import co.id.fifgroup.personneladmin.service.PeopleTypeService;

import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;

@VariableResolver(DelegatingVariableResolver.class)
public class PeopleTypeHistoryComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(PeopleTypeHistoryComposer.class);
	
	private PeopleTypeHistoryComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient PeopleTypeService peopleTypeService;

	@Wire
	private Listbox lbxPeopleTypeHistory;
	
	private Long personId;
	private Long companyId;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		personId = (Long) arg.get("personId");
		companyId = (Long) arg.get("companyId");
		initComponent();
		populateData();
	}
	
	public void initComponent() {
		lbxPeopleTypeHistory.setMold("paging");
		lbxPeopleTypeHistory.setPageSize(GlobalVariable.getMaxRowPerPage());
	}
	
	public void populateData() {
		try {
			List<PeopleTypeDTO> peopleTypes = peopleTypeService.getPeopleTypeEmployee(personId, companyId);
			ListModelList<PeopleTypeDTO> listModel = new ListModelList<PeopleTypeDTO>(peopleTypes);
			lbxPeopleTypeHistory.setModel(listModel);
			lbxPeopleTypeHistory.setItemRenderer(getItemRenderer());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public ListitemRenderer<PeopleTypeDTO> getItemRenderer() {
		ListitemRenderer<PeopleTypeDTO> listitemRenderer = new SerializableListItemRenderer<PeopleTypeDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final PeopleTypeDTO data, final int index)
					throws Exception {
				
				new Listcell(FormatDateUI.formatDate(data.getEffectiveStartDate())).setParent(item);
				new Listcell(FormatDateUI.formatDate(data.getEffectiveEndDate())).setParent(item);
				new Listcell(co.id.fifgroup.personneladmin.constant.PeopleType.getPeopleTypeDescription(data.getPeopleType())).setParent(item);
				new Listcell(data.getEmploymentCategory()).setParent(item);
				String detail = null;
				if (null != data.getSource() && data.getSource().equalsIgnoreCase(PeopleSource.RECRUITMENT.name())) {
					detail = data.getRefId() != null ? data.getApplicantNo() : null;
				}
				Listcell lc = new Listcell();
				if (detail != null) {
					A link = new A(detail);
					link.addEventListener("onClick", new SerializableEventListener<Event>() {
						
						private static final long serialVersionUID = 1L;
						
						@Override
						public void onEvent(Event arg0) throws Exception {
							if (data.getSource().equalsIgnoreCase(PeopleSource.RECRUITMENT.name())) {
								params = new HashMap<String, Object>();
								params.put("transactionId", data.getRefId());
								Window window = (Window) Executions.createComponents("~./hcms/recruitment/data_applicant_for_pea.zul", getSelf().getParent(), params);
								window.doModal();
							}
							
						}
					});
					
					link.setParent(lc);
				} else {
					Label label = new Label(data.getEmployeeNumber());
					label.setParent(lc);
					
				}
				lc.setParent(item);	
			}
		};
		
		return listitemRenderer;
	}
	
	@Listen("onClick = #btnBack")
	public void doBack() {
		getSelf().onClose();
	}
}
