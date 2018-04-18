package co.id.fifgroup.personneladmin.domain;

import java.io.Serializable;
import java.util.Date;

public class AssignmentStage implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_ASSIGNMENT_STG.UPLOAD_ID
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    private Long uploadId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_ASSIGNMENT_STG.EMPLOYEE_NUMBER
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    private String employeeNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_ASSIGNMENT_STG.ORGANIZATION_CODE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    private String organizationCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_ASSIGNMENT_STG.JOB_CODE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    private String jobCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_ASSIGNMENT_STG.GRADE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    private String grade;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_ASSIGNMENT_STG.EFFECTIVE_START_DATE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    private Date effectiveStartDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_ASSIGNMENT_STG.TYPE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_ASSIGNMENT_STG.ROW_NUMBER
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    private Long rowNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_ASSIGNMENT_STG.RAW_DATA
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    private String rawData;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_ASSIGNMENT_STG.IS_CLOSED
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    private boolean isClosed;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_ASSIGNMENT_STG.SUB_GRADE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    private String subGrade;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_ASSIGNMENT_STG
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_ASSIGNMENT_STG.UPLOAD_ID
     *
     * @return the value of PEA_ASSIGNMENT_STG.UPLOAD_ID
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public Long getUploadId() {
        return uploadId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_ASSIGNMENT_STG.UPLOAD_ID
     *
     * @param uploadId the value for PEA_ASSIGNMENT_STG.UPLOAD_ID
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_ASSIGNMENT_STG.EMPLOYEE_NUMBER
     *
     * @return the value of PEA_ASSIGNMENT_STG.EMPLOYEE_NUMBER
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_ASSIGNMENT_STG.EMPLOYEE_NUMBER
     *
     * @param employeeNumber the value for PEA_ASSIGNMENT_STG.EMPLOYEE_NUMBER
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber == null ? null : employeeNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_ASSIGNMENT_STG.ORGANIZATION_CODE
     *
     * @return the value of PEA_ASSIGNMENT_STG.ORGANIZATION_CODE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_ASSIGNMENT_STG.ORGANIZATION_CODE
     *
     * @param organizationCode the value for PEA_ASSIGNMENT_STG.ORGANIZATION_CODE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode == null ? null : organizationCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_ASSIGNMENT_STG.JOB_CODE
     *
     * @return the value of PEA_ASSIGNMENT_STG.JOB_CODE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public String getJobCode() {
        return jobCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_ASSIGNMENT_STG.JOB_CODE
     *
     * @param jobCode the value for PEA_ASSIGNMENT_STG.JOB_CODE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode == null ? null : jobCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_ASSIGNMENT_STG.GRADE
     *
     * @return the value of PEA_ASSIGNMENT_STG.GRADE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public String getGrade() {
        return grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_ASSIGNMENT_STG.GRADE
     *
     * @param grade the value for PEA_ASSIGNMENT_STG.GRADE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_ASSIGNMENT_STG.EFFECTIVE_START_DATE
     *
     * @return the value of PEA_ASSIGNMENT_STG.EFFECTIVE_START_DATE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_ASSIGNMENT_STG.EFFECTIVE_START_DATE
     *
     * @param effectiveStartDate the value for PEA_ASSIGNMENT_STG.EFFECTIVE_START_DATE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_ASSIGNMENT_STG.TYPE
     *
     * @return the value of PEA_ASSIGNMENT_STG.TYPE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_ASSIGNMENT_STG.TYPE
     *
     * @param type the value for PEA_ASSIGNMENT_STG.TYPE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_ASSIGNMENT_STG.ROW_NUMBER
     *
     * @return the value of PEA_ASSIGNMENT_STG.ROW_NUMBER
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public Long getRowNumber() {
        return rowNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_ASSIGNMENT_STG.ROW_NUMBER
     *
     * @param rowNumber the value for PEA_ASSIGNMENT_STG.ROW_NUMBER
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_ASSIGNMENT_STG.RAW_DATA
     *
     * @return the value of PEA_ASSIGNMENT_STG.RAW_DATA
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_ASSIGNMENT_STG.RAW_DATA
     *
     * @param rawData the value for PEA_ASSIGNMENT_STG.RAW_DATA
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public void setRawData(String rawData) {
        this.rawData = rawData == null ? null : rawData.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_ASSIGNMENT_STG.IS_CLOSED
     *
     * @return the value of PEA_ASSIGNMENT_STG.IS_CLOSED
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public boolean isIsClosed() {
        return isClosed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_ASSIGNMENT_STG.IS_CLOSED
     *
     * @param isClosed the value for PEA_ASSIGNMENT_STG.IS_CLOSED
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_ASSIGNMENT_STG.SUB_GRADE
     *
     * @return the value of PEA_ASSIGNMENT_STG.SUB_GRADE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public String getSubGrade() {
        return subGrade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_ASSIGNMENT_STG.SUB_GRADE
     *
     * @param subGrade the value for PEA_ASSIGNMENT_STG.SUB_GRADE
     *
     * @mbggenerated Tue Jul 09 11:43:29 ICT 2013
     */
    public void setSubGrade(String subGrade) {
        this.subGrade = subGrade == null ? null : subGrade.trim();
    }
}