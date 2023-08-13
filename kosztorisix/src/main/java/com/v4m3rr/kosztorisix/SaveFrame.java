package com.v4m3rr.kosztorisix;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.v4m3rr.kosztorisix.edit.EditFrame;
import com.v4m3rr.kosztorisix.menu.MenuButton;

public class SaveFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	@SuppressWarnings("rawtypes")
	private Vector<Vector> dataVector;
	private String filename;
	private String estimateTitle;
	private String vatStr;
	private EditFrame parent;
	private boolean createPdf = false;
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("rawtypes")
	public SaveFrame(EditFrame parent,String title,Vector<Vector> vector,String estimateTitle_, String vatStr_,String filename) {
		dataVector = vector;
		this.filename = filename;
		this.estimateTitle=estimateTitle_;
		this.vatStr=vatStr_;
		this.parent=parent;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(SaveFrame.class.getResource("/images/icon.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		addWindowListener(new WindowAdapter() {
			  @Override
			  public void windowClosing(WindowEvent we)
			  {     
			      SaveFrame.this.setVisible(false);
			      parent.setEnabled(true);
			      parent.toFront();
			      parent.requestFocus();
			  }
		});
		setTitle(title);
		setBounds(100, 100, 450, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		JButton saveButton = new MenuButton("Zapisz");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename = textField.getText().replaceAll("[^A-Za-z0-9()\\[\\] ]", "").replaceAll(" ", "_");
	            if (!filename.isEmpty()) {            	
	                String documentsPath = System.getProperty("user.home") + "\\Documents";
	                String kosztoFolderPath = documentsPath + "\\kosztorisix"+ "\\"+filename;
	                String filePath = kosztoFolderPath +"\\" + filename;
	                
	                File kosztoFolder = new File(kosztoFolderPath);
	                if (!kosztoFolder.exists()) {
	                    if (!kosztoFolder.mkdirs()) {
	                        System.err.println("Failed to create folder: " + kosztoFolderPath);
	                    }
	                }
	                
	                if (fileExists(filePath+".vec")||fileExists(filePath+".vars")) {
	                    String ObjButtons[] = {"Tak","Nie"};
	    			    int PromptResult = JOptionPane.showOptionDialog(SaveFrame.this, 
	    			        "Plik o podanej nazwie już istnieje czy chesz go nadpisać?", "Zapis do pliku", 
	    			        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
	    			        ObjButtons,ObjButtons[1]);
	    			    if(PromptResult==0)
	    			    {
	    			    	SaveFrame.this.filename=filename;
	    			    	saveDataToFile(dataVector,estimateTitle ,vatStr,filePath);    
	    			    }
	                }
	                else {
	                	SaveFrame.this.filename=filename;
	                	saveDataToFile(dataVector,estimateTitle ,vatStr,filePath);
	                }
	            } else {
	                JOptionPane.showMessageDialog(SaveFrame.this, "Pole nazwa pliku jest puste lub zawiera nieodpowienie znaki.", "Błąd", JOptionPane.ERROR_MESSAGE);
	            }
			}
		});
		saveButton.setSize(109, 33);
		saveButton.setLocation(315, 77);
		contentPane.add(saveButton);

		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Podaj nazwę pliku");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 162, 20);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField(20);
		textField.setFont(new Font("Trebuchet MS", Font.PLAIN, 21));
		textField.setBounds(10, 39, 414, 33);
		contentPane.add(textField);
		textField.setColumns(10);
	}
	
	public void showIfFilename(boolean createPdf) {
		this.createPdf=createPdf;
		if(!filename.isEmpty()) {			
			String documentsPath = System.getProperty("user.home") + "\\Documents";
            String kosztoFolderPath = documentsPath + "\\kosztorisix"+ "\\"+this.filename;
            String filePath = kosztoFolderPath +"\\" + this.filename;
            
            File kosztoFolder = new File(kosztoFolderPath);
            if (!kosztoFolder.exists()) {
                if (!kosztoFolder.mkdirs()) {
                    System.err.println("Failed to create folder: " + kosztoFolderPath);
                }
            }
            
            saveDataToFile(dataVector,estimateTitle ,vatStr,filePath);	
		}
		else {
			setVisible(true);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void saveDataToFile(Vector<Vector> vector, String estimateTitle,String vatStr,String filename) {	
        String filePath =  filename;
        
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath+".vec"))) {       
            outputStream.writeObject(dataVector);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        } 
        
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath+".vars"))) {
        	String[] strs = {estimateTitle,vatStr,parent.getSumaCena()};
            outputStream.writeObject(strs);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        } 
        JOptionPane.showMessageDialog(SaveFrame.this, "Plik został zapisany poprawnie", "Zapis", JOptionPane.INFORMATION_MESSAGE);
        SaveFrame.this.setVisible(false);
        parent.setEnabled(true);
        parent.toFront();
	    parent.requestFocus();
	    
	    if(createPdf) {
	    	parent.createPdf(this.filename);
	    	parent.dispose();
	    }
    }
    
    private static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
