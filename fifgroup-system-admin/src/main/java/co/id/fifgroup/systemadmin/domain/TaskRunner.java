package co.id.fifgroup.systemadmin.domain;

import java.io.Serializable;
import java.util.Date;

public class TaskRunner implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_RUNNER_HDR.TASK_RUNNER_ID
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_RUNNER_HDR.TASK_RUNNER_NAME
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    private String taskRunnerName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_RUNNER_HDR.DESCRIPTION
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_RUNNER_HDR.EXECUTION_TYPE
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    private String executionType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_RUNNER_HDR.DATE_FROM
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    private Date dateFrom;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_RUNNER_HDR.DATE_TO
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    private Date dateTo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_RUNNER_HDR.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_RUNNER_HDR.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_RUNNER_HDR.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_RUNNER_HDR.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_TASK_RUNNER_HDR
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_RUNNER_HDR.TASK_RUNNER_ID
     *
     * @return the value of SAM_TASK_RUNNER_HDR.TASK_RUNNER_ID
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_RUNNER_HDR.TASK_RUNNER_ID
     *
     * @param id the value for SAM_TASK_RUNNER_HDR.TASK_RUNNER_ID
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_RUNNER_HDR.TASK_RUNNER_NAME
     *
     * @return the value of SAM_TASK_RUNNER_HDR.TASK_RUNNER_NAME
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public String getTaskRunnerName() {
        return taskRunnerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_RUNNER_HDR.TASK_RUNNER_NAME
     *
     * @param taskRunnerName the value for SAM_TASK_RUNNER_HDR.TASK_RUNNER_NAME
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void setTaskRunnerName(String taskRunnerName) {
        this.taskRunnerName = taskRunnerName == null ? null : taskRunnerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_RUNNER_HDR.DESCRIPTION
     *
     * @return the value of SAM_TASK_RUNNER_HDR.DESCRIPTION
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_RUNNER_HDR.DESCRIPTION
     *
     * @param description the value for SAM_TASK_RUNNER_HDR.DESCRIPTION
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_RUNNER_HDR.EXECUTION_TYPE
     *
     * @return the value of SAM_TASK_RUNNER_HDR.EXECUTION_TYPE
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public String getExecutionType() {
        return executionType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_RUNNER_HDR.EXECUTION_TYPE
     *
     * @param executionType the value for SAM_TASK_RUNNER_HDR.EXECUTION_TYPE
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void setExecutionType(String executionType) {
        this.executionType = executionType == null ? null : executionType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_RUNNER_HDR.DATE_FROM
     *
     * @return the value of SAM_TASK_RUNNER_HDR.DATE_FROM
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_RUNNER_HDR.DATE_FROM
     *
     * @param dateFrom the value for SAM_TASK_RUNNER_HDR.DATE_FROM
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_RUNNER_HDR.DATE_TO
     *
     * @return the value of SAM_TASK_RUNNER_HDR.DATE_TO
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_RUNNER_HDR.DATE_TO
     *
     * @param dateTo the value for SAM_TASK_RUNNER_HDR.DATE_TO
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_RUNNER_HDR.CREATED_BY
     *
     * @return the value of SAM_TASK_RUNNER_HDR.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_RUNNER_HDR.CREATED_BY
     *
     * @param createdBy the value for SAM_TASK_RUNNER_HDR.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_RUNNER_HDR.CREATION_DATE
     *
     * @return the value of SAM_TASK_RUNNER_HDR.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_RUNNER_HDR.CREATION_DATE
     *
     * @param creationDate the value for SAM_TASK_RUNNER_HDR.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_RUNNER_HDR.LAST_UPDATED_BY
     *
     * @return the value of SAM_TASK_RUNNER_HDR.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_RUNNER_HDR.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for SAM_TASK_RUNNER_HDR.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_RUNNER_HDR.LAST_UPDATE_DATE
     *
     * @return the value of SAM_TASK_RUNNER_HDR.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_RUNNER_HDR.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for SAM_TASK_RUNNER_HDR.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}