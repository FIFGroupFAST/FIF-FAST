package co.id.fifgroup.basicsetup.dao;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.basicsetup.dto.OtherInfoDTO;
import co.id.fifgroup.basicsetup.dto.OtherInfoDetailDTO;

public interface OtherInfoFinder {

	public OtherInfoDTO getOtherInfoDtoByFormNameAndGroupId(@Param("formName") String formName, @Param("groupId") Long groupId);
	public OtherInfoDTO getEnableOtherInfoDtoByFormNameAndGroupId(@Param("formName") String formName, @Param("groupId") Long groupId);
	public OtherInfoDetailDTO getOtherInfoDetail(@Param("formName") String formName, @Param("groupId") Long groupId, @Param("companyId") Long companyId, @Param("detailCode") String detailCode);
	
}
