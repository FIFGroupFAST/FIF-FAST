package co.id.fifgroup.systemadmin.validation;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.systemadmin.domain.Function;
import co.id.fifgroup.systemadmin.domain.FunctionExample;
import co.id.fifgroup.systemadmin.service.FunctionService;

@Component
public class FunctionValidator implements Validator<Function>{
	
	public static String MODULE_NOT_EMPTY = "module.notEmpty";
	public static String NAME_NOT_EMPTY = "name.notEmpty";
	public static String DESCRIPTION_NOT_EMPTY = "description.notEmpty";
	public static String FORM_LINK_NOT_EMPTY = "formlink.notEmpty";
	public static String NAME_IS_NOT_UNIQUE = "Function Name must be unique";
	
	@Autowired
	private FunctionService functionService;
	
	@Override
	public void validate(Function subject) throws ValidationException {
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
		Map<String, String> violations = new HashMap<>();
		if (subject.getModuleId() == null)
			violations.put(MODULE_NOT_EMPTY, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("que.moduleName")}));
		if (isNullOrEmpty(subject.getFunctionName()))
			violations.put(NAME_NOT_EMPTY, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("sam.functionName")}));	
		if (isNullOrEmpty(subject.getDescription()))
			violations.put(DESCRIPTION_NOT_EMPTY, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("common.description")}));	
		if (isNullOrEmpty(subject.getActionUrl()))
			violations.put(FORM_LINK_NOT_EMPTY, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("sam.formLink")}));	
		
		if(subject.getId() == null){
			if(!isUnique(subject.getFunctionName()))
				violations.put(NAME_IS_NOT_UNIQUE, Labels.getLabel("common.mustBeUnique", new Object[] {Labels.getLabel("sam.functionName")}));
		}
		
		if(subject.getActionUrl() != null){
			File file = new File(subject.getActionUrl());
			String[] str = file.getName().split("\\.");
			if(str.length > 1){
				if(!str[1].equals("zul"))
					violations.put(FORM_LINK_NOT_EMPTY, "Form link must has .zul extension");
			}else
				violations.put(FORM_LINK_NOT_EMPTY, "Form link must has .zul extension");
		}
		
		if (violations.size() > 0) throw new ValidationException(violations); 
	}
	
	private boolean isUnique(String functionName){
		FunctionExample example = new FunctionExample();
		example.createCriteria().andFunctionNameEqualTo(functionName);
		int result = functionService.countByExample(example);
		if(result > 0)
			return false;
		else
			return true;
	}

}
