package co.id.fifgroup.basicsetup.service;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.TransactionType;
import co.id.fifgroup.basicsetup.domain.TransactionTypeExample;
import co.id.fifgroup.basicsetup.dto.TransactionTypeDTO;

public interface TransactionTypeService {

	public List<TransactionType> getTransactionTypeByExample(TransactionTypeExample transactionTypeExample, int limit, int offset);
	public List<TransactionType> getTransactionTypeByExample(TransactionTypeExample transactionTypeExample);
 	public int getCountTransactionTypeByExample(TransactionTypeExample transactionTypeExample);
	public void save(TransactionType transactionType) throws Exception;
	public List<TransactionTypeDTO> getTransactionTypeDTOByModuleAndTransactionType(Module module, String transactionType);
	public int getCountTransactionTypeDTOByModuleAndTransactionType(Module module, String transactionType);
	public int countByExample(TransactionTypeExample printerCodeExistExample);
	public boolean isExistTransactionTypeForUpdate(TransactionType subject);
	public List<TransactionType> getTransactionTypeByTrxCode(String trxCode);
	
	
}
