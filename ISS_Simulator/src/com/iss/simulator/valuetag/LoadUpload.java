package com.iss.simulator.valuetag;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.iss.simulator.util.SimulatorConfig;

@SuppressWarnings("serial")
class LoadUpload extends JDialog implements  ActionListener {
	
	List<String> engines;
	
	SimulatorConfig sc;
	
	String DATAFOLDER;
	
	JLabel enginName;
	JLabel fileNameView;
	
	public LoadUpload(Dialog dialog, String name) {
		super(dialog, name, true);
		getContentPane().setLayout(new FlowLayout());
	}
	
	public void setEngines(List<String> engines) {
		this.engines = engines;
	}
	
	public void setConfig(SimulatorConfig sc) {
		this.sc = sc;
		DATAFOLDER = sc.getProperty("ValueTag.LoadRoot", "data");
		new File(DATAFOLDER).mkdirs();
	}

	public JDialog createDialog() {
		setSize(400, engines.size() * 50 + 50);
		setLocation(400, 150);
		
		JPanel content = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
		content.setPreferredSize(new Dimension(400, engines.size() * 45));
		for(String engine: engines) {
			
			JPanel eContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,5));
			eContent.setPreferredSize(new Dimension(400, 35));
			
			JLabel engineLabel = new JLabel(engine);
			engineLabel.setPreferredSize(new Dimension(75,20));
			eContent.add(engineLabel);
			
			JButton upload = new JButton("Upload");
			upload.setName(engine);
			upload.setPreferredSize(new Dimension(100, 25));
			upload.addActionListener(this);
			eContent.add(upload);
			
			JButton delete = new JButton("Delete");
			delete.setName(engine);
			delete.setPreferredSize(new Dimension(100, 25));
			delete.addActionListener(this);
			eContent.add(delete);
			
			content.add(eContent);
		}
		
		add(content, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT,30,10));
		bottom.setPreferredSize(new Dimension(400, 40));
		
		JButton doneBtn = new JButton("Done");
		doneBtn.addActionListener(this);
		bottom.add(doneBtn);
		
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
        String btnName=obj.getName();

        if("Done".equals(command)) {
        	close();
        } else if("Upload".equals(command)) {
        	FileUploadDialog dialog = new FileUploadDialog(this, "File Upload");
        	dialog.setEngine(btnName);
        	dialog.createDialog();
        } else if("Delete".equals(command)) {
        	try {
        		File directory = new File(DATAFOLDER + File.separator + btnName);
        		for(File files: directory.listFiles()) {
        			Files.delete(files.toPath());
        		}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
        }
	}
	
	class FileUploadDialog extends JDialog {
		
		String engine;
		
		public FileUploadDialog(Dialog dialog, String name) {
			super(dialog, name, true);
		}
		
		public void setEngine(String engine) {
			this.engine = engine;
		}
		
		public JDialog createDialog() {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("XLSX Files", "xlsx");
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
	        chooser.setFileFilter(filter);
	        chooser.addActionListener(new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = (JFileChooser) e.getSource();
			        if (JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())) {
			        	File file = chooser.getSelectedFile();
			        	FileOutputStream fos = null;
			        	try {
			        		File directory = new File(DATAFOLDER + File.separator + engine);
			        		if(!directory.exists()) directory.mkdir();
			        		fos = new FileOutputStream(new File(DATAFOLDER + File.separator + engine + File.separator + "load.xlsx"));
			        		Files.copy(file.toPath(), fos);
			        		
			        		// JOptionPane
			        		
			        		setVisible(false);
			        	} catch(Exception ex) {
			        		ex.printStackTrace();
			        	} finally {
			        		try {
								if(fos != null) fos.close();
							} catch (IOException ex1) {
								ex1.printStackTrace();
							}
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
	}
	
}