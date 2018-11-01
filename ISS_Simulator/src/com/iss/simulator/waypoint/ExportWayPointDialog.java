package com.iss.simulator.waypoint;

import java.awt.Component;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.iss.simulator.models.WayPoint;
import com.iss.simulator.models.WayPointModel;
import com.iss.simulator.util.SimulatorConfig;

@SuppressWarnings({"serial","resource"})
public class ExportWayPointDialog extends JOptionPane {
	
	final String[] WayPointHeaders = {"WayPointNo", "Latitude", "Longitude", "LeadTime(s)", "Heading", "FwdDraft", "AftDraft"};
	
	Component com;
	JTable m_table;
	
	SimulatorConfig sc;
	
	String EXPORTFOLDER;
	
	public ExportWayPointDialog(Frame frame, String name) {
		super();
		this.com = frame;
	}
	
	public void setTable(JTable table) {
		this.m_table = table;
	}
	
	public void setConfig(SimulatorConfig sc) {
		this.sc = sc;
		EXPORTFOLDER = sc.getProperty("File.Export", "export");
		new File(EXPORTFOLDER).mkdirs();
	}
	
	public JOptionPane createDialog() {
		
		FileOutputStream fos = null;
		
		try {
		
			WayPointModel model = (WayPointModel)m_table.getModel();
	
			String filename = new Date().getTime() + "_WayPoint.xlsx";
			File file = new File(EXPORTFOLDER + File.separator + filename);
	        
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
			
			fos = new FileOutputStream(file);
			workbook.write(fos);
			
			JOptionPane.showMessageDialog(com,"File Export Success"); 
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(com,"File Export Error!!", "Alert", JOptionPane.WARNING_MESSAGE);      
		} finally {
			if(fos != null)
				try {
					fos.close();
				} catch (IOException e) {
				}
		}
		return this;
	}

	public String convertLeadTime(double leadTime) {
		
		int hour = (int)leadTime / 3600;
		int min = (int)(leadTime % 3600) / 60;
		
		return hour + "h " + (min < 10 ? "0"+min:min) + "m";
	}
}