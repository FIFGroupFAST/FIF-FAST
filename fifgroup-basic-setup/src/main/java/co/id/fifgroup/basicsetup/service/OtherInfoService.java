package co.id.fifgroup.basicsetup.service;

import co.id.fifgroup.basicsetup.domain.OtherInfoHeaderExample;
import co.id.fifgroup.basicsetup.dto.OtherInfoDTO;
import co.id.fifgroup.basicsetup.dto.OtherInfoDetailDTO;


public interface OtherInfoService {

	public void save(OtherInfoDTO otherInfoDto) throws Exception;
	public OtherInfoDTO getOtherInfoDtoByFormNameAndGroupId(String formName, Long groupId);
	public OtherInfoDTO getEnableOtherInfoDtoByFormNameAndGroupId(String formName, Long groupId);
	public int getCountOtherInfoHeaderByExample(OtherInfoHeaderExample otherInfoHeaderExample);
	public OtherInfoDetailDTO getOtherInfoDetail(String formName, Long groupId, Long companyId, String detailCode);
	
}
