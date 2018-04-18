package co.id.fifgroup.workstructure.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.version.VersioningService;
import co.id.fifgroup.workstructure.constant.MinMax;
import co.id.fifgroup.workstructure.domain.Grade;
import co.id.fifgroup.workstructure.domain.GradeExample;
import co.id.fifgroup.workstructure.domain.GradeRate;

import co.id.fifgroup.workstructure.dto.GradeDTO;
import co.id.fifgroup.workstructure.dto.GradeSequenceDTO;
import co.id.fifgroup.workstructure.dto.GradeSetElementDTO;

public interface GradeSetupService extends VersioningService<GradeDTO>{

	List<GradeDTO> findByGradeName(String gradeName, Long companyId);

	GradeDTO findByGradeById(Long gradeId);

	/*add by rim CR HCMS Promotion Acting*/
	List<GradeDTO> findByGradeCode(String gradeCode, Long companyId);
	/*end add by rim CR HCMS Promotion Acting*/
	List<Grade> findByExample(GradeExample example);
	List<Grade> findByExample(GradeExample example, int limit, int offset);
	Integer countByExample(GradeExample example);
	List<GradeDTO> findByInquiry(GradeDTO example);
	List<GradeDTO> findByExample(GradeDTO example);
	List<GradeDTO> findByExample(GradeDTO example, int limit, int offset);
	int countByExample(GradeDTO example);
	List<GradeDTO> findByGradeSetId(Long gradeSetId);
	List<GradeDTO> findByGradeSetIdAndCodeAndName(Long gradeSetId, String gradeCode, String gradeName, int limit, int offset);
	Integer countByGradeSetIdAndCodeAndName(Long gradeSetId, String gradeCode, String gradeName);
	GradeDTO findByIdAndDate(Long id, Date date);
	GradeSetElementDTO findGradeSetElementByJobIdAndGradeId(Long jobId, Long gradeId);
	Integer findMaxGradeSequenceByJobId(Long jobId);
	List<String> findGradeActiveByCompanyId(Long companyId, String grade, int limit, int offset);
	List<String> findSubGradeActiveByCompanyId(Long companyId, String subGrade);
	List<String> findSubGradeByGradeCompanyId(Long companyId, String grade);
	List<String> findStringGradeByCompanyId(Long companyId, Date effectiveOnDate);
	List<Long> findGradeIdsByStringGrade(Long companyId, String grade, String subgrade);
	GradeDTO findGradeByGradeAndSubGrade(Long companyId, Long gradeSetId, 
			String grade, String subGrade);
	BigDecimal getMinSalaryByGradeIdAndBranchId(Long gradeId, Long branchId, Date date);
	BigDecimal getMaxSalaryByGradeIdAndBranchId(Long gradeId, Long branchId, Date date);
	GradeSequenceDTO getGradeSequence(Long fromJobId, Long toJobId, Long fromGradeId, Long toGradeId, Integer level, String status, Long validGradeInJob);
	GradeSetElementDTO getMinOrMaxValidGradeByJobId(Long jobId, MinMax minMax);
	Boolean isGradeToHigherOrEqualGradeFrom(Long gradeIdFrom, Long gradeIdTo, Long jobIdFrom, Long jobIdTo);
	Boolean isFutureExist(Long id);
	public Long findVersionIdByIdAndDate(Long id, Date date);
	public void validateGradeRateSalary(Long branchId, Long gradeId, Date effectiveOnDate) throws ValidationException;
	public List<GradeDTO> findActiveGradeForLov(GradeDTO example, int limit, int offset);
	public Integer countActiveGradeForLov(GradeDTO example);
	Grade findByPrimaryKey(Long gradeId);
	GradeDTO findLastVersionByCode(String code, Long companyId);
	
	GradeRate getGradeRateByGradeIdAndBranchId(Long gradeId, Long branchId, Date date);
	boolean isActiveGrade(Long gradeId, Date effectiveOnDate);
	
	List<String> findGradeActiveByDateAndCompanyId(Long companyId, Date effectiveDate );
	List<String> findSubGradeActiveByDateAndCompanyId(Long companyId, Date effectiveDate );
	
	//recommit
	Boolean isGradeToHigherOrEqualGradeFrom(Long gradeIdFrom, Long gradeIdTo, Long jobIdFrom, Long jobIdTo, Date effectiveOnDate);
	public GradeSetElementDTO getMinOrMaxValidGradeByJobIdAndEffectiveDate(Long jobId, MinMax minMax, Date effectiveOnDate);

	Integer countByExampleCurrentAndFuture(GradeDTO example);

	List<GradeDTO> findByExampleCurrentAndFuture(GradeDTO example, int limit,
			int offset);
}
