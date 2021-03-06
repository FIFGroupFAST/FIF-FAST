package co.id.fifgroup.ssoa.domain;

import java.util.Date;

public class StockOpnameUnregAssetImg {
	private Long id;
	private Long stockOpnameUnregAssetId;
	private String imageFileName;
	private String imageFilePath;
	private byte[] imageFile;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;

	public StockOpnameUnregAssetImg() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStockOpnameUnregAssetId() {
		return stockOpnameUnregAssetId;
	}

	public void setStockOpnameUnregAssetId(Long stockOpnameUnregAssetId) {
		this.stockOpnameUnregAssetId = stockOpnameUnregAssetId;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public byte[] getImageFile() {
		return imageFile;
	}

	public void setImageFile(byte[] imageFile) {
		this.imageFile = imageFile;
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

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	
}