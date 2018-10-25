package com.iss.simulator.models;

import javax.swing.DefaultCellEditor;
import javax.swing.table.TableColumn;

public class ColumnData {
	String title;
	int width;
	int alignment;
	DefaultCellEditor cellEditor;
	boolean resizeable;

	public ColumnData(String title, int width, int alignment, boolean resizeable) {
		this.title = title;
		this.width = width;
		this.alignment = alignment;
		this.cellEditor = null;
		this.resizeable = resizeable;
	}

	public ColumnData(String title, int width, int alignment, DefaultCellEditor cellEditor) {
		this.title = title;
		this.width = width;
		this.alignment = alignment;
		this.cellEditor = cellEditor;
	}
	
	public ColumnData(String title, int width, int alignment, DefaultCellEditor cellEditor, boolean resizeable) {
		this.title = title;
		this.width = width;
		this.alignment = alignment;
		this.cellEditor = cellEditor;
		this.resizeable = resizeable;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getAlignment() {
		return alignment;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}
	
	public DefaultCellEditor getCellEditor() {
		return cellEditor;
	}
	
	public void setCellEditor(DefaultCellEditor cellEditor) {
		this.cellEditor = cellEditor;
	}

	public boolean isResizeable() {
		return resizeable;
	}

	public void setResizeable(boolean resizeable) {
		this.resizeable = resizeable;
	}
	
	public int getMaxSize() {
		return isResizeable() ? 999:0;
	}
	
}