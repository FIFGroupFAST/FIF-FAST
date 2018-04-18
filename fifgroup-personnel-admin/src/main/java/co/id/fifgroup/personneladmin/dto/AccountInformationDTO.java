package co.id.fifgroup.personneladmin.dto;

import java.util.List;

import co.id.fifgroup.basicsetup.common.History;
import co.id.fifgroup.personneladmin.domain.AccountInformation;
import co.id.fifgroup.personneladmin.domain.DrivingLicense;

public class AccountInformationDTO extends AccountInformation implements
		History {

	private static final long serialVersionUID = 1L;

	private List<DrivingLicense> drivingLicenses;
	private boolean isValidDrivingLicense;

	private List<MediaDTO> listMediaUploads;

	public List<DrivingLicense> getDrivingLicenses() {
		return drivingLicenses;
	}

	public void setDrivingLicenses(List<DrivingLicense> drivingLicenses) {
		this.drivingLicenses = drivingLicenses;
	}

	public List<MediaDTO> getListMediaUploads() {
		return listMediaUploads;
	}

	public void setListMediaUploads(List<MediaDTO> listMediaUploads) {
		this.listMediaUploads = listMediaUploads;
	}

	public boolean isValidDrivingLicense() {
		return isValidDrivingLicense;
	}

	public void setValidDrivingLicense(boolean isValidDrivingLicense) {
		this.isValidDrivingLicense = isValidDrivingLicense;
	}

}
