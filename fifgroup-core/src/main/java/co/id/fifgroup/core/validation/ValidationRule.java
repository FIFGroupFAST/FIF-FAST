package co.id.fifgroup.core.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;


public class ValidationRule {

	public static final Pattern ALPHANUM = Pattern.compile("^[a-zA-Z0-9]+$");
	public static final Pattern DIGIT = Pattern.compile("^[0-9]+$");
	public static final Pattern ALPHABET = Pattern.compile("^[a-zA-Z]+$");
	public static final Pattern WORD = Pattern.compile("^[a-zA-Z0-9_]+$");
	public static final Pattern EMAIL = Pattern.compile("^[_A-Za-z0-9-\\+]+([\\._A-Za-z0-9]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	public static final Pattern TEXT = Pattern.compile("^[a-zA-Z0-9 _]+$");
	public static final Pattern COMPONENTID = Pattern.compile("^[_a-zA-Z]+([_a-zA-Z0-9]*)$");
	public static final Pattern NUMERIC = Pattern.compile("^[0-9]([0-9]*)*(\\.)*([0-9]*)*$");
	public static final Pattern INTEGERUNSIGNED = Pattern.compile("^[1-9]([0-9]*)*$");
	public static final DateFormat DATEFORMAT_FOR_LOOKUP_SETUP = new SimpleDateFormat("dd-MM-yyyy");
	public static final DateFormat DATEFORMAT = new SimpleDateFormat("dd-MMM-yyyy");
	public static final DateFormat DATETIMEFORMAT = new SimpleDateFormat("dd-MMM-yyyy kk:mm:ss");
	public static final Pattern PHONE_NUMBER = Pattern.compile("^[\\+]([0-9])*$");
	public static final Pattern ALPHABET_WITH_SPACE = Pattern.compile("^[a-zA-Z ]+$");
	public static final Pattern ALPHANUM_WITH_SPACE = Pattern.compile("^[a-zA-Z0-9 ]+$");
	
	public static final Pattern NPWP = Pattern.compile("^[0-9]{2}(\\.)[0-9]{3}(\\.)[0-9]{3}(\\.)[0-9](\\-)[0-9]{3}(\\.)[0-9]{3}$");
	
	public static final boolean matches(String str, Pattern pattern) {
		if (null == str || null == pattern)
			return false;
		return pattern.matcher(str).matches();
	}
	
	public static boolean isAlphaNumeric(String str) {
		return matches(str, ALPHANUM);
	}
	
	public static boolean isDigit(String str) {
		return matches(str, DIGIT);
	}
	
	public static boolean isAlphabet(String str) {
		return matches(str, ALPHABET);
	}
	
	public static boolean isWord(String str) {
		return matches(str, WORD);
	}
	
	public static boolean isEmail(String str) {
		return matches(str, EMAIL);
	}
	
	public static boolean isText(String str) {
		return matches(str, TEXT);
	}
	
	public static boolean isComponentId(String str) {
		return matches(str, COMPONENTID);
	}
	
	public static boolean isNumeric(String str) {
		if(matches(str, NUMERIC)) {
			String number = str.split("\\.")[0];
			if(number.length() > 1 && number.charAt(0) == '0') {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isDecimal(String str, int precision) {
		String pattern = "^(\\+|-)?([0-9]+(\\.[0-9]{0,"+precision+"}+))$";
		Pattern DECIMAL = Pattern.compile(pattern);
		return matches(str, DECIMAL);
	}
	
	public static boolean isCurrency(String str){
		return isNumeric(str);
	}
	
	public static boolean isValidDateTime(String str){
		try {
			DATETIMEFORMAT.setLenient(false);
			String strDateTime = DATETIMEFORMAT.format(DATETIMEFORMAT.parse(str));
			String[] splitDateTime = strDateTime.split(" ");
			if(splitDateTime.length == 2) {
				String[] splitDate = splitDateTime[0].split("-");
				if(splitDate.length == 3) {
					int year = Integer.parseInt(splitDate[2]);
					if(String.valueOf(year).length() != 4) {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	public static boolean isValidDateForLookupSetup(String str) {
		try {
			DATEFORMAT_FOR_LOOKUP_SETUP.setLenient(false);
			DATEFORMAT_FOR_LOOKUP_SETUP.parse(str);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	public static boolean isValidDate(String str) {
		try {
			DATEFORMAT.setLenient(false);
			String strDate =  DATEFORMAT.format(DATEFORMAT.parse(str));
			String splitDate[] = strDate.split("-");
			if(splitDate.length == 3) {
				int year = Integer.parseInt(splitDate[2]);
				if(String.valueOf(year).length() != 4) {
					return false;
				}
			} else {
				return false;
			}
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	public static boolean isIntegerUnsigned(String str) {
		return matches(str, INTEGERUNSIGNED);
	}
	
	public static boolean isPhoneNumber(String str) {
		return matches(str, PHONE_NUMBER);
	}
	
	public static boolean isAlphabetWithSpace(String str) {
		return matches(str, ALPHABET_WITH_SPACE);
	}
	
	public static boolean isAlphanumWithSpace(String str) {
		return matches(str, ALPHANUM_WITH_SPACE);
	}
	
	public static boolean isNPWP(String str) {
		return matches(str, NPWP);
	}
}
