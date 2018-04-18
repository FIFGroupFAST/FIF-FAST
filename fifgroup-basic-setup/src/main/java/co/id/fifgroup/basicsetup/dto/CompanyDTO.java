package co.id.fifgroup.basicsetup.dto;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.basicsetup.common.History;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyContact;
import co.id.fifgroup.basicsetup.domain.CompanyOtherInfoValue;
import co.id.fifgroup.basicsetup.domain.OtherInfoComponent;
import co.id.fifgroup.core.domain.City;
import co.id.fifgroup.core.domain.Kecamatan;
import co.id.fifgroup.core.domain.Kelurahan;
import co.id.fifgroup.core.domain.Province;
import co.id.fifgroup.core.domain.ZipCode;

public class CompanyDTO extends Company implements History {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9219105791961368852L;

	private List<CompanyContact> companyContact;
	private List<CompanyOtherInfoValue> companyOtherInfoValues;
	private Province provinceObject;
	private City cityObject;
	private Kecamatan kecamatanObject;
	private Kelurahan kelurahanObject;
	private ZipCode zipCodeObject;
	private String userName;
	private OtherInfoComponent otherInfoComponent;
	private boolean isValidContactType;
	private Date effectiveDate;
	
	public List<CompanyContact> getCompanyContact() {
		return companyContact;
	}
	public void setCompanyContact(List<CompanyContact> companyContact) {
		this.companyContact = companyContact;
	}
	public List<CompanyOtherInfoValue> getCompanyOtherInfoValues() {
		return companyOtherInfoValues;
	}
	public void setCompanyOtherInfoValues(
			List<CompanyOtherInfoValue> companyOtherInfoValues) {
		this.companyOtherInfoValues = companyOtherInfoValues;
	}
	public Province getProvinceObject() {
		return provinceObject;
	}
	public void setProvinceObject(Province provinceObject) {
		this.provinceObject = provinceObject;
	}
	public City getCityObject() {
		return cityObject;
	}
	public void setCityObject(City cityObject) {
		this.cityObject = cityObject;
	}
	public Kecamatan getKecamatanObject() {
		return kecamatanObject;
	}
	public void setKecamatanObject(Kecamatan kecamatanObject) {
		this.kecamatanObject = kecamatanObject;
	}
	public Kelurahan getKelurahanObject() {
		return kelurahanObject;
	}
	public void setKelurahanObject(Kelurahan kelurahanObject) {
		this.kelurahanObject = kelurahanObject;
	}
	public ZipCode getZipCodeObject() {
		return zipCodeObject;
	}
	public void setZipCodeObject(ZipCode zipCodeObject) {
		this.zipCodeObject = zipCodeObject;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public OtherInfoComponent getOtherInfoComponent() {
		return otherInfoComponent;
	}
	public void setOtherInfoComponent(OtherInfoComponent otherInfoComponent) {
		this.otherInfoComponent = otherInfoComponent;
	}
	public boolean isValidContactType() {
		return isValidContactType;
	}
	public void setValidContactType(boolean isValidContactType) {
		this.isValidContactType = isValidContactType;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

}
