package com.iss.simulator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SimulatorConfig extends Properties {
	
	Integer[] engineLoadSelectedValues;
	
	Properties pro;
	
	public SimulatorConfig() {
		
		FileInputStream fis = null;
		try {
			File file = new File("conf" + File.separator + "Simulator.properties");
			fis = new FileInputStream(file);
			
			pro = new Properties();
			pro.load(fis);
			
		} catch(Exception e ) {
			e.printStackTrace();
		} finally {
			if(fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public int getNumberProperty(String propertyName) {
		try {
			return Integer.parseInt(getProperty(propertyName));
		} catch(NumberFormatException e) {
			return 0;
		}
	}
	
	public int getNumberProperty(String propertyName, String defaultValue) {
		try {
			return Integer.parseInt(getProperty(propertyName, defaultValue));
		} catch(NumberFormatException e) {
			return 0;
		}
	}
	
	public String getProperty(String propertyName) {
		return pro.getProperty(propertyName);
	}
	
	public String getProperty(String propertyName, String defaultValue) {
		return pro.getProperty(propertyName, defaultValue);
	}
	
	public List<String> getEngineNames() {
		List<String> result = new ArrayList<String>();
		
		int meCount = 0;
		int geCount = 0;
		int blrCount = 0;
		
		try {
			meCount = Integer.parseInt(pro.getProperty("MainEngine.Count"));
		} catch(NumberFormatException e) {}
		
		try {
			geCount = Integer.parseInt(pro.getProperty("GeneratorEngine.Count"));
		} catch(NumberFormatException e) {}
		
		try {
			blrCount = Integer.parseInt(pro.getProperty("ShipBolier.Count"));
		} catch(NumberFormatException e) {}
		
		setEngineName(result, meCount, "ME");
		setEngineName(result, geCount, "GE");
		setEngineName(result, blrCount, "BLR");
		
		return result;
	}
	
	public String[] getEngineLoad() {
		String load = pro.getProperty("Engine.Load", "100%,75%,50%,25%,0%");
		return load.split(",");
	}
	
	public Integer[] getEngineLoadSelectedValues() {
		if(engineLoadSelectedValues == null) engineLoadSelectedValues = new Integer[getEngineNames().size()];
		return this.engineLoadSelectedValues;
	}
	
	public void setEngineLoadSelectedValue(int index, int value) {
		this.engineLoadSelectedValues[index] = value;
	}
	
	public Integer getEngineLoadSelectedValue(int index) {
		return this.engineLoadSelectedValues[index];
	}
	
	public void setEngineName(List<String> list, int count, String engine) {
		for(int i=0; i<count; i++) {
			list.add(engine + (i+1));
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		//SimulatorConfig sc = new SimulatorConfig();
		//sc.setProperty();
	}
}
