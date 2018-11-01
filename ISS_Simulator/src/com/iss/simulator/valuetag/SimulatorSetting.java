package com.iss.simulator.valuetag;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.iss.simulator.models.ValueTag;
import com.iss.simulator.util.SimulatorConfig;

@SuppressWarnings("serial")
public class SimulatorSetting extends JDialog implements  ActionListener{
	
	Dialog dialog=this;

	SimulatorConfig sc;

	List<String> engines;
	
	List<JList<String>> lists;
	
	ValueTagPanel valuetagPanel;

	final String[] ValueTagHeaders = {"RedisKey", "Description", "RedisType", "MinValue", "MaxValue", "Value_String", "Value_Boolean"};
	
	public SimulatorSetting(Frame frame, String name) {
		super(frame, name, true);
		getContentPane().setLayout(new BorderLayout());
		lists = new ArrayList<JList<String>>();
	}

	public void setConfig(SimulatorConfig sc) {
		this.sc = sc;
		this.engines = sc.getEngineNames();
		//sc.getEngineLoadSelectedValues();
	}
	
	public void setWayPointPanel(ValueTagPanel valuetagPanel){
		this.valuetagPanel = valuetagPanel;
	}
	
	public JDialog createDialog() {
		setSize(engines.size() * 115, 250);
		setLocation(200, 50);
		
		String[] engineLoad = sc.getEngineLoad();
		
		//top - filter
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
		
		JPanel setting = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
		
		for(int i=0; i<this.engines.size(); i++) {
			String engine = this.engines.get(i);
			Integer value = sc.getEngineLoadSelectedValue(engine);
			
			JPanel content = new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
			content.setPreferredSize(new Dimension(100, 150));
			
			JList<String> list = new JList<String>(engineLoad);
			list.setName(engine);
			if(value != null) list.setSelectedIndex(value);
			list.setPreferredSize(new Dimension(100, 100));
			DefaultListCellRenderer renderer =  (DefaultListCellRenderer)list.getCellRenderer();  
			renderer.setHorizontalAlignment(JLabel.CENTER); 
			content.add(list, BorderLayout.CENTER);
			lists.add(list);
			
			JLabel engineName = new JLabel(engine, FlowLayout.LEFT);
			engineName.setPreferredSize(new Dimension(70,20));
			content.add(engineName, BorderLayout.SOUTH);
			
			setting.add(content);
		}
		
		add(setting, BorderLayout.CENTER);
		
		//bottom - button
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
		JButton importBtn = new JButton("Import");
		importBtn.addActionListener(this);
		bottom.add(importBtn);
		JButton doneBtn = new JButton("Done");
		doneBtn.addActionListener(this);
		bottom.add(doneBtn);
		JButton close = new JButton("Close");
		close.addActionListener(this);
		bottom.add(close);
		add(top, BorderLayout.NORTH);
		add(bottom, BorderLayout.SOUTH);
		
		setVisible(true);
		setResizable(true);
		return this;
	}

	public void close() {
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton obj = (JButton) e.getSource();
        String command = obj.getText();
        if ("Import".equals(command)){
        	LoadUpload spenel = new LoadUpload(dialog, "Engine Load Setting");
        	spenel.setEngines(engines);
        	spenel.setConfig(sc);
            spenel.createDialog();
        } else if("Done".equals(command)) {
        	Map<String, ValueTag> map = GetLoadSettingData();
        	valuetagPanel.changeData(map);
        } else if("Close".equals(command)) {
        	close();
        }
		
	}
	
	public Map<String, ValueTag> GetLoadSettingData() {
		Map<String, ValueTag> result = new HashMap<String, ValueTag>();
		for(int i=0; i<lists.size(); i++) {
			JList<String> list = lists.get(i);
    		File file = new File(sc.getProperty("ValueTag.LoadRoot") + File.separator + list.getName() + File.separator + "Load.xlsx");
    		XSSFWorkbook workbook = null;
    		try {
	        	if(!file.isFile()) continue;
	        	
	        	OPCPackage opcPackage = OPCPackage.open(file);
				workbook = new XSSFWorkbook(opcPackage);
				opcPackage.close();
				if(workbook.getNumberOfSheets() <= list.getSelectedIndex()) continue;
				XSSFSheet sheet = workbook.getSheetAt(list.getSelectedIndex());
				boolean isHeader = true;
				
				for(Row row : sheet) {
					if(row.getCell(0) == null) break;

					if (isHeader) {
						isHeader = false;
						continue;
					}
					
					ValueTag vt = new ValueTag();
					
					for(int cellCount=0; cellCount<ValueTagHeaders.length; cellCount++) {
						if(row.getCell(cellCount) != null) {
							
							Cell cell = row.getCell(cellCount);
							String cellName = ValueTagHeaders[cellCount];
							int cellType = cell.getCellType();
							Object cellValue = null;
						
							if(cellType == Cell.CELL_TYPE_NUMERIC || cellType == Cell.CELL_TYPE_FORMULA) {
								cellValue = cell.getNumericCellValue();
							} else if(cellType == Cell.CELL_TYPE_STRING) {
								cellValue = cell.getStringCellValue();
							} else if(cellType == Cell.CELL_TYPE_BOOLEAN) {
								cellValue = cell.getBooleanCellValue();
							}
							vt.setValue(cellName, cellValue);
						}
					}
					result.put(vt.getRedisKey(), vt);
				}
				setVisible(false);
				
				sc.setEngineLoadSelectedValue(list.getName(), list.getSelectedIndex());
				
			} catch(Exception ex) {
				ex.printStackTrace();
			}
    	}
		return result;
	}
}