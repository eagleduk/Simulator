package com.iss.simulator;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.iss.simulator.services.SimulatorService;
import com.iss.simulator.util.TextNumberFormatter;
import com.iss.simulator.valuetag.ValueTagPanel;
import com.iss.simulator.waypoint.WayPointPanel;

public class IssSimulator extends JPanel {

	JTextField serverIP, port, timeOut, runtime;
	Checkbox use;
	
	static Timer timer;
	static int TimerSec = 2;

	public static void main(String[] args) throws Exception {
		try {
			JFrame frame = new JFrame();
			Container con = frame.getContentPane();
			IssSimulator ep = new IssSimulator(frame);
			con.add(ep);
			frame.setSize(800, 700);
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
		System.out.println("= ISS Simulator =");

		frame.setTitle("ISS Simulator v0.1");
		setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem save = new JMenuItem("save");
		JMenuItem load = new JMenuItem("load");
		JMenuItem exit = new JMenuItem("exit");
		exit.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				frame.dispose();
				frame.setVisible(false);
				timer.cancel();
			}
		});
		
		menu.add(save); menu.add(load); menu.add(exit);
		menuBar.add(menu);
		
		//frame.setJMenuBar(menuBar);

		JPanel settingPanel = new JPanel();
		settingPanel.setLayout(new FlowLayout());

		/* Server IP */
		JLabel label = new JLabel("Server IP:");
		settingPanel.add(label);
		serverIP = new JFormattedTextField(TextNumberFormatter.IPFormatter());
		serverIP.setPreferredSize(new Dimension(100, 20));
		serverIP.setText("127.000.000.001");
		serverIP.setName("serverIP");
		settingPanel.add(serverIP);

		/* Port */
		label = new JLabel(" Port:");
		settingPanel.add(label);
		port = new JFormattedTextField(TextNumberFormatter.IntegerFormatter());
		port.setPreferredSize(new Dimension(100, 20));
		port.setText("6379");
		port.setName("port");
		settingPanel.add(port);

		/* Timeout */
		label = new JLabel(" Timeout:");
		settingPanel.add(label);
		timeOut = new JFormattedTextField(TextNumberFormatter.IntegerFormatter());
		timeOut.setPreferredSize(new Dimension(100, 20));
		timeOut.setText("1000");
		timeOut.setName("timeout");
		settingPanel.add(timeOut);

		/* Runtime */
		label = new JLabel(" Runtime:");
		settingPanel.add(label);
		runtime = new JFormattedTextField(TextNumberFormatter.IntegerFormatter());
		runtime.setPreferredSize(new Dimension(100, 20));
		runtime.setText("2");
		runtime.setName("runtime");
		settingPanel.add(runtime);

		/* Use */
		label = new JLabel(" Use:");
		settingPanel.add(label);

		use = new Checkbox("");
		settingPanel.add(use);

		/*
		JButton button = new JButton("Done");
		settingPanel.add(button);
		*/
		
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
		tab.addTab("Way Point", wayPointPanel);

		ValueTagPanel valueTagPanel = new ValueTagPanel(frame);
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
				System.out.println(component.getName());
				map.put(component.getName(), (JTable)component );
			}
		}
		
	}
};