package co.id.fifgroup.core.domain;

import java.io.Serializable;
import java.util.Date;

public interface HistoricalObject extends Serializable {
	
	Date getStartDate();
	
	void setStartDate(Date startDate);
	
	Date getEndDate();
	
	void setEndDate(Date endDate);
}
