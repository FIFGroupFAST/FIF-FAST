package co.id.fifgroup.core.finder;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.dto.SupplierDTO;
import co.id.fifgroup.core.dto.TaxDTO;


public interface TaxFinder {
	SupplierDTO getSupplier(@Param("supplierCode") String supplierCode);
	void isSupplierPrivate(SupplierDTO supplierDto);
	String getPpnRate(@Param("orgId") Long orgId);
	String getTaxRates(@Param("taxCode") String taxCode, @Param("orgId") Long orgId);
	BigDecimal getCummulativeTransactionAmount(@Param("supplierCode") String supplierCode, @Param("companyId") Long companyId,
			@Param("branchCode") String branchCode);
	void getProgressiveTaxAmount(TaxDTO taxDto);
}
