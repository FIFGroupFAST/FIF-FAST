package co.id.fifgroup.basicsetup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.TransactionType;
import co.id.fifgroup.basicsetup.dto.TransactionTypeDTO;

public interface TransactionTypeFinder {

	public List<TransactionTypeDTO> getTransactionTypeDTOByModuleAndTransactionType(@Param("module") Module module, @Param("transactionType") String transactionType);
	public int getCountTransactionTypeDTOByModuleAndTransactionType(@Param("module") Module module, @Param("transactionType") String transactionType);
	public List<TransactionType> getTransactionTypeByTrxCode(@Param("trxCode") String trxCode);
	
}
