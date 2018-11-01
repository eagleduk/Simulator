package com.iss.simulator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings("serial")
public class SimulatorConfig extends Properties {
	
	Map<String, Integer> engineLoadSelectedValues = new HashMap<String, Integer>();
	
	Properties pro;
	
	int meCount;
	int geCount;
	int blrCount;
	
	public SimulatorConfig() {
		
		FileInputStream fis = null;
		try {
			File file = new File("conf" + File.separator + "Simulator.properties");
			fis = new FileInputStream(file);
			
			pro = new Properties();
			pro.load(fis);
			
			meCount = getNumberProperty("MainEngine.defaultCount", "2");
			geCount = getNumberProperty("GeneratorEngine.defaultCount", "4");
			blrCount = getNumberProperty("ShipBolier.defaultCount", "2");
			
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
	
	public boolean getBooleanProperty(String proeprtyName) {
		try {
			return Boolean.parseBoolean(getProperty(proeprtyName));
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public boolean getBooleanProperty(String proeprtyName, String defaultValue) {
		try {
			return Boolean.parseBoolean(getProperty(proeprtyName, defaultValue));
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public List<String> getEngineNames() {
		List<String> result = new ArrayList<String>();
		
		/*
		int meCount = getNumberProperty("MainEngine.Count");
		int geCount = getNumberProperty("GeneratorEngine.Count");
		int blrCount = getNumberProperty("ShipBolier.Count");
		*/
		
		setEngineName(result, meCount, "ME");
		setEngineName(result, geCount, "GE");
		setEngineName(result, blrCount, "BLR");
		
		return result;
	}
	
	public String[] getEngineLoad() {
		String load = pro.getProperty("Engine.Load", "100%,75%,50%,25%,0%");
		return load.split(",");
	}
	
	/*
	public Integer[] getEngineLoadSelectedValues() {
		if(engineLoadSelectedValues == null) engineLoadSelectedValues = new Integer[getEngineNames().size()];
		return this.engineLoadSelectedValues;
	}
	*/
	
	public void setEngineLoadSelectedValue(String key, int value) {
		this.engineLoadSelectedValues.put(key, value);
	}
	
	public Integer getEngineLoadSelectedValue(String key) {
		return this.engineLoadSelectedValues.get(key);
	}
	
	public void setEngineName(List<String> list, int count, String engine) {
		for(int i=0; i<count; i++) {
			list.add(engine + " " + (i+1));
		}
	}
	
	public void setMeCount(int value) {
		this.meCount = value;
	}
	
	public void setGeCount(int value) {
		this.geCount = value;
	}
	
	public void setBlrCount(int value) {
		this.blrCount = value;
	}

	public void setEngineCount(int mode, String actionCommand) {
		int value = 0;
		
		try {
			value = Integer.parseInt(actionCommand);
		} catch(NumberFormatException e) {}
		
		if(mode == 1) {
			setMeCount(value);
		} else if(mode == 2) {
			setGeCount(value);
		} else if(mode == 3) {
			setBlrCount(value);
		}
	}
}
