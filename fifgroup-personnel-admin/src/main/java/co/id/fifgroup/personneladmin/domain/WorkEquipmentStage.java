package co.id.fifgroup.personneladmin.domain;

import java.io.Serializable;
import java.util.Date;

public class WorkEquipmentStage implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORK_EQUIPMENT_STG.UPLOAD_ID
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    private Long uploadId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORK_EQUIPMENT_STG.EMPLOYEE_NUMBER
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    private String employeeNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORK_EQUIPMENT_STG.EQUIPMENT_TYPE
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    private Long equipmentType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORK_EQUIPMENT_STG.DATE_ASSIGNED
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    private Date dateAssigned;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORK_EQUIPMENT_STG.RETURN_DATE
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    private Date returnDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORK_EQUIPMENT_STG.ASSET_NUMBER
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    private String assetNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORK_EQUIPMENT_STG.REASON
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    private String reason;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORK_EQUIPMENT_STG.ROW_NUMBER
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    private Long rowNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORK_EQUIPMENT_STG.RAW_DATA
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    private String rawData;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORK_EQUIPMENT_STG.IS_CLOSED
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    private boolean isClosed;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_WORK_EQUIPMENT_STG
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORK_EQUIPMENT_STG.UPLOAD_ID
     *
     * @return the value of PEA_WORK_EQUIPMENT_STG.UPLOAD_ID
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public Long getUploadId() {
        return uploadId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORK_EQUIPMENT_STG.UPLOAD_ID
     *
     * @param uploadId the value for PEA_WORK_EQUIPMENT_STG.UPLOAD_ID
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORK_EQUIPMENT_STG.EMPLOYEE_NUMBER
     *
     * @return the value of PEA_WORK_EQUIPMENT_STG.EMPLOYEE_NUMBER
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORK_EQUIPMENT_STG.EMPLOYEE_NUMBER
     *
     * @param employeeNumber the value for PEA_WORK_EQUIPMENT_STG.EMPLOYEE_NUMBER
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber == null ? null : employeeNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORK_EQUIPMENT_STG.EQUIPMENT_TYPE
     *
     * @return the value of PEA_WORK_EQUIPMENT_STG.EQUIPMENT_TYPE
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public Long getEquipmentType() {
        return equipmentType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORK_EQUIPMENT_STG.EQUIPMENT_TYPE
     *
     * @param equipmentType the value for PEA_WORK_EQUIPMENT_STG.EQUIPMENT_TYPE
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public void setEquipmentType(Long equipmentType) {
        this.equipmentType = equipmentType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORK_EQUIPMENT_STG.DATE_ASSIGNED
     *
     * @return the value of PEA_WORK_EQUIPMENT_STG.DATE_ASSIGNED
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public Date getDateAssigned() {
        return dateAssigned;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORK_EQUIPMENT_STG.DATE_ASSIGNED
     *
     * @param dateAssigned the value for PEA_WORK_EQUIPMENT_STG.DATE_ASSIGNED
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public void setDateAssigned(Date dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORK_EQUIPMENT_STG.RETURN_DATE
     *
     * @return the value of PEA_WORK_EQUIPMENT_STG.RETURN_DATE
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORK_EQUIPMENT_STG.RETURN_DATE
     *
     * @param returnDate the value for PEA_WORK_EQUIPMENT_STG.RETURN_DATE
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORK_EQUIPMENT_STG.ASSET_NUMBER
     *
     * @return the value of PEA_WORK_EQUIPMENT_STG.ASSET_NUMBER
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public String getAssetNumber() {
        return assetNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORK_EQUIPMENT_STG.ASSET_NUMBER
     *
     * @param assetNumber the value for PEA_WORK_EQUIPMENT_STG.ASSET_NUMBER
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber == null ? null : assetNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORK_EQUIPMENT_STG.REASON
     *
     * @return the value of PEA_WORK_EQUIPMENT_STG.REASON
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public String getReason() {
        return reason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORK_EQUIPMENT_STG.REASON
     *
     * @param reason the value for PEA_WORK_EQUIPMENT_STG.REASON
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORK_EQUIPMENT_STG.ROW_NUMBER
     *
     * @return the value of PEA_WORK_EQUIPMENT_STG.ROW_NUMBER
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public Long getRowNumber() {
        return rowNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORK_EQUIPMENT_STG.ROW_NUMBER
     *
     * @param rowNumber the value for PEA_WORK_EQUIPMENT_STG.ROW_NUMBER
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORK_EQUIPMENT_STG.RAW_DATA
     *
     * @return the value of PEA_WORK_EQUIPMENT_STG.RAW_DATA
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORK_EQUIPMENT_STG.RAW_DATA
     *
     * @param rawData the value for PEA_WORK_EQUIPMENT_STG.RAW_DATA
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public void setRawData(String rawData) {
        this.rawData = rawData == null ? null : rawData.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORK_EQUIPMENT_STG.IS_CLOSED
     *
     * @return the value of PEA_WORK_EQUIPMENT_STG.IS_CLOSED
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public boolean isIsClosed() {
        return isClosed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORK_EQUIPMENT_STG.IS_CLOSED
     *
     * @param isClosed the value for PEA_WORK_EQUIPMENT_STG.IS_CLOSED
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }
}