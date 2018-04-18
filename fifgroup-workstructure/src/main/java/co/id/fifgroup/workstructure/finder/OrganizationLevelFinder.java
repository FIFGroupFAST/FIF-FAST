package co.id.fifgroup.workstructure.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.workstructure.dto.OrganizationLevelDto;

public interface OrganizationLevelFinder {
	
	List<OrganizationLevelDto> findByExample(OrganizationLevelDto example);
	Integer countByExample(OrganizationLevelDto example);
	List<OrganizationLevelDto> findAll(@Param("companyId") Long companyId);
	String findByCode(@Param("levelCode") String levelCode, @Param("companyId") Long companyId);
	List<OrganizationLevelDto> findByExampleOrderByName(OrganizationLevelDto example);
}