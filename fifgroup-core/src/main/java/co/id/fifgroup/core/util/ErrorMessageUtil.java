package co.id.fifgroup.core.util;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zul.Box;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vbox;

import com.google.common.base.Strings;

public class ErrorMessageUtil {
	
	public static void setErrorMessage(Component component, String errorMessage) {
		setErrorMessage(component, errorMessage, false);
	}
	
	public static void setErrorMessage(Component component, String errorMessage, boolean vertical) {
		if (Strings.isNullOrEmpty(errorMessage))
			return;
		Component parent = component.getParent();
		Label lblError = new Label(errorMessage);
		lblError.setStyle("color:red; word-wrap: break-word;");
		
		Component sibling = component.getNextSibling();
		
//		if((parent instanceof Layout)) {
		if ((parent instanceof Box) && parent.getId().startsWith("errMsg-")) {
			if (vertical)
				parent.insertBefore(lblError, component);
			else
				parent.appendChild(lblError);
		}
		else {
//			Layout newParent = vertical ? new Vlayout() : new Hlayout();	
			Box newParent = vertical ? new Vbox() : new Hbox();
			newParent.setId("errMsg-"+component.getId());
			newParent.setStyle("word-wrap: break-word;");
			newParent.setHflex("auto");
			component.setParent(newParent);
			if (vertical)
				newParent.insertBefore(lblError, component);
			else
				newParent.appendChild(lblError);
			if (parent != null)
				parent.insertBefore(newParent, sibling);
		}
	}
	
	public static void clearErrorMessage(Component component) {
//		if(component.getParent() instanceof Hlayout) {
		if(component.getParent() instanceof Hbox && component.getParent().getId().startsWith("errMsg-")) {
			Component parent = component.getParent();
			Components.removeAllChildren(parent);
			component.setParent(parent); 
//		} else if(component.getParent() instanceof Vlayout) {
		} else if(component.getParent() instanceof Vbox && component.getParent().getId().startsWith("errMsg-")) {
			List<Component> previousSiblings = getAllPreviousSiblings(component, null);
			for (Component sibling : previousSiblings) {
				if (sibling != null)
					component.getParent().removeChild(sibling);
			}
		}
	}
	
	private static List<Component> getAllPreviousSiblings(Component component, List<Component> result) {
		if (null == result) {
			result = new ArrayList<>();
		}
		if (null != component) {
			Component previous = component.getPreviousSibling();
			result.add(previous);
			getAllPreviousSiblings(previous, result);
		}
		return result;
	}
}
