package co.id.fifgroup.core.ui;

import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

public class FormLabel extends Div {
	
	private static final long serialVersionUID = 1L;
	private Label label;
	private Label sign;
	private String lblValue;
	private boolean required;
	private String REQUIRED_SIGN = "*";
	
	public FormLabel() {
		label = new Label();
		label.setSclass("form-label");
		appendChild(label);
	}

	public String getValue() {
		return lblValue;
	}

	public void setValue(String lblValue) {
		label.setValue(lblValue);
		this.lblValue = lblValue;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
		if(required){
			sign = new Label();
			sign.setValue(REQUIRED_SIGN);
			sign.setStyle("color: red");
			appendChild(sign);
		}
	}
}
