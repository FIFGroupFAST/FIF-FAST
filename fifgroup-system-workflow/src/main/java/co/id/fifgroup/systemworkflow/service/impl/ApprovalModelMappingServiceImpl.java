package co.id.fifgroup.systemworkflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingDetail;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingDetailExample;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingHeader;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingHeaderExample;
import co.id.fifgroup.systemworkflow.domain.LevelMessageMapping;
import co.id.fifgroup.systemworkflow.domain.LevelMessageMappingExample;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMappingDTO;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMappingHeaderDTO;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMessageMappingDTO;
import co.id.fifgroup.systemworkflow.dto.LevelMessageDTO;
import co.id.fifgroup.systemworkflow.service.ApprovalModelMappingService;
import co.id.fifgroup.systemworkflow.util.ApprovalModelListOfValueComposer;
import co.id.fifgroup.systemworkflow.validation.ApprovalModelMappingValidator;

import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.systemworkflow.dao.ApprovalModelMappingDetailMapper;
import co.id.fifgroup.systemworkflow.dao.ApprovalModelMappingHeaderMapper;
import co.id.fifgroup.systemworkflow.dao.ApprovalModelMappingMapper;
import co.id.fifgroup.systemworkflow.dao.LevelMessageMappingMapper;

@Transactional(readOnly=true)
@Service
public class ApprovalModelMappingServiceImpl implements ApprovalModelMappingService {

	@Autowired
	private ApprovalModelMappingHeaderMapper approvalModelMappingHeaderMapper;
	@Autowired
	private ApprovalModelMappingDetailMapper approvalModelMappingDetailMapper;
	@Autowired
	private LevelMessageMappingMapper levelMessageMappingMapper;
	@Autowired
	private ApprovalModelMappingMapper approvalModelMappingMapper;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private ApprovalModelMappingValidator approvalModelMappingValidator;
	
	public List<ApprovalModelMappingHeader> getAllApprovalModelMappingHeader() {
		return approvalModelMappingHeaderMapper.selectByExample(null);
	}
	
	@Override
	public List<ApprovalModelMappingHeaderDTO> getApprovalModelMappingHeaderByCriteria(Long scopeId, TrxType trxType, Long orgHierId, Date effectiveOnDate) {
		Long transactionId = trxType != null ? trxType.getCode() : null;
		return approvalModelMappingMapper.getApprovalModelMappingHeader(scopeId, transactionId, orgHierId, effectiveOnDate);
	}

	@Override
	public List<ApprovalModelMappingDetail> getApprovalModelMappingDetailByHeaderId(
			Long headerId) {
		ApprovalModelMappingDetailExample example = new ApprovalModelMappingDetailExample();
		example.createCriteria().andModelMappingIdEqualTo(headerId);
		example.setOrderByClause("PRIORITY ASC");
		return approvalModelMappingDetailMapper.selectByExampleWithBLOBs(example);
	}
	
	@Override
	public List<LevelMessageMapping> getLevelMessageMappingByDetailId(
			Long detailId) {
		LevelMessageMappingExample example = new LevelMessageMappingExample();
		example.createCriteria().andMappingDetailIdEqualTo(detailId);
		example.setOrderByClause("LEVEL_APPROVAL ASC");
		return levelMessageMappingMapper.selectByExample(example);
	}

