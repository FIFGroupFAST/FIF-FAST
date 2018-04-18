package co.id.fifgroup.core.service;

import java.math.BigDecimal;


public interface TaxService {
	float getPpnRate(Long orgId);
	BigDecimal getTaxAmount(String taxCode, Long companyId, BigDecimal amount, Long orgId, String supplierCode, String branchCode);
	String getTaxCode(String supplierCode);
	float getTaxRate(String taxCode, Long orgId);
	BigDecimal getCummulativeTransactionAmount(String supplierCode, Long companyId, String branchCode);
	BigDecimal getProgressiveTaxAmount(String taxCode, BigDecimal amount, BigDecimal cummAmount);
	Boolean isSupplierPrivate(String supplierCode);

//Start added for Career, by Jatis
	BigDecimal calculateAmountIncludeTax(BigDecimal totalAmount,
			boolean includePpn, String branchCode, Long companyId,
			Long companyOtherInfo, String supplierCode);
//End added for Career, by Jatis
}
