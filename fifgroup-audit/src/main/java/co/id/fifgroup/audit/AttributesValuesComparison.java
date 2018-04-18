package co.id.fifgroup.audit;

public class AttributesValuesComparison {
	
	private String attributeLabel;
	private String currentValue;
	private String newValue;

	public AttributesValuesComparison(String attributeLabel, String currentValue, String newValue) {
		this.setAttributeLabel(attributeLabel);
		this.setCurrentValue(currentValue);
		this.setNewValue(newValue);
	}
	
	public void setAttributeLabel(String attributeLabel) {
		this.attributeLabel = attributeLabel;
	}

	public String getAttributeLabel() {
		return attributeLabel;
	}
	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getNewValue() {
		return newValue;
	}
	
	public String toString() {
		return "currentValue: " + currentValue + " newValue: " + newValue;
	}
}
