package co.id.fifgroup.core.ui;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.domain.ZipCode;
import co.id.fifgroup.core.ui.lookup.ZipCodeSearchCriteria;

public class CommonPopupZipCodeBandbox extends Bandbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2917127806335845078L;
	private CommonPopupZipCodeBandbox source = this;
	private ZipCode value;
	private ZipCodeSearchCriteria zipCodeSearchCriteria;
	
	public CommonPopupZipCodeBandbox() {
		addEventListener(Events.ON_OPEN, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4524798196691631084L;

			@Override
			public void onEvent(Event event) throws Exception {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("source", source);
				params.put("provinceCode", zipCodeSearchCriteria.getProvinceCode());
				params.put("cityCode", zipCodeSearchCriteria.getCityCode());
				params.put("kecamatanCode", zipCodeSearchCriteria.getKecamatanCode());
				params.put("kelurahanCode", zipCodeSearchCriteria.getKelurahanCode());
				Window win = (Window) Executions.createComponents("~./common/zip_code_list_of_value.zul", null, params);
				win.doModal();
			}
		});
	}

	public ZipCode getValueZipcode() {
		return value;
	}

	@Override
	public void setRawValue(Object value) {
		this.value = (ZipCode) value;
		if(this.value != null) {
			super.setValue(this.value.getZipCode());
		} else {
			super.setValue(null);
		}
	}

	public ZipCodeSearchCriteria getZipCodeSearchCriteria() {
		return zipCodeSearchCriteria;
	}

	public void setZipCodeSearchCriteria(ZipCodeSearchCriteria zipCodeSearchCriteria) {
		this.zipCodeSearchCriteria = zipCodeSearchCriteria;
	}
	
}
