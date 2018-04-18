package co.id.fifgroup.ssoa.dto;


import co.id.fifgroup.ssoa.domain.RetirementImg;

public class RetirementImgDTO extends RetirementImg {

	private static final long serialVersionUID = 9207558486244871296L;
	public RetirementDetailDTO retirementDetailDTO;
	public RetirementDetailDTO getRetirementDetailDTO() {
		return retirementDetailDTO;
	}
	public void setRetirementDetailDTO(RetirementDetailDTO retirementDetailDTO) {
		this.retirementDetailDTO = retirementDetailDTO;
	}
	
}