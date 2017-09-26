package com.prescription.doctorprescription.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.prescription.doctorprescription.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by medisys on 8/17/2017.
 */

public class PrescriptionPdf {

    private final String TAG = "Prescription Pdf";

   // PdfContentByte contentByte;


   /* private void barcodeGenerate(){
        try{
            Document document = new Document(new Rectangle(PageSize.A4));
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("barcode128.pdf"));
            document.open();

            //Barcode 128
            document.add(new Paragraph("Barcode 128"));
            Barcode128 code128 = new Barcode128();
            code128.setGenerateChecksum(true);
            code128.setCode("0123456789");

            //Add Barcode to PDF document
            document.add(code128.createImageWithBarcode(writer.getDirectContent(), null, null));
            document.close();
        }catch (Exception fe){
            Log.d("PrescriptionPdf", fe.toString());
        }

    }*/




    /*public Image createBarcode128(String myText) {
        Barcode128 code128 = new Barcode128();
        code128.setCode(myText);
        Image myBarCodeImage128 = code128.createImageWithBarcode(contentByte,
                BaseColor.BLACK, BaseColor.BLACK);
        return myBarCodeImage128;
    }*/

    public  void createPDF(Document doc, String reqNo){

        try {

            /*Font boldfont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);*/

            PdfPTable table1Head = new PdfPTable(3);
            table1Head.getDefaultCell().setBorder(0);

            PdfPCell cellOne = new PdfPCell(new Phrase(Font.BOLD, "DR. Md Abu Bakar Siddique"));
            PdfPCell cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell cellThree = new PdfPCell(new Phrase(Font.BOLD, "Popular Diagonostic Center"));

            cellOne.setBorder(Rectangle.NO_BORDER);
            cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table1Head.addCell(cellOne);

            cellTwo.setBorder(Rectangle.NO_BORDER);
            cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
            table1Head.addCell(cellTwo);

            cellThree.setBorder(Rectangle.NO_BORDER);
            cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table1Head.addCell(cellThree);

            float[] columnWidths = {2f, 1f, 2f};
            table1Head.setWidths(columnWidths);
            doc.add(table1Head);


            PdfPTable table2Head = new PdfPTable(3);
            table2Head.getDefaultCell().setBorder(0);

            PdfPCell t2cellOne = new PdfPCell(new Phrase("MBBS, FCPS"));
            PdfPCell t2cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t2cellThree = new PdfPCell(new Phrase("6 P.M - 10 P.M"));

            t2cellOne.setBorder(Rectangle.NO_BORDER);
            t2cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table2Head.addCell(t2cellOne);

            t2cellTwo.setBorder(Rectangle.NO_BORDER);
            t2cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
            table2Head.addCell(t2cellTwo);

            t2cellThree.setBorder(Rectangle.NO_BORDER);
            t2cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table2Head.addCell(t2cellThree);

            float[] t2columnWidths = {2f, 1f, 2f};
            table2Head.setWidths(t2columnWidths);
            doc.add(table2Head);


            PdfPTable table3Head = new PdfPTable(3);
            table3Head.getDefaultCell().setBorder(0);

            PdfPCell t3cellOne = new PdfPCell(new Phrase("Gyneecologycal Surgeon"));
            PdfPCell t3cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t3cellThree = new PdfPCell(new Phrase("01735487522"));

            t3cellOne.setBorder(Rectangle.NO_BORDER);
            t3cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table3Head.addCell(t3cellOne);

            t3cellTwo.setBorder(Rectangle.NO_BORDER);
            t3cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
            table3Head.addCell(t3cellTwo);

            t3cellThree.setBorder(Rectangle.NO_BORDER);
            t3cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table3Head.addCell(t3cellThree);

            float[] t3columnWidths = {2f, 1f, 2f};
            table3Head.setWidths(t3columnWidths);
            doc.add(table3Head);


            PdfPTable table4Head = new PdfPTable(3);
            table4Head.getDefaultCell().setBorder(0);

            PdfPCell t4cellOne = new PdfPCell(new Phrase("Cheif Consultant(Gynee)"));
            PdfPCell t4cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t4cellThree = new PdfPCell(new Phrase("01735485478"));

            t4cellOne.setBorder(Rectangle.NO_BORDER);
            t4cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table4Head.addCell(t4cellOne);

            t4cellTwo.setBorder(Rectangle.NO_BORDER);
            t4cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
            table4Head.addCell(t4cellTwo);

            t4cellThree.setBorder(Rectangle.NO_BORDER);
            t4cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table4Head.addCell(t4cellThree);

            float[] t4columnWidths = {2f, 1f, 2f};
            table4Head.setWidths(t4columnWidths);
            doc.add(table4Head);


            PdfPTable table5Head = new PdfPTable(3);
            table5Head.getDefaultCell().setBorder(0);

            PdfPCell t5cellOne = new PdfPCell(new Phrase("Dhaka Medical College"));
            PdfPCell t5cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t5cellThree = new PdfPCell(new Phrase("Mirpur-6, Dhaka"));

            t5cellOne.setBorder(Rectangle.NO_BORDER);
            t5cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table5Head.addCell(t5cellOne);

            t5cellTwo.setBorder(Rectangle.NO_BORDER);
            t5cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
            table5Head.addCell(t5cellTwo);

            t5cellThree.setBorder(Rectangle.NO_BORDER);
            t5cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table5Head.addCell(t5cellThree);

            float[] t5columnWidths = {2f, 1f, 2f};
            table5Head.setWidths(t5columnWidths);
            doc.add(table5Head);




            Paragraph paragraph = new Paragraph("--------------------------------------------------------------------------------------------------------------");
            Font paragrFont = new Font(Font.FontFamily.COURIER);
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            paragraph.setFont(paragrFont);
            doc.add(paragraph);

            /*PdfPTable tableBarcode = new PdfPTable(1);
            //tableBarcode.setWidthPercentage(15);
            //tableBarcode.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            //tableBarcode.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            //tableBarcode.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableBarcode.getDefaultCell().setFixedHeight(10);

            PdfPCell cellOneBarcode = new PdfPCell(new Phrase((new Chunk(createBarcode128("25468"), 0, 0))));
            //PdfPCell cellTwoBarcode = new PdfPCell(new Phrase("CODE 128"));

            cellOneBarcode.setBorder(Rectangle.NO_BORDER);
            cellOneBarcode.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            tableBarcode.addCell(cellOneBarcode);
            *//*cellTwoBarcode.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            tableBarcode.addCell(cellTwoBarcode);*//*

            float[] columnWidthsBarcode = {1f};
            tableBarcode.setWidths(columnWidthsBarcode);
            doc.add(tableBarcode);*/


            PdfPTable table3 = new PdfPTable(4);
            table3.getDefaultCell().setBorder(0);

            PdfPCell t3CellOne = new PdfPCell(new Phrase("Pat ID :"));
            PdfPCell t3CellTwo = new PdfPCell(new Phrase("25468"));
            PdfPCell t3CellThree = new PdfPCell(new Phrase("Date : "));
            PdfPCell t3CellFour = new PdfPCell(new Phrase("01-03-2017"));

            t3CellOne.setBorder(Rectangle.NO_BORDER);
            t3CellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table3.addCell(t3CellOne);

            t3CellTwo.setBorder(Rectangle.NO_BORDER);
            t3CellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table3.addCell(t3CellTwo);

            t3CellThree.setBorder(Rectangle.NO_BORDER);
            t3CellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table3.addCell(t3CellThree);

            t3CellFour.setBorder(Rectangle.NO_BORDER);
            t3CellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table3.addCell(t3CellFour);

            float[] columnWidthsT3 = {1f, 1.5f, 1f, 1.5f};
            table3.setWidths(columnWidthsT3);
            doc.add(table3);

            PdfPTable table5 = new PdfPTable(6);
            table5.getDefaultCell().setBorder(0);

            PdfPCell t5CellOne = new PdfPCell(new Phrase(""));
            PdfPCell t5CellTwo = new PdfPCell(new Phrase("Md. Chabed Alam"));
            PdfPCell t5CellThree = new PdfPCell(new Phrase("Age : "));
            PdfPCell t5CellFour = new PdfPCell(new Phrase("31"));
            PdfPCell t5CellFive = new PdfPCell(new Phrase("Sex : "));
            PdfPCell t5CellSix = new PdfPCell(new Phrase("Male"));

            t5CellOne.setBorder(Rectangle.NO_BORDER);
            t5CellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table5.addCell(t5CellOne);

            t5CellTwo.setBorder(Rectangle.NO_BORDER);
            t5CellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table5.addCell(t5CellTwo);

            t5CellThree.setBorder(Rectangle.NO_BORDER);
            t5CellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table5.addCell(t5CellThree);

            t5CellFour.setBorder(Rectangle.NO_BORDER);
            t5CellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table5.addCell(t5CellFour);

            t5CellFive.setBorder(Rectangle.NO_BORDER);
            t5CellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table5.addCell(t5CellFive);

            t5CellSix.setBorder(Rectangle.NO_BORDER);
            t5CellSix.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table5.addCell(t5CellSix);

            float[] columnWidthsT5 = {0.5f, 1.5f, 1f, 1f, 1f, 1f};
            table5.setWidths(columnWidthsT5);
            doc.add(table5);



            /*LaboratoryReport labReportRes = new LaboratoryReport();
            String age = "";*/

            /*for(LaboratoryReport lr : labReport){
                labReportRes.setPat_no(lr.getPat_no());
                labReportRes.setPat_name(lr.getPat_name());
                age = lr.getYrs() + " Yrs " + lr.getMos() + " Mos " + lr.getDays() + " Days";
                labReportRes.setSex(lr.getSex());
            }*/

            // Adding Request No and Receive Date
            /*PdfPTable table = new PdfPTable(4);
            table.getDefaultCell().setBorder(0);

            PdfPCell cellOne = new PdfPCell(new Phrase("Patient No#   : "));
            PdfPCell cellTwo = new PdfPCell(new Phrase(""));
            PdfPCell cellThree = new PdfPCell(new Phrase("Order No : "));
            PdfPCell cellFour = new PdfPCell(new Phrase(""));

            cellOne.setBorder(Rectangle.NO_BORDER);

            cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table.addCell(cellOne);

            cellTwo.setBorder(Rectangle.NO_BORDER);
            cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table.addCell(cellTwo);

            cellThree.setBorder(Rectangle.NO_BORDER);
            cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table.addCell(cellThree);

            cellFour.setBorder(Rectangle.NO_BORDER);
            cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table.addCell(cellFour);

            float[] columnWidths = {1f, 1f, 1f, 1f};
            table.setWidths(columnWidths);
            doc.add(table);

            // Adding Patient Name and Sex
            PdfPTable table1 = new PdfPTable(4);
            table1.getDefaultCell().setBorder(0);

            PdfPCell t1CellOne = new PdfPCell(new Phrase("Patient Name : "));
            PdfPCell t1cellTwo = new PdfPCell(new Phrase("Patient Name"));
            PdfPCell t1CellThree = new PdfPCell(new Phrase("Age : "));
//			PdfPCell t1cellFour = new PdfPCell(new Phrase(labReportRes.getYrs() + " " + labReportRes.getMos() + " " + labReportRes.getDays()));
            PdfPCell t1cellFour = new PdfPCell(new Phrase("Age"));

            t1CellOne.setBorder(Rectangle.NO_BORDER);
            t1CellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table1.addCell(t1CellOne);

            t1cellTwo.setBorder(Rectangle.NO_BORDER);
            t1cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table1.addCell(t1cellTwo);

            t1CellThree.setBorder(Rectangle.NO_BORDER);
            t1CellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table1.addCell(t1CellThree);

            t1cellFour.setBorder(Rectangle.NO_BORDER);
            t1cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table1.addCell(t1cellFour);

            float[] columnWidthsT1 = {1f, 1f, 1f, 1f};
            table1.setWidths(columnWidthsT1);

            doc.add(table1);

            // Adding Request Date and Receive Data and Time
            PdfPTable table2 = new PdfPTable(6);
            table1.getDefaultCell().setBorder(0);

            PdfPCell t2CellOne = new PdfPCell(new Phrase("Nationality : "));
            PdfPCell t2CellTwo = new PdfPCell(new Phrase("SAUDI"));
            PdfPCell t2CellThree = new PdfPCell(new Phrase("Gender : "));
            PdfPCell t2CellFour = new PdfPCell(new Phrase(""));
            PdfPCell t2CellFive = new PdfPCell(new Phrase("Merital Status : "));
            PdfPCell t2CellSix = new PdfPCell(new Phrase(""));

            t2CellOne.setBorder(Rectangle.NO_BORDER);
            t2CellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table2.addCell(t2CellOne);

            t2CellTwo.setBorder(Rectangle.NO_BORDER);
            t2CellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table2.addCell(t2CellTwo);

            t2CellThree.setBorder(Rectangle.NO_BORDER);
            t2CellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table2.addCell(t2CellThree);

            t2CellFour.setBorder(Rectangle.NO_BORDER);
            t2CellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table2.addCell(t2CellFour);

            t2CellFive.setBorder(Rectangle.NO_BORDER);
            t2CellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table2.addCell(t2CellFive);

            t2CellSix.setBorder(Rectangle.NO_BORDER);
            t2CellSix.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table2.addCell(t2CellSix);

            float[] columnWidthsT2 = {1f, 1f, 1f, 0.75f, 1.25f, 1f};
            table2.setWidths(columnWidthsT2);

            doc.add(table2);*/

            // Adding Lab No and Speciment
           /* PdfPTable table3 = new PdfPTable(6);
            table1.getDefaultCell().setBorder(0);

            PdfPCell t3CellOne = new PdfPCell(new Phrase("Lab No # : "));
            PdfPCell t3CellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t3CellThree = new PdfPCell(new Phrase("Specimen : "));
            PdfPCell t3CellFour = new PdfPCell(new Phrase("  "));
            PdfPCell t3CellFive = new PdfPCell(new Phrase("  "));
            PdfPCell t3CellSix = new PdfPCell(new Phrase("  "));

            t3CellOne.setBorder(Rectangle.NO_BORDER);
            t3CellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table3.addCell(t3CellOne);

            t3CellTwo.setBorder(Rectangle.NO_BORDER);
            t3CellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table3.addCell(t3CellTwo);

            t3CellThree.setBorder(Rectangle.NO_BORDER);
            t3CellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table3.addCell(t3CellThree);

            t3CellFour.setBorder(Rectangle.NO_BORDER);
            t3CellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table3.addCell(t3CellFour);

            float[] columnWidthsT3 = {1f, 1f, 1f, 1f, 1f, 1f};
            table3.setWidths(columnWidthsT3);

            doc.add(table3);*/

            // Adding Location
            /*PdfPTable table4 = new PdfPTable(2);
            table1.getDefaultCell().setBorder(0);
            PdfPCell t4CellOne = new PdfPCell(new Phrase("Location : "));
            PdfPCell t4CellTwo = new PdfPCell(new Phrase(" "));

            t4CellOne.setBorder(Rectangle.NO_BORDER);
            t4CellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table4.addCell(t4CellOne);
            t4CellTwo.setBorder(Rectangle.NO_BORDER);
            t4CellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table4.addCell(t4CellTwo);
            float[] columnWidthsT4 = {1f, 3f};
            table4.setWidths(columnWidthsT4);

            doc.add(table4);*/

            Paragraph p2 = new Paragraph(
                    "--------------------------------------------------------------------------------------------------------------");
            Font paraFont = new Font(Font.FontFamily.COURIER);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setFont(paraFont);
            // add paragraph to document
            doc.add(p2);



            PdfPTable table6 = new PdfPTable(5);
            table6.getDefaultCell().setBorder(0);

            PdfPCell t6cellOne = new PdfPCell(new Phrase(Font.BOLD, "Cheif Complaints :"));
            PdfPCell t6cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t6cellThree = new PdfPCell(new Phrase(Font.BOLD, "Rx "));
            PdfPCell t6cellFour = new PdfPCell(new Phrase(" "));
            PdfPCell t6cellFive = new PdfPCell(new Phrase(" "));

            t6cellOne.setBorder(Rectangle.NO_BORDER);
            t6cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table6.addCell(t6cellOne);

            t6cellTwo.setBorder(Rectangle.NO_BORDER);
            t6cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table6.addCell(t6cellTwo);

            t6cellThree.setBorder(Rectangle.NO_BORDER);
            t6cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table6.addCell(t6cellThree);

            t6cellFour.setBorder(Rectangle.NO_BORDER);
            t6cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table6.addCell(t6cellFour);

            t6cellFive.setBorder(Rectangle.NO_BORDER);
            t6cellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table6.addCell(t6cellFive);

            float[] t6columnWidths = {2f, 1f, 1f, 1f, 1f};
            table6.setWidths(t6columnWidths);
            doc.add(table6);



            PdfPTable table7 = new PdfPTable(5);
            table7.getDefaultCell().setBorder(0);

            PdfPCell t7cellOne = new PdfPCell(new Phrase("Jaundice for 4 month"));
            PdfPCell t7cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t7cellThree = new PdfPCell(new Phrase(" "));
            PdfPCell t7cellFour = new PdfPCell(new Phrase(Font.BOLD, "Tab. Flusid 20/50"));
            PdfPCell t7cellFive = new PdfPCell(new Phrase(""));

            t7cellOne.setBorder(Rectangle.NO_BORDER);
            t7cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table7.addCell(t7cellOne);

            t7cellTwo.setBorder(Rectangle.NO_BORDER);
            t7cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table7.addCell(t7cellTwo);

            t7cellThree.setBorder(Rectangle.NO_BORDER);
            t7cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table7.addCell(t7cellThree);

            t7cellFour.setBorder(Rectangle.NO_BORDER);
            t7cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table7.addCell(t7cellFour);

            t7cellFive.setBorder(Rectangle.NO_BORDER);
            t7cellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table7.addCell(t7cellFive);

            float[] t7columnWidths = {3f, 1f, 1f, 4f, 1f};
            table7.setWidths(t7columnWidths);
            doc.add(table7);


            PdfPTable table8 = new PdfPTable(5);
            table8.getDefaultCell().setBorder(0);

            PdfPCell t8cellOne = new PdfPCell(new Phrase("Weekness for 4 month"));
            PdfPCell t8cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t8cellThree = new PdfPCell(new Phrase(" "));
            PdfPCell t8cellFour = new PdfPCell(new Phrase("1+ 0 +1"));
            PdfPCell t8cellFive = new PdfPCell(new Phrase(""));

            t8cellOne.setBorder(Rectangle.NO_BORDER);
            t8cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table8.addCell(t8cellOne);

            t8cellTwo.setBorder(Rectangle.NO_BORDER);
            t8cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table8.addCell(t8cellTwo);

            t8cellThree.setBorder(Rectangle.NO_BORDER);
            t8cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table8.addCell(t8cellThree);

            t8cellFour.setBorder(Rectangle.NO_BORDER);
            t8cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table8.addCell(t8cellFour);

            t8cellFive.setBorder(Rectangle.NO_BORDER);
            t8cellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table8.addCell(t8cellFive);

            float[] t8columnWidths = {3f, 1f, 1f, 4f, 1f};
            table8.setWidths(t8columnWidths);
            doc.add(table8);


            PdfPTable table9 = new PdfPTable(5);
            table9.getDefaultCell().setBorder(0);

            PdfPCell t9cellOne = new PdfPCell(new Phrase("B/P: 110/70"));
            PdfPCell t9cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t9cellThree = new PdfPCell(new Phrase(" "));
            PdfPCell t9cellFour = new PdfPCell(new Phrase(Font.BOLD, "Syp. Osmolax"));
            PdfPCell t9cellFive = new PdfPCell(new Phrase(""));

            t9cellOne.setBorder(Rectangle.NO_BORDER);
            t9cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table9.addCell(t9cellOne);

            t9cellTwo.setBorder(Rectangle.NO_BORDER);
            t9cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table9.addCell(t9cellTwo);

            t9cellThree.setBorder(Rectangle.NO_BORDER);
            t9cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table9.addCell(t9cellThree);

            t9cellFour.setBorder(Rectangle.NO_BORDER);
            t9cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table9.addCell(t9cellFour);

            t9cellFive.setBorder(Rectangle.NO_BORDER);
            t9cellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table9.addCell(t9cellFive);

            float[] t9columnWidths = {3f, 1f, 1f, 4f, 1f};
            table9.setWidths(t9columnWidths);
            doc.add(table9);



            PdfPTable table10 = new PdfPTable(5);
            table10.getDefaultCell().setBorder(0);

            PdfPCell t10cellOne = new PdfPCell(new Phrase(Font.BOLD, "General Exam:"));
            PdfPCell t10cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t10cellThree = new PdfPCell(new Phrase(" "));
            PdfPCell t10cellFour = new PdfPCell(new Phrase("2 spoons in 3 Times each day"));
            PdfPCell t10cellFive = new PdfPCell(new Phrase(""));

            t10cellOne.setBorder(Rectangle.NO_BORDER);
            t10cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table10.addCell(t10cellOne);

            t10cellTwo.setBorder(Rectangle.NO_BORDER);
            t10cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table10.addCell(t10cellTwo);

            t10cellThree.setBorder(Rectangle.NO_BORDER);
            t10cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table10.addCell(t10cellThree);

            t10cellFour.setBorder(Rectangle.NO_BORDER);
            t10cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table10.addCell(t10cellFour);

            t10cellFive.setBorder(Rectangle.NO_BORDER);
            t10cellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table10.addCell(t10cellFive);

            float[] t10columnWidths = {3f, 1f, 1f, 4f, 1f};
            table10.setWidths(t10columnWidths);
            doc.add(table10);



            PdfPTable table11 = new PdfPTable(5);
            table11.getDefaultCell().setBorder(0);

            PdfPCell t11cellOne = new PdfPCell(new Phrase("Jaundice"));
            PdfPCell t11cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t11cellThree = new PdfPCell(new Phrase(" "));
            PdfPCell t11cellFour = new PdfPCell(new Phrase(Font.BOLD, "Tab. Indever 40mg"));
            PdfPCell t11cellFive = new PdfPCell(new Phrase(""));

            t11cellOne.setBorder(Rectangle.NO_BORDER);
            t11cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table11.addCell(t11cellOne);

            t11cellTwo.setBorder(Rectangle.NO_BORDER);
            t11cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table11.addCell(t11cellTwo);

            t11cellThree.setBorder(Rectangle.NO_BORDER);
            t11cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table11.addCell(t11cellThree);

            t11cellFour.setBorder(Rectangle.NO_BORDER);
            t11cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table11.addCell(t11cellFour);

            t11cellFive.setBorder(Rectangle.NO_BORDER);
            t11cellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table11.addCell(t11cellFive);

            float[] t11columnWidths = {3f, 1f, 1f, 4f, 1f};
            table11.setWidths(t11columnWidths);
            doc.add(table11);


            PdfPTable table12 = new PdfPTable(5);
            table12.getDefaultCell().setBorder(0);

            PdfPCell t12cellOne = new PdfPCell(new Phrase("Anaemia"));
            PdfPCell t12cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t12cellThree = new PdfPCell(new Phrase(" "));
            PdfPCell t12cellFour = new PdfPCell(new Phrase("0.5+ 0 +0.5"));
            PdfPCell t12cellFive = new PdfPCell(new Phrase(""));

            t12cellOne.setBorder(Rectangle.NO_BORDER);
            t12cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table12.addCell(t12cellOne);

            t12cellTwo.setBorder(Rectangle.NO_BORDER);
            t12cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table12.addCell(t12cellTwo);

            t12cellThree.setBorder(Rectangle.NO_BORDER);
            t12cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table12.addCell(t12cellThree);

            t12cellFour.setBorder(Rectangle.NO_BORDER);
            t12cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table12.addCell(t12cellFour);

            t12cellFive.setBorder(Rectangle.NO_BORDER);
            t12cellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table12.addCell(t12cellFive);

            float[] t12columnWidths = {3f, 1f, 1f, 4f, 1f};
            table12.setWidths(t12columnWidths);
            doc.add(table12);


            PdfPTable table13 = new PdfPTable(5);
            table13.getDefaultCell().setBorder(0);

            PdfPCell t13cellOne = new PdfPCell(new Phrase("Spidernaevi"));
            PdfPCell t13cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t13cellThree = new PdfPCell(new Phrase(" "));
            PdfPCell t13cellFour = new PdfPCell(new Phrase(Font.BOLD, "Tab. Ursocol 300mg"));
            PdfPCell t13cellFive = new PdfPCell(new Phrase(""));

            t13cellOne.setBorder(Rectangle.NO_BORDER);
            t13cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table13.addCell(t13cellOne);

            t13cellTwo.setBorder(Rectangle.NO_BORDER);
            t13cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table13.addCell(t13cellTwo);

            t13cellThree.setBorder(Rectangle.NO_BORDER);
            t13cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table13.addCell(t13cellThree);

            t13cellFour.setBorder(Rectangle.NO_BORDER);
            t13cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table13.addCell(t13cellFour);

            t13cellFive.setBorder(Rectangle.NO_BORDER);
            t13cellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table13.addCell(t13cellFive);

            float[] t13columnWidths = {3f, 1f, 1f, 4f, 1f};
            table13.setWidths(t13columnWidths);
            doc.add(table13);


            PdfPTable table14 = new PdfPTable(5);
            table14.getDefaultCell().setBorder(0);

            PdfPCell t14cellOne = new PdfPCell(new Phrase("Testes: Soft,Small"));
            PdfPCell t14cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t14cellThree = new PdfPCell(new Phrase(" "));
            PdfPCell t14cellFour = new PdfPCell(new Phrase("1+ 0 +1"));
            PdfPCell t14cellFive = new PdfPCell(new Phrase(""));

            t14cellOne.setBorder(Rectangle.NO_BORDER);
            t14cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table14.addCell(t14cellOne);

            t14cellTwo.setBorder(Rectangle.NO_BORDER);
            t14cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table14.addCell(t14cellTwo);

            t14cellThree.setBorder(Rectangle.NO_BORDER);
            t14cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table14.addCell(t14cellThree);

            t14cellFour.setBorder(Rectangle.NO_BORDER);
            t14cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table14.addCell(t14cellFour);

            t14cellFive.setBorder(Rectangle.NO_BORDER);
            t14cellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table14.addCell(t14cellFive);

            float[] t14columnWidths = {3f, 1f, 1f, 4f, 1f};
            table14.setWidths(t14columnWidths);
            doc.add(table14);



            PdfPTable table15 = new PdfPTable(5);
            table15.getDefaultCell().setBorder(0);

            PdfPCell t15cellOne = new PdfPCell(new Phrase(Font.BOLD, "Investigation:"));
            PdfPCell t15cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t15cellThree = new PdfPCell(new Phrase(" "));
            PdfPCell t15cellFour = new PdfPCell(new Phrase(" "));
            PdfPCell t15cellFive = new PdfPCell(new Phrase(""));

            t15cellOne.setBorder(Rectangle.NO_BORDER);
            t15cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table15.addCell(t15cellOne);

            t15cellTwo.setBorder(Rectangle.NO_BORDER);
            t15cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table15.addCell(t15cellTwo);

            t15cellThree.setBorder(Rectangle.NO_BORDER);
            t15cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table15.addCell(t15cellThree);

            t15cellFour.setBorder(Rectangle.NO_BORDER);
            t15cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table15.addCell(t15cellFour);

            t15cellFive.setBorder(Rectangle.NO_BORDER);
            t15cellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table15.addCell(t15cellFive);

            float[] t15columnWidths = {3f, 1f, 1f, 4f, 1f};
            table15.setWidths(t15columnWidths);
            doc.add(table15);


            PdfPTable table16 = new PdfPTable(5);
            table16.getDefaultCell().setBorder(0);

            PdfPCell t16cellOne = new PdfPCell(new Phrase("SGPT,SGOT,S.Albumin,S.Billirubin,GGT"));
            PdfPCell t16cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t16cellThree = new PdfPCell(new Phrase(" "));
            PdfPCell t16cellFour = new PdfPCell(new Phrase(" "));
            PdfPCell t16cellFive = new PdfPCell(new Phrase(""));

            t16cellOne.setBorder(Rectangle.NO_BORDER);
            t16cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table16.addCell(t16cellOne);

            t16cellTwo.setBorder(Rectangle.NO_BORDER);
            t16cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table16.addCell(t16cellTwo);

            t16cellThree.setBorder(Rectangle.NO_BORDER);
            t16cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table16.addCell(t16cellThree);

            t16cellFour.setBorder(Rectangle.NO_BORDER);
            t16cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table16.addCell(t16cellFour);

            t16cellFive.setBorder(Rectangle.NO_BORDER);
            t16cellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table16.addCell(t16cellFive);

            float[] t16columnWidths = {3f, 1f, 1f, 4f, 1f};
            table16.setWidths(t16columnWidths);
            doc.add(table16);


            PdfPTable table17 = new PdfPTable(5);
            table17.getDefaultCell().setBorder(0);

            PdfPCell t17cellOne = new PdfPCell(new Phrase(Font.BOLD, "Advice:"));
            PdfPCell t17cellTwo = new PdfPCell(new Phrase(" "));
            PdfPCell t17cellThree = new PdfPCell(new Phrase(" "));
            PdfPCell t17cellFour = new PdfPCell(new Phrase("Drink Water, Take Rest 7 Days"));
            PdfPCell t17cellFive = new PdfPCell(new Phrase(""));

            t17cellOne.setBorder(Rectangle.NO_BORDER);
            t17cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table17.addCell(t17cellOne);

            t17cellTwo.setBorder(Rectangle.NO_BORDER);
            t17cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table17.addCell(t17cellTwo);

            t17cellThree.setBorder(Rectangle.NO_BORDER);
            t17cellThree.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table17.addCell(t17cellThree);

            t17cellFour.setBorder(Rectangle.NO_BORDER);
            t17cellFour.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table17.addCell(t17cellFour);

            t17cellFive.setBorder(Rectangle.NO_BORDER);
            t17cellFive.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table17.addCell(t17cellFive);

            float[] t17columnWidths = {3f, 1f, 1f, 4f, 1f};
            table17.setWidths(t17columnWidths);
            doc.add(table17);


        } catch (DocumentException de) {
            Log.e("PDFCreator","DocumentException:" + de);
            de.printStackTrace();
        } finally {
            doc.close();
        }
    }


