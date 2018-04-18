/**
 * Copyright (c) 2012 FIF. All Rights Reserved. <BR>
 * <BR>
 * This software contains confidential and proprietary information of FIF.
 * ("Confidential Information"). <BR>
 * <BR>
 * Such Confidential Information shall not be disclosed and it shall only be
 * used in accordance with the terms of the license agreement entered into with
 * FIF; other than in accordance with the written permission of FIF. <BR>
 * 
 * Created on: Jun 22, 2010
 *
 * Author           Date         Version Description <BR>
 * ---------------- ------------ ------- --------------------------------- <BR>
 * 
 *  
 */

package co.id.fifgroup.avm.manager;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.avm.dao.AVMRuleMappingDAO;
import co.id.fifgroup.avm.dao.mybatis.MyBatisAVMRuleMappingDAO;
import co.id.fifgroup.avm.domain.AVMRuleMapping;

/**
 * 
 * @author Iswanto and Erlangga
 *
 */

public class AVMRuleEvaluationManager {

	public static final int RULE_VALIDATION = 1;
	public static final int RULE_EVALUATION = 2;

//	private int actiontype;
//	private String ruleexpression;
//	private Object application;
	private AVMRuleMappingDAO avmRuleMappingDAO;

	private static final Logger logger = LoggerFactory
			.getLogger(AVMRuleEvaluationManager.class);
	private static final boolean EVALUATION_RESULT_BY_DEFAULT = true;
	private static final boolean VALIDATION_RESULT_BY_DEFAULT = false;

	public AVMRuleEvaluationManager(){
		super();
		avmRuleMappingDAO = new MyBatisAVMRuleMappingDAO();
	}
	
//	public int getActiontype() {
//		return actiontype;
//	}
//
//	public void setActiontype(int actiontype) {
//		this.actiontype = actiontype;
//	}
//
//	public String getRuleexpression() {
//		return ruleexpression;
//	}
//
//	public void setRuleexpression(String ruleexpression) {
//		this.ruleexpression = ruleexpression;
//	}
//
//	public Object getApplication() {
//		return application;
//	}
//
//	public void setApplication(Object application) {
//		this.application = application;
//	}

	public AVMRuleMappingDAO getAvmRuleMappingDAO() {
		return avmRuleMappingDAO;
	}

	public void setAvmRuleMappingDAO(AVMRuleMappingDAO avmRuleMappingDAO) {
		this.avmRuleMappingDAO = avmRuleMappingDAO;
	}

