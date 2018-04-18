package co.id.fifgroup.basicsetup.common;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import co.id.fifgroup.core.util.DateUtil;

public abstract class AbstractHistoricalService<T extends History> implements HistoricalService<T> {

	protected abstract void insertNewHistory(T object);
	protected abstract void endDateCurrentHistory(T object, Date dateTo, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit);
	
	protected void saveHistory(T object, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) {
		Date dateFromObject = null;
		Date dateFromObjectBeforeUpdate = null;
		if(dateFromBeforeCurrentEdit == null) {
			insertNewHistory(object);
		} else {
			dateFromObject = DateUtils.truncate(object.getEffectiveStartDate(), Calendar.DATE);
			dateFromObjectBeforeUpdate = DateUtils.truncate(dateFromBeforeCurrentEdit, Calendar.DATE);
			if(dateFromObject.compareTo(dateFromObjectBeforeUpdate) == 0) {
				if(dateToBeforeCurrentEdit == null) dateToBeforeCurrentEdit = DateUtil.MAX_DATE;
				endDateCurrentHistory(object, object.getEffectiveEndDate(), dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
			} else if(dateFromObject.compareTo(dateFromObjectBeforeUpdate) > 0) {
				Calendar calCurrentDateTo = Calendar.getInstance();
				calCurrentDateTo.setTime(object.getEffectiveStartDate());
				calCurrentDateTo.add(Calendar.DAY_OF_MONTH, -1);
				endDateCurrentHistory(object, calCurrentDateTo.getTime(), dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
				insertNewHistory(object);
			}
		}
	}

	@Override
	public boolean isCurrent(T object) {
		Date dateFromObject = DateUtils.truncate(object.getEffectiveStartDate(), Calendar.DATE);
		Date dateToObject = DateUtils.truncate(object.getEffectiveEndDate(), Calendar.DATE);
		Date currentDate = DateUtils.truncate(new Date(), Calendar.DATE);
		if(dateFromObject == null || dateToObject == null || currentDate == null) return false;
		if((dateFromObject.compareTo(currentDate) == 0 || dateFromObject.compareTo(currentDate) < 0) && (dateToObject.compareTo(currentDate) == 0 || dateToObject.compareTo(currentDate) > 0)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isFuture(T object) {
		Date dateObject = DateUtils.truncate(object.getEffectiveStartDate(), Calendar.DATE);
		Date currentDate = DateUtils.truncate(new Date(), Calendar.DATE);
		if(dateObject == null || currentDate == null) return false;
		return dateObject.compareTo(currentDate) > 0;
	}
	
	@Override
	public boolean isPastHistory(T object) {
		Date dateObject = DateUtils.truncate(object.getEffectiveEndDate(), Calendar.DATE);
		Date currentDate = DateUtils.truncate(new Date(), Calendar.DATE);
		if(dateObject == null || currentDate == null) return false;
		return currentDate.compareTo(dateObject) > 0;
	}
}
