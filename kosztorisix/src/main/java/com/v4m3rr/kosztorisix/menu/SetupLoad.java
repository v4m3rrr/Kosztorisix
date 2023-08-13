package com.v4m3rr.kosztorisix.menu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

public class SetupLoad {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void Load(String title,String folderName,String filePath) {
		
		Vector<Vector> dataVector;		
		String[] vars;
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath+"\\"+folderName+".vec"))) {
			dataVector= (Vector<Vector>) inputStream.readObject();
			try (ObjectInputStream inputStream2 = new ObjectInputStream(new FileInputStream(filePath+"\\"+folderName+".vars"))) {
				vars = (String[]) inputStream2.readObject();
				
				SetupFrame frame = new SetupFrame(title,vars[0],vars[1],vars[2],dataVector,folderName);	
				frame.setVisible(true);
			}catch (IOException | ClassNotFoundException e) {
		        e.printStackTrace();
			}
		}catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
}