	public boolean evaluateRule(int actiontype, String ruleExpression, Object application) {

		boolean evaluationresult = false;
		String[] parsedexpressions = parseExpression(ruleExpression);

		try {
			if (parsedexpressions.length != 0) {
				evaluationresult = validateParsedExpressions(parsedexpressions, actiontype, application);
			} else {
				evaluationresult = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			evaluationresult = false;
		}
		if (ruleExpression.equals(""))
			evaluationresult = true;
		logger.info("evaluation result : " + evaluationresult);
		return evaluationresult;
	}

	public String[] parseExpression(String ruleexpression) {

		List<String> parsedExpressionList = new ArrayList<String>();
		parsedExpressionList = unwrapSingleQouteMark(ruleexpression);

		String[] parsedExpressions = new String[parsedExpressionList.size()];

		// flush the content of the bulk into array-type storage
		for (int i = 0; i < parsedExpressionList.size(); i++) {
			parsedExpressions[i] = parsedExpressionList.get(i);
		}

		return parsedExpressions;
	}

	public boolean validateParsedExpressions(String[] parsedexpressions, int actiontype, Object application)
			throws Exception {

		Stack<String> stack = new Stack<String>();

		Stack<Object> tempstack = new Stack<Object>();

		String element = "";
		for (int i = 0; i < parsedexpressions.length; i++) {

			element = parsedexpressions[i];

			logger.info("index = " + i + " element : " + element);

			if (!element.equals("")) {

				stack.push(element);

				if (!element.matches("[=!><|&][|&=]")
						&& !element.matches("[()]") && !element.matches("[|&]")
						&& !element.matches("[><]") && stack.size() > 1) {

					String secondtopelement = (String) stack
							.get(stack.size() - 2);

					if (secondtopelement.matches("[=!><][=]")
							|| secondtopelement.matches("[><]")) {

						String secondoperand = (String) stack.pop();
						String operator = (String) stack.pop();
						String firstoperand = (String) stack.pop();

						logger.info("First Operand : " + firstoperand
								+ " operator : " + operator
								+ " Second Operand : " + secondoperand);

						boolean result = evaluateLogicalExpression(
								firstoperand, secondoperand, operator,
								actiontype, application);

						if (tempstack.size() > 1) {

							String booleanoperator = (String) tempstack.pop();
							boolean previousresult = (Boolean) tempstack.pop();

							if (actiontype == RULE_EVALUATION) {

								if (booleanoperator.equals("|")
										|| booleanoperator.equals("||")) {
									result = result | previousresult;
								} else if (booleanoperator.equals("&")
										|| booleanoperator.equals("&&")) {
									result = result & previousresult;
								}
							}
						}

						tempstack.push(result);
						logger.info("tempstack : ");
						printStack(tempstack);
					}

				}
				if (element.matches("[=!><][=]") || element.matches("[><]")) {
					String secondtopelement = (String) stack
							.get(stack.size() - 2);

					if (secondtopelement.matches("[=!><|&][|&=]")
							|| secondtopelement.matches("[()]")
							|| secondtopelement.matches("[><]")) {

						throw new Exception("error");
					}

				}
				if (element.matches("[|&][|&]") || element.matches("[|&]")) {

					if (tempstack.size() == 0) {

						throw new Exception("error");
					} else {

						String booleanoperator = (String) stack.pop();
						tempstack.push(booleanoperator);
					}
					logger.info("tempstack : ");
					printStack(tempstack);
				}
				if (element.equals(")") && stack.size() > 1) {

					String secondtopelement = (String) stack
							.get(stack.size() - 2);

					if (secondtopelement.equals("(")) {
						stack.pop();
						stack.pop();
					} else {
						throw new Exception("error");

					}
				}
			}

			printStack(stack);

		}
		logger.info("size of stack: " + stack.size());

		if (actiontype == RULE_EVALUATION) {
			if (!tempstack.isEmpty()) {
				return (Boolean) tempstack.pop();
			} else {
				return EVALUATION_RESULT_BY_DEFAULT;
			}
		} else {
			if (!tempstack.isEmpty()) {
				return (Boolean) tempstack.pop();
			} else {
				return VALIDATION_RESULT_BY_DEFAULT;
			}
		}
	}

	public boolean evaluateLogicalExpression(String firstoperand,
			String secondoperand, String operator, int actiontype, Object application)
			throws Exception {

		boolean evaluationresult = false;

		AVMRuleMapping mapping = avmRuleMappingDAO
				.getAVMRuleMapping(firstoperand);
		if (mapping != null) {
			Class<?> applicationClass = Class.forName(mapping.getClassName());
			Method method = applicationClass.getMethod(mapping.getMethodName());
			// add yahya - condition when method not found in class 
			if (method != null) {
				Class<?> returnType = method.getReturnType();
				
				if ((returnType.isPrimitive() && isPrimitiveNumberType(returnType))
						|| isSubClassOfNumber(returnType)) {
					try {
						evaluationresult = true;
						BigDecimal secondDecimal = new BigDecimal(secondoperand);
						if (actiontype == RULE_EVALUATION) {
							
							BigDecimal firstDecimal = new BigDecimal(method.invoke(
									application).toString());
							logger.debug("First Decimal : "
									+ firstDecimal.toString() + " operator :  "
									+ operator + " Second Decimal : "
									+ secondDecimal.toPlainString());
							evaluationresult = evaluateNumberType(
									firstDecimal.doubleValue(),
									secondDecimal.doubleValue(), operator);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw e;
					}
					
				}
				
				else if (returnType.equals(java.lang.String.class)) {
					evaluationresult = true;
					String secondString = secondoperand;
					if (actiontype == RULE_EVALUATION) {
						
						String firstString = method.invoke(application).toString();
						logger.debug("First String : " + firstString
								+ " operator :  " + operator + " Second String "
								+ secondString);
						evaluationresult = evaluateStringType(firstString,
								secondString, operator);
					}
				} else if (returnType.equals(java.lang.Boolean.class)) {
					evaluationresult = true;
					String secondString = secondoperand.toUpperCase();
					if (actiontype == RULE_EVALUATION) {
						
						String firstString = String.valueOf(method.invoke(application)).toUpperCase();
						logger.debug("First String : " + firstString
								+ " operator :  " + operator + " Second String "
								+ secondString);
						evaluationresult = evaluateStringType(firstString,
								secondString, operator);
					}
				}
				
				logger.info("after resolving class");				
			}
			
		}

		return evaluationresult;

	}

	public boolean evaluateStringType(String firstcomponent,
			String secondcomponent, String operator) {

		boolean result = true;
		if (operator.equals("==")) {
			result = firstcomponent.equals(secondcomponent);
		} else {
			result = !firstcomponent.equals(secondcomponent);
		}
		return result;
	}

	public boolean evaluateDateType(Date firstdate, Date seconddate,
			String operator) {

		boolean result = true;
		if (operator.equals("="))
			result = firstdate.equals(seconddate);
		else if (operator.equals("!="))
			result = !firstdate.equals(seconddate);
		else if (operator.equals(">"))
			result = firstdate.after(seconddate);
		else if (operator.equals(">="))
			result = firstdate.after(seconddate) | firstdate.equals(seconddate);
		else if (operator.equals("<"))
			result = firstdate.before(seconddate);
		else if (operator.equals("<="))
			result = firstdate.before(seconddate)
					| firstdate.equals(seconddate);

		return result;
	}

	private boolean evaluateNumberType(double firstComponent,
			double secondComponent, String operator) {
		logger.info("First Component : " + firstComponent
				+ " Second Component : " + secondComponent);
		boolean result = true;
		if (operator.equals("=") || operator.equals("=="))
			result = firstComponent == secondComponent;
		else if (operator.equals("!="))
			result = firstComponent != secondComponent;
		else if (operator.equals(">"))
			result = firstComponent > secondComponent;
		else if (operator.equals(">="))
			result = firstComponent >= secondComponent;
		else if (operator.equals("<"))
			result = firstComponent < secondComponent;
		else if (operator.equals("<="))
			result = firstComponent <= secondComponent;

		logger.info("evaluation result : " + result);
		return result;
	}

	public static List<String> unwrapSingleQouteMark(String string2bTokenized) {
		String separator = "\'";
		List<String> vectorReturned = new ArrayList<String>();
		if (string2bTokenized != null && !string2bTokenized.isEmpty()) {
			StringTokenizer tokens = new StringTokenizer(string2bTokenized,
					separator);
			int count = tokens.countTokens();
			for (int i = 0; i < count; i++) {
				String temp = tokens.nextToken().trim();
				vectorReturned.add(temp);
			}
		}
		return vectorReturned;
	}

	public Class<?> getReturnTypeofProperty(String attributePath)
			throws Exception {

		String[] splittedAttributePath = (attributePath.trim()).split("\\.");
		String className = splittedAttributePath[0];
		String methodName = splittedAttributePath[splittedAttributePath.length - 1];
		for (int i = 1; i < splittedAttributePath.length - 1; i++) {
			className += "." + splittedAttributePath[i];
		}
		Class<?> targetedClass;
		Class<?> returnedClass = null;

		targetedClass = Class.forName(className);
		Method method = targetedClass.getMethod(methodName);
		returnedClass = method.getReturnType();
		logger.debug(returnedClass.getName());

		return returnedClass;
	}

	private boolean isPrimitiveNumberType(Class<?> aClass) {
		return (aClass.getName().equals("double")
				|| aClass.getName().equals("int")
				|| aClass.getName().equals("long")
				|| aClass.getName().equals("short")
				|| aClass.getName().equals("float") || aClass.getName().equals(
				"byte"));
	}

	private boolean isSubClassOfNumber(Class<?> aClass) {
		Class<?> superClass = aClass.getSuperclass();
		if (superClass.equals(java.lang.Object.class)) {
			return false;
		} else if (superClass.equals(java.lang.Number.class)) {
			return true;
		} else {
			return isSubClassOfNumber(aClass);
		}
	}

	public static void printStack(Stack<?> stack) {
		Object[] arr = stack.toArray();

		for (int i = 0; i < arr.length; i++) {
			logger.info(arr[i].toString());
		}
	}
}
