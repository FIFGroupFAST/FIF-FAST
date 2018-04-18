package co.id.fifgroup.basicsetup.service.impl;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.basicsetup.constant.LookupType;
import co.id.fifgroup.basicsetup.constant.SQLStatement;
import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.dao.LookupDependentMapper;
import co.id.fifgroup.basicsetup.dao.LookupDynamicFinderMapper;
import co.id.fifgroup.basicsetup.dao.LookupDynamicMapper;
import co.id.fifgroup.basicsetup.dao.LookupFinderMapper;
import co.id.fifgroup.basicsetup.dao.LookupHeaderMapper;
import co.id.fifgroup.basicsetup.domain.LookupDependent;
import co.id.fifgroup.basicsetup.domain.LookupDependentExample;
import co.id.fifgroup.basicsetup.domain.LookupDynamic;
import co.id.fifgroup.basicsetup.domain.LookupDynamicExample;
import co.id.fifgroup.basicsetup.domain.LookupHeader;
import co.id.fifgroup.basicsetup.domain.LookupHeaderExample;
import co.id.fifgroup.basicsetup.dto.LookupDTO;
import co.id.fifgroup.basicsetup.dto.LookupDependentDetailDTO;
import co.id.fifgroup.basicsetup.dto.LookupDynamicDTO;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.basicsetup.validation.LookupValidator;
import co.id.fifgroup.core.service.LookupServiceAdapter;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.validation.ValidationException;

@Transactional
@Service
public class LookupServiceImpl implements LookupService, LookupServiceAdapter {

	@Autowired
	private LookupHeaderMapper lookupHeaderMapper;
	@Autowired
	private LookupDependentMapper lookupDependentMapper;
	@Autowired
	private LookupDynamicMapper lookupDynamicMapper;
	@Autowired
	private LookupDynamicFinderMapper lookupDynamicFinder;
	@Autowired
	private LookupFinderMapper lookupFinderMapper;
	@Autowired
	private LookupValidator lookupValidator;
	
