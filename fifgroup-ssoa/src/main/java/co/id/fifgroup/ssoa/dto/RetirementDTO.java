package co.id.fifgroup.ssoa.dto;



import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.domain.transaction.CommonTrx;
import co.id.fifgroup.ssoa.domain.Retirement;

public class RetirementDTO extends Retirement implements CommonTrx{
	private static final long serialVersionUID = 5749051688120495054L;
	private String branchCode;
	private String branchName;
	private String retirementType;
	private String status;
	private String bastStatus;
	private String userName;
	private String ebsFlag;
	private String assetType;
	private UUID personUUID;
	
	private List<RetirementBastDTO> retirementBast;
	private List<RetirementDetailDTO> retirementDetail;
	private List<RetirementQuotationDTO> retirementQuotation;
	private List<RetirementRVDTO> retirementRV;
	
	private Long ebsAssetId;
	private String bookTypeCode;
	private String resultOut;
	private String accountCode;
	private String retiredFlag;
	private String arBranchType;
	
	public List<RetirementDetailDTO> getRetirementDetail() {
		return retirementDetail;
	}
	public void setRetirementDetail(List<RetirementDetailDTO> retirementDetail) {
		this.retirementDetail = retirementDetail;
	}
	
	public List<RetirementQuotationDTO> getRetirementQuotation() {
		return retirementQuotation;
	}
	public void setRetirementQuotation(List<RetirementQuotationDTO> retirementQuotation) {
		this.retirementQuotation = retirementQuotation;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getRetirementType() {
		return retirementType;
	}
	public void setRetirementType(String retirementType) {
		this.retirementType = retirementType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBastStatus() {
		return bastStatus;
	}
	public void setBastStatus(String bastStatus) {
		this.bastStatus = bastStatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<RetirementBastDTO> getRetirementBast() {
		return retirementBast;
	}
	public void setRetirementBast(List<RetirementBastDTO> retirementBast) {
		this.retirementBast = retirementBast;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public UUID getPersonUUID() {
		return personUUID;
	}
	public void setPersonUUID(UUID personUUID) {
		this.personUUID = personUUID;
	}
	public Long getEbsAssetId() {
		return ebsAssetId;
	}
	public void setEbsAssetId(Long ebsAssetId) {
		this.ebsAssetId = ebsAssetId;
	}
	public String getBookTypeCode() {
		return bookTypeCode;
	}
	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}
	public String getResultOut() {
		return resultOut;
	}
	public void setResultOut(String resultOut) {
		this.resultOut = resultOut;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getRetiredFlag() {
		return retiredFlag;
	}
	public void setRetiredFlag(String retiredFlag) {
		this.retiredFlag = retiredFlag;
	}
	public List<RetirementRVDTO> getRetirementRV() {
		return retirementRV;
	}
	public void setRetirementRV(List<RetirementRVDTO> retirementRV) {
		this.retirementRV = retirementRV;
	}
	public String getArBranchType() {
		return arBranchType;
	}
	public void setArBranchType(String arBranchType) {
		this.arBranchType = arBranchType;
	}
	public String getEbsFlag() {
		return ebsFlag;
	}
	public void setEbsFlag(String ebsFlag) {
		this.ebsFlag = ebsFlag;
	}
	
	
	
}