package co.id.fifgroup.systemadmin.validation;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.systemadmin.domain.FieldPermission;
import co.id.fifgroup.systemadmin.domain.Menu;
import co.id.fifgroup.systemadmin.domain.ResponsibilityExample;
import co.id.fifgroup.systemadmin.dto.ItemExclusionDTO;
import co.id.fifgroup.systemadmin.dto.ResponsibilityCompanyDTO;
import co.id.fifgroup.systemadmin.dto.ResponsibilityDTO;
import co.id.fifgroup.systemadmin.service.ResponsibilityService;

@Component
public class ResponsibilityValidator implements Validator<ResponsibilityDTO>{

	public static String RESPONSIBILITY_NOT_EMPTY = "responsibility.notEmpty";
	public static String RESPONSIBILITY_NOT_UNIQUE = "responsibility.notUniqe";
	public static String DATE_FROM_NOT_EMPTY = "dateFrom.notEmpty";
	public static String MENU_NAME_NOT_EMPTY = "menuName.notEmpty";
	public static String DATE_TO_NOT_SAME_DATE_FROM = "dateTo.notSame.dateFrom";
	public static String ITEM_EXCLUSIONS_NAME_CANNOT_BE_EMPTY = "itemExclusionsName.cannotBe.empty";
	public static String BUSINESS_GROUP_EMPTY = "businessGroup.MustNotBeEmpty";
	public static String COMPANY_EMPTY = "companyMustNotBeEmpty";
	public static String COMPANY_NAME_EMPTY = "companyNameMustNotBeEmpty";
	public static String ITEM_EXCLUSIONS_DOUBLE = "itemExclusionsDouble";
	public static String COMPANY_DOUBLE = "compantDouble";
	public static String GRADE_BY_ASSIGNMENT_DOUBLE = "gradeByAssignmentDouble";
	public static String ORGANIZATION_BY_ASSIGN_DOUBLE = "organizationByAssignDouble";
	public static String GRADE_MULTI_DOUBLE = "gradeMultiDouble";
	public static String ORGANIZATION_MULTI_DOUBLE = "organizationMultiDouble";
	public static String FIELD_PERMISSIONS_NAME_EMPTY = "fieldPermissionNameEmpty";
	public static String FIELD_PERMSSIONS_DOUBLE = "fieldPermissionsDouble";
	
	private Menu menu;
	
	@Autowired
	private ResponsibilityService responsibilityService;
	
	@Override
	public void validate(ResponsibilityDTO subject) throws ValidationException {
		if(menu == null) {
			menu = new Menu();
			subject.setMenu(menu);
		}
		
		
	}
	
