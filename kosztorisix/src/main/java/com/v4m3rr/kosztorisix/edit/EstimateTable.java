package com.v4m3rr.kosztorisix.edit;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class EstimateTable extends JTable {
	
	private static final Font font =new Font("Trebuchet MS",Font.PLAIN,16);
	
	public EstimateTable(DefaultTableModel model) {
		super(model);
		super.getColumnModel().getColumn(0).setCellRenderer(new WordWrapCellRenderer(font,5));
		super.getTableHeader().setFont(new Font("Trebuchet MS",Font.PLAIN,21));
		super.setFont(new Font("Trebuchet MS",Font.PLAIN,21));
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		super.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		super.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		super.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		
		
	}
	
	public void AddRow(String[] rowData) {
		DefaultTableModel model = (DefaultTableModel) super.getModel();
		model.addRow(rowData);
	}
	
	public void setJTableColumnsWidth(
	        double... percentages) {
		int tablePreferredWidth = super.getWidth();
	    double total = 0;
	    for (int i = 0; i < super.getColumnModel().getColumnCount(); i++) {
	        total += percentages[i];
	    }
	    
	    for (int i = 0; i < super.getColumnModel().getColumnCount(); i++) {
	        TableColumn column = super.getColumnModel().getColumn(i);
	        column.setPreferredWidth((int)
	                (tablePreferredWidth * (percentages[i] / total)));
	    }
	}
}
