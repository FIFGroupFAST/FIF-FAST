package co.id.fifgroup.personneladmin.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.HousEmployeeDTO;

public interface HousEmployeeMapper {

	public Integer insert(HousEmployeeDTO housEmployee);
	
	public Integer countHouseEmployee(@Param("employeeNumber") String employeeNumber);
	
	public Integer update(HousEmployeeDTO housEmployee);
	
	public Integer insertUser(@Param("userName") String userName, @Param("personId") Long personId, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
	public Integer countUser(@Param("userName") String userName);
	
	public Integer updateUser(@Param("userName") String userName, @Param("personId") Long personId);
	
}
