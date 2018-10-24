package com.iss.simulator.valuetag;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.iss.simulator.models.ValueTag;
import com.iss.simulator.models.ValueTagModel;
import com.iss.simulator.models.WayPointModel;
import com.iss.simulator.util.SimulatorConfig;
import com.iss.simulator.util.TextNumberFormatter;

public class ValueTagPanel extends JPanel {
	
	SimulatorConfig sc;
	
	JFrame frame;
	
	ValueTagPanel valuetagPanel;
	
	JPanel NumberGroupPanel;
	JPanel StringGroupPanel;
	JPanel BooleanGroupPanel;
	
	JTable table;
	
	JTextField searchT, redisKeyT, descriptionT, minValueT, maxValueT, StringValueT;
	
	JComboBox<String> redisTypeCmb;
	
	JCheckBox BooleanValueT;
	
	JButton searchBtn;
	
	ButtonAction action = new ButtonAction();
	
    public ValueTagPanel(JFrame frame) {
    	this.frame = frame;
    	valuetagPanel = this;
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        ButtonAction action = new ButtonAction();
        //search(top)
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        JPanel top1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel top2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        top2.setBorder(BorderFactory.createEmptyBorder(0, 130, 0, 0));
        top.add(top1);
        top.add(top2);
        
        JLabel search = new JLabel("Search Properties", JLabel.LEFT);
        search.setPreferredSize(new Dimension(150,20));
        top1.add(search);
        searchT = new JTextField();
        searchT.setPreferredSize(new Dimension(200,20));
        searchT.addActionListener(ae -> {
        	searchBtn.doClick();
        });
        top1.add(searchT);
        searchBtn = new JButton("Search");
        searchBtn.setPreferredSize(new Dimension(80,20));
        searchBtn.addActionListener(action);
        top1.add(searchBtn);
        
        JButton simulatorBtn = new JButton("SimulatorSetting");
        simulatorBtn.addActionListener(action);
        top2.add(simulatorBtn);
        
        ValueTagModel m_data = new ValueTagModel();
        table = new JTable();
        table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setAutoCreateColumnsFromModel(false);
        table.setModel(m_data);
        table.setRowHeight(30);
        table.setName("ValueTag");
        table.setAutoCreateRowSorter(true);
        //trs = new TableRowSorter<ValueTagModel>();
        //table.setRowSorter(trs);
        
        for(int i = 0 ; i <ValueTagModel.m_columns.length; i++) {
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        	renderer.setHorizontalAlignment(ValueTagModel.m_columns[i].getAlignment());
            TableColumn column = new TableColumn(i, ValueTagModel.m_columns[i].getWidth(), renderer, ValueTagModel.m_columns[i].getCellEditor());
            table.addColumn(column);
        }
        
        JScrollPane jScollPane = new JScrollPane(table);
        JTableHeader header = table.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
        add(jScollPane, BorderLayout.CENTER);
        
        //property and button ( right)
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(220,200));
        right.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
        
        /* Redis Key */
        JLabel redisKey = new JLabel("RedisKey", JLabel.LEFT);
        redisKey.setPreferredSize(new Dimension(80,20));
        right.add(redisKey);
        redisKeyT = new JTextField();
        redisKeyT.setName("RedisKey");
        redisKeyT.setPreferredSize(new Dimension(120,20));
        right.add(redisKeyT);

