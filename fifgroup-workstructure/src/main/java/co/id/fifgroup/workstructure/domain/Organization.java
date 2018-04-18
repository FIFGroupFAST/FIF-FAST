package co.id.fifgroup.workstructure.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Organization implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATIONS.ORGANIZATION_ID
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATIONS.COMPANY_ID
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    private Long companyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATIONS.ORGANIZATION_CODE
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    private String organizationCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATIONS.ORGANIZATION_NAME
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    private String organizationName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATIONS.CREATED_BY
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATIONS.CREATION_DATE
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATIONS.LAST_UPDATED_BY
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATIONS.LAST_UPDATE_DATE
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_ORGANIZATIONS.ORGANIZATION_UUID
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    private UUID OrganizationUuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_ORGANIZATIONS
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATIONS.ORGANIZATION_ID
     *
     * @return the value of WOS_ORGANIZATIONS.ORGANIZATION_ID
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATIONS.ORGANIZATION_ID
     *
     * @param id the value for WOS_ORGANIZATIONS.ORGANIZATION_ID
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATIONS.COMPANY_ID
     *
     * @return the value of WOS_ORGANIZATIONS.COMPANY_ID
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATIONS.COMPANY_ID
     *
     * @param companyId the value for WOS_ORGANIZATIONS.COMPANY_ID
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATIONS.ORGANIZATION_CODE
     *
     * @return the value of WOS_ORGANIZATIONS.ORGANIZATION_CODE
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATIONS.ORGANIZATION_CODE
     *
     * @param organizationCode the value for WOS_ORGANIZATIONS.ORGANIZATION_CODE
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode == null ? null : organizationCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATIONS.ORGANIZATION_NAME
     *
     * @return the value of WOS_ORGANIZATIONS.ORGANIZATION_NAME
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATIONS.ORGANIZATION_NAME
     *
     * @param organizationName the value for WOS_ORGANIZATIONS.ORGANIZATION_NAME
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName == null ? null : organizationName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATIONS.CREATED_BY
     *
     * @return the value of WOS_ORGANIZATIONS.CREATED_BY
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATIONS.CREATED_BY
     *
     * @param createdBy the value for WOS_ORGANIZATIONS.CREATED_BY
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATIONS.CREATION_DATE
     *
     * @return the value of WOS_ORGANIZATIONS.CREATION_DATE
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATIONS.CREATION_DATE
     *
     * @param creationDate the value for WOS_ORGANIZATIONS.CREATION_DATE
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATIONS.LAST_UPDATED_BY
     *
     * @return the value of WOS_ORGANIZATIONS.LAST_UPDATED_BY
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATIONS.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for WOS_ORGANIZATIONS.LAST_UPDATED_BY
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATIONS.LAST_UPDATE_DATE
     *
     * @return the value of WOS_ORGANIZATIONS.LAST_UPDATE_DATE
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATIONS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for WOS_ORGANIZATIONS.LAST_UPDATE_DATE
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_ORGANIZATIONS.ORGANIZATION_UUID
     *
     * @return the value of WOS_ORGANIZATIONS.ORGANIZATION_UUID
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public UUID getOrganizationUuid() {
        return OrganizationUuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_ORGANIZATIONS.ORGANIZATION_UUID
     *
     * @param OrganizationUuid the value for WOS_ORGANIZATIONS.ORGANIZATION_UUID
     *
     * @mbggenerated Fri Apr 26 15:12:14 ICT 2013
     */
    public void setOrganizationUuid(UUID OrganizationUuid) {
        this.OrganizationUuid = OrganizationUuid;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Organization [id=" + id + ", companyId=" + companyId
				+ ", organizationCode=" + organizationCode
				+ ", organizationName=" + organizationName + ", createdBy="
				+ createdBy + ", creationDate=" + creationDate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdateDate="
				+ lastUpdateDate + ", OrganizationUuid=" + OrganizationUuid
				+ "]";
	}
    
    
}