package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.util.Date;

public class OcopMovementHeader implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.MOVEMENT_HEADER_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Long movementHeaderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.MOVEMENT_TYPE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private String movementType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.ORGANIZATION_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Long organizationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.RENTAL_TYPE_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Long rentalTypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.SOURCE_LOCATION_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Long sourceLocationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.TARGET_LOCATION_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Long targetLocationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.COMMENTS
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private String comments;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.REQUESTOR
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Long requestor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.REQUEST_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Date requestDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.AUTHORIZATION_STATUS
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private String authorizationStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.APPROVED_FLAG
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private String approvedFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.APPROVED_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Date approvedDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.RECEIVED_ALL_FLAG
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private String receivedAllFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.LAST_RECEIVED_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Date lastReceivedDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.CANCEL_FLAG
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private String cancelFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.CANCEL_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Date cancelDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.CLOSED_CODE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private String closedCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.CLOSED_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Date closedDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.CREATED_BY
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.CREATION_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_BY
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Long lastUpdateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_LOGIN
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private Long lastUpdateLogin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIF_RS_MOVEMENT_HEADERS
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.MOVEMENT_HEADER_ID
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.MOVEMENT_HEADER_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Long getMovementHeaderId() {
        return movementHeaderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.MOVEMENT_HEADER_ID
     *
     * @param movementHeaderId the value for FIF_RS_MOVEMENT_HEADERS.MOVEMENT_HEADER_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setMovementHeaderId(Long movementHeaderId) {
        this.movementHeaderId = movementHeaderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.MOVEMENT_TYPE
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.MOVEMENT_TYPE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public String getMovementType() {
        return movementType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.MOVEMENT_TYPE
     *
     * @param movementType the value for FIF_RS_MOVEMENT_HEADERS.MOVEMENT_TYPE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setMovementType(String movementType) {
        this.movementType = movementType == null ? null : movementType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.ORGANIZATION_ID
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.ORGANIZATION_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.ORGANIZATION_ID
     *
     * @param organizationId the value for FIF_RS_MOVEMENT_HEADERS.ORGANIZATION_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.RENTAL_TYPE_ID
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.RENTAL_TYPE_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Long getRentalTypeId() {
        return rentalTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.RENTAL_TYPE_ID
     *
     * @param rentalTypeId the value for FIF_RS_MOVEMENT_HEADERS.RENTAL_TYPE_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setRentalTypeId(Long rentalTypeId) {
        this.rentalTypeId = rentalTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.SOURCE_LOCATION_ID
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.SOURCE_LOCATION_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Long getSourceLocationId() {
        return sourceLocationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.SOURCE_LOCATION_ID
     *
     * @param sourceLocationId the value for FIF_RS_MOVEMENT_HEADERS.SOURCE_LOCATION_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setSourceLocationId(Long sourceLocationId) {
        this.sourceLocationId = sourceLocationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.TARGET_LOCATION_ID
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.TARGET_LOCATION_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Long getTargetLocationId() {
        return targetLocationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.TARGET_LOCATION_ID
     *
     * @param targetLocationId the value for FIF_RS_MOVEMENT_HEADERS.TARGET_LOCATION_ID
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setTargetLocationId(Long targetLocationId) {
        this.targetLocationId = targetLocationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.COMMENTS
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.COMMENTS
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public String getComments() {
        return comments;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.COMMENTS
     *
     * @param comments the value for FIF_RS_MOVEMENT_HEADERS.COMMENTS
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.REQUESTOR
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.REQUESTOR
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Long getRequestor() {
        return requestor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.REQUESTOR
     *
     * @param requestor the value for FIF_RS_MOVEMENT_HEADERS.REQUESTOR
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setRequestor(Long requestor) {
        this.requestor = requestor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.REQUEST_DATE
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.REQUEST_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.REQUEST_DATE
     *
     * @param requestDate the value for FIF_RS_MOVEMENT_HEADERS.REQUEST_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.AUTHORIZATION_STATUS
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.AUTHORIZATION_STATUS
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public String getAuthorizationStatus() {
        return authorizationStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.AUTHORIZATION_STATUS
     *
     * @param authorizationStatus the value for FIF_RS_MOVEMENT_HEADERS.AUTHORIZATION_STATUS
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setAuthorizationStatus(String authorizationStatus) {
        this.authorizationStatus = authorizationStatus == null ? null : authorizationStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.APPROVED_FLAG
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.APPROVED_FLAG
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public String getApprovedFlag() {
        return approvedFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.APPROVED_FLAG
     *
     * @param approvedFlag the value for FIF_RS_MOVEMENT_HEADERS.APPROVED_FLAG
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setApprovedFlag(String approvedFlag) {
        this.approvedFlag = approvedFlag == null ? null : approvedFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.APPROVED_DATE
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.APPROVED_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Date getApprovedDate() {
        return approvedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.APPROVED_DATE
     *
     * @param approvedDate the value for FIF_RS_MOVEMENT_HEADERS.APPROVED_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.RECEIVED_ALL_FLAG
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.RECEIVED_ALL_FLAG
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public String getReceivedAllFlag() {
        return receivedAllFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.RECEIVED_ALL_FLAG
     *
     * @param receivedAllFlag the value for FIF_RS_MOVEMENT_HEADERS.RECEIVED_ALL_FLAG
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setReceivedAllFlag(String receivedAllFlag) {
        this.receivedAllFlag = receivedAllFlag == null ? null : receivedAllFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.LAST_RECEIVED_DATE
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.LAST_RECEIVED_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Date getLastReceivedDate() {
        return lastReceivedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.LAST_RECEIVED_DATE
     *
     * @param lastReceivedDate the value for FIF_RS_MOVEMENT_HEADERS.LAST_RECEIVED_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setLastReceivedDate(Date lastReceivedDate) {
        this.lastReceivedDate = lastReceivedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.CANCEL_FLAG
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.CANCEL_FLAG
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public String getCancelFlag() {
        return cancelFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.CANCEL_FLAG
     *
     * @param cancelFlag the value for FIF_RS_MOVEMENT_HEADERS.CANCEL_FLAG
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag == null ? null : cancelFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.CANCEL_DATE
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.CANCEL_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Date getCancelDate() {
        return cancelDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.CANCEL_DATE
     *
     * @param cancelDate the value for FIF_RS_MOVEMENT_HEADERS.CANCEL_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.CLOSED_CODE
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.CLOSED_CODE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public String getClosedCode() {
        return closedCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.CLOSED_CODE
     *
     * @param closedCode the value for FIF_RS_MOVEMENT_HEADERS.CLOSED_CODE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setClosedCode(String closedCode) {
        this.closedCode = closedCode == null ? null : closedCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.CLOSED_DATE
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.CLOSED_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Date getClosedDate() {
        return closedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.CLOSED_DATE
     *
     * @param closedDate the value for FIF_RS_MOVEMENT_HEADERS.CLOSED_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.CREATED_BY
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.CREATED_BY
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.CREATED_BY
     *
     * @param createdBy the value for FIF_RS_MOVEMENT_HEADERS.CREATED_BY
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.CREATION_DATE
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.CREATION_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.CREATION_DATE
     *
     * @param creationDate the value for FIF_RS_MOVEMENT_HEADERS.CREATION_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_BY
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_BY
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_BY
     *
     * @param lastUpdateBy the value for FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_BY
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_DATE
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_DATE
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_LOGIN
     *
     * @return the value of FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_LOGIN
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public Long getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_LOGIN
     *
     * @param lastUpdateLogin the value for FIF_RS_MOVEMENT_HEADERS.LAST_UPDATE_LOGIN
     *
     * @mbggenerated Thu Oct 09 14:46:57 ICT 2014
     */
    public void setLastUpdateLogin(Long lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }
}