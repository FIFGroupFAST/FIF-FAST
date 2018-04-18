package co.id.fifgroup.basicsetup.controller;

import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.dto.FormulaUsageDTO;
import co.id.fifgroup.basicsetup.service.FormulaUsageService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;

@VariableResolver(DelegatingVariableResolver.class)
public class FormulaUsageComposer extends SelectorComposer<Window> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2771018105408575163L;
	@Wire
	private Searchtextbox txtFormulaName;
	@Wire
	private Listbox lstFormulaUsage;
	
	@WireVariable(rewireOnActivate=true)
	private transient FormulaUsageService formulaUsageServiceImpl;
	
	private ListModelList<FormulaUsageDTO> listModelFormulaUsageDto;
	
	private String formulaName;
	
	private List<FormulaUsageDTO> formulaUsageDto;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
	}

	private void init() {
		initListModelFormulaUsage();
	}
	
	private void initListModelFormulaUsage() {
		listModelFormulaUsageDto = new ListModelList<FormulaUsageDTO>();
		lstFormulaUsage.setPageSize(GlobalVariable.getMaxRowPerPage());
		lstFormulaUsage.setModel(listModelFormulaUsageDto);
		lstFormulaUsage.renderAll();
	}
	
	@Listen("onClick = #btnFind; onOk = #txtFormulaName")
	public void onClickFind() {
		formulaName = txtFormulaName.getValue();
		if(formulaName.equals("%%")) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -684077296539455844L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						search();
					}
				}
				
			});
		} else {
			search();
		}
	}
	
	private void search() {
		formulaUsageDto = formulaUsageServiceImpl.getFormulaUsageDtoByFormulaName(formulaName);
		listModelFormulaUsageDto.clear();
		listModelFormulaUsageDto.addAll(formulaUsageDto);
	}
	
}
