package co.id.fifgroup.core.domain;
/* add by PMW, ticket 15111616041796, 15-Feb-2016 */
import java.util.Date;

public class FlagBoardingAutomatic {
private static final long serialVersionUID = 1L;
	
	private int boardingVersionId;	
	private int boardingParameterId;	
	private int versionNumber;		
	private Date dateFrom;			
	private Date dateTo;				
	private String assignmentStatus;	
	private String employeeCategory;	
	private String transferBy;			
	private int isKeyJob;			
	private String flagHousing;			
	private String updateFlagHousing;	
	private int isPlacmentStatus;	
	private Long matrixLocationId;	
	private Long companyId;			
	private Long createdBy;
	private Long personId;
	private String locationName;

	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getOriginLocation() {
		return originLocation;
	}
	public void setOriginLocation(Long originLocation) {
		this.originLocation = originLocation;
	}
	public Long getDestinationLocation() {
		return destinationLocation;
	}
	public void setDestinationLocation(Long destinationLocation) {
		this.destinationLocation = destinationLocation;
	}
	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}
	private Date createdDate;			
	private Long lastUpdateBy;		
	private Date lastUpdateDate;
	private Long originLocation;
	private Long destinationLocation;
	
	public int getBoardingVersionId() {
		return boardingVersionId;
	}
	public void setBoardingVersionId(int boardingVersionId) {
		this.boardingVersionId = boardingVersionId;
	}
	public int getBoardingParameterId() {
		return boardingParameterId;
	}
	public void setBoardingParameterId(int boardingParameterId) {
		this.boardingParameterId = boardingParameterId;
	}
	public int getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getAssignmentStatus() {
		return assignmentStatus;
	}
	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}
	public String getEmployeeCategory() {
		return employeeCategory;
	}
	public void setEmployeeCategory(String employeeCategory) {
		this.employeeCategory = employeeCategory;
	}
	public String getTransferBy() {
		return transferBy;
	}
	public void setTransferBy(String transferBy) {
		this.transferBy = transferBy;
	}
	public int getIsKeyJob() {
		return isKeyJob;
	}
	public void setIsKeyJob(int isKeyJob) {
		this.isKeyJob = isKeyJob;
	}
	public String getFlagHousing() {
		return flagHousing;
	}
	public void setFlagHousing(String flagHousing) {
		this.flagHousing = flagHousing;
	}
	public String getUpdateFlagHousing() {
		return updateFlagHousing;
	}
	public void setUpdateFlagHousing(String updateFlagHousing) {
		this.updateFlagHousing = updateFlagHousing;
	}
	public int getIsPlacmentStatus() {
		return isPlacmentStatus;
	}
	public void setIsPlacmentStatus(int isPlacmentStatus) {
		this.isPlacmentStatus = isPlacmentStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Long getMatrixLocationId() {
		return matrixLocationId;
	}
	public void setMatrixLocationId(Long matrixLocationId) {
		this.matrixLocationId = matrixLocationId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}	
}
