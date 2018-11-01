package com.iss.simulator.services;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.iss.simulator.models.ValueTag;
import com.iss.simulator.models.WayPoint;
import com.iss.simulator.valuetag.ValueTagPanel;
import com.iss.simulator.waypoint.WayPointPanel;

public class LoadService {
	JPanel panel;
	
	File file;
	
	public LoadService(JPanel panel) {
		this.panel = panel;
	}
	
	public LoadService(JPanel panel, File file) {
		this.panel = panel;
		this.file = file;
	}
	
	public void load() throws Exception {
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(this.file);
			
			NodeList list = doc.getDocumentElement().getChildNodes();

			Node redisInfo = null, waypointmodel = null, valuetagmodel = null;
			
			for(int i=0; i<list.getLength(); i++) {
				Node node = list.item(i);
				if(node.getNodeType() == 1) {
					String nodeName = node.getNodeName();
					if("RedisInfo".equals(nodeName)) {
						redisInfo = node;
					} else if("WayPointModel".equals(nodeName)) {
						waypointmodel = node;
					} else if("ValueTagModel".equals(nodeName)) {
						valuetagmodel = node;
					}
				}
			}
			
			
			for(Component component: panel.getComponents()) {
				if(component instanceof JPanel) {
					if(redisInfo != null) {
						RedisInfoLoad((JPanel) component, redisInfo);
					}
				} else if(component instanceof JTabbedPane) { 
					if(waypointmodel != null || valuetagmodel != null) {
						ValueTabLoad((JTabbedPane) component, waypointmodel, valuetagmodel);
					}
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw e;
		} catch (SAXException e) {
			e.printStackTrace();
			throw e;
		} catch(IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void RedisInfoLoad(JPanel redisinfoPanel, Node redisInfo) {
		Map<String, String> vmap = new HashMap<String, String>();
		NodeList list = redisInfo.getChildNodes();
		for(int i=0; i<list.getLength(); i++) {
			Node info = list.item(i);
			if(info.getNodeType() == 1)
				vmap.put(info.getNodeName(), info.getTextContent());
		}
		
		Component[] components = redisinfoPanel.getComponents();
		for(int i=0; i<components.length; i++) {
			Component component = components[i];
			
			String name = component.getName();
			if(name == null) continue;
			
			if(vmap.containsKey(name)) {
				String value = vmap.get(name);
				
				if(component instanceof JTextField) {
					((JTextField) component).setText(value);
				} else if(component instanceof JCheckBox) {
					((JCheckBox)component).setSelected(Boolean.valueOf(value));
				}
			}
		}
	}
	
	private void ValueTabLoad(JTabbedPane tab, Node waypointmodel, Node valuetagmodel) {
		for(Component component: tab.getComponents()) {
			if(component instanceof WayPointPanel) 
				WayPointModelLoad((WayPointPanel) component, waypointmodel);
			else if(component instanceof ValueTagPanel)
				ValueTagModelLoad((ValueTagPanel) component, valuetagmodel);
		}
	}
	
	private void WayPointModelLoad(WayPointPanel waypointpanel, Node waypointmodel) {
		List<WayPoint> wlist = new ArrayList<WayPoint>();
		
		NodeList list = waypointmodel.getChildNodes();
		for(int i=0; i<list.getLength(); i++) {
			Node waypoints = list.item(i);
			if(waypoints.getNodeType() == 1) {
				wlist.add(WayPointLoad(waypoints));
			}
		}
		
		waypointpanel.addData(wlist);
	}
	
	private WayPoint WayPointLoad(Node waypoints) {
		WayPoint waypoint = new WayPoint();
		NodeList list = waypoints.getChildNodes();
		for(int i=0; i<list.getLength(); i++) {
			Node info = list.item(i);
			if(info.getNodeType() == 1) {
				waypoint.setValue(info.getNodeName(), info.getTextContent());
			}
		}
		return waypoint;
	}
	
	private void ValueTagModelLoad(ValueTagPanel valuetagpanel, Node valuetagmodel) {
		
		List<ValueTag> vlist = new ArrayList<ValueTag>();
		
		NodeList list = valuetagmodel.getChildNodes();
		for(int i=0; i<list.getLength(); i++) {
			Node valuetags = list.item(i);
			if(valuetags.getNodeType() == 1) {
				vlist.add(ValueTagLoad(valuetags));
			}
		}
		
		valuetagpanel.addData(vlist);
	}
	
	private ValueTag ValueTagLoad(Node valuetags) {
		
		ValueTag valuetag = new ValueTag();
		
		NodeList list = valuetags.getChildNodes();
		for(int i=0; i<list.getLength(); i++) {
			Node info = list.item(i);
			if(info.getNodeType() == 1) {
				valuetag.setValue(info.getNodeName(), info.getTextContent());
			}
		}
		return valuetag;
	}
	
}
