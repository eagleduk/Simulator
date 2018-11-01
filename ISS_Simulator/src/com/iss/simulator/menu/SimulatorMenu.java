package com.iss.simulator.menu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.iss.simulator.util.SimulatorConfig;

@SuppressWarnings("serial")
public class SimulatorMenu extends JMenuBar {

	JFrame frame;
	
	JPanel panel;
	
	Timer timer;
	
	SimulatorConfig sc;
	
	int meCount, geCount, blrCount;
	
	int setMeCount, setGeCount, setBlrCount;
	
	MenuActionListener menuListener;
	
	public SimulatorMenu(JFrame frame, JPanel panel, Timer timer, SimulatorConfig sc) {
		this.frame = frame;
		this.panel = panel;
		this.timer = timer;
		this.sc = sc;
		menuListener = new MenuActionListener(this.frame, this.panel, this.timer, this.sc);
	}
	
	public SimulatorMenu(int meCount, int geCount, int blrCount) {
		this.meCount = meCount;
		this.geCount = geCount;
		this.blrCount = blrCount;
	}
	
	public JMenu FileMenu() {
		
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
		int defaultSelect = sc.getNumberProperty("MainEngine.defaultCount");
		int maxCount = sc.getNumberProperty("MainEngine.MaxCount");
		
		for(int i=1; i<=maxCount; i++) {
			JCheckBoxMenuItem item = new JCheckBoxMenuItem(String.valueOf(i), (defaultSelect == i));
			item.setPreferredSize(new Dimension(110, 20));
			item.addActionListener(new JCheckBoxMenuItemAction(1));
			
			bg.add(item);
			meMenu.add(item);
		}
		
		return meMenu;
	}
	
	public JMenu GeneratorCountMenu() {
		JMenu meMenu = new JMenu("Generator Engine");
		
		ButtonGroup bg = new ButtonGroup();
		int defaultSelect = sc.getNumberProperty("GeneratorEngine.defaultCount");
		int maxCount = sc.getNumberProperty("GeneratorEngine.MaxCount");
		
		for(int i=1; i<=maxCount; i++) {
			JCheckBoxMenuItem item = new JCheckBoxMenuItem(String.valueOf(i), (defaultSelect == i));
			item.setPreferredSize(new Dimension(110, 20));
			item.addActionListener(new JCheckBoxMenuItemAction(2));
			bg.add(item);
			
			meMenu.add(item);
		}
		
		return meMenu;
	}
	
	public JMenu ShipBoilerCountMenu() {
		JMenu meMenu = new JMenu("ShipBoiler Engine");
		
		ButtonGroup bg = new ButtonGroup();
		int defaultSelect = sc.getNumberProperty("ShipBolier.defaultCount");
		int maxCount = sc.getNumberProperty("ShipBolier.MaxCount");
		
		for(int i=1; i<=maxCount; i++) {
			JCheckBoxMenuItem item = new JCheckBoxMenuItem(String.valueOf(i), (defaultSelect == i));
			item.setPreferredSize(new Dimension(110, 20));
			item.addActionListener(new JCheckBoxMenuItemAction(3));
			bg.add(item);
			
			meMenu.add(item);
		}
		
		return meMenu;
	}
	
	class JCheckBoxMenuItemAction implements ActionListener {
		int mode;
		
		public JCheckBoxMenuItemAction(int mode) {
			this.mode = mode;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			sc.setEngineCount(this.mode, e.getActionCommand());
		}
	}
}
