package co.id.fifgroup.basicsetup.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.id.fifgroup.basicsetup.domain.Printer;
import co.id.fifgroup.basicsetup.service.PrinterUtilService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class PrinterUtilServiceImpl implements PrinterUtilService {
	
	private final Logger logger = LoggerFactory.getLogger(PrinterUtilServiceImpl.class);
	
	@Override
	public void print(Printer printer, Map<String, String> args) throws Exception {
		try {
			Template template = new Template("printerUtil", new StringReader(printer.getArgument()), new Configuration());
			Writer output = new StringWriter();
			template.process(args, output);
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(output.toString());
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			StringBuffer strBufferInput = new StringBuffer();
			StringBuffer strBufferError = new StringBuffer();
			String s = "";
			while((s = stdInput.readLine()) != null) {
				strBufferInput.append(s);
			}
			
			while((s = stdError.readLine()) != null) {
				strBufferError.append(s);
			}
			
			logger.debug("Standard output");
			logger.debug(strBufferInput.toString());
			logger.debug("Standard error");
			logger.debug(strBufferError.toString());
			
//			Runtime.getRuntime().exec(output.toString());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("System Error", e);
		}
	}

}
