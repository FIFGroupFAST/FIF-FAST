package co.id.fifgroup.basicsetup.domain;

import java.io.Serializable;

public class GLCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2254997123325679260L;
	private String segmentName;
	private Long segmentNum;
	private String flexValue;
	public String getSegmentName() {
		return segmentName;
	}
	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}
	public Long getSegmentNum() {
		return segmentNum;
	}
	public void setSegmentNum(Long segmentNum) {
		this.segmentNum = segmentNum;
	}
	public String getFlexValue() {
		return flexValue;
	}
	public void setFlexValue(String flexValue) {
		this.flexValue = flexValue;
	}
	
}