	@Transactional(readOnly=true)
	private LookupHeader getLookupHeader(String lookupName, Long groupId) {
		LookupHeaderExample lookupHeaderExample = new LookupHeaderExample();
		LookupHeaderExample.Criteria criteriaLookupHeader = lookupHeaderExample.createCriteria();
		criteriaLookupHeader.andNameEqualTo(lookupName);
		criteriaLookupHeader.andGroupIdEqualTo(groupId);
		List<LookupHeader> resultLookupHeader = lookupHeaderMapper.selectByExample(lookupHeaderExample);
		if(resultLookupHeader.size() == 1) {
			return resultLookupHeader.get(0);
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	private LookupDynamic getLookupDynamic(Long lookupId, Long scope) {
		LookupDynamicExample lookupDynamicExample = new LookupDynamicExample();
		LookupDynamicExample.Criteria criteriaLookupDynamic = lookupDynamicExample.createCriteria();
		criteriaLookupDynamic.andLookupIdEqualTo(lookupId);
		criteriaLookupDynamic.andCompanyScopeEqualTo(scope);
		List<LookupDynamic> resultLookupDynamic = lookupDynamicMapper.selectByExample(lookupDynamicExample);
		if(resultLookupDynamic.size() == 1) {
			return resultLookupDynamic.get(0);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<KeyValue> getLookups(String lookupName, Long groupId, Long scope) {
		LookupHeader lookupHeader = getLookupHeader(lookupName, groupId);
		if(lookupHeader != null) {
			if(lookupHeader.getLookupType().equals(LookupType.DEPENDENT.toString())) {
				return getLookupsDependent(lookupHeader, scope, true);
			} else if(lookupHeader.getLookupType().equals(LookupType.DYNAMIC.toString())) {
				return getLookupsDynamic(lookupHeader, scope);
			}
			throw new RuntimeException(Labels.getLabel("bse.err.lookupTypeDoesNotExist"));
		}
		throw new RuntimeException(Labels.getLabel("bse.err.lookupNameDoesNotExist"));
	}

	@Override
	@Transactional(readOnly=true)
	public KeyValue getLookup(String lookupName, String key, Long groupId, Long scope) {
		if(null == key)
			return null;
		LookupHeader lookupHeader = getLookupHeader(lookupName, groupId);
		if(lookupHeader != null) {
			if(lookupHeader.getLookupType().equals(LookupType.DEPENDENT.toString())) {
				LookupDependentExample lookupDependentExample = new LookupDependentExample();
				LookupDependentExample.Criteria criteriaLookupDependent = lookupDependentExample.createCriteria();
				criteriaLookupDependent.andLookupIdEqualTo(lookupHeader.getLookupId());
				criteriaLookupDependent.andDetailCodeEqualTo(key);
				List<LookupDependent> resultLookupDependent = lookupDependentMapper.selectByExample(lookupDependentExample);
				if(resultLookupDependent.size() == 1) {
					LookupDependent lookupDependent = resultLookupDependent.get(0);
					KeyValue keyValueLookupDependent = new KeyValue();
					keyValueLookupDependent.setKey(lookupDependent.getDetailCode());
					keyValueLookupDependent.setValue(lookupDependent.getMeaning());
					keyValueLookupDependent.setDescription(lookupDependent.getDescription());
					return keyValueLookupDependent;
				} else {
					return null;
				}
			} else if(lookupHeader.getLookupType().equals(LookupType.DYNAMIC.toString())) {
				LookupDynamic lookupDynamicCompany = getLookupDynamic(lookupHeader.getLookupId(), scope);
				if(lookupDynamicCompany != null) {
					KeyValue keyValueLookupDynamicCompany = lookupDynamicFinder.getLookupDynamicWithKey(generateSqlLookupDynamic(lookupDynamicCompany), key);
					if(keyValueLookupDynamicCompany != null) {
						return keyValueLookupDynamicCompany;
					}
					return null;
				} else {
					LookupDynamic lookupDynamicCommon = getLookupDynamic(lookupHeader.getLookupId(), ScopeType.COMMON.getValue());
					if(lookupDynamicCommon != null) {
						KeyValue keyValueLookupDynamicCommon = lookupDynamicFinder.getLookupDynamicWithKey(generateSqlLookupDynamic(lookupDynamicCommon), key);
						if(keyValueLookupDynamicCommon != null) {
							return keyValueLookupDynamicCommon;
						}
						return null;
					} else {
						return null;
					}
				}
			}
			throw new RuntimeException(Labels.getLabel("bse.err.lookupTypeDoesNotExist"));
		}
		throw new RuntimeException(Labels.getLabel("bse.err.lookupNameDoesNotExist"));
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<KeyValue> getLookups(String lookupName, Long groupId, Long scope, boolean active) {
		LookupHeader lookupHeader = getLookupHeader(lookupName, groupId);
		if(lookupHeader != null) {
			if(lookupHeader.getLookupType().equals(LookupType.DEPENDENT.toString())) {
				return getLookupsDependent(lookupHeader, scope, active);
			} else if(lookupHeader.getLookupType().equals(LookupType.DYNAMIC.toString())) {
				return getLookupsDynamic(lookupHeader, scope);
			}
			throw new RuntimeException(Labels.getLabel("bse.err.lookupTypeDoesNotExist"));
		}
		throw new RuntimeException(Labels.getLabel("bse.err.lookupNameDoesNotExist"));
	}

	@Transactional(readOnly=true)
	private List<KeyValue> getLookupsDependent(LookupHeader lookupHeader, Long scope, boolean active) {
		LookupDependentExample lookupDependentExample = new LookupDependentExample();
		LookupDependentExample.Criteria criteriaAndGroup = lookupDependentExample.createCriteria();
		criteriaAndGroup.andLookupIdEqualTo(lookupHeader.getLookupId());
		if(active) {
			Date currentDate = DateUtils.truncate(new Date(), Calendar.DATE);
			criteriaAndGroup.andDateFromLessThanOrEqualTo(currentDate);
			criteriaAndGroup.andDateToGreaterThanOrEqualTo(currentDate);
		}
		
		List<Long> scopeField = new ArrayList<Long>();
		scopeField.add(ScopeType.COMMON.getValue());
		if(scope != null) {
			scopeField.add(scope);
		}
		criteriaAndGroup.andCompanyScopeIn(scopeField);
		lookupDependentExample.setOrderByClause("SEQ_NUMBER ASC, DESCRIPTION ASC");
		List<LookupDependent> resultLookupDependent = lookupDependentMapper.selectByExample(lookupDependentExample);
		List<KeyValue> resultKeyValue = new ArrayList<KeyValue>();
		for(LookupDependent lookupDependent : resultLookupDependent) {
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(lookupDependent.getDetailCode());
			keyValue.setValue(lookupDependent.getMeaning());
			keyValue.setDescription(lookupDependent.getDescription());
			resultKeyValue.add(keyValue);
		}
		return resultKeyValue;
	}
	
	@Transactional(readOnly=true)
	private List<KeyValue> getLookupsDynamic(LookupHeader lookupHeader, Long scope) {
		LookupDynamic lookupDynamicCompany = getLookupDynamic(lookupHeader.getLookupId(), scope);
		if(lookupDynamicCompany != null) {
			return lookupDynamicFinder.getLookupDynamic(generateSqlLookupDynamic(lookupDynamicCompany));
		} else {
			LookupDynamic lookupDynamicCommon = getLookupDynamic(lookupHeader.getLookupId(), ScopeType.COMMON.getValue());
			if(lookupDynamicCommon != null) {
				return lookupDynamicFinder.getLookupDynamic(generateSqlLookupDynamic(lookupDynamicCommon));
			} else {
				return null;
			}
		}
	}
	
	private String generateSqlLookupDynamic(LookupDynamic lookupDynamic) {
		String sql = SQLStatement.SELECT.getValue() + " " + lookupDynamic.getSelectStatement() + " " + SQLStatement.FROM + " " + lookupDynamic.getFromStatement();
		if(lookupDynamic.getWhereStatement() != null && !lookupDynamic.getWhereStatement().equals("")) {
			sql += " " + SQLStatement.WHERE.getValue() + " " + lookupDynamic.getWhereStatement();
		}
		if(lookupDynamic.getOrderBy() != null && !lookupDynamic.getOrderBy().equals("")) {
			sql += " " + SQLStatement.ORDER_BY.getValue() + " " + lookupDynamic.getOrderBy();
		}
		return sql;
	}

	@Override
	@Transactional
	public void save(LookupDTO lookupDto) throws Exception {
		lookupValidator.validate(lookupDto);
		if(lookupDto.getLookupType().equals(LookupType.DEPENDENT.toString())) {
			if(lookupDto.isValidLookupDependent()) {
				if(lookupDto.getLookupId() == null) {
					insertLookupDependent(lookupDto);
				} else {
					updateLookupDependent(lookupDto);
				}				
			} else {
				throw new ValidationException();
			}
		} else if(lookupDto.getLookupType().equals(LookupType.DYNAMIC.toString())) {
			if(lookupDto.getLookupId() == null) {
				insertLookupDynamic(lookupDto);
			} else {
				updateLookupDynamic(lookupDto);
			}
		}
	}
	
	@Transactional
	private void insertLookupDependent(LookupDTO lookupDto) {
		lookupHeaderMapper.insert(lookupDto);
		for(LookupDependentDetailDTO lookupDependentDetailDto : lookupDto.getLookupDependentDetailDtos()) {
			lookupDependentDetailDto.setLookupId(lookupDto.getLookupId());
			lookupDependentMapper.insert(lookupDependentDetailDto);
		}
	}
	
	@Transactional
	private void updateLookupDependent(LookupDTO lookupDto) {
		lookupHeaderMapper.updateByPrimaryKey(lookupDto);
		for(LookupDependentDetailDTO lookupDependentDetailDto : lookupDto.getLookupDependentDetailDtos()) {
			if(lookupDependentDetailDto.getLookupId() != null) {
				lookupDependentMapper.updateByPrimaryKey(lookupDependentDetailDto);
			} else {
				lookupDependentDetailDto.setLookupId(lookupDto.getLookupId());
				lookupDependentMapper.insert(lookupDependentDetailDto);
			}
		}
	}
	
	@Transactional
	private void insertLookupDynamic(LookupDTO lookupDto) {
		lookupHeaderMapper.insert(lookupDto);
		for(LookupDynamicDTO lookupDynamicDto : lookupDto.getLookupDynamicDtos()) {
			lookupDynamicDto.setLookupId(lookupDto.getLookupId());
			lookupDynamicMapper.insert(lookupDynamicDto);
		}
	}
	
	@Transactional
	private void updateLookupDynamic(LookupDTO lookupDto) {
		lookupHeaderMapper.updateByPrimaryKey(lookupDto);
		for(LookupDynamicDTO lookupDynamicDto : lookupDto.getLookupDynamicDtos()) {
			if(lookupDynamicDto.getLookupId() != null) {
				lookupDynamicMapper.updateByPrimaryKey(lookupDynamicDto);
			} else {
				lookupDynamicDto.setLookupId(lookupDto.getLookupId());
				lookupDynamicMapper.insert(lookupDynamicDto);
			}
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<LookupHeader> getLookupHeaderDependent() {
		LookupHeaderExample lookupHeaderExample = new LookupHeaderExample();
		lookupHeaderExample.createCriteria().andLookupTypeEqualTo(LookupType.DEPENDENT.toString());
		return lookupHeaderMapper.selectByExample(lookupHeaderExample);
	}

	@Override
	@Transactional(readOnly=true)
	public List<KeyValue> getLookupValues(String searchCriteria, Long groupId,
			String lookupName, Long scope, String parentDetailLookup,
			boolean active, int limit, int offset) {
		LookupHeader lookupHeader = getLookupHeader(lookupName, groupId);
		if(lookupHeader != null) {
			if(lookupHeader.getLookupType().equals(LookupType.DEPENDENT.toString())) {
				return getLookupsDependentWithSearchCriteria(lookupHeader, searchCriteria, scope, parentDetailLookup, active, new RowBounds(offset, limit));
			} else if(lookupHeader.getLookupType().equals(LookupType.DYNAMIC.toString())) {
				return getLookupsDynamicWithSearchCriteria(lookupHeader, searchCriteria, scope, new RowBounds(offset, limit));
			}
			return null;
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	private LookupDependentExample getLookupDependentExampleWithSearchCriteria(Long lookupId, String searchCriteria, String parentDetailLookup, boolean active, Long scope) {
		LookupDependentExample lookupDependentExample = new LookupDependentExample();
		LookupDependentExample.Criteria criteriaAndGroup = lookupDependentExample.createCriteria();
		criteriaAndGroup.andLookupIdEqualTo(lookupId);
		criteriaAndGroup.andDescriptionLikeInsensitive(searchCriteria);
		if(parentDetailLookup != null) {
			criteriaAndGroup.andParentDetailCodeEqualTo(parentDetailLookup);
		}
		if(active) {
			Date currentDate = DateUtils.truncate(new Date(), Calendar.DATE);
			criteriaAndGroup.andDateFromLessThanOrEqualTo(currentDate);
			criteriaAndGroup.andDateToGreaterThanOrEqualTo(currentDate);
		}
		lookupDependentExample.setOrderByClause("SEQ_NUMBER ASC, DESCRIPTION ASC");
		List<Long> scopeField = new ArrayList<Long>();
		scopeField.add(ScopeType.COMMON.getValue());
		if(scope != null) {
			scopeField.add(scope);
		}
		criteriaAndGroup.andCompanyScopeIn(scopeField);
		return lookupDependentExample;
	}
	
	
	
	@Transactional(readOnly=true)
	private List<KeyValue> getLookupsDependentWithSearchCriteria(LookupHeader lookupHeader, String searchCriteria, Long scope, String parentDetailLookup, boolean active, RowBounds rowBounds) {
		List<LookupDependent> resultLookupDependent = lookupDependentMapper.selectByExampleWithRowbounds(getLookupDependentExampleWithSearchCriteria(lookupHeader.getLookupId(), searchCriteria, parentDetailLookup, active, scope), rowBounds);
		List<KeyValue> resultKeyValue = new ArrayList<KeyValue>();
		for(LookupDependent lookupDependent : resultLookupDependent) {
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(lookupDependent.getDetailCode());
			keyValue.setValue(lookupDependent.getMeaning());
			keyValue.setDescription(lookupDependent.getDescription());
			resultKeyValue.add(keyValue);
		}
		return resultKeyValue;
	}

	@Transactional(readOnly=true)
	private List<KeyValue> getLookupsDynamicWithSearchCriteria(LookupHeader lookupHeader, String searchCriteria, Long scope, RowBounds rowBounds) {
		LookupDynamic lookupDynamicCompany = getLookupDynamic(lookupHeader.getLookupId(), scope);
		if(lookupDynamicCompany != null) {
			return lookupDynamicFinder.getLookupDynamicWithSearchCriteria(generateSqlLookupDynamic(lookupDynamicCompany), searchCriteria, rowBounds);
		} else {
			LookupDynamic lookupDynamicCommon = getLookupDynamic(lookupHeader.getLookupId(), ScopeType.COMMON.getValue());
			if(lookupDynamicCommon != null) {
				return lookupDynamicFinder.getLookupDynamicWithSearchCriteria(generateSqlLookupDynamic(lookupDynamicCommon), searchCriteria, rowBounds);
			} else {
				return null;
			}
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public int getCountLookupValues(String searchCriteria, Long groupId,
			String lookupName, Long scope, String parentDetailLookup, boolean active) {
		LookupHeader lookupHeader = getLookupHeader(lookupName, groupId);
		if(lookupHeader != null) {
			if(lookupHeader.getLookupType().equals(LookupType.DEPENDENT.toString())) {
				return getCountLookupsDependentWithSearchCriteria(lookupHeader, searchCriteria, scope, parentDetailLookup, active);
			} else if(lookupHeader.getLookupType().equals(LookupType.DYNAMIC.toString())) {
				LookupDynamic lookupDynamicCompany = getLookupDynamic(lookupHeader.getLookupId(), scope);
				if(lookupDynamicCompany != null) {
					return getCountLookupDynamicWithSearchCriteria(generateSqlLookupDynamic(lookupDynamicCompany), searchCriteria);
				} else {
					LookupDynamic lookupDynamicCommon = getLookupDynamic(lookupHeader.getLookupId(), ScopeType.COMMON.getValue());
					if(lookupDynamicCommon != null) {
						return getCountLookupDynamicWithSearchCriteria(generateSqlLookupDynamic(lookupDynamicCommon), searchCriteria);
					} else {
						return 0;
					}
				}
			}
			return 0;
		}
		return 0;
	}
	
	@Transactional(readOnly=true)
	private int getCountLookupsDependentWithSearchCriteria(LookupHeader lookupHeader, String searchCriteria, Long scope, String parentDetailLookup, boolean active) {
		return lookupDependentMapper.countByExample(getLookupDependentExampleWithSearchCriteria(lookupHeader.getLookupId(), searchCriteria, parentDetailLookup, active, scope));
	}

	@Transactional(readOnly=true)
	private int getCountLookupDynamicWithSearchCriteria(String sql, String searchCriteria) {
		return lookupDynamicFinder.getCountLookupDynamicWithSearchCriteria(sql, searchCriteria);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<LookupDTO> getLookupHeaderDtoByLookupNameAndDescriptionAndType(
			String lookupName, String description, LookupType lookupType, Long groupId) {
		return lookupFinderMapper.getLookupHeaderDtoByLookupNameAndDescriptionAndType(lookupName, description, lookupType, groupId);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountLookupHeaderDtoByLookupNameAndDescriptionAndType(
			String lookupName, String description, LookupType lookupType, Long groupId) {
		return lookupFinderMapper.getCountLookupHeaderDtoByLookupNameAndDescriptionAndType(lookupName, description, lookupType, groupId);
	}
	
	@Override
	@Transactional(readOnly=true)
	public LookupDTO getLookupByLookupId(Long lookupId) {
		LookupHeader lookupHeader = lookupHeaderMapper.selectByPrimaryKey(lookupId);
		if(lookupHeader.getLookupType().equals(LookupType.DEPENDENT.toString())) {
			return lookupFinderMapper.getLookupDependentByLookupId(lookupId);			
		} else {
			return lookupFinderMapper.getLookupDynamicByLookupId(lookupId);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<LookupHeader> getLookupHeaderByExample(
			LookupHeaderExample lookupHeaderExample, int limit, int offset) {
		return lookupHeaderMapper.selectByExampleWithRowbounds(lookupHeaderExample, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountLookupHeaderByExample(
			LookupHeaderExample lookupHeaderExample) {
		return lookupHeaderMapper.countByExample(lookupHeaderExample);
	}

	@Override
	@Transactional(readOnly=true)
	public List<LookupHeader> getLookupHeaderByExample(
			LookupHeaderExample lookupHeaderExample) {
		List<LookupHeader> result = lookupHeaderMapper.selectByExample(lookupHeaderExample);
		if(result.size() > 0) {
			return result;
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public void validateDynamicSql(LookupDynamic lookupDynamic) throws SQLSyntaxErrorException {
		lookupDynamicFinder.getLookupDynamic(generateSqlLookupDynamic(lookupDynamic));
	}

	@Override
	@Transactional(readOnly=true)
	public LookupHeader getLookupHeaderByPrimaryKey(Long id) {
		return lookupHeaderMapper.selectByPrimaryKey(id);
	}

	@Override
	public KeyValue getLookupCaseInsensitive(String lookupName, String key,
			Long groupId, Long scope) {
		LookupHeader lookupHeader = getLookupHeader(lookupName, groupId);
		if(lookupHeader != null) {
			if(lookupHeader.getLookupType().equals(LookupType.DEPENDENT.toString())) {
				return lookupFinderMapper.getLookupDependentWithKeyCaseInsensitive(lookupHeader.getLookupId(), key);
			} else if(lookupHeader.getLookupType().equals(LookupType.DYNAMIC.toString())) {
				LookupDynamic lookupDynamicCompany = getLookupDynamic(lookupHeader.getLookupId(), scope);
				if(lookupDynamicCompany != null) {
					KeyValue keyValueLookupDynamicCompany = lookupDynamicFinder.getLookupDynamicWithKeyCaseInsensitive(generateSqlLookupDynamic(lookupDynamicCompany), key);
					if(keyValueLookupDynamicCompany != null) {
						return keyValueLookupDynamicCompany;
					}
					return null;
				} else {
					LookupDynamic lookupDynamicCommon = getLookupDynamic(lookupHeader.getLookupId(), ScopeType.COMMON.getValue());
					if(lookupDynamicCommon != null) {
						KeyValue keyValueLookupDynamicCommon = lookupDynamicFinder.getLookupDynamicWithKeyCaseInsensitive(generateSqlLookupDynamic(lookupDynamicCommon), key);
						if(keyValueLookupDynamicCommon != null) {
							return keyValueLookupDynamicCommon;
						}
						return null;
					} else {
						return null;
					}
				}
			}
			throw new RuntimeException(Labels.getLabel("bse.err.lookupTypeDoesNotExist"));
		}
		throw new RuntimeException(Labels.getLabel("bse.err.lookupNameDoesNotExist"));
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountLookup(String lookupName, Long groupId, Long scope) {
		LookupHeaderExample lookupHeaderExample = new LookupHeaderExample();
		lookupHeaderExample
		.createCriteria()
		.andNameEqualTo(lookupName)
		.andGroupIdEqualTo(groupId);
		List<LookupHeader> resultLookupHeader = lookupHeaderMapper.selectByExample(lookupHeaderExample);
		if(!resultLookupHeader.isEmpty()) {
			List<Long> companyScope = new ArrayList<Long>();
			companyScope.add(ScopeType.COMMON.getValue());
			companyScope.add(scope);
			LookupHeader lookupHeader = resultLookupHeader.get(0);
			LookupDependentExample lookupDependentExample = new LookupDependentExample();
			lookupDependentExample
			.createCriteria()
			.andLookupIdEqualTo(lookupHeader.getLookupId())
			.andCompanyScopeIn(companyScope);
			return lookupDependentMapper.countByExample(lookupDependentExample);
		}
		return 0;
	}

	@Override
	public List<KeyValue> getLookupKeyValues(String lookupName, String key) {
		return lookupFinderMapper.getLookupKeyValues(lookupName, key);
	}

	//Add by HBP 15103120081532 - Modifikasi Form PD Untuk Budget Control
	@Override
	public LookupDTO getLookupByName(String lookupName) { 
		return lookupFinderMapper.getLookupByName(lookupName);
	}
	//End Add by HBP 15103120081532 - Modifikasi Form PD Untuk Budget Control
}
