package co.id.fifgroup.basicsetup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.basicsetup.dto.PrinterDTO;

public interface PrinterFinder {

	public int getCountPrinterDtoByPrinterName(@Param("printerName") String printerName);
	public List<PrinterDTO> getPrinterDtoByPrinterName(@Param("printerName") String printerName);

}
