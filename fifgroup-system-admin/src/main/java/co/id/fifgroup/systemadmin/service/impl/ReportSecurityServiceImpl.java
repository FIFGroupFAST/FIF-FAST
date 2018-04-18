package co.id.fifgroup.systemadmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.id.fifgroup.core.constant.SecurityType;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.systemadmin.finder.ReportSecurityFinder;
import co.id.fifgroup.systemadmin.service.ReportSecurityService;

@Service
public class ReportSecurityServiceImpl implements ReportSecurityService {
	
	@Autowired
	private ReportSecurityFinder reportSecurityFinder;
	@Autowired
	private SecurityService securityServiceImpl;
	
	@Override
	public List<KeyValue> getCompany(Long responsibilityId,
			Long currentCompanyId, String companyName, int limit, int offset) {
		return reportSecurityFinder.getCompany(responsibilityId, currentCompanyId, companyName, new RowBounds(offset, limit));
	}

	@Override
	public List<KeyValue> getBranch(Long selectedCompanyId, Long responsibilityId, String branchCode, String branchName, int limit, int offset) {
		
		List<KeyValue> branches = new ArrayList<KeyValue>();
				
		if(selectedCompanyId != null){
			String currentSecurityType = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType();
			if(!currentSecurityType.equals(SecurityType.VIEW_ALL.toString())){
				branches.add(new KeyValue(securityServiceImpl.getSecurityProfile().getBranchId(), securityServiceImpl.getSecurityProfile().getBranchName(), securityServiceImpl.getSecurityProfile().getBranchName()));
			}else{
				branches = reportSecurityFinder.getBranches(selectedCompanyId, branchCode, branchName, new RowBounds(offset, limit));
			}
		}else{
			branches = reportSecurityFinder.getBranchByResponsibility(responsibilityId, securityServiceImpl.getSecurityProfile().getPersonId(), branchCode, branchName, new RowBounds(offset, limit));
		}
		
		return branches;
	}

	@Override
	public int countCompany(Long responsibilityId, Long currentCompanyId,
			String companyName) {
		return reportSecurityFinder.countCompany(responsibilityId, currentCompanyId, companyName);
	}

	@Override
	public int countBranch(Long selectedCompanyId, Long responsibilityId, String branchCode,
			String branchName) {
		if(selectedCompanyId != null){
			String currentSecurityType = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType();
			if(!currentSecurityType.equals(SecurityType.VIEW_ALL.toString())){
				return 1;
			}else{
				return reportSecurityFinder.countBranches(selectedCompanyId, branchCode, branchName);
			}
		}else{
			return reportSecurityFinder.countBranchByResponsibility(responsibilityId, securityServiceImpl.getSecurityProfile().getPersonId(), branchCode, branchName);
		}
	}

	@Override
	public List<KeyValue> getOrganization(Long selectedCompanyId,
			Long selectedBranchId, Long locationId, String organizationCode, String organizationName, int limit,
			int offset) {
		
		List<KeyValue> organizations = new ArrayList<>();
		String securityType = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType();
		if(securityType.equals(SecurityType.VIEW_SINGLE.toString()) ||
				securityType.equals(SecurityType.VIEW_OWN.toString())){
			organizations.add(new KeyValue(securityServiceImpl.getSecurityProfile().getOrganizationId(), securityServiceImpl.getSecurityProfile().getOrganizationCode(), securityServiceImpl.getSecurityProfile().getOrganizationName()));
		}else{
			List<Long> inOrgId = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations();
			List<Long> notInOrgId = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations();
			organizations = reportSecurityFinder.getOrganizationByBranch(selectedCompanyId, selectedBranchId, locationId, organizationCode, organizationName, inOrgId, notInOrgId, new RowBounds(offset, limit));					
		}
		
		
		return organizations;
	}

	@Override
	public int countOrganization(Long selectedCompanyId, Long selectedBranchId, Long locationId,
			String organizationCode, String organizationName) {
		String securityType = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType();
		if(securityType.equals(SecurityType.VIEW_SINGLE.toString()) ||
				securityType.equals(SecurityType.VIEW_OWN.toString())){
			return 1;
		}else{
			List<Long> inOrgId = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations();
			List<Long> notInOrgId = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations();
			return reportSecurityFinder.countOrganizationByBranch(selectedCompanyId, selectedBranchId, locationId, organizationCode, organizationName, inOrgId, notInOrgId);					
		}
	}

	@Override
	public List<KeyValue> getLocation(Long responsibilityId,
			Long selectedCompanyId, Long seletedBranchId, String locationCode, String locationName, int limit,
			int offset) {
		
		List<KeyValue> locations = new ArrayList<KeyValue>();
		
		if(selectedCompanyId != null){
			String securityType = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType();
			if(securityType.equals(SecurityType.VIEW_SINGLE.toString()) ||
					securityType.equals(SecurityType.VIEW_OWN.toString())){
				KeyValue currentLocation = reportSecurityFinder.getCurrentLocation(securityServiceImpl.getSecurityProfile().getOrganizationId());
				locations.add(currentLocation);
				return locations;
			}else{
				return reportSecurityFinder.getLocation(selectedCompanyId, seletedBranchId, locationCode, locationName, new RowBounds(offset, limit));
			}
		}else{
			return reportSecurityFinder.getLocation(selectedCompanyId, seletedBranchId, locationCode, locationName, new RowBounds(offset, limit));
		}
	}

	@Override
	public int countLocation(Long responsibilityId, Long selectedCompanyId,
			Long seletedBranchId, String locationCode, String locationName) {
		if(selectedCompanyId != null){
			String securityType = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType();
			if(securityType.equals(SecurityType.VIEW_SINGLE.toString()) ||
					securityType.equals(SecurityType.VIEW_OWN.toString())){
				return 1;
			}else{
				return reportSecurityFinder.countLocation(selectedCompanyId, seletedBranchId, locationCode, locationName);
			}
		}else{
			return reportSecurityFinder.countLocation(selectedCompanyId, seletedBranchId, locationCode, locationName);
		}
	}

	@Override
	public List<KeyValue> getEmployee(Long companyId, Long branchId,
			Long locationId, Long organizationId, String employeeNumber, String fullName,
			int limit, int offset) {
		
		List<KeyValue> employees = new ArrayList<KeyValue>();
		
		String securityType = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType();
		if(securityType.equals(SecurityType.VIEW_SINGLE.toString()) ||
				securityType.equals(SecurityType.VIEW_OWN.toString())){
			employees.add(new KeyValue(securityServiceImpl.getSecurityProfile().getPersonId(), securityServiceImpl.getSecurityProfile().getEmployeeNumber(), securityServiceImpl.getSecurityProfile().getFullName()));
			return employees;
		}else{
			List<Long> inOrgId = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations();
			List<Long> notInOrgId = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations();
			return reportSecurityFinder.getEmployee(companyId, branchId, locationId, organizationId, inOrgId, notInOrgId, employeeNumber, fullName, new RowBounds(offset, limit));
		}
	}

	@Override
	public int countEmployee(Long companyId, Long branchId, Long locationId, Long organizationId, String employeeNumber, String fullName) {
		String securityType = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType();
		if(securityType.equals(SecurityType.VIEW_SINGLE.toString()) ||
				securityType.equals(SecurityType.VIEW_OWN.toString())){
			return 1;
		}else{
			List<Long> inOrgId = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations();
			List<Long> notInOrgId = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations();
			return reportSecurityFinder.countEmployee(companyId, branchId, locationId, organizationId, inOrgId, notInOrgId, employeeNumber, fullName);
		}
	}

	@Override
	public List<KeyValue> getJob(Long companyId, Long responsibilityId, String jobCode, String jobName, int limit, int offset) {
		Long personId = securityServiceImpl.getSecurityProfile().getPersonId();
		return reportSecurityFinder.getJob(companyId, responsibilityId, personId, jobCode, jobName, new RowBounds(offset, limit));
	}

	@Override
	public int countJob(Long companyId, Long responsibilityId, String jobCode, String jobName) {
		Long personId = securityServiceImpl.getSecurityProfile().getPersonId();
		return reportSecurityFinder.countJob(companyId, responsibilityId, personId, jobCode, jobName);
	}

	@Override
	public List<KeyValue> getEmployeeCWK(Long companyId, Long branchId,
			Long locationId, Long organizationId, String employeeNumber,
			String fullName, int limit, int offset) {
		List<KeyValue> employees = new ArrayList<KeyValue>();
		
		String securityType = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType();
		if(securityType.equals(SecurityType.VIEW_SINGLE.toString()) ||
				securityType.equals(SecurityType.VIEW_OWN.toString())){
			employees.add(new KeyValue(securityServiceImpl.getSecurityProfile().getPersonId(), securityServiceImpl.getSecurityProfile().getEmployeeNumber(), securityServiceImpl.getSecurityProfile().getFullName()));
			return employees;
		}else{
			List<Long> inOrgId = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations();
			List<Long> notInOrgId = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations();
			return reportSecurityFinder.getEmployeeCWK(companyId, branchId, locationId, organizationId, inOrgId, notInOrgId, employeeNumber, fullName, new RowBounds(offset, limit));
		}
	}

	@Override
	public int countEmployeeCWK(Long companyId, Long branchId, Long locationId,
			Long organizationId, String employeeNumber, String fullName) {
		String securityType = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType();
		if(securityType.equals(SecurityType.VIEW_SINGLE.toString()) ||
				securityType.equals(SecurityType.VIEW_OWN.toString())){
			return 1;
		}else{
			List<Long> inOrgId = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations();
			List<Long> notInOrgId = securityServiceImpl.getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations();
			return reportSecurityFinder.countEmployeeCWK(companyId, branchId, locationId, organizationId, inOrgId, notInOrgId, employeeNumber, fullName);
		}
	}


}
