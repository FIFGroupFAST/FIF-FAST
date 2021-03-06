package co.id.fifgroup.workstructure.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GradeRate implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_RATES.GRADE_RATE_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_RATES.VERSION_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long versionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_RATES.BRANCH_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long branchId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_RATES.MIN_SALARY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private BigDecimal minSalary;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_RATES.MAX_SALARY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private BigDecimal maxSalary;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_RATES.CREATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_RATES.CREATION_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_RATES.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_RATES.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_GRADE_RATES
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_RATES.GRADE_RATE_ID
     *
     * @return the value of WOS_GRADE_RATES.GRADE_RATE_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_RATES.GRADE_RATE_ID
     *
     * @param id the value for WOS_GRADE_RATES.GRADE_RATE_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_RATES.VERSION_ID
     *
     * @return the value of WOS_GRADE_RATES.VERSION_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getVersionId() {
        return versionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_RATES.VERSION_ID
     *
     * @param versionId the value for WOS_GRADE_RATES.VERSION_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_RATES.BRANCH_ID
     *
     * @return the value of WOS_GRADE_RATES.BRANCH_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getBranchId() {
        return branchId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_RATES.BRANCH_ID
     *
     * @param branchId the value for WOS_GRADE_RATES.BRANCH_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_RATES.MIN_SALARY
     *
     * @return the value of WOS_GRADE_RATES.MIN_SALARY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public BigDecimal getMinSalary() {
        return minSalary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_RATES.MIN_SALARY
     *
     * @param minSalary the value for WOS_GRADE_RATES.MIN_SALARY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setMinSalary(BigDecimal minSalary) {
        this.minSalary = minSalary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_RATES.MAX_SALARY
     *
     * @return the value of WOS_GRADE_RATES.MAX_SALARY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public BigDecimal getMaxSalary() {
        return maxSalary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_RATES.MAX_SALARY
     *
     * @param maxSalary the value for WOS_GRADE_RATES.MAX_SALARY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setMaxSalary(BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_RATES.CREATED_BY
     *
     * @return the value of WOS_GRADE_RATES.CREATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_RATES.CREATED_BY
     *
     * @param createdBy the value for WOS_GRADE_RATES.CREATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_RATES.CREATION_DATE
     *
     * @return the value of WOS_GRADE_RATES.CREATION_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_RATES.CREATION_DATE
     *
     * @param creationDate the value for WOS_GRADE_RATES.CREATION_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_RATES.LAST_UPDATED_BY
     *
     * @return the value of WOS_GRADE_RATES.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_RATES.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for WOS_GRADE_RATES.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_RATES.LAST_UPDATE_DATE
     *
     * @return the value of WOS_GRADE_RATES.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_RATES.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for WOS_GRADE_RATES.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}