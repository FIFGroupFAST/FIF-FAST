package co.id.fifgroup.workstructure.domain;

import java.io.Serializable;
import java.util.Date;

public class JobInfo implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_INFOS.VERSION_ID
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    private Long versionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_INFOS.INFO_ID
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    private Long infoId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_INFOS.INFO_VALUE
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    private String infoValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_INFOS.CREATED_BY
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_INFOS.CREATION_DATE
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_INFOS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_JOB_INFOS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_JOB_INFOS
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_INFOS.VERSION_ID
     *
     * @return the value of WOS_JOB_INFOS.VERSION_ID
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public Long getVersionId() {
        return versionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_INFOS.VERSION_ID
     *
     * @param versionId the value for WOS_JOB_INFOS.VERSION_ID
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_INFOS.INFO_ID
     *
     * @return the value of WOS_JOB_INFOS.INFO_ID
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public Long getInfoId() {
        return infoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_INFOS.INFO_ID
     *
     * @param infoId the value for WOS_JOB_INFOS.INFO_ID
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_INFOS.INFO_VALUE
     *
     * @return the value of WOS_JOB_INFOS.INFO_VALUE
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public String getInfoValue() {
        return infoValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_INFOS.INFO_VALUE
     *
     * @param infoValue the value for WOS_JOB_INFOS.INFO_VALUE
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public void setInfoValue(String infoValue) {
        this.infoValue = infoValue == null ? null : infoValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_INFOS.CREATED_BY
     *
     * @return the value of WOS_JOB_INFOS.CREATED_BY
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_INFOS.CREATED_BY
     *
     * @param createdBy the value for WOS_JOB_INFOS.CREATED_BY
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_INFOS.CREATION_DATE
     *
     * @return the value of WOS_JOB_INFOS.CREATION_DATE
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_INFOS.CREATION_DATE
     *
     * @param creationDate the value for WOS_JOB_INFOS.CREATION_DATE
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_INFOS.LAST_UPDATED_BY
     *
     * @return the value of WOS_JOB_INFOS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_INFOS.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for WOS_JOB_INFOS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_JOB_INFOS.LAST_UPDATE_DATE
     *
     * @return the value of WOS_JOB_INFOS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_JOB_INFOS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for WOS_JOB_INFOS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Jan 22 12:12:57 ICT 2014
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}