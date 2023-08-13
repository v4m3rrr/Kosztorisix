package com.v4m3rr.kosztorisix.edit;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class NumberTextField extends JTextField {

	private int maxDigitNum = 19;
	
	public NumberTextField() {		
		super.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c= e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) ||(c == KeyEvent.VK_DELETE))) {
					
					getToolkit().beep();
					e.consume();
				}
				else if(maxDigitNum<=NumberTextField.this.getText().length()) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
	}
	
	public NumberTextField(int maxDigitNum_) {	
		maxDigitNum=maxDigitNum_;
		super.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c= e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) ||(c == KeyEvent.VK_DELETE))) {
					
					getToolkit().beep();
					e.consume();
				}
				else if(maxDigitNum<=NumberTextField.this.getText().length()) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
	}
	
	public void setValue(int count) {
		if(count>=0) {
			this.setText(Integer.toString(count));
		}		
	}
	
	public int getValue() {
		return Integer.parseInt(this.getText());
	}
}
