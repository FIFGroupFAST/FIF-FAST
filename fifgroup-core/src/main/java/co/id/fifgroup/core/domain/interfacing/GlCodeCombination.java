package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.util.Date;

public class GlCodeCombination implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long codeCombinationId;
	private Long chartOfAccountId;
	private String detailPostingAllowedFlag;
	private String detailBudgetingAllowedFlag;
	private String accountType;
	private String enabledFlag;
	private String summaryFlag;
	private String segment1;
	private String segment2;
	private String segment3;
	private String segment4;
	private String segment5;
	private String segment6;
	private String segment7;
	private String segment8;
	
	private Date lastUpdateDate;
	private Long lastUpdatedBy;
	
	
	public String getSegment1() {
		return segment1;
	}
	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}
	public String getSegment4() {
		return segment4;
	}
	public void setSegment4(String segment4) {
		this.segment4 = segment4;
	}
	public String getSegment5() {
		return segment5;
	}
	public void setSegment5(String segment5) {
		this.segment5 = segment5;
	}
	public String getSegment6() {
		return segment6;
	}
	public void setSegment6(String segment6) {
		this.segment6 = segment6;
	}
	public String getSegment7() {
		return segment7;
	}
	public void setSegment7(String segment7) {
		this.segment7 = segment7;
	}
	public String getSegment8() {
		return segment8;
	}
	public void setSegment8(String segment8) {
		this.segment8 = segment8;
	}
	public String getSegment2() {
		return segment2;
	}
	public void setSegment2(String segment2) {
		this.segment2 = segment2;
	}
	public String getSegment3() {
		return segment3;
	}
	public void setSegment3(String segment3) {
		this.segment3 = segment3;
	}
	public Long getCodeCombinationId() {
		return codeCombinationId;
	}
	public void setCodeCombinationId(Long codeCombinationId) {
		this.codeCombinationId = codeCombinationId;
	}
	public Long getChartOfAccountId() {
		return chartOfAccountId;
	}
	public void setChartOfAccountId(Long chartOfAccountId) {
		this.chartOfAccountId = chartOfAccountId;
	}
	public String getDetailPostingAllowedFlag() {
		return detailPostingAllowedFlag;
	}
	public void setDetailPostingAllowedFlag(String detailPostingAllowedFlag) {
		this.detailPostingAllowedFlag = detailPostingAllowedFlag;
	}
	public String getDetailBudgetingAllowedFlag() {
		return detailBudgetingAllowedFlag;
	}
	public void setDetailBudgetingAllowedFlag(String detailBudgetingAllowedFlag) {
		this.detailBudgetingAllowedFlag = detailBudgetingAllowedFlag;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public String getSummaryFlag() {
		return summaryFlag;
	}
	public void setSummaryFlag(String summaryFlag) {
		this.summaryFlag = summaryFlag;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	

	

}
