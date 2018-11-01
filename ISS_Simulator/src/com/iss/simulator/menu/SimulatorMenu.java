package com.iss.simulator.menu;

import java.awt.event.ActionEvent;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.iss.simulator.util.SimulatorConfig;

public class SimulatorMenu extends JMenuBar {

	JFrame frame;
	
	JPanel panel;
	
	Timer timer;
	
	SimulatorConfig sc;
	
	int meCount, geCount, blrCount;
	
	int setMeCount, setGeCount, setBlrCount;
	
	public SimulatorMenu(JFrame frame, JPanel panel, Timer timer, SimulatorConfig sc) {
		this.frame = frame;
		this.panel = panel;
		this.timer = timer;
		this.sc = sc;
	}
	
	public SimulatorMenu(int meCount, int geCount, int blrCount) {
		this.meCount = meCount;
		this.geCount = geCount;
		this.blrCount = blrCount;
	}
	
	public JMenu FileMenu() {
		
		MenuActionListener menuListener = new MenuActionListener(this.frame, this.panel, this.timer, this.sc);
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem save = new JMenuItem("save");
		save.addActionListener(menuListener);
		JMenuItem load = new JMenuItem("load");
		load.addActionListener(menuListener);
		JMenuItem exit = new JMenuItem("exit");
		exit.addActionListener(menuListener);

		fileMenu.add(save); fileMenu.add(load); 
		fileMenu.addSeparator();
		fileMenu.add(exit);
		
		return fileMenu;
	}
	
	public JMenu OptionMenu() {
		JMenu optionMenu = new JMenu("Option");
		JMenu meMenu = MainEngineCountMenu();
		JMenu geMenu = GeneratorCountMenu();
		JMenu blrMenu = ShipBoilerCountMenu();
		
		optionMenu.add(meMenu);
		optionMenu.add(geMenu);
		optionMenu.add(blrMenu);
		
		return optionMenu;
	}
	
	public JMenu MainEngineCountMenu() {
		JMenu meMenu = new JMenu("Main Engine");
		
		ButtonGroup bg = new ButtonGroup();
		JCheckBoxMenuItem j1 = new JCheckBoxMenuItem("1");
		JCheckBoxMenuItem j2 = new JCheckBoxMenuItem("2");
		JCheckBoxMenuItem j3 = new JCheckBoxMenuItem("3");
		JCheckBoxMenuItem j4 = new JCheckBoxMenuItem("4");
		bg.add(j1); bg.add(j2); bg.add(j3); bg.add(j4);
		meMenu.add(j1);
		meMenu.add(j2);
		meMenu.add(j3);
		meMenu.add(j4);
		
		return meMenu;
	}
	
	public JMenu GeneratorCountMenu() {
		JMenu meMenu = new JMenu("Generator Engine");
		
		ButtonGroup bg = new ButtonGroup();
		JCheckBoxMenuItem j1 = new JCheckBoxMenuItem("1");
		JCheckBoxMenuItem j2 = new JCheckBoxMenuItem("2");
		JCheckBoxMenuItem j3 = new JCheckBoxMenuItem("3");
		JCheckBoxMenuItem j4 = new JCheckBoxMenuItem("4");
		bg.add(j1); bg.add(j2); bg.add(j3); bg.add(j4);
		meMenu.add(j1);
		meMenu.add(j2);
		meMenu.add(j3);
		meMenu.add(j4);
		
		return meMenu;
	}
	
	public JMenu ShipBoilerCountMenu() {
		JMenu meMenu = new JMenu("ShipBoiler Engine");
		
		ButtonGroup bg = new ButtonGroup();
		JCheckBoxMenuItem j1 = new JCheckBoxMenuItem("1");
		JCheckBoxMenuItem j2 = new JCheckBoxMenuItem("2");
		JCheckBoxMenuItem j3 = new JCheckBoxMenuItem("3");
		JCheckBoxMenuItem j4 = new JCheckBoxMenuItem("4");
		bg.add(j1); bg.add(j2); bg.add(j3); bg.add(j4);
		meMenu.add(j1);
		meMenu.add(j2);
		meMenu.add(j3);
		meMenu.add(j4);
		
		return meMenu;
	}
}
