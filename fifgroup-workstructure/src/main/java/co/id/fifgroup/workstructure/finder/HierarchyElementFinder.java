package co.id.fifgroup.workstructure.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.workstructure.domain.HierarchyElement;

public interface HierarchyElementFinder {
	
	List<HierarchyElement> findByHierarchyId(@Param("hierarchyId") Long hierarchyId, @Param("versionId") Long versionId, @Param("date") Date date, @Param("isActiveOrg") Boolean isActiveOrg);
}
