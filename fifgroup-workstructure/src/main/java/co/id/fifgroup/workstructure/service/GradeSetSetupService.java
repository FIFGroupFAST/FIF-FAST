package co.id.fifgroup.workstructure.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.domain.GradeSet;
import co.id.fifgroup.workstructure.domain.GradeSetElement;
import co.id.fifgroup.workstructure.domain.GradeSetElementExample;
import co.id.fifgroup.workstructure.domain.GradeSetExample;

import co.id.fifgroup.workstructure.dto.GradeSetDTO;
import co.id.fifgroup.workstructure.dto.GradeSetElementDTO;

public interface GradeSetSetupService {
	
	void save(GradeSetDTO gradeSetDto) throws ValidationException;	
	List<GradeSetDTO> findByExample(GradeSetDTO gradeSetDto);
	Integer countByExample(GradeSetDTO gradeSetDto);
	List<GradeSet> findByExample(GradeSetExample example);	
	Integer countByExample(GradeSetExample example);
	List<GradeSetElement> findElementByExample(GradeSetElementExample example);
	List<GradeSetDTO> findByInquiry(GradeSetDTO example);
	GradeSetDTO findById(Long id);
	Long findIdByName(String gradeSetName);
	List<GradeSetElementDTO> findGradeSetElementByGradeIdInOneGradeSet(Long gradeId);
	List<GradeSetElementDTO> findGradeIdInGradeSetId(List<Long> gradeSetId);
	GradeSetElementDTO findGradeSetElementByGradeId(Long gradeId);
	List<GradeSetElementDTO> findValidGradeElementById(Long jobId);
	List<GradeSetElementDTO> findOrderedGradeByGradeSetId(List<Long> gradeSetId);
	Long getMinimumValidGrade(Long jobId, Date effectiveDate);
	List<GradeSetElementDTO> findOrderedGradeByGradeSetId(List<Long> gradeSetId, Date effectiveOnDate);
	public List<GradeSetElementDTO> findValidGradeElementByIdAndEffectiveDate(Long jobId, Date effectiveOnDate);
}
