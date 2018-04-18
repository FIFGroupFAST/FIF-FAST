package co.id.fifgroup.systemadmin.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.systemadmin.constant.TaskScheduleType;
import co.id.fifgroup.systemadmin.dto.TaskRequestDTO;

@Component
public class TaskScheduleValidator implements Validator<TaskRequestDTO> {

	public static final String TOTAL_DAYS = "taskSchedule.totalDays";
	public static final String END_AFTER_OCCURENCE = "taskSchedule.endAfterOccurence";
	public static final String END_BY_DATE = "taskSchedule.endByDate";
	public static final String RECUR_EVERY = "taskSchedule.recurEvery";
	public static final String START_TIME = "taskSchedule.startTime";
	public static final String EXECUTION_TIME = "taskSchedule.executionTime";
	public static final String DAY_NUM = "taskSchedule.dayNum";
	public static final String TOTAL_MONTH = "taskSchedule.totalMonth";
	public static final String DAY_MONTH = "dayMonth";

	@Override
	public void validate(TaskRequestDTO subject) throws ValidationException {

		Map<String, String> violations = new HashMap<String, String>();
		if (null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		if (subject.getId() == null) {
			if(subject.getScheduleType() != TaskScheduleType.IMMEDIATELY.getValue().toUpperCase()){
				insertValidation(subject, violations);
			}
		}
		if (violations.size() > 0)
			throw new ValidationException(violations);
	}

	private void insertValidation(TaskRequestDTO subject,
			Map<String, String> errors) {
		formValidation(subject, errors);
	}

	private void formValidation(TaskRequestDTO subject,
			Map<String, String> errors) {
		String endType = subject.getEndType();
		switch (subject.getScheduleType()) {
		case "DAILY":
			if (subject.getDailyPattern().equalsIgnoreCase("EVERY_DAYS")) {
				if(subject.getTotalDay() == null){
					errors.put(TOTAL_DAYS, Labels.getLabel("sam.err.totalDaysCannotBeEmpty"));
				} else if (subject.getTotalDay() < 1) {
					errors.put(RECUR_EVERY, Labels.getLabel("sam.err.recurEveryDaysMustBeGreaterThanZero"));
				}
			}
			if (subject.getRecurEvery() == null) {
				errors.put(RECUR_EVERY, Labels.getLabel("sam.err.recurEveryDaysCannotBeEmpty"));
			} 
			break;
		case "WEEKLY":
			if (subject.getRecurEvery() == null) {
					errors.put(RECUR_EVERY, Labels.getLabel("sam.err.recurEveryWeeksCannotBeEmpty"));
			}else
			if(subject.getRecurEvery()<1){
				errors.put(RECUR_EVERY, Labels.getLabel("sam.err.recurEveryWeeksMustBeGreaterThanZero"));
			}
			break;
		case "MONTHLY":
			if (subject.getMonthlyPattern().equalsIgnoreCase("DAY")) {
				
				if(subject.getDayNum() == null){
					errors.put(DAY_NUM, Labels.getLabel("sam.err.monthlyDayNumCannotBeEmpty"));
				}
				if(subject.getTotalMonth() == null){
					errors.put(TOTAL_MONTH, Labels.getLabel("sam.err.totalMonthCannotBeEmpty"));
				}
				
				if(subject.getDayNum() == null && subject.getTotalMonth() == null){
					errors.put(DAY_MONTH, Labels.getLabel("sam.err.monthlyDayNumAndTotalMonthCannotBeEmpty"));
				}else{
					if(subject.getDayNum() > 31 || subject.getDayNum() < 1){
						errors.put(DAY_NUM, Labels.getLabel("sam.err.monthlyDayNumAndTotalMonthMustBetween"));
					}else if(subject.getTotalMonth() > 12 || subject.getTotalMonth() < 1){
						errors.put(TOTAL_MONTH, Labels.getLabel("sam.err.totalMonthMustBetweenOneAndTwelve"));
					}else if(subject.getDayNum() > 31 || subject.getDayNum() < 1
							&& subject.getTotalMonth() > 12 || subject.getTotalMonth() < 1){
						errors.put(DAY_MONTH, Labels.getLabel("sam.err.monthlyDayNumAndTotalMonthCannotBeEmpty"));
					}
				}
			}else{
				if(subject.getTotalMonth() == null){
					errors.put(TOTAL_MONTH, Labels.getLabel("sam.err.totalMonthCannotBeEmpty"));
				}else if(subject.getTotalMonth() > 12 || subject.getTotalMonth() < 1){
					errors.put(TOTAL_MONTH, Labels.getLabel("sam.err.totalMonthMustBetweenOneAndTwelve"));
				}
			}
			/*
			 * Remove recur every validation in monthly type
			 * 
			 * if (subject.getRecurEvery() == null) {
				errors.put(RECUR_EVERY, Labels.getLabel("sam.err.recurEveryMonthsCannotBeEmpty"));
			}else
			if(subject.getRecurEvery()<1){
				errors.put(RECUR_EVERY, Labels.getLabel("sam.err.recurEveryMonthsMustBeGreaterThanZero"));
			}*/
			break;
		case "YEARLY":
				if(subject.getRecurEvery() == null){
					errors.put(RECUR_EVERY, Labels.getLabel("sam.err.recurEveryYearsCannotBeEmpty"));
				}
				if(subject.getRecurEvery()<1){
					errors.put(RECUR_EVERY, Labels.getLabel("sam.err.recurEveryYearsMustBeGreaterThanZero"));
				}
			break;
		}
		
		if(subject.getExecutionHours() == null){
			errors.put(EXECUTION_TIME, Labels.getLabel("sam.err.executionTimeCannotBeEmpty"));
		}
		 if (endType.equalsIgnoreCase("END_AFTER")) {
			 if(subject.getEndAfterOccurence() == null){
				 errors.put(END_AFTER_OCCURENCE, Labels.getLabel("sam.err.endAfterOccurenceCannotBeEmpty"));
			 }
			 return;
		} else if(endType.equalsIgnoreCase("END_BY_DATE")){
			if(subject.getEndByDate() == null){
				 errors.put(END_BY_DATE, Labels.getLabel("sam.err.endByDateCannotBeEmpty"));
			 } else if (subject.getEndByDate().compareTo(subject.getStartTime()) <= 0) {
				 errors.put(END_BY_DATE, Labels.getLabel("common.mustBeGreaterThan", new Object[] {Labels.getLabel("sam.endBy"), Labels.getLabel("sam.start")}));
			 }
			return;
		}

	}

}
