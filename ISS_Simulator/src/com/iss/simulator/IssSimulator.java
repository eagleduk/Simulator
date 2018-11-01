package com.iss.simulator;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.iss.simulator.menu.SimulatorMenu;
import com.iss.simulator.services.SimulatorService;
import com.iss.simulator.util.SimulatorConfig;
import com.iss.simulator.util.TextNumberFormatter;
import com.iss.simulator.valuetag.ValueTagPanel;
import com.iss.simulator.waypoint.WayPointPanel;

public class IssSimulator extends JPanel {
	
	static SimulatorConfig sc;
	
	JTextField serverIP, port, timeOut, runtime;
	JCheckBox isLocal, use;
	
	static Timer timer;
	static int TimerSec = 2;
	
	String tempIP;

	public static void main(String[] args) throws Exception {
		try {
			sc = new SimulatorConfig();
			
			JFrame frame = new JFrame();
			Container con = frame.getContentPane();
			IssSimulator ep = new IssSimulator(frame);
			con.add(ep);
			frame.setSize(800, 700);
			frame.setLocation(500, 100);
			frame.setResizable(true);
			frame.setVisible(true);
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) { 
					System.exit(0);
					frame.dispose();
					frame.setVisible(false);
                    timer.cancel();
				}
			});
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public IssSimulator(JFrame frame) {
		
		frame.setTitle("ISS Simulator " + sc.getProperty("Sim.Version", "v0.1"));
		setLayout(new BorderLayout());
		
		JPanel settingPanel = new JPanel();
		settingPanel.setLayout(new FlowLayout());
		
		SimulatorMenu menu = new SimulatorMenu(frame, this, timer, sc);
		
		JMenuBar menubar = new JMenuBar();
		menubar.add(menu.FileMenu());
		menubar.add(menu.OptionMenu());
		frame.setJMenuBar(menubar);
		
		/* Server IP */
		JLabel label = new JLabel(" Server IP : ");
		settingPanel.add(label);
		serverIP = new JFormattedTextField(TextNumberFormatter.IPFormatter());
		serverIP.setPreferredSize(new Dimension(110, 20));
		serverIP.setText(sc.getProperty("Redis.host", "127.000.000.001"));
		serverIP.setName("serverIP");
		settingPanel.add(serverIP);

		/* Local Check */
		isLocal = new JCheckBox("localhost");
		isLocal.setName("isLocal");
		isLocal.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				serverIP.setEditable(!isLocal.isSelected());
				if(isLocal.isSelected()) {
					tempIP = serverIP.getText();
					serverIP.setText("127.000.000.001");
				} else {
					serverIP.setText(tempIP);
				}
			}
		});
		settingPanel.add(isLocal);

		/* Port */
		label = new JLabel(" Port : ");
		settingPanel.add(label);
		port = new JFormattedTextField(TextNumberFormatter.IntegerFormatter());
		port.setPreferredSize(new Dimension(60, 20));
		port.setText(sc.getProperty("Redis.port", "6379"));
		port.setName("port");
		settingPanel.add(port);

		/* Timeout */
		label = new JLabel(" Timeout : ");
		settingPanel.add(label);
		timeOut = new JFormattedTextField(TextNumberFormatter.IntegerFormatter());
		timeOut.setPreferredSize(new Dimension(60, 20));
		timeOut.setText(sc.getProperty("Redis.timeout", "1000"));
		timeOut.setName("timeout");
		settingPanel.add(timeOut);

		/* Runtime */
		label = new JLabel(" Runtime : ");
		settingPanel.add(label);
		runtime = new JFormattedTextField(TextNumberFormatter.IntegerFormatter());
		runtime.setPreferredSize(new Dimension(40, 20));
		runtime.setText(sc.getProperty("Redis.Runtime", "2"));
		runtime.setName("runtime");
		settingPanel.add(runtime);

		/* Use */
		label = new JLabel(" Use : ");
		settingPanel.add(label);

		use = new JCheckBox("");
		settingPanel.add(use);

		JToggleButton button = new JToggleButton("START");
		button.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton abstractButton = (AbstractButton) e.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                if(selected) {
                	// RunTimer
                	button.setText(" STOP ");
                	Map<String, JTable> map = GetValues();
                	
                	TimerSec = Integer.parseInt(runtime.getText());
                	
                	timer = new Timer();

                	SimulatorService service = new SimulatorService(serverIP.getText(), port.getText(), timeOut.getText(), runtime.getText());
                	service.setTableDatas(map);
                	timer.schedule(service, new Date(), TimerSec * 1000);
                } else {
                	// Stop Timer
                	button.setText("START");
                	timer.cancel();
                }
			}
		});
		
		settingPanel.add(button);
		
		add(settingPanel, BorderLayout.NORTH);

		JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);

		WayPointPanel wayPointPanel = new WayPointPanel(frame);
		wayPointPanel.setConfig(sc);
		tab.addTab("Way Point", wayPointPanel);

		ValueTagPanel valueTagPanel = new ValueTagPanel(frame);
		valueTagPanel.setConfig(sc);
		tab.addTab("Value & Tag", valueTagPanel);

		add(tab, BorderLayout.CENTER);
	}
	
	public Map<String, JTable> GetValues() {
		Map<String, JTable> map = new HashMap<String, JTable>();
    	for(Component c: getComponents()) {
    		Component[] components = null;
    		if(c instanceof JPanel) {
    			components = ((JPanel)c).getComponents();
    		} else if(c instanceof JTabbedPane) {
    			components = ((JTabbedPane) c).getComponents();
    			GetValues(map, components);
    		}
    	}
    	return map;
	}
	
	public void GetValues(Map<String, JTable> map, Component[] components) {
		for(Component component: components) {
			if(component instanceof JTextField) {
			} else if(component instanceof JPanel) {
				GetValues(map, ((JPanel)component).getComponents());
			} else if(component instanceof JScrollPane) {
				GetValues(map, ((JScrollPane)component).getViewport().getComponents());
			} else if(component instanceof JTable) {
				map.put(component.getName(), (JTable)component );
			}
		}
	}
};