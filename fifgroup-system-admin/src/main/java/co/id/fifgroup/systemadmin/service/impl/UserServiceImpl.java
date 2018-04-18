package co.id.fifgroup.systemadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.CommunicationException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.ldap.authentication.SpringSecurityAuthenticationSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;

import co.id.fifgroup.systemadmin.constant.ResponsibilityType;
import co.id.fifgroup.systemadmin.dao.UserMapper;
import co.id.fifgroup.systemadmin.dao.UserResponsibilityMapper;
import co.id.fifgroup.systemadmin.domain.User;
import co.id.fifgroup.systemadmin.domain.UserExample;
import co.id.fifgroup.systemadmin.domain.UserResponsibility;
import co.id.fifgroup.systemadmin.domain.UserResponsibilityExample;
import co.id.fifgroup.systemadmin.dto.UserDTO;
import co.id.fifgroup.systemadmin.dto.UserResponsibilityDTO;
import co.id.fifgroup.systemadmin.finder.UserFinder;
import co.id.fifgroup.systemadmin.service.UserService;
import co.id.fifgroup.systemadmin.validation.UserValidator;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService{
	
	//User Entity
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserResponsibilityMapper userResponsibilityMapper;
	
	//Finder
	@Autowired
	private UserFinder userFinder;
	
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	@Transactional(readOnly=true)
	public int countUserByExample(UserExample example) {
		return userMapper.countByExample(example);
	}

	@Override
	@Transactional
	public int save(UserDTO userDto) throws Exception{
		int result = 0;
		if(userDto.getId() == null) {
			result = userMapper.insert(userDto);
		} else {
			UserDTO newUserDTO = userDto;
			newUserDTO.setUserName(null);
			result = userMapper.updateByPrimaryKeySelective(newUserDTO);
		}
		
		for (UserResponsibilityDTO directRespDto : userDto.getDirectResponsibilityDto()) {
			directRespDto.setUserId(userDto.getId());
			directRespDto.setResponsibilityType(ResponsibilityType.DIRECT.toString());
			directRespDto.setCreatedBy(userDto.getCreatedBy());
			directRespDto.setCreationDate(userDto.getCreationDate());
			directRespDto.setLastUpdatedBy(userDto.getLastUpdatedBy());
			directRespDto.setLastUpdateDate(userDto.getLastUpdateDate());
			if(directRespDto.getDateTo() == null) {
				directRespDto.setDateTo(DateUtil.MAX_DATE);
			}
			
			if(directRespDto.getId() == null) {
				userResponsibilityMapper.insert(directRespDto);
			} else {
				userResponsibilityMapper.updateByPrimaryKey(directRespDto);
			}
		}
		
		for (UserResponsibilityDTO jobRespDto : userDto.getJobResponsibilityDto()) {
			if(jobRespDto.getResponsibilityId() != null) {
				jobRespDto.setUserId(userDto.getId());
				jobRespDto.setResponsibilityType(ResponsibilityType.JOB.toString());
				jobRespDto.setCreatedBy(userDto.getCreatedBy());
				jobRespDto.setCreationDate(userDto.getCreationDate());
				jobRespDto.setLastUpdatedBy(userDto.getLastUpdatedBy());
				jobRespDto.setLastUpdateDate(userDto.getLastUpdateDate());
				if(jobRespDto.getDateTo() == null) {
					jobRespDto.setDateTo(DateUtil.MAX_DATE);
				}
				
				if(jobRespDto.getId() == null) {
					userResponsibilityMapper.insert(jobRespDto);
				} else {
					userResponsibilityMapper.updateByPrimaryKey(jobRespDto);
				}
			}
		}

		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public List<User> getUserByExample(UserExample example) {
		return userMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public UserDTO getUserById(Long id) {
		UserDTO userDto = new UserDTO();		
		userDto = userFinder.selectByPrimaryKey(id);
		
		UserResponsibility directUserRespExample = new UserResponsibility();
		directUserRespExample.setUserId(id);
		directUserRespExample.setResponsibilityType(ResponsibilityType.DIRECT.toString());
		List<UserResponsibilityDTO> userResponsibilityDTOs = userFinder.findDirectResponsibilityByExample(directUserRespExample);
		if(userResponsibilityDTOs != null)
			userDto.setDirectResponsibilityDto(userResponsibilityDTOs);
				
		UserResponsibility jobUserRespExample = new UserResponsibility();
		jobUserRespExample.setUserId(id);
		jobUserRespExample.setResponsibilityType(ResponsibilityType.JOB.toString());
		userDto.setJobResponsibilityDto(userFinder.findJobResponsibilityByExample(jobUserRespExample));
		
		return userDto;
	}

	@Override
	@Transactional(readOnly=true)
	public List<UserDTO> getUserByUsernameAndEmployeeName(UserDTO userDTO, int limit, int offset) {
		return userFinder.findUserByUsernameAndEmployeeName(userDTO, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int countUserByUsernameAndEmployeeName(UserDTO userDTO) {
		return userFinder.countUserByUsernameAndEmployeeName(userDTO);
	}

	@Override
	@Transactional(readOnly=true)
	public List<UserDTO> getActiveUserWithJobId(String userName) {
		return userFinder.findAllActiveUserWithJobId(userName);
	}

	@Override
	public int saveUserResponsibility(UserResponsibility userResponsibility) {
		return userResponsibilityMapper.insert(userResponsibility);
	}

	@Override
	public int deleteUserResponsibilityByUserResponsibilityId(Long id) {
		return userResponsibilityMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=true)
	public int countResponsibilityByUserAndResponsibilityId(Long userId, Long respId) {
		UserResponsibilityExample example = new UserResponsibilityExample();
		example.createCriteria().andUserIdEqualTo(userId).andResponsibilityIdEqualTo(respId);
		return userResponsibilityMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<UserDTO> getUserWithHasDirectResposnibility() {
		return userFinder.findAllActiveUserWithHasActiveDirectResponsibility();
	}

	@Override
	public void updateByExample(User user, UserExample example) {
		userMapper.updateByExampleSelective(user, example);
		
	}

	@Override
	@Transactional(readOnly=true)
	public int countByExample(UserExample example) {
		return userMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean validateLdapUser(String userName) throws Exception{
		
		//Check double
		UserExample example = new UserExample();
		example.createCriteria().andUserNameEqualTo(userName);
		int result = userMapper.countByExample(example);
		if(result > 0)
			return false;
		
		//check ldap
		/*AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectClass", "person")).and(new EqualsFilter("sAMAccountName", userName));
		try {
			List<?> list = ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), new AttributesMapper() {
				
				@Override
				public Object mapFromAttributes(Attributes attr) throws NamingException {
					String userName = (String) attr.get("sAMAccountName").get();
					return userName;
				}
			});
			
			if(!list.isEmpty())
				return true;
			
		} catch (CommunicationException e) {
			throw new Exception("Could not connect to LDAP Server. may caused by : "+e.getMessage());
		}catch (Exception e) {
			
		}*/
		
		return true;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Long> getPersonIdByResponsibilityId(String responsibilityName, Long companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUserByExampleWithRowbounds(String searchCriteria,
			int limit, int offset) {
		UserDTO userDto = new UserDTO();
		userDto.setUserName(searchCriteria);
		List<UserDTO> results =  userFinder.findUserByUsernameAndEmployeeName(userDto, new RowBounds(offset, limit));
		List<User> returnResult = new ArrayList<>();
		for(UserDTO result : results){
			returnResult.add(modelMapper.map(result, User.class));
		}
		return returnResult;
	}

	@Override
	public List<User> selectByExampleWithRowbounds(UserExample example,
			RowBounds rowBounds) {
		return userMapper.selectByExampleWithRowbounds(example, rowBounds);
	}
}
