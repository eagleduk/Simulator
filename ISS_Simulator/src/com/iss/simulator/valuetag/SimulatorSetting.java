package com.iss.simulator.valuetag;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class SimulatorSetting extends JDialog implements  ActionListener{
	
	String engines[] = {"ME1", "ME2", "GE1", "GE2", "GE3", "GE4", "GE5", "BLR1", "BLR2" };
	
	Dialog dialog=this;
	
	public SimulatorSetting(Frame frame, String name) {
		super(frame, name, true);
		getContentPane().setLayout(new BorderLayout());
	}

	
	public JDialog createDialog() {
		setSize(engines.length * 115, 250);
		setLocation(200, 50);
		
		//top - filter
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
		
		JPanel setting = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
		
		for(String engine: engines) {
			
			JPanel content = new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
			content.setPreferredSize(new Dimension(100, 150));
			
			JList<String> list = new JList<String>(new String[]{"100%","75%","50%","25%","0%"});
			list.setName(engine);
			list.setPreferredSize(new Dimension(100, 100));
			DefaultListCellRenderer renderer =  (DefaultListCellRenderer)list.getCellRenderer();  
			renderer.setHorizontalAlignment(JLabel.CENTER); 
			content.add(list, BorderLayout.CENTER);
			
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
        System.out.println(command);
        if ("Import".equals(command)){
        	SimulatorListDialogImport spenel = new SimulatorListDialogImport(dialog, "Engine Load Setting");
            spenel.createDialog();
        } else if("Done".equals(command)) {
        } else if("Close".equals(command)) {
        	close();
        }
		
	}
	
	
}