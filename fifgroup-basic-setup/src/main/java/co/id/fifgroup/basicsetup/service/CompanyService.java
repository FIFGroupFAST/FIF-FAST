package co.id.fifgroup.basicsetup.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.basicsetup.common.HistoricalService;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.dto.CompanyDTO;

public interface CompanyService extends HistoricalService<CompanyDTO> {

	List<Company> getCompanyByExample(CompanyExample companyExample);
	List<CompanyDTO> getCompaniesByEffectiveOnDateAndCompanyName(Date effectiveOnDate, String companyName);
	int getCountCompaniesByEffectiveOnDateAndCompanyName(Date effectiveOnDate, String companyName);
	CompanyDTO getDetailCompany(CompanyDTO companyDto);
	void save(CompanyDTO companyDto, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception;
	Company getCompanyBySequenceNumber(Long companySequenceNumber);
	void delete(CompanyDTO companyDto);
	Long getBusinessGroupIdByCompany(Long companyId);
	String getCompanyCodeById(Long id);
	String getCompanyNameById(Long id);
	Company getCompanyByIdAndEffectiveDate(Long companyId, Date effectiveDate);
	String getCompanyGlCodeById(Long id);
	List<Long> getCompanyInBusinessGroup(Long companyId);
	String getOtherInfoCompany(Long companyId, Date effectiveOnDate, String otherInfoCode);
	CompanyDTO findCompanyById(Long companyId, Date date);
	List<CompanyDTO> findCompanyCommon(CompanyDTO companyDTO, int limit, int offset);
	Integer countCompanyCommon(CompanyDTO companyDTO);
	Company getCompanyByCode(String companyCode);

}
