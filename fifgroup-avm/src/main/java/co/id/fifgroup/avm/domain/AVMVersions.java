package co.id.fifgroup.avm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class AVMVersions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int avmVersionId;
	private int versionNumber;
	private Date effectiveStartDate;
	private Date effectiveEndDate;
	private UUID avmId;

	public int getAvmVersionId() {
		return avmVersionId;
	}

	public void setAvmVersionId(int avmVersionId) {
		this.avmVersionId = avmVersionId;
	}

	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public UUID getAvmId() {
		return avmId;
	}

	public void setAvmId(UUID avmId) {
		this.avmId = avmId;
	}

}
