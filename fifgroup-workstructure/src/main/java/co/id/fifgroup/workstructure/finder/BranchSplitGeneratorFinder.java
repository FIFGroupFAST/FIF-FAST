package co.id.fifgroup.workstructure.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.workstructure.dto.BranchSplitMasterDTO;

public interface BranchSplitGeneratorFinder {
	List<KeyValue> getCostCenter(@Param("code") String code, RowBounds rowBounds);
	Integer countCostCenter(@Param("code") String code);
	BranchSplitMasterDTO getTemplateByType(@Param("type")String type, @Param("companyId")Long companyId);
	List<KeyValue> getGlCodes(@Param("code") String code, RowBounds rowBounds);
	Integer countGlCodes(@Param("code") String code);
	int countOrganizationNodeByHierarchyId(@Param("hierarchyId")Long hierarchyId, @Param("orgId")Long orgId);
	void deleteOrgHierElementByVersionId(@Param("versionId") Long versionId);
	Integer getLastVerionHierarcyById(@Param("orgHierId")Long orgHierId);
}
