package com.iss.simulator.valuetag;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

class SimulatorListDialogImport extends JDialog implements  ActionListener{
	JLabel enginName;
	JLabel fileNameView;
	
	public SimulatorListDialogImport(Dialog dialog, String name) {
		super(dialog, name, true);
		getContentPane().setLayout(new GridLayout(3,2));
	}
	

	public JDialog createDialog() {
		setSize(600, 500);
		setLocation(400, 150);
		
		JPanel [] panel = new JPanel [6]; 
		for(int i = 0; i<panel.length; i++) {
			panel[i]=new JPanel();
			Border border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK)
					, BorderFactory.createEmptyBorder(30, 10, 5, 30));
			panel[i].setBorder(border);
			add(panel[i]);
			
			JLabel nameL = new JLabel("Engine Type : ", JLabel.LEFT);
			nameL.setPreferredSize(new Dimension(75,20));
			panel[i].add(nameL);
			
			enginName = new JLabel("MainEngin1", JLabel.CENTER);
			enginName.setPreferredSize(new Dimension(150,20));
			panel[i].add(enginName);
			
			JPanel fileuploadPanel = new JPanel();
			fileuploadPanel.setLayout(null);
			
			JButton fileBtn = new JButton("Choose File");
			fileBtn.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			fileBtn.setBackground(Color.WHITE);
			fileBtn.setBounds(0, 0, 75, 20);
			fileuploadPanel.add(fileBtn);
			
			fileNameView = new JLabel("Choose File Count");
			fileNameView.setBounds(0, 75, 105, 20);
			fileuploadPanel.add(fileNameView);
			
			JButton uploadBtn = new JButton("Upload");
			uploadBtn.setBackground(Color.BLACK);
			uploadBtn.setForeground(Color.WHITE);
			uploadBtn.setBounds(0, 180, 50, 20);
			uploadBtn.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			fileuploadPanel.add(uploadBtn);
			
			panel[i].add(fileuploadPanel);
		}
		
		
		
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
        String btnName=obj.getName();
        System.out.println(command);
        if ("SimulatorList".equals(command)){

        }
		
	}
	
	
}