package co.id.fifgroup.personneladmin.domain;

import java.io.Serializable;
import java.util.Date;

public class HousingAllowance implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_HOUSING_ALLOWANCE.HOUSING_ALLOWANCE_ID
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    private Long housingAllowanceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_HOUSING_ALLOWANCE.PERSON_ID
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    private Long personId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_HOUSING_ALLOWANCE.COMPANY_ID
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    private Long companyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_HOUSING_ALLOWANCE.EFFECTIVE_START_DATE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    private Date effectiveStartDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_HOUSING_ALLOWANCE.EFFECTIVE_END_DATE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    private Date effectiveEndDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_HOUSING_ALLOWANCE.HOUSE_ALLOWANCE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    private String houseAllowance;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_HOUSING_ALLOWANCE.CREATED_BY
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_HOUSING_ALLOWANCE.CREATION_DATE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_HOUSING_ALLOWANCE.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_HOUSING_ALLOWANCE.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_HOUSING_ALLOWANCE.IS_LUMPSUM_REQUESTED
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    private Boolean isLumpsumRequested;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_HOUSING_ALLOWANCE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_HOUSING_ALLOWANCE.HOUSING_ALLOWANCE_ID
     *
     * @return the value of PEA_HOUSING_ALLOWANCE.HOUSING_ALLOWANCE_ID
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public Long getHousingAllowanceId() {
        return housingAllowanceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_HOUSING_ALLOWANCE.HOUSING_ALLOWANCE_ID
     *
     * @param housingAllowanceId the value for PEA_HOUSING_ALLOWANCE.HOUSING_ALLOWANCE_ID
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public void setHousingAllowanceId(Long housingAllowanceId) {
        this.housingAllowanceId = housingAllowanceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_HOUSING_ALLOWANCE.PERSON_ID
     *
     * @return the value of PEA_HOUSING_ALLOWANCE.PERSON_ID
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public Long getPersonId() {
        return personId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_HOUSING_ALLOWANCE.PERSON_ID
     *
     * @param personId the value for PEA_HOUSING_ALLOWANCE.PERSON_ID
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_HOUSING_ALLOWANCE.COMPANY_ID
     *
     * @return the value of PEA_HOUSING_ALLOWANCE.COMPANY_ID
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_HOUSING_ALLOWANCE.COMPANY_ID
     *
     * @param companyId the value for PEA_HOUSING_ALLOWANCE.COMPANY_ID
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_HOUSING_ALLOWANCE.EFFECTIVE_START_DATE
     *
     * @return the value of PEA_HOUSING_ALLOWANCE.EFFECTIVE_START_DATE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_HOUSING_ALLOWANCE.EFFECTIVE_START_DATE
     *
     * @param effectiveStartDate the value for PEA_HOUSING_ALLOWANCE.EFFECTIVE_START_DATE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_HOUSING_ALLOWANCE.EFFECTIVE_END_DATE
     *
     * @return the value of PEA_HOUSING_ALLOWANCE.EFFECTIVE_END_DATE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_HOUSING_ALLOWANCE.EFFECTIVE_END_DATE
     *
     * @param effectiveEndDate the value for PEA_HOUSING_ALLOWANCE.EFFECTIVE_END_DATE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_HOUSING_ALLOWANCE.HOUSE_ALLOWANCE
     *
     * @return the value of PEA_HOUSING_ALLOWANCE.HOUSE_ALLOWANCE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public String getHouseAllowance() {
        return houseAllowance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_HOUSING_ALLOWANCE.HOUSE_ALLOWANCE
     *
     * @param houseAllowance the value for PEA_HOUSING_ALLOWANCE.HOUSE_ALLOWANCE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public void setHouseAllowance(String houseAllowance) {
        this.houseAllowance = houseAllowance == null ? null : houseAllowance.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_HOUSING_ALLOWANCE.CREATED_BY
     *
     * @return the value of PEA_HOUSING_ALLOWANCE.CREATED_BY
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_HOUSING_ALLOWANCE.CREATED_BY
     *
     * @param createdBy the value for PEA_HOUSING_ALLOWANCE.CREATED_BY
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_HOUSING_ALLOWANCE.CREATION_DATE
     *
     * @return the value of PEA_HOUSING_ALLOWANCE.CREATION_DATE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_HOUSING_ALLOWANCE.CREATION_DATE
     *
     * @param creationDate the value for PEA_HOUSING_ALLOWANCE.CREATION_DATE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_HOUSING_ALLOWANCE.LAST_UPDATED_BY
     *
     * @return the value of PEA_HOUSING_ALLOWANCE.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_HOUSING_ALLOWANCE.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for PEA_HOUSING_ALLOWANCE.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_HOUSING_ALLOWANCE.LAST_UPDATE_DATE
     *
     * @return the value of PEA_HOUSING_ALLOWANCE.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_HOUSING_ALLOWANCE.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for PEA_HOUSING_ALLOWANCE.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_HOUSING_ALLOWANCE.IS_LUMPSUM_REQUESTED
     *
     * @return the value of PEA_HOUSING_ALLOWANCE.IS_LUMPSUM_REQUESTED
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public Boolean getIsLumpsumRequested() {
        return isLumpsumRequested;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_HOUSING_ALLOWANCE.IS_LUMPSUM_REQUESTED
     *
     * @param isLumpsumRequested the value for PEA_HOUSING_ALLOWANCE.IS_LUMPSUM_REQUESTED
     *
     * @mbggenerated Tue Nov 25 16:59:26 ICT 2014
     */
    public void setIsLumpsumRequested(Boolean isLumpsumRequested) {
        this.isLumpsumRequested = isLumpsumRequested;
    }
}