package co.id.fifgroup.notification.test.domain;

import java.util.Date;
import java.util.UUID;

public class Transaction {

	private UUID trxId;
	private String benefitType;
	private double amount;
	private Date trxDate;

	public UUID getTrxId() {
		return trxId;
	}

	public void setTrxId(UUID trxId) {
		this.trxId = trxId;
	}

	public String getBenefitType() {
		return benefitType;
	}

	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

}
