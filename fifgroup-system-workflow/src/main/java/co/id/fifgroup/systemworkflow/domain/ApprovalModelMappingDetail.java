package co.id.fifgroup.systemworkflow.domain;

import java.io.Serializable;
import java.util.UUID;

public class ApprovalModelMappingDetail implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_AVM_MAPPING_DETAIL.MAPPING_DETAIL_ID
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    private Long mappingDetailId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_AVM_MAPPING_DETAIL.MODEL_MAPPING_ID
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    private Long modelMappingId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_AVM_MAPPING_DETAIL.PRIORITY
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    private Long priority;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_AVM_MAPPING_DETAIL.CONDITION_RULE
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    private String conditionRule;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SWF_AVM_MAPPING_DETAIL.APPROVAL_MODEL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    private UUID approvalModel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SWF_AVM_MAPPING_DETAIL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_AVM_MAPPING_DETAIL.MAPPING_DETAIL_ID
     *
     * @return the value of SWF_AVM_MAPPING_DETAIL.MAPPING_DETAIL_ID
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    public Long getMappingDetailId() {
        return mappingDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_AVM_MAPPING_DETAIL.MAPPING_DETAIL_ID
     *
     * @param mappingDetailId the value for SWF_AVM_MAPPING_DETAIL.MAPPING_DETAIL_ID
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    public void setMappingDetailId(Long mappingDetailId) {
        this.mappingDetailId = mappingDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_AVM_MAPPING_DETAIL.MODEL_MAPPING_ID
     *
     * @return the value of SWF_AVM_MAPPING_DETAIL.MODEL_MAPPING_ID
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    public Long getModelMappingId() {
        return modelMappingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_AVM_MAPPING_DETAIL.MODEL_MAPPING_ID
     *
     * @param modelMappingId the value for SWF_AVM_MAPPING_DETAIL.MODEL_MAPPING_ID
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    public void setModelMappingId(Long modelMappingId) {
        this.modelMappingId = modelMappingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_AVM_MAPPING_DETAIL.PRIORITY
     *
     * @return the value of SWF_AVM_MAPPING_DETAIL.PRIORITY
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    public Long getPriority() {
        return priority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_AVM_MAPPING_DETAIL.PRIORITY
     *
     * @param priority the value for SWF_AVM_MAPPING_DETAIL.PRIORITY
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    public void setPriority(Long priority) {
        this.priority = priority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_AVM_MAPPING_DETAIL.CONDITION_RULE
     *
     * @return the value of SWF_AVM_MAPPING_DETAIL.CONDITION_RULE
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    public String getConditionRule() {
        return conditionRule;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_AVM_MAPPING_DETAIL.CONDITION_RULE
     *
     * @param conditionRule the value for SWF_AVM_MAPPING_DETAIL.CONDITION_RULE
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    public void setConditionRule(String conditionRule) {
        this.conditionRule = conditionRule == null ? null : conditionRule.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SWF_AVM_MAPPING_DETAIL.APPROVAL_MODEL
     *
     * @return the value of SWF_AVM_MAPPING_DETAIL.APPROVAL_MODEL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    public UUID getApprovalModel() {
        return approvalModel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SWF_AVM_MAPPING_DETAIL.APPROVAL_MODEL
     *
     * @param approvalModel the value for SWF_AVM_MAPPING_DETAIL.APPROVAL_MODEL
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    public void setApprovalModel(UUID approvalModel) {
        this.approvalModel = approvalModel;
    }
}