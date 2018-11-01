package com.iss.simulator.menu;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.iss.simulator.services.LoadService;
import com.iss.simulator.services.SaveService;
import com.iss.simulator.util.SimulatorConfig;

public class LoadConfigDialog extends JDialog {

	JFrame frame;
	
	JPanel panel;
	
	SimulatorConfig sc;
	
	public LoadConfigDialog(JFrame frame, JPanel panel) {
		super(frame, "Load Config", true);
		this.panel = panel;
		this.frame = frame;
	}
	
	public void setConfig(SimulatorConfig config) {
		this.sc = config;
	}
	
	public JDialog createDialog() {
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
		chooser.setCurrentDirectory(new File(sc.getProperty("Config.Folder")));
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileFilter(filter);
        
        chooser.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = (JFileChooser) e.getSource();
		        if (JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())) {
		        	try {
		        		
		        		if(chooser.getSelectedFile().isFile()) {
		        			
		        			LoadService service = new LoadService(panel, chooser.getSelectedFile());
		        			service.load();
		        			
		        			JOptionPane.showMessageDialog(panel, "Config Load Done");
		        			close();
		        			
		        		} else {
		        			throw new Exception("Not Selected File");
		        		}
		        		
		        	} catch(Exception ex) {
		        		ex.printStackTrace();
		        		JOptionPane.showMessageDialog(panel, ex.getMessage(), "Fail", JOptionPane.ERROR_MESSAGE);
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
	
}
