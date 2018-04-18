package co.id.fifgroup.core.domain.transaction;

public interface CommonLoanTrx extends CommonTrx {

	public Long getLoanType();
	
	public Boolean isAffco();
}
