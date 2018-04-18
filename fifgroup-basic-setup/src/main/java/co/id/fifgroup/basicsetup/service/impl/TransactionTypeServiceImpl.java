package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.TransactionTypeFinder;
import co.id.fifgroup.basicsetup.dao.TransactionTypeMapper;
import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.TransactionType;
import co.id.fifgroup.basicsetup.domain.TransactionTypeExample;
import co.id.fifgroup.basicsetup.dto.TransactionTypeDTO;
import co.id.fifgroup.basicsetup.service.TransactionTypeService;
import co.id.fifgroup.basicsetup.validation.TransactionTypeValidator;

@Transactional
@Service
public class TransactionTypeServiceImpl implements TransactionTypeService {

	@Autowired
	private TransactionTypeMapper transactionTypeMapper;
	@Autowired
	private TransactionTypeFinder transactionTypeFinderMapper;
	@Autowired
	private TransactionTypeValidator transactionValidator;
	
	@Override
	@Transactional(readOnly=true)
	public List<TransactionType> getTransactionTypeByExample(
			TransactionTypeExample transactionTypeExample, int limit, int offset) {
		return transactionTypeMapper.selectByExampleWithRowbounds(transactionTypeExample, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountTransactionTypeByExample(
			TransactionTypeExample transactionTypeExample) {
		return transactionTypeMapper.countByExample(transactionTypeExample);
	}

	@Override
	@Transactional
	public void save(TransactionType transactionType) throws Exception{
		transactionValidator.validate(transactionType);
		if(transactionType.getTrxTypeId() == null) {
			transactionTypeMapper.insert(transactionType);
		} else {
			transactionTypeMapper.updateByPrimaryKey(transactionType);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<TransactionTypeDTO> getTransactionTypeDTOByModuleAndTransactionType(Module module, String transactionType) {
		return transactionTypeFinderMapper.getTransactionTypeDTOByModuleAndTransactionType(module, transactionType);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountTransactionTypeDTOByModuleAndTransactionType(
			Module module, String transactionType) {
		return transactionTypeFinderMapper.getCountTransactionTypeDTOByModuleAndTransactionType(module, transactionType);
	}

	@Override
	@Transactional(readOnly = true)
	public int countByExample(TransactionTypeExample example) {
		return transactionTypeMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isExistTransactionTypeForUpdate(TransactionType subject) {
		TransactionTypeExample countTransactionTypeByTrxType = new TransactionTypeExample();
		countTransactionTypeByTrxType.createCriteria().andTrxTypeEqualTo(subject.getTrxType());
		if(countByExample(countTransactionTypeByTrxType) > 0) {
			TransactionTypeExample selectTransactionTypeByTrxCode = new TransactionTypeExample();
			selectTransactionTypeByTrxCode.createCriteria().andTrxCodeEqualTo(subject.getTrxCode()).andTrxTypeEqualTo(subject.getTrxType());
			if(selectByExample(selectTransactionTypeByTrxCode).size() > 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	@Transactional(readOnly=true)
	private List<TransactionType> selectByExample(TransactionTypeExample transactionTypeExample) {
		return transactionTypeMapper.selectByExample(transactionTypeExample);
	}

	@Override
	@Transactional(readOnly=true)
	public List<TransactionType> getTransactionTypeByExample(TransactionTypeExample example) {
		return transactionTypeMapper.selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<TransactionType> getTransactionTypeByTrxCode(String trxCode) {
		return transactionTypeFinderMapper.getTransactionTypeByTrxCode(trxCode);
	}

	
	
}
