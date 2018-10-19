package com.iss.simulator.valuetag;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.iss.simulator.models.ValueTag;

class SimulatorListDialogImport extends JDialog implements  ActionListener {
	
	String engines[] = {"ME1", "ME2", "GE1", "GE2", "GE3", "GE4", "GE5", "BLR1", "BLR2" };
	
	JLabel enginName;
	JLabel fileNameView;
	
	public SimulatorListDialogImport(Dialog dialog, String name) {
		super(dialog, name, true);
		getContentPane().setLayout(new FlowLayout());
	}
	

	public JDialog createDialog() {
		setSize(400, engines.length * 50 + 50);
		setLocation(400, 150);
		
		JPanel content = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
		content.setPreferredSize(new Dimension(400, engines.length * 45));
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
		
		/*
		
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
		
		*/
		
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

        if ("SimulatorList".equals(command)){

        } else if("Done".equals(command)) {
        	close();
        } else if("Upload".equals(command)) {
        	//System.out.println(btnName + " " + command);
        	FileUploadDialog dialog = new FileUploadDialog(this, "fe");
        	dialog.setEngine(btnName);
        	dialog.createDialog();
        } else if("Delete".equals(command)) {
        	//System.out.println(btnName + " " + command);
        	try {
				Files.delete((new File("." + File.separator + btnName + File.separator + btnName + ".png")).toPath());
			} catch (IOException ex) {
				// TODO Auto-generated catch block
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
			        		fos = new FileOutputStream(new File("." + File.separator + engine + File.separator + engine + ".png"));
			        		Files.copy(file.toPath(), fos);
			        		
			        		// JOptionPane
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