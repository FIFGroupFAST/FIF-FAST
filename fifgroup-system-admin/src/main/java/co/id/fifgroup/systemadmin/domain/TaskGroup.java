package co.id.fifgroup.systemadmin.domain;

import java.io.Serializable;
import java.util.Date;

public class TaskGroup implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_GROUP_HDR.TASK_GROUP_ID
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_GROUP_HDR.TASK_GROUP_NAME
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    private String taskGroupName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_GROUP_HDR.DESCRIPTION
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_GROUP_HDR.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_GROUP_HDR.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_GROUP_HDR.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_TASK_GROUP_HDR.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_TASK_GROUP_HDR
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_GROUP_HDR.TASK_GROUP_ID
     *
     * @return the value of SAM_TASK_GROUP_HDR.TASK_GROUP_ID
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_GROUP_HDR.TASK_GROUP_ID
     *
     * @param id the value for SAM_TASK_GROUP_HDR.TASK_GROUP_ID
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_GROUP_HDR.TASK_GROUP_NAME
     *
     * @return the value of SAM_TASK_GROUP_HDR.TASK_GROUP_NAME
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public String getTaskGroupName() {
        return taskGroupName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_GROUP_HDR.TASK_GROUP_NAME
     *
     * @param taskGroupName the value for SAM_TASK_GROUP_HDR.TASK_GROUP_NAME
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public void setTaskGroupName(String taskGroupName) {
        this.taskGroupName = taskGroupName == null ? null : taskGroupName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_GROUP_HDR.DESCRIPTION
     *
     * @return the value of SAM_TASK_GROUP_HDR.DESCRIPTION
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_GROUP_HDR.DESCRIPTION
     *
     * @param description the value for SAM_TASK_GROUP_HDR.DESCRIPTION
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_GROUP_HDR.CREATED_BY
     *
     * @return the value of SAM_TASK_GROUP_HDR.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_GROUP_HDR.CREATED_BY
     *
     * @param createdBy the value for SAM_TASK_GROUP_HDR.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_GROUP_HDR.CREATION_DATE
     *
     * @return the value of SAM_TASK_GROUP_HDR.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_GROUP_HDR.CREATION_DATE
     *
     * @param creationDate the value for SAM_TASK_GROUP_HDR.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_GROUP_HDR.LAST_UPDATE_DATE
     *
     * @return the value of SAM_TASK_GROUP_HDR.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_GROUP_HDR.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for SAM_TASK_GROUP_HDR.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_TASK_GROUP_HDR.LAST_UPDATED_BY
     *
     * @return the value of SAM_TASK_GROUP_HDR.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_TASK_GROUP_HDR.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for SAM_TASK_GROUP_HDR.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 21:54:16 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}