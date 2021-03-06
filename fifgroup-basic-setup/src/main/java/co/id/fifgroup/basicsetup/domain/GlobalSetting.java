package co.id.fifgroup.basicsetup.domain;

import java.io.Serializable;
import java.util.Date;

public class GlobalSetting implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_GLOBAL_SETTINGS.GLOBAL_SETTING_ID
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    private Long globalSettingId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_GLOBAL_SETTINGS.CODE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_GLOBAL_SETTINGS.DESCRIPTION
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_GLOBAL_SETTINGS.DATA_TYPE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    private String dataType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_GLOBAL_SETTINGS.VALUE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    private String value;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_GLOBAL_SETTINGS.CREATED_BY
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_GLOBAL_SETTINGS.CREATION_DATE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_GLOBAL_SETTINGS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BSE_GLOBAL_SETTINGS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_GLOBAL_SETTINGS.GLOBAL_SETTING_ID
     *
     * @return the value of BSE_GLOBAL_SETTINGS.GLOBAL_SETTING_ID
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public Long getGlobalSettingId() {
        return globalSettingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_GLOBAL_SETTINGS.GLOBAL_SETTING_ID
     *
     * @param globalSettingId the value for BSE_GLOBAL_SETTINGS.GLOBAL_SETTING_ID
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public void setGlobalSettingId(Long globalSettingId) {
        this.globalSettingId = globalSettingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_GLOBAL_SETTINGS.CODE
     *
     * @return the value of BSE_GLOBAL_SETTINGS.CODE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_GLOBAL_SETTINGS.CODE
     *
     * @param code the value for BSE_GLOBAL_SETTINGS.CODE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_GLOBAL_SETTINGS.DESCRIPTION
     *
     * @return the value of BSE_GLOBAL_SETTINGS.DESCRIPTION
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_GLOBAL_SETTINGS.DESCRIPTION
     *
     * @param description the value for BSE_GLOBAL_SETTINGS.DESCRIPTION
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_GLOBAL_SETTINGS.DATA_TYPE
     *
     * @return the value of BSE_GLOBAL_SETTINGS.DATA_TYPE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_GLOBAL_SETTINGS.DATA_TYPE
     *
     * @param dataType the value for BSE_GLOBAL_SETTINGS.DATA_TYPE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_GLOBAL_SETTINGS.VALUE
     *
     * @return the value of BSE_GLOBAL_SETTINGS.VALUE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_GLOBAL_SETTINGS.VALUE
     *
     * @param value the value for BSE_GLOBAL_SETTINGS.VALUE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_GLOBAL_SETTINGS.CREATED_BY
     *
     * @return the value of BSE_GLOBAL_SETTINGS.CREATED_BY
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_GLOBAL_SETTINGS.CREATED_BY
     *
     * @param createdBy the value for BSE_GLOBAL_SETTINGS.CREATED_BY
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_GLOBAL_SETTINGS.CREATION_DATE
     *
     * @return the value of BSE_GLOBAL_SETTINGS.CREATION_DATE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_GLOBAL_SETTINGS.CREATION_DATE
     *
     * @param creationDate the value for BSE_GLOBAL_SETTINGS.CREATION_DATE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_GLOBAL_SETTINGS.LAST_UPDATED_BY
     *
     * @return the value of BSE_GLOBAL_SETTINGS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_GLOBAL_SETTINGS.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for BSE_GLOBAL_SETTINGS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BSE_GLOBAL_SETTINGS.LAST_UPDATE_DATE
     *
     * @return the value of BSE_GLOBAL_SETTINGS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BSE_GLOBAL_SETTINGS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for BSE_GLOBAL_SETTINGS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}