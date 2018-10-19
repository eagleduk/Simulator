package com.iss.simulator.models;

public class WayPoint
{
	int no;
	double latitude;
	double longitude;
	double alignment;
	String leadTimeString;
	int leadTime;
	double heading;
	double forwardDraft;
	double afterDraft;
	
	public int getLeadTime() {
		return leadTime;
	}
	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getAlignment() {
		return alignment;
	}
	public void setAlignment(double alignment) {
		this.alignment = alignment;
	}
	public String getLeadTimeString() {
		return leadTimeString;
	}
	public void setLeadTimeString(String leadTimeString) {
		this.leadTimeString = leadTimeString;
	}
	public double getHeading() {
		return heading;
	}
	public void setHeading(double heading) {
		this.heading = heading;
	}
	public double getForwardDraft() {
		return forwardDraft;
	}
	public void setForwardDraft(double forwardDraft) {
		this.forwardDraft = forwardDraft;
	}
	public double getAfterDraft() {
		return afterDraft;
	}
	public void setAfterDraft(double afterDraft) {
		this.afterDraft = afterDraft;
	}

	public void setValue(String cellName, Object cellValue) {
		if("WayPointNo".equals(cellName)) {
			if(cellValue instanceof Double) setNo(new Double((double) cellValue).intValue());
			else if(cellValue instanceof String) setNo(Integer.parseInt((String)cellValue));
			else setNo(((Integer) cellValue).intValue());
		} else if("Latitude".equals(cellName)) {
			if(cellValue instanceof String) setLatitude(Double.parseDouble(((String)cellValue)));
			else setLatitude((double)cellValue);
		} else if("Longitude".equals(cellName)) {
			if(cellValue instanceof String) setLongitude(Double.parseDouble(((String)cellValue)));
			else setLongitude((double)cellValue);
		} else if("LeadTime(s)".equals(cellName)) {
			if(cellValue instanceof Double) setLeadTime(new Double((double) cellValue).intValue());
			else if(cellValue instanceof String) setLeadTime(Integer.parseInt((String)cellValue));
			else setLeadTime(((Integer) cellValue).intValue());
		} else if("Heading".equals(cellName)) {
			if(cellValue instanceof String) setHeading(Double.parseDouble(((String)cellValue)));
			else setHeading((double)cellValue);
		} else if("FwdDraft".equals(cellName) || "ForwardDraft".equals(cellName)) {
			if(cellValue instanceof String) setForwardDraft(Double.parseDouble(((String)cellValue)));
			else setForwardDraft((double)cellValue);
		} else if("AftDraft".equals(cellName) || "AfterDraft".equals(cellName)) {
			if(cellValue instanceof String) setAfterDraft(Double.parseDouble(((String)cellValue)));
			else setAfterDraft((double)cellValue);
		} else if("LeadTime".equals(cellName)) {
			setLeadTimeString((String)cellValue);
		}
	}
	
	public Object getValueAt(int nCol) {
		switch (nCol) {
		case 0:
			return getNo();
		case 1:
			return getLatitude();
		case 2:
			return getLongitude();
		case 3:
			return getLeadTimeString();
		case 4:
			return getLeadTime();
		case 5:
			return getHeading();
		case 6:
			return getForwardDraft();
		case 7:
			return getAfterDraft();
		}
		return "";
	}
	
	public Object getValue(String name) {
		Object result = null;
		if("WayPointNo".equals(name)) {
			result = getNo();
		} else if("Latitude".equals(name)) {
			result = getLatitude();
		} else if("Longitude".equals(name)) {
			result = getLongitude();
		} else if("LeadTime(s)".equals(name)) {
			result = getLeadTime();
		} else if("Heading".equals(name)) {
			result = getHeading();
		} else if("FwdDraft".equals(name) || "ForwardDraft".equals(name)) {
			result = getForwardDraft();
		} else if("AftDraft".equals(name) || "AfterDraft".equals(name)) {
			result = getAfterDraft();
		} else if("LeadTime".equals(name)) {
			result = getLeadTimeString();
		}
		return result;
	}
	
}