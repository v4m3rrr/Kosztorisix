package com.v4m3rr.kosztorisix.menu;


import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.v4m3rr.kosztorisix.edit.EditFrame;

public class SetupFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JComboBox<String> comboBox;
	
	private String sumaStr;
	/**
	 * Create the frame.
	 */
	public SetupFrame(String title,String estimateTitle, String vatStr, String sumaStr_,@SuppressWarnings("rawtypes") Vector<Vector> dataVector,String filename) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SetupFrame.class.getResource("/images/icon.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		addWindowListener(new WindowAdapter() {
			  @Override
			  public void windowClosing(WindowEvent we)
			  { 
			    String ObjButtons[] = {"Tak","Nie"};
			    int PromptResult = JOptionPane.showOptionDialog(SetupFrame.this, 
			        "Czy napewno chcesz wyjść z programu?", "Wyjście z programu", 
			        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
			        ObjButtons,ObjButtons[1]);
			    if(PromptResult==0)
			    {
			      System.exit(0);      
			    }
			  }
		});
		setTitle(title);
		setBounds(100, 100, 451, 204);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		
		MenuButton createEstimate = new MenuButton("Dalej");
		createEstimate.setBounds(286, 119, 138, 33);
		createEstimate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().isEmpty() && !comboBox.getSelectedItem().toString().isEmpty()) {
					SetupFrame.this.dispose();
					EditFrame editFrame = new EditFrame(SetupFrame.this.getTitle(),textField.getText(),comboBox.getSelectedItem().toString(),sumaStr,dataVector,filename);
					editFrame.setVisible(true);
				}
				else {
					String ObjButtons[] = {"Ok",};
				    JOptionPane.showOptionDialog(SetupFrame.this, 
				        "Musisz wypełnić wszytkie pola", "Dalej", 
				        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, 
				        ObjButtons,null);
				}
			}
		});
		contentPane.setLayout(null);
		contentPane.add(createEstimate);

		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		textField.setBounds(10, 42, 414, 33);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Podaj tytuł kosztorysu");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 21));
		lblNewLabel.setBounds(10, 11, 212, 33);
		contentPane.add(lblNewLabel);
		
		JLabel lblPodajStawkVat = new JLabel("Podaj stawkę VAT");
		lblPodajStawkVat.setFont(new Font("Trebuchet MS", Font.PLAIN, 21));
		lblPodajStawkVat.setBounds(10, 75, 212, 33);
		contentPane.add(lblPodajStawkVat);
		
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
		comboBox.setBounds(10, 105, 91, 47);
		comboBox.addItem("");
		comboBox.addItem("0%");
		comboBox.addItem("8%");
		comboBox.addItem("23%");
		contentPane.add(comboBox);
		
		if(!estimateTitle.isEmpty()) {
			textField.setText(estimateTitle);
		}
		
		if(!vatStr.isEmpty()) {
			comboBox.setSelectedItem(vatStr);
		}
		
		if(!sumaStr_.isEmpty()) {
			sumaStr = sumaStr_;
		}
		else {
			sumaStr="";
		}
	}
}
