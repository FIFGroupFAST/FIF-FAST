package co.id.fifgroup.systemworkflow.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class VacationRule implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_VACATION_RULE.VACATION_RULE_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Long vacationRuleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_VACATION_RULE.EFFECTIVE_DATE_FROM
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Date effectiveDateFrom;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_VACATION_RULE.EFFECTIVE_DATE_TO
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Date effectiveDateTo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_VACATION_RULE.CREATED_BY
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_VACATION_RULE.CREATED_DATE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Date createdDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_VACATION_RULE.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_VACATION_RULE.LAST_UPDATED_DATE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Date lastUpdatedDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_VACATION_RULE.APPROVER_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private UUID approverId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_VACATION_RULE.SUBSTITUTE_APPROVER_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private UUID substituteApproverId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SWF_VACATION_RULE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_VACATION_RULE.VACATION_RULE_ID
     *
     * @return the value of SWF_VACATION_RULE.VACATION_RULE_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Long getVacationRuleId() {
        return vacationRuleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_VACATION_RULE.VACATION_RULE_ID
     *
     * @param vacationRuleId the value for SWF_VACATION_RULE.VACATION_RULE_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setVacationRuleId(Long vacationRuleId) {
        this.vacationRuleId = vacationRuleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_VACATION_RULE.EFFECTIVE_DATE_FROM
     *
     * @return the value of SWF_VACATION_RULE.EFFECTIVE_DATE_FROM
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Date getEffectiveDateFrom() {
        return effectiveDateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_VACATION_RULE.EFFECTIVE_DATE_FROM
     *
     * @param effectiveDateFrom the value for SWF_VACATION_RULE.EFFECTIVE_DATE_FROM
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setEffectiveDateFrom(Date effectiveDateFrom) {
        this.effectiveDateFrom = effectiveDateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_VACATION_RULE.EFFECTIVE_DATE_TO
     *
     * @return the value of SWF_VACATION_RULE.EFFECTIVE_DATE_TO
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Date getEffectiveDateTo() {
        return effectiveDateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_VACATION_RULE.EFFECTIVE_DATE_TO
     *
     * @param effectiveDateTo the value for SWF_VACATION_RULE.EFFECTIVE_DATE_TO
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setEffectiveDateTo(Date effectiveDateTo) {
        this.effectiveDateTo = effectiveDateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_VACATION_RULE.CREATED_BY
     *
     * @return the value of SWF_VACATION_RULE.CREATED_BY
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_VACATION_RULE.CREATED_BY
     *
     * @param createdBy the value for SWF_VACATION_RULE.CREATED_BY
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_VACATION_RULE.CREATED_DATE
     *
     * @return the value of SWF_VACATION_RULE.CREATED_DATE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_VACATION_RULE.CREATED_DATE
     *
     * @param createdDate the value for SWF_VACATION_RULE.CREATED_DATE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_VACATION_RULE.LAST_UPDATED_BY
     *
     * @return the value of SWF_VACATION_RULE.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_VACATION_RULE.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for SWF_VACATION_RULE.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_VACATION_RULE.LAST_UPDATED_DATE
     *
     * @return the value of SWF_VACATION_RULE.LAST_UPDATED_DATE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_VACATION_RULE.LAST_UPDATED_DATE
     *
     * @param lastUpdatedDate the value for SWF_VACATION_RULE.LAST_UPDATED_DATE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_VACATION_RULE.APPROVER_ID
     *
     * @return the value of SWF_VACATION_RULE.APPROVER_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public UUID getApproverId() {
        return approverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_VACATION_RULE.APPROVER_ID
     *
     * @param approverId the value for SWF_VACATION_RULE.APPROVER_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setApproverId(UUID approverId) {
        this.approverId = approverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_VACATION_RULE.SUBSTITUTE_APPROVER_ID
     *
     * @return the value of SWF_VACATION_RULE.SUBSTITUTE_APPROVER_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public UUID getSubstituteApproverId() {
        return substituteApproverId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_VACATION_RULE.SUBSTITUTE_APPROVER_ID
     *
     * @param substituteApproverId the value for SWF_VACATION_RULE.SUBSTITUTE_APPROVER_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setSubstituteApproverId(UUID substituteApproverId) {
        this.substituteApproverId = substituteApproverId;
    }
}