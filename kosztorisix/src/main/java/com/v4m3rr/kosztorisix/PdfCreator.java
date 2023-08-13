package com.v4m3rr.kosztorisix;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.v4m3rr.kosztorisix.edit.CurrencyTextFields;

import javax.swing.JTable;

public class PdfCreator {

	static public void CreatePdf(String estimateTitle,String vatStr, JTable jt, String sumaStr,String filename) {
		try {
			int count=jt.getRowCount();
			Document document=new Document();
			String documentsPath = System.getProperty("user.home") + "\\Documents";
            String kosztoFolderPath = documentsPath + "\\kosztorisix";
            filename = kosztoFolderPath + "\\" + filename+"\\" + filename;
			PdfWriter.getInstance(document,new FileOutputStream(filename+".pdf"));
			document.open();
			
			Font font = new Font(BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED), 12);
	        Font fontHeader = new Font(BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1250, BaseFont.EMBEDDED), 12);
			Font font2 = new Font(BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED), 15);
			Font font3 = new Font(BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED), 17);
		 	
			//To uppercase first letter
				
			Paragraph paragraph = new Paragraph(estimateTitle.substring(0, 1).toUpperCase() + estimateTitle.substring(1),font3);
	        paragraph.setAlignment(Element.ALIGN_CENTER);
	        paragraph.setSpacingAfter(8f);
	        
	        document.add(paragraph);
			
			PdfPTable tab=new PdfPTable(4);
			tab.setWidthPercentage(100f);
			float[] widths = {0.7f,0.1f,0.2f,0.2f};
			tab.setWidths(widths);
			
			String[] nagloweczki = {"Opis","Ilość","Kwota jedn.","Kwota cał."};
			for(int i=0;i<4;i++) {
				PdfPCell celus = new PdfPCell(new Paragraph(nagloweczki[i], fontHeader));
				celus.setPaddingBottom(5);
				tab.addCell(celus);
			}
	
			
			for(int i=0;i<count;i++){
				for(int j=0;j<4;j++) {
					PdfPCell cell = new PdfPCell(new Paragraph(GetData(jt, i, j).toString(), font));
					cell.setPaddingBottom(5);
					tab.addCell(cell);
				}
			}			
			document.add(tab);
			
			
			Paragraph p = new Paragraph("RAZEM: "+sumaStr,font2);
			p.setAlignment(Element.ALIGN_RIGHT);
	        p.setSpacingBefore(3f);
			document.add(p);
			
			long totalTotal = CurrencyTextFields.deFormat(sumaStr);
			double vatPrice = Double.parseDouble(vatStr.substring(0,vatStr.length()-1))/100.0; 
			long vatTotal = (long)Math.round(totalTotal*vatPrice);		
					
			if(vatStr!="0%") {
				Paragraph p1 = new Paragraph("Podatek VAT "+vatStr+": "+CurrencyTextFields.toStringFormat(vatTotal),font2);
				p1.setAlignment(Element.ALIGN_RIGHT);
				document.add(p1);
			}
				
			long razemTotal = totalTotal+vatTotal;
			
			Paragraph p2 = new Paragraph("Ogółem: "+CurrencyTextFields.toStringFormat(razemTotal),font2);
			p2.setAlignment(Element.ALIGN_RIGHT);
			document.add(p2);
			
			document.close();				
			
			File file = new File(filename+".pdf");
			Desktop.getDesktop().open(file);
		}
		catch(Exception ee){
			System.out.print(ee.getMessage());
		}
	}
	
	public static Object GetData(JTable table, int row_index, int col_index){
		return table.getModel().getValueAt(row_index, col_index);
	}
}
