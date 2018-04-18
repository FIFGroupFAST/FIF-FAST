package co.id.fifgroup.core.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.dto.ZipCodeDto;
import co.id.fifgroup.core.service.ZipCodeService;
import co.id.fifgroup.core.ui.GlobalVariable;

@VariableResolver(DelegatingVariableResolver.class)
public class ListOfValueZipCodeComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(ListOfValueZipCodeComposer.class);
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Listbox listbox;
	private Bandbox source;
	@Wire
	private Paging pgListOfValue;
	@Wire
	private Textbox txtKecamatan;
	@Wire
	private Textbox txtKelurahan;
	@Wire
	private Textbox txtZipCode;
	@WireVariable(rewireOnActivate=true)
	private transient ZipCodeService zipCodeServiceImpl;
	private String kecamatan;
	private String kelurahan;
	private String zipCode;
	private String provinceCode;
	private String cityCode;
	private String kecamatanCode;
	private String kelurahanCode;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		source = arg.get("source") == null ? null : (Bandbox) arg.get("source");
		provinceCode = arg.get("provinceCode") == null ? null : arg.get("provinceCode").toString();
		cityCode = arg.get("cityCode") == null ? null : arg.get("cityCode").toString();
		kecamatanCode = arg.get("kecamatanCode") == null ? null : arg.get("kecamatanCode").toString();
		kelurahanCode = arg.get("kelurahanCode") == null ? null : arg.get("kelurahanCode").toString();
	}

	@Listen("onSelect = #listbox")
	public void select(){
		try {
			ZipCodeDto selectedData = (ZipCodeDto) listbox.getSelectedItem().getValue();
			source.setValue(selectedData.getZipCode().getZipCode());
			source.setRawValue(selectedData.getZipCode());
			getSelf().detach();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Listen("onClick=#btnDeselect")
	public void deselect() {
		if(source != null) {
			source.setRawValue(null);
			Events.postEvent(Events.ON_CLOSE, source, null);
		}
		getSelf().detach();
	}
	
	private void loadListbox() {
		List<ZipCodeDto> zipCodeDtos = zipCodeServiceImpl.getZipCodeByKecamatanAndKelurahanAndZipCode(kecamatan, kelurahan, zipCode, provinceCode, cityCode, kecamatanCode, kelurahanCode, pgListOfValue.getPageSize(), pgListOfValue.getActivePage() * pgListOfValue.getPageSize());
		ListModel<ZipCodeDto> listModel = new ListModelList<ZipCodeDto>(zipCodeDtos);
		listbox.setModel(listModel);
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		kecamatan = txtKecamatan.getValue();
		kelurahan = txtKelurahan.getValue();
		zipCode = txtZipCode.getValue();
		pgListOfValue.setTotalSize(zipCodeServiceImpl.getCountZipCodeByKecamatanAndKelurahanAndZipCode(kecamatan, kelurahan, zipCode, provinceCode, cityCode, kecamatanCode, kelurahanCode));
		pgListOfValue.setPageSize(GlobalVariable.getMaxRowPerPage());
		pgListOfValue.setActivePage(0);
		loadListbox();
	}

	@Listen("onPaging = #pgListOfValue")
	public void onPaging() {
		loadListbox();
	}
}
