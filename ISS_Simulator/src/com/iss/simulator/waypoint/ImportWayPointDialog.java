package com.iss.simulator.waypoint;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.iss.simulator.models.WayPoint;

public class ImportWayPointDialog extends JDialog {
	
	final String[] WayPointHeaders = {"WayPointNo", "Latitude", "Longitude", "LeadTime(s)", "Heading", "FwdDraft", "AftDraft"};
	WayPointPanel wayPointPanel;
	
	public ImportWayPointDialog(Frame frame, String name) {
		super(frame, name, true);
		getContentPane().setLayout(new BorderLayout());
	}
	
	public void setWayPointPanel(WayPointPanel wayPointPanel){
		this.wayPointPanel = wayPointPanel;
	}

	public JDialog createDialog() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XLSX Files", "xlsx");
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setFileFilter(filter);
        chooser.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = (JFileChooser) e.getSource();
		        if (JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())) {
		        	File file = chooser.getSelectedFile();
		        	
		        	try {
		        	
			        	OPCPackage opcPackage = OPCPackage.open(file);
						XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
						opcPackage.close();
						XSSFSheet sheet = workbook.getSheetAt(0);
						boolean isHeader = true;
						
						List<WayPoint> list = new ArrayList<WayPoint>();
						for(Row row : sheet) {
							if(row.getCell(0) == null) break;
	
							if (isHeader) {
								isHeader = false;
								continue;
							}
							
							WayPoint wp = new WayPoint();
							
							for(int cellCount=0; cellCount<WayPointHeaders.length; cellCount++) {
								if(row.getCell(cellCount) != null) {
									
									Cell cell = row.getCell(cellCount);
									String cellName = WayPointHeaders[cellCount];
									int cellType = cell.getCellType();
									Object cellValue = null;
								
									if(cellType == Cell.CELL_TYPE_NUMERIC) {
										cellValue = cell.getNumericCellValue();
									} else if(cellType == Cell.CELL_TYPE_STRING) {
										cellValue = cell.getStringCellValue();
									} else if(cellType == Cell.CELL_TYPE_BOOLEAN) {
										cellValue = cell.getNumericCellValue();
									}
									
									if("LeadTime(s)".equals(cellName)) {
										String LeadTimeString = convertLeadTime((double)cellValue);
										wp.setValue("LeadTime", LeadTimeString);
									}
									
									wp.setValue(cellName, cellValue);
								}
							}
							list.add(wp);
						}
						
						wayPointPanel.addData(list);
						setVisible(false);
					} catch(Exception ex) {
						ex.printStackTrace();
					}
		        	
		        } else if (JFileChooser.CANCEL_SELECTION.equals(e.getActionCommand())) {
		        	setVisible(false);
		        }
			}
		});
        
        add(chooser);
        
        setSize(600, 400);
		setLocation(200, 50);
        setVisible(true);
		setResizable(false);
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