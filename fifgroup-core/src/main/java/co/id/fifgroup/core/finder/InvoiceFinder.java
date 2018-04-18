package co.id.fifgroup.core.finder;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.interfacing.InvoiceIndividualDetail;
import co.id.fifgroup.core.domain.interfacing.InvoiceIndividualHeader;
import co.id.fifgroup.core.dto.InvoiceInterfaceDTO;

public interface InvoiceFinder {
	
	public List<InvoiceInterfaceDTO> findInvoice(@Param("invoiceNo") String invoiceNo);
	public void insertInvoiceIndividualDetail(InvoiceIndividualDetail invoiceIndividualDetail);
	public void insertInvoiceIndividualHeader(InvoiceIndividualHeader invoiceIndividualHeader);
	public BigDecimal getInvoiceIndividualAmount(InvoiceInterfaceDTO invoiceInterfaceDTO);
	public void insertSupplierIndividualAmt(@Param("supplierCode") String supplierCode, @Param("supplierName") String supplierName,
			@Param("amount") BigDecimal amount, @Param("branchId") String branchId, @Param("companyId") String companyId);
	public void updateIndividualAmount(@Param("supplierCode") String supplierCode, @Param("supplierName") String supplierName,
			@Param("amount") BigDecimal amount, @Param("branchId") String branchId, @Param("companyId") String companyId);
}
