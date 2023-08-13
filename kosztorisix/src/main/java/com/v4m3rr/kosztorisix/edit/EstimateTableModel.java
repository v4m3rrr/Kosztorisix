package com.v4m3rr.kosztorisix.edit;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class EstimateTableModel extends DefaultTableModel {
	
	public EstimateTableModel(String[] columnNames) {
		super(0,columnNames.length);
		super.setColumnIdentifiers(columnNames);
	}
	public EstimateTableModel(@SuppressWarnings("rawtypes") Vector<Vector> dataVector,String[] columnNames) {
		super(0,columnNames.length);
		super.setColumnIdentifiers(columnNames);
		super.setDataVector(dataVector, columnIdentifiers);
	}
	
	@Override
	public boolean isCellEditable(int row, int column){  
        return false;
    }
}
