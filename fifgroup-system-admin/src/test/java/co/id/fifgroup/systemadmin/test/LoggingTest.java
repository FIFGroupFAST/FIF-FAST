package co.id.fifgroup.systemadmin.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import co.id.fifgroup.core.validation.TaskExecutionException;

import co.id.fifgroup.core.task.ExecutableTask;

public class LoggingTest extends ExecutableTask{
	
	@Override
	public void execute() throws TaskExecutionException {
		String filePath = "D:\\HCMS\\LOGS1179_30-Apr-2013.txt";
		File file = new File(filePath);
		try {
			PrintWriter log = new PrintWriter(file);
			for (int i = 0; i < 4; i++) {
				log.println("lalala");
				debug("lalala");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			debug(e.getMessage());
		}
	}

}
