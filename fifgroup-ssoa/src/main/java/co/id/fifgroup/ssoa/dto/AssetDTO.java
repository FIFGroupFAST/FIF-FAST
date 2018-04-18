package co.id.fifgroup.ssoa.dto;

import co.id.fifgroup.ssoa.domain.Assets;

public class AssetDTO extends Assets  {

	private static final long serialVersionUID = 1L;
	//private List<AssetLabelingDetailDTO> assetLabelingDetailDTO;
	private String userName;
	private String transactionTypeCode;
		
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTransactionTypeCode() {
		return transactionTypeCode;
	}

	public void setTransactionTypeCode(String transactionTypeCode) {
		this.transactionTypeCode = transactionTypeCode;
	}
	
	
}
