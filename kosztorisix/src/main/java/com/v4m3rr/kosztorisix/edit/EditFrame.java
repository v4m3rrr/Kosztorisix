package com.v4m3rr.kosztorisix.edit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.v4m3rr.kosztorisix.PdfCreator;
import com.v4m3rr.kosztorisix.SaveFrame;
import com.v4m3rr.kosztorisix.menu.MenuButton;

public class EditFrame extends JFrame {

	private JPanel contentPane;
	private EstimateTable table;
	private JTextArea descText;
	private NumberTextField countText;
	private CurrencyTextFields priceText;
	private JLabel lblSumaCena;
	private SaveFrame saveFrame;
	
	private String estimateTitle;
	private String vatStr;
	

	private void UpdateSum(long old, long neww) {
		long value = CurrencyTextFields.deFormat(lblSumaCena.getText());
		value = value - old +neww;
		
		lblSumaCena.setText(CurrencyTextFields.toStringFormat(value));
	}
	
	/**
	 * Create the frame.
	 */
	public EditFrame(String title, String estimateTitle, String vatStr,String sumaStr,@SuppressWarnings("rawtypes") Vector<Vector> dataVector, String filename) {
		this.estimateTitle=estimateTitle;
		this.vatStr=vatStr;
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditFrame.class.getResource("/images/icon.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			  @Override
			  public void windowClosing(WindowEvent we)
			  { 
			    String ObjButtons[] = {"Tak","Nie"};
			    int PromptResult = JOptionPane.showOptionDialog(EditFrame.this, 
			        "Czy napewno chcesz wyjść z programu?", "Wyjście z programu", 
			        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
			        ObjButtons,ObjButtons[1]);
			    if(PromptResult==0)
			    {
			      System.exit(0);      
			    }
			  }
		});	
		setBounds(100, 100, 1280, 720);
		setTitle(title);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton saveButton = new MenuButton("Zapisz");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditFrame.this.setEnabled(false);
				saveFrame.showIfFilename(false);
			}
		});
		saveButton.setLocation(10, 610);
		contentPane.add(saveButton);
		
		JButton nextButton = new MenuButton("Dalej");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditFrame.this.setEnabled(false);
				saveFrame.showIfFilename(true);
			}
		});
		nextButton.setLocation(1044, 610);
		contentPane.add(nextButton);
		
		JScrollPane scrollPaneTable = new JScrollPane();
		scrollPaneTable.setBounds(10, 194, 1244, 406);
		contentPane.add(scrollPaneTable);
		
		String[] columnNames = {"Opis","Ilość","Cena jedn.","Cena cał."};
		if(dataVector!=null) {
			table = new EstimateTable(new EstimateTableModel(dataVector,columnNames));
		}else {
			table = new EstimateTable(new EstimateTableModel(columnNames));
		}
		scrollPaneTable.setViewportView(table);
		table.setSize(scrollPaneTable.getSize());
		table.setJTableColumnsWidth(50,10,20,20);
	    table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	int viewRow = EditFrame.this.table.getSelectedRow();

	            if (!event.getValueIsAdjusting() && viewRow != -1) {
	                int modelRow =  EditFrame.this.table.convertRowIndexToModel(viewRow);
	                
	                descText.setText((String) EditFrame.this.table.getModel().getValueAt(modelRow, 0));
	                countText.setText((String) EditFrame.this.table.getModel().getValueAt(modelRow, 1));
	                
	                long priceValue = CurrencyTextFields.deFormat((String) EditFrame.this.table.getModel().getValueAt(modelRow, 2));
	                priceText.setZlText(CurrencyTextFields.formatZl(Long.toString(priceValue/100)).replace(" ", "").replace(".", ""));
	                priceText.setGrText(CurrencyTextFields.formatGr(Long.toString(priceValue%100)).replace(" ", "").replace(".", ""));
	            }
	        }
	    });
		
		JScrollPane scrollPaneDesc = new JScrollPane();
		scrollPaneDesc.setBounds(10, 40, 620, 60);
		contentPane.add(scrollPaneDesc);
		
		descText = new JTextArea();
		descText.setFont(new Font("Dialog", Font.PLAIN, 18));
		descText.setLineWrap(true);
		descText.setWrapStyleWord(true);
		scrollPaneDesc.setViewportView(descText);
		
		JLabel descLabel = new JLabel("Podaj opis");
		descLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		descLabel.setBounds(10, 11, 97, 27);
		contentPane.add(descLabel);
		
		JLabel countLabel = new JLabel("Podaj ilość");
		countLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		countLabel.setBounds(519, 105, 97, 27);
		contentPane.add(countLabel);
		
		countText = new NumberTextField();
		countText.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
		countText.setText("1");
		countText.setBounds(519, 133, 64, 50);
		countText.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(countText);
		
		JButton btnCountUp = new JButton("▲");
		btnCountUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumberTextField field = EditFrame.this.countText;
				field.setValue(field.getValue()+1);
			}
		});
		btnCountUp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCountUp.setBounds(582, 133, 48, 25);
		contentPane.add(btnCountUp);
		
		JButton btnCountDown = new JButton("▼");
		btnCountDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumberTextField field = EditFrame.this.countText;
				field.setValue(field.getValue()-1);
			}
		});
		btnCountDown.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCountDown.setBounds(582, 157, 48, 25);
		contentPane.add(btnCountDown);
		
		JLabel priceLabel = new JLabel("Podaj kwotę jednostkową");
		priceLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		priceLabel.setBounds(10, 105, 224, 27);
		contentPane.add(priceLabel);
		
		JLabel priceZlLabel = new JLabel("ZŁ");
		priceZlLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		priceZlLabel.setBounds(377, 133, 35, 50);
		contentPane.add(priceZlLabel);
		
		JLabel lblGr = new JLabel("GR");
		lblGr.setFont(new Font("Dialog", Font.BOLD, 24));
		lblGr.setBounds(467, 133, 42, 50);
		contentPane.add(lblGr);
		
		priceText = new CurrencyTextFields(new NumberTextField(),new NumberTextField(2),contentPane);
		
		MenuButton btnAdd = new MenuButton("Dodaj");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!descText.getText().isEmpty() && !countText.getText().isEmpty() && !priceText.isEmptyZl() && !priceText.isEmptyGr()) {
					long sumPrice = priceText.getPriceValue()*Integer.parseInt(countText.getText());
						
					
					String text = descText.getText();
			        String[] sentences = text.split("\\.\\s*");

			        StringBuilder result = new StringBuilder();

			        for (String sentence : sentences) {
			            if (!sentence.isEmpty()) {
			                String firstLetter = sentence.substring(0, 1);
			                String restOfSentence = sentence.substring(1);
			                result.append(firstLetter.toUpperCase()).append(restOfSentence).append(". ");
			            }
			        }
			        
			        descText.setText(result.toString().trim());
					
					String[] data = {descText.getText(),countText.getText(),priceText.getPriceText(),CurrencyTextFields.toStringFormat(sumPrice)};
					table.AddRow(data);
					
					UpdateSum((long)0,sumPrice);
					
					descText.setText("");
					countText.setText("1");
					priceText.clear();
				}
				else {
					String ObjButtons[] = {"Ok"};
				    JOptionPane.showOptionDialog(EditFrame.this, 
				        "Musisz wypełnić wszytkie pola", "Dodaj", 
				        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, 
				        ObjButtons,null);
				}
			}
		});
		btnAdd.setBounds(660, 40, 150, 40);
		contentPane.add(btnAdd);
		
		MenuButton btnEdit = new MenuButton("Edytuj");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(table.getSelectedRow() != -1) {
					if(!descText.getText().isEmpty() && !countText.getText().isEmpty() && !priceText.isEmptyZl() && !priceText.isEmptyGr()) {
						long sumPrice = priceText.getPriceValue()*Integer.parseInt(countText.getText());
						
						String[] data = {descText.getText(),countText.getText(),priceText.getPriceText(),CurrencyTextFields.toStringFormat(sumPrice)};
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						String priceFulText = (String) table.getValueAt(row, 3);
						long old = CurrencyTextFields.deFormat(priceFulText);
						
						model.setValueAt(data[0], row, 0);
						model.setValueAt(data[1], row, 1);
						model.setValueAt(data[2], row, 2);
						model.setValueAt(data[3], row, 3);
						
						UpdateSum(old,sumPrice);
						
						descText.setText("");
						countText.setText("1");
						priceText.clear();
					}
					else {
						String ObjButtons[] = {"Ok",};
					    JOptionPane.showOptionDialog(EditFrame.this, 
					        "Musisz wypełnić wszytkie pola", "Edytuj", 
					        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, 
					        ObjButtons,null);
					}
				}
				else {
					String ObjButtons[] = {"Ok",};
				    JOptionPane.showOptionDialog(EditFrame.this, 
				        "Musisz zaznaczyć rekord do edycji", "Edytuj", 
				        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, 
				        ObjButtons,null);
				}
				
			}
		});
		btnEdit.setBounds(660, 91, 150, 40);
		contentPane.add(btnEdit);
		
		MenuButton btnDelete = new MenuButton("Usuń");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				int row = table.getSelectedRow();
				if(table.getSelectedRow() != -1) {
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					String priceFulText = (String) table.getValueAt(row, 3);
					long old = CurrencyTextFields.deFormat(priceFulText);
					
					model.removeRow(table.getSelectedRow());
				    
					UpdateSum(old,0);
					
					descText.setText("");
					countText.setText("1");
					priceText.clear();
				}
				else {
					String ObjButtons[] = {"Ok",};
				    JOptionPane.showOptionDialog(EditFrame.this, 
				        "Musisz zaznaczyć rekord do usunięcia", "Usuń", 
				        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, 
				        ObjButtons,null);
				}
			}
		});
		btnDelete.setText("Usuń");
		btnDelete.setBounds(660, 143, 150, 40);
		contentPane.add(btnDelete);
		
		JLabel lblSuma = new JLabel("Suma");
		lblSuma.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSuma.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		lblSuma.setBounds(1105, 55, 137, 40);
		contentPane.add(lblSuma);
		
		lblSumaCena = new JLabel("0 zł");
		lblSumaCena.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSumaCena.setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
		lblSumaCena.setBounds(843, 92, 399, 40);
		contentPane.add(lblSumaCena);
		
		MenuButton mnbtnZapiszJako = new MenuButton("Zapisz");
		mnbtnZapiszJako.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditFrame.this.setEnabled(false);
				saveFrame.setVisible(true);
			}
		});
		mnbtnZapiszJako.setText("Zapisz jako");
		mnbtnZapiszJako.setBounds(230, 610, 210, 60);
		contentPane.add(mnbtnZapiszJako);
		
		if(!sumaStr.isEmpty()) {
			lblSumaCena.setText(sumaStr);
		}
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		saveFrame = new SaveFrame(EditFrame.this,EditFrame.this.getTitle(),model.getDataVector(),estimateTitle,vatStr,filename);
	}
	
	public String getSumaCena() {
		return  lblSumaCena.getText();
	}
	
	public void createPdf(String filename) {
		PdfCreator.CreatePdf(estimateTitle, vatStr, table, lblSumaCena.getText(), filename);
	}
}
