package struts2.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;

public class pdfnew {
	private Document document = new Document();
	
	public pdfnew(String inhalt, String patient, String doctor) {
		this.pdferstellen(inhalt, patient, doctor);
	}

	public void pdferstellen(String text, String patient, String doctor) {
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("user.home") + File.separator + "Rezept"+patient+".pdf"));
			document.open();
			Font[] fonts ={
					new Font(),
					new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD,new BaseColor(0,0,0)),
					new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL)
			};
			
			Image image = Image.getInstance("http://www.gastroenterologia.ro/Imagini/sigla_CMT.png");
			image.scaleAbsolute(50,50);
			image.setAbsolutePosition(250f, 750f);
			document.add(image);
			Paragraph paragraph = new Paragraph();
			Paragraph paragraph2 = new Paragraph();
			Paragraph paragraph3 = new Paragraph();
			Paragraph paragraph4 = new Paragraph();
			paragraph.setFont(fonts[2]);
			LocalDate ldt = LocalDate.now();
			paragraph.add(ldt.toString());
			paragraph2.setFont(fonts[2]);			
			paragraph2.add("\n"+"Dr. "+ doctor);
			paragraph2.setFont(fonts[1]);			
			paragraph2.add("\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"Rezept");
			paragraph4.setFont(fonts[2]);
			paragraph4.add("\n"+"\n"+"\n"+"\n"+"\n"+"\n"+text);
			document.add(paragraph);
			document.add(paragraph2);
			document.add(paragraph3);
			document.add(paragraph4);
			document.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void deletePdf(String filepath)
	{
		try{
		File f = new File(filepath);
		f.delete();
		}
		catch(Exception e)
		{
			
		}
	}
	
//	public Document getPdf(){
//		try {
//			PdfWriter.getInstance(document, new FileOutputStream("Rezept.pdf"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//		return document;
//	}
	
}