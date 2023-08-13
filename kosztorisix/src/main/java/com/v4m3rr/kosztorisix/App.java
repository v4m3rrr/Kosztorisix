package com.v4m3rr.kosztorisix;

import com.v4m3rr.kosztorisix.menu.MenuFrame;

public class App {
	public static void main(String[] args) {
		try {
			MenuFrame frame = new MenuFrame("Kosztorisix");
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