	@Override
	public List<ApprovalModelMessageMappingDTO> getApprovalModelMessageMappingDtoByHeaderId(
			Long headerId) {
		List<ApprovalModelMessageMappingDTO> listApprovalMessageMappingDto = new ArrayList<ApprovalModelMessageMappingDTO>();
		List<ApprovalModelMappingDetail> listApprovalMappingDetail = getApprovalModelMappingDetailByHeaderId(headerId);
		
		// detail mapping
		for (ApprovalModelMappingDetail approvalMappingDetail : listApprovalMappingDetail) {
			ApprovalModelMessageMappingDTO approvalModelMessageMappingDto = new ApprovalModelMessageMappingDTO();
			List<LevelMessageDTO> levelMessageDtos = new ArrayList<LevelMessageDTO>();
			List<LevelMessageMapping> listLevelMessageMappings = getLevelMessageMappingByDetailId(approvalMappingDetail.getMappingDetailId());
			
			// level message
			Long levelActual = (long) 0;
			int count = 0;
			LevelMessageDTO levelMessageDto = new LevelMessageDTO();
			List<LevelMessageMapping> newList = new ArrayList<LevelMessageMapping>();
			levelMessageDto.setLevelMessageUUID(UUID.randomUUID());
			levelMessageDto.setLevel(levelActual);
			for (LevelMessageMapping levelMessage : listLevelMessageMappings) {
				if (levelMessage.getLevelApproval() == levelActual) {
					newList.add(levelMessage);
				} else {
					levelMessageDto.setListLevelMessage(newList);
					levelMessageDtos.add(levelMessageDto);
					levelActual++;
					
					levelMessageDto = new LevelMessageDTO();
					newList = new ArrayList<LevelMessageMapping>();
					levelMessageDto.setLevelMessageUUID(UUID.randomUUID());
					levelMessageDto.setLevel(levelActual);
					newList.add(levelMessage);
				}
				count++; 
				if (count == listLevelMessageMappings.size()) {
					levelMessageDto.setListLevelMessage(newList);
					levelMessageDtos.add(levelMessageDto);
				}
			}
			
			approvalModelMessageMappingDto.setModelMappingDetail(approvalMappingDetail);
			approvalModelMessageMappingDto.setListLevelMessage(levelMessageDtos);
			listApprovalMessageMappingDto.add(approvalModelMessageMappingDto);
		}
		return listApprovalMessageMappingDto;
	}

	@Override
	@Transactional(readOnly=false)
	public void saveApprovalModelMapping(
			ApprovalModelMappingDTO approvalModelMappingDto) throws ValidationException {
		approvalModelMappingValidator.validate(approvalModelMappingDto);
		long createdBy = securityServiceImpl.getSecurityProfile().getUserId();
		Date createdDate = new Date();
		if (approvalModelMappingDto.getModelMappingHeader().getModelMappingId() != null) {
			approvalModelMappingDto.getModelMappingHeader().setLastUpdatedBy(createdBy);
			approvalModelMappingDto.getModelMappingHeader().setLastUpdatedDate(createdDate);
			approvalModelMappingHeaderMapper.updateByPrimaryKey(approvalModelMappingDto.getModelMappingHeader());
			deleteApprovalModelMappingDetail(approvalModelMappingDto);
		} else {
			insertApprovalModelMappingHeader(approvalModelMappingDto, createdBy, createdDate);			
		}
		insertApprovalModelMappingDetail(approvalModelMappingDto);
		
	}
	
	@Transactional(readOnly=false)
	public void insertApprovalModelMappingHeader(ApprovalModelMappingDTO approvalModelMappingDto, Long createdBy, Date createdDate) {
		approvalModelMappingDto.getModelMappingHeader().setCreatedBy(createdBy);
		approvalModelMappingDto.getModelMappingHeader().setCreatedDate(createdDate);
		approvalModelMappingDto.getModelMappingHeader().setLastUpdatedBy(createdBy);
		approvalModelMappingDto.getModelMappingHeader().setLastUpdatedDate(createdDate);
		approvalModelMappingHeaderMapper.insert(approvalModelMappingDto.getModelMappingHeader());
	}
	
	@Transactional(readOnly=false)
	public void insertApprovalModelMappingDetail(ApprovalModelMappingDTO approvalModelMappingDto) {
		long priority = (long) 0;
		for (ApprovalModelMessageMappingDTO approvalModelMessageMappingDto : approvalModelMappingDto.getListModelMessageMapping()) {
			approvalModelMessageMappingDto.getModelMappingDetail().setModelMappingId(approvalModelMappingDto.getModelMappingHeader().getModelMappingId());
			approvalModelMessageMappingDto.getModelMappingDetail().setPriority(priority);
			approvalModelMappingDetailMapper.insert(approvalModelMessageMappingDto.getModelMappingDetail());
			for (LevelMessageDTO levelMessageDto : approvalModelMessageMappingDto.getListLevelMessage()) {
				if (!approvalModelMessageMappingDto.getModelMappingDetail().getApprovalModel().equals(ApprovalModelListOfValueComposer.NO_APPROVAL_MODEL_UUID)) {
					for (LevelMessageMapping levelMessageMapping : levelMessageDto.getListLevelMessage()) {
						levelMessageMapping.setMappingDetailId(approvalModelMessageMappingDto.getModelMappingDetail().getMappingDetailId());
						levelMessageMapping.setTransactionId(approvalModelMappingDto.getModelMappingHeader().getTransactionId());
						levelMessageMappingMapper.insert(levelMessageMapping);					
					}					
				}
			}
			priority++;
		}
	}
	
