package com.iss.simulator.menu;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SimulatorMenu extends JMenuBar {

	int meCount, geCount, blrCount;
	
	int setMeCount, setGeCount, setBlrCount;
	
	public SimulatorMenu(int meCount, int geCount, int blrCount) {
		this.meCount = meCount;
		this.geCount = geCount;
		this.blrCount = blrCount;
	}
	
	public JMenu FileMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem save = new JMenuItem("save");
		JMenuItem load = new JMenuItem("load");
		JMenuItem exit = new JMenuItem("exit");
		
		/*
		exit.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				frame.dispose();
				frame.setVisible(false);
				timer.cancel();
			}
		});
		*/
		
		fileMenu.add(save); fileMenu.add(load); fileMenu.add(exit);
		
		return fileMenu;
	}
	
	public JMenu OptionMenu() {
		JMenu optionMenu = new JMenu("Option");
		JMenu meMenu = new JMenu("MainEngine");
		
		ButtonGroup bg = new ButtonGroup();
		JCheckBoxMenuItem j1 = new JCheckBoxMenuItem("1");
		JCheckBoxMenuItem j2 = new JCheckBoxMenuItem("2");
		bg.add(j1); bg.add(j2);
		meMenu.add(j1);
		meMenu.add(j2);
		
		optionMenu.add(meMenu);
		
		return optionMenu;
	}
}
