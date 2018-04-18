package co.id.fifgroup.basicsetup.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.common.AbstractHistoricalService;
import co.id.fifgroup.basicsetup.constant.OtherInfoDataType;
import co.id.fifgroup.basicsetup.dao.CompanyContactMapper;
import co.id.fifgroup.basicsetup.dao.CompanyFinder;
import co.id.fifgroup.basicsetup.dao.CompanyMapper;
import co.id.fifgroup.basicsetup.dao.CompanyOtherInfoValueMapper;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyContact;
import co.id.fifgroup.basicsetup.domain.CompanyContactExample;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.domain.CompanyOtherInfoValue;
import co.id.fifgroup.basicsetup.domain.CompanyOtherInfoValueExample;
import co.id.fifgroup.basicsetup.dto.CompanyDTO;
import co.id.fifgroup.basicsetup.dto.LookupDTO;
import co.id.fifgroup.basicsetup.dto.OtherInfoDetailDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.basicsetup.service.OtherInfoService;
import co.id.fifgroup.basicsetup.validation.CompanyValidator;
import co.id.fifgroup.core.service.CompanyServiceAdapter;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;

@Transactional
@Service("companyServiceImpl")
public class CompanyServiceImpl extends AbstractHistoricalService<CompanyDTO> implements CompanyService, CompanyServiceAdapter {
	
	@Autowired
	private CompanyFinder companyFinder;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private CompanyContactMapper companyContactMapper;
	@Autowired
	private CompanyOtherInfoValueMapper companyOtherInfoValueMapper;
	@Autowired
	private CompanyValidator companyValidator;
	@Autowired
	private OtherInfoService otherInfoServiceImpl;
	@Autowired
	private LookupService lookupServiceImpl;
	
	private static final Map<Long, CompanyDTO> cachedCompany = Collections.synchronizedMap(new HashMap<Long, CompanyDTO>()); 
	
