package co.id.fifgroup.personneladmin.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import co.id.fifgroup.core.audit.Traversable;

public class Education implements Serializable, Traversable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.EDUCATION_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Long educationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.PERSON_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Long personId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.COMPANY_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Long companyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.START_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Date startDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.END_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Date endDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.EDUCATION_LEVEL
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private String educationLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.INSTITUTION
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private String institution;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.FACULTY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private String faculty;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.MAJOR
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private String major;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.GPA
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private BigDecimal gpa;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.IS_GRADUATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private boolean graduate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.CREATED_BY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.CREATION_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.LAST_UPDATED_BY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_EDUCATIONS.LAST_UPDATE_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_EDUCATIONS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.EDUCATION_ID
     *
     * @return the value of PEA_EDUCATIONS.EDUCATION_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Long getEducationId() {
        return educationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.EDUCATION_ID
     *
     * @param educationId the value for PEA_EDUCATIONS.EDUCATION_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setEducationId(Long educationId) {
        this.educationId = educationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.PERSON_ID
     *
     * @return the value of PEA_EDUCATIONS.PERSON_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Long getPersonId() {
        return personId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.PERSON_ID
     *
     * @param personId the value for PEA_EDUCATIONS.PERSON_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.COMPANY_ID
     *
     * @return the value of PEA_EDUCATIONS.COMPANY_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.COMPANY_ID
     *
     * @param companyId the value for PEA_EDUCATIONS.COMPANY_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.START_DATE
     *
     * @return the value of PEA_EDUCATIONS.START_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.START_DATE
     *
     * @param startDate the value for PEA_EDUCATIONS.START_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.END_DATE
     *
     * @return the value of PEA_EDUCATIONS.END_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.END_DATE
     *
     * @param endDate the value for PEA_EDUCATIONS.END_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.EDUCATION_LEVEL
     *
     * @return the value of PEA_EDUCATIONS.EDUCATION_LEVEL
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public String getEducationLevel() {
        return educationLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.EDUCATION_LEVEL
     *
     * @param educationLevel the value for PEA_EDUCATIONS.EDUCATION_LEVEL
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel == null ? null : educationLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.INSTITUTION
     *
     * @return the value of PEA_EDUCATIONS.INSTITUTION
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.INSTITUTION
     *
     * @param institution the value for PEA_EDUCATIONS.INSTITUTION
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setInstitution(String institution) {
        this.institution = institution == null ? null : institution.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.FACULTY
     *
     * @return the value of PEA_EDUCATIONS.FACULTY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.FACULTY
     *
     * @param faculty the value for PEA_EDUCATIONS.FACULTY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty == null ? null : faculty.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.MAJOR
     *
     * @return the value of PEA_EDUCATIONS.MAJOR
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public String getMajor() {
        return major;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.MAJOR
     *
     * @param major the value for PEA_EDUCATIONS.MAJOR
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.GPA
     *
     * @return the value of PEA_EDUCATIONS.GPA
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public BigDecimal getGpa() {
        return gpa;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.GPA
     *
     * @param gpa the value for PEA_EDUCATIONS.GPA
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setGpa(BigDecimal gpa) {
        this.gpa = gpa;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.IS_GRADUATE
     *
     * @return the value of PEA_EDUCATIONS.IS_GRADUATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public boolean isGraduate() {
        return graduate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.IS_GRADUATE
     *
     * @param graduate the value for PEA_EDUCATIONS.IS_GRADUATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setGraduate(boolean graduate) {
        this.graduate = graduate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.CREATED_BY
     *
     * @return the value of PEA_EDUCATIONS.CREATED_BY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.CREATED_BY
     *
     * @param createdBy the value for PEA_EDUCATIONS.CREATED_BY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.CREATION_DATE
     *
     * @return the value of PEA_EDUCATIONS.CREATION_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.CREATION_DATE
     *
     * @param creationDate the value for PEA_EDUCATIONS.CREATION_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.LAST_UPDATED_BY
     *
     * @return the value of PEA_EDUCATIONS.LAST_UPDATED_BY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for PEA_EDUCATIONS.LAST_UPDATED_BY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_EDUCATIONS.LAST_UPDATE_DATE
     *
     * @return the value of PEA_EDUCATIONS.LAST_UPDATE_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_EDUCATIONS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for PEA_EDUCATIONS.LAST_UPDATE_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

	@Override
	public Object getId() {
		return getEducationId();
	}
}