package co.id.fifgroup.ssoa.domain;


import java.util.Date;
import java.util.List;

import co.id.fifgroup.ssoa.dto.AssetTransferBastDTO;
public class AssetTransferBast {
	private Long transferBastId;
	private Long transferId;
	private String bastDocName;
	private String bastDocPath;
	private String bastDocFileName;
	private byte[] bastDocFile;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;

	public AssetTransferBast() {
		
	}

	public Long getTransferBastId() {
		return transferBastId;
	}

	public void setTransferBastId(Long transferBastId) {
		this.transferBastId = transferBastId;
	}

	public Long getTransferId() {
		return transferId;
	}

	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}

	public String getBastDocName() {
		return bastDocName;
	}

	public void setBastDocName(String bastDocName) {
		this.bastDocName = bastDocName;
	}

	public String getBastDocPath() {
		return bastDocPath;
	}

	public void setBastDocPath(String bastDocPath) {
		this.bastDocPath = bastDocPath;
	}

	public String getBastDocFileName() {
		return bastDocFileName;
	}

	public void setBastDocFileName(String bastDocFileName) {
		this.bastDocFileName = bastDocFileName;
	}

	public byte[] getBastDocFile() {
		return bastDocFile;
	}

	public void setBastDocFile(byte[] bastDocFile) {
		this.bastDocFile = bastDocFile;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	

	
}
