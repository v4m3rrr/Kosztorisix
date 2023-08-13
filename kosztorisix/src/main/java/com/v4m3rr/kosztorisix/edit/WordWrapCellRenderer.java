package com.v4m3rr.kosztorisix.edit;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
	private int margin;
	
    WordWrapCellRenderer(Font font, int margin) {
    	this.margin=margin;
        setLineWrap(true);
        setWrapStyleWord(true);
        setFont(font);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);

        if (table.getRowHeight(row) != getPreferredSize().height) {
            table.setRowHeight(row, getPreferredSize().height);
        }
        setBorder(BorderFactory.createEmptyBorder(0, 0, margin, margin));
        
        
        setText(value == null ? "" : value.toString());

        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        }
        else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        
        return this;
    }
}
