
package co.id.fifgroup.workstructure.domain;

import java.io.Serializable;
import java.util.Date;

public class OrganizationTemplateDetail implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORG_TEMPLATE_DTL.TEMPLATE_DTL_ID
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private Long templateDtlId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORG_TEMPLATE_DTL.TEMPLATE_ID
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private Long templateId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORG_TEMPLATE_DTL.ORG_NAME
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private String orgName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORG_TEMPLATE_DTL.LEVEL_CODE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private String levelCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORG_TEMPLATE_DTL.ORG_HEAD_ID
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private Long orgHeadId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORG_TEMPLATE_DTL.COST_CENTER_CODE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private String costCenterCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORG_TEMPLATE_DTL.PREFIX_CODE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private String prefixCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORG_TEMPLATE_DTL.IS_PARENT
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private boolean isParent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORG_TEMPLATE_DTL.CREATED_BY
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORG_TEMPLATE_DTL.CREATION_DATE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORG_TEMPLATE_DTL.LAST_UPDATED_BY
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORG_TEMPLATE_DTL.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORG_TEMPLATE_DTL.TEMPLATE_DTL_ID
     *
     * @return the value of WOS_ORG_TEMPLATE_DTL.TEMPLATE_DTL_ID
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public Long getTemplateDtlId() {
        return templateDtlId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORG_TEMPLATE_DTL.TEMPLATE_DTL_ID
     *
     * @param templateDtlId the value for WOS_ORG_TEMPLATE_DTL.TEMPLATE_DTL_ID
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setTemplateDtlId(Long templateDtlId) {
        this.templateDtlId = templateDtlId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORG_TEMPLATE_DTL.TEMPLATE_ID
     *
     * @return the value of WOS_ORG_TEMPLATE_DTL.TEMPLATE_ID
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public Long getTemplateId() {
        return templateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORG_TEMPLATE_DTL.TEMPLATE_ID
     *
     * @param templateId the value for WOS_ORG_TEMPLATE_DTL.TEMPLATE_ID
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORG_TEMPLATE_DTL.ORG_NAME
     *
     * @return the value of WOS_ORG_TEMPLATE_DTL.ORG_NAME
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORG_TEMPLATE_DTL.ORG_NAME
     *
     * @param orgName the value for WOS_ORG_TEMPLATE_DTL.ORG_NAME
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORG_TEMPLATE_DTL.LEVEL_CODE
     *
     * @return the value of WOS_ORG_TEMPLATE_DTL.LEVEL_CODE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORG_TEMPLATE_DTL.LEVEL_CODE
     *
     * @param levelCode the value for WOS_ORG_TEMPLATE_DTL.LEVEL_CODE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode == null ? null : levelCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORG_TEMPLATE_DTL.ORG_HEAD_ID
     *
     * @return the value of WOS_ORG_TEMPLATE_DTL.ORG_HEAD_ID
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public Long getOrgHeadId() {
        return orgHeadId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORG_TEMPLATE_DTL.ORG_HEAD_ID
     *
     * @param orgHeadId the value for WOS_ORG_TEMPLATE_DTL.ORG_HEAD_ID
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setOrgHeadId(Long orgHeadId) {
        this.orgHeadId = orgHeadId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORG_TEMPLATE_DTL.COST_CENTER_CODE
     *
     * @return the value of WOS_ORG_TEMPLATE_DTL.COST_CENTER_CODE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public String getCostCenterCode() {
        return costCenterCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORG_TEMPLATE_DTL.COST_CENTER_CODE
     *
     * @param costCenterCode the value for WOS_ORG_TEMPLATE_DTL.COST_CENTER_CODE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setCostCenterCode(String costCenterCode) {
        this.costCenterCode = costCenterCode == null ? null : costCenterCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORG_TEMPLATE_DTL.PREFIX_CODE
     *
     * @return the value of WOS_ORG_TEMPLATE_DTL.PREFIX_CODE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public String getPrefixCode() {
        return prefixCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORG_TEMPLATE_DTL.PREFIX_CODE
     *
     * @param prefixCode the value for WOS_ORG_TEMPLATE_DTL.PREFIX_CODE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setPrefixCode(String prefixCode) {
        this.prefixCode = prefixCode == null ? null : prefixCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORG_TEMPLATE_DTL.IS_PARENT
     *
     * @return the value of WOS_ORG_TEMPLATE_DTL.IS_PARENT
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public boolean isIsParent() {
        return isParent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORG_TEMPLATE_DTL.IS_PARENT
     *
     * @param isParent the value for WOS_ORG_TEMPLATE_DTL.IS_PARENT
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORG_TEMPLATE_DTL.CREATED_BY
     *
     * @return the value of WOS_ORG_TEMPLATE_DTL.CREATED_BY
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORG_TEMPLATE_DTL.CREATED_BY
     *
     * @param createdBy the value for WOS_ORG_TEMPLATE_DTL.CREATED_BY
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORG_TEMPLATE_DTL.CREATION_DATE
     *
     * @return the value of WOS_ORG_TEMPLATE_DTL.CREATION_DATE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORG_TEMPLATE_DTL.CREATION_DATE
     *
     * @param creationDate the value for WOS_ORG_TEMPLATE_DTL.CREATION_DATE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORG_TEMPLATE_DTL.LAST_UPDATED_BY
     *
     * @return the value of WOS_ORG_TEMPLATE_DTL.LAST_UPDATED_BY
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORG_TEMPLATE_DTL.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for WOS_ORG_TEMPLATE_DTL.LAST_UPDATED_BY
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORG_TEMPLATE_DTL.LAST_UPDATE_DATE
     *
     * @return the value of WOS_ORG_TEMPLATE_DTL.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORG_TEMPLATE_DTL.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for WOS_ORG_TEMPLATE_DTL.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}