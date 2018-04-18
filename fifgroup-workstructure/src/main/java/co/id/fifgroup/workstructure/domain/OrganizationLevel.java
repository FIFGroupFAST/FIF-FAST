package co.id.fifgroup.workstructure.domain;

import java.io.Serializable;
import java.util.Date;

public class OrganizationLevel implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATION_LEVELS.LEVEL_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long levelId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATION_LEVELS.LEVEL_CODE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private String levelCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATION_LEVELS.COMPANY_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long companyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATION_LEVELS.LEVEL_NAME
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private String levelName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATION_LEVELS.DESCRIPTION
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATION_LEVELS.COLOR
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private String color;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATION_LEVELS.START_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Date startDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATION_LEVELS.END_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Date endDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATION_LEVELS.CREATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATION_LEVELS.CREATION_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATION_LEVELS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATION_LEVELS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_ORGANIZATION_LEVELS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATION_LEVELS.LEVEL_ID
     *
     * @return the value of WOS_ORGANIZATION_LEVELS.LEVEL_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getLevelId() {
        return levelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATION_LEVELS.LEVEL_ID
     *
     * @param levelId the value for WOS_ORGANIZATION_LEVELS.LEVEL_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATION_LEVELS.LEVEL_CODE
     *
     * @return the value of WOS_ORGANIZATION_LEVELS.LEVEL_CODE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATION_LEVELS.LEVEL_CODE
     *
     * @param levelCode the value for WOS_ORGANIZATION_LEVELS.LEVEL_CODE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode == null ? null : levelCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATION_LEVELS.COMPANY_ID
     *
     * @return the value of WOS_ORGANIZATION_LEVELS.COMPANY_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATION_LEVELS.COMPANY_ID
     *
     * @param companyId the value for WOS_ORGANIZATION_LEVELS.COMPANY_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATION_LEVELS.LEVEL_NAME
     *
     * @return the value of WOS_ORGANIZATION_LEVELS.LEVEL_NAME
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATION_LEVELS.LEVEL_NAME
     *
     * @param levelName the value for WOS_ORGANIZATION_LEVELS.LEVEL_NAME
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATION_LEVELS.DESCRIPTION
     *
     * @return the value of WOS_ORGANIZATION_LEVELS.DESCRIPTION
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATION_LEVELS.DESCRIPTION
     *
     * @param description the value for WOS_ORGANIZATION_LEVELS.DESCRIPTION
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATION_LEVELS.COLOR
     *
     * @return the value of WOS_ORGANIZATION_LEVELS.COLOR
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public String getColor() {
        return color;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATION_LEVELS.COLOR
     *
     * @param color the value for WOS_ORGANIZATION_LEVELS.COLOR
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATION_LEVELS.START_DATE
     *
     * @return the value of WOS_ORGANIZATION_LEVELS.START_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATION_LEVELS.START_DATE
     *
     * @param startDate the value for WOS_ORGANIZATION_LEVELS.START_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATION_LEVELS.END_DATE
     *
     * @return the value of WOS_ORGANIZATION_LEVELS.END_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATION_LEVELS.END_DATE
     *
     * @param endDate the value for WOS_ORGANIZATION_LEVELS.END_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATION_LEVELS.CREATED_BY
     *
     * @return the value of WOS_ORGANIZATION_LEVELS.CREATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATION_LEVELS.CREATED_BY
     *
     * @param createdBy the value for WOS_ORGANIZATION_LEVELS.CREATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATION_LEVELS.CREATION_DATE
     *
     * @return the value of WOS_ORGANIZATION_LEVELS.CREATION_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATION_LEVELS.CREATION_DATE
     *
     * @param creationDate the value for WOS_ORGANIZATION_LEVELS.CREATION_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATION_LEVELS.LAST_UPDATED_BY
     *
     * @return the value of WOS_ORGANIZATION_LEVELS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATION_LEVELS.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for WOS_ORGANIZATION_LEVELS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATION_LEVELS.LAST_UPDATE_DATE
     *
     * @return the value of WOS_ORGANIZATION_LEVELS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATION_LEVELS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for WOS_ORGANIZATION_LEVELS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}