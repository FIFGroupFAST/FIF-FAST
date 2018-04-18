package co.id.fifgroup.systemadmin.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.systemadmin.dto.UserDTO;
import co.id.fifgroup.systemadmin.service.UserService;

import static com.google.common.base.Strings.isNullOrEmpty;
@Component
public class UserValidator implements Validator<UserDTO>{
	
	@Autowired
	private UserService userService;
	public static String USER_NAME_EMPTY = "USER_NAME_EMPTY";
	public static String DATE_FROM_EMPTY = "DATE_FROM_EMPTY";
	public static String INVALID_USER_NAME = "INVALID_USER_NAME";
	@Override
	public void validate(UserDTO subject) throws ValidationException {
		
		Map<String, String> violations = new HashMap<>();
		
		if(isNullOrEmpty(subject.getUserName()))
			violations.put(USER_NAME_EMPTY, Labels.getLabel("common.err.fieldRequired",new Object[] {Labels.getLabel("sam.userName")}));
		/*else if(!userService.validateLdapUser(subject.getUserName())){
			violations.put(INVALID_USER_NAME, Labels.getLabel("sam.invalidUserName"));
		}*/
		if(subject.getDateFrom() == null)
			violations.put(DATE_FROM_EMPTY, Labels.getLabel("common.err.fieldRequired",new Object[] {Labels.getLabel("mpp.dateFrom")}));
		
		if (violations.size() > 0) throw new ValidationException(violations); 
		
	}

}
