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
 * Created on: Aug 25, 2012
 *
 * Author           Date         Version Description <BR>
 * ---------------- ------------ ------- --------------------------------- <BR>
 * 
 *  
 */
package co.id.fifgroup.core.ui;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.number.NumberFormatter;

import co.id.fifgroup.core.util.ApplicationContextUtil;

import co.id.fifgroup.core.security.Security;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.GlobalSettingServiceAdapter;
import co.id.fifgroup.core.service.SecurityService;

public class GlobalVariable{
	
	private static String MAX_ROW_PER_PAGE = "MAXROW";
	private static String MAX_ROW_PER_PAGE_QUESTIONNAIRE = "MAX_ROW_PER_PAGE_QUESTIONNAIRE";
	private static String MAX_THREAD_POOL = "MAX_THREAD_POOL";
	private static String DATE_FORMAT = "DATE_FORMAT";
	private static String MONEY_FORMAT = "MONEY_FORMAT";
	private static String PAY_TERMINATION_BANK_CODE = "PAY_TERMINATION_BANK_CODE";
	private static String DATE_TIME_FORMAT = "DATE_TIME_FORMAT";
	private static String MPP_MAX_TRANSACTION = "MPP_MAX_TRANSACTION";
	
	public static String RESPONSIBILITY_NAME = "responsibilityName";
	public static String APPS_ROOT_FOLDER = "APPS_ROOT_FOLDER";
	public static String USER_MANUAL_KEY = "USER_MANUAL_KEY";
	public static String FUNCTION_PERMISSION_KEY = "FUNCTION_PERMISSION_KEY";
	public static String ACTION_ID = "ACTION_ID";
	
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalVariable.class);
	
	
	public static int getMaxRowPerPage() {
		return Integer.valueOf(getGlobalSettingServiceAdapter().getGlobalSettingValueByCode(MAX_ROW_PER_PAGE));
	}
	
	public static int getMaxRowPerPageQuestionnaire() {
		return Integer.valueOf(getGlobalSettingServiceAdapter().getGlobalSettingValueByCode(MAX_ROW_PER_PAGE_QUESTIONNAIRE));
	}
	
	public static int getMaxThreadPool(){
		return Integer.valueOf(getGlobalSettingServiceAdapter().getGlobalSettingValueByCode(MAX_THREAD_POOL));
	}

	public static String getAppsRootFolder() {
		return getGlobalSettingServiceAdapter().getGlobalSettingValueByCode(APPS_ROOT_FOLDER);
	}
	
	public static String getDateFormat(){
		return getGlobalSettingServiceAdapter().getGlobalSettingValueByCode(DATE_FORMAT);
	}
	
	public static GlobalSettingServiceAdapter getGlobalSettingServiceAdapter(){
		return (GlobalSettingServiceAdapter) ApplicationContextUtil.getApplicationContext().getBean("globalSettingServiceImpl");
	}
	
	public static String getMoneyFormat(){
		return getGlobalSettingServiceAdapter().getGlobalSettingValueByCode(MONEY_FORMAT);
	}
	
	public static String formatNumber(Number value) {
		try {
			return new NumberFormatter(getMoneyFormat()).print(value, Locale.getDefault());
		} catch(IllegalArgumentException ex) {
			logger.error(ex.getMessage(), ex);
			return String.valueOf(value);
		}
	}
	
	public static String getTerminationBankCode(){
		return getGlobalSettingServiceAdapter().getGlobalSettingValueByCode(PAY_TERMINATION_BANK_CODE);
	}
	
	private static Security getSecurity() {
		SecurityService securityService = ApplicationContextUtil.getApplicationContext().getBean("securityServiceImpl", SecurityService.class);
		return securityService.getSecurityProfile().getSecurity();
	}
	
	public static boolean hasPermission(String permissionName) {
		return getSecurity().hasPermission(permissionName);
	}
	
	public static boolean hasPermission(SecurityProfile securityProfile, String permissionName) {
		return securityProfile.getSecurity().hasPermission(permissionName);
	}
	
	public static String getDateTimeFormat(){
		return getGlobalSettingServiceAdapter().getGlobalSettingValueByCode(DATE_TIME_FORMAT);
	}
	
	public static int getMaxMppTransaction(){
		return Integer.valueOf(getGlobalSettingServiceAdapter().getGlobalSettingValueByCode(MPP_MAX_TRANSACTION));
	}
}
