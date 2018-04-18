package co.id.fifgroup.systemadmin.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.User;
import co.id.fifgroup.systemadmin.domain.UserExample;
import co.id.fifgroup.systemadmin.domain.UserResponsibility;
import co.id.fifgroup.systemadmin.dto.UserDTO;

public interface UserService {
	
	public int countUserByExample(UserExample example);
	
	public int save(UserDTO userDto) throws Exception;
	
	public List<User> getUserByExample(UserExample example);
	
	public List<UserDTO> getUserByUsernameAndEmployeeName(UserDTO userDTO, int limit, int offset);
	
	public int countUserByUsernameAndEmployeeName(UserDTO userDTO);
	
	public UserDTO getUserById(Long id);
	
	public List<UserDTO> getActiveUserWithJobId(String userName);
	
	public int saveUserResponsibility(UserResponsibility userResponsibility);
	
	public int deleteUserResponsibilityByUserResponsibilityId(Long id);
	
	public int countResponsibilityByUserAndResponsibilityId(Long userId, Long respId);
	
	public List<UserDTO> getUserWithHasDirectResposnibility();
	
	public void updateByExample(User user, UserExample example);
	
	public int countByExample(UserExample example);
	
	public List<User> selectByExampleWithRowbounds(UserExample example, RowBounds rowBounds);
	
	public boolean validateLdapUser(String userName) throws Exception;
	
	public List<Long> getPersonIdByResponsibilityId(String responsibilityName, Long companyId);

	public List<User> getUserByExampleWithRowbounds(String searchCriteria,
			int limit, int offset);
	
}
