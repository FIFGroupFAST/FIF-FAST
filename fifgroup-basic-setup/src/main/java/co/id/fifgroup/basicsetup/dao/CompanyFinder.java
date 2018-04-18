package co.id.fifgroup.basicsetup.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.basicsetup.dto.CompanyDTO;

public interface CompanyFinder {

	public Long getCompanyId();
	public int getCountCompaniesByEffectiveOnDateAndCompanyName(@Param("effectiveOnDate") Date effectiveOnDate, @Param("companyName") String companyName);
	public List<CompanyDTO> getCompaniesByEffectiveOnDateAndCompanyName(@Param("effectiveOnDate") Date effectiveOnDate, @Param("companyName") String companyName);
	public CompanyDTO getDetailCompany(CompanyDTO companyDto);
	public Long getBusinessGroupIdByCompany(@Param("companyId") Long companyId);
	public String getOtherInfoValueCompany(@Param("companyId") Long companyId, @Param("effectiveDate") Date effectiveOnDate, @Param("infoId") Long otherInfoDetailId);
	public String getCompanyCodeById(Long id);
	public String getCompanyNameById(Long id);
	public String getCompanyGlCodeById(Long id);
	public List<Long> getCompanyInBusinessGroup(@Param("companyId") Long companyId);
	public CompanyDTO getCompanySeqNumAndBusinessGroupId(@Param("companyId") Long companyId, @Param("effectiveOnDate") Date effectiveOnDate);
	CompanyDTO getCompanyById(@Param("companyId") Long companyId, @Param("effectiveDate") Date effectiveDate);
	List<CompanyDTO> findCompanyCommon(CompanyDTO companyDTO, RowBounds rowBounds);
	Integer countCompanyCommon(CompanyDTO companyDTO);

} 