	private Long getCompanyId() {
		return companyFinder.getCompanyId();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Company> getCompanyByExample(CompanyExample companyExample) {
		return companyMapper.selectByExample(companyExample);
	}

	@Override
	@Transactional(readOnly=true)
	public List<CompanyDTO> getCompaniesByEffectiveOnDateAndCompanyName(Date effectiveOnDate, String companyName) {
		if(effectiveOnDate== null){
			return companyFinder.getCompaniesByEffectiveOnDateAndCompanyName(null, companyName);
		}else{
			return companyFinder.getCompaniesByEffectiveOnDateAndCompanyName(DateUtils.truncate(effectiveOnDate, Calendar.DATE), companyName);
		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountCompaniesByEffectiveOnDateAndCompanyName(
			Date effectiveOnDate, String companyName) {
		return companyFinder.getCountCompaniesByEffectiveOnDateAndCompanyName(DateUtils.truncate(effectiveOnDate, Calendar.DATE), companyName);
	}

	@Override
	@Transactional(readOnly=true)
	public CompanyDTO getDetailCompany(CompanyDTO companyDto) {
		return companyFinder.getDetailCompany(companyDto);
	}

	@Override
	@Transactional
	public void save(CompanyDTO companyDto, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception {
		companyValidator.setComponentHistorical(dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
		companyValidator.validate(companyDto);
		if(companyDto.isValidContactType()) {
			if(companyDto.getCompanyId() == null) {
				Long companyId = getCompanyId();
				companyDto.setCompanyId(companyId);
				saveHistory(companyDto, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
			} else {
				saveHistory(companyDto, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
			}			
		} else {
			throw new ValidationException();
		}
	}

	@Override
	@Transactional(readOnly=true)
	public Company getCompanyBySequenceNumber(Long companySequenceNumber) {
		return companyMapper.selectByPrimaryKey(companySequenceNumber);
	}

	@Override
	@Transactional
	protected void insertNewHistory(CompanyDTO object) {
		object.setEffectiveStartDate(DateUtils.truncate(object.getEffectiveStartDate(), Calendar.DATE));
		object.setEffectiveEndDate(DateUtils.truncate(object.getEffectiveEndDate(), Calendar.DATE));
		companyMapper.insert(object);
		for(CompanyContact companyContact : object.getCompanyContact()) {
			companyContact.setCompanySeqNum(object.getCompanySeqNum());
			companyContactMapper.insert(companyContact);
		}
		for(CompanyOtherInfoValue companyOtherInfoValue : object.getCompanyOtherInfoValues()) {
			companyOtherInfoValue.setCompanySeqNum(object.getCompanySeqNum());
			companyOtherInfoValueMapper.insert(companyOtherInfoValue);
		}
	}

	@Override
	@Transactional
	protected void endDateCurrentHistory(CompanyDTO object, Date dateTo, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) {
		CompanyExample companyExample = new CompanyExample();
		companyExample.createCriteria().andCompanyIdEqualTo(object.getCompanyId()).andEffectiveStartDateEqualTo(DateUtils.truncate(dateFromBeforeCurrentEdit, Calendar.DATE)).andEffectiveEndDateEqualTo(DateUtils.truncate(dateToBeforeCurrentEdit, Calendar.DATE));
		CompanyDTO companyDtoEndDate = new CompanyDTO();
		companyDtoEndDate.setEffectiveEndDate(DateUtils.truncate(dateTo, Calendar.DATE));
		
		companyMapper.updateByExampleSelective(companyDtoEndDate, companyExample);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean hasFuture(CompanyDTO object) {
		CompanyExample companyExample = new CompanyExample();
		companyExample.createCriteria().andCompanyIdEqualTo(object.getCompanyId()).andEffectiveStartDateGreaterThan(DateUtils.truncate(object.getEffectiveEndDate(), Calendar.DATE));
		List<Company> resultCompany = companyMapper.selectByExample(companyExample);
		if(resultCompany.size() == 1) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public void delete(CompanyDTO companyDto) {
		Calendar calEffectiveEndDateCurrent = Calendar.getInstance();
		calEffectiveEndDateCurrent.setTime(DateUtils.truncate(companyDto.getEffectiveStartDate(), Calendar.DATE));
		calEffectiveEndDateCurrent.add(Calendar.DAY_OF_MONTH, -1);
		
		CompanyExample prevCompanyExample = new CompanyExample();
		prevCompanyExample.createCriteria().andCompanyIdEqualTo(companyDto.getCompanyId()).andEffectiveEndDateEqualTo(calEffectiveEndDateCurrent.getTime());
		
		List<Company> resultCompany = companyMapper.selectByExample(prevCompanyExample);
		
		CompanyContactExample companyContactExample = new CompanyContactExample();
		companyContactExample.createCriteria().andCompanySeqNumEqualTo(companyDto.getCompanySeqNum());

		
		CompanyOtherInfoValueExample companyOtherInfoValueExample = new CompanyOtherInfoValueExample();
		companyOtherInfoValueExample.createCriteria().andCompanySeqNumEqualTo(companyDto.getCompanySeqNum());
		
		CompanyExample companyExample = new CompanyExample();
		companyExample.createCriteria().andCompanyIdEqualTo(companyDto.getCompanyId()).andEffectiveStartDateEqualTo(DateUtils.truncate(companyDto.getEffectiveStartDate(), Calendar.DATE)).andEffectiveEndDateEqualTo(DateUtils.truncate(companyDto.getEffectiveEndDate(), Calendar.DATE));
		
		companyContactMapper.deleteByExample(companyContactExample);
		companyOtherInfoValueMapper.deleteByExample(companyOtherInfoValueExample);
		companyMapper.deleteByExample(companyExample);
		
		if(resultCompany.size() == 1) {
			
			Company prevCompany = resultCompany.get(0);
			CompanyExample updateCurrentCompanyExample = new CompanyExample();
			updateCurrentCompanyExample.createCriteria().andCompanyIdEqualTo(prevCompany.getCompanyId()).andEffectiveStartDateEqualTo(DateUtils.truncate(prevCompany.getEffectiveStartDate(), Calendar.DATE)).andEffectiveEndDateEqualTo(DateUtils.truncate(prevCompany.getEffectiveEndDate(), Calendar.DATE));
			Company company = new Company();
			company.setEffectiveEndDate(DateUtils.truncate(DateUtil.MAX_DATE, Calendar.DATE));

			companyMapper.updateByExampleSelective(company, updateCurrentCompanyExample);
		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public Long getBusinessGroupIdByCompany(Long companyId) {
		return companyFinder.getBusinessGroupIdByCompany(companyId);
	}

	@Override
	@Transactional(readOnly=true)
	public String getOtherInfoValueCompany(Long companyId, Date effectiveOnDate, Long otherInfoDetailId) {
		return companyFinder.getOtherInfoValueCompany(companyId, effectiveOnDate, otherInfoDetailId);
	}

	@Override
	@Transactional(readOnly=true)
	public String getCompanyCodeById(Long id) {
		return companyFinder.getCompanyCodeById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Company getCompanyByIdAndEffectiveDate(Long companyId,
			Date effectiveDate) {
		CompanyExample example = new CompanyExample();
		effectiveDate = DateUtils.truncate(effectiveDate, Calendar.DATE);
		example.or()
			.andCompanyIdEqualTo(companyId)
			.andEffectiveStartDateLessThanOrEqualTo(effectiveDate)
			.andEffectiveEndDateGreaterThanOrEqualTo(effectiveDate);
		List<Company> companies = companyMapper.selectByExample(example);
		return companies.size() > 0 ? companies.get(0) : null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public String getCompanyGlCodeById(Long id) {
		return companyFinder.getCompanyGlCodeById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Long> getCompanyInBusinessGroup(Long companyId) {
		return companyFinder.getCompanyInBusinessGroup(companyId);
	}

	@Override
	@Transactional(readOnly=true)
	public String getOtherInfoCompany(Long companyId, Date effectiveOnDate, String otherInfoCode) {
		CompanyDTO company = companyFinder.getCompanySeqNumAndBusinessGroupId(companyId, DateUtils.truncate(new Date(), Calendar.DATE));
		OtherInfoDetailDTO otherInfoDetailDTO = otherInfoServiceImpl.getOtherInfoDetail("COMPANY", company.getGroupId(), companyId, otherInfoCode);
		if(otherInfoDetailDTO != null) {
			CompanyOtherInfoValueExample companyOtherValueExample = new CompanyOtherInfoValueExample();
			companyOtherValueExample.createCriteria().andCompanySeqNumEqualTo(company.getCompanySeqNum())
			.andOtherInfoDtlIdEqualTo(otherInfoDetailDTO.getOtherInfoDtlId());
			
			List<CompanyOtherInfoValue> results = companyOtherInfoValueMapper.selectByExample(companyOtherValueExample);
			
			if(!results.isEmpty()) {
				
				CompanyOtherInfoValue companyOtherInfoValue = results.get(0);
				
				if(otherInfoDetailDTO.getDataType().equals(OtherInfoDataType.LOOKUP.toString())) {
					LookupDTO lookupDto = lookupServiceImpl.getLookupByLookupId(otherInfoDetailDTO.getLookupId());
					if(lookupDto != null) {
						return lookupServiceImpl.getLookup(lookupDto.getName(), companyOtherInfoValue.getValue(), company.getGroupId(), companyId).getValue().toString();
					}
				} else {
					return companyOtherInfoValue.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public CompanyDTO findCompanyById(Long companyId, Date effectiveDate) {
		if (cachedCompany.containsKey(companyId )) {
			return cachedCompany.get(companyId);
		} 
		cachedCompany.put(companyId , companyFinder.getCompanyById(companyId, effectiveDate));
		return cachedCompany.get(companyId);
	}

	@Override
	public String getCompanyNameById(Long id) {
		return companyFinder.getCompanyNameById(id);
	}

	@Override
	public List<CompanyDTO> findCompanyCommon(CompanyDTO companyDTO, int limit, int offset) {
		return companyFinder.findCompanyCommon(companyDTO, new RowBounds(offset, limit));
	}

	@Override
	public Integer countCompanyCommon(CompanyDTO companyDTO) {
		return companyFinder.countCompanyCommon(companyDTO);
	}

	@Override
	public Company getCompanyByCode(String companyCode) {
		CompanyExample companyExample = new CompanyExample();
		companyExample.createCriteria().andCompanyCodeEqualTo(companyCode)
			.andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(new Date()))
			.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
		List<Company> companies = companyMapper.selectByExample(companyExample);
		return companies != null ? (companies.size() > 0 ? companies.get(0) : null) : null;
	}

}
