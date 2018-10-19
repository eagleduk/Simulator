package com.iss.simulator.valuetag;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import com.iss.simulator.models.ValueTagModel;

public class SimulatorListDialog extends JDialog implements  ActionListener{
	Dialog dialog=this;
	public SimulatorListDialog(Frame frame, String name) {
		super(frame, name, true);
		getContentPane().setLayout(new BorderLayout());
	}

	
	public JDialog createDialog() {
		setSize(700, 500);
		setLocation(200, 50);
		
		//top - filter
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
		
		JLabel engineType = new JLabel("Engine Type", FlowLayout.LEFT);
		engineType.setPreferredSize(new Dimension(70,20));
		top.add(engineType);
		JComboBox<Object> engineTypeCmb = new JComboBox<Object>();
		engineTypeCmb.setPreferredSize(new Dimension(100,20));
		top.add(engineTypeCmb);
		
		JLabel dataType = new JLabel("Data Type", FlowLayout.LEFT);
		dataType.setPreferredSize(new Dimension(60,20));
		top.add(dataType);
		JComboBox<Object> dataTypeCmb = new JComboBox<Object>();
		dataTypeCmb.setPreferredSize(new Dimension(100,20));
		top.add(dataTypeCmb);
		
		//center - table
		
		ValueTagModel m_data = new ValueTagModel();
        JTable table = new JTable();
        
        table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setAutoCreateColumnsFromModel(false);
        table.setModel(m_data);
        table.setRowHeight(30);
        
        for(int i = 0 ; i <ValueTagModel.m_columns.length; i++) {
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        	renderer.setHorizontalAlignment(ValueTagModel.m_columns[i].getAlignment());
            javax.swing.table.TableColumn column = new javax.swing.table.TableColumn(i, ValueTagModel.m_columns[i].getWidth(), renderer, null);
            table.addColumn(column);
        }
        
        JScrollPane jScollPane = new JScrollPane(table);
        JTableHeader header = table.getTableHeader();
        header.setUpdateTableInRealTime(true);
        header.setReorderingAllowed(true);
       
		 
		 
		//bottom - button

		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
		JButton importBtn = new JButton("Import");
		importBtn.addActionListener(this);
		bottom.add(importBtn);
		JButton doneBtn = new JButton("Done");
		doneBtn.addActionListener(this);
		bottom.add(doneBtn);
		add(top, BorderLayout.NORTH);
		add(jScollPane, BorderLayout.CENTER);
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
        System.out.println(command);
        if ("Import".equals(command)){
        	SimulatorListDialogImport spenel = new SimulatorListDialogImport(dialog, "SimulatorList Dialog");
            spenel.createDialog();
        }else if("Done".equals(command)){
        	
        }
		
	}
	
	
}