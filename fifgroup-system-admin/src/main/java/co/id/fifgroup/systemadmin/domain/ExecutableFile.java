package co.id.fifgroup.systemadmin.domain;

import java.io.Serializable;
import java.util.Date;

public class ExecutableFile implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_EXECUTABLE_FILES.EXECUTABLE_FILE_ID
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_EXECUTABLE_FILES.MODULE_ID
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    private Long moduleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_EXECUTABLE_FILES.EXECUTABLE_FILE_NAME
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    private String executableFileName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_EXECUTABLE_FILES.TASK_TYPE
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    private String taskType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_EXECUTABLE_FILES.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_EXECUTABLE_FILES.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_EXECUTABLE_FILES.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_EXECUTABLE_FILES.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_EXECUTABLE_FILES
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_EXECUTABLE_FILES.EXECUTABLE_FILE_ID
     *
     * @return the value of SAM_EXECUTABLE_FILES.EXECUTABLE_FILE_ID
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_EXECUTABLE_FILES.EXECUTABLE_FILE_ID
     *
     * @param id the value for SAM_EXECUTABLE_FILES.EXECUTABLE_FILE_ID
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_EXECUTABLE_FILES.MODULE_ID
     *
     * @return the value of SAM_EXECUTABLE_FILES.MODULE_ID
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public Long getModuleId() {
        return moduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_EXECUTABLE_FILES.MODULE_ID
     *
     * @param moduleId the value for SAM_EXECUTABLE_FILES.MODULE_ID
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_EXECUTABLE_FILES.EXECUTABLE_FILE_NAME
     *
     * @return the value of SAM_EXECUTABLE_FILES.EXECUTABLE_FILE_NAME
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public String getExecutableFileName() {
        return executableFileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_EXECUTABLE_FILES.EXECUTABLE_FILE_NAME
     *
     * @param executableFileName the value for SAM_EXECUTABLE_FILES.EXECUTABLE_FILE_NAME
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public void setExecutableFileName(String executableFileName) {
        this.executableFileName = executableFileName == null ? null : executableFileName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_EXECUTABLE_FILES.TASK_TYPE
     *
     * @return the value of SAM_EXECUTABLE_FILES.TASK_TYPE
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_EXECUTABLE_FILES.TASK_TYPE
     *
     * @param taskType the value for SAM_EXECUTABLE_FILES.TASK_TYPE
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType == null ? null : taskType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_EXECUTABLE_FILES.CREATED_BY
     *
     * @return the value of SAM_EXECUTABLE_FILES.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_EXECUTABLE_FILES.CREATED_BY
     *
     * @param createdBy the value for SAM_EXECUTABLE_FILES.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_EXECUTABLE_FILES.CREATION_DATE
     *
     * @return the value of SAM_EXECUTABLE_FILES.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_EXECUTABLE_FILES.CREATION_DATE
     *
     * @param creationDate the value for SAM_EXECUTABLE_FILES.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_EXECUTABLE_FILES.LAST_UPDATED_BY
     *
     * @return the value of SAM_EXECUTABLE_FILES.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_EXECUTABLE_FILES.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for SAM_EXECUTABLE_FILES.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_EXECUTABLE_FILES.LAST_UPDATE_DATE
     *
     * @return the value of SAM_EXECUTABLE_FILES.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_EXECUTABLE_FILES.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for SAM_EXECUTABLE_FILES.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}