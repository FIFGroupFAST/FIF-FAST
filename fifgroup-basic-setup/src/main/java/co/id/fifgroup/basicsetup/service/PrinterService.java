package co.id.fifgroup.basicsetup.service;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.Printer;
import co.id.fifgroup.basicsetup.domain.PrinterExample;
import co.id.fifgroup.basicsetup.dto.PrinterDTO;

public interface PrinterService {

	public void save(Printer printer) throws Exception;
	public int countByExample(PrinterExample example);
	public int getCountPrinterDtoByPrinterName(String printerName);
	public List<PrinterDTO> getPrinterDtoByPrinterName(String printerName);
	public List<Printer> getPrinterByExample(PrinterExample printerExample, int limit, int offset);
	public List<Printer> getPrinterByExample(PrinterExample printerExample);
	public boolean isExistPrinterForUpdate(Printer record);
}
