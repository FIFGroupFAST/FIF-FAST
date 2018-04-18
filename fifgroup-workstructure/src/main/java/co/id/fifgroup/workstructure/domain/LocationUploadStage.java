package co.id.fifgroup.workstructure.domain;

import java.io.Serializable;
import java.util.Date;

public class LocationUploadStage implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.UPLOAD_ID
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private Long uploadId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.DATE_FROM
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private Date dateFrom;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.DATE_TO
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private Date dateTo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.LOCATION_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private String locationCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.LOCATION_NAME
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private String locationName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.DESCRIPTION
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.COUNTRY_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private String countryCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.PROVINCE_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private String provinceCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.CITY_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private String cityCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.ZONE_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private String zoneCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.ROW_NUMBER
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private Integer rowNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.RAW_DATA
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private String rawData;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.IS_CLOSED
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private Boolean isClosed;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.WORKING_SCHEDULE_ID
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private Long workingScheduleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_LOCATION_STG.BRANCH_OWNER_ID
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private Long branchOwnerId;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_LOCATION_STG
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.UPLOAD_ID
     *
     * @return the value of WOS_LOCATION_STG.UPLOAD_ID
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public Long getUploadId() {
        return uploadId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.UPLOAD_ID
     *
     * @param uploadId the value for WOS_LOCATION_STG.UPLOAD_ID
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setUploadId(Long uploadId) {
        this.uploadId = uploadId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.DATE_FROM
     *
     * @return the value of WOS_LOCATION_STG.DATE_FROM
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.DATE_FROM
     *
     * @param dateFrom the value for WOS_LOCATION_STG.DATE_FROM
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.DATE_TO
     *
     * @return the value of WOS_LOCATION_STG.DATE_TO
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.DATE_TO
     *
     * @param dateTo the value for WOS_LOCATION_STG.DATE_TO
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.LOCATION_CODE
     *
     * @return the value of WOS_LOCATION_STG.LOCATION_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public String getLocationCode() {
        return locationCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.LOCATION_CODE
     *
     * @param locationCode the value for WOS_LOCATION_STG.LOCATION_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode == null ? null : locationCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.LOCATION_NAME
     *
     * @return the value of WOS_LOCATION_STG.LOCATION_NAME
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.LOCATION_NAME
     *
     * @param locationName the value for WOS_LOCATION_STG.LOCATION_NAME
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName == null ? null : locationName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.DESCRIPTION
     *
     * @return the value of WOS_LOCATION_STG.DESCRIPTION
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.DESCRIPTION
     *
     * @param description the value for WOS_LOCATION_STG.DESCRIPTION
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.COUNTRY_CODE
     *
     * @return the value of WOS_LOCATION_STG.COUNTRY_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.COUNTRY_CODE
     *
     * @param countryCode the value for WOS_LOCATION_STG.COUNTRY_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.PROVINCE_CODE
     *
     * @return the value of WOS_LOCATION_STG.PROVINCE_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.PROVINCE_CODE
     *
     * @param provinceCode the value for WOS_LOCATION_STG.PROVINCE_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.CITY_CODE
     *
     * @return the value of WOS_LOCATION_STG.CITY_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.CITY_CODE
     *
     * @param cityCode the value for WOS_LOCATION_STG.CITY_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.ZONE_CODE
     *
     * @return the value of WOS_LOCATION_STG.ZONE_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public String getZoneCode() {
        return zoneCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.ZONE_CODE
     *
     * @param zoneCode the value for WOS_LOCATION_STG.ZONE_CODE
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode == null ? null : zoneCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.ROW_NUMBER
     *
     * @return the value of WOS_LOCATION_STG.ROW_NUMBER
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public Integer getRowNumber() {
        return rowNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.ROW_NUMBER
     *
     * @param rowNumber the value for WOS_LOCATION_STG.ROW_NUMBER
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.RAW_DATA
     *
     * @return the value of WOS_LOCATION_STG.RAW_DATA
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public String getRawData() {
        return rawData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.RAW_DATA
     *
     * @param rawData the value for WOS_LOCATION_STG.RAW_DATA
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setRawData(String rawData) {
        this.rawData = rawData == null ? null : rawData.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.IS_CLOSED
     *
     * @return the value of WOS_LOCATION_STG.IS_CLOSED
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public Boolean getIsClosed() {
        return isClosed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.IS_CLOSED
     *
     * @param isClosed the value for WOS_LOCATION_STG.IS_CLOSED
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.WORKING_SCHEDULE_ID
     *
     * @return the value of WOS_LOCATION_STG.WORKING_SCHEDULE_ID
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public Long getWorkingScheduleId() {
        return workingScheduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.WORKING_SCHEDULE_ID
     *
     * @param workingScheduleId the value for WOS_LOCATION_STG.WORKING_SCHEDULE_ID
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setWorkingScheduleId(Long workingScheduleId) {
        this.workingScheduleId = workingScheduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_LOCATION_STG.BRANCH_OWNER_ID
     *
     * @return the value of WOS_LOCATION_STG.BRANCH_OWNER_ID
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public Long getBranchOwnerId() {
        return branchOwnerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_LOCATION_STG.BRANCH_OWNER_ID
     *
     * @param branchOwnerId the value for WOS_LOCATION_STG.BRANCH_OWNER_ID
     *
     * @mbggenerated Thu Jan 30 14:59:01 ICT 2014
     */
    public void setBranchOwnerId(Long branchOwnerId) {
        this.branchOwnerId = branchOwnerId;
    }
}