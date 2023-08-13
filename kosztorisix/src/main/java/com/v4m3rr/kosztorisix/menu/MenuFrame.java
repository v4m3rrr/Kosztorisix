package com.v4m3rr.kosztorisix.menu;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MenuFrame extends JFrame {

	/**
	 * Variables
	 */
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MenuFrame(String title) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuFrame.class.getResource("/images/icon.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		addWindowListener(new WindowAdapter() {
			  @Override
			  public void windowClosing(WindowEvent we)
			  { 
			    String ObjButtons[] = {"Tak","Nie"};
			    int PromptResult = JOptionPane.showOptionDialog(MenuFrame.this, 
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
		setBounds(100, 100, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);

		MenuButton createEstimate = new MenuButton("Stwórz Kosztorys");
		createEstimate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuFrame.this.dispose();
				SetupFrame setupFrame = new SetupFrame(getTitle(),"","","",null,"");
				setupFrame.setVisible(true);
			}
		});
		contentPane.add(createEstimate);
		
		MenuButton createBoq = new MenuButton("Stwórz Przedmiar");
		createBoq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		contentPane.add(createBoq);
		
		MenuButton loadEstimate = new MenuButton("Wczytaj");
		loadEstimate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
                
                File startDirectory = new File(System.getProperty("user.home") + "/Documents/kosztorisix");
                fileChooser.setCurrentDirectory(startDirectory);       
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                
                int result = fileChooser.showOpenDialog(MenuFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFolder = fileChooser.getSelectedFile();
                    String lastSelectedFolderName = selectedFolder.getName();
                    String absolutePath =selectedFolder.getAbsolutePath();
                    
                    MenuFrame.this.dispose();
                    SetupLoad.Load(title, lastSelectedFolderName, absolutePath);
                    
                }
                else {
                	JOptionPane.showMessageDialog(MenuFrame.this, "Nie udało się otworzyć pliku. Upewnij się że został wybrany folder z poprawną nazwą.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
			}
		});
		contentPane.add(loadEstimate);
		
		CenterButtons(new JButton[] {createEstimate,createBoq,loadEstimate},10);
	}
	
	private void CenterButtons(JButton[] buttons, int margin) {
		int x = this.getWidth()/2 - buttons[0].getWidth()/2;	
		int y= this.getHeight()/2 -(buttons.length*buttons[0].getHeight()+(buttons.length-1)*margin)/2 -buttons[0].getHeight()/2;
		
		for(JButton button : buttons) {
			button.setLocation(x, y);
			y+=margin+buttons[0].getHeight();
		}
	}
}
