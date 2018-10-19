package com.iss.simulator.waypoint;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JTable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.iss.simulator.models.WayPoint;
import com.iss.simulator.models.WayPointModel;

public class ExportWayPointDialog extends JDialog {
	
	final String[] WayPointHeaders = {"WayPointNo", "Latitude", "Longitude", "LeadTime(s)", "Heading", "FwdDraft", "AftDraft"};
	
	JTable m_table;
	
	public ExportWayPointDialog(Frame frame, String name) {
		super(frame, name, true);
		getContentPane().setLayout(new BorderLayout());
	}
	
	public void setTable(JTable table) {
		this.m_table = table;
	}
	
	public JDialog createDialog() {
		
		try {
		
			WayPointModel model = (WayPointModel)m_table.getModel();
	
			String filename = new Date().getTime() + ".xlsx";
			File file = new File(filename);
			
	        
			Workbook workbook = new SXSSFWorkbook();
			SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet();
			
			Row row = sheet.createRow(0);
			for (int columns=0; columns<WayPointHeaders.length; columns++) {
				row.createCell(columns).setCellValue(WayPointHeaders[columns]);
			}
			
			
			for(int rows=0; rows<model.getRowCount(); rows++) {
				row = sheet.createRow(rows+1);
				WayPoint wp = model.getData(rows);
				for(int columns=0; columns<WayPointHeaders.length; columns++) {
					Cell cell = row.createCell(columns);
					Object oo = wp.getValue(WayPointHeaders[columns]);
					if(oo!=null) {
						if (oo instanceof String) {
							cell.setCellValue((String) oo);
						} else if(oo instanceof Boolean) {
							cell.setCellValue((Boolean) oo);
						} else if(oo instanceof Double) {
							cell.setCellValue((double) oo);
						} else if(oo instanceof Integer) {
							cell.setCellValue((int) oo);
						}
					}
				}
			}
			
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			
	        setSize(600, 400);
			setLocation(200, 50);
	        setVisible(true);
			setResizable(true);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public void close() {
		this.dispose();
	}
	
	public String convertLeadTime(double leadTime) {
		
		int hour = (int)leadTime / 3600;
		int min = (int)(leadTime % 3600) / 60;
		
		return hour + "h " + (min < 10 ? "0"+min:min) + "m";
	}
}