        /* Redis Type */
        JLabel redisType = new JLabel("RedisType", JLabel.LEFT);
        redisType.setPreferredSize(new Dimension(80,20));
        right.add(redisType);
        redisTypeCmb = new JComboBox<String>(new String[]{"NUMBER","STRING","BOOLEAN"});
        redisTypeCmb.setName("RedisType");
        redisTypeCmb.setPreferredSize(new Dimension(120,20));
        redisTypeCmb.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if("NUMBER".equals(redisTypeCmb.getSelectedItem())) {
					NumberGroupPanel.setVisible(true);
					StringGroupPanel.setVisible(false);
					BooleanGroupPanel.setVisible(false);
				} else if("STRING".equals(redisTypeCmb.getSelectedItem())) {
					NumberGroupPanel.setVisible(false);
					StringGroupPanel.setVisible(true);
					BooleanGroupPanel.setVisible(false);
				} else if("BOOLEAN".equals(redisTypeCmb.getSelectedItem())) {
					NumberGroupPanel.setVisible(false);
					StringGroupPanel.setVisible(false);
					BooleanGroupPanel.setVisible(true);
				}
			}
		});
        right.add(redisTypeCmb);
        
        /* Description */
        JLabel description = new JLabel("Description", JLabel.LEFT);
        description.setPreferredSize(new Dimension(80,20));
        right.add(description);
        descriptionT = new JTextField();
        descriptionT.setName("Description");
        descriptionT.setPreferredSize(new Dimension(120,20));
        right.add(descriptionT);

        /* Number Group Panel */
        NumberGroupPanel = new JPanel();
        NumberGroupPanel.setLayout(new GridLayout(2,2));
        NumberGroupPanel.setPreferredSize(new Dimension(220,50));
        right.add(NumberGroupPanel);
        
        /* MinValue */
        JLabel minValue = new JLabel("MinValue", JLabel.LEFT);
        minValue.setPreferredSize(new Dimension(60,20));
        NumberGroupPanel.add(minValue);
        minValueT = new JFormattedTextField(TextNumberFormatter.DoubleFormatter());
        minValueT.setName("MinValue");
        minValueT.setPreferredSize(new Dimension(120,20));
        NumberGroupPanel.add(minValueT);
        
        /* MaxValue */
        JLabel maxValue = new JLabel("MaxValue", JLabel.LEFT);
        maxValue.setPreferredSize(new Dimension(60,20));
        NumberGroupPanel.add(maxValue);
        maxValueT = new JFormattedTextField(TextNumberFormatter.DoubleFormatter());
        maxValueT.setName("MaxValue");
        maxValueT.setPreferredSize(new Dimension(120,20));
        NumberGroupPanel.add(maxValueT);
        
        /* String Group Panel */
        StringGroupPanel = new JPanel();
        StringGroupPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        StringGroupPanel.setPreferredSize(new Dimension(220,50));
        StringGroupPanel.setVisible(false);
        right.add(StringGroupPanel);
        
        JLabel StringValue = new JLabel("Value", JLabel.LEFT);
        StringValue.setPreferredSize(new Dimension(60,20));
        StringGroupPanel.add(StringValue);
        StringValueT = new JTextField();
        StringValueT.setName("StringValue");
        StringValueT.setPreferredSize(new Dimension(120,20));
        StringGroupPanel.add(StringValueT);
        
        /* Boolean Group Panel */
        BooleanGroupPanel = new JPanel();
        BooleanGroupPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        BooleanGroupPanel.setPreferredSize(new Dimension(220,50));
        BooleanGroupPanel.setVisible(false);
        right.add(BooleanGroupPanel);
        
        JLabel BooleanValue = new JLabel("Value", JLabel.LEFT);
        BooleanValue.setPreferredSize(new Dimension(60,20));
        BooleanGroupPanel.add(BooleanValue);
        BooleanValueT = new JCheckBox();
        BooleanValueT.setName("BooleanValue");
        BooleanValueT.setPreferredSize(new Dimension(120,20));
        BooleanGroupPanel.add(BooleanValueT);
        
        /* Add Button */
        JButton addBtn = new JButton("Add");
        addBtn.setPreferredSize(new Dimension(80,30));
        addBtn.addActionListener(action);
        right.add(addBtn);
        
        /* Delete Button */
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setPreferredSize(new Dimension(80,30));
        deleteBtn.addActionListener(action);
        right.add(deleteBtn);
        
        
        JPanel btnGroupPanel = new JPanel();
        btnGroupPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        btnGroupPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        right.add(btnGroupPanel);
        
        /* Import */
        JButton importBtn = new JButton("Import");
        importBtn.setName("ValueTagPanelImport");
        importBtn.setPreferredSize(new Dimension(80,30));
        importBtn.addActionListener(action);
        btnGroupPanel.add(importBtn);
        
        /* Export */
        JButton exportBtn = new JButton("Export");
        exportBtn.setPreferredSize(new Dimension(80,30));
        exportBtn.addActionListener(action);
        btnGroupPanel.add(exportBtn);
        
        add(top,BorderLayout.NORTH);
        add(right, BorderLayout.EAST);
    }
    
    public void addData(List<ValueTag> values) {
		ValueTagModel m_data = new ValueTagModel();
		for (ValueTag vt : values)
			m_data.addData(vt);
		table.setModel(m_data);
	}
	
	public void addData(ValueTag value) {
		ValueTagModel m_data = (ValueTagModel) table.getModel();
		m_data.addData(value);
		table.setModel(m_data);
		table.updateUI();
	}
	
	public void changeData(Map<String, ValueTag> map) {
		ValueTagModel m_data = (ValueTagModel) table.getModel();
		m_data.changeValue(map);
		table.setModel(m_data);
		table.updateUI();
	}
	
	public void setConfig(SimulatorConfig sc) {
		this.sc = sc;
	}
    
    class ButtonAction implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	
        	JButton obj = (JButton) e.getSource();
            String command = obj.getText();
            
            if ("SimulatorSetting".equals(command)){
            	SimulatorSetting spenel = new SimulatorSetting(frame, "SimulatorList Dialog");
            	spenel.setWayPointPanel(valuetagPanel);
            	spenel.setConfig(sc);
                spenel.createDialog();
            } else if("Add".equals(command)) {
            	
            	String redisType = (String) redisTypeCmb.getSelectedItem();
            	
            	ValueTag vt = new ValueTag();
            	vt.setValue(redisKeyT.getName(), redisKeyT.getText());
            	vt.setValue(redisTypeCmb.getName(), redisTypeCmb.getSelectedItem());
            	vt.setValue(descriptionT.getName(), descriptionT.getText());
            	
            	if("NUMBER".equals(redisType)) {
            		vt.setValue(minValueT.getName(), minValueT.getText());
            		vt.setValue(maxValueT.getName(), maxValueT.getText());
            	} else if("STRING".equals(redisType)) {
            		vt.setValue(StringValueT.getName(), StringValueT.getText());
            	} else if("BOOLEAN".equals(redisType)) {
            		vt.setValue(BooleanValueT.getName(), Boolean.toString(BooleanValueT.isSelected()));
            	}
            	
            	addData(vt);
            	
            } else if("Delete".equals(command)) {
            	
            	if(table.getSelectedRowCount() == 0) return;
            	
            	int[] rows = table.getSelectedRows();
				ValueTagModel m_data = (ValueTagModel) table.getModel();
				for(int i=(rows.length-1); i>=0; i--) {
					m_data.removeRow(table.getSelectedRow());
				}
				table.setModel(m_data);
				table.updateUI();
				
            } else if("Search".equals(command)){
            	String search = searchT.getText();
            	TableRowSorter<ValueTagModel> trs = new TableRowSorter<ValueTagModel>((ValueTagModel)table.getModel());
            	table.setRowSorter(trs);
            	trs.setModel((ValueTagModel)table.getModel());
            	trs.setRowFilter(RowFilter.regexFilter("(?i)" + search, new int[] {0}));
            	
            } else if("Import".equals(command)) {
            	ImportValueTagDialog spenel = new ImportValueTagDialog(frame, "Import Value & Tag");
				spenel.setWayPointPanel(valuetagPanel);
				spenel.createDialog();
            } else if("Export".equals(command)) {
            	ExportValueTagPane spenel = new ExportValueTagPane(frame, "Export");
            	spenel.setTable(table);
            	spenel.createDialog();
            }
        }
    }
};
