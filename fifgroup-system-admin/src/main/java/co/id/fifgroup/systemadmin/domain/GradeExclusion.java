package co.id.fifgroup.systemadmin.domain;

import java.io.Serializable;

public class GradeExclusion implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_GRADE_EXCLUSIONS.GRADE_EXCLUSION_ID
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_GRADE_EXCLUSIONS.SECURITY_COY_TYPE
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    private String securityCompanyType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_GRADE_EXCLUSIONS.RESP_COMPANY_ID
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    private Long respCompanyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_GRADE_EXCLUSIONS.GRADE_ID
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    private Long gradeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_GRADE_EXCLUSIONS
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_GRADE_EXCLUSIONS.GRADE_EXCLUSION_ID
     *
     * @return the value of SAM_GRADE_EXCLUSIONS.GRADE_EXCLUSION_ID
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_GRADE_EXCLUSIONS.GRADE_EXCLUSION_ID
     *
     * @param id the value for SAM_GRADE_EXCLUSIONS.GRADE_EXCLUSION_ID
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_GRADE_EXCLUSIONS.SECURITY_COY_TYPE
     *
     * @return the value of SAM_GRADE_EXCLUSIONS.SECURITY_COY_TYPE
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public String getSecurityCompanyType() {
        return securityCompanyType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_GRADE_EXCLUSIONS.SECURITY_COY_TYPE
     *
     * @param securityCompanyType the value for SAM_GRADE_EXCLUSIONS.SECURITY_COY_TYPE
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public void setSecurityCompanyType(String securityCompanyType) {
        this.securityCompanyType = securityCompanyType == null ? null : securityCompanyType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_GRADE_EXCLUSIONS.RESP_COMPANY_ID
     *
     * @return the value of SAM_GRADE_EXCLUSIONS.RESP_COMPANY_ID
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public Long getRespCompanyId() {
        return respCompanyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_GRADE_EXCLUSIONS.RESP_COMPANY_ID
     *
     * @param respCompanyId the value for SAM_GRADE_EXCLUSIONS.RESP_COMPANY_ID
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public void setRespCompanyId(Long respCompanyId) {
        this.respCompanyId = respCompanyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_GRADE_EXCLUSIONS.GRADE_ID
     *
     * @return the value of SAM_GRADE_EXCLUSIONS.GRADE_ID
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public Long getGradeId() {
        return gradeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_GRADE_EXCLUSIONS.GRADE_ID
     *
     * @param gradeId the value for SAM_GRADE_EXCLUSIONS.GRADE_ID
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }
}