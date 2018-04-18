package co.id.fifgroup.personneladmin.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zkplus.spring.SpringUtil;

import co.id.fifgroup.personneladmin.constant.PeopleType;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;
import co.id.fifgroup.workstructure.ui.CommonBranchBandbox;
import co.id.fifgroup.workstructure.ui.CommonEmployeeBandboxAdapter;
import co.id.fifgroup.workstructure.ui.CommonOrganizationBandbox;

import co.id.fifgroup.core.constant.SecurityType;
import co.id.fifgroup.core.security.SecurityFilter;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.CommonPopupTripleBandbox;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateTripleCriteria;
import co.id.fifgroup.core.ui.lookup.TripleBandKeyValue;

public class CommonEmployeeCompanyBandbox extends CommonPopupTripleBandbox
	implements CommonEmployeeBandboxAdapter{

	private static final long serialVersionUID = 1L;
	
	private CommonOrganizationBandbox bnbOrganization;
	private CommonBranchBandbox bnbBranch;
	
	private Boolean isAffco = null;
	private Date effectiveOnDate = DateUtils.truncate(new Date(), Calendar.DATE);
	
	private PersonFinderDelegate personFinderDelegate;
	private boolean canViewExEmployee=false;
	
	private boolean isKeyAsPersonDTO;
	

	public CommonEmployeeCompanyBandbox() {
		personFinderDelegate = new PersonFinderDelegate() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<PersonDTO> find(PersonCriteriaDTO criteria, Long companyId,
					int offset, int limit) {
				return getPersonService().getPersonByCompanyName(criteria, getSecurityService().getSecurityProfile().getWorkspaceBusinessGroupId(), offset, limit);
			}
			
			@Override
			public int count(PersonCriteriaDTO criteria, Long companyId) {
				return getPersonService().countPersonByCompanyName(criteria, getSecurityService().getSecurityProfile().getWorkspaceBusinessGroupId());
			}
		};
		
		
		this.setTitle(Labels.getLabel("common.listOfEmployee"));
		this.setSearchText1(Labels.getLabel("bse.companyName"));
		this.setSearchText2(Labels.getLabel("common.lovEmployeeNumber"));
		this.setSearchText3(Labels.getLabel("common.lovEmployeeName"));
		this.setHeaderLabel1(Labels.getLabel("bse.companyName"));
		this.setHeaderLabel2(Labels.getLabel("common.lovEmployeeNumber"));
		this.setHeaderLabel3(Labels.getLabel("common.lovEmployeeName"));
		this.setConcatDescriptionDescription2(true);
		
		setSearchDelegate(new SerializableSearchDelegateTripleCriteria<PersonDTO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<PersonDTO> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, String searchCriteria3, int limit,
					int offset) {
				SecurityFilter filter = getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter();
				boolean viewExEmployee=false;
				if(canViewExEmployee){
				 viewExEmployee= GlobalVariable.hasPermission(getSecurityProfile(), "common.viewExEmployee");
				}
				SecurityProfile securityProfile = getSecurityService().getSecurityProfile();
				PersonCriteriaDTO criteria = new PersonCriteriaDTO();
				if (securityProfile.getSecurity().getSecurityType().equals(SecurityType.VIEW_HIERARCHY.toString())
						||securityProfile.getSecurity().getSecurityType().equals(SecurityType.VIEW_SINGLE.toString())) {
					criteria.setSupervisorId(securityProfile.getPersonId());
				}
				
				List<PeopleType> peopleTypes = new ArrayList<PeopleType>();
				peopleTypes.add(PeopleType.EMPLOYEE);
				
				if (viewExEmployee) {
					peopleTypes.add(PeopleType.EX_EMPLOYEE);
				}
				
				criteria.setInPeopleTypes(peopleTypes);
				
				if(bnbOrganization != null && bnbOrganization.getKeyValue() != null && bnbOrganization.getKeyValue().getKey() != null) {
					List<Long> inOrganization = new ArrayList<Long>();
					inOrganization.add(Long.parseLong(bnbOrganization.getKeyValue().getKey().toString()));
					criteria.setInOrganizationId(inOrganization);
					criteria.setPersonId(filter.getPersonId());
					criteria.setEffectiveOnDate(effectiveOnDate);
					criteria.setIsAffco(isAffco);
					criteria.setCompanyName(searchCriteria1);
					criteria.setEmployeeNumber(searchCriteria2);
					criteria.setFullName(searchCriteria3);
					return personFinderDelegate.find(criteria, null, offset, limit);
				} else if(bnbBranch != null  && bnbBranch.getKeyValue() != null && bnbBranch.getKeyValue().getKey() != null) {
					List<Long> inOrganization = new ArrayList<Long>();
					inOrganization = getOrganizationInBranch((Long)bnbBranch.getKeyValue().getKey());
					criteria.setInOrganizationId(inOrganization);
					criteria.setPersonId(filter.getPersonId());
					criteria.setCompanyName(searchCriteria1);
					criteria.setEmployeeNumber(searchCriteria2);
					criteria.setFullName(searchCriteria3);
					criteria.setIsAffco(isAffco);
					criteria.setEffectiveOnDate(effectiveOnDate);
					return personFinderDelegate.find(criteria, null, offset, limit);
				} else {
					criteria.setInOrganizationId(filter.getInOrganizations());
					criteria.setNotInOrganizationId(filter.getNotInOrganizations());
					criteria.setGradeExclusions(filter.getGradeExclusions());
					criteria.setPersonId(filter.getPersonId());
					//criteria.setCompanyId(getSecurityService().getSecurityProfile().getWorkspaceCompanyId());
					criteria.setCompanyName(searchCriteria1);
					criteria.setEmployeeNumber(searchCriteria2);
					criteria.setFullName(searchCriteria3);
					criteria.setIsAffco(isAffco);					
					criteria.setEffectiveOnDate(effectiveOnDate);
					return personFinderDelegate.find(criteria, null, offset, limit);					
				}
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2, String searchCriteria3) {
				SecurityFilter filter = getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter();
				boolean viewExEmployee=false;
				if(canViewExEmployee){
				 viewExEmployee= GlobalVariable.hasPermission(getSecurityProfile(), "common.viewExEmployee");
				}
				
				SecurityProfile securityProfile = getSecurityService().getSecurityProfile();
				PersonCriteriaDTO criteria = new PersonCriteriaDTO();
				if (securityProfile.getSecurity().getSecurityType().equals(SecurityType.VIEW_HIERARCHY.toString())
						||securityProfile.getSecurity().getSecurityType().equals(SecurityType.VIEW_SINGLE.toString())) {
					criteria.setSupervisorId(securityProfile.getPersonId());
				}
				
				
				List<PeopleType> peopleTypes = new ArrayList<PeopleType>();
				peopleTypes.add(PeopleType.EMPLOYEE);
				
				if (viewExEmployee) {
					peopleTypes.add(PeopleType.EX_EMPLOYEE);
				}
				
				criteria.setInPeopleTypes(peopleTypes);
				
				if(bnbOrganization != null && bnbOrganization.getKeyValue() != null && bnbOrganization.getKeyValue().getKey() != null) {
					List<Long> inOrganization = new ArrayList<Long>();
					inOrganization.add(Long.parseLong(bnbOrganization.getKeyValue().getKey().toString()));
					criteria.setInOrganizationId(inOrganization);
					criteria.setPersonId(filter.getPersonId());
					criteria.setIsAffco(isAffco);
					criteria.setEffectiveOnDate(effectiveOnDate);
					criteria.setCompanyName(searchCriteria1);
					criteria.setEmployeeNumber(searchCriteria2);
					criteria.setFullName(searchCriteria3);
					return personFinderDelegate.count(criteria, null);
				} else if(bnbBranch != null && bnbBranch.getKeyValue() != null && bnbBranch.getKeyValue().getKey() != null) {
					List<Long> inOrganization = new ArrayList<Long>();
					inOrganization = getOrganizationInBranch((Long)bnbBranch.getKeyValue().getKey());
					criteria.setInOrganizationId(inOrganization);
					criteria.setCompanyName(searchCriteria1);
					criteria.setEmployeeNumber(searchCriteria2);
					criteria.setFullName(searchCriteria3);
					criteria.setPersonId(filter.getPersonId());
					criteria.setIsAffco(isAffco);
					criteria.setEffectiveOnDate(effectiveOnDate);
					return personFinderDelegate.count(criteria, null);
				} else {
					criteria.setInOrganizationId(filter.getInOrganizations());
					criteria.setNotInOrganizationId(filter.getNotInOrganizations());
					criteria.setGradeExclusions(filter.getGradeExclusions());
					criteria.setPersonId(filter.getPersonId());
					//criteria.setCompanyId(getSecurityService().getSecurityProfile().getWorkspaceCompanyId());
					criteria.setCompanyName(searchCriteria1);
					criteria.setEmployeeNumber(searchCriteria2);
					criteria.setFullName(searchCriteria3);
					criteria.setIsAffco(isAffco);
					criteria.setEffectiveOnDate(effectiveOnDate);
					return personFinderDelegate.count(criteria, null);					
				}
			}

			@Override
			public void mapper(TripleBandKeyValue keyValue, PersonDTO data) {
				if(isKeyAsPersonDTO()){
					keyValue.setKey(data);
				}
				else {
					keyValue.setKey(data.getPersonId());
				}
				keyValue.setValue(data.getPrimaryAssignmentDTO().getCompanyName());
				keyValue.setDescription(data.getEmployeeNumber());
				keyValue.setDescription2(data.getFullName());
			}
		});
	}
	
	public OrganizationSetupService getOrganizationService() {
		return (OrganizationSetupService) SpringUtil.getBean("organizationSetupServiceImpl");
	}
	
	public SecurityService getSecurityService() {
		return (SecurityService) SpringUtil.getBean("securityServiceImpl");
	}
	
	public PersonService getPersonService() {
		return (PersonService) SpringUtil.getBean("personService");
	}

	public CommonPopupDoubleBandbox getBnbOrganization() {
		return bnbOrganization;
	}

	public void setBnbOrganization(CommonOrganizationBandbox bnbOrganization) {
		this.bnbOrganization = bnbOrganization;
		if (bnbOrganization != null) 
			this.bnbOrganization.setBnbEmployee(this);
	}

	public CommonPopupDoubleBandbox getBnbBranch() {
		return bnbBranch;
	}

	public void setBnbBranch(CommonBranchBandbox bnbBranch) {
		this.bnbBranch = bnbBranch;
		if (bnbBranch != null)
			this.bnbBranch.setBnbEmployee(this);
		
	}
	
	public List<Long> getOrganizationInBranch(Long branchId) {
		List<Organization> orgs = getOrganizationService().getOrganizationByBranchAndSecurityFilter(getSecurityProfile().getWorkspaceCompanyId(), 
				branchId, 
				null, 
				null, 
				getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getInOrganizations(), 
				getSecurityService().getSecurityProfile().getSecurity().getSecurityFilter().getNotInOrganizations(),
				null,
				Integer.MAX_VALUE,  0);
		List<Long> result = new ArrayList<>();
		if (orgs.isEmpty())
			return Arrays.asList(0L);
		for (Organization org : orgs) {
			if(org.getId()!=null){
			result.add(org.getId());
			}
		}
		
		return result;
		
	}

	public Boolean getIsAffco() {
		return isAffco;
	}

	public void setIsAffco(Boolean isAffco) {
		this.isAffco = isAffco;
	}

	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}

	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}
	
	private SecurityProfile getSecurityProfile() {
		return getSecurityService().getSecurityProfile();
	}

	@Override
	public void clearSelection() {
		setRawValue(null);		
	}

	public PersonFinderDelegate getPersonFinderDelegate() {
		return personFinderDelegate;
	}

	public void setPersonFinderDelegate(PersonFinderDelegate personFinderDelegate) {
		this.personFinderDelegate = personFinderDelegate;
	}
	public boolean isCanViewExEmployee() {
		return canViewExEmployee;
	}

	public void setCanViewExEmployee(boolean canViewExEmployee) {
		this.canViewExEmployee = canViewExEmployee;
	}
	
	public void setRawValue(Long employeeId, String companyName, String employeeNumber, String employeeName){
		TripleBandKeyValue keyValue = new TripleBandKeyValue();
		keyValue.setKey(employeeId);
		keyValue.setValue(companyName);
		keyValue.setDescription(employeeNumber);
		keyValue.setDescription2(employeeName);
		super.setRawValue(keyValue);
	}

	public boolean isKeyAsPersonDTO() {
		return isKeyAsPersonDTO;
	}

	public void setKeyAsPersonDTO(boolean isKeyAsPersonDTO) {
		this.isKeyAsPersonDTO = isKeyAsPersonDTO;
	}
}
