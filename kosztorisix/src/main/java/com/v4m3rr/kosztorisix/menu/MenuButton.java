package com.v4m3rr.kosztorisix.menu;

import java.awt.Font;

import javax.swing.JButton;

public class MenuButton extends JButton {

	public static final int WIDTH = 210;
	public static final int HEIGHT = 60;
	
	private static final Font font = new Font("Trebuchet MS",Font.PLAIN,21);
	
	public MenuButton(String text){
		super.setText(text);
		super.setSize(WIDTH, HEIGHT);
		super.setFont(font);
		super.setFocusable(false);
	}
	
}