	@Transactional(readOnly=false)
	public void deleteApprovalModelMappingDetail(ApprovalModelMappingDTO approvalModelMappingDto) {
		for (ApprovalModelMessageMappingDTO approvalModelMessageMappingDto : approvalModelMappingDto.getListModelMessageMapping()) {
			approvalModelMappingMapper.deleteLevelMessageMappingByMappingDetailId(approvalModelMessageMappingDto.getModelMappingDetail().getMappingDetailId());
		}
		approvalModelMappingMapper.deleteApprovalModelMappingDetailByMappingId(approvalModelMappingDto.getModelMappingHeader().getModelMappingId());
	}

	@Override
	public ApprovalModelMappingHeader getApprovalModelMappingHeaderByTransactionId(Long transactionId, Long companyId, Date transactionDate) {
		ApprovalModelMappingHeader mappingHeader = new ApprovalModelMappingHeader();
		ApprovalModelMappingHeaderExample example = new ApprovalModelMappingHeaderExample();
		example.createCriteria().andTransactionIdEqualTo(transactionId).andScopeEqualTo(companyId)
			.andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(transactionDate)).andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(transactionDate));
		List<ApprovalModelMappingHeader> listMappingHeader = approvalModelMappingHeaderMapper.selectByExample(example); 
		mappingHeader = listMappingHeader.size() > 0 ? listMappingHeader.get(0) : null;
		if (mappingHeader != null) {
			return mappingHeader;
		} else {
			example = new ApprovalModelMappingHeaderExample();
			//example.createCriteria().andScopeEqualTo(ScopeType.COMMON.getValue());
			example.createCriteria().andTransactionIdEqualTo(transactionId).andScopeEqualTo(ScopeType.COMMON.getValue())
				.andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(transactionDate))
				.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(transactionDate));
			
			listMappingHeader = approvalModelMappingHeaderMapper.selectByExample(example); 
			mappingHeader = listMappingHeader.size() > 0 ? listMappingHeader.get(0) : null;
		}
		return mappingHeader;
	}

	@Override
	public ApprovalModelMappingDetail getApprovalModelMappingDetailByHeaderIdAndPriority(
			Long headerId, Long priority) {
		ApprovalModelMappingDetailExample example = new ApprovalModelMappingDetailExample();
		example.createCriteria().andModelMappingIdEqualTo(headerId).andPriorityEqualTo(priority);
		List<ApprovalModelMappingDetail> list = approvalModelMappingDetailMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int countApprovalModelMappingActiveByScopeAndTransactionId(
			Long scopeId, Long transactionid, Date effectiveStartDate) {
		return approvalModelMappingMapper.countApprovalModelMappingActiveByScopeAndTransactionId(scopeId, transactionid, effectiveStartDate);
	}

	@Override
	public ApprovalModelMappingHeader getApprovalModelMappingActiveByScopeAndTransactionId(
			Long scopeId, Long transactionid, Date effectiveOnDate) {
		ApprovalModelMappingHeaderExample example = new ApprovalModelMappingHeaderExample();
		example.createCriteria().andScopeEqualTo(scopeId).andTransactionIdEqualTo(transactionid)
		.andEffectiveStartDateLessThanOrEqualTo(effectiveOnDate).andEffectiveEndDateGreaterThanOrEqualTo(effectiveOnDate);
		List<ApprovalModelMappingHeader> list = approvalModelMappingHeaderMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void deleteApprovalModelMapping(
			ApprovalModelMappingDTO approvalModelMappingDto) {
		deleteApprovalModelMappingDetail(approvalModelMappingDto);
		approvalModelMappingHeaderMapper.deleteByPrimaryKey(approvalModelMappingDto.getModelMappingHeader().getModelMappingId());
	}


}
