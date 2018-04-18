package co.id.fifgroup.core.domain.transaction;

import java.math.BigDecimal;

public interface CommonAssessmentPaymentTrx extends CommonTrx {
	public BigDecimal getAssessmentPaymentAmount();
}
