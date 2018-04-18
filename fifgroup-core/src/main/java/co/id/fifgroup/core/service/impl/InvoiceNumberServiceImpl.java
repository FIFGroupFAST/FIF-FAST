package co.id.fifgroup.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.domain.interfacing.InvoiceNumber;
import co.id.fifgroup.core.finder.FIFAppsFinder;
import co.id.fifgroup.core.service.InvoiceNumberService;

@Service
@Transactional
public class InvoiceNumberServiceImpl implements InvoiceNumberService {

	@Autowired
	public FIFAppsFinder fifappsFinder;
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void getInvoiceNumber(InvoiceNumber invoiceNumber) {
		fifappsFinder.getInvoiceNumber(invoiceNumber);
	}

}
