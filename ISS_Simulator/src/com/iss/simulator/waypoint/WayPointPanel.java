package com.iss.simulator.waypoint;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import com.iss.simulator.models.WayPoint;
import com.iss.simulator.models.WayPointModel;
import com.iss.simulator.util.SimulatorConfig;

public class WayPointPanel extends JPanel {
	
	SimulatorConfig sc;
	
	JFrame frame;
	JTable m_table;
	WayPointPanel wayPoint;

	public WayPointPanel(JFrame frame) {
		this.frame = frame;
		wayPoint = this;
		setLayout(new BorderLayout());

		WayPointModel m_data = new WayPointModel();
		m_table = new JTable();
		m_table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		m_table.setAutoCreateColumnsFromModel(false);
		m_table.setModel(m_data);
		m_table.setRowHeight(30);
		m_table.setName("WayPoints");

		for (int k = 0; k < WayPointModel.m_columns.length; k++) {
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setHorizontalAlignment(WayPointModel.m_columns[k].getAlignment());
			javax.swing.table.TableColumn column = new javax.swing.table.TableColumn(k,
					WayPointModel.m_columns[k].getWidth(), renderer, null);
			m_table.addColumn(column);
		}
		JScrollPane spane = new JScrollPane(m_table);
		JTableHeader header = m_table.getTableHeader();
		header.setUpdateTableInRealTime(true);
		header.setReorderingAllowed(true);

		add(spane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		ButtonAction action = new ButtonAction();

		JButton addbt = new JButton("Add");
		addbt.addActionListener(action);
		buttonPanel.add(addbt);

		JButton updatebt = new JButton("Update");
		updatebt.addActionListener(action);
		buttonPanel.add(updatebt);

		JButton deletebt = new JButton("Delete");
		deletebt.addActionListener(action);
		buttonPanel.add(deletebt);

		JButton importBt = new JButton("Import");
		importBt.addActionListener(action);
		buttonPanel.add(importBt);

		JButton exportBt = new JButton("Export");
		exportBt.addActionListener(action);
		buttonPanel.add(exportBt);

		add(buttonPanel, BorderLayout.NORTH);
	}
	
	public void setConfig(SimulatorConfig sc) {
		this.sc = sc;
	}

	public void addData(List<WayPoint> waypoint) {
		WayPointModel m_data = new WayPointModel();
		for (WayPoint wp : waypoint)
			m_data.addData(wp);
		m_table.setModel(m_data);
	}
	
	public void addData(WayPoint waypoint) {
		WayPointModel m_data = (WayPointModel) m_table.getModel();
		m_data.addData(waypoint);
		m_table.setModel(m_data);
		m_table.updateUI();
	}
	
	public void updateData(int row, WayPoint waypoint) {
		WayPointModel m_data = (WayPointModel) m_table.getModel();
		m_data.updateRow(row, waypoint);
		m_table.setModel(m_data);
		m_table.updateUI();
	}

	class ButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton obj = (JButton) e.getSource();
			String command = obj.getText();

			if ("Add".equals(command)) {
				
				AddWayPointDialog spenel = new AddWayPointDialog(frame, "Add Way Point");
				spenel.setRowNumber(m_table.getRowCount());
				spenel.setWayPointPanel(wayPoint);
				spenel.createDialog();
				
			} else if ("Update".equals(command)) {
				
				if(m_table.getSelectedRowCount() == 0) return;
				
				UpdateWayPointDialog spenel = new UpdateWayPointDialog(frame, "Update Way Point");
				int columnCount = WayPointModel.m_columns.length;
				WayPoint wp = new WayPoint();
				for(int i=0; i<columnCount; i++) {
					Object value = m_table.getValueAt(m_table.getSelectedRow(), i);
					String title = WayPointModel.m_columns[i].getTitle();
					wp.setValue(title, value);
				}
				spenel.setWayPointPanel(wayPoint);
				spenel.setRowNumber(m_table.getSelectedRow());
				spenel.setWayPoint(wp);
				spenel.createDialog();
				
			} else if ("Import".equals(command)) {
				
				ImportWayPointDialog spenel = new ImportWayPointDialog(frame, "Import Way Point");
				spenel.setWayPointPanel(wayPoint);
				spenel.createDialog();
				
			} else if ("Delete".equals(command)) {
				if(m_table.getSelectedRowCount() == 0) return;
				
				int[] rows = m_table.getSelectedRows();
				WayPointModel m_data = (WayPointModel) m_table.getModel();
				for(int i=(rows.length-1); i>=0; i--) {
					System.out.println(rows[i]);
					m_data.removeRow(m_table.getSelectedRow());
				}
				m_table.setModel(m_data);
				m_table.updateUI();
				
			} else if ("Export".equals(command)) {
				ExportWayPointDialog spenel = new ExportWayPointDialog(frame, "Export Way Point");
				spenel.setTable(m_table);
				spenel.createDialog();
			}
		}
	}
};

