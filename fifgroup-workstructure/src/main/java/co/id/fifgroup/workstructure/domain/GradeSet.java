package co.id.fifgroup.workstructure.domain;

import java.io.Serializable;
import java.util.Date;

public class GradeSet implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SETS.GRADE_SET_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SETS.COMPANY_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long companyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SETS.GRADE_SET_NAME
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private String gradeSetName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SETS.DESCRIPTION
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SETS.CREATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SETS.START_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Date startDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SETS.END_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Date endDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SETS.CREATION_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SETS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SETS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_GRADE_SETS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SETS.GRADE_SET_ID
     *
     * @return the value of WOS_GRADE_SETS.GRADE_SET_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SETS.GRADE_SET_ID
     *
     * @param id the value for WOS_GRADE_SETS.GRADE_SET_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SETS.COMPANY_ID
     *
     * @return the value of WOS_GRADE_SETS.COMPANY_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SETS.COMPANY_ID
     *
     * @param companyId the value for WOS_GRADE_SETS.COMPANY_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SETS.GRADE_SET_NAME
     *
     * @return the value of WOS_GRADE_SETS.GRADE_SET_NAME
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public String getGradeSetName() {
        return gradeSetName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SETS.GRADE_SET_NAME
     *
     * @param gradeSetName the value for WOS_GRADE_SETS.GRADE_SET_NAME
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setGradeSetName(String gradeSetName) {
        this.gradeSetName = gradeSetName == null ? null : gradeSetName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SETS.DESCRIPTION
     *
     * @return the value of WOS_GRADE_SETS.DESCRIPTION
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SETS.DESCRIPTION
     *
     * @param description the value for WOS_GRADE_SETS.DESCRIPTION
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SETS.CREATED_BY
     *
     * @return the value of WOS_GRADE_SETS.CREATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SETS.CREATED_BY
     *
     * @param createdBy the value for WOS_GRADE_SETS.CREATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SETS.START_DATE
     *
     * @return the value of WOS_GRADE_SETS.START_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SETS.START_DATE
     *
     * @param startDate the value for WOS_GRADE_SETS.START_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SETS.END_DATE
     *
     * @return the value of WOS_GRADE_SETS.END_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SETS.END_DATE
     *
     * @param endDate the value for WOS_GRADE_SETS.END_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SETS.CREATION_DATE
     *
     * @return the value of WOS_GRADE_SETS.CREATION_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SETS.CREATION_DATE
     *
     * @param creationDate the value for WOS_GRADE_SETS.CREATION_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SETS.LAST_UPDATED_BY
     *
     * @return the value of WOS_GRADE_SETS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SETS.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for WOS_GRADE_SETS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SETS.LAST_UPDATE_DATE
     *
     * @return the value of WOS_GRADE_SETS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SETS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for WOS_GRADE_SETS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}