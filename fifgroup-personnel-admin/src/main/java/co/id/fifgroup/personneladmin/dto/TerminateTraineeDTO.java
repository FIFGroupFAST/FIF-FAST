package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.Date;

public class TerminateTraineeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date terminationDate;

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

}
