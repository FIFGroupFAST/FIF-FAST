package co.id.fifgroup.systemworkflow.domain;

import java.io.Serializable;
import java.util.Date;

public class TemplateMessageMapping implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_TEMPLATE_MESSAGE_MAPPING.TEMPLATE_MAPPING_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Long templateMappingId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_TEMPLATE_MESSAGE_MAPPING.TEMPLATE_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Long templateId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_TEMPLATE_MESSAGE_MAPPING.TRANSACTION_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Long transactionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_TEMPLATE_MESSAGE_MAPPING.ACTION_TYPE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private String actionType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_TEMPLATE_MESSAGE_MAPPING.URL_DETAIL
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private String urlDetail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_TEMPLATE_MESSAGE_MAPPING.CREATED_BY
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_TEMPLATE_MESSAGE_MAPPING.CREATED_DATE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Date createdDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_TEMPLATE_MESSAGE_MAPPING.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_TEMPLATE_MESSAGE_MAPPING.LAST_UPDATED_DATE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private Date lastUpdatedDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SWF_TEMPLATE_MESSAGE_MAPPING
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.TEMPLATE_MAPPING_ID
     *
     * @return the value of SWF_TEMPLATE_MESSAGE_MAPPING.TEMPLATE_MAPPING_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Long getTemplateMappingId() {
        return templateMappingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.TEMPLATE_MAPPING_ID
     *
     * @param templateMappingId the value for SWF_TEMPLATE_MESSAGE_MAPPING.TEMPLATE_MAPPING_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setTemplateMappingId(Long templateMappingId) {
        this.templateMappingId = templateMappingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.TEMPLATE_ID
     *
     * @return the value of SWF_TEMPLATE_MESSAGE_MAPPING.TEMPLATE_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Long getTemplateId() {
        return templateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.TEMPLATE_ID
     *
     * @param templateId the value for SWF_TEMPLATE_MESSAGE_MAPPING.TEMPLATE_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.TRANSACTION_ID
     *
     * @return the value of SWF_TEMPLATE_MESSAGE_MAPPING.TRANSACTION_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Long getTransactionId() {
        return transactionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.TRANSACTION_ID
     *
     * @param transactionId the value for SWF_TEMPLATE_MESSAGE_MAPPING.TRANSACTION_ID
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.ACTION_TYPE
     *
     * @return the value of SWF_TEMPLATE_MESSAGE_MAPPING.ACTION_TYPE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.ACTION_TYPE
     *
     * @param actionType the value for SWF_TEMPLATE_MESSAGE_MAPPING.ACTION_TYPE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setActionType(String actionType) {
        this.actionType = actionType == null ? null : actionType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.URL_DETAIL
     *
     * @return the value of SWF_TEMPLATE_MESSAGE_MAPPING.URL_DETAIL
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public String getUrlDetail() {
        return urlDetail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.URL_DETAIL
     *
     * @param urlDetail the value for SWF_TEMPLATE_MESSAGE_MAPPING.URL_DETAIL
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setUrlDetail(String urlDetail) {
        this.urlDetail = urlDetail == null ? null : urlDetail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.CREATED_BY
     *
     * @return the value of SWF_TEMPLATE_MESSAGE_MAPPING.CREATED_BY
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.CREATED_BY
     *
     * @param createdBy the value for SWF_TEMPLATE_MESSAGE_MAPPING.CREATED_BY
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.CREATED_DATE
     *
     * @return the value of SWF_TEMPLATE_MESSAGE_MAPPING.CREATED_DATE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.CREATED_DATE
     *
     * @param createdDate the value for SWF_TEMPLATE_MESSAGE_MAPPING.CREATED_DATE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.LAST_UPDATED_BY
     *
     * @return the value of SWF_TEMPLATE_MESSAGE_MAPPING.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for SWF_TEMPLATE_MESSAGE_MAPPING.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.LAST_UPDATED_DATE
     *
     * @return the value of SWF_TEMPLATE_MESSAGE_MAPPING.LAST_UPDATED_DATE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_TEMPLATE_MESSAGE_MAPPING.LAST_UPDATED_DATE
     *
     * @param lastUpdatedDate the value for SWF_TEMPLATE_MESSAGE_MAPPING.LAST_UPDATED_DATE
     *
     * @mbggenerated Tue Mar 05 17:01:53 ICT 2013
     */
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}