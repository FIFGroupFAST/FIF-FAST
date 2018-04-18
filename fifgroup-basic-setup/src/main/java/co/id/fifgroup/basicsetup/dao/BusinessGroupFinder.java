package co.id.fifgroup.basicsetup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.basicsetup.dto.BusinessGroupDTO;

public interface BusinessGroupFinder {

	public List<BusinessGroupDTO> getBusinessGroupDtoByGroupName(@Param("groupName") String groupName);
	public int getCountBusinessGroupDtoByGroupName(@Param("groupName") String groupName);
	
}
