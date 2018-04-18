package co.id.fifgroup.workstructure.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.workstructure.dto.GradeSetDTO;
import co.id.fifgroup.workstructure.dto.GradeSetElementDTO;

public interface GradeSetFinder {

	List<GradeSetDTO> findByInquiry(GradeSetDTO gradeSetDto);
	List<GradeSetDTO> findByExample(GradeSetDTO gradeSetDto);
	Integer countByExample(GradeSetDTO gradeSetDto);
	GradeSetDTO findById(@Param("id") Long id);
	Long findIdByName(@Param("gradeSetName") String gradeSetName, @Param("companyId") Long companyId);
	List<GradeSetElementDTO> findGradeSetElementByGradeIdInOneGradeSet(@Param("gradeId") Long gradeId);
	List<GradeSetElementDTO> findGradeIdInGradeSetId(@Param("gradeSetIds") List<Long> gradeSetId);
	GradeSetElementDTO findGradeSetElementByGradeId(@Param("gradeId") Long gradeId);
	List<GradeSetElementDTO> findValidGradeElementById(@Param("jobId") Long jobId);
	GradeSetDTO findByIdForPromotion(@Param("id") Long id);
	List<GradeSetElementDTO> findOrderedGradeByGradeSetId(@Param("gradeSetIds") List<Long> gradeSetId);
	
	Long getMinimumValidGrade(@Param("jobId") Long jobId, @Param("effectiveDate") Date effectiveDate);
	
	List<GradeSetElementDTO> findOrderedGradeByGradeSetIdAndEffectiveDate(@Param("gradeSetIds") List<Long> gradeSetId, @Param("effectiveOnDate") Date effectiveOnDate);
	List<GradeSetElementDTO> findValidGradeElementByIdAndEffectiveDate(@Param("jobId") Long jobId, @Param("effectiveOnDate") Date effectiveOnDate);
}
