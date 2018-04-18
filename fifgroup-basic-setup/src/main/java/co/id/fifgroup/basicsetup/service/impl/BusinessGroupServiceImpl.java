package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.BusinessGroupFinder;
import co.id.fifgroup.basicsetup.dao.BusinessGroupMapper;
import co.id.fifgroup.basicsetup.domain.BusinessGroup;
import co.id.fifgroup.basicsetup.domain.BusinessGroupExample;
import co.id.fifgroup.basicsetup.dto.BusinessGroupDTO;
import co.id.fifgroup.basicsetup.service.BusinessGroupService;
import co.id.fifgroup.basicsetup.validation.BusinessGroupValidator;

@Transactional(readOnly=true)
@Service
public class BusinessGroupServiceImpl implements BusinessGroupService {

	@Autowired
	private BusinessGroupMapper businessGroupMapper;
	@Autowired
	private BusinessGroupFinder businessGroupFinder;
	@Autowired
	private BusinessGroupValidator businessGroupValidator;
	
	@Override
	public int countByExample(BusinessGroupExample example) {
		return businessGroupMapper.countByExample(example);
	}

	
	private List<BusinessGroup> selectByExample(BusinessGroupExample example) {
		return businessGroupMapper.selectByExample(example);
	}

	@Override
	public List<BusinessGroup> getBusinessGroupByExample(BusinessGroupExample example,
			int limit, int offset) {
		return businessGroupMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}
	
	@Override
	public boolean isExistBusinessGroupForUpdate(BusinessGroup record) {
		BusinessGroupExample countBusinessGroupByName = new BusinessGroupExample();
		countBusinessGroupByName.createCriteria().andGroupNameEqualTo(record.getGroupName());
		if(countByExample(countBusinessGroupByName) > 0) {
			BusinessGroupExample selectBusinessGroupByCode = new BusinessGroupExample();
			selectBusinessGroupByCode.createCriteria().andGroupCodeEqualTo(record.getGroupCode()).andGroupNameEqualTo(record.getGroupName());
			if(selectByExample(selectBusinessGroupByCode).size() > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void save(BusinessGroup businessGroup) throws Exception {
		businessGroupValidator.validate(businessGroup);
		if(businessGroup.getGroupId() == null) {
			businessGroupMapper.insert(businessGroup);
		} else {
			businessGroupMapper.updateByPrimaryKey(businessGroup);
		}
	}

	@Override
	public List<BusinessGroup> getBusinessGroupByExample(
			BusinessGroupExample businessGroupExample) {
		return selectByExample(businessGroupExample);
	}

	@Override
	public List<BusinessGroupDTO> getBusinessGroupDtoByGroupName(String groupName) {
		return businessGroupFinder.getBusinessGroupDtoByGroupName(groupName);
	}

	@Override
	public int getCountBusinessGroupDtoByGroupName(String groupName) {
		return businessGroupFinder.getCountBusinessGroupDtoByGroupName(groupName);
	}
	
}
