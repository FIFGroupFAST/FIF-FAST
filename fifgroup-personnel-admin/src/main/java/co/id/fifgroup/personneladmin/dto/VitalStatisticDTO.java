package co.id.fifgroup.personneladmin.dto;

import java.util.List;

import co.id.fifgroup.personneladmin.domain.Apparel;
import co.id.fifgroup.personneladmin.domain.VitalStatistic;

public class VitalStatisticDTO extends VitalStatistic {

	private static final long serialVersionUID = 1L;

	private List<Apparel> apparels;
	private boolean isValidApparels;

	public List<Apparel> getApparels() {
		return apparels;
	}

	public void setApparels(List<Apparel> apparels) {
		this.apparels = apparels;
	}

	public boolean isValidApparels() {
		return isValidApparels;
	}

	public void setValidApparels(boolean isValidApparels) {
		this.isValidApparels = isValidApparels;
	}
	
	

}