	public void validateFirstPage(ResponsibilityDTO subject) throws ValidationException{
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		Map<String, String> violations = new HashMap<>();
		if (isNullOrEmpty(subject.getResponsibilityName()))
			violations.put(RESPONSIBILITY_NOT_EMPTY, Labels.getLabel("common.err.fieldRequired",new Object[] {Labels.getLabel("sam.responsibilityName")}));
		if (subject.getDateFrom() == null)
			violations.put(DATE_FROM_NOT_EMPTY, Labels.getLabel("common.err.fieldRequired",new Object[] {Labels.getLabel("common.dateFrom")}));
		if (subject.getMenuId() == null)
			violations.put(MENU_NAME_NOT_EMPTY, Labels.getLabel("common.err.fieldRequired",new Object[] {Labels.getLabel("sam.menu")}));

		if(subject.getDateFrom() != null && subject.getDateTo() != null){
			if(subject.getDateTo().before(subject.getDateFrom()))
				violations.put(DATE_TO_NOT_SAME_DATE_FROM, Labels.getLabel("common.effectiveDateToMustBeHigherThanOrEqualToEffectiveDateFrom"));
		}
		if(subject.getId() == null){
			if(!isUnique(subject.getResponsibilityName()))
				violations.put(RESPONSIBILITY_NOT_UNIQUE, Labels.getLabel("common.isExist",new Object[] {Labels.getLabel("sam.responsibilityName")}));
		}
		
		if(subject.getItemExclusions() != null && subject.getItemExclusions().size() > 0){
			for (ItemExclusionDTO itemEx : subject.getItemExclusions()) {
				if(!isNullOrEmpty(itemEx.getItemType())){
					if(itemEx.getActionId() == null)
						violations.put(ITEM_EXCLUSIONS_NAME_CANNOT_BE_EMPTY, "[Item exclusions] "+Labels.getLabel("common.err.fieldRequired",new Object[] {Labels.getLabel("sam.itemName")}));
				}
			}
			
			for(int i = 0; i < subject.getItemExclusions().size(); i++) {
				
				for(int j = 0; j < subject.getItemExclusions().size(); j++) {
					if(i == j) {
						continue;
					}
					if(subject.getItemExclusions().get(i).getActionId().equals(subject.getItemExclusions().get(j).getActionId())) {
						violations.put(ITEM_EXCLUSIONS_DOUBLE, "[Item exclusions] "+Labels.getLabel("sam.cannotBeDuplicated",new Object[] {Labels.getLabel("sam.itemExclusions")}));
					}
				}
			}
		}
		
		if(subject.getBusinessGroupId() == null)
			violations.put(BUSINESS_GROUP_EMPTY, "[Data Group] "+Labels.getLabel("common.err.fieldRequired",new Object[] {Labels.getLabel("sam.businessGroup")}));
		
		if(subject.getResponsibilityCompanyDto() != null){
			for (ResponsibilityCompanyDTO resco : subject.getResponsibilityCompanyDto()) {
				if(resco.getCompanyId() == null)
					violations.put(COMPANY_NAME_EMPTY, "[Data Group] "+Labels.getLabel("common.err.fieldRequired",new Object[] {Labels.getLabel("bse.companyName")}));
			}
			
			for(int i = 0; i < subject.getResponsibilityCompanyDto().size(); i++) {
				for(int j = 0; j < subject.getResponsibilityCompanyDto().size(); j++) {
					if(i == j) {
						continue;
					}
					if(subject.getResponsibilityCompanyDto().get(i).getCompanyId().equals(subject.getResponsibilityCompanyDto().get(j).getCompanyId())) {
						violations.put(COMPANY_DOUBLE, "[Data Group] "+Labels.getLabel("sam.cannotBeDuplicated",new Object[] {Labels.getLabel("sam.company")}));
					}
				}
			}
			
			for (ResponsibilityCompanyDTO resCo : subject.getResponsibilityCompanyDto()) {
				
				if(resCo.getGradeExclusionByAssignments() != null && resCo.getGradeExclusionByAssignments().size() > 0){
					for(int i = 0; i < resCo.getGradeExclusionByAssignments().size(); i++) {
						for(int j = 0; j < resCo.getGradeExclusionByAssignments().size(); j++) {
							if(i == j) {
								continue;
							}
							if( resCo.getGradeExclusionByAssignments().get(i).getGradeId().equals(resCo.getGradeExclusionByAssignments().get(j).getGradeId()) ) {
								violations.put(GRADE_BY_ASSIGNMENT_DOUBLE, "[Data Group > by Assignment]  "+Labels.getLabel("sam.cannotBeDuplicated",new Object[] {Labels.getLabel("sam.gradeExclusion")}));
							}
						}
					}
				}
				
				if(resCo.getIncludedOrganizationByAssignments() != null && resCo.getIncludedOrganizationByAssignments().size() > 0){
					for(int i = 0; i < resCo.getIncludedOrganizationByAssignments().size(); i++) {
						for(int j = 0; j < resCo.getIncludedOrganizationByAssignments().size(); j++) {
							if(i == j) {
								continue;
							}
							if(resCo.getIncludedOrganizationByAssignments().get(i).getOrganizationId().equals(resCo.getIncludedOrganizationByAssignments().get(j).getOrganizationId())) {
								violations.put(ORGANIZATION_BY_ASSIGN_DOUBLE, "[Data Group > by Assignment] "+Labels.getLabel("sam.cannotBeDuplicated",new Object[] {Labels.getLabel("tms.organizationName")}));
							}
						}
					}
				}
				
				if(resCo.getGradeExclusionMultiCompanys() != null && resCo.getGradeExclusionMultiCompanys().size() > 0){
					for(int i = 0; i < resCo.getGradeExclusionMultiCompanys().size(); i++) {
						for(int j = 0; j < resCo.getGradeExclusionMultiCompanys().size(); j++) {
							if(i == j) {
								continue;
							}
							if( resCo.getGradeExclusionMultiCompanys().get(i).getGradeId().equals(resCo.getGradeExclusionMultiCompanys().get(j).getGradeId()) ) {
								violations.put(GRADE_MULTI_DOUBLE, "[Data Group > Multi Company] "+Labels.getLabel("sam.cannotBeDuplicated",new Object[] {Labels.getLabel("sam.gradeExclusion")}));
							}
						}
					}
				}
				
				if(resCo.getIncludedOrganizationMultiCompanys() != null && resCo.getIncludedOrganizationMultiCompanys().size() > 0){
					for(int i = 0; i < resCo.getIncludedOrganizationMultiCompanys().size(); i++) {
						for(int j = 0; j < resCo.getIncludedOrganizationMultiCompanys().size(); j++) {
							if(i == j) {
								continue;
							}
							if(resCo.getIncludedOrganizationMultiCompanys().get(i).getOrganizationId().equals(resCo.getIncludedOrganizationMultiCompanys().get(j).getOrganizationId())) {
								violations.put(ORGANIZATION_MULTI_DOUBLE, "[Data Group > Multi Company] "+Labels.getLabel("sam.cannotBeDuplicated",new Object[] {Labels.getLabel("tms.organizationName")}));
							}
						}
					}
				}
			}
			
		}else
			violations.put(COMPANY_EMPTY, "[Data Group] "+Labels.getLabel("common.err.fieldRequired",new Object[] {Labels.getLabel("bse.companyName")}));
		
		if(subject.getFieldPermissions() != null && subject.getFieldPermissions().size() > 0){
			
			for (FieldPermission perm : subject.getFieldPermissions()) {
				if(perm.getPermissionName() == null || perm.getPermissionName().equals(""))
					violations.put(FIELD_PERMISSIONS_NAME_EMPTY, "[Field Permissions] "+Labels.getLabel("common.err.fieldRequired",new Object[] {Labels.getLabel("sam.permissionName")}));
			}
			
			
			for(int i = 0; i < subject.getFieldPermissions().size(); i++) {
				for(int j = 0; j < subject.getFieldPermissions().size(); j++) {
					if(i == j) {
						continue;
					}
					if(subject.getFieldPermissions().get(i).getPermissionName() != null && subject.getFieldPermissions().get(j).getPermissionName() != null){
						if(subject.getFieldPermissions().get(i).getPermissionName().equalsIgnoreCase(subject.getFieldPermissions().get(j).getPermissionName())) {
							violations.put(FIELD_PERMSSIONS_DOUBLE, "[Field Permissions] "+Labels.getLabel("sam.cannotBeDuplicated",new Object[] {Labels.getLabel("sam.permissionName")}));
						}
					}
				}
			}
		}
		
		
		if (violations.size() > 0) throw new ValidationException(violations); 
		
	}
	
	private boolean isUnique(String respName){
		ResponsibilityExample example = new ResponsibilityExample();
		example.createCriteria().andResponsibilityNameEqualTo(respName);
		int result = responsibilityService.countResponsibilityByExample(example);
		if(result > 0)
			return false;
		else
			return true;
	}
}
