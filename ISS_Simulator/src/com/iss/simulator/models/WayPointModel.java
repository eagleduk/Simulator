package com.iss.simulator.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class WayPointModel extends AbstractTableModel {
	protected List<WayPoint> m_vector;
	protected boolean m_sortAsc;
	protected int m_sortCol;

	static final public ColumnData m_columns[] = { 
				new ColumnData("WayPointNo", 100, JLabel.CENTER, true),
				new ColumnData("Latitude", 200, JLabel.CENTER, true), 
				new ColumnData("Longitude", 200, JLabel.CENTER, true),
				new ColumnData("LeadTime", 100, JLabel.CENTER, true), 
				new ColumnData("LeadTime(s)", 0, JLabel.CENTER, false),
				new ColumnData("Heading", 100, JLabel.CENTER, true), 
				new ColumnData("ForwardDraft", 100, JLabel.CENTER, true), 
				new ColumnData("AfterDraft", 100, JLabel.CENTER, true) 
			};

	protected int m_columnsCount = m_columns.length;

	public WayPointModel() {
		m_vector = new ArrayList<WayPoint>();
	}

	public int getRowCount() {
		return m_vector == null ? 0 : m_vector.size();
	}

	public void removeAll() {
		m_vector.clear();
	}
	
	public void removeRow(int row) {
		List<WayPoint> newList = new ArrayList<WayPoint>();
		for(int i=0; i<m_vector.size(); i++) {
			WayPoint wp = m_vector.get(i);
			if(i!=row) newList.add(wp);
		}
		m_vector = newList;
	}
	
	public void updateRow(int row, WayPoint data) {
		List<WayPoint> newList = new ArrayList<WayPoint>();
		for(int i=0; i<m_vector.size(); i++) {
			WayPoint wp = m_vector.get(i);
			if(i==row) newList.add(data);
			else if(i!=row) newList.add(wp);
		}
		m_vector = newList;
	}

	public String getColumnName(int column) {
		String str = m_columns[column].getTitle();
		return str;
	}

	public int getColumnCount() {
		return m_columns.length;
	}

	public void addData(WayPoint data) {
		m_vector.add(data);
	}

	public boolean isCellEditable(int nRow, int nCol) {
		return false;
	}

	public WayPoint getData(int nRow) {
		return m_vector.get(nRow);
	}
	
	public Object getValueAt(int nRow, int nCol) {
		if (nRow < 0 || nRow >= getRowCount())
			return "";
		WayPoint row = (WayPoint) m_vector.get(nRow);
		switch (nCol) {
		case 0:
			return row.getNo();
		case 1:
			return row.getLatitude();
		case 2:
			return row.getLongitude();
		case 3:
			return row.getLeadTimeString();
		case 4:
			return row.getLeadTime();
		case 5:
			return row.getHeading();
		case 6:
			return row.getForwardDraft();
		case 7:
			return row.getAfterDraft();
		}
		return "";
	}
}