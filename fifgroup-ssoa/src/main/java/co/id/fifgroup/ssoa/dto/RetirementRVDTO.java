package co.id.fifgroup.ssoa.dto;

import co.id.fifgroup.ssoa.domain.RetirementRV;

public class RetirementRVDTO extends RetirementRV {

	private static final long serialVersionUID = 9207558486244871296L;
	private String total;
	private String description;
	private String date;
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}