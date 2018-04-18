package co.id.fifgroup.basicsetup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.OtherInfoDetailMapper;
import co.id.fifgroup.basicsetup.dao.OtherInfoFinder;
import co.id.fifgroup.basicsetup.dao.OtherInfoHeaderMapper;
import co.id.fifgroup.basicsetup.domain.OtherInfoHeaderExample;
import co.id.fifgroup.basicsetup.dto.OtherInfoDTO;
import co.id.fifgroup.basicsetup.dto.OtherInfoDetailDTO;
import co.id.fifgroup.basicsetup.service.OtherInfoService;
import co.id.fifgroup.basicsetup.validation.OtherInfoValidator;
import co.id.fifgroup.core.service.OtherInfoServiceAdapter;
import co.id.fifgroup.core.validation.ValidationException;

@Transactional
@Service
public class OtherInfoServiceImpl implements OtherInfoService, OtherInfoServiceAdapter {
	
	@Autowired
	private OtherInfoHeaderMapper otherInfoHeaderMapper;
	@Autowired
	private OtherInfoDetailMapper otherInfoDetailMapper;
	@Autowired
	private OtherInfoFinder otherInfoFinder;
	@Autowired
	private OtherInfoValidator otherInfoValidator;
	
	@Override
	@Transactional
	public void save(OtherInfoDTO otherInfoDto) throws Exception {
		otherInfoValidator.validate(otherInfoDto);
		if(otherInfoDto.isValidOtherInfo()) {
			if(otherInfoDto.getOtherInfoHdrId() == null) {
				insertOtherInfo(otherInfoDto);
			} else {
				updateOtherInfo(otherInfoDto);
			}			
		} else {
			throw new ValidationException();
		}
	}
	
	@Transactional
	private void insertOtherInfo(OtherInfoDTO otherInfoDto) {
		otherInfoHeaderMapper.insert(otherInfoDto);
		for(OtherInfoDetailDTO otherInfoDetailDto : otherInfoDto.getOtherInfoDetail()) {
			otherInfoDetailDto.setOtherInfoHdrId(otherInfoDto.getOtherInfoHdrId());
			otherInfoDetailMapper.insert(otherInfoDetailDto);
		}
	}
	
	@Transactional
	private void updateOtherInfo(OtherInfoDTO otherInfoDto) {
		otherInfoHeaderMapper.updateByPrimaryKey(otherInfoDto);
		for(OtherInfoDetailDTO otherInfoDetailDto : otherInfoDto.getOtherInfoDetail()) {
			if(otherInfoDetailDto.getOtherInfoDtlId() != null) {
				otherInfoDetailMapper.updateByPrimaryKey(otherInfoDetailDto);
			} else {
				otherInfoDetailDto.setOtherInfoHdrId(otherInfoDto.getOtherInfoHdrId());
				otherInfoDetailMapper.insert(otherInfoDetailDto);
			}
		}
	}

	@Override
	@Transactional(readOnly=true)
	public OtherInfoDTO getOtherInfoDtoByFormNameAndGroupId(String formName, Long groupId) {
		return otherInfoFinder.getOtherInfoDtoByFormNameAndGroupId(formName, groupId);
	}
	
	@Override
	@Transactional(readOnly=true)
	public OtherInfoDTO getEnableOtherInfoDtoByFormNameAndGroupId(String formName, Long groupId) {
		return otherInfoFinder.getEnableOtherInfoDtoByFormNameAndGroupId(formName, groupId);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountOtherInfoHeaderByExample(
			OtherInfoHeaderExample otherInfoHeaderExample) {
		return otherInfoHeaderMapper.countByExample(otherInfoHeaderExample);
	}

	@Override
	@Transactional(readOnly=true)
	public OtherInfoDetailDTO getOtherInfoDetail(String formName, Long groupId, Long companyId, String detailCode) {
		return otherInfoFinder.getOtherInfoDetail(formName, groupId, companyId, detailCode);
	}

	@Override
	public Long getOtherInfoDetailId(String formName, Long groupId,
			Long companyId, String detailCode) {
		OtherInfoDetailDTO otherInfoDetail = otherInfoFinder.getOtherInfoDetail(formName, groupId, companyId, detailCode);
		return otherInfoDetail != null ? otherInfoDetail.getOtherInfoDtlId() : null;
	}

	
	
}
