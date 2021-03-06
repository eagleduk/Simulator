package com.iss.simulator.models;

public class ValueTag {
	
	String redisKey;
	String description;
	String redisType;
	String minValue;
	String maxValue;
	String String_Value;
	String Boolean_Value;
	
	public String getRedisKey() {
		return redisKey;
	}
	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRedisType() {
		return redisType;
	}
	public void setRedisType(String redisType) {
		this.redisType = redisType;
	}
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getString_Value() {
		return String_Value;
	}
	public void setString_Value(String string_Value) {
		String_Value = string_Value;
	}
	public String getBoolean_Value() {
		return Boolean_Value;
	}
	public void setBoolean_Value(String boolean_Value) {
		Boolean_Value = boolean_Value;
	}
	
	public void setValue(String name, Object value) {
		if("RedisKey".equals(name)) {
			setRedisKey((String) value);
		} else if("Description".equals(name)) {
			setDescription((String) value);
		} else if("MinValue".equals(name)) {
			if(value instanceof String) setMinValue((String) value);
			else if(value instanceof Double) setMinValue(String.valueOf((double)value));
		} else if("MaxValue".equals(name)) {
			if(value instanceof String) setMaxValue((String) value);
			else if(value instanceof Double) setMaxValue(String.valueOf((double)value));
		} else if("String_Value".equals(name) || "StringValue".equals(name) || "Value_String".equals(name)) {
			setString_Value((String) value);
		} else if("Boolean_Value".equals(name) || "BooleanValue".equals(name) || "Value_Boolean".equals(name)) {
			if(value instanceof Boolean) setBoolean_Value(Boolean.toString((boolean)value));
			setBoolean_Value((String)value);
		} else if("RedisType".equals(name)) {
			setRedisType(((String) value).toUpperCase());
		}
	}
	
	public void setValue(int nCol, Object value) {
		String name = "RedisKey";
		if(nCol == 1 ) name = "Description";
		else if(nCol == 3) name ="MinValue";
		else if(nCol == 4) name="MaxValue";
		else if(nCol == 5) name="String_Value";
		else if(nCol == 6) name ="Boolean_Value";
		else if(nCol == 2)name = "RedisType";
		
		setValue(name, value);
	}
	
	public Object getValue(int nCol) {
		String name = "RedisKey";
		if(nCol == 1 ) name = "Description";
		else if(nCol == 3) name ="MinValue";
		else if(nCol == 4) name="MaxValue";
		else if(nCol == 5) name="String_Value";
		else if(nCol == 6) name ="Boolean_Value";
		else if(nCol == 2) name = "RedisType";
		
		return getValue(name);
	}
	
	public Object getValue(String name) {
		if("RedisKey".equals(name)) {
			return getRedisKey();
		} else if("Description".equals(name)) {
			return getDescription();
		} else if("MinValue".equals(name)) {
			return getMinValue();
		} else if("MaxValue".equals(name)) {
			return getMaxValue();
		} else if("String_Value".equals(name) || "StringValue".equals(name) || "Value_String".equals(name)) {
			return getString_Value();
		} else if("Boolean_Value".equals(name) || "BooleanValue".equals(name) || "Value_Boolean".equals(name)) {
			return getBoolean_Value();
		} else if("RedisType".equals(name)) {
			return getRedisType();
		}
		return "";
	}
	
	public String getNameTag(int nCol) {
		String name = "RedisKey";
		if(nCol == 1 ) name = "Description";
		else if(nCol == 3) name ="MinValue";
		else if(nCol == 4) name="MaxValue";
		else if(nCol == 5) name="String_Value";
		else if(nCol == 6) name ="Boolean_Value";
		else if(nCol == 2) name = "RedisType";
		
		return name;
	}
}
