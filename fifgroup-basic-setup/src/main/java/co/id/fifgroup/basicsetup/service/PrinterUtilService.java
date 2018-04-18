package co.id.fifgroup.basicsetup.service;

import java.util.Map;

import co.id.fifgroup.basicsetup.domain.Printer;

public interface PrinterUtilService {

	public void print(Printer printer, Map<String, String> args) throws Exception;
	
}
