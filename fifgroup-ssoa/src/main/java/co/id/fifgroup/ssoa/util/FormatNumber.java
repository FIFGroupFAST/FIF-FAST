package co.id.fifgroup.ssoa.util;

import java.text.NumberFormat;

public class FormatNumber {

	public static String formatNumber(double number){
		final NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
		return nf.format(number);
	}
}
