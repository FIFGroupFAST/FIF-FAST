package co.id.fifgroup.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zkoss.zul.DefaultTreeModel;

import co.id.fifgroup.core.constant.FunctionAccessType;
import co.id.fifgroup.core.constant.LoginStatus;
import co.id.fifgroup.core.constant.MenuItemType;
import co.id.fifgroup.core.constant.SecurityType;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.security.Security;
import co.id.fifgroup.core.security.SecurityFilter;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.AuthorizedMenuDTO;
import co.id.fifgroup.core.ui.SimpleTreeNode;
import co.id.fifgroup.core.ui.SimpleTreeNodeCollection;
import co.id.fifgroup.core.ui.TreeInfo;
import co.id.fifgroup.core.ui.lookup.KeyValue;

//@Service("securityServiceImpl")
public class MockSecurityService implements SecurityService{

	@Override
	public SecurityProfile getSecurityProfile() {
		try {
			SecurityProfile securityProfile = (SecurityProfile) SecurityContextHolder.getContext().getAuthentication().getDetails();
			return securityProfile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public SecurityProfile getSecurityProfile(String userName, Long companyId) {
		SecurityProfile profile = new SecurityProfile();
		profile.setWorkspaceBusinessGroupId(1L);
		profile.setWorkspaceBusinessGroupId(1L);
		profile.setWorkspaceCompanyId(companyId);
		profile.setCompanyId(companyId);
		profile.setPersonId(15L);
		profile.setUserId(22L);
		profile.setEmployeeNumber(userName);
		profile.setPersonUUID(UUID.randomUUID());
		
		return profile;
	}

	@Override
	public List<KeyValue> getEmployeeAuthorizedCompanies(String username) {
		List<KeyValue> autorizedCompanies = new ArrayList<KeyValue>();
		autorizedCompanies.add(new KeyValue(1L, "FIF", "FIF"));
		return autorizedCompanies;
	}

	@Override
	public List<KeyValue> getResponsibilityByCompany(String username,
			Long companyId, LoginStatus loginStatus) {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		keyValues.add(new KeyValue(1L, "Responsibility", "Responsibility"));
		return keyValues;
	}

	@Override
	public Security getSecurityByResponsibilityId(Long respId, Long companyId,
			boolean isAssignment) {
		Security security = new Security();
		security.setBusinessGroupId(1L);
		security.setResponsibilityId(respId);
		security.setResponsibilityName("Responsibility Name");
		security.setMenuId(1L);
		security.setTaskGroupId(1L);
		security.setFunctionDefaultAccess(FunctionAccessType.FULL.name());
		security.setSecurityType(SecurityType.VIEW_ALL.name());
		security.setOrgFilter(null);
		security.setRecursive(false);
		security.setOrganizationLevelId(0L);
		security.setSecurityFilter(new SecurityFilter());
		security.setFieldPermissions(new ArrayList<String>());
		security.setMenuItemExclusions(new ArrayList<Long>());
		security.setFunctionItemExclusions(new ArrayList<Long>());
		security.setFunctionPermissions(new ArrayList<FunctionPermission>());
		return security;
	}

	@Override
	public int countActiveUserByUsername(String userName) {
		return 1;
	}

	@Override
	public DefaultTreeModel<TreeInfo> getAuthorizedTreeModelMenuByRootMenuId(
			Long menuId, List<Long> menuExclusions,
			List<Long> functionExclusions) {
		SimpleTreeNodeCollection<TreeInfo> children = buildNode(menuId, menuExclusions, functionExclusions);
    	SimpleTreeNode<TreeInfo> treeNode = new SimpleTreeNode<TreeInfo>(new TreeInfo(null, null), children, true);
    	return new DefaultTreeModel<TreeInfo>(treeNode);
	}
	
	private SimpleTreeNodeCollection<TreeInfo> buildNode(Long rootMenuId, List<Long> menuExclusions, List<Long> functionExclusions){
		SimpleTreeNodeCollection<TreeInfo> child =  new SimpleTreeNodeCollection<>();
		child.addAll(populateMenu(rootMenuId, menuExclusions, functionExclusions));
		return child;
	}
	
	private SimpleTreeNodeCollection<TreeInfo> populateMenu(Long rootMenuId, List<Long> menuExclusions, List<Long> functionExclusions){
		SimpleTreeNodeCollection<TreeInfo> ch = new SimpleTreeNodeCollection<>();

		List<AuthorizedMenuDTO> menus = getMenuByRootId(rootMenuId);
    	
    	for (AuthorizedMenuDTO m : menus) {
    		String type = m.getMenuItemType();
    		if(type.equals(MenuItemType.SUBMENU.toString())){
    			SimpleTreeNodeCollection<TreeInfo> child =  buildNode(m.getActionId(), menuExclusions, functionExclusions);
        		SimpleTreeNode<TreeInfo> node = new SimpleTreeNode<TreeInfo>(new TreeInfo(m.getDisplayName(), MenuItemType.SUBMENU.toString(), m), child, true);
    			ch.add(node);
    		}else{
    			SimpleTreeNode<TreeInfo> node = new SimpleTreeNode<TreeInfo>(new TreeInfo(m.getDisplayName(), MenuItemType.FUNCTION.toString(), m));
    			ch.add(node);
    		}
    		
		}
    	
    	return ch;
	}

	@Override
	public FunctionPermission getFunctionPermissionByFunctionId(Long functionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmployee(String userName) {
		return true;
	}

	@Override
	public List<KeyValue> getNonEmployeeAuthorizedCompaniesByUserName(
			String userName) {
		List<KeyValue> autorizedCompanies = new ArrayList<KeyValue>();
		autorizedCompanies.add(new KeyValue(1L, "FIF", "FIF"));
		return autorizedCompanies;
	}

	@Override
	public int isBusinessGroupAdmin(String userName) {
		return 0;
	}

	@Override
	public List<KeyValue> getBusinessGroup() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		keyValues.add(new KeyValue(1L, "FIFGROUP", "FIFGROUP"));
		return keyValues;
	}

	@Override
	public List<KeyValue> getSuperUserResponsibility() {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		keyValues.add(new KeyValue(1L, "Responsibility", "Responsibility"));
		return keyValues;
	}

	@Override
	public Security getSecurityByResponsibilityId(Long respId) {
		Security security = new Security();
		security.setBusinessGroupId(1L);
		security.setResponsibilityId(respId);
		security.setResponsibilityName("Responsibility Name");
		security.setMenuId(1L);
		security.setTaskGroupId(1L);
		security.setFunctionDefaultAccess(FunctionAccessType.FULL.name());
		security.setSecurityType(SecurityType.VIEW_ALL.name());
		security.setOrgFilter(null);
		security.setRecursive(false);
		security.setOrganizationLevelId(0L);
		security.setSecurityFilter(new SecurityFilter());
		security.setFieldPermissions(null);
		security.setMenuItemExclusions(null);
		security.setFunctionItemExclusions(null);
		security.setFunctionPermissions(null);
		return security;
	}

	@Override
	public Long getUserIdByUsername(String username) {
		return 1L;
	}
	
	private List<AuthorizedMenuDTO> getMenuByRootId(Long rootId) {
		List<AuthorizedMenuDTO> menus = new ArrayList<AuthorizedMenuDTO>();
		// OCOP
		menus.add(new AuthorizedMenuDTO(2L, "", "SUBMENU", 0, "OCOP", "OCOP", 1L, null, "OCOP"));
		menus.add(new AuthorizedMenuDTO(3L, "~./hcms/ocop/ocop_setup_inquiry.zul", "FUNCTION", 1, "OCOP Setup", "OCOP SETUP", 2L, null, "OCOP"));
		menus.add(new AuthorizedMenuDTO(4L, "~./hcms/ocop/upload_ocop_plafond_search.zul", "FUNCTION", 2, "Upload OCOP Plafond", "Upload OCOP Plafond", 2L, null, "OCOP"));
		menus.add(new AuthorizedMenuDTO(10L, "~./hcms/ocop/ocop_sequence_setup.zul", "FUNCTION", 8, "OCOP Sequence Setup", "OCOP Sequence Setup", 2L, null, "OCOP"));
		menus.add(new AuthorizedMenuDTO(5L, "~./hcms/ocop/vendor_matrix_setup.zul", "FUNCTION", 3, "Vendor Matrix Setup", "Vendor Matrix Setup", 2L, null, "OCOP"));
		menus.add(new AuthorizedMenuDTO(6L, "~./hcms/ocop/upload_vendor_matrix_search.zul", "FUNCTION", 4, "Upload Vendor Matrix", "Upload Vendor Matrix", 2L, null, "OCOP"));
		menus.add(new AuthorizedMenuDTO(11L, "~./hcms/ocop/eligibility_additional_setup.zul", "FUNCTION", 9, "Eligibility Additional Setup", "Eligibility Additional Setup", 2L, null, "OCOP"));
		menus.add(new AuthorizedMenuDTO(7L, "~./hcms/ocop/entry_ocop_eligibility_additional_inquiry.zul", "FUNCTION", 5, "Entry OCOP Eligibility Additional", "Entry OCOP Eligibility Additional", 2L, null, "OCOP"));
		menus.add(new AuthorizedMenuDTO(8L, "~./hcms/ocop/upload_ocop_eligibility_additional_search.zul", "FUNCTION", 6, "Upload OCOP Eligibility Additional", "Upload OCOP Eligibility Additional", 2L, null, "OCOP"));
		menus.add(new AuthorizedMenuDTO(9L, "~./hcms/ocop/ocop_transaction_inquiry.zul", "FUNCTION", 7, "OCOP Transaction", "OCOP Transaction", 2L, null, "OCOP"));
		menus.add(new AuthorizedMenuDTO(12L, "~./hcms/ocop/main_demo_zkee.zul", "FUNCTION", 7, "Main Demo", "Main Demo", 2L, null, "OCOP"));
		
		//HOUSING 
		menus.add(new AuthorizedMenuDTO(20L, "", "SUBMENU", 20, "Housing", "Housing", 1L, null, "Housing"));
		menus.add(new AuthorizedMenuDTO(21L, "~./hcms/housing/housing_setup.zul", "FUNCTION", 21, "Housing Setup", "Housing Setup", 20L, null, "Housing"));
		menus.add(new AuthorizedMenuDTO(25L, "~./hcms/housing/housing_plafond_upload_inquiry.zul", "FUNCTION", 25, "Housing Plafond Upload Inquiry", "Housing Plafond Upload Inquiry", 20L, null, "Housing"));
		menus.add(new AuthorizedMenuDTO(22L, "~./hcms/housing/housing_placement_request_inquiry.zul", "FUNCTION", 22, "Housing Placement Request Inquiry", "Housing Placement Request Inquiry", 20L, null, "Housing"));
		menus.add(new AuthorizedMenuDTO(23L, "~./hcms/housing/housing_contract_creation_inquiry.zul", "FUNCTION", 23, "Housing Contract Creation Inquiry", "Housing Contract Creation Inquiry", 20L, null, "Housing"));
		menus.add(new AuthorizedMenuDTO(24L, "~./hcms/housing/boarding_request_inquiry.zul", "FUNCTION", 24, "Boarding Request Inquiry", "Boarding Request Inquiry", 20L, null, "Housing"));
		
		//DISCIPLINARY LETTER
		menus.add(new AuthorizedMenuDTO(30L, "~./hcms/module/file.zul", "SUBMENU", 30, "Disciplinary Letter", "Disciplinary Letter", 1L, null, "Disciplinary Letter"));
		menus.add(new AuthorizedMenuDTO(32L, "~./hcms/disciplinary-letter/disciplinary_letter_setup_inquiry.zul", "FUNCTION", 32, "Disciplinary Letter Setup Inquiry", "Disciplinary Letter Setup Inquiry", 30L, null, "Disciplinary Letter"));
		menus.add(new AuthorizedMenuDTO(31L, "~./hcms/disciplinary-letter/disciplinary_reason_setup_inquiry.zul", "FUNCTION", 31, "Reason Setup Inquiry", "Reason Setup Inquiry", 30L, null, "Disciplinary Letter"));
		menus.add(new AuthorizedMenuDTO(34L, "~./hcms/disciplinary-letter/disciplinary_letter_submission_inquiry.zul", "FUNCTION", 34, "Disciplinary Letter Submission Inquiry", "Disciplinary Letter Submission Inquiry", 30L, null, "Disciplinary Letter"));
		menus.add(new AuthorizedMenuDTO(35L, "~./hcms/disciplinary-letter/disciplinary_letter_suggestion_request.zul", "FUNCTION", 35, "Disciplinary Letter Suggestion Request", "Disciplinary Letter Suggestion Request", 30L, null, "Disciplinary Letter"));
		
		//DOCUMENT LETTER
		menus.add(new AuthorizedMenuDTO(40L, "", "SUBMENU", 40, "Document Letter", "Document Letter", 1L, null, "Document Letter"));
		menus.add(new AuthorizedMenuDTO(41L, "~./hcms/document-letter/document_parameter_inquiry.zul", "FUNCTION", 41, "Document and Letter Parameter Setup", "Document and Letter Parameter Setup", 40L, null, "Document Letter"));
		menus.add(new AuthorizedMenuDTO(42L, "~./hcms/document-letter/document_type_inquiry.zul", "FUNCTION", 42, "Document and Letter Setup Inquiry", "Document and Letter Setup Inquiry", 40L, null, "Document Letter"));
		menus.add(new AuthorizedMenuDTO(43L, "~./hcms/document-letter/document_and_letter_mapping_inquiry.zul", "FUNCTION", 43, "Document and Letter Mapping Inquiry", "Document and Letter Mapping Inquiry", 40L, null, "Document Letter"));
		
		//WORKING EQUIPMENT
		menus.add(new AuthorizedMenuDTO(50L, "", "SUBMENU", 50, "Working Equipment", "Working Equipment", 1L, null, "Working Equipment"));
		menus.add(new AuthorizedMenuDTO(51L, "~./hcms/working-equipment/working_equipment_setup.zul", "FUNCTION", 51, "Working Equipment Setup", "Working Equipment Setup", 50L, null, "Working Equipment"));
		menus.add(new AuthorizedMenuDTO(52L, "~./hcms/working-equipment/upload_working_equipment_plafond_inquiry.zul", "FUNCTION", 52, "Upload Working Equipment Eligibility", "Upload Working Equipment Eligibility", 50L, null, "Working Equipment"));
		menus.add(new AuthorizedMenuDTO(53L, "~./hcms/working-equipment/working_equipment_request_inquiry.zul", "FUNCTION", 53, "Request Working Equipment", "Request Working Equipment", 50L, null, "Working Equipment"));
		menus.add(new AuthorizedMenuDTO(54L, "~./hcms/working-equipment/working_equipment_order_inquiry.zul", "FUNCTION", 54, "Order Working Equipment", "Order Working Equipment", 50L, null, "Working Equipment"));
		menus.add(new AuthorizedMenuDTO(55L, "~./hcms/working-equipment/working_equipment_receiving_inquiry.zul", "FUNCTION", 55, "Receive Working Equipment", "Receive Working Equipment", 50L, null, "Working Equipment"));
		menus.add(new AuthorizedMenuDTO(56L, "~./hcms/working-equipment/working_equipment_assignment_inquiry.zul", "FUNCTION", 56, "Assign to Employee or CWK", "Assign to Employee or CWK", 50L, null, "Working Equipment"));
		menus.add(new AuthorizedMenuDTO(57L, "~./hcms/working-equipment/working_equipment_inventory_inquiry.zul", "FUNCTION", 57, "Maintain Inventory", "Maintain Inventory", 50L, null, "Working Equipment"));
		menus.add(new AuthorizedMenuDTO(58L,"~./hcms/working-equipment/working_equipment_type_setup_inquiry.zul","FUNCTION",58,"Working Equipment Type Setup","Working Equipment Type Setup",50L,null,"Working Equipment"));
		
		//CWK
		menus.add(new AuthorizedMenuDTO(60L, "~./hcms/module/file.zul", "SUBMENU", 60, "CWK", "CWK", 1L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(81L, "~./hcms/hcms-contingent-worker/mapping_mpp_cwk_and_employee_inquiry.zul", "FUNCTION", 81, "Mapping MPP of CWK and Employee", "Mapping MPP of CWK and Employee", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(63L, "~./hcms/hcms-contingent-worker/job_and_vendor_matrix_setup.zul", "FUNCTION", 63, "Job and Vendor Matrix Setup", "Job and Vendor Matrix Setup", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(64L, "~./hcms/hcms-contingent-worker/search_upload_job_and_vendor_matrix.zul", "FUNCTION", 64, "Upload Job and Vendor Matrix", "Upload Job and Vendor Matrix", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(61L, "~./hcms/hcms-contingent-worker/cwk_requisition_inquiry.zul", "FUNCTION", 61, "CWK Requisition", "CWK Requisition", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(62L, "~./hcms/hcms-contingent-worker/cwk_requisition_request_cancellation.zul", "FUNCTION", 62, "CWK Requisition Cancelation", "CWK Requisition Cancelation", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(78L, "~./hcms/workstructure/job_setup.zul", "FUNCTION", 78, "Job Setup", "Job Setup", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(65L, "~./hcms/hcms-contingent-worker/pkwt_inquiry.zul", "FUNCTION", 65, "PKWT Setup", "PKWT Setup", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(82L, "~./hcms/hcms-contingent-worker/recruitment_process_inquiry.zul", "FUNCTION", 81, "Recruitment Process Setup", "Recruitment Process Setup", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(67L, "~./hcms/hcms-contingent-worker/hire_new_cwk_inquiry.zul", "FUNCTION", 67, "Hire New CWK", "Hire New CWK", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(68L, "~./hcms/hcms-contingent-worker/search_upload_cwk_applicant.zul", "FUNCTION", 68, "Upload New CWK", "Upload New CWK", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(66L, "~./hcms/hcms-contingent-worker/generate_pkwt_inquiry.zul", "FUNCTION", 66, "PKWT Generation", "PKWT Generation", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(69L, "~./hcms/hcms-contingent-worker/cwk_enter_and_maintain_inquiry.zul", "FUNCTION", 69, "Contingent Worker Enter and Maintain", "Contingent Worker Enter and Maintain", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(80L, "~./hcms/hcms-contingent-worker/cwk_career_path_setup.zul", "FUNCTION", 80, "CWK Career Path Setup", "CWK Career Path Setup", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(70L, "~./hcms/hcms-contingent-worker/transfer_cwk_inquiry.zul", "FUNCTION", 70, "Transfer CWK", "Transfer CWK", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(72L, "~./hcms/hcms-contingent-worker/pkwt_extension_inquiry.zul", "FUNCTION", 72, "Request PKWT Extention", "Request PKWT Extention", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(75L, "~./hcms/hcms-contingent-worker/search_upload_assignment.zul", "FUNCTION", 75, "Upload CWK Assignment ", "Upload CWK Assignment", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(73L, "~./hcms/hcms-contingent-worker/transfer_cwk_within_group_inquiry.zul", "FUNCTION", 73, "Transfer CWK Within Group", "Transfer CWK Within Group", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(77L, "~./hcms/hcms-contingent-worker/termination_reason_inquiry.zul", "FUNCTION", 77, "Termination Reason Setup", "Termination Reason Setup", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(71L, "~./hcms/hcms-contingent-worker/termination_cwk_inquiry.zul", "FUNCTION", 71, "Terminate CWK", "Terminate CWK", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(74L, "~./hcms/hcms-contingent-worker/hire_cwk_to_probation_inquiry.zul", "FUNCTION", 74, "Hire CWK to Probation", "Hire CWK to Probation", 60L, null, "CWK"));
//		menus.add(new AuthorizedMenuDTO(79L, "~./hcms/hcms-contingent-worker/termination_exit_interview_inquiry.zul", "FUNCTION", 79, "Exit Interview", "Exit Interview", 60L, null, "CWK"));
		menus.add(new AuthorizedMenuDTO(83L, "~./hcms/hcms-contingent-worker/vendor_group_setup.zul", "FUNCTION", 83, "Outsource Vendor Group Setup", "Outsource Vendor Group Setup", 60L, null, "CWK"));

		
		//COMPETENCY
		menus.add(new AuthorizedMenuDTO(75L, "", "SUBMENU", 20, "Competency", "Competency", 1L, null, "Competency"));
		menus.add(new AuthorizedMenuDTO(21L, "~./hcms/competency/proficiency_level_setup_inquiry.zul", "FUNCTION", 21, "Proficiency Level Setup", "Proficiency Level Setup", 75L, null, "Competency"));
		menus.add(new AuthorizedMenuDTO(25L, "~./hcms/competency/competency_setup_inquiry.zul", "FUNCTION", 22, "Competency Setup", "Competency Setup", 75L, null, "Competency"));
		menus.add(new AuthorizedMenuDTO(26L, "~./hcms/competency/upload_competency_inquiry.zul", "FUNCTION", 23, "Upload Competency", "Upload Competency", 75L, null, "Competency"));
		menus.add(new AuthorizedMenuDTO(22L, "~./hcms/competency/competency_group_setup_inquiry.zul", "FUNCTION", 24, "Competency Group Setup", "Competency Group Setup", 75L, null, "Competency"));
		menus.add(new AuthorizedMenuDTO(24L, "~./hcms/competency/competency_job_inquiry.zul", "FUNCTION", 24, "Competency Job", "Competency Job", 75L, null, "Competency"));
		//menus.add(new AuthorizedMenuDTO(23L, "~./hcms/competency/competency_weightage_per_grade_inquiry.zul", "FUNCTION", 23, "Competency Weightage per Grade", "Competency Weightage per Grade", 75L, null, "Competency"));
		
		
		//WORKSTRUCTURE
		menus.add(new AuthorizedMenuDTO(76L, "", "SUBMENU", 20, "Workstructure", "Workstructure", 1L, null, "Workstructure"));
		menus.add(new AuthorizedMenuDTO(21L, "~./hcms/workstructure/grade_setup_inquiry.zul", "FUNCTION", 21, "Grade Setup", "Grade Setup", 76L, null, "Workstructure"));
		menus.add(new AuthorizedMenuDTO(21L, "~./hcms/workstructure/job_setup_inquiry.zul", "FUNCTION", 22, "Job Setup", "Job Setup", 76L, null, "Workstructure"));
		menus.add(new AuthorizedMenuDTO(21L, "~./hcms/workstructure/location_setup_inquiry.zul", "FUNCTION", 23, "Location Setup", "Location Setup", 76L, null, "Workstructure"));
		menus.add(new AuthorizedMenuDTO(21L, "~./hcms/workstructure/organization_setup_inquiry.zul", "FUNCTION", 24, "Organization Setup", "Organization Setup", 76L, null, "Workstructure"));
		
		List<AuthorizedMenuDTO> treeMenus = new ArrayList<AuthorizedMenuDTO>();
		for (AuthorizedMenuDTO menu : menus) {
			if (menu.getModuleId().equals(rootId)) {
				treeMenus.add(menu);
			}
		}
		return treeMenus;
	}

	@Override
	public List<KeyValue> getCwkAuthorizedCompanies(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
