package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.PrinterFinder;
import co.id.fifgroup.basicsetup.dao.PrinterMapper;
import co.id.fifgroup.basicsetup.domain.Printer;
import co.id.fifgroup.basicsetup.domain.PrinterExample;
import co.id.fifgroup.basicsetup.dto.PrinterDTO;
import co.id.fifgroup.basicsetup.service.PrinterService;
import co.id.fifgroup.basicsetup.validation.PrinterValidator;

@Transactional
@Service
public class PrinterServiceImpl implements PrinterService{
	
	@Autowired
	private PrinterMapper printerMapper;	
	@Autowired
	private PrinterFinder printerFinderMapper;
	@Autowired
	private PrinterValidator printerValidator;

	@Override
	@Transactional
	public void save(Printer printer) throws Exception {
		printerValidator.validate(printer);
		if(printer.getPrinterId() == null) {
			printerMapper.insert(printer);
		} else {
			printerMapper.updateByPrimaryKey(printer);
		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public int countByExample(PrinterExample example) {
		return printerMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountPrinterDtoByPrinterName(String printerName) {
		return printerFinderMapper.getCountPrinterDtoByPrinterName(printerName);
	}

	@Override
	@Transactional(readOnly=true)
	public List<PrinterDTO> getPrinterDtoByPrinterName(String printerName) {
		return printerFinderMapper.getPrinterDtoByPrinterName(printerName);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Printer> getPrinterByExample(PrinterExample printerExample,
			int limit, int offset) {
		return printerMapper.selectByExampleWithRowbounds(printerExample, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public List<Printer> getPrinterByExample(PrinterExample printerExample) {
		return selectByExample(printerExample);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isExistPrinterForUpdate(Printer record) {
		PrinterExample countPrinterByName = new PrinterExample();
		countPrinterByName.createCriteria().andPrinterNameEqualTo(record.getPrinterName());
		if(countByExample(countPrinterByName) > 0) {
			PrinterExample selectPrinterByCode = new PrinterExample();
			selectPrinterByCode.createCriteria().andPrinterCodeEqualTo(record.getPrinterCode()).andPrinterNameEqualTo(record.getPrinterName());
			if(selectByExample(selectPrinterByCode).size() > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	@Transactional(readOnly=true)
	private List<Printer> selectByExample(PrinterExample printerExample) {
		return printerMapper.selectByExample(printerExample);
	}
	
	

}
