package co.id.fifgroup.core.dto;

import co.id.fifgroup.core.domain.DomainObject;
import co.id.fifgroup.core.domain.Kecamatan;
import co.id.fifgroup.core.domain.Kelurahan;
import co.id.fifgroup.core.domain.ZipCode;

public class ZipCodeDto implements DomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8310709588314781257L;
	private Kecamatan kecamatan;
	private Kelurahan kelurahan;
	private ZipCode zipCode;
	
	public Kecamatan getKecamatan() {
		return kecamatan;
	}

	public void setKecamatan(Kecamatan kecamatan) {
		this.kecamatan = kecamatan;
	}

	public Kelurahan getKelurahan() {
		return kelurahan;
	}

	public void setKelurahan(Kelurahan kelurahan) {
		this.kelurahan = kelurahan;
	}

	public ZipCode getZipCode() {
		return zipCode;
	}

	public void setZipCode(ZipCode zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public Integer getObjectVersion() {
		return null;
	}

	@Override
	public void setObjectVersion(Integer version) {
		
	}

}
