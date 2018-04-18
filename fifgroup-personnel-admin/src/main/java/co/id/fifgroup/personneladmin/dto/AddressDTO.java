package co.id.fifgroup.personneladmin.dto;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.core.domain.ZipCode;
import co.id.fifgroup.personneladmin.domain.Address;

public class AddressDTO extends Address implements Traversable {

	private static final long serialVersionUID = 1L;

	private String provinceName;
	private String cityName;
	private String kecamatanName;
	private String kelurahanName;
	private ZipCode zipCodeObject;

	public ZipCode getZipCodeObject() {
		return zipCodeObject;
	}

	public void setZipCodeObject(ZipCode zipCodeObject) {
		this.zipCodeObject = zipCodeObject;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getKecamatanName() {
		return kecamatanName;
	}

	public void setKecamatanName(String kecamatanName) {
		this.kecamatanName = kecamatanName;
	}

	public String getKelurahanName() {
		return kelurahanName;
	}

	public void setKelurahanName(String kelurahanName) {
		this.kelurahanName = kelurahanName;
	}

	@Override
	public Object getId() {
		return getAddressId();
	}

}
