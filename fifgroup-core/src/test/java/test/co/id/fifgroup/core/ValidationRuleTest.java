package test.co.id.fifgroup.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import co.id.fifgroup.core.validation.ValidationRule;

public class ValidationRuleTest {

	@Test
	public void isNPWPTest() {
		assertTrue(ValidationRule.isNPWP("01.331.465.3-218.001"));
		assertTrue(ValidationRule.isNPWP("01.331.465.3-218.001"));
		assertFalse(ValidationRule.isNPWP("001.331.465.3-21a8.001"));
		assertFalse(ValidationRule.isNPWP("001.33.65-3-218.001"));
	}
	
	@Test
	public void testIsAlphabet() {
		assertTrue(ValidationRule.isAlphabet("AplhABeTA"));
		assertTrue(ValidationRule.isAlphabet("AplhABeTAOmegA"));

		assertFalse(ValidationRule.isAlphabet("44"));
		assertFalse(ValidationRule.isAlphabet("4lphaOmega"));
		assertFalse(ValidationRule.isAlphabet("Alph&a"));
		assertFalse(ValidationRule.isAlphabet("Alpha_omega"));
	}

	@Test
	public void testIsAlphaNumeric() {
		assertTrue(ValidationRule.isAlphaNumeric("Alpha123"));
		assertTrue(ValidationRule.isAlphaNumeric("321Beta"));
		assertTrue(ValidationRule.isAlphaNumeric("123Alpha123"));
		assertTrue(ValidationRule.isAlphaNumeric("98Beta902Alpha"));
		assertTrue(ValidationRule.isAlphaNumeric("AlphaBeta"));
		assertTrue(ValidationRule.isAlphaNumeric("123456"));
		assertTrue(ValidationRule.isAlphaNumeric("Alpha123Beta"));

		assertFalse(ValidationRule.isAlphaNumeric("/alpha"));
		assertFalse(ValidationRule.isAlphaNumeric("Alpha%Beta"));
		assertFalse(ValidationRule.isAlphaNumeric("Alpha#Beta"));
		assertFalse(ValidationRule.isAlphaNumeric("'''''"));
		assertFalse(ValidationRule.isAlphaNumeric("Alpha'Beta"));
		assertFalse(ValidationRule.isAlphaNumeric("Alpha;Beta"));
		assertFalse(ValidationRule.isAlphaNumeric("Alpha{Beta"));
		assertFalse(ValidationRule.isAlphaNumeric("Alpha.Beta"));
		assertFalse(ValidationRule.isAlphaNumeric("Alpha,Beta"));
	}

	@Test
	public void testIsDigit() {
		// round
		assertTrue(ValidationRule.isDigit("123456"));
		assertTrue(ValidationRule.isDigit("00492"));
		assertTrue(ValidationRule.isDigit("42522234562"));

		assertFalse(ValidationRule.isDigit("9107579123A4125324"));
		assertFalse(ValidationRule.isDigit("Alpha"));
		assertFalse(ValidationRule.isDigit("  00492"));
		assertFalse(ValidationRule.isDigit("904%"));
		assertFalse(ValidationRule.isDigit("90.4"));
		assertFalse(ValidationRule.isDigit("190.44"));
	}

	@Test
	public void testIsEmail() {
		assertTrue(ValidationRule.isEmail("timotius_pamungkas@yahoo.co.id"));
		assertTrue(ValidationRule
				.isEmail("timotius.pamungkas@fifgroup.astra.co.id"));
		assertTrue(ValidationRule.isEmail("timotius.pamungkas@google.com"));
		assertTrue(ValidationRule.isEmail("timotius_pamungkas_99@yahoo.co.id"));
		assertTrue(ValidationRule
				.isEmail("timotius.pamungkas99@fifgroup.astra.co.id"));
		assertTrue(ValidationRule.isEmail("timotius.pamungkas99@gmail.com"));
		assertTrue(ValidationRule.isEmail("timotius.pamungkas.99@gmail.com"));
		assertTrue(ValidationRule.isEmail("99timotius_pamungkas@yahoo.co.id"));
		assertTrue(ValidationRule
				.isEmail("99timotius.pamungkas@fifgroup.astra.co.id"));
		assertTrue(ValidationRule.isEmail("99timotius.pamungkas@google.com"));

		assertFalse(ValidationRule.isEmail("timotius pamungkas@yahoo.co.id"));
		assertFalse(ValidationRule.isEmail("timotius_pamungkas @yahoo.co.id"));
		assertFalse(ValidationRule.isEmail("timotius_pamungkas@ yahoo.co.id"));
		assertFalse(ValidationRule.isEmail("timotius_pamungkas at yahoo.com"));
		assertFalse(ValidationRule.isEmail("timotius.pamungkas@yahoo"));
	}

