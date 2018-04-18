package co.id.fifgroup.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stream {

	private static final Logger logger = LoggerFactory.getLogger(Stream.class);
	
	private long startTime;	
	
	public void start(){
		this.startTime = System.currentTimeMillis();
	}
	
	public String stop(String message){
		String result = String.format("%s elapsed time : %d ms", message, System.currentTimeMillis() - startTime);
		logger.info(result);	
		return result;
	}
	
}
