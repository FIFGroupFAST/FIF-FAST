package co.id.fifgroup.systemadmin.domain;

import java.io.Serializable;
import java.util.Date;

import co.id.fifgroup.core.audit.Traversable;

public class Function implements Serializable, Traversable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_FUNCTIONS.FUNCTION_ID
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_FUNCTIONS.MODULE_ID
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    private Long moduleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_FUNCTIONS.FUNCTION_NAME
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    private String functionName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_FUNCTIONS.DESCRIPTION
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_FUNCTIONS.ACTION_URL
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    private String actionUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_FUNCTIONS.USER_MANUAL
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    private String userManual;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_FUNCTIONS.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_FUNCTIONS.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_FUNCTIONS.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_FUNCTIONS.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_FUNCTIONS
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_FUNCTIONS.FUNCTION_ID
     *
     * @return the value of SAM_FUNCTIONS.FUNCTION_ID
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_FUNCTIONS.FUNCTION_ID
     *
     * @param id the value for SAM_FUNCTIONS.FUNCTION_ID
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_FUNCTIONS.MODULE_ID
     *
     * @return the value of SAM_FUNCTIONS.MODULE_ID
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public Long getModuleId() {
        return moduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_FUNCTIONS.MODULE_ID
     *
     * @param moduleId the value for SAM_FUNCTIONS.MODULE_ID
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_FUNCTIONS.FUNCTION_NAME
     *
     * @return the value of SAM_FUNCTIONS.FUNCTION_NAME
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_FUNCTIONS.FUNCTION_NAME
     *
     * @param functionName the value for SAM_FUNCTIONS.FUNCTION_NAME
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName == null ? null : functionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_FUNCTIONS.DESCRIPTION
     *
     * @return the value of SAM_FUNCTIONS.DESCRIPTION
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_FUNCTIONS.DESCRIPTION
     *
     * @param description the value for SAM_FUNCTIONS.DESCRIPTION
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_FUNCTIONS.ACTION_URL
     *
     * @return the value of SAM_FUNCTIONS.ACTION_URL
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public String getActionUrl() {
        return actionUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_FUNCTIONS.ACTION_URL
     *
     * @param actionUrl the value for SAM_FUNCTIONS.ACTION_URL
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl == null ? null : actionUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_FUNCTIONS.USER_MANUAL
     *
     * @return the value of SAM_FUNCTIONS.USER_MANUAL
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public String getUserManual() {
        return userManual;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_FUNCTIONS.USER_MANUAL
     *
     * @param userManual the value for SAM_FUNCTIONS.USER_MANUAL
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public void setUserManual(String userManual) {
        this.userManual = userManual == null ? null : userManual.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_FUNCTIONS.CREATED_BY
     *
     * @return the value of SAM_FUNCTIONS.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_FUNCTIONS.CREATED_BY
     *
     * @param createdBy the value for SAM_FUNCTIONS.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_FUNCTIONS.CREATION_DATE
     *
     * @return the value of SAM_FUNCTIONS.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_FUNCTIONS.CREATION_DATE
     *
     * @param creationDate the value for SAM_FUNCTIONS.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_FUNCTIONS.LAST_UPDATED_BY
     *
     * @return the value of SAM_FUNCTIONS.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_FUNCTIONS.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for SAM_FUNCTIONS.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_FUNCTIONS.LAST_UPDATE_DATE
     *
     * @return the value of SAM_FUNCTIONS.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_FUNCTIONS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for SAM_FUNCTIONS.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 11:08:32 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}