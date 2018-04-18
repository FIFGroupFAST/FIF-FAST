package co.id.fifgroup.main.service;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import co.id.fifgroup.systemadmin.domain.UserAccessRecord;
import co.id.fifgroup.systemadmin.service.UserAccessRecordService;

public class UserAccessRecordInterceptor implements AfterReturningAdvice{
	
	private static String RECORD_METHOD = "recordFunctionAccess";

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		if(method.getName().equals(RECORD_METHOD)){
			UserAccessRecordService service = (UserAccessRecordService) target;
			UserAccessRecord data = (UserAccessRecord) returnValue;
			service.record(data);
		}
		
	}
}
