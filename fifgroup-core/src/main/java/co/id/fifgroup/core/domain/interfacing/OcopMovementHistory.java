package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.util.Date;

public class OcopMovementHistory implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_HISTORY_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Long movementHistoryId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.ORGANIZATION_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Long organizationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RENTED_ITEM_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Long rentedItemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_HEADER_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Long movementHeaderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_LINE_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Long movementLineId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_TYPE
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private String movementType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.SOURCE_LOCATION_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Long sourceLocationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.TARGET_LOCATION_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Long targetLocationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.REQUESTOR
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Long requestor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.REQUEST_DATE
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Date requestDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.APPROVER
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Long approver;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.APPROVER_DATE
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Date approverDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RECEIVER
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Long receiver;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RECEIVE_DATE
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private Date receiveDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FIF_RS_RNTD_ITEM_MVMNT_HSTRY
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_HISTORY_ID
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_HISTORY_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Long getMovementHistoryId() {
        return movementHistoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_HISTORY_ID
     *
     * @param movementHistoryId the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_HISTORY_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setMovementHistoryId(Long movementHistoryId) {
        this.movementHistoryId = movementHistoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.ORGANIZATION_ID
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.ORGANIZATION_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.ORGANIZATION_ID
     *
     * @param organizationId the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.ORGANIZATION_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RENTED_ITEM_ID
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RENTED_ITEM_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Long getRentedItemId() {
        return rentedItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RENTED_ITEM_ID
     *
     * @param rentedItemId the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RENTED_ITEM_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setRentedItemId(Long rentedItemId) {
        this.rentedItemId = rentedItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_HEADER_ID
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_HEADER_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Long getMovementHeaderId() {
        return movementHeaderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_HEADER_ID
     *
     * @param movementHeaderId the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_HEADER_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setMovementHeaderId(Long movementHeaderId) {
        this.movementHeaderId = movementHeaderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_LINE_ID
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_LINE_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Long getMovementLineId() {
        return movementLineId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_LINE_ID
     *
     * @param movementLineId the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_LINE_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setMovementLineId(Long movementLineId) {
        this.movementLineId = movementLineId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_TYPE
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_TYPE
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public String getMovementType() {
        return movementType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_TYPE
     *
     * @param movementType the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.MOVEMENT_TYPE
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setMovementType(String movementType) {
        this.movementType = movementType == null ? null : movementType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.SOURCE_LOCATION_ID
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.SOURCE_LOCATION_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Long getSourceLocationId() {
        return sourceLocationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.SOURCE_LOCATION_ID
     *
     * @param sourceLocationId the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.SOURCE_LOCATION_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setSourceLocationId(Long sourceLocationId) {
        this.sourceLocationId = sourceLocationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.TARGET_LOCATION_ID
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.TARGET_LOCATION_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Long getTargetLocationId() {
        return targetLocationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.TARGET_LOCATION_ID
     *
     * @param targetLocationId the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.TARGET_LOCATION_ID
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setTargetLocationId(Long targetLocationId) {
        this.targetLocationId = targetLocationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.REQUESTOR
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.REQUESTOR
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Long getRequestor() {
        return requestor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.REQUESTOR
     *
     * @param requestor the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.REQUESTOR
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setRequestor(Long requestor) {
        this.requestor = requestor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.REQUEST_DATE
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.REQUEST_DATE
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.REQUEST_DATE
     *
     * @param requestDate the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.REQUEST_DATE
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.APPROVER
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.APPROVER
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Long getApprover() {
        return approver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.APPROVER
     *
     * @param approver the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.APPROVER
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setApprover(Long approver) {
        this.approver = approver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.APPROVER_DATE
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.APPROVER_DATE
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Date getApproverDate() {
        return approverDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.APPROVER_DATE
     *
     * @param approverDate the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.APPROVER_DATE
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setApproverDate(Date approverDate) {
        this.approverDate = approverDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RECEIVER
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RECEIVER
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Long getReceiver() {
        return receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RECEIVER
     *
     * @param receiver the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RECEIVER
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RECEIVE_DATE
     *
     * @return the value of FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RECEIVE_DATE
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RECEIVE_DATE
     *
     * @param receiveDate the value for FIF_RS_RNTD_ITEM_MVMNT_HSTRY.RECEIVE_DATE
     *
     * @mbggenerated Thu Oct 09 16:06:33 ICT 2014
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
}