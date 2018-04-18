package co.id.fifgroup.workstructure.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.workstructure.dto.OrgLevelHierDTO;

public interface OrgLevelHierFinder {
	
	OrgLevelHierDTO find(@Param("companyId") Long companyId);
	OrgLevelHierDTO findByIdAndVersionNumber(@Param("id") Long id, @Param("versionNumber") Integer versionNumber);
	List<Integer> findVersionsById(@Param("id") Long id);
	Integer isHaveFuture(@Param("id") Long id);
	Integer isFutureExist(@Param("id") Long id);
}
