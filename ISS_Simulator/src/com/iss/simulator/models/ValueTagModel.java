package com.iss.simulator.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import com.iss.simulator.util.TextNumberFormatter;

public class ValueTagModel extends AbstractTableModel {

	protected List<ValueTag> m_vector;
	protected boolean m_sortAsc;
	protected int m_sortCol;
	
	static final public ColumnData m_columns[] = { 
			new ColumnData("RedisKey", 80, JLabel.CENTER, true),
			new ColumnData("Description", 100, JLabel.CENTER, new DefaultCellEditor(new JTextField()), true),
			new ColumnData("RedisType", 0, JLabel.CENTER, false), 
			new ColumnData("MinValue", 80, JLabel.CENTER, new DefaultCellEditor(new JFormattedTextField(TextNumberFormatter.DoubleFormatter())), true),
			new ColumnData("MaxValue", 80, JLabel.CENTER, new DefaultCellEditor(new JFormattedTextField(TextNumberFormatter.DoubleFormatter())), true), 
			new ColumnData("String_Value", 100, JLabel.CENTER, true),
			new ColumnData("Boolean_Value", 100, JLabel.CENTER, new DefaultCellEditor(new JComboBox<String>(new String[] {"true","false"})), true) 
			};

	protected int m_columnsCount = m_columns.length;

	public ValueTagModel() {
		m_vector = new ArrayList<ValueTag>();
	}

	public int getRowCount() {
		return m_vector == null ? 0 : m_vector.size();
	}

	public void removeAll() {
		m_vector.clear();
	}
	
	public void removeRow(int row) {
		List<ValueTag> newList = new ArrayList<ValueTag>();
		for(int i=0; i<m_vector.size(); i++) {
			ValueTag vt = m_vector.get(i);
			if(i!=row) newList.add(vt);
		}
		m_vector = newList;
	}
	
	public void updateRow(int row, ValueTag data) {
		List<ValueTag> newList = new ArrayList<ValueTag>();
		for(int i=0; i<m_vector.size(); i++) {
			ValueTag vt = m_vector.get(i);
			if(i==row) newList.add(data);
			else if(i!=row) newList.add(vt);
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

	public void addData(ValueTag data) {
		m_vector.add(data);
	}

	public boolean isCellEditable(int nRow, int nCol) {
		if(nCol == 0 || nCol == 2) return false;
		else {
			ValueTag vt = getData(nRow);
			if("NUMBER".equals(vt.getRedisType())) {
				if(nCol == 5 || nCol == 6) return false;
			} else if("STRING".equals(vt.getRedisType())) {
				if(nCol == 3 || nCol == 4 || nCol == 6) return false;
			} else if("BOOLEAN".equals(vt.getRedisType())) {
				if(nCol == 3 || nCol == 4 || nCol == 5) return false;
			}
		}
		return true;
	}

	public ValueTag getData(int nRow) {
		return m_vector.get(nRow);
	}

	public Object getValueAt(int nRow, int nCol) {
		if (nRow < 0 || nRow >= getRowCount())
			return "";
		ValueTag row = (ValueTag) m_vector.get(nRow);
		switch (nCol) {
		case 0:
			return row.getRedisKey();
		case 1:
			return row.getDescription();
		case 2:
			return row.getRedisType();
		case 3:
			return row.getMinValue();
		case 4:
			return row.getMaxValue();
		case 5:
			return row.getString_Value();
		case 6:
			return row.getBoolean_Value();
		}
		return "";
	}
	
	public void setValueAt(Object aValue, int nRow, int nCol) {
		
		if (nRow < 0 || nRow >= getRowCount())
			return ;
		ValueTag row = (ValueTag) m_vector.get(nRow);
		
		try {
			row.setValue(nCol, aValue);
			//fireTableCellUpdated(nRow, nCol);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ;
	}
	
	public void changeValue(Map<String, ValueTag> targetData) {
		Map<String, ValueTag> sourceData = new HashMap<String, ValueTag>();
		
		for(ValueTag vt: m_vector) {
			sourceData.put(vt.getRedisKey(), vt);
		}
		
		Iterator<Entry<String, ValueTag>> it = targetData.entrySet().iterator();
		while(it.hasNext()) {
			String key = it.next().getKey();
			ValueTag vt = targetData.get(key);
			sourceData.put(key, vt);
		}
		
		Iterator<Entry<String, ValueTag>> it2 = sourceData.entrySet().iterator();
		removeAll();
		
		while(it2.hasNext()) {
			String key = it2.next().getKey();
			ValueTag vt = targetData.get(key);
			m_vector.add(vt);
		}
	}
	
}
 