package com.v4m3rr.kosztorisix.edit;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CurrencyTextFields extends JTextField {

	//Zl format:### ### zł
	
	private NumberTextField zl;
	private NumberTextField gr;
	
	public CurrencyTextFields(NumberTextField zl, NumberTextField gr, JPanel contentPane) {
		zl.setFont(new Font("Trebuchet MS", Font.PLAIN, 21));
		zl.setLocation(10, 133);
		zl.setSize(357, 50);
		zl.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(zl);
		
		gr.setText("00");
		gr.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
		gr.setBounds(422, 133, 35, 50);
		gr.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(gr);
		
		this.zl=zl;
		this.gr=gr;
	}
	
	public String getZlText() {
		return formatZl(zl.getText());
	}
	
	public String getGrText() {
		return formatGr(gr.getText());
	}
	
	public long getZlValue() {
		String text =zl.getText().replace(" ", "").replace(".", "");
		return Long.parseLong(text);
	}
	
	public long getGrValue() {
		String text =gr.getText().replace(" ", "").replace(".", "");
		return Long.parseLong(text);
	}
	
	public String getPriceText() {
		return new String(getZlText()+","+getGrText()+" zł");
	}
	
	public long getPriceValue() {
		return getZlValue()*100 + getGrValue();
	}
	
	public void setZl(long zl) {
		this.zl.setText(Long.toString(zl));
	}
	
	public void setGr(long gr) {
		this.gr.setText(Long.toString(gr));
	}
	
	public void setZlText(String text) {
		this.zl.setText(text);
	}
	
	public void setGrText(String text) {
		this.gr.setText(text);
	}
	
	public boolean isEmptyZl() {
		return zl.getText().isEmpty();
	}
	
	public boolean isEmptyGr() {
		return gr.getText().isEmpty();
	}
	
	public void clear() {
		zl.setText("");
		gr.setText("00");
	}
	
	public static String toStringFormat(long price) {
		long zlVal=price/100;
		long grVal = price%100;
		String sumPriceText = new String(CurrencyTextFields.formatZl(Long.toString(zlVal)) + ","
				+CurrencyTextFields.formatGr(Long.toString(grVal))+" zł");
		
		return sumPriceText;
	}
	
	static public String formatZl(String zlIn) {
		String zlText=zlIn.replace(" ", "").replace(".", "");
		zlText = zlText.replaceFirst("^0+(?!$)", "");
		
		//12 341 234
		for(int i=3;i<zlText.length();i+=4){
			zlText = new StringBuffer(zlText.trim()).insert(zlText.length()-i, " ").toString();
		}
		
		return zlText;
	}
	
	static public String formatGr(String grIn) {
		String grText = grIn.replace(" ", "").replace(".", "");
		grText = grText.replaceFirst("^0+(?!$)", "");
		
		if(grText.length()==1) {
			grText = "0"+grText;
		}
		else if(grText.length()==0) {
			grText = "00";
		}
		
		return grText;
	}
	
	static public long deFormat(String str_) {
		String str = str_.replace(" ", "");
		str = str.substring(0,str.length()-2);
		str = str.replace(",", "");
		str = str.replace(".", "");
		
		long value = Long.parseLong(str);
		
		return value;
	}
}


















