package co.id.fifgroup.eligibility.domain;

import java.io.Serializable;
import java.util.Date;

import co.id.fifgroup.eligibility.constant.ColumnType;
import co.id.fifgroup.eligibility.constant.Operator;
import co.id.fifgroup.eligibility.constant.ValueType;

public class DecisionTableColumn implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.COLUMN_ID
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.MODEL_ID
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private String modelId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.VERSION_NUMBER
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private Integer versionNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.COLUMN_TYPE
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private ColumnType columnType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.COLUMN_NAME
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.FACT_TYPE_ID
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private String factTypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.FIELD_ID
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private String fieldId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.OPERATOR
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private Operator operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.VALUE_TYPE
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private ValueType valueType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.VALUE_LIST
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private String valueList;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.CREATED_BY
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.CREATION_DATE
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.LAST_UPDATED_BY
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ELR_DEC_TBL_COLUMNS.LAST_UPDATE_DATE
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ELR_DEC_TBL_COLUMNS
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.COLUMN_ID
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.COLUMN_ID
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.COLUMN_ID
     *
     * @param id the value for ELR_DEC_TBL_COLUMNS.COLUMN_ID
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.MODEL_ID
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.MODEL_ID
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public String getModelId() {
        return modelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.MODEL_ID
     *
     * @param modelId the value for ELR_DEC_TBL_COLUMNS.MODEL_ID
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.VERSION_NUMBER
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.VERSION_NUMBER
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public Integer getVersionNumber() {
        return versionNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.VERSION_NUMBER
     *
     * @param versionNumber the value for ELR_DEC_TBL_COLUMNS.VERSION_NUMBER
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.COLUMN_TYPE
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.COLUMN_TYPE
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public ColumnType getColumnType() {
        return columnType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.COLUMN_TYPE
     *
     * @param columnType the value for ELR_DEC_TBL_COLUMNS.COLUMN_TYPE
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.COLUMN_NAME
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.COLUMN_NAME
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.COLUMN_NAME
     *
     * @param name the value for ELR_DEC_TBL_COLUMNS.COLUMN_NAME
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.FACT_TYPE_ID
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.FACT_TYPE_ID
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public String getFactTypeId() {
        return factTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.FACT_TYPE_ID
     *
     * @param factTypeId the value for ELR_DEC_TBL_COLUMNS.FACT_TYPE_ID
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setFactTypeId(String factTypeId) {
        this.factTypeId = factTypeId == null ? null : factTypeId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.FIELD_ID
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.FIELD_ID
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public String getFieldId() {
        return fieldId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.FIELD_ID
     *
     * @param fieldId the value for ELR_DEC_TBL_COLUMNS.FIELD_ID
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId == null ? null : fieldId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.OPERATOR
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.OPERATOR
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.OPERATOR
     *
     * @param operator the value for ELR_DEC_TBL_COLUMNS.OPERATOR
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.VALUE_TYPE
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.VALUE_TYPE
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public ValueType getValueType() {
        return valueType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.VALUE_TYPE
     *
     * @param valueType the value for ELR_DEC_TBL_COLUMNS.VALUE_TYPE
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.VALUE_LIST
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.VALUE_LIST
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public String getValueList() {
        return valueList;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.VALUE_LIST
     *
     * @param valueList the value for ELR_DEC_TBL_COLUMNS.VALUE_LIST
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setValueList(String valueList) {
        this.valueList = valueList == null ? null : valueList.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.CREATED_BY
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.CREATED_BY
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.CREATED_BY
     *
     * @param createdBy the value for ELR_DEC_TBL_COLUMNS.CREATED_BY
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.CREATION_DATE
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.CREATION_DATE
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.CREATION_DATE
     *
     * @param creationDate the value for ELR_DEC_TBL_COLUMNS.CREATION_DATE
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.LAST_UPDATED_BY
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.LAST_UPDATED_BY
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for ELR_DEC_TBL_COLUMNS.LAST_UPDATED_BY
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ELR_DEC_TBL_COLUMNS.LAST_UPDATE_DATE
     *
     * @return the value of ELR_DEC_TBL_COLUMNS.LAST_UPDATE_DATE
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ELR_DEC_TBL_COLUMNS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for ELR_DEC_TBL_COLUMNS.LAST_UPDATE_DATE
     *
     * @mbggenerated Fri Sep 13 15:57:27 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}