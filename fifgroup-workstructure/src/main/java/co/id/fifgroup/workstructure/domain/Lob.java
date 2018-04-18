package co.id.fifgroup.workstructure.domain;

import java.io.Serializable;
import java.util.Date;

public class Lob implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOBS.LOB_ID
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOBS.JOB_ID
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    private Long jobId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOBS.EFFECTIVE_START_DATE
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    private Date effectiveStartDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOBS.EFFECTIVE_END_DATE
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    private Date effectiveEndDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOBS.CREATED_BY
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOBS.CREATION_DATE
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOBS.LAST_UPDATED_BY
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOBS.LAST_UPDATE_DATE
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOBS.ORGANIZATION_ID
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    private Long organizationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOBS.LOB_ID
     *
     * @return the value of WOS_LOBS.LOB_ID
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOBS.LOB_ID
     *
     * @param id the value for WOS_LOBS.LOB_ID
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOBS.JOB_ID
     *
     * @return the value of WOS_LOBS.JOB_ID
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public Long getJobId() {
        return jobId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOBS.JOB_ID
     *
     * @param jobId the value for WOS_LOBS.JOB_ID
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOBS.EFFECTIVE_START_DATE
     *
     * @return the value of WOS_LOBS.EFFECTIVE_START_DATE
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOBS.EFFECTIVE_START_DATE
     *
     * @param effectiveStartDate the value for WOS_LOBS.EFFECTIVE_START_DATE
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOBS.EFFECTIVE_END_DATE
     *
     * @return the value of WOS_LOBS.EFFECTIVE_END_DATE
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOBS.EFFECTIVE_END_DATE
     *
     * @param effectiveEndDate the value for WOS_LOBS.EFFECTIVE_END_DATE
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOBS.CREATED_BY
     *
     * @return the value of WOS_LOBS.CREATED_BY
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOBS.CREATED_BY
     *
     * @param createdBy the value for WOS_LOBS.CREATED_BY
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOBS.CREATION_DATE
     *
     * @return the value of WOS_LOBS.CREATION_DATE
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOBS.CREATION_DATE
     *
     * @param creationDate the value for WOS_LOBS.CREATION_DATE
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOBS.LAST_UPDATED_BY
     *
     * @return the value of WOS_LOBS.LAST_UPDATED_BY
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOBS.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for WOS_LOBS.LAST_UPDATED_BY
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOBS.LAST_UPDATE_DATE
     *
     * @return the value of WOS_LOBS.LAST_UPDATE_DATE
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOBS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for WOS_LOBS.LAST_UPDATE_DATE
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOBS.ORGANIZATION_ID
     *
     * @return the value of WOS_LOBS.ORGANIZATION_ID
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOBS.ORGANIZATION_ID
     *
     * @param organizationId the value for WOS_LOBS.ORGANIZATION_ID
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}