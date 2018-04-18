package co.id.fifgroup.basicsetup.domain;

import java.io.Serializable;
import java.util.List;

import org.zkoss.zul.Grid;

public class OtherInfoComponent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4769097036910476431L;
	private Grid component;
	private List<OtherInfoDetail> otherInfoDetail;
	
	public Grid getComponent() {
		return component;
	}
	public void setComponent(Grid component) {
		this.component = component;
	}
	public List<OtherInfoDetail> getOtherInfoDetail() {
		return otherInfoDetail;
	}
	public void setOtherInfoDetail(List<OtherInfoDetail> otherInfoDetail) {
		this.otherInfoDetail = otherInfoDetail;
	}

}
