package co.id.fifgroup.core.finder;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.interfacing.FaMassAdditions;

public interface GeneralAffairFinder {
	public void insertFaMassAddition(FaMassAdditions faMassAdditions) throws Exception;
	public HashMap<String, Object> getFaMassDescription(@Param("companyId") Long companyId, @Param("duration") Long duration);
	public Long getPayablesCodeCombinationId(@Param("companyId") Long companyId, @Param("branchCode") String branchCode );
	public Long getExpenseCodeCombinationId(@Param("companyId") Long companyId, @Param("branchCode") String branchCode);
	public Long getLocationId(@Param("branchGlCode") String branchGlCode);
	public Long getPayablesCodeCombinationIdHousing(@Param("companyId") Long companyId, @Param("branchCode") String branchCode,@Param("costCenterCode") String costCenterCode,@Param("segment4") String segment4,@Param("segment5") String segment5,@Param("segment6") String segment6,@Param("segment7") String segment7,@Param("segment8") String segment8);
	public Long getExpenseCodeCombinationIdHousing(@Param("companyId") Long companyId, @Param("branchCode") String branchCode,@Param("costCenterCode") String costCenterCode,@Param("segment4") String segment4,@Param("segment5") String segment5,@Param("segment6") String segment6,@Param("segment7") String segment7,@Param("segment8") String segment8);
	// 15060508220346 | 15/06/2015 | By : PHI
	public Long getEmployeePersonId(@Param("employeeNumber") String employeeNumber);
	
	// 15072314060623 | 03/08/2015 | By : PHI
	public Long getCodeCombinationId(Map<String, Object> map);
	public Long getChartAccountId(String shortName);
	public int insertNewCodeCombination(Map<String, Object> map);
	// End 15072314060623 | 03/08/2015 | By : PHI
}
