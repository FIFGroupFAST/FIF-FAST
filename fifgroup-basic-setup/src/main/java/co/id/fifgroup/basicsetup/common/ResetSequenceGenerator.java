package co.id.fifgroup.basicsetup.common;

import java.util.List;
import java.util.Map;

import co.id.fifgroup.basicsetup.domain.SequenceGenerator;
import co.id.fifgroup.basicsetup.domain.SequenceGeneratorExample;
import co.id.fifgroup.basicsetup.service.SequenceGeneratorService;
import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.core.validation.TaskExecutionException;

public class ResetSequenceGenerator extends ExecutableTask {

	
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Override
	public void execute() throws TaskExecutionException {
		sequenceGeneratorService = (SequenceGeneratorService) getApplicationContext().getBean("sequenceGeneratorServiceImpl");
		SequenceGeneratorExample sequenceGeneratorExample = new SequenceGeneratorExample();
		sequenceGeneratorExample.createCriteria().andIsResetPerYearEqualTo(true);
		List<SequenceGenerator> sequenceGenerators = sequenceGeneratorService.getSequenceGeneratorByExample(sequenceGeneratorExample);
		for(SequenceGenerator sequenceGenerator : sequenceGenerators) {
			sequenceGeneratorService.resetSequenceGenerator(sequenceGenerator);
		}
	}

	@Override
	public void setParameter(Map<String, Object> parameter) {
		
	}

}
