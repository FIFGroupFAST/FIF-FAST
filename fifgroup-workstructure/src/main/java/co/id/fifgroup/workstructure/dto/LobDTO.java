package co.id.fifgroup.workstructure.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import co.id.fifgroup.workstructure.domain.Lob;

import co.id.fifgroup.basicsetup.common.History;

public class LobDTO extends Lob implements History {

	private static final long serialVersionUID = 1L;

	private Long branchId;
	private String organizationCode;
	private String organizationName;
	private String jobCode;
	private String jobName;
	private Date effectiveOnDate;
	private String userName;
	private List<LobElementDTO> lobElements;
	private boolean isValidLobElements;

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<LobElementDTO> getLobElements() {
		return lobElements;
	}

	public void setLobElements(List<LobElementDTO> lobElements) {
		this.lobElements = lobElements;
	}

	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}

	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}

	@Override
	public String toString() {
		return getRawData();
	}

	private String getRawData() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String str = "";
		Iterator<LobElementDTO> itr = lobElements.iterator();
		while (itr.hasNext()) {
			LobElementDTO element = itr.next();
			str += sdf.format(getEffectiveStartDate()) + ", "
					+ organizationCode + ", " + jobCode + ", "
					+ element.getProductCode() + ", " + element.getPercentage()
					+ ", " + element.getRowNum();
			if (itr.hasNext())
				str += "#";
		}
		return str;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobCode == null) ? 0 : jobCode.hashCode());
		result = prime
				* result
				+ ((organizationCode == null) ? 0 : organizationCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LobDTO other = (LobDTO) obj;
		if (jobCode == null) {
			if (other.jobCode != null)
				return false;
		} else if (!jobCode.equals(other.jobCode))
			return false;
		if (organizationCode == null) {
			if (other.organizationCode != null)
				return false;
		} else if (!organizationCode.equals(other.organizationCode))
			return false;
		return true;
	}

	public boolean isValidLobElements() {
		return isValidLobElements;
	}

	public void setValidLobElements(boolean isValidLobElements) {
		this.isValidLobElements = isValidLobElements;
	}

}
