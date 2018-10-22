package com.iss.simulator.waypoint;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.poi.util.SystemOutLogger;

import com.iss.simulator.models.WayPoint;
import com.iss.simulator.util.TextNumberFormatter;

public class UpdateWayPointDialog extends JDialog {
	
	JPanel settingPanel;
	
	JTextField wayPointNoT, latitudeT, longitudeT, leadTimeT, headingT, forwardDraftT, afterDraftT, convertLeadTimeTF;
	
	JLabel convertLeadTimeT;
	
	int row;
	
	WayPoint wp;
	
	WayPointPanel wayPointPanel;
	
	public UpdateWayPointDialog(Frame frame, String name) {
		super(frame, name, true);
		getContentPane().setLayout(new BorderLayout());
	}
	
	public void setRowNumber(int row) {
		this.row = row;
	}
	
	public void setWayPoint(WayPoint wp) {
		this.wp = wp;
	}
	
	public void setWayPointPanel(WayPointPanel wayPointPanel){
		this.wayPointPanel = wayPointPanel;
	}

	public JDialog createDialog() {
		setSize(600, 400);
		setLocation(200, 50);
		
		JPanel wrap = new JPanel();
		wrap.setLayout(new BorderLayout());
		wrap.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 0));
		JLabel index = new JLabel(String.valueOf(wp.getNo()), JLabel.LEFT);
		index.setFont(index.getFont().deriveFont(20.0f));
		wrap.add(index,BorderLayout.NORTH);
		
		settingPanel = new JPanel();
		settingPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 10));
		settingPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 90));

		/* WayPoint No 
		JLabel wayPointNo = new JLabel("WayPointNo", JLabel.LEFT);
		wayPointNo.setPreferredSize(new Dimension(120, 20));
		settingPanel.add(wayPointNo);
		wayPointNoT = new JFormattedTextField(TextNumberFormatter.IntegerFormatter());
		wayPointNoT.setText(String.valueOf(wp.getNo()));
		wayPointNoT.setPreferredSize(new Dimension(300, 20));
		wayPointNoT.setName("WayPointNo");
		settingPanel.add(wayPointNoT);
		 */
		
		/* Latitude */
		JLabel latitude = new JLabel("Latitude", JLabel.LEFT);
		latitude.setPreferredSize(new Dimension(120, 20));
		settingPanel.add(latitude);
		latitudeT = new JFormattedTextField(TextNumberFormatter.DoubleFormatter());
		latitudeT.setText(String.valueOf(wp.getLatitude()));
		latitudeT.setPreferredSize(new Dimension(300, 20));
		latitudeT.setName("Latitude");
		settingPanel.add(latitudeT);

		/* Longitude */
		JLabel longitude = new JLabel("Longitude", JLabel.LEFT);
		longitude.setPreferredSize(new Dimension(120, 20));
		settingPanel.add(longitude);
		longitudeT = new JFormattedTextField(TextNumberFormatter.DoubleFormatter());
		longitudeT.setText(String.valueOf(wp.getLongitude()));
		longitudeT.setPreferredSize(new Dimension(300, 20));
		longitudeT.setName("Longitude");
		settingPanel.add(longitudeT);

		/* LeadTime*/
		JLabel leadTime = new JLabel("LeadTime", JLabel.LEFT);
		leadTime.setPreferredSize(new Dimension(118, 20));
		settingPanel.add(leadTime);
		leadTimeT = new JFormattedTextField(TextNumberFormatter.IntegerFormatter());
		leadTimeT.setText(String.valueOf(wp.getLeadTime()));
		leadTimeT.setPreferredSize(new Dimension(269, 20));
		leadTimeT.setName("LeadTime(s)");
		leadTimeT.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				String LeadTimeString = convertLeadTime(Double.parseDouble(leadTimeT.getText()));
				convertLeadTimeT.setText(LeadTimeString);
				convertLeadTimeTF.setText(LeadTimeString);
			}
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		
		leadTimeT.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String LeadTimeString = convertLeadTime(Double.parseDouble(leadTimeT.getText()));
				convertLeadTimeT.setText(LeadTimeString);
				convertLeadTimeTF.setText(LeadTimeString);
			}
		});
		settingPanel.add(leadTimeT);
		JLabel prefix = new JLabel("sec", JLabel.LEFT);
		prefix.setPreferredSize(new Dimension(30, 20));
		settingPanel.add(prefix);

		/* LeadTime String */
		JLabel convertLeadTime = new JLabel("ConvertLeadTime", JLabel.LEFT);
		convertLeadTime.setPreferredSize(new Dimension(120, 20));
		settingPanel.add(convertLeadTime);
		convertLeadTimeT = new JLabel(wp.getLeadTimeString(), JLabel.LEFT);
		convertLeadTimeT.setPreferredSize(new Dimension(300, 20));
		convertLeadTimeT.setName("LeadTimeString");
		settingPanel.add(convertLeadTimeT);
		convertLeadTimeTF = new JTextField(wp.getLeadTimeString());
		convertLeadTimeTF.setName("LeadTime");
		convertLeadTimeTF.setVisible(false);
		settingPanel.add(convertLeadTimeTF);
		
		/* Heading */
		JLabel heading = new JLabel("Heading", JLabel.LEFT);
		heading.setPreferredSize(new Dimension(120, 20));
		settingPanel.add(heading);
		headingT = new JFormattedTextField(TextNumberFormatter.DoubleFormatter());
		headingT.setText(String.valueOf(wp.getHeading()));
		headingT.setPreferredSize(new Dimension(300, 20));
		headingT.setName("Heading");
		settingPanel.add(headingT);

		/* Forward Draft */
		JLabel forwardDraft = new JLabel("ForwardDraft", JLabel.LEFT);
		forwardDraft.setPreferredSize(new Dimension(120, 20));
		settingPanel.add(forwardDraft);
		forwardDraftT = new JFormattedTextField(TextNumberFormatter.DoubleFormatter());
		forwardDraftT.setText(String.valueOf(wp.getForwardDraft()));
		forwardDraftT.setPreferredSize(new Dimension(300, 20));
		forwardDraftT.setName("FwdDraft");
		settingPanel.add(forwardDraftT);

		/* After Draft */
		JLabel afterDratf = new JLabel("AfterDratf", JLabel.LEFT);
		afterDratf.setPreferredSize(new Dimension(120, 20));
		settingPanel.add(afterDratf);
		afterDraftT = new JFormattedTextField(TextNumberFormatter.DoubleFormatter());
		afterDraftT.setText(String.valueOf(wp.getAfterDraft()));
		afterDraftT.setPreferredSize(new Dimension(300, 20));
		afterDraftT.setName("AftDraft");
		settingPanel.add(afterDraftT);

		/* Save */
		JButton save = new JButton("SAVE");
		save.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					WayPoint wp = new WayPoint();
					//wp.setNo(row);
					for(Component component: settingPanel.getComponents()) {
						if(component instanceof JTextField) {
							String cellName = component.getName();
							Object cellValue = (Object)((JTextField) component).getText();
							wp.setValue(cellName, cellValue);
						}
					}
					wayPointPanel.updateData(row, wp);
					close();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		settingPanel.add(save);

		wrap.add(settingPanel,BorderLayout.CENTER);
		add(wrap);
		setVisible(true);
		setResizable(true);
		return this;
	}
	
	public String convertLeadTime(double leadTime) {
		
		int hour = (int)leadTime / 3600;
		int min = (int)(leadTime % 3600) / 60;
		
		return hour + "h " + (min < 10 ? "0"+min:min) + "m";
	}

	public void close() {
		this.dispose();
	}
}