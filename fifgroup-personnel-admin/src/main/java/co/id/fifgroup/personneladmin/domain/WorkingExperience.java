package co.id.fifgroup.personneladmin.domain;

import java.io.Serializable;
import java.util.Date;

public class WorkingExperience implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.EXPERIENCE_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Long experienceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.PERSON_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Long personId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.COMPANY_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Long companyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.START_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Date startDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.END_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Date endDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.COMPANY_NAME
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private String companyName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.POSITION
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private String position;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.REFERENCE_NAME
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private String referenceName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.REFERENCE_CONTACT
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private String referenceContact;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.REFERENCE_POSITION
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private String referencePosition;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.CREATED_BY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.CREATION_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.LAST_UPDATED_BY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEA_WORKING_EXPERIENCES.LAST_UPDATE_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_WORKING_EXPERIENCES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.EXPERIENCE_ID
     *
     * @return the value of PEA_WORKING_EXPERIENCES.EXPERIENCE_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Long getExperienceId() {
        return experienceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.EXPERIENCE_ID
     *
     * @param experienceId the value for PEA_WORKING_EXPERIENCES.EXPERIENCE_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.PERSON_ID
     *
     * @return the value of PEA_WORKING_EXPERIENCES.PERSON_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Long getPersonId() {
        return personId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.PERSON_ID
     *
     * @param personId the value for PEA_WORKING_EXPERIENCES.PERSON_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.COMPANY_ID
     *
     * @return the value of PEA_WORKING_EXPERIENCES.COMPANY_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.COMPANY_ID
     *
     * @param companyId the value for PEA_WORKING_EXPERIENCES.COMPANY_ID
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.START_DATE
     *
     * @return the value of PEA_WORKING_EXPERIENCES.START_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.START_DATE
     *
     * @param startDate the value for PEA_WORKING_EXPERIENCES.START_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.END_DATE
     *
     * @return the value of PEA_WORKING_EXPERIENCES.END_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.END_DATE
     *
     * @param endDate the value for PEA_WORKING_EXPERIENCES.END_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.COMPANY_NAME
     *
     * @return the value of PEA_WORKING_EXPERIENCES.COMPANY_NAME
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.COMPANY_NAME
     *
     * @param companyName the value for PEA_WORKING_EXPERIENCES.COMPANY_NAME
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.POSITION
     *
     * @return the value of PEA_WORKING_EXPERIENCES.POSITION
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public String getPosition() {
        return position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.POSITION
     *
     * @param position the value for PEA_WORKING_EXPERIENCES.POSITION
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.REFERENCE_NAME
     *
     * @return the value of PEA_WORKING_EXPERIENCES.REFERENCE_NAME
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public String getReferenceName() {
        return referenceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.REFERENCE_NAME
     *
     * @param referenceName the value for PEA_WORKING_EXPERIENCES.REFERENCE_NAME
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName == null ? null : referenceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.REFERENCE_CONTACT
     *
     * @return the value of PEA_WORKING_EXPERIENCES.REFERENCE_CONTACT
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public String getReferenceContact() {
        return referenceContact;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.REFERENCE_CONTACT
     *
     * @param referenceContact the value for PEA_WORKING_EXPERIENCES.REFERENCE_CONTACT
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setReferenceContact(String referenceContact) {
        this.referenceContact = referenceContact == null ? null : referenceContact.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.REFERENCE_POSITION
     *
     * @return the value of PEA_WORKING_EXPERIENCES.REFERENCE_POSITION
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public String getReferencePosition() {
        return referencePosition;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.REFERENCE_POSITION
     *
     * @param referencePosition the value for PEA_WORKING_EXPERIENCES.REFERENCE_POSITION
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setReferencePosition(String referencePosition) {
        this.referencePosition = referencePosition == null ? null : referencePosition.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.CREATED_BY
     *
     * @return the value of PEA_WORKING_EXPERIENCES.CREATED_BY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.CREATED_BY
     *
     * @param createdBy the value for PEA_WORKING_EXPERIENCES.CREATED_BY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.CREATION_DATE
     *
     * @return the value of PEA_WORKING_EXPERIENCES.CREATION_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.CREATION_DATE
     *
     * @param creationDate the value for PEA_WORKING_EXPERIENCES.CREATION_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.LAST_UPDATED_BY
     *
     * @return the value of PEA_WORKING_EXPERIENCES.LAST_UPDATED_BY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for PEA_WORKING_EXPERIENCES.LAST_UPDATED_BY
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEA_WORKING_EXPERIENCES.LAST_UPDATE_DATE
     *
     * @return the value of PEA_WORKING_EXPERIENCES.LAST_UPDATE_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEA_WORKING_EXPERIENCES.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for PEA_WORKING_EXPERIENCES.LAST_UPDATE_DATE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}