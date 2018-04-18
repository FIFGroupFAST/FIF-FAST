package co.id.fifgroup.workstructure.util;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

import co.id.fifgroup.workstructure.dto.LobElementDTO;

public class LobElementProcessor extends CellProcessorAdaptor {

	private String productCode;
	
	private final Logger logger = LoggerFactory.getLogger(LobElementProcessor.class);
	
	public LobElementProcessor(String productCode) {
		super();
				this.productCode = productCode;
	}
	
	public LobElementProcessor(String productCode, final CellProcessor next) {
		super(next);
		this.productCode = productCode;
	}
	
	public LobElementProcessor(final CellProcessor next) {
		super(next);
		this.productCode = "00";
	}
	
	@Override
	public Object execute(Object value, CsvContext context) {
		logger.debug("Product Code " + productCode);
		try {
			final LobElementDTO result = new LobElementDTO(productCode, new BigDecimal(String.valueOf(value)));
			return result;
		} catch (Exception e) {
			final LobElementDTO result = new LobElementDTO(productCode, BigDecimal.ZERO);
			return result;
		}
	}

}
