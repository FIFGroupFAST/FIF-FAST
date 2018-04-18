package co.id.fifgroup.systemadmin.validation;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.systemadmin.domain.MenuExample;
import co.id.fifgroup.systemadmin.dto.MenuDto;
import co.id.fifgroup.systemadmin.service.MenuService;

@Component
public class MenuValidator implements Validator<MenuDto>{

	public static String MENU_NOT_EMPTY = "menu.notEmpty";
	public static String MENU_NAME_NOT_UNIQUE = "menuname.notunique";
	public static String DESCRIPTION_NOT_EMPTY = "description.notEmpty";
	public static String DATE_FROM_NOT_EMPTY = "dateFrom.notEmpty";
	public static String DATE_TO_NOT_SAME_DATE_FROM = "dateTo.notSame.dateFrom";
	public static String MENU_DETAIL_ERROR = "MENU_DETAIL_ERROR";
	
	@Autowired
	private MenuService menuService;
	
	@Override
	public void validate(MenuDto subject) throws ValidationException {
		if(null == subject)
			throw new IllegalArgumentException("subject must not be null.");
	
		Map<String, String> violations = new HashMap<>();
		
		if (isNullOrEmpty(subject.getMenuName()))
			violations.put(MENU_NOT_EMPTY, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("sam.menuName")}));
		if (isNullOrEmpty(subject.getDescription()))
			violations.put(DESCRIPTION_NOT_EMPTY, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("common.description")}));
		if (subject.getDateFrom() == null)
			violations.put(DATE_FROM_NOT_EMPTY, Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("mpp.dateFrom")}));
		if(subject.getDateTo() != null) {
			if (DateUtil.compareDate(subject.getDateFrom(), subject.getDateTo())) {
				violations.put(DATE_TO_NOT_SAME_DATE_FROM, Labels.getLabel("common.effectiveDateToMustBeHigherThanOrEqualToEffectiveDateFrom"));
			}
		}
		
		if(subject.getId() == null){
			if(!isUnique(subject.getMenuName()))
				violations.put(MENU_NAME_NOT_UNIQUE, Labels.getLabel("common.mustBeUnique", new Object[] {Labels.getLabel("sam.menuName")}));
		}
	
		
		
		if (violations.size() > 0) throw new ValidationException(violations); 
	}

	
	private boolean isUnique(String menuName){
		MenuExample example = new MenuExample();
		example.createCriteria().andMenuNameEqualTo(menuName);
		int result = menuService.countByExample(example);
		if(result > 0)
			return false;
		else
			return true;
	}
}
