package co.id.fifgroup.systemadmin.dto;

import co.id.fifgroup.systemadmin.domain.UserAccessRecord;

public class UserAccessRecordDTO extends UserAccessRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 94323474450759640L;
	
	int numberOfAccess;

	public int getNumberOfAccess() {
		return numberOfAccess;
	}

	public void setNumberOfAccess(int numberOfAccess) {
		this.numberOfAccess = numberOfAccess;
	}
	

}
