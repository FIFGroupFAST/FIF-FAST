package co.id.fifgroup.personneladmin.dto;

import java.util.List;

import co.id.fifgroup.personneladmin.domain.MobilityEmployee;
import co.id.fifgroup.personneladmin.domain.MobilityZone;

public class MobilityEmployeeDTO extends MobilityEmployee {

	private static final long serialVersionUID = 1L;
	
	private List<MobilityZone> mobilityZone;
	private boolean isValid;

	public List<MobilityZone> getMobilityZone() {
		return mobilityZone;
	}

	public void setMobilityZone(List<MobilityZone> mobilityZone) {
		this.mobilityZone = mobilityZone;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
