package co.id.fifgroup.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.domain.Bank;

public interface BankMapper {

	public List<Bank> selectByExample(Bank bank, RowBounds rowBounds);
	
	public int countByExample(Bank bank);
	
	public Bank selectByCode(@Param("bankCode") String bankCode);
	
	public Bank selectByAccountNumber(String accountNo);
}
