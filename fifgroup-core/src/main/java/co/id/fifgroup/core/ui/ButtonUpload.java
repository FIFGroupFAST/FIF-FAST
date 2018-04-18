package co.id.fifgroup.core.ui;

import org.zkoss.zul.Button;

public class ButtonUpload extends Button {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4722670127581665623L;

	public ButtonUpload() {
		super();
		setUpload("true,multiple=true,native,maxsize=-1");
		
	}

	public ButtonUpload(String label, String image) {
		super(label, image);
		setUpload("true,multiple=true,native,maxsize=-1");
	}

	public ButtonUpload(String label) {
		super(label);
		setUpload("true,multiple=true,native,maxsize=-1");
	}

}
