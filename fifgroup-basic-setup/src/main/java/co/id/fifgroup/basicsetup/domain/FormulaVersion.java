package co.id.fifgroup.basicsetup.domain;

import java.io.Serializable;
import java.util.Date;

public class FormulaVersion implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_FORMULA_VERSIONS.VERSION_ID
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    private Long versionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_FORMULA_VERSIONS.FORMULA_ID
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_FORMULA_VERSIONS.RETURN_TYPE
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    private String returnType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_FORMULA_VERSIONS.VERSION_NUMBER
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    private Integer versionNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_FORMULA_VERSIONS.DATE_FROM
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    private Date dateFrom;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_FORMULA_VERSIONS.DATE_TO
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    private Date dateTo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_FORMULA_VERSIONS.CREATED_BY
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_FORMULA_VERSIONS.CREATION_DATE
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_FORMULA_VERSIONS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_FORMULA_VERSIONS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_FORMULA_VERSIONS.FORMULA
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    private String formula;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table BSE_FORMULA_VERSIONS
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_FORMULA_VERSIONS.VERSION_ID
     *
     * @return the value of BSE_FORMULA_VERSIONS.VERSION_ID
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public Long getVersionId() {
        return versionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_FORMULA_VERSIONS.VERSION_ID
     *
     * @param versionId the value for BSE_FORMULA_VERSIONS.VERSION_ID
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_FORMULA_VERSIONS.FORMULA_ID
     *
     * @return the value of BSE_FORMULA_VERSIONS.FORMULA_ID
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_FORMULA_VERSIONS.FORMULA_ID
     *
     * @param id the value for BSE_FORMULA_VERSIONS.FORMULA_ID
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_FORMULA_VERSIONS.RETURN_TYPE
     *
     * @return the value of BSE_FORMULA_VERSIONS.RETURN_TYPE
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_FORMULA_VERSIONS.RETURN_TYPE
     *
     * @param returnType the value for BSE_FORMULA_VERSIONS.RETURN_TYPE
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType == null ? null : returnType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_FORMULA_VERSIONS.VERSION_NUMBER
     *
     * @return the value of BSE_FORMULA_VERSIONS.VERSION_NUMBER
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public Integer getVersionNumber() {
        return versionNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_FORMULA_VERSIONS.VERSION_NUMBER
     *
     * @param versionNumber the value for BSE_FORMULA_VERSIONS.VERSION_NUMBER
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_FORMULA_VERSIONS.DATE_FROM
     *
     * @return the value of BSE_FORMULA_VERSIONS.DATE_FROM
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_FORMULA_VERSIONS.DATE_FROM
     *
     * @param dateFrom the value for BSE_FORMULA_VERSIONS.DATE_FROM
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_FORMULA_VERSIONS.DATE_TO
     *
     * @return the value of BSE_FORMULA_VERSIONS.DATE_TO
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_FORMULA_VERSIONS.DATE_TO
     *
     * @param dateTo the value for BSE_FORMULA_VERSIONS.DATE_TO
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_FORMULA_VERSIONS.CREATED_BY
     *
     * @return the value of BSE_FORMULA_VERSIONS.CREATED_BY
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_FORMULA_VERSIONS.CREATED_BY
     *
     * @param createdBy the value for BSE_FORMULA_VERSIONS.CREATED_BY
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_FORMULA_VERSIONS.CREATION_DATE
     *
     * @return the value of BSE_FORMULA_VERSIONS.CREATION_DATE
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_FORMULA_VERSIONS.CREATION_DATE
     *
     * @param creationDate the value for BSE_FORMULA_VERSIONS.CREATION_DATE
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_FORMULA_VERSIONS.LAST_UPDATED_BY
     *
     * @return the value of BSE_FORMULA_VERSIONS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_FORMULA_VERSIONS.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for BSE_FORMULA_VERSIONS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_FORMULA_VERSIONS.LAST_UPDATE_DATE
     *
     * @return the value of BSE_FORMULA_VERSIONS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_FORMULA_VERSIONS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for BSE_FORMULA_VERSIONS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_FORMULA_VERSIONS.FORMULA
     *
     * @return the value of BSE_FORMULA_VERSIONS.FORMULA
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public String getFormula() {
        return formula;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_FORMULA_VERSIONS.FORMULA
     *
     * @param formula the value for BSE_FORMULA_VERSIONS.FORMULA
     *
     * @mbggenerated Wed Mar 27 22:03:56 ICT 2013
     */
    public void setFormula(String formula) {
        this.formula = formula == null ? null : formula.trim();
    }
}