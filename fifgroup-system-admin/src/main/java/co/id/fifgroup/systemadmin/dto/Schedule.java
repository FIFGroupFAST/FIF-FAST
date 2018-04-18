package co.id.fifgroup.systemadmin.dto;

import java.util.Date;

import co.id.fifgroup.core.util.DateUtil;

import co.id.fifgroup.systemadmin.constant.DayName;
import co.id.fifgroup.systemadmin.constant.MonthName;
import co.id.fifgroup.systemadmin.constant.TaskEndDateType;
import co.id.fifgroup.systemadmin.constant.TaskMonthlyPattern;
import co.id.fifgroup.systemadmin.constant.TaskScheduleType;
import co.id.fifgroup.systemadmin.constant.TaskSequenceType;
import co.id.fifgroup.systemadmin.constant.TaskYearlyPattern;


public class Schedule {

	private String scheduleType = TaskScheduleType.IMMEDIATELY.getValue();
	private String pattern = TaskMonthlyPattern.DAY.getValue();
	
	private Date startTime;
	private Date executionTime;
	
	//DailyTask or WeeklyTask
	
	private int recurEvery = 1; 
	/*recurEvery can be 
	 * recur every x day s
	or recur every x week 
	or recur every x month*/
	
	private int dayNum = 1;
	boolean everyWeekDays = false;
	
	//Sequence used for specifying first, second dayname e.g first sunday, second thursday etc
	//always followed with theDayName
	private String theSequence = TaskSequenceType.FIRST.getValue();
	private String theDayName = DayName.MONDAY.getValue();
	
	
	//MonthlyTask
	private int dateNum = 1;
	private int monthlyMonthNum = 1;
	private String monthlyMonthName = MonthName.JAN.getValue();

	//YearlyTask
	private int theMonthNum = 1;
	private String yearlyMonthName = MonthName.JAN.getValue();
	
	//TaskEndType
	private String endType = TaskEndDateType.NEVER_END.getValue();
	private int afterOccurence = 1;
	private Date endDate = null;
	
	
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the dayNum
	 */
	public int getDayNum() {
		return dayNum;
	}
	/**
	 * @param dayNum the dayNum to set
	 */
	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}
	/**
	 * @return the everyWeekDays
	 */
	public boolean isEveryWeekDays() {
		return everyWeekDays;
	}
	/**
	 * @param everyWeekDays the everyWeekDays to set
	 */
	public void setEveryWeekDays(boolean everyWeekDays) {
		this.everyWeekDays = everyWeekDays;
	}
	/**
	 * @return the executionTime
	 */
	public Date getExecutionTime() {
		return executionTime;
	}
	/**
	 * @param executionTime the executionTime to set
	 */
	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the recurEvery
	 */
	public int getRecurEvery() {
		return recurEvery;
	}
	/**
	 * @param recurEvery the recurEvery to set
	 */
	public void setRecurEvery(int recurEvery) {
		this.recurEvery = recurEvery;
	}
	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}
	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	/**
	 * @return the scheduleType
	 */
	public String getScheduleType() {
		return scheduleType;
	}
	/**
	 * @param scheduleType the scheduleType to set
	 */
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	/**
	 * @return the dateNum
	 */
	public int getDateNum() {
		return dateNum;
	}
	/**
	 * @param dateNum the dateNum to set
	 */
	public void setDateNum(int dateNum) {
		this.dateNum = dateNum;
	}
	/**
	 * @return the monthlyMonthNum
	 */
	public int getMonthlyMonthNum() {
		return monthlyMonthNum;
	}
	/**
	 * @param monthlyMonthNum the monthlyMonthNum to set
	 */
	public void setMonthlyMonthNum(int monthlyMonthNum) {
		this.monthlyMonthNum = monthlyMonthNum;
	}
	/**
	 * @return the monthlyMonthName
	 */
	public String getMonthlyMonthName() {
		return monthlyMonthName;
	}
	/**
	 * @param monthlyMonthName the monthlyMonthName to set
	 */
	public void setMonthlyMonthName(String monthlyMonthName) {
		this.monthlyMonthName = monthlyMonthName;
	}
	/**
	 * @return the yearlyMonthName
	 */
	public String getYearlyMonthName() {
		return yearlyMonthName;
	}
	/**
	 * @param yearlyMonthName the yearlyMonthName to set
	 */
	public void setYearlyMonthName(String yearlyMonthName) {
		this.yearlyMonthName = yearlyMonthName;
	}
	/**
	 * @return the theSequence
	 */
	public String getTheSequence() {
		return theSequence;
	}
	/**
	 * @param theSequence the theSequence to set
	 */
	public void setTheSequence(String theSequence) {
		this.theSequence = theSequence;
	}
	/**
	 * @return the theDayName
	 */
	public String getTheDayName() {
		return theDayName;
	}
	/**
	 * @param theDayName the theDayName to set
	 */
	public void setTheDayName(String theDayName) {
		this.theDayName = theDayName;
	}
	/**
	 * @return the theMonthNum
	 */
	public int getTheMonthNum() {
		return theMonthNum;
	}
	/**
	 * @param theMonthNum the theMonthNum to set
	 */
	public void setTheMonthNum(int theMonthNum) {
		this.theMonthNum = theMonthNum;
	}
	/**
	 * @return the endType
	 */
	public String getEndType() {
		return endType;
	}
	/**
	 * @param endType the endType to set
	 */
	public void setEndType(String endType) {
		this.endType = endType;
	}
	/**
	 * @return the afterOccurence
	 */
	public int getAfterOccurence() {
		return afterOccurence;
	}
	/**
	 * @param afterOccurence the afterOccurence to set
	 */
	public void setAfterOccurence(int afterOccurence) {
		this.afterOccurence = afterOccurence;
	}
}
