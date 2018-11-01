package com.iss.simulator.services;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.iss.simulator.models.ValueTag;
import com.iss.simulator.models.ValueTagModel;
import com.iss.simulator.models.WayPoint;
import com.iss.simulator.models.WayPointModel;
import com.iss.simulator.valuetag.ValueTagPanel;
import com.iss.simulator.waypoint.WayPointPanel;

public class SaveService {

	JPanel panel;
	
	File file;
	
	public SaveService(JPanel panel) {
		this.panel = panel;
	}
	
	public SaveService(JPanel panel, File file) {
		this.panel = panel;
		this.file = file;
	}
	
	public void save() throws Exception {
		
		FileOutputStream out = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Simulator");
			doc.appendChild(rootElement);
			
			for(Component component: panel.getComponents()) {
				if(component instanceof JPanel) 
					RedisInfoPanel((JPanel)component, doc, rootElement);
				else if(component instanceof JTabbedPane) 
					SimulatorValueTab((JTabbedPane)component, doc, rootElement);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");	
			DOMSource source = new DOMSource(doc);
			
			String fileName = this.file.getPath();
			if(fileName.indexOf(".xml") == -1) fileName = fileName + ".xml";
			
			out = new FileOutputStream(new File(fileName));
			
			StreamResult result = new StreamResult(out);

			// 파일로 쓰지 않고 콘솔에 찍어보고 싶을 경우 다음을 사용 (디버깅용)
			//StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw e;
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			throw e;
		} catch (TransformerException e) {
			e.printStackTrace();
			throw e;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	private void RedisInfoPanel(JPanel redisinfoPanel, Document doc, Element rootElement) {
		
		Element redisInfo = doc.createElement("RedisInfo");
		rootElement.appendChild(redisInfo);
		
		Component[] components = redisinfoPanel.getComponents();
		
		for(int i=0; i<components.length; i++) {
			Component component = components[i];
			
			String name = component.getName();
			if(name == null) continue;
			
			String value = "";
			Element info = doc.createElement(name);
			
			if(component instanceof JTextField) {
				value = ((JTextField) component).getText();
			} else if(component instanceof JCheckBox) {
				value = String.valueOf(((JCheckBox)component).isSelected());
			}
			
			info.setTextContent(value);
			redisInfo.appendChild(info);
		}
		
	}
	
	private void SimulatorValueTab(JTabbedPane tab, Document doc, Element rootElement) {
		for(Component component: tab.getComponents()) {
			if(component instanceof WayPointPanel) 
				WriteWayPointValues((WayPointPanel) component, doc, rootElement);
			else if(component instanceof ValueTagPanel)
				WriteValueTagValues((ValueTagPanel) component, doc, rootElement);
		}
	}
	
	private void WriteWayPointValues(WayPointPanel wayPointPanel, Document doc, Element rootElement) {
		for(Component cc: wayPointPanel.getComponents()) {
			if(cc instanceof JScrollPane) {
				JTable table = (JTable) ((JScrollPane) cc).getViewport().getComponents()[0];
				WayPointModel wpm = (WayPointModel)table.getModel();
				Element wpmE = doc.createElement("WayPointModel");
				WriteValues(wpm, doc, wpmE);
				rootElement.appendChild(wpmE);
			}
		}
	}
	
	private void WriteValues(WayPointModel wpm, Document doc, Element wpmE) {
		for(int i=0; i<wpm.getRowCount(); i++) {
			Element element = doc.createElement("WayPoint");
			WayPoint waypoint = wpm.getData(i);
			
			for(int j=0; j<WayPointModel.m_columns.length; j++) {
				String name = waypoint.getNameTag(j);
				String value = String.valueOf(waypoint.getValueAt(j));
				Element info = doc.createElement(name);
				info.setTextContent(value);
				element.appendChild(info);
			}
			
			wpmE.appendChild(element);
		}
	}
	
	private void WriteValueTagValues(ValueTagPanel valueTagPanel, Document doc, Element rootElement) {
		for(Component cc: valueTagPanel.getComponents()) {
			if(cc instanceof JScrollPane) {
				JTable table = (JTable) ((JScrollPane) cc).getViewport().getComponents()[0];
				ValueTagModel wtm = (ValueTagModel)table.getModel();

				Element wpmE = doc.createElement("ValueTagModel");
				WriteValues(wtm, doc, wpmE);
				rootElement.appendChild(wpmE);
			}
		}
	}
	
	private void WriteValues(ValueTagModel wtm, Document doc, Element wpmE) {
		for(int i=0; i<wtm.getRowCount(); i++) {
			Element element = doc.createElement("ValueTag");
			ValueTag valuetag = wtm.getData(i);
			
			for(int j=0; j<ValueTagModel.m_columns.length; j++) {
				String name = valuetag.getNameTag(j);
				String value = String.valueOf(valuetag.getValue(name));
				Element info = doc.createElement(name);
				info.setTextContent(value == null ? "" : value);
				element.appendChild(info);
			}
			wpmE.appendChild(element);
		}
	}

}
