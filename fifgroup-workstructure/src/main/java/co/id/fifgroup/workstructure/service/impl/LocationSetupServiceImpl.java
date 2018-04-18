package co.id.fifgroup.workstructure.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.joda.time.LocalDate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.IdGenerator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.version.AbstractVersioningService;
import co.id.fifgroup.workstructure.domain.Location;
import co.id.fifgroup.workstructure.domain.LocationExample;
import co.id.fifgroup.workstructure.domain.LocationInfo;
import co.id.fifgroup.workstructure.domain.LocationInfoExample;
import co.id.fifgroup.workstructure.domain.LocationVersion;
import co.id.fifgroup.workstructure.service.LocationSetupService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.workstructure.dao.LocationInfoMapper;
import co.id.fifgroup.workstructure.dao.LocationMapper;
import co.id.fifgroup.workstructure.dao.LocationVersionMapper;
import co.id.fifgroup.workstructure.dto.LocationDTO;
import co.id.fifgroup.workstructure.dto.OtherInfoDTO;
import co.id.fifgroup.workstructure.finder.LocationFinder;
import co.id.fifgroup.workstructure.validation.LocationValidator;

@Transactional
@Service
public class LocationSetupServiceImpl extends AbstractVersioningService<LocationDTO> implements LocationSetupService{
	
	private Logger logger = LoggerFactory.getLogger(LocationSetupServiceImpl.class);
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private LocationMapper locationMapper;	
	@Autowired
	private LocationVersionMapper locationVersionMapper;
	@Autowired
	private LocationInfoMapper otherInfoMapper;
	@Autowired
	private LocationFinder locationFinder;
	@Autowired
	private LocationValidator locationValidator;
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public void save(LocationDTO subject) throws Exception{
		super.save(subject);		
	}

	@Override
	public void delete(LocationDTO selectedLocation) {
		super.delete(selectedLocation);
	}

	@Override
	public void insertHeader(LocationDTO subject) {
		
		Location location = modelMapper.map(subject, Location.class);
		locationMapper.insert(location);
		subject.setId(location.getId());
	}

	@Override
	public void updateHeader(LocationDTO subject) {
		
		Location location = modelMapper.map(subject, Location.class);
		locationMapper.updateByPrimaryKey(location);
	}

	@Override
	public void insertDetail(LocationDTO subject) {
		
		LocationVersion version = modelMapper.map(subject, LocationVersion.class);
		version.setId(subject.getId());
		locationVersionMapper.insert(version);
		subject.setVersionId(version.getVersionId());
		
		if(subject.getOtherInfos() != null) {
			for(OtherInfoDTO info : subject.getOtherInfos()) {
				LocationInfo otherInfo = modelMapper.map(info, LocationInfo.class);
				otherInfo.setVersionId(subject.getVersionId());
				otherInfoMapper.insert(otherInfo);
			}
		}
	}

	@Override
	public void updateDetail(LocationDTO subject) {
		
		LocationVersion version = modelMapper.map(subject, LocationVersion.class);
		
		LocationInfoExample example = new LocationInfoExample();
		example.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		otherInfoMapper.deleteByExample(example);
		
		if(subject.getOtherInfos() != null) {
			for(OtherInfoDTO info : subject.getOtherInfos()) {
				LocationInfo otherInfo = modelMapper.map(info, LocationInfo.class);
				otherInfo.setVersionId(subject.getVersionId());
				otherInfoMapper.insert(otherInfo);
			}
		}		
		locationVersionMapper.updateByPrimaryKey(version);
	}

	@Override
	@Transactional(readOnly = true)
	public LocationDTO findByIdAndVersionNumber(Long id, Integer versionNumber) {
		return locationFinder.findByIdAndVersionNumber(id, versionNumber);
	}

	@Override
	@Transactional(readOnly = true)
	public void validate(LocationDTO subject) throws ValidationException {
		locationValidator.validate(subject);
	}

	@Override
	public void deleteHeader(LocationDTO subject) {
		locationMapper.deleteByPrimaryKey(subject.getId());		
	}

	@Override
	public void deleteDetail(LocationDTO subject) {
		LocationInfoExample example = new LocationInfoExample();
		example.createCriteria().andVersionIdEqualTo(subject.getVersionId());
		otherInfoMapper.deleteByExample(example);
		
		locationVersionMapper.deleteByPrimaryKey(subject.getVersionId());
	}

	@Override
	@Transactional(readOnly = true)
	public Long getNextHeaderId() {
		return idGenerator.getNextHeaderId("WOS_LOCATIONS_S");
	}

	@Override
	@Transactional(readOnly = true)
	public List<Integer> findVersionsById(Long id) {
		return locationFinder.findVersionsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<LocationDTO> findByExample(LocationDTO example) {
		return locationFinder.findByExample(example);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<LocationDTO> findByExample(LocationDTO example, int limit,
			int offset) {
		return locationFinder.findByExample(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countByExample(LocationDTO example) {
		return locationFinder.countByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public LocationDTO findById(Long id) {
		return locationFinder.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Location> findByExample(LocationExample example) {
		example.createCriteria().andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		return locationMapper.selectByExample(example);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Location> findByExample(LocationExample example, int limit, int offset) {
		example.createCriteria().andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		example.setOrderByClause("LOCATION_CODE");
		return locationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public List<LocationDTO> findByInquiry(LocationDTO example) {
		return locationFinder.findByInquiry(example);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countByExample(LocationExample example) {
		example.createCriteria().andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		return locationMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isHaveFuture(Long id) {
		return locationFinder.isHaveFuture(id) == 1;
	}

	@Override
	@Transactional(readOnly = true)
	public LocationDTO findByCode(String locationCode) {
		return locationFinder.findByCode(locationCode, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), new Date());
	}

	@Override
	@Transactional(readOnly = true)
	public String findIndexColaOnOtherInfo(Long locationId) {
		return locationFinder.findIndexColaOnOtherInfo(locationId, new LocalDate().toDate());
	}
	
	@Override
	@Transactional(readOnly = true)
	public String findIndexColaOnOtherInfo(Long locationId, Date effectiveOnDate) {
		return locationFinder.findIndexColaOnOtherInfo(locationId, DateUtils.truncate(effectiveOnDate, Calendar.DATE));
	}

	@Override
	@Transactional(readOnly = true)
	public LocationDTO findByIdAndDate(Long id, Date effectiveDate) {
		return locationFinder.findByIdAndDate(id, effectiveDate);
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean isFutureExist(Long id) {
		return locationFinder.isFutureExist(id) > 0;
	}

	@Override
	@Transactional(readOnly=true)
	public Long findVersionIdByIdAndDate(Long id, Date effectiveDate) {
		return locationFinder.findVersionIdByIdAndDate(id, effectiveDate);
	}

	@Override
	@Transactional(readOnly=true)
	public LocationDTO getLocationHomeBase(Long companyId, String locationCode, Date effectiveOnDate) {
		return locationFinder.getLocationHomeBase(companyId, locationCode, effectiveOnDate);
	}

	@Override
	public List<LocationDTO> findActiveLocationForLov(LocationDTO example, int limit,
			int offset) {
		return locationFinder.findActiveLocationForLov(example, new RowBounds(offset, limit));
	}

	@Override
	public Integer countActiveLocationForLov(LocationDTO example) {
		return locationFinder.countActiveLocationForLov(example);
	}

	@Override
	public Location findByPrimaryKey(Long locationId) {
		return locationMapper.selectByPrimaryKey(locationId);
	}

	@Override
	public LocationDTO findByCode(String locationCode, Date effectiveStartDate) {
		return locationFinder.findByCode(locationCode, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), effectiveStartDate);
	}

	@Override
	public Location findByName(String locationName, Long companyId) {
		LocationExample example = new LocationExample();
		example.createCriteria().andCompanyIdEqualTo(companyId)
			.andLocationNameEqualTo(locationName);
		List<Location> locations = locationMapper.selectByExample(example);
		return locations.size() > 0 ? locations.get(0) : null;
	}

	@Override
	public LocationDTO findLocationByOrganizationId(Long organizationId,
			Long companyId, Date effectiveDate) {
		return locationFinder.findLocationByOrganizationId(organizationId, companyId, effectiveDate);
	}

	
	
}
