package co.id.fifgroup.core.dto;

import co.id.fifgroup.core.version.Version;

public class HousingSetupDTO extends Version {
	private static final long serialVersionUID = 8785841081790032996L;

	private Long companyId;
	private String assignmentStatus;
	private String employmentCategory;
	private String transferBy;
	private Long eligibilityMatrixLocationId;
	private DecisionTableDTO housingEligible;
	private Double overplafondPercentage;
	private Boolean isCanBeOverride;
	private Double percentagePrivateHouse;
	private Double percentageRentHouse;
	private Integer closureExtendContractActive;
	private Integer maxContractEmployee;
	private Integer maxContractHc;
	private Long offeringHouseAreaId;
	private String budgetActivityCode;
	private Boolean lumpsumHousingActive;
	private Boolean lumpsumBoardingActive;
	private Boolean isUpdateSetup;
	private Integer housingPeriod;
	private Integer boardingPeriod;
	private Integer maxContractPrivate;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getAssignmentStatus() {
		return assignmentStatus;
	}

	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	public String getEmploymentCategory() {
		return employmentCategory;
	}

	public void setEmploymentCategory(String employmentCategory) {
		this.employmentCategory = employmentCategory;
	}

	public String getTransferBy() {
		return transferBy;
	}

	public void setTransferBy(String transferBy) {
		this.transferBy = transferBy;
	}

	public Long getEligibilityMatrixLocationId() {
		return eligibilityMatrixLocationId;
	}

	public void setEligibilityMatrixLocationId(Long eligibilityMatrixLocationId) {
		this.eligibilityMatrixLocationId = eligibilityMatrixLocationId;
	}

	public DecisionTableDTO getHousingEligible() {
		return housingEligible;
	}

	public void setHousingEligible(DecisionTableDTO housingEligible) {
		this.housingEligible = housingEligible;
	}

	public Double getOverplafondPercentage() {
		return overplafondPercentage;
	}

	public void setOverplafondPercentage(Double overplafondPercentage) {
		this.overplafondPercentage = overplafondPercentage;
	}

	public Boolean getIsCanBeOverride() {
		return isCanBeOverride;
	}

	public void setIsCanBeOverride(Boolean isCanBeOverride) {
		this.isCanBeOverride = isCanBeOverride;
	}

	public Double getPercentagePrivateHouse() {
		return percentagePrivateHouse;
	}

	public void setPercentagePrivateHouse(Double percentagePrivateHouse) {
		this.percentagePrivateHouse = percentagePrivateHouse;
	}

	public Double getPercentageRentHouse() {
		return percentageRentHouse;
	}

	public void setPercentageRentHouse(Double percentageRentHouse) {
		this.percentageRentHouse = percentageRentHouse;
	}

	public Integer getClosureExtendContractActive() {
		return closureExtendContractActive;
	}

	public void setClosureExtendContractActive(
			Integer closureExtendContractActive) {
		this.closureExtendContractActive = closureExtendContractActive;
	}

	public Integer getMaxContractEmployee() {
		return maxContractEmployee;
	}

	public void setMaxContractEmployee(Integer maxContractEmployee) {
		this.maxContractEmployee = maxContractEmployee;
	}

	public Integer getMaxContractHc() {
		return maxContractHc;
	}

	public void setMaxContractHc(Integer maxContractHc) {
		this.maxContractHc = maxContractHc;
	}


	public Long getOfferingHouseAreaId() {
		return offeringHouseAreaId;
	}

	public void setOfferingHouseAreaId(Long offeringHouseAreaId) {
		this.offeringHouseAreaId = offeringHouseAreaId;
	}

	public String getBudgetActivityCode() {
		return budgetActivityCode;
	}

	public void setBudgetActivityCode(String budgetActivityCode) {
		this.budgetActivityCode = budgetActivityCode;
	}

	public Boolean getLumpsumHousingActive() {
		return lumpsumHousingActive;
	}

	public void setLumpsumHousingActive(Boolean lumpsumHousingActive) {
		this.lumpsumHousingActive = lumpsumHousingActive;
	}

	public Boolean getLumpsumBoardingActive() {
		return lumpsumBoardingActive;
	}

	public void setLumpsumBoardingActive(Boolean lumpsumBoardingActive) {
		this.lumpsumBoardingActive = lumpsumBoardingActive;
	}

	public Boolean getIsUpdateSetup() {
		return isUpdateSetup;
	}

	public void setIsUpdateSetup(Boolean isUpdateSetup) {
		this.isUpdateSetup = isUpdateSetup;
	}

	public Integer getHousingPeriod() {
		return housingPeriod;
	}

	public void setHousingPeriod(Integer housingPeriod) {
		this.housingPeriod = housingPeriod;
	}

	public Integer getBoardingPeriod() {
		return boardingPeriod;
	}

	public void setBoardingPeriod(Integer boardingPeriod) {
		this.boardingPeriod = boardingPeriod;
	}

	public Integer getMaxContractPrivate() {
		return maxContractPrivate;
	}

	public void setMaxContractPrivate(Integer maxContractPrivate) {
		this.maxContractPrivate = maxContractPrivate;
	}

}