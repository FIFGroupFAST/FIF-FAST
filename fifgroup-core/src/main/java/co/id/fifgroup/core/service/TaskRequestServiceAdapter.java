package co.id.fifgroup.core.service;

import java.util.Map;

public interface TaskRequestServiceAdapter {

	public void buildImmediatelySchedule(String taskRunnerName, Map<String, Object> param) throws Exception;
}
