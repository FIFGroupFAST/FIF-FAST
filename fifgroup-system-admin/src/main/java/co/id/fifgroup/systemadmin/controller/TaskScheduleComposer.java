package co.id.fifgroup.systemadmin.controller;

import java.util.Map;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import com.google.common.base.Objects;

import co.id.fifgroup.systemadmin.constant.DayName;
import co.id.fifgroup.systemadmin.constant.MonthName;
import co.id.fifgroup.systemadmin.constant.TaskDailyPattern;
import co.id.fifgroup.systemadmin.constant.TaskEndDateType;
import co.id.fifgroup.systemadmin.constant.TaskMonthlyPattern;
import co.id.fifgroup.systemadmin.constant.TaskScheduleType;
import co.id.fifgroup.systemadmin.constant.TaskSequenceType;
import co.id.fifgroup.systemadmin.constant.TaskYearlyPattern;
import co.id.fifgroup.systemadmin.domain.TaskRequest;
import co.id.fifgroup.systemadmin.dto.TaskRequestDTO;
import co.id.fifgroup.systemadmin.service.TaskRequestService;
import co.id.fifgroup.systemadmin.validation.TaskScheduleValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class TaskScheduleComposer extends SelectorComposer<Window>{
	
	private static Logger log = LoggerFactory.getLogger(TaskScheduleComposer.class);

	private static final long serialVersionUID = 1L;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private transient TaskRequestService taskRequestService;
	@Wire
	private Groupbox dailyGroup;
	@Wire
	private Groupbox weeklyGroup;
	@Wire
	private Groupbox monthlyGroup;
	@Wire
	private Groupbox yearlyGroup;
	@Wire
	private Radiogroup runTheTask;
	
	@Wire
	private Radiogroup rdgDailyPeriod;
	@Wire
	private Intbox txtDailyTotalDay;
	@Wire
	private Datebox dtbDailyStart;
	@Wire
	private Radiogroup rdgDailyEndDate;
	@Wire
	private Intbox dailyEndAfterOccurence;
	@Wire
	private Datebox dtbDailyEnd;
	
	
	@Wire
	private Intbox txtWeeklyRecurEvery;
	@Wire
	private Radiogroup rdgWeeklyRecurOn;
	@Wire
	private Datebox dtbWeeklyStart;
	@Wire
	private Radiogroup rdgWeeklyEndDate;
	@Wire
	private Intbox weeklyEndAfterOccurnce;
	@Wire
	private Datebox dtbWeeklyEnd;
	@Wire
	private Timebox tmbWeeklyExecutionTime;
	
	@Wire
	private Radiogroup rdgMonthlyPeriod; 
	@Wire
	private Intbox monthlyDayNum;
	@Wire
	private Intbox monthlyMonthNum;
	@Wire
	private Listbox lbMonthlyDayCount;
	@Wire
	private Listbox lbDayName;
	@Wire
	private Datebox dtbMonthlyStart;
	@Wire
	private Radiogroup rdgMonthlyEndDate;
	@Wire
	private Intbox monthlyAfterOccurence;
	@Wire
	private Datebox dtbMonthlyEnd;
	@Wire
	private Intbox monthlyTotalMonth;
	@Wire
	private Timebox tmbMonthlyExecutionTime;
	
	
	@Wire
	private Intbox yearlyRecurEvery;
	@Wire
	private Radiogroup rdgYearlyPeriod;
	@Wire
	private Intbox intDayOfMonth;
	@Wire
	private Listbox lbMonthName;
	@Wire
	private Listbox lbYearlySeq;
	@Wire
	private Listbox lbYearDayName;
	@Wire
	private Listbox lbYearMonthName;
	@Wire
	private Datebox dtbYearlyStart;
	@Wire
	private Radiogroup rdgYearlyEndDate;
	@Wire
	private Intbox yearAfterOccurence;
	@Wire
	private Datebox dtbYearlyEnd;
	@Wire
	private Timebox tmbYearlyExecutionTime;
	
	@Wire
	private Label lblDailyTotalDay;
	@Wire
	private Label lblDailyEndAfterOccurence;
	@Wire
	private Label lblWeeklyRecurEvery;
	@Wire
	private Label lblWeeklyEndAfterOccurnce;
	@Wire
	private Label lblMonthlyMonthNum;
	@Wire
	private Label lblMonthlyTotalMonth;
	@Wire
	private Label lblMonthlyAfterOccurence;
	@Wire
	private Label lblYearlyRecurEvery;
	@Wire
	private Label lblYearAfterOccurence;
	@Wire
	private Timebox tmbDailyExecutionTime;
	
	@WireVariable
	private TaskScheduleValidator taskScheduleValidator;
	
	private TaskRequestDTO taskSchedule;
	private TaskRequest editTaskSchedule;
	private TaskRequestDetailComposer composer;
	private boolean setDisabled = false;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		taskSchedule = (TaskRequestDTO) arg.get("taskSchedule");
		composer = (TaskRequestDetailComposer) arg.get("composer");
		if(arg.containsKey("setDisabled")){
			setDisabled = (boolean) arg.get("setDisabled");
			editTaskSchedule = taskRequestService.getTaskRequestByTaskRequestId(taskSchedule.getId());
		}
		loadLookup();
		loadParameters();
	}
	
	private void loadLookup(){
		LocalDateTime ldt = new LocalDateTime();
		ldt = ldt.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
		tmbDailyExecutionTime.setValue(ldt.toDate());
		tmbWeeklyExecutionTime.setValue(ldt.toDate());
		tmbMonthlyExecutionTime.setValue(ldt.toDate());
		tmbYearlyExecutionTime.setValue(ldt.toDate());
		
		lbMonthName.setModel(new ListModelList<MonthName>(MonthName.values()));
		lbMonthName.renderAll();
		lbMonthName.setSelectedIndex(0);
		
		lbYearMonthName.setModel(new ListModelList<MonthName>(MonthName.values()));
		lbYearMonthName.renderAll();
		lbYearMonthName.setSelectedIndex(0);
		
		lbYearDayName.setModel(new ListModelList<DayName>(DayName.values()));
		lbYearDayName.renderAll();
		lbYearDayName.setSelectedIndex(0);
		
		lbDayName.setModel(new ListModelList<DayName>(DayName.values()));
		lbDayName.renderAll();
		lbDayName.setSelectedIndex(0);
		
		lbMonthlyDayCount.setModel(new ListModelList<TaskSequenceType>(TaskSequenceType.values()));
		lbMonthlyDayCount.renderAll();
		lbMonthlyDayCount.setSelectedIndex(0);
		
		lbYearlySeq.setModel(new ListModelList<TaskSequenceType>(TaskSequenceType.values()));
		lbYearlySeq.renderAll();
		lbYearlySeq.setSelectedIndex(0);
		
		
	}
	
	public void checkAllRadioButtonGroupToIndexZero(){
		rdgDailyPeriod.setSelectedIndex(0);
		rdgYearlyPeriod.setSelectedIndex(0);
		rdgMonthlyPeriod.setSelectedIndex(0);
		rdgDailyEndDate.setSelectedIndex(0);
		rdgWeeklyEndDate.setSelectedIndex(0);
		rdgMonthlyEndDate.setSelectedIndex(0);
		rdgYearlyEndDate.setSelectedIndex(0);
	}

	
	
	private void loadParameters(){
		LocalDateTime ldt = new LocalDateTime();
		ldt = ldt.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
		if(setDisabled){
			if(editTaskSchedule != null){
				runTheTask.setSelectedIndex(TaskScheduleType.getKey(editTaskSchedule.getScheduleType()));
				disableRadioButton(runTheTask);
				if(editTaskSchedule.getScheduleType()!= null){
				if(editTaskSchedule.getScheduleType().equals(TaskScheduleType.DAILY.toString())){
					disableRadioButton(rdgDailyEndDate);
					disableRadioButton(rdgDailyPeriod);
					dailyGroup.setVisible(true);
					rdgDailyPeriod.setSelectedIndex(TaskDailyPattern.getKey(editTaskSchedule.getDailyPattern()));
					txtDailyTotalDay.setValue(editTaskSchedule.getTotalDay());
					if(editTaskSchedule.getDailyPattern().equals(TaskDailyPattern.EVERYDAYS.toString()))
						txtDailyTotalDay.setValue(editTaskSchedule.getTotalDay());
					dtbDailyStart.setValue(editTaskSchedule.getStartTime());
					dtbDailyStart.setDisabled(setDisabled);
					tmbDailyExecutionTime.setValue(editTaskSchedule.getExecutionHours()==null?ldt.toDate():editTaskSchedule.getExecutionHours());
					tmbDailyExecutionTime.setDisabled(setDisabled);
					rdgDailyEndDate.setSelectedIndex(TaskEndDateType.getKey(editTaskSchedule.getEndType()));
					if(editTaskSchedule.getEndType().equals(TaskEndDateType.END_AFTER.getValue()))
						dailyEndAfterOccurence.setValue(editTaskSchedule.getEndAfterOccurence());
						dailyEndAfterOccurence.setDisabled(setDisabled);
					if(editTaskSchedule.getEndType().equals(TaskEndDateType.END_BY_DATE.toString()))
						dtbDailyEnd.setValue(editTaskSchedule.getEndByDate());
					if(rdgDailyPeriod.getSelectedIndex()==1){
						txtDailyTotalDay.setDisabled(true);
					}else{
						txtDailyTotalDay.setDisabled(false);
					}
//					onCheckRdgDailyPeriod();
//					onCheckRdgDailyEndDate();
				}
				
				if(editTaskSchedule.getScheduleType().equals(TaskScheduleType.WEEKLY.toString())){
					weeklyGroup.setVisible(true);
					disableRadioButton(rdgWeeklyEndDate);
					disableRadioButton(rdgWeeklyRecurOn);
					txtWeeklyRecurEvery.setValue(editTaskSchedule.getRecurEvery());
					rdgWeeklyRecurOn.setSelectedIndex(DayName.getKey(editTaskSchedule.getRecurOn()));
					dtbWeeklyStart.setValue(editTaskSchedule.getStartTime());
//					tmbWeeklyExecutionTime.setValue(editTaskSchedule.getExecutionHours());
					tmbWeeklyExecutionTime.setValue(editTaskSchedule.getExecutionHours()==null?ldt.toDate():editTaskSchedule.getExecutionHours());
					rdgWeeklyEndDate.setSelectedIndex(TaskEndDateType.getKey(editTaskSchedule.getEndType()));
					if(editTaskSchedule.getEndType().equals(TaskEndDateType.END_AFTER.toString()))
						weeklyEndAfterOccurnce.setValue(editTaskSchedule.getEndAfterOccurence());
					if(editTaskSchedule.getEndType().equals(TaskEndDateType.END_BY_DATE.toString()))
						dtbWeeklyEnd.setValue(editTaskSchedule.getEndByDate());
					weeklyEndAfterOccurnce.setDisabled(setDisabled);
					tmbWeeklyExecutionTime.setDisabled(setDisabled);
					dtbWeeklyStart.setDisabled(setDisabled);
					dtbWeeklyEnd.setDisabled(setDisabled);
					txtWeeklyRecurEvery.setDisabled(setDisabled);
//					onCheckRdgWeeklyEndDate();
				}
				if(editTaskSchedule.getScheduleType().equals(TaskScheduleType.MONTHLY.toString())){
					monthlyGroup.setVisible(true);
					disableRadioButton(rdgMonthlyEndDate);
					disableRadioButton(rdgMonthlyPeriod);
					rdgMonthlyPeriod.setSelectedIndex(TaskMonthlyPattern.getKey(editTaskSchedule.getMonthlyPattern()));
					if(editTaskSchedule.getMonthlyPattern().equals(TaskMonthlyPattern.DAY.toString())){
						monthlyDayNum.setValue(editTaskSchedule.getDayNum());
						monthlyMonthNum.setValue(editTaskSchedule.getTotalMonth());
					}
					if(editTaskSchedule.getMonthlyPattern().equals(TaskMonthlyPattern.THE.toString())){
						for (Listitem item : lbMonthlyDayCount.getItems()) {
							if(item.getValue().equals(editTaskSchedule.getSequenceType()))
								item.setSelected(true);
						}
						for (Listitem item : lbDayName.getItems()) {
							if(item.getValue().equals(editTaskSchedule.getDayName()))
								item.setSelected(true);
						}
						monthlyTotalMonth.setValue(editTaskSchedule.getTotalMonth());
						monthlyTotalMonth.setDisabled(setDisabled);
					}
					dtbMonthlyStart.setValue(editTaskSchedule.getStartTime());
//					tmbMonthlyExecutionTime.setValue(editTaskSchedule.getExecutionHours());
					tmbMonthlyExecutionTime.setValue(editTaskSchedule.getExecutionHours()==null?ldt.toDate():editTaskSchedule.getExecutionHours());
					rdgMonthlyEndDate.setSelectedIndex(TaskEndDateType.getKey(editTaskSchedule.getEndType()));
					if(editTaskSchedule.getEndType().equals(TaskEndDateType.END_AFTER.toString()))
						monthlyAfterOccurence.setValue(editTaskSchedule.getEndAfterOccurence());
					if(editTaskSchedule.getEndType().equals(TaskEndDateType.END_BY_DATE.toString()))
						dtbMonthlyEnd.setValue(editTaskSchedule.getEndByDate());
					
					
						lbDayName.setDisabled(setDisabled);
						lbMonthlyDayCount.setDisabled(setDisabled);
						monthlyAfterOccurence.setDisabled(setDisabled);
						dtbMonthlyStart.setDisabled(setDisabled);
						tmbMonthlyExecutionTime.setDisabled(setDisabled);
						dtbMonthlyEnd.setDisabled(setDisabled);
						monthlyMonthNum.setDisabled(setDisabled);
						monthlyDayNum.setDisabled(setDisabled);
//					onCheckRdgMonthlyEndDate();
//					onCheckRdgMonthlyPeriod();
				}
				if(editTaskSchedule.getScheduleType().equals(TaskScheduleType.YEARLY.toString())){
					yearlyGroup.setVisible(true);
					disableRadioButton(rdgYearlyEndDate);
					disableRadioButton(rdgYearlyPeriod);
					yearlyRecurEvery.setValue(editTaskSchedule.getRecurEvery());
					rdgYearlyPeriod.setSelectedIndex(TaskYearlyPattern.getKey(editTaskSchedule.getYearlyPattern()));
					if(editTaskSchedule.getYearlyPattern().equals(TaskYearlyPattern.MONTH.toString())){
						for (Listitem item : lbMonthName.getItems()) {
							if(item.getValue().equals(editTaskSchedule.getMonthName()))
								item.setSelected(true);
						}
					}
					if(editTaskSchedule.getYearlyPattern().equals(TaskYearlyPattern.DAY.toString())){
						for (Listitem item : lbYearlySeq.getItems()) {
							if(item.getValue().equals(editTaskSchedule.getSequenceType()))
								item.setSelected(true);
						}
						for (Listitem item : lbYearDayName.getItems()) {
							if(item.getValue().equals(editTaskSchedule.getDayName()))
								item.setSelected(true);
						}
						for (Listitem item : lbYearMonthName.getItems()) {
							if(item.getValue().equals(editTaskSchedule.getMonthName()))
								item.setSelected(true);
						}
					}
					dtbYearlyStart.setValue(editTaskSchedule.getStartTime());
//					tmbYearlyExecutionTime.setValue(editTaskSchedule.getExecutionHours());
					tmbYearlyExecutionTime.setValue(editTaskSchedule.getExecutionHours()==null?ldt.toDate():editTaskSchedule.getExecutionHours());
					tmbYearlyExecutionTime.setDisabled(setDisabled);
					rdgYearlyEndDate.setSelectedIndex(TaskEndDateType.getKey(editTaskSchedule.getEndType()));
					if(editTaskSchedule.getEndType().equals(TaskEndDateType.END_AFTER.toString()))
						yearAfterOccurence.setValue(editTaskSchedule.getEndAfterOccurence());
					if(editTaskSchedule.getEndType().equals(TaskEndDateType.END_BY_DATE.toString()))
						dtbYearlyEnd.setValue(editTaskSchedule.getEndByDate());
					yearlyRecurEvery.setDisabled(setDisabled);
					yearAfterOccurence.setDisabled(setDisabled);
					dtbYearlyStart.setDisabled(setDisabled);
					dtbYearlyEnd.setDisabled(setDisabled);
					tmbYearlyExecutionTime.setDisabled(setDisabled);
					lbYearlySeq.setDisabled(setDisabled);
					lbYearDayName.setDisabled(setDisabled);
					lbYearMonthName.setDisabled(setDisabled);
					lbMonthName.setDisabled(setDisabled);
//					onCheckRdgYearlyPeriod();
//					onCheckRdgYearlyEndDate();
					intDayOfMonth.setValue(editTaskSchedule.getDayNum());
					intDayOfMonth.setDisabled(setDisabled);
				}
				}
			}
		}else{
			if(taskSchedule != null){
				runTheTask.setSelectedIndex(TaskScheduleType.getKey(taskSchedule.getScheduleType()));
				if(taskSchedule.getScheduleType()!= null){
					if(taskSchedule.getScheduleType().equals(TaskScheduleType.DAILY.toString())){
						tmbDailyExecutionTime.setValue(ldt.toDate());
						dailyGroup.setVisible(true);
						rdgDailyPeriod.setSelectedIndex(TaskDailyPattern.getKey(taskSchedule.getDailyPattern()));
						txtDailyTotalDay.setValue(taskSchedule.getTotalDay());
						if(taskSchedule.getDailyPattern().equals(TaskDailyPattern.EVERYDAYS.toString()))
							txtDailyTotalDay.setValue(taskSchedule.getTotalDay());
						dtbDailyStart.setValue(taskSchedule.getStartTime());
						rdgDailyEndDate.setSelectedIndex(TaskEndDateType.getKey(taskSchedule.getEndType()));
						if(taskSchedule.getEndType().equals(TaskEndDateType.END_AFTER.toString()))
							dailyEndAfterOccurence.setValue(taskSchedule.getEndAfterOccurence());
						if(taskSchedule.getEndType().equals(TaskEndDateType.END_BY_DATE.toString()))
							dtbDailyEnd.setValue(taskSchedule.getEndByDate());
						if(rdgDailyPeriod.getSelectedIndex()==1){
							txtDailyTotalDay.setDisabled(true);
						}else{
							txtDailyTotalDay.setDisabled(false);
						}
						tmbDailyExecutionTime.setValue(taskSchedule.getExecutionHours()==null?ldt.toDate():taskSchedule.getExecutionHours());
						onCheckRdgDailyPeriod();
						onCheckRdgDailyEndDate();
					}
					
					if(taskSchedule.getScheduleType().equals(TaskScheduleType.WEEKLY.toString())){
						tmbWeeklyExecutionTime.setValue(taskSchedule.getExecutionHours()==null?ldt.toDate():taskSchedule.getExecutionHours());
						weeklyGroup.setVisible(true);
						txtWeeklyRecurEvery.setValue(taskSchedule.getRecurEvery());
						rdgWeeklyRecurOn.setSelectedIndex(DayName.getKey(taskSchedule.getRecurOn()));
						dtbWeeklyStart.setValue(taskSchedule.getStartTime());
						rdgWeeklyEndDate.setSelectedIndex(TaskEndDateType.getKey(taskSchedule.getEndType()));
						if(taskSchedule.getEndType().equals(TaskEndDateType.END_AFTER.toString()))
							weeklyEndAfterOccurnce.setValue(taskSchedule.getEndAfterOccurence());
						if(taskSchedule.getEndType().equals(TaskEndDateType.END_BY_DATE.toString()))
							dtbWeeklyEnd.setValue(taskSchedule.getEndByDate());
						onCheckRdgWeeklyEndDate();
					}
					if(taskSchedule.getScheduleType().equals(TaskScheduleType.MONTHLY.toString())){
						tmbMonthlyExecutionTime.setValue(taskSchedule.getExecutionHours()==null?ldt.toDate():taskSchedule.getExecutionHours());
						monthlyGroup.setVisible(true);
						rdgMonthlyPeriod.setSelectedIndex(TaskMonthlyPattern.getKey(taskSchedule.getMonthlyPattern()));
						if(taskSchedule.getMonthlyPattern().equals(TaskMonthlyPattern.DAY.toString())){
							monthlyDayNum.setValue(taskSchedule.getDayNum());
							monthlyMonthNum.setValue(taskSchedule.getTotalMonth());
						}else{
							for (Listitem item : lbMonthlyDayCount.getItems()) {
								if(item.getValue().equals(taskSchedule.getSequenceType()))
									item.setSelected(true);
							}
							for (Listitem item : lbDayName.getItems()) {
								if(item.getValue().equals(taskSchedule.getDayName()))
									item.setSelected(true);
							}
							monthlyTotalMonth.setValue(taskSchedule.getTotalMonth());
						}
//						if(taskSchedule.getMonthlyPattern().equals(TaskMonthlyPattern.THE.toString())){
//						}
						dtbMonthlyStart.setValue(taskSchedule.getStartTime());
						rdgMonthlyEndDate.setSelectedIndex(TaskEndDateType.getKey(taskSchedule.getEndType()));
						if(taskSchedule.getEndType().equals(TaskEndDateType.END_AFTER.toString()))
							monthlyAfterOccurence.setValue(taskSchedule.getEndAfterOccurence());
						if(taskSchedule.getEndType().equals(TaskEndDateType.END_BY_DATE.toString()))
							dtbMonthlyEnd.setValue(taskSchedule.getEndByDate());
						onCheckRdgMonthlyEndDate();
						onCheckRdgMonthlyPeriod();
					}
					if(taskSchedule.getScheduleType().equals(TaskScheduleType.YEARLY.toString())){
						tmbYearlyExecutionTime.setValue(taskSchedule.getExecutionHours()==null?ldt.toDate():taskSchedule.getExecutionHours());
						yearlyGroup.setVisible(true);
						yearlyRecurEvery.setValue(taskSchedule.getRecurEvery());
						rdgYearlyPeriod.setSelectedIndex(TaskYearlyPattern.getKey(taskSchedule.getYearlyPattern()));
						if(taskSchedule.getYearlyPattern().equals(TaskYearlyPattern.MONTH.toString())){
							for (Listitem item : lbMonthName.getItems()) {
								if(item.getValue().equals(taskSchedule.getMonthName()))
									item.setSelected(true);
							}
						}
						if(taskSchedule.getYearlyPattern().equals(TaskYearlyPattern.DAY.toString())){
							for (Listitem item : lbYearlySeq.getItems()) {
								if(item.getValue().equals(taskSchedule.getSequenceType()))
									item.setSelected(true);
							}
							for (Listitem item : lbYearDayName.getItems()) {
								if(item.getValue().equals(taskSchedule.getDayName()))
									item.setSelected(true);
							}
							for (Listitem item : lbYearMonthName.getItems()) {
								if(item.getValue().equals(taskSchedule.getMonthName()))
									item.setSelected(true);
							}
						}
						dtbYearlyStart.setValue(taskSchedule.getStartTime());
						rdgYearlyEndDate.setSelectedIndex(TaskEndDateType.getKey(taskSchedule.getEndType()));
						if(taskSchedule.getEndType().equals(TaskEndDateType.END_AFTER.toString()))
							yearAfterOccurence.setValue(taskSchedule.getEndAfterOccurence());
						if(taskSchedule.getEndType().equals(TaskEndDateType.END_BY_DATE.toString()))
							dtbYearlyEnd.setValue(taskSchedule.getEndByDate());
						onCheckRdgYearlyPeriod();
						onCheckRdgYearlyEndDate();
					}
				}
			}
		}
	}

	private void disableRadioButton(Radiogroup r) {
		for(Radio rad : r.getItems()){
			rad.setDisabled(setDisabled);
		}
	}

	@Listen("onCheck=radiogroup#rdgDailyPeriod")
	public void onCheckRdgDailyPeriod(){
		if(rdgDailyPeriod.getSelectedIndex()==1){
			txtDailyTotalDay.setDisabled(true);
		}else{
			txtDailyTotalDay.setDisabled(false);
		}
	}
	
	@Listen("onCheck=radiogroup#rdgYearlyPeriod")
	public void onCheckRdgYearlyPeriod(){
		if(rdgYearlyPeriod.getSelectedIndex()==0){
			intDayOfMonth.setDisabled(false);
			lbMonthName.setDisabled(false);
			lbYearlySeq.setDisabled(true);
			lbYearDayName.setDisabled(true);
			lbYearMonthName.setDisabled(true);
		}else{
			intDayOfMonth.setDisabled(true);
			lbMonthName.setDisabled(true);
			lbYearlySeq.setDisabled(false);
			lbYearDayName.setDisabled(false);
			lbYearMonthName.setDisabled(false);
		}
	}
	
	@Listen("onCheck=radiogroup#rdgMonthlyPeriod")
	public void onCheckRdgMonthlyPeriod(){
		if(rdgMonthlyPeriod.getSelectedItem().getLabel().toUpperCase().equals(TaskMonthlyPattern.DAY.getValue())){
			lbMonthlyDayCount.setDisabled(true);
			lbDayName.setDisabled(true);
			monthlyTotalMonth.setDisabled(true);
			monthlyDayNum.setDisabled(false);
			monthlyMonthNum.setDisabled(false);
		}else{
			lbMonthlyDayCount.setDisabled(false);
			lbDayName.setDisabled(false);
			monthlyTotalMonth.setDisabled(false);
			monthlyDayNum.setDisabled(true);
			monthlyMonthNum.setDisabled(true);
		}
	}
	
	@Listen("onCheck=radiogroup#rdgDailyEndDate")
	public void onCheckRdgDailyEndDate(){
		if(rdgDailyEndDate.getSelectedIndex()==0){
			dailyEndAfterOccurence.setDisabled(true);
			dtbDailyEnd.setDisabled(true);
		}else if(rdgDailyEndDate.getSelectedIndex()==1){
			dailyEndAfterOccurence.setDisabled(false);
			dtbDailyEnd.setDisabled(true);
		}else{
			dailyEndAfterOccurence.setDisabled(true);
			dtbDailyEnd.setDisabled(false);
		}
	}
	
	@Listen("onCheck=radiogroup#rdgWeeklyEndDate")
	public void onCheckRdgWeeklyEndDate(){
		if(rdgWeeklyEndDate.getSelectedIndex()==0){
			weeklyEndAfterOccurnce.setDisabled(true);
			dtbWeeklyEnd.setDisabled(true);
		}else if(rdgWeeklyEndDate.getSelectedIndex()==1){
			weeklyEndAfterOccurnce.setDisabled(false);
			dtbWeeklyEnd.setDisabled(true);
		}else{
			weeklyEndAfterOccurnce.setDisabled(true);
			dtbWeeklyEnd.setDisabled(false);
		}
		
	}
	
	@Listen("onCheck=radiogroup#rdgMonthlyEndDate")
	public void onCheckRdgMonthlyEndDate(){
		if(rdgMonthlyEndDate.getSelectedIndex()==0){
			monthlyAfterOccurence.setDisabled(true);
			dtbMonthlyEnd.setDisabled(true);
		}else if(rdgMonthlyEndDate.getSelectedIndex()==1){
			monthlyAfterOccurence.setDisabled(false);
			dtbMonthlyEnd.setDisabled(true);
		}else{
			monthlyAfterOccurence.setDisabled(true);
			dtbMonthlyEnd.setDisabled(false);
		}
	}
	
	@Listen("onCheck=radiogroup#rdgYearlyEndDate")
	public void onCheckRdgYearlyEndDate(){
		if(rdgYearlyEndDate.getSelectedIndex()==0){
			yearAfterOccurence.setDisabled(true);
			dtbYearlyEnd.setDisabled(true);
		}else if(rdgYearlyEndDate.getSelectedIndex()==1){
			yearAfterOccurence.setDisabled(false);
			dtbYearlyEnd.setDisabled(true);
		}else{
			yearAfterOccurence.setDisabled(true);
			dtbYearlyEnd.setDisabled(false);
		}
	}
	
	@Listen("onClick=radiogroup#runTheTask")
	public void showScheduleDetail(){
		int idx = runTheTask.getSelectedIndex();
		hideAllScheduleGroup();
		if(idx == TaskScheduleType.DAILY.getKey()){
			dailyGroup.setVisible(true);
			onCheckRdgDailyPeriod();
			onCheckRdgDailyEndDate();
		}
		
		if(idx == TaskScheduleType.WEEKLY.getKey()){
			weeklyGroup.setVisible(true);
			onCheckRdgWeeklyEndDate();
		}
		
		if(idx == TaskScheduleType.MONTHLY.getKey()){
		monthlyGroup.setVisible(true);
		onCheckRdgMonthlyEndDate();
		onCheckRdgMonthlyPeriod();
		}
		
		if(idx == TaskScheduleType.YEARLY.getKey()){
			yearlyGroup.setVisible(true);
			onCheckRdgYearlyPeriod();
			onCheckRdgYearlyEndDate();			
		}
	}
	
	private void hideAllScheduleGroup(){
		dailyGroup.setVisible(false);
		weeklyGroup.setVisible(false);
		monthlyGroup.setVisible(false);
		yearlyGroup.setVisible(false);
	}
	
	@Listen("onClick=button#btnOk")
	public void onOk(){
		
		if(taskSchedule == null)
			taskSchedule = new TaskRequestDTO();
		if(runTheTask.getSelectedIndex() == TaskScheduleType.IMMEDIATELY.getKey()){
			taskSchedule.setEndType(TaskEndDateType.END_AFTER.getValue());
			taskSchedule.setEndAfterOccurence(1);
		}
		if(runTheTask.getSelectedIndex() == TaskScheduleType.DAILY.getKey()){
			taskSchedule.setRecurEvery(rdgDailyPeriod.getSelectedIndex());
			if(rdgDailyPeriod.getSelectedIndex() == TaskDailyPattern.EVERYDAYS.getKey())
				taskSchedule.setTotalDay(txtDailyTotalDay.getValue());
			taskSchedule.setDailyPattern(TaskDailyPattern.getValue(rdgDailyPeriod.getSelectedIndex()));
//			taskSchedule.setStartTime(dtbDailyStart.getValue());
			LocalDateTime ldt = new LocalDateTime(dtbDailyStart.getValue());
			LocalDateTime ext = new LocalDateTime(tmbDailyExecutionTime.getValue());
			ldt = ldt.withHourOfDay(ext.getHourOfDay()).withMinuteOfHour(ext.getMinuteOfHour()).withSecondOfMinute(0);
			taskSchedule.setStartTime(ldt.toDate());
			ext = ext.withDayOfMonth(ldt.getDayOfMonth()).withYear(ldt.getYear()).withMonthOfYear(ldt.getMonthOfYear());
			taskSchedule.setExecutionHours(ext.toDate());
			if(rdgDailyEndDate.getSelectedIndex() == TaskEndDateType.END_AFTER.getKey())
				taskSchedule.setEndAfterOccurence(dailyEndAfterOccurence.getValue());
			if(rdgDailyEndDate.getSelectedIndex() == TaskEndDateType.END_BY_DATE.getKey())
				taskSchedule.setEndByDate(dtbDailyEnd.getValue());
			taskSchedule.setEndType(TaskEndDateType.getValue(rdgDailyEndDate.getSelectedIndex()));
		}
		if(runTheTask.getSelectedIndex() == TaskScheduleType.WEEKLY.getKey()){
			taskSchedule.setRecurEvery(txtWeeklyRecurEvery.getValue());
			taskSchedule.setRecurOn(DayName.getValue(rdgWeeklyRecurOn.getSelectedIndex()));
//			taskSchedule.setStartTime(dtbWeeklyStart.getValue());
			LocalDateTime ldt = new LocalDateTime(dtbWeeklyStart.getValue());
			LocalDateTime ext = new LocalDateTime(tmbWeeklyExecutionTime.getValue());
			ldt = ldt.withHourOfDay(ext.getHourOfDay()).withMinuteOfHour(ext.getMinuteOfHour()).withSecondOfMinute(0);
			taskSchedule.setStartTime(ldt.toDate());
			ext = ext.withDayOfMonth(ldt.getDayOfMonth()).withYear(ldt.getYear()).withMonthOfYear(ldt.getMonthOfYear());
			taskSchedule.setExecutionHours(ext.toDate());
			taskSchedule.setEndType(TaskEndDateType.getValue(rdgWeeklyEndDate.getSelectedIndex()));
			if(rdgWeeklyEndDate.getSelectedIndex() == TaskEndDateType.END_AFTER.getKey())
				taskSchedule.setEndAfterOccurence(weeklyEndAfterOccurnce.getValue());
			if(rdgWeeklyEndDate.getSelectedIndex() == TaskEndDateType.END_BY_DATE.getKey())
				taskSchedule.setEndByDate(dtbWeeklyEnd.getValue());
		}
		if(runTheTask.getSelectedIndex() == TaskScheduleType.MONTHLY.getKey()){
			
			taskSchedule.setMonthlyPattern(TaskMonthlyPattern.getValue(rdgMonthlyPeriod.getSelectedIndex()));
			if(rdgMonthlyPeriod.getSelectedIndex() == TaskMonthlyPattern.DAY.getKey()){
				taskSchedule.setDayNum(monthlyDayNum.getValue());
				taskSchedule.setTotalMonth(monthlyMonthNum.getValue());
			}else{
				taskSchedule.setSequenceType(TaskSequenceType.getValue(lbMonthlyDayCount.getSelectedIndex()+1).toUpperCase());
				taskSchedule.setDayName(lbDayName.getSelectedItem().getValue().toString().toUpperCase());
				taskSchedule.setTotalMonth(monthlyTotalMonth.getValue());
			}
//			taskSchedule.setStartTime(dtbMonthlyStart.getValue());
			LocalDateTime ldt = new LocalDateTime(dtbMonthlyStart.getValue());
			LocalDateTime ext = new LocalDateTime(tmbMonthlyExecutionTime.getValue());
			ldt = ldt.withHourOfDay(ext.getHourOfDay()).withMinuteOfHour(ext.getMinuteOfHour()).withSecondOfMinute(0);
			taskSchedule.setStartTime(ldt.toDate());
			ext = ext.withDayOfMonth(ldt.getDayOfMonth()).withYear(ldt.getYear()).withMonthOfYear(ldt.getMonthOfYear());
			taskSchedule.setExecutionHours(ext.toDate());
			taskSchedule.setEndType(TaskEndDateType.getValue(rdgMonthlyEndDate.getSelectedIndex()));
			if(rdgMonthlyEndDate.getSelectedIndex() == TaskEndDateType.END_AFTER.getKey())
				taskSchedule.setEndAfterOccurence(monthlyAfterOccurence.getValue());
			if(rdgMonthlyEndDate.getSelectedIndex() == TaskEndDateType.END_BY_DATE.getKey())
				taskSchedule.setEndByDate(dtbMonthlyEnd.getValue());
		}
		if(runTheTask.getSelectedIndex() == TaskScheduleType.YEARLY.getKey()){
			taskSchedule.setRecurEvery(yearlyRecurEvery.getValue());
			yearlyRecurEvery.setDisabled(setDisabled);
			
			taskSchedule.setYearlyPattern(TaskYearlyPattern.getValue(rdgYearlyPeriod.getSelectedIndex()));
			if(rdgYearlyPeriod.getSelectedIndex() == TaskYearlyPattern.MONTH.getKey()) {
				taskSchedule.setMonthName(lbMonthName.getSelectedItem().getValue().toString().toUpperCase());
				taskSchedule.setDayNum(Objects.firstNonNull(intDayOfMonth.getValue(), 1));
			}else if(rdgYearlyPeriod.getSelectedIndex() == TaskYearlyPattern.DAY.getKey()){
				taskSchedule.setSequenceType(lbYearlySeq.getSelectedItem().getValue().toString().toUpperCase());
				taskSchedule.setDayName(lbYearDayName.getSelectedItem().getValue().toString().toUpperCase());
				taskSchedule.setMonthName( lbYearMonthName.getSelectedItem().getValue().toString().toUpperCase());
			}
//			taskSchedule.setStartTime(dtbYearlyStart.getValue());
			LocalDateTime ldt = new LocalDateTime(dtbYearlyStart.getValue());
			LocalDateTime ext = new LocalDateTime(tmbYearlyExecutionTime.getValue());
			ldt = ldt.withHourOfDay(ext.getHourOfDay()).withMinuteOfHour(ext.getMinuteOfHour()).withSecondOfMinute(0);
			taskSchedule.setStartTime(ldt.toDate());
			ext = ext.withDayOfMonth(ldt.getDayOfMonth()).withYear(ldt.getYear()).withMonthOfYear(ldt.getMonthOfYear());
			taskSchedule.setExecutionHours(ext.toDate());
			taskSchedule.setEndType(TaskEndDateType.getValue(rdgYearlyEndDate.getSelectedIndex()));
			if(rdgYearlyEndDate.getSelectedIndex() == TaskEndDateType.END_AFTER.getKey())
				taskSchedule.setEndAfterOccurence(yearAfterOccurence.getValue());
			else if(rdgYearlyEndDate.getSelectedIndex() == TaskEndDateType.END_BY_DATE.getKey())
				taskSchedule.setEndByDate(dtbYearlyEnd.getValue());
			
		}
		taskSchedule.setScheduleType(TaskScheduleType.getValue(runTheTask.getSelectedIndex()));
		try{
			clearErrorMessage();
			taskScheduleValidator.validate(taskSchedule);
			composer.setTaskSchedule(taskSchedule);
			getSelf().detach();
		}catch(ValidationException vex){
			log.error(vex.getMessage());
			showErrorMessage(vex);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
	}

	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(lblDailyTotalDay); 
		ErrorMessageUtil.clearErrorMessage(lblDailyEndAfterOccurence);
		ErrorMessageUtil.clearErrorMessage(dtbDailyStart);
		ErrorMessageUtil.clearErrorMessage(dtbDailyEnd);
		ErrorMessageUtil.clearErrorMessage(tmbDailyExecutionTime);
		
		ErrorMessageUtil.clearErrorMessage(lblWeeklyRecurEvery);
		ErrorMessageUtil.clearErrorMessage(dtbWeeklyStart);
		ErrorMessageUtil.clearErrorMessage(lblWeeklyEndAfterOccurnce);
		ErrorMessageUtil.clearErrorMessage(dtbWeeklyEnd);
		ErrorMessageUtil.clearErrorMessage(tmbWeeklyExecutionTime);
		
		ErrorMessageUtil.clearErrorMessage(lblMonthlyMonthNum);
		ErrorMessageUtil.clearErrorMessage(lblMonthlyTotalMonth);
		ErrorMessageUtil.clearErrorMessage(dtbMonthlyStart);
		ErrorMessageUtil.clearErrorMessage(lblMonthlyAfterOccurence);
		ErrorMessageUtil.clearErrorMessage(dtbMonthlyEnd);
		ErrorMessageUtil.clearErrorMessage(tmbMonthlyExecutionTime);
		
		ErrorMessageUtil.clearErrorMessage(lblYearlyRecurEvery);
		ErrorMessageUtil.clearErrorMessage(dtbYearlyStart);
		ErrorMessageUtil.clearErrorMessage(lblYearAfterOccurence);
		ErrorMessageUtil.clearErrorMessage(dtbYearlyEnd);
		ErrorMessageUtil.clearErrorMessage(tmbYearlyExecutionTime);
	}

	private void showErrorMessage(ValidationException vex) {
		Map<String, String> errors = vex.getConstraintViolations();
		if(taskSchedule.getScheduleType().equalsIgnoreCase("DAILY")){
			if(errors.containsKey(TaskScheduleValidator.TOTAL_DAYS)) {
				ErrorMessageUtil.setErrorMessage(lblDailyTotalDay, vex.getMessage(TaskScheduleValidator.TOTAL_DAYS));
			}
			if(errors.containsKey(TaskScheduleValidator.END_AFTER_OCCURENCE)) {
				ErrorMessageUtil.setErrorMessage(lblDailyEndAfterOccurence, vex.getMessage(TaskScheduleValidator.END_AFTER_OCCURENCE));
			}
			if(errors.containsKey(TaskScheduleValidator.START_TIME)) {
				ErrorMessageUtil.setErrorMessage(dtbDailyStart, vex.getMessage(TaskScheduleValidator.START_TIME));
			}
			if(errors.containsKey(TaskScheduleValidator.END_BY_DATE)) {
				ErrorMessageUtil.setErrorMessage(dtbDailyEnd, vex.getMessage(TaskScheduleValidator.END_BY_DATE));
			}
			if(errors.containsKey(TaskScheduleValidator.EXECUTION_TIME)){
				ErrorMessageUtil.setErrorMessage(tmbDailyExecutionTime, vex.getMessage(TaskScheduleValidator.EXECUTION_TIME));
			}
			if(errors.containsKey(TaskScheduleValidator.RECUR_EVERY)) {
				ErrorMessageUtil.setErrorMessage(lblDailyTotalDay, vex.getMessage(TaskScheduleValidator.RECUR_EVERY));
			}
		}else if(taskSchedule.getScheduleType().equalsIgnoreCase("WEEKLY")){
			if(errors.containsKey(TaskScheduleValidator.RECUR_EVERY)) {
				ErrorMessageUtil.setErrorMessage(lblWeeklyRecurEvery, vex.getMessage(TaskScheduleValidator.RECUR_EVERY));
			}
			if(errors.containsKey(TaskScheduleValidator.END_AFTER_OCCURENCE)) {
				ErrorMessageUtil.setErrorMessage(lblWeeklyEndAfterOccurnce, vex.getMessage(TaskScheduleValidator.END_AFTER_OCCURENCE));
			}
			if(errors.containsKey(TaskScheduleValidator.START_TIME)) {
				ErrorMessageUtil.setErrorMessage(dtbWeeklyStart, vex.getMessage(TaskScheduleValidator.START_TIME));
			}
			if(errors.containsKey(TaskScheduleValidator.END_BY_DATE)) {
				ErrorMessageUtil.setErrorMessage(dtbWeeklyEnd, vex.getMessage(TaskScheduleValidator.END_BY_DATE));
			}
			if(errors.containsKey(TaskScheduleValidator.EXECUTION_TIME)){
				ErrorMessageUtil.setErrorMessage(tmbWeeklyExecutionTime, vex.getMessage(TaskScheduleValidator.EXECUTION_TIME));
			}
		}else if(taskSchedule.getScheduleType().equalsIgnoreCase("MONTHLY")){
			if(taskSchedule.getMonthlyPattern().equalsIgnoreCase("DAY")){
				if(errors.containsKey(TaskScheduleValidator.DAY_MONTH)){
					ErrorMessageUtil.setErrorMessage(lblMonthlyMonthNum, vex.getMessage(TaskScheduleValidator.DAY_MONTH));
				}else{
					if(errors.containsKey(TaskScheduleValidator.DAY_NUM)){
						ErrorMessageUtil.setErrorMessage(lblMonthlyMonthNum, vex.getMessage(TaskScheduleValidator.DAY_NUM));
					}
					if(errors.containsKey(TaskScheduleValidator.TOTAL_MONTH)){
						ErrorMessageUtil.setErrorMessage(lblMonthlyMonthNum, vex.getMessage(TaskScheduleValidator.TOTAL_MONTH));
					}
				}
			}else{
				
				if(errors.containsKey(TaskScheduleValidator.TOTAL_MONTH)){
					ErrorMessageUtil.setErrorMessage(lblMonthlyTotalMonth, vex.getMessage(TaskScheduleValidator.TOTAL_MONTH));
				}
			}
			if(errors.containsKey(TaskScheduleValidator.END_AFTER_OCCURENCE)) {
				ErrorMessageUtil.setErrorMessage(lblMonthlyAfterOccurence, vex.getMessage(TaskScheduleValidator.END_AFTER_OCCURENCE));
			}
			if(errors.containsKey(TaskScheduleValidator.START_TIME)) {
				ErrorMessageUtil.setErrorMessage(dtbMonthlyStart, vex.getMessage(TaskScheduleValidator.START_TIME));
			}
			if(errors.containsKey(TaskScheduleValidator.END_BY_DATE)) {
				ErrorMessageUtil.setErrorMessage(dtbMonthlyEnd, vex.getMessage(TaskScheduleValidator.END_BY_DATE));
			}
			if(errors.containsKey(TaskScheduleValidator.EXECUTION_TIME)){
				ErrorMessageUtil.setErrorMessage(tmbMonthlyExecutionTime, vex.getMessage(TaskScheduleValidator.EXECUTION_TIME));
			}
			if(errors.containsKey(TaskScheduleValidator.RECUR_EVERY)) {
				ErrorMessageUtil.setErrorMessage(lblDailyTotalDay, vex.getMessage(TaskScheduleValidator.RECUR_EVERY));
			}
		}else if(taskSchedule.getScheduleType().equalsIgnoreCase("YEARLY")){
			if(errors.containsKey(TaskScheduleValidator.RECUR_EVERY)) {
				ErrorMessageUtil.setErrorMessage(lblYearlyRecurEvery, vex.getMessage(TaskScheduleValidator.RECUR_EVERY));
			}
			if(errors.containsKey(TaskScheduleValidator.END_AFTER_OCCURENCE)) {
				ErrorMessageUtil.setErrorMessage(lblYearAfterOccurence, vex.getMessage(TaskScheduleValidator.END_AFTER_OCCURENCE));
			}
			if(errors.containsKey(TaskScheduleValidator.START_TIME)) {
				ErrorMessageUtil.setErrorMessage(dtbYearlyStart, vex.getMessage(TaskScheduleValidator.START_TIME));
			}
			if(errors.containsKey(TaskScheduleValidator.END_BY_DATE)) {
				ErrorMessageUtil.setErrorMessage(dtbYearlyEnd, vex.getMessage(TaskScheduleValidator.END_BY_DATE));
			}
			if(errors.containsKey(TaskScheduleValidator.EXECUTION_TIME)){
				ErrorMessageUtil.setErrorMessage(tmbYearlyExecutionTime, vex.getMessage(TaskScheduleValidator.EXECUTION_TIME));
			}
		}
		
	}
}
