package co.id.fifgroup.workstructure.finder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.workstructure.domain.GradeRate;

import co.id.fifgroup.workstructure.dto.GradeDTO;
import co.id.fifgroup.workstructure.dto.GradeSetElementDTO;

public interface GradeFinder {

	GradeDTO findById(@Param("id") Long id);
	
	List<GradeDTO> findByInquiry(GradeDTO example);	
	List<GradeDTO> findByExample(GradeDTO example);
	
	List<GradeDTO> findByExampleCurrentAndFuture(GradeDTO example);
	List<GradeDTO> findByExampleCurrentAndFuture(GradeDTO example, RowBounds rowBounds);
	
	int countByExampleCurrentAndFuture(GradeDTO example);
	
	Integer countByExample(GradeDTO example);	
	GradeDTO findByIdAndVersionNumber(@Param("id") Long id, @Param("versionNumber") Integer versionNumber);
	GradeDTO findByIdAndDate(@Param("id") Long id, @Param("effectiveDate") Date effectiveDate);
	List<Integer> findVersionsById(@Param("id") Long id);
	List<GradeDTO> findByName(@Param("name") String name, @Param("companyId") Long companyId);
	
	/*add by RIM CR HCMS - Promotion Acting*/
	List<GradeDTO> findByCode(@Param("gradeCode") String gradeCode, @Param("companyId") Long companyId);
	/*end add by RIM CR HCMS - Promotion Acting*/
	
	List<GradeDTO> findByGradeSetId(@Param("gradeSetId") Long gradeSetId, @Param("gradeCode") String gradeCode, @Param("gradeName") String gradeName, RowBounds rowBounds);
	Integer countByGradeSetId(@Param("gradeSetId") Long gradeSetId, @Param("gradeCode") String gradeCode, @Param("gradeName") String gradeName);
	Integer isHaveFuture(@Param("id") Long id);
	GradeDTO findLastVersionByCode(@Param("gradeCode") String gradeCode, @Param("companyId") Long companyId);
	GradeSetElementDTO findGradeSetElementByJobIdAndGradeId(@Param("jobId") Long jobId, @Param("gradeId") Long gradeId);
	List<Integer> findGradeSequenceValidGradeByJobId(@Param("jobId") Long jobId);
	List<String> findGradeActiveByCompanyId(@Param("companyId") Long companyId, @Param("grade") String grade, RowBounds rowBounds);
	List<String> findSubGradeActiveByCompanyId(@Param("companyId") Long companyId, @Param("subGrade") String subGrade);
	List<String> findSubGradeByGradeCompanyId(@Param("companyId") Long companyId, @Param("grade") String grade);
	List<String> findStringGradeByCompanyId(@Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	List<Long> findGradeIdsByStringGrade(@Param("companyId") Long companyId, @Param("grade") String grade, @Param("subgrade") String subgrade);
	
	GradeDTO findGradeByGradeAndSubGrade(@Param("companyId") Long companyId, @Param("gradeSetId") Long gradeSetId, 
			@Param("grade") String grade, @Param("subGrade") String subGrade);
	BigDecimal getMinSalaryByGradeIdAndBranchId(@Param("gradeId") Long gradeId, @Param("branchId")Long branchId, @Param("date") Date date);
	BigDecimal getMaxSalaryByGradeIdAndBranchId(@Param("gradeId") Long gradeId, @Param("branchId")Long branchId, @Param("date") Date date);
	List<GradeDTO> findByExampleWithRowbounds(GradeDTO example, RowBounds rowbounds);
	Integer isFutureExist(@Param("id") Long id);
	Long findVersionIdByIdAndDate(@Param("id") Long id, @Param("effectiveDate") Date effectiveDate);
	public List<GradeDTO> findActiveGradeForLov(GradeDTO example, RowBounds rowBounds);
	public Integer countActiveGradeForLov(GradeDTO example);
	GradeRate getGradeRateByGradeIdAndBranchId(@Param("gradeId") Long gradeId, @Param("branchId")Long branchId, @Param("date") Date date);
	
	List<String> findGradeActiveByDateAndCompanyId(@Param("companyId") Long companyId, @Param("effectiveDate") Date effectiveDate);
	List<String> findSubGradeActiveByDateAndCompanyId(@Param("companyId") Long companyId, @Param("effectiveDate") Date effectiveDate);
	
}