   /* public  void CreateBar(Document document, PdfWriter writer){

        Barcode128 code128 = new Barcode128();
        code128.setGenerateChecksum(true);
        code128.setCode("0123456789");
        //Add Barcode to PDF document
        try {
            document.add(code128.createImageWithBarcode(writer.getDirectContent(), null, null));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }*/

    // Header For PDF
    public void addHeaderImg(Document doc, Context context) {
        // Image adding
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pdf_header);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image headerImg = null;
        try {
            headerImg = Image.getInstance(stream.toByteArray());
            headerImg.setAlignment(Image.MIDDLE);
            // add image to document

        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            doc.add(headerImg);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

        /*try{
            PdfPTable table1Head = new PdfPTable(2);
            table1Head.getDefaultCell().setBorder(0);

            PdfPCell cellOne = new PdfPCell(new Phrase("DR. Md Abu Bakar Siddique Khan"));
            PdfPCell cellTwo = new PdfPCell(new Phrase("Popular Diagonostic Center"));

            cellOne.setBorder(Rectangle.NO_BORDER);

            cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table1Head.addCell(cellOne);

            cellTwo.setBorder(Rectangle.NO_BORDER);
            cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table1Head.addCell(cellTwo);

            float[] columnWidths = {1f, 1f};
            table1Head.setWidths(columnWidths);
            doc.add(table1Head);


            PdfPTable table2Head = new PdfPTable(2);
            table2Head.getDefaultCell().setBorder(0);

            PdfPCell t2cellOne = new PdfPCell(new Phrase("MBBS, FCPS"));
            PdfPCell t2cellTwo = new PdfPCell(new Phrase("6 P.M - 10 P.M"));

            t2cellOne.setBorder(Rectangle.NO_BORDER);

            t2cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table2Head.addCell(t2cellOne);

            t2cellTwo.setBorder(Rectangle.NO_BORDER);
            t2cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table2Head.addCell(t2cellTwo);

            float[] t2columnWidths = {1f, 1f};
            table2Head.setWidths(t2columnWidths);
            doc.add(table2Head);


            PdfPTable table3Head = new PdfPTable(2);
            table3Head.getDefaultCell().setBorder(0);

            PdfPCell t3cellOne = new PdfPCell(new Phrase("Gyneecologycal Surgeon"));
            PdfPCell t3cellTwo = new PdfPCell(new Phrase("01735487522"));

            t3cellOne.setBorder(Rectangle.NO_BORDER);

            t3cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table3Head.addCell(t3cellOne);

            t3cellTwo.setBorder(Rectangle.NO_BORDER);
            t3cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table3Head.addCell(t3cellTwo);

            float[] t3columnWidths = {1f, 1f};
            table3Head.setWidths(t3columnWidths);
            doc.add(table3Head);


            PdfPTable table4Head = new PdfPTable(2);
            table4Head.getDefaultCell().setBorder(0);

            PdfPCell t4cellOne = new PdfPCell(new Phrase("Cheif Consultant(Gynee)"));
            PdfPCell t4cellTwo = new PdfPCell(new Phrase("01735485478"));

            t4cellOne.setBorder(Rectangle.NO_BORDER);

            t4cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table4Head.addCell(t4cellOne);

            t4cellTwo.setBorder(Rectangle.NO_BORDER);
            t4cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table4Head.addCell(t4cellTwo);

            float[] t4columnWidths = {1f, 1f};
            table4Head.setWidths(t4columnWidths);
            doc.add(table4Head);


            PdfPTable table5Head = new PdfPTable(2);
            table5Head.getDefaultCell().setBorder(0);

            PdfPCell t5cellOne = new PdfPCell(new Phrase("Dhaka Medical College"));
            PdfPCell t5cellTwo = new PdfPCell(new Phrase("Mirpur-6, Dhaka"));

            t5cellOne.setBorder(Rectangle.NO_BORDER);

            t5cellOne.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            table5Head.addCell(t5cellOne);

            t5cellTwo.setBorder(Rectangle.NO_BORDER);
            t5cellTwo.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
            table5Head.addCell(t5cellTwo);

            float[] t5columnWidths = {1f, 1f};
            table5Head.setWidths(t5columnWidths);
            doc.add(table5Head);

        } catch (DocumentException de) {
            Log.e("PDFCreator","DocumentException:" + de);
            de.printStackTrace();
        } finally {
            doc.close();
        }*/
        }

