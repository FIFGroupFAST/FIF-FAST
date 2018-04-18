package co.id.fifgroup.core.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.version.Version;

public class PaymentCycleDTO extends Version {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8350970854520936649L;
	private String paymentCycleCode;
    private String description;
    private Long companyId;
    private String paymentCycleType;
    private String recurrenceType;
    private Integer recurEvery;
    private String recurrenceEndType;
    private Integer recurrenceEndValues;
    private Integer cutOffProcessDay;
    private String weeklyRecurOn;
    private Integer dayNumber;
    private Integer monthlyPeriodNumber;
    private String sequenceName;
    private String dayName;
    private String monthName;
    private Long taskRunnerId;
    private String userName;
    private Date effectiveOnDate;
    private List<PaymentCyclePaycodeDTO> paymentCyclePaycode = new ArrayList<PaymentCyclePaycodeDTO>();
    private boolean paycodeValid;
    private String paycode;
    private String jobKey;
    
	public String getPaymentCycleCode() {
		return paymentCycleCode;
	}
	public void setPaymentCycleCode(String paymentCycleCode) {
		this.paymentCycleCode = paymentCycleCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getPaymentCycleType() {
		return paymentCycleType;
	}
	public void setPaymentCycleType(String paymentCycleType) {
		this.paymentCycleType = paymentCycleType;
	}
	public String getRecurrenceType() {
		return recurrenceType;
	}
	public void setRecurrenceType(String recurrenceType) {
		this.recurrenceType = recurrenceType;
	}
	public Integer getRecurEvery() {
		return recurEvery;
	}
	public void setRecurEvery(Integer recurEvery) {
		this.recurEvery = recurEvery;
	}
	public String getRecurrenceEndType() {
		return recurrenceEndType;
	}
	public void setRecurrenceEndType(String recurrenceEndType) {
		this.recurrenceEndType = recurrenceEndType;
	}
	public Integer getRecurrenceEndValues() {
		return recurrenceEndValues;
	}
	public void setRecurrenceEndValues(Integer recurrenceEndValues) {
		this.recurrenceEndValues = recurrenceEndValues;
	}
	public Integer getCutOffProcessDay() {
		return cutOffProcessDay;
	}
	public void setCutOffProcessDay(Integer cutOffProcessDay) {
		this.cutOffProcessDay = cutOffProcessDay;
	}
	public String getWeeklyRecurOn() {
		return weeklyRecurOn;
	}
	public void setWeeklyRecurOn(String weeklyRecurOn) {
		this.weeklyRecurOn = weeklyRecurOn;
	}
	public Integer getDayNumber() {
		return dayNumber;
	}
	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}
	public Integer getMonthlyPeriodNumber() {
		return monthlyPeriodNumber;
	}
	public void setMonthlyPeriodNumber(Integer monthlyPeriodNumber) {
		this.monthlyPeriodNumber = monthlyPeriodNumber;
	}
	public String getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	public String getDayName() {
		return dayName;
	}
	public void setDayName(String dayName) {
		this.dayName = dayName;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public Long getTaskRunnerId() {
		return taskRunnerId;
	}
	public void setTaskRunnerId(Long taskRunnerId) {
		this.taskRunnerId = taskRunnerId;
	}
	public List<PaymentCyclePaycodeDTO> getPaymentCyclePaycode() {
		return paymentCyclePaycode;
	}
	public void setPaymentCyclePaycode(List<PaymentCyclePaycodeDTO> paymentCyclePaycode) {
		this.paymentCyclePaycode = paymentCyclePaycode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}
	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}
	public boolean isPaycodeValid() {
		return paycodeValid;
	}
	public void setPaycodeValid(boolean paycodeValid) {
		this.paycodeValid = paycodeValid;
	}
	public String getPaycode() {
		return paycode;
	}
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}
	public String getJobKey() {
		return jobKey;
	}
	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}
	
}