	@Test
	public void testIsIntegerUnsigned() {
		assertTrue(ValidationRule.isIntegerUnsigned("123"));
		assertTrue(ValidationRule.isIntegerUnsigned("99999"));

		assertFalse(ValidationRule.isIntegerUnsigned("-123"));
		assertFalse(ValidationRule.isIntegerUnsigned("123.24"));
		assertFalse(ValidationRule.isIntegerUnsigned("123.24.52"));
		assertFalse(ValidationRule.isIntegerUnsigned("123,24"));
	}

	@Test
	public void testIsNumeric() {
		assertTrue(ValidationRule.isNumeric("124213"));
		assertTrue(ValidationRule.isNumeric("3421.233"));
		assertTrue(ValidationRule.isNumeric("0.25"));

		// numeric ga boleh minus
		assertFalse(ValidationRule.isNumeric("-1242"));
		assertFalse(ValidationRule.isNumeric("Alpha"));
		assertFalse(ValidationRule.isNumeric("904.234.555"));
		assertFalse(ValidationRule.isNumeric("200,7"));
		assertFalse(ValidationRule.isNumeric("02.5"));
		assertFalse(ValidationRule.isNumeric("99alpha"));
	}

	@Test
	public void testIsPhoneNumber() {
		// Phone harus pakai kode negara
		assertTrue(ValidationRule.isPhoneNumber("+628123456789"));
		assertTrue(ValidationRule.isPhoneNumber("+6221111999"));
		assertTrue(ValidationRule.isPhoneNumber("+6221555555"));
		assertTrue(ValidationRule.isPhoneNumber("+62812888888"));

		assertFalse(ValidationRule.isPhoneNumber("+6221-555555"));
		assertFalse(ValidationRule.isPhoneNumber("021-555555"));
	}

	@Test
	public void testIsText() {
		assertTrue(ValidationRule.isText("Seekor gajah sedang minum di sungai"));
		assertTrue(ValidationRule
				.isText("Seekor gajah_gendut sedang minum di sungai"));
		assertTrue(ValidationRule.isText("Seekor gajah dengan 2 telinga"));
		assertTrue(ValidationRule.isText("2 gajah berlari"));

		assertFalse(ValidationRule.isText("Seekor gajah $@%#$%"));
	}

	@Test
	public void testIsValidDate() {
		assertTrue(ValidationRule.isValidDate("1-APR-2013"));
		assertTrue(ValidationRule.isValidDate("4-May-2013"));
		assertTrue(ValidationRule.isValidDate("12-JUN-2013"));
		assertTrue(ValidationRule.isValidDate("12-aug-2013"));
		assertTrue(ValidationRule.isValidDate("31-Dec-4712"));

		assertFalse(ValidationRule.isValidDate("31-Feb-2013"));
		assertFalse(ValidationRule.isValidDate("12-04-2013"));
		assertFalse(ValidationRule.isValidDate("12/04/2013"));
		assertFalse(ValidationRule.isValidDate("12 Apr 2013"));
		assertFalse(ValidationRule.isValidDate("12 April 2013"));
		assertFalse(ValidationRule.isValidDate("12-Apr-13"));
	}

	@Test
	public void testIsWord() {
		assertTrue(ValidationRule
				.isWord("A_single_word_separated_by_underscore"));
		assertTrue(ValidationRule.isWord("aword"));
		assertTrue(ValidationRule.isWord("aword99"));
		assertTrue(ValidationRule.isWord("4325aword99"));
		assertTrue(ValidationRule.isWord("aword_99"));
		assertTrue(ValidationRule.isWord("412_aword_99"));
		assertTrue(ValidationRule.isWord("009_aword"));
		assertTrue(ValidationRule.isWord("a_word"));
		assertTrue(ValidationRule.isWord("aword99"));

		assertFalse(ValidationRule.isWord("not a_word"));
		assertFalse(ValidationRule.isWord("not a word"));
		assertFalse(ValidationRule.isWord("not_a word"));
		assertFalse(ValidationRule.isWord("not_a_%#word"));
		assertFalse(ValidationRule.isWord("$@#%@#$$^&*^%"));
		assertFalse(ValidationRule.isWord("...."));
	}
	
	@Test
	public void testDecimal(){
		assertTrue(ValidationRule.isDecimal("0.1",1));
		assertTrue(ValidationRule.isDecimal("0.12",2));
		assertTrue(ValidationRule.isDecimal("0.123", 3));
		assertTrue(ValidationRule.isDecimal("0.1234", 4));
		assertTrue(ValidationRule.isDecimal("0.12345", 5));
		
		assertTrue(ValidationRule.isDecimal("-13.123", 3));
		assertTrue(ValidationRule.isDecimal("+143.123", 3));
		
		assertFalse(ValidationRule.isDecimal("345.123", 2));
		assertFalse(ValidationRule.isDecimal("asd.123", 2));
		assertFalse(ValidationRule.isDecimal("13,123", 2));
		assertFalse(ValidationRule.isDecimal("13", 0));
	
	}
	
	

}
