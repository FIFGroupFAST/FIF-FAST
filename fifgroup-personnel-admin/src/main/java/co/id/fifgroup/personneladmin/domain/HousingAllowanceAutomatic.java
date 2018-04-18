package co.id.fifgroup.personneladmin.domain;
/* add by PMW, ticket 15111616041796, 15-Feb-2016 */
import java.util.Date;

public class HousingAllowanceAutomatic {
	
	private static final long serialVersionUID = 1L;
	
		private Long housingAllowanceId;

	
	    private Long personId;

	
	    private Long companyId;


	    private Date effectiveStartDate;

	
	    private Date effectiveEndDate;

	
	    private String houseAllowance;

	  
	    private Long createdBy;

	
	    private Date creationDate;

	  
	    private Long lastUpdatedBy;

	  
	    private Date lastUpdateDate;

	   
	    private Boolean isLumpsumRequested;


		public Long getHousingAllowanceId() {
			return housingAllowanceId;
		}


		public void setHousingAllowanceId(Long housingAllowanceId) {
			this.housingAllowanceId = housingAllowanceId;
		}


		public Long getPersonId() {
			return personId;
		}


		public void setPersonId(Long personId) {
			this.personId = personId;
		}


		public Long getCompanyId() {
			return companyId;
		}


		public void setCompanyId(Long companyId) {
			this.companyId = companyId;
		}


		public Date getEffectiveStartDate() {
			return effectiveStartDate;
		}


		public void setEffectiveStartDate(Date effectiveStartDate) {
			this.effectiveStartDate = effectiveStartDate;
		}


		public Date getEffectiveEndDate() {
			return effectiveEndDate;
		}


		public void setEffectiveEndDate(Date effectiveEndDate) {
			this.effectiveEndDate = effectiveEndDate;
		}


		public String getHouseAllowance() {
			return houseAllowance;
		}


		public void setHouseAllowance(String houseAllowance) {
			this.houseAllowance = houseAllowance;
		}


		public Long getCreatedBy() {
			return createdBy;
		}


		public void setCreatedBy(Long createdBy) {
			this.createdBy = createdBy;
		}


		public Date getCreationDate() {
			return creationDate;
		}


		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}


		public Long getLastUpdatedBy() {
			return lastUpdatedBy;
		}


		public void setLastUpdatedBy(Long lastUpdatedBy) {
			this.lastUpdatedBy = lastUpdatedBy;
		}


		public Date getLastUpdateDate() {
			return lastUpdateDate;
		}


		public void setLastUpdateDate(Date lastUpdateDate) {
			this.lastUpdateDate = lastUpdateDate;
		}


		public Boolean getIsLumpsumRequested() {
			return isLumpsumRequested;
		}


		public void setIsLumpsumRequested(Boolean isLumpsumRequested) {
			this.isLumpsumRequested = isLumpsumRequested;
		}

}
