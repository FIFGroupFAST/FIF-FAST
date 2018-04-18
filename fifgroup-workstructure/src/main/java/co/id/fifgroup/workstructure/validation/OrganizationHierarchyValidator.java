package co.id.fifgroup.workstructure.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;
import co.id.fifgroup.workstructure.constant.HierarchyType;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchyExample;

import co.id.fifgroup.workstructure.dao.OrganizationHierarchyMapper;
import co.id.fifgroup.workstructure.dto.OrgHierElementDTO;
import co.id.fifgroup.workstructure.dto.OrgHierarchyDTO;

@Component
public class OrganizationHierarchyValidator implements Validator<OrgHierarchyDTO>{
	
	@Autowired
	private OrganizationHierarchyMapper organizationHierarchyMapper;
	
	public static final String HIERARCHY_TYPE = "hierarchyType";
	public static final String HIERARCHY_NAME = "hierarchyName";
	public static final String DATE_TO = "dateTo";
	public static final String ROOT = "root";
	public static final String ELEMENT = "element";
	
	public void validate(OrgHierarchyDTO subject) throws ValidationException {
		Map<String, String> mapValidate = new HashMap<>();

		hierarchyTypeValidation(subject, mapValidate);
		hierarchyNameValidation(subject, mapValidate);
		rootValidation(subject, mapValidate);
		elementValidation(subject, mapValidate);
		
		if(mapValidate.size() > 0) {
			throw new ValidationException(mapValidate);
		}		
	}
	
	private void hierarchyTypeValidation(OrgHierarchyDTO subject, Map<String, String> mapValidate) {
		int countType = 0;
		if(subject.getOrgHierType() == null || subject.getOrgHierType().equals(""))
			mapValidate.put(HIERARCHY_TYPE, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationHierarchyType") }));
		else{
			if(subject.getOrgHierType().equals(HierarchyType.STRUCTURAL.toString())) {
				OrganizationHierarchyExample example = new OrganizationHierarchyExample();
				example.createCriteria().andOrgHierTypeLikeInsensitive(HierarchyType.STRUCTURAL.toString()).
				andCompanyIdEqualTo(subject.getCompanyId());
				countType = organizationHierarchyMapper.countByExample(example);
			
				if(subject.getId() == null)
					if(countType > 0) mapValidate.put(HIERARCHY_TYPE, Labels.getLabel("common.isExist", new Object[] { Labels.getLabel("wos.structuralType") }));
				
				if(!subject.getDateTo().equals(DateUtil.MAX_DATE))
					mapValidate.put(DATE_TO, Labels.getLabel("wos.effectiveDateToMustBeMaxDate"));
			}
		}
	}
	
	private void hierarchyNameValidation(OrgHierarchyDTO subject, Map<String, String> mapValidate) {
		int countName = 0;
		if(subject.getId() == null) {
			OrganizationHierarchyExample example = new OrganizationHierarchyExample();			
			example.clear();
			example.createCriteria().andOrgHierNameLikeInsensitive(subject.getOrgHierName());
			countName = organizationHierarchyMapper.countByExample(example);
		}
		
		if(subject.getOrgHierName() == null || subject.getOrgHierName().equals(""))
			mapValidate.put(HIERARCHY_NAME, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.organizationHierarchyName") }));
		else if(subject.getId() == null) {
			if(countName > 0) mapValidate.put(HIERARCHY_NAME, Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("wos.organizationHierarchyName") }));
		}
		
	}
	
	private void rootValidation(OrgHierarchyDTO subject, Map<String, String> mapValidate) {
		if(!subject.getElements().isEmpty()) {
			boolean isRoot = false;
			for(OrgHierElementDTO element : subject.getElements()) {
				if(element.getParentId().equals(element.getOrganizationId())) {
					isRoot = true;
					break;
				}
			}
			if(!isRoot)
				mapValidate.put(ROOT, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.parentOrganization") }));
		}
	}
	
	private void elementValidation(OrgHierarchyDTO subject, Map<String, String> mapValidate) {
		if(!subject.getElements().isEmpty()) {
			boolean isElement = false;
			for(OrgHierElementDTO element : subject.getElements()) {
				if(!element.getParentId().equals(element.getOrganizationId())) {
					isElement = true;
					break;
				}
			}
			if(!isElement)
				mapValidate.put(ELEMENT, Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("wos.subordinate") }));
		}
	}

}
