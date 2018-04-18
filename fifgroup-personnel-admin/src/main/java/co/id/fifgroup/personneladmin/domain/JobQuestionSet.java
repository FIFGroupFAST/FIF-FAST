package co.id.fifgroup.personneladmin.domain;

import java.io.Serializable;
import java.util.Date;

public class JobQuestionSet implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_JOB_QUESTION_SETS.JOB_QUESTION_SET_ID
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    private Long jobQuestionSetId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_JOB_QUESTION_SETS.JOB_ID
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    private Long jobId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_JOB_QUESTION_SETS.QUESTION_SET_ID
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    private Long questionSetId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_JOB_QUESTION_SETS.START_DATE
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    private Date startDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_JOB_QUESTION_SETS.END_DATE
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    private Date endDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_JOB_QUESTION_SETS.COMPANY_ID
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    private Long companyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_JOB_QUESTION_SETS.CREATED_BY
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_JOB_QUESTION_SETS.CREATION_DATE
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_JOB_QUESTION_SETS.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_JOB_QUESTION_SETS.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_JOB_QUESTION_SETS
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_JOB_QUESTION_SETS.JOB_QUESTION_SET_ID
     *
     * @return the value of PEA_JOB_QUESTION_SETS.JOB_QUESTION_SET_ID
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public Long getJobQuestionSetId() {
        return jobQuestionSetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_JOB_QUESTION_SETS.JOB_QUESTION_SET_ID
     *
     * @param jobQuestionSetId the value for PEA_JOB_QUESTION_SETS.JOB_QUESTION_SET_ID
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public void setJobQuestionSetId(Long jobQuestionSetId) {
        this.jobQuestionSetId = jobQuestionSetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_JOB_QUESTION_SETS.JOB_ID
     *
     * @return the value of PEA_JOB_QUESTION_SETS.JOB_ID
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public Long getJobId() {
        return jobId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_JOB_QUESTION_SETS.JOB_ID
     *
     * @param jobId the value for PEA_JOB_QUESTION_SETS.JOB_ID
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_JOB_QUESTION_SETS.QUESTION_SET_ID
     *
     * @return the value of PEA_JOB_QUESTION_SETS.QUESTION_SET_ID
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public Long getQuestionSetId() {
        return questionSetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_JOB_QUESTION_SETS.QUESTION_SET_ID
     *
     * @param questionSetId the value for PEA_JOB_QUESTION_SETS.QUESTION_SET_ID
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public void setQuestionSetId(Long questionSetId) {
        this.questionSetId = questionSetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_JOB_QUESTION_SETS.START_DATE
     *
     * @return the value of PEA_JOB_QUESTION_SETS.START_DATE
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_JOB_QUESTION_SETS.START_DATE
     *
     * @param startDate the value for PEA_JOB_QUESTION_SETS.START_DATE
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_JOB_QUESTION_SETS.END_DATE
     *
     * @return the value of PEA_JOB_QUESTION_SETS.END_DATE
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_JOB_QUESTION_SETS.END_DATE
     *
     * @param endDate the value for PEA_JOB_QUESTION_SETS.END_DATE
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_JOB_QUESTION_SETS.COMPANY_ID
     *
     * @return the value of PEA_JOB_QUESTION_SETS.COMPANY_ID
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_JOB_QUESTION_SETS.COMPANY_ID
     *
     * @param companyId the value for PEA_JOB_QUESTION_SETS.COMPANY_ID
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_JOB_QUESTION_SETS.CREATED_BY
     *
     * @return the value of PEA_JOB_QUESTION_SETS.CREATED_BY
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_JOB_QUESTION_SETS.CREATED_BY
     *
     * @param createdBy the value for PEA_JOB_QUESTION_SETS.CREATED_BY
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_JOB_QUESTION_SETS.CREATION_DATE
     *
     * @return the value of PEA_JOB_QUESTION_SETS.CREATION_DATE
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_JOB_QUESTION_SETS.CREATION_DATE
     *
     * @param creationDate the value for PEA_JOB_QUESTION_SETS.CREATION_DATE
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_JOB_QUESTION_SETS.LAST_UPDATED_BY
     *
     * @return the value of PEA_JOB_QUESTION_SETS.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_JOB_QUESTION_SETS.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for PEA_JOB_QUESTION_SETS.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_JOB_QUESTION_SETS.LAST_UPDATE_DATE
     *
     * @return the value of PEA_JOB_QUESTION_SETS.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_JOB_QUESTION_SETS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for PEA_JOB_QUESTION_SETS.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Jun 18 18:38:31 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}