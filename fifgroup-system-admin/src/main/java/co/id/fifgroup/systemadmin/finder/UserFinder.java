package co.id.fifgroup.systemadmin.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.UserResponsibility;
import co.id.fifgroup.systemadmin.dto.UserDTO;
import co.id.fifgroup.systemadmin.dto.UserResponsibilityDTO;

public interface UserFinder {
	
	List<UserResponsibilityDTO> findDirectResponsibilityByExample(UserResponsibility example);
	
	List<UserResponsibilityDTO> findJobResponsibilityByExample(UserResponsibility example);
	
	List<UserDTO> findUserByUsernameAndEmployeeName(UserDTO userDTO, RowBounds rowBounds);
	
	int countUserByUsernameAndEmployeeName(UserDTO userDTO);
	
	UserDTO selectByPrimaryKey(@Param("userId") Long id);
	
	List<UserDTO> findAllActiveUserWithJobId(@Param("userName")String userName);
	
	List<UserDTO> findAllActiveUserWithHasActiveDirectResponsibility();
	
}
