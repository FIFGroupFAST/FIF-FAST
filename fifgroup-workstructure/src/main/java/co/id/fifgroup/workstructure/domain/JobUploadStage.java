package co.id.fifgroup.workstructure.domain;

import java.io.Serializable;
import java.util.Date;

public class JobUploadStage implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.UPLOAD_ID
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private Long uploadId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.DATE_FROM
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private Date dateFrom;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.DATE_TO
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private Date dateTo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.JOB_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private String jobCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.JOB_NAME
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private String jobName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.DESCRIPTION
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.JOB_FOR_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private String jobForCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.JOB_TYPE_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private String jobTypeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.PEOPLE_CATEGORY_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private String peopleCategoryCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.JOB_GROUP_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private String jobGroupCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.IS_KEY_JOB
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private boolean isKeyJob;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.IS_MANAGER
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private boolean isManager;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.GRADE_SET_ID
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private Long gradeSetId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.ROW_NUMBER
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private Integer rowNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.RAW_DATA
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private String rawData;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.IS_CLOSED
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private Boolean isClosed;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_STG.WORKING_SCHEDULE_ID
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private Long workingScheduleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_JOB_STG
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.UPLOAD_ID
     *
     * @return the value of WOS_JOB_STG.UPLOAD_ID
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public Long getUploadId() {
        return uploadId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.UPLOAD_ID
     *
     * @param uploadId the value for WOS_JOB_STG.UPLOAD_ID
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.DATE_FROM
     *
     * @return the value of WOS_JOB_STG.DATE_FROM
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.DATE_FROM
     *
     * @param dateFrom the value for WOS_JOB_STG.DATE_FROM
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.DATE_TO
     *
     * @return the value of WOS_JOB_STG.DATE_TO
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.DATE_TO
     *
     * @param dateTo the value for WOS_JOB_STG.DATE_TO
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.JOB_CODE
     *
     * @return the value of WOS_JOB_STG.JOB_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public String getJobCode() {
        return jobCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.JOB_CODE
     *
     * @param jobCode the value for WOS_JOB_STG.JOB_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode == null ? null : jobCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.JOB_NAME
     *
     * @return the value of WOS_JOB_STG.JOB_NAME
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.JOB_NAME
     *
     * @param jobName the value for WOS_JOB_STG.JOB_NAME
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.DESCRIPTION
     *
     * @return the value of WOS_JOB_STG.DESCRIPTION
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.DESCRIPTION
     *
     * @param description the value for WOS_JOB_STG.DESCRIPTION
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.JOB_FOR_CODE
     *
     * @return the value of WOS_JOB_STG.JOB_FOR_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public String getJobForCode() {
        return jobForCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.JOB_FOR_CODE
     *
     * @param jobForCode the value for WOS_JOB_STG.JOB_FOR_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setJobForCode(String jobForCode) {
        this.jobForCode = jobForCode == null ? null : jobForCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.JOB_TYPE_CODE
     *
     * @return the value of WOS_JOB_STG.JOB_TYPE_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public String getJobTypeCode() {
        return jobTypeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.JOB_TYPE_CODE
     *
     * @param jobTypeCode the value for WOS_JOB_STG.JOB_TYPE_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setJobTypeCode(String jobTypeCode) {
        this.jobTypeCode = jobTypeCode == null ? null : jobTypeCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.PEOPLE_CATEGORY_CODE
     *
     * @return the value of WOS_JOB_STG.PEOPLE_CATEGORY_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public String getPeopleCategoryCode() {
        return peopleCategoryCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.PEOPLE_CATEGORY_CODE
     *
     * @param peopleCategoryCode the value for WOS_JOB_STG.PEOPLE_CATEGORY_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setPeopleCategoryCode(String peopleCategoryCode) {
        this.peopleCategoryCode = peopleCategoryCode == null ? null : peopleCategoryCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.JOB_GROUP_CODE
     *
     * @return the value of WOS_JOB_STG.JOB_GROUP_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public String getJobGroupCode() {
        return jobGroupCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.JOB_GROUP_CODE
     *
     * @param jobGroupCode the value for WOS_JOB_STG.JOB_GROUP_CODE
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setJobGroupCode(String jobGroupCode) {
        this.jobGroupCode = jobGroupCode == null ? null : jobGroupCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.IS_KEY_JOB
     *
     * @return the value of WOS_JOB_STG.IS_KEY_JOB
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public boolean isIsKeyJob() {
        return isKeyJob;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.IS_KEY_JOB
     *
     * @param isKeyJob the value for WOS_JOB_STG.IS_KEY_JOB
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setIsKeyJob(boolean isKeyJob) {
        this.isKeyJob = isKeyJob;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.IS_MANAGER
     *
     * @return the value of WOS_JOB_STG.IS_MANAGER
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public boolean isIsManager() {
        return isManager;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.IS_MANAGER
     *
     * @param isManager the value for WOS_JOB_STG.IS_MANAGER
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.GRADE_SET_ID
     *
     * @return the value of WOS_JOB_STG.GRADE_SET_ID
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public Long getGradeSetId() {
        return gradeSetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.GRADE_SET_ID
     *
     * @param gradeSetId the value for WOS_JOB_STG.GRADE_SET_ID
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setGradeSetId(Long gradeSetId) {
        this.gradeSetId = gradeSetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.ROW_NUMBER
     *
     * @return the value of WOS_JOB_STG.ROW_NUMBER
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public Integer getRowNumber() {
        return rowNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.ROW_NUMBER
     *
     * @param rowNumber the value for WOS_JOB_STG.ROW_NUMBER
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.RAW_DATA
     *
     * @return the value of WOS_JOB_STG.RAW_DATA
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.RAW_DATA
     *
     * @param rawData the value for WOS_JOB_STG.RAW_DATA
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setRawData(String rawData) {
        this.rawData = rawData == null ? null : rawData.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.IS_CLOSED
     *
     * @return the value of WOS_JOB_STG.IS_CLOSED
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public Boolean getIsClosed() {
        return isClosed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.IS_CLOSED
     *
     * @param isClosed the value for WOS_JOB_STG.IS_CLOSED
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_STG.WORKING_SCHEDULE_ID
     *
     * @return the value of WOS_JOB_STG.WORKING_SCHEDULE_ID
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public Long getWorkingScheduleId() {
        return workingScheduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_STG.WORKING_SCHEDULE_ID
     *
     * @param workingScheduleId the value for WOS_JOB_STG.WORKING_SCHEDULE_ID
     *
     * @mbggenerated Wed Jul 03 15:34:16 ICT 2013
     */
    public void setWorkingScheduleId(Long workingScheduleId) {
        this.workingScheduleId = workingScheduleId;
    }
}