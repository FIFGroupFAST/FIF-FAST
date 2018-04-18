package co.id.fifgroup.eligibility.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.validation.ValidationException;
import com.google.common.base.Strings;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.eligibility.dao.FactTypeFieldMapper;
import co.id.fifgroup.eligibility.dao.FactTypeMapper;
import co.id.fifgroup.eligibility.domain.FactType;
import co.id.fifgroup.eligibility.domain.FactTypeField;
import co.id.fifgroup.eligibility.dto.FactTypeDTO;
import co.id.fifgroup.eligibility.dto.FactTypeFieldDTO;
import co.id.fifgroup.eligibility.finder.FactTypeFinder;
import co.id.fifgroup.eligibility.service.FactTypeSetupService;
import co.id.fifgroup.eligibility.util.IdGenerator;
import co.id.fifgroup.eligibility.validation.FactTypeValidator;

@Service
@Transactional(readOnly = true)
public class FactTypeSetupServiceImpl implements FactTypeSetupService{

	private static final Logger logger = LoggerFactory.getLogger(FactTypeSetupServiceImpl.class);
	
	private static final Map<String, FactTypeDTO> cachedFactTypes = Collections.synchronizedMap(new HashMap<String, FactTypeDTO>());
	
	@Autowired
	FactTypeFinder finder;
	
	@Autowired
	FactTypeValidator validator;
	
	@Autowired
	FactTypeMapper factTypeMapper;
	
	@Autowired
	FactTypeFieldMapper fieldMapper;
	
	@Autowired
	SecurityService securityServiceImpl;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	@Transactional(readOnly = false)
	public void save(FactTypeDTO subject) throws ValidationException {
		try {
			final Date today = new Date();
			final Long userId = securityServiceImpl.getSecurityProfile().getUserId();
			logger.debug(String.valueOf(subject));
			subject.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			
			validate(subject);
			
			if (Strings.isNullOrEmpty(subject.getId())) {
				
				subject.setId(IdGenerator.generateFrom(subject.getName()));
				FactType factType = modelMapper.map(subject, FactType.class);
				factType.setCreatedBy(userId);
				factType.setCreationDate(today);
				factType.setLastUpdatedBy(userId);
				factType.setLastUpdateDate(today);
				factTypeMapper.insert(factType);
				
				for (FactTypeFieldDTO fieldDTO : subject.getFields()) {
					FactTypeField field = modelMapper.map(fieldDTO, FactTypeField.class);
					field.setId(IdGenerator.generateFrom(field.getName()));
					field.setFactTypeId(factType.getId());
					field.setCreatedBy(userId);
					field.setCreationDate(today);
					field.setLastUpdatedBy(userId);
					field.setLastUpdateDate(today);
					fieldMapper.insert(field);
				}
			} else {
				FactType factType = new FactType();
				factType.setId(subject.getId());
				factType.setSqlQuery(subject.getSqlQuery());
				factType.setLastUpdatedBy(userId);
				factType.setLastUpdateDate(today);
				factTypeMapper.updateByPrimaryKeySelective(factType);
				for (FactTypeFieldDTO fieldDTO : subject.getFields()) {
					if (Strings.isNullOrEmpty(fieldDTO.getId())) {
						FactTypeField field = modelMapper.map(fieldDTO, FactTypeField.class);
						field.setId(IdGenerator.generateFrom(field.getName()));
						field.setFactTypeId(subject.getId());
						field.setCreatedBy(subject.getCreatedBy());
						field.setCreationDate(subject.getCreationDate());
						field.setLastUpdatedBy(userId);
						field.setLastUpdateDate(today);
						fieldMapper.insert(field);
					}
				}
			}
			cachedFactTypes.put(subject.getId(),finder.findByExample(new FactTypeDTO(subject.getId())).get(0));
		} catch (Exception ex) {
			subject.setId(null);
			throw ex;
		}
		
	}
	

	@Override
	public void validate(FactTypeDTO subject) throws ValidationException{
		validator.validate(subject);
	}

	@Override
	public List<FactTypeDTO> findByExample(FactTypeDTO example) {
		return finder.findByExample(example);
	}

	@Override
	public Integer countByExample(FactTypeDTO example) {
		return finder.countByExample(example);
	}


	@Override
	public FactTypeDTO findById(String factTypeId) {
		if (!cachedFactTypes.containsKey(factTypeId)) {
			cachedFactTypes.put(factTypeId,finder.findByExample(new FactTypeDTO(factTypeId)).get(0));
		}
		return cachedFactTypes.get(factTypeId);
	}

	
	
}
