package com.iss.simulator.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class TextNumberFormatter extends NumberFormatter {

	public TextNumberFormatter() {
	}
	
	public static NumberFormatter IntegerFormatter() {
		NumberFormatter formatter = null;
		try {
			NumberFormat format = NumberFormat.getIntegerInstance();
			format.setParseIntegerOnly(true);
			format.setGroupingUsed(false);
			
			formatter = new NumberFormatter(format);
			formatter.setValueClass(Long.class);
			formatter.setAllowsInvalid(false);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return formatter;
	}
	
	public static MaskFormatter IPFormatter() {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("###.###.###.###");
			//formatter.setValidCharacters("01234567");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return formatter;
	}
	
	public static NumberFormatter DoubleFormatter() {
		NumberFormatter formatter = null;
		try {
			NumberFormat format = DecimalFormat.getInstance();
			format.setGroupingUsed(false);
			format.setMinimumFractionDigits(1);
			format.setMinimumFractionDigits(4);
			format.setRoundingMode(RoundingMode.DOWN);
			
			formatter = new NumberFormatter(format);
			formatter.setAllowsInvalid(false);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return formatter;
	}
	
}
