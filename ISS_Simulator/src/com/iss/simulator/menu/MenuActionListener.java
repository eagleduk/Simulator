package com.iss.simulator.menu;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowStateListener;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.iss.simulator.services.SaveService;
import com.iss.simulator.util.SimulatorConfig;

public class MenuActionListener implements ActionListener {

	JFrame frame;
	
	JPanel panel;
	
	Timer timer;
	
	SimulatorConfig sc;
	
	public MenuActionListener(JFrame frame, JPanel panel, Timer timer, SimulatorConfig sc) {
		this.frame = frame;
		this.panel = panel;
		this.timer = timer;
		this.sc = sc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem obj = (JMenuItem) e.getSource();
        String command = obj.getText();
        if("save".equals(command)) {
        	SaveConfigDialog config = new SaveConfigDialog(frame, panel);
        	config.setConfig(sc);
        	config.createDialog();
        } else if("load".equals(command)) {
        	LoadConfigDialog config = new LoadConfigDialog(frame, panel);
        	config.setConfig(sc);
        	config.createDialog();
        } else if("exit".equals(command)) {
        	System.exit(0);
			frame.dispose();
			frame.setVisible(false);
            timer.cancel();
        }
	}

}
