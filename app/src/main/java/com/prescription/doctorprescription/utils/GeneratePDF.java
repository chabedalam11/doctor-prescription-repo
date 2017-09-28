package com.prescription.doctorprescription.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.html.HtmlUtilities;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseField;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.prescription.doctorprescription.R;
import com.prescription.doctorprescription.webService.model.Analysis;
import com.prescription.doctorprescription.webService.model.DesignationInfo;
import com.prescription.doctorprescription.webService.model.DocClinicInfo;
import com.prescription.doctorprescription.webService.model.DrugMaster;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by medisys on 26-Sep-17.
 */

public class GeneratePDF {
    final String TAG = "GeneratePDF";
    PrescriptionMemories memory;

    private Context context;
    private Context bcontext;

    public GeneratePDF(Context current,Context bcurrent){
        this.context = current;
        this.bcontext = bcurrent;
    }

    Document document;
    PdfWriter docWriter;
    static PdfContentByte cb;
    private static BaseFont bfBold;
    float currY;
    Image myImg;
    PdfPTable pendingTable;

    public void createPDF() throws IOException {
        Log.d(TAG, "HIt method");
        memory = new PrescriptionMemories(context);

        this.document = new Document(PageSize.A4, 20.0f, 20.0f, 20.0f, 150.0f);
        try {
            String path = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/Prescription").toString();
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Log.d(TAG, "PDF Path: " + path);
            this.docWriter = PdfWriter.getInstance(this.document, new FileOutputStream(new File(dir, "prescription.pdf")));
            this.docWriter.setBoxSize("art", new Rectangle(TabSettings.DEFAULT_TAB_INTERVAL, 54.0f, 559.0f, 788.0f));
            this.docWriter.setPageEvent(new HeaderFooter());
            this.document.open();
            cb = this.docWriter.getDirectContent();
            initializeFonts();
            GeneratePDF.this.checkForAnyPendingtableToWrite();
            this.document.close();
            Log.d("OK", "done");
            displayPdf();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e2) {
            e2.printStackTrace();
        }
    }

    private void displayPdf() {
        File pdfFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "prescription/prescription.pdf");
        try {
            if (pdfFile.exists()) {
                Uri path = Uri.fromFile(pdfFile);
                Intent pdfIntent = new Intent("android.intent.action.VIEW");
                pdfIntent.setDataAndType(path, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(pdfIntent);
                return;
            }
            Toast.makeText(context, "File not found", 1).show();
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Please Install HPeprint", 1).show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void initializeFonts() {
        try {
            bfBold = BaseFont.createFont("Helvetica-Bold", "Cp1252", false);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private PdfPTable createHeaderTable() throws DocumentException, MalformedURLException, IOException {
        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setBorder(0);
        table.setTotalWidth(this.document.right() - 20.0f);
        table.setWidthPercentage(100.0f);
        table.setWidths(new int[]{6, 5, 4});
        BaseColor myColor = WebColors.getRGBColor("#EBEBEB");
        PdfPCell cell = new PdfPCell(new PdfPCell(setImage((int) R.drawable.rx800), false));
        cell.setVerticalAlignment(1);
        cell.setFixedHeight(25.0f);
        cell.setBackgroundColor(myColor);
        cell.setBorder(0);
        cell.setColspan(4);
        table.addCell(cell);
        return table;
    }

    private PdfPTable createHeaderTableText(String headerText) throws DocumentException, IOException {
        BaseColor myColor = WebColors.getRGBColor("#EBEBEB");
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        int[] iArr = new int[1];
        table.setWidths(new int[]{15});
        Font font = new Font(setRobotoBold(), 14.0f, 0, BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase(headerText, font));
        cell.setFixedHeight(25.0f);
        cell.setBackgroundColor(myColor);
        cell.setBorder(0);
        table.addCell(cell);
        return table;
    }


    private PdfPTable createPatientInfoTable() throws DocumentException, IOException {
        BaseColor myColor = WebColors.getRGBColor("#EBEBEB");
        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        int[] iArr = new int[3];
        table.setWidths(new int[]{6, 5, 4});
        Font font = new Font(setRobotoBold(), 14.0f, 0, BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase("Patient No.: " +PrescriptionInfo.patientInfo.getT_pat_id(), font));
        cell.setColspan(2);
        cell.setVerticalAlignment(1);
        cell.setFixedHeight(25.0f);
        cell.setBackgroundColor(myColor);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Date: " + PrescriptionUtils.getCurrentDate(), font));
        cell.setBorder(0);
        cell.setHorizontalAlignment(2);
        cell.setBackgroundColor(myColor);
        cell.setVerticalAlignment(1);
        cell.setFixedHeight(20.0f);
        cell.setColspan(5);
        table.addCell(cell);
        table.setSpacingAfter(10.0f);
        cell = new PdfPCell(new Paragraph("PATINFO"));
        PdfPTable nestedTable = new PdfPTable(3);
        nestedTable.setWidthPercentage(100.0f);
        nestedTable.getDefaultCell().setBorder(0);
        iArr = new int[3];
        nestedTable.setWidths(new int[]{1, 10, 1});
        if (PrescriptionInfo.patientInfo.getT_pat_sex() != null && PrescriptionInfo.patientInfo.getT_pat_sex().equals("MALE")) {
            this.myImg = setImage(R.drawable.male_dark);
        } else {
            this.myImg = setImage(R.drawable.female_dark);
        }
        PdfPCell cellImage = new PdfPCell(this.myImg, false);
        cell.setVerticalAlignment(1);
        cellImage.setBorder(0);
        cellImage.setPaddingTop(6.0f);
        cellImage.setPaddingLeft(BaseField.BORDER_WIDTH_THICK);
        cellImage.setPaddingBottom(BaseField.BORDER_WIDTH_THICK);
        nestedTable.addCell(cellImage);
        cell = new PdfPCell(new Paragraph(PrescriptionInfo.patientInfo.getT_pat_name(), new Font(setRobotoBold(), 14.0f, 0, BaseColor.BLACK)));
        cell.setColspan(2);
        cell.setPaddingTop(6.0f);
        cell.setBorder(0);
        nestedTable.addCell(cell);
        cell.addElement(nestedTable);
        cell.setBorder(0);
        table.addCell(cell);
        table.addCell(PdfObject.NOTHING);
        cell = new PdfPCell(new Paragraph("PHONE"));
        PdfPTable nestedTable2 = new PdfPTable(3);
        nestedTable2.setWidthPercentage(100.0f);
        nestedTable2.getDefaultCell().setBorder(0);
        iArr = new int[3];
        nestedTable2.setWidths(new int[]{1, 1, 4});
        this.myImg = setImage(R.drawable.phone);
        PdfPCell cellPhoneImage = new PdfPCell(this.myImg, false);
        cellPhoneImage.setBorder(0);
        nestedTable2.addCell(PdfObject.NOTHING);
        nestedTable2.addCell(cellPhoneImage);
        cell = new PdfPCell(new Paragraph(getValidField(PrescriptionInfo.patientInfo.getT_pat_mobile()), new Font(setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
        cell.setBorder(0);
        cell.setVerticalAlignment(4);
        nestedTable2.addCell(cell);
        nestedTable2.getDefaultCell().setBorder(0);
        cell.addElement(nestedTable2);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Sex"));
        PdfPTable nestedSexTable = new PdfPTable(3);
        nestedSexTable.setWidthPercentage(100.0f);
        nestedSexTable.getDefaultCell().setBorder(0);
        iArr = new int[3];
        nestedSexTable.setWidths(new int[]{1, 1, 4});
        nestedSexTable.addCell(PdfObject.NOTHING);
        cell = new PdfPCell(new Paragraph("Sex", setKeyFont()));
        cell.setVerticalAlignment(1);
        cell.setBorder(0);
        nestedSexTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(PrescriptionInfo.patientInfo.getT_pat_sex(), setValueFont()));
        cell.setVerticalAlignment(1);
        cell.setBorder(0);
        nestedSexTable.addCell(cell);
        cell.addElement(nestedSexTable);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Age"));
        PdfPTable nestedAgeTable = new PdfPTable(2);
        nestedAgeTable.setWidthPercentage(100.0f);
        nestedAgeTable.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nestedAgeTable.setWidths(new int[]{1, 4});
        cell = new PdfPCell(new Paragraph("Age", setKeyFont()));
        cell.setVerticalAlignment(1);
        cell.setBorder(0);
        nestedAgeTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(PrescriptionInfo.patientInfo.getT_pat_age()+" Years", setValueFont()));
        cell.setVerticalAlignment(1);
        cell.setBorder(0);
        nestedAgeTable.addCell(cell);
        cell.addElement(nestedAgeTable);
        cell.setBorder(0);
        table.addCell(cell);
        return table;
    }




    public PdfPTable createMedicineInfoTable(DrugMaster medicine) throws DocumentException, IOException {
        String str;
        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        int[] iArr = new int[3];
        table.setWidths(new int[]{8, 3, 3});
        PdfPCell cell = new PdfPCell(new Paragraph("MEDICINE"));
        PdfPTable nestedTable = new PdfPTable(2);
        nestedTable.setWidthPercentage(100.0f);
        nestedTable.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nestedTable.setWidths(new int[]{1, 10});
        PdfPCell cellImage = new PdfPCell(setImage(R.drawable.cap), false);
        cellImage.setBorder(0);
        //cellImage.setPadding(4.0f);
        nestedTable.addCell(cellImage);
        String fullMedicineName="";
        if (!medicine.getT_um().equals(" ")){
            fullMedicineName=medicine.getT_um()+". ";
        }
        if (!medicine.getT_medicine_name().equals(" ")){
            fullMedicineName+=medicine.getT_medicine_name();
        }
        if (!medicine.getT_strength().equals(" ")){
            fullMedicineName+="-"+medicine.getT_strength();
        }

        PdfPCell cell2 = new PdfPCell(new Phrase(fullMedicineName, new Font(setRobotoBold(), 14.0f, 0, BaseColor.BLACK)));
        cell2.setBorder(0);
        cell2.setHorizontalAlignment(0);
        cell2.setVerticalAlignment(0);
        nestedTable.addCell(cell2);
        cell.setColspan(2);
        cell.addElement(nestedTable);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("Duration"));
        PdfPTable nestedTable2 = new PdfPTable(1);
        nestedTable2.setWidthPercentage(100.0f);
        nestedTable2.getDefaultCell().setBorder(0);
        iArr = new int[1];
        nestedTable2.setWidths(new int[]{7});
        cell = new PdfPCell(new Paragraph(medicine.getT_duration(), setValueFont()));
        cell.setBorder(0);
        nestedTable2.addCell(cell);
        cell.addElement(nestedTable2);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("Frequency"));
        PdfPTable nestedTable3 = new PdfPTable(2);
        nestedTable3.setWidthPercentage(100.0f);
        nestedTable3.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nestedTable3.setWidths(new int[]{1, 5});
        nestedTable3.addCell(PdfObject.NOTHING);
        PdfPTable nestedTableDose = new PdfPTable(1);
        nestedTableDose.setWidthPercentage(100.0f);
        nestedTableDose.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nestedTableDose.setWidths(new int[]{10});
        /*PdfPCell cell1 = new PdfPCell(new Paragraph("Frequency", setKeyFont()));
        cell1.setBorder(0);
        nestedTableDose.addCell(cell1);*/
        PdfPCell cell1 = new PdfPCell(new Paragraph(medicine.getT_dose_time(), setValueFont()));
        cell1.setBorder(0);
        nestedTableDose.addCell(cell1);
        cell1.addElement(nestedTableDose);
        nestedTable3.addCell(cell1);
        cell.addElement(nestedTable3);
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("NOTE"));
        PdfPTable nestedTable4 = new PdfPTable(1);
        nestedTable4.setWidthPercentage(100.0f);
        nestedTable4.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nestedTable4.setWidths(new int[]{7});
        String advice="";
        if(!medicine.getT_advice().equals(" ")){
            advice="("+medicine.getT_advice()+")";
        }
        cell = new PdfPCell(new Paragraph(advice, setValueFont()));
        cell.setBorder(0);
        nestedTable4.addCell(cell);
        cell.addElement(nestedTable4);
        //cell.setRowspan(2);
        cell.setColspan(2);
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);
        return table;
    }

    public String getValidField(String str) {
        if (str == null || str.length() == 0) {
            return "-";
        }
        return str;
    }

    public Font setKeyFont() throws DocumentException, IOException {
        return new Font(setRobotoLight(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"));
    }

    public Font setValueFont() throws DocumentException, IOException {
        return new Font(setRobotoRegular(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, BaseColor.BLACK);
    }

    private Image setImage(int maleDark) throws BadElementException, MalformedURLException, IOException {
        float eighteen = context.getResources().getDimension(R.dimen.v18dp);
        float twenty = context.getResources().getDimension(R.dimen.v20dp);
        float sixsteen = context.getResources().getDimension(R.dimen.v16dp);
        int w = (int) eighteen;
        int h = (int) eighteen;
        switch (maleDark) {
            case R.drawable.address:
                w = (int) twenty;
                h = (int) eighteen;
                break;
            case R.drawable.mail:
                w = (int) twenty;
                h = (int) sixsteen;
                break;
            case R.drawable.male_dark:
                w = (int) twenty;
                h = (int) eighteen;
                break;
            case R.drawable.phone:
                w = (int) eighteen;
                h = (int) eighteen;
                break;
            case R.drawable.rx800:
                w = (int) twenty;
                h = (int) eighteen;
                break;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(bcontext.getResources(), maleDark);
        Bitmap.createScaledBitmap(bitmap, w, h, true).compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmap.recycle();
        return Image.getInstance(stream.toByteArray());
    }


    private void checkForAnyPendingtableToWrite() {
        try {
            PdfPTable mainTable = new PdfPTable(2);
            mainTable.getDefaultCell().setBorder(0);
            mainTable.setWidthPercentage(100.0f);
            int[] iArr = new int[2];
            mainTable.setWidths(new int[]{5, 10});
            PdfPCell cellLeft = new PdfPCell(new Paragraph("LEFT"));
            cellLeft.addElement(createLeftSideTable());
            cellLeft.setBorder(0);
            mainTable.addCell(cellLeft);

            PdfPCell cellRight = new PdfPCell(new Paragraph("RIGHT"));
            cellRight.addElement(createRightSideTable());
            cellRight.setBorder(0);
            mainTable.addCell(cellRight);
            addTable(mainTable);
            //addTable(mainTable);

            if(!PrescriptionInfo.advice.equals("")){
                PdfPTable adviceTable = new PdfPTable(3);
                adviceTable.getDefaultCell().setBorder(0);
                adviceTable.setWidthPercentage(100.0f);
                adviceTable.setWidths(new int[]{2,1, 12});

                Font font = new Font(setRobotoBold(), 14.0f, 0, BaseColor.BLACK);
                PdfPCell cell = new PdfPCell(new Phrase("Advice ", font));
                cell.setBorder(0);
                adviceTable.addCell(cell);

                cell = new PdfPCell(new Phrase(":", font));
                cell.setBorder(0);
                adviceTable.addCell(cell);

                font = new Font(setRobotoBold(), 12.0f, 0, BaseColor.BLACK);
                cell = new PdfPCell(new Phrase(PrescriptionInfo.advice, font));
                cell.setBorder(0);
                adviceTable.addCell(cell);
                addTable(adviceTable);
            }
        }catch (DocumentException d){
            d.printStackTrace();
        }catch (IOException i){
            i.printStackTrace();
        }
    }

    private PdfPTable createLeftSideTable() throws DocumentException, IOException {
        PdfPTable nestedTable = new PdfPTable(3);
        nestedTable.setWidthPercentage(100.0f);
        nestedTable.getDefaultCell().setBorder(0);
        int [] iArr = new int[3];
        nestedTable.setWidths(new int[]{4,1, 5});

        PdfPCell cell = new PdfPCell(new Paragraph("CCHEADER"));
        cell.setColspan(3);
        cell.setBorder(0);
        cell.addElement(createHeaderTableText("Chief Complaints"));
        nestedTable.addCell(cell);

        cell = new PdfPCell(new Paragraph(PrescriptionInfo.chiefcomplaint, setValueFont()));
        cell.setColspan(3);
        cell.setBorder(0);
        nestedTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("GENERALEXAMIN"));
        cell.setColspan(3);
        cell.setBorder(0);
        cell.setPaddingTop(8.0f);
        cell.addElement(createHeaderTableText("General Examinations"));
        nestedTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Pulse", setKeyFont()));
        cell.setBorder(0);
        nestedTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(":", setValueFont()));
        cell.setBorder(0);
        nestedTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(PrescriptionInfo.patientPalse, setValueFont()));
        cell.setBorder(0);
        nestedTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("B/P", setKeyFont()));
        cell.setBorder(0);
        nestedTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(":", setValueFont()));
        cell.setBorder(0);
        nestedTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(PrescriptionInfo.patientBloodPressure, setValueFont()));
        cell.setBorder(0);
        nestedTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Temperature", setKeyFont()));
        cell.setBorder(0);
        nestedTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(":", setValueFont()));
        cell.setBorder(0);
        nestedTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(PrescriptionInfo.patientTemprature, setValueFont()));
        cell.setBorder(0);
        nestedTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("Resp", setKeyFont()));
        cell.setBorder(0);
        nestedTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(":", setValueFont()));
        cell.setBorder(0);
        nestedTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(PrescriptionInfo.patientResp, setValueFont()));
        cell.setBorder(0);
        nestedTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("INVESTIGATIONS"));
        cell.setColspan(3);
        cell.setBorder(0);
        cell.setPaddingTop(8.0f);
        cell.addElement(createHeaderTableText("Investigations"));
        nestedTable.addCell(cell);

        ArrayList<Analysis> analysisesList=PrescriptionInfo.analysisesList;
        for(Analysis analysis: analysisesList){
            cell = new PdfPCell(new Paragraph("=> "+analysis.getT_analysis_name(), setValueFont()));
            cell.setColspan(3);
            cell.setBorder(0);
            nestedTable.addCell(cell);
        }
        return nestedTable;
    }

    private PdfPTable createRightSideTable() throws DocumentException, IOException {
        PdfPTable nestedTable = new PdfPTable(1);
        nestedTable.setWidthPercentage(100.0f);
        nestedTable.getDefaultCell().setBorder(0);
        int [] iArr = new int[1];
        nestedTable.setWidths(new int[]{10});
        PdfPTable rxHEaderTable = GeneratePDF.this.createHeaderTable();
        rxHEaderTable.setSpacingAfter(7.0f);
        PdfPCell cell = new PdfPCell(new Paragraph("HEADER"));
        cell.addElement(rxHEaderTable);
        cell.setBorder(0);
        nestedTable.addCell(cell);
        ArrayList<DrugMaster> drugMasterList =PrescriptionInfo.drugMasterList;
        for(DrugMaster medicine:drugMasterList){
            cell = new PdfPCell(new Paragraph("RIGHT"));
            cell.addElement(createMedicineInfoTable(medicine));
            cell.setBorder(0);
            nestedTable.addCell(cell);
        }


        return nestedTable;
    }

    private void addTable(PdfPTable table) {
        table.setTotalWidth(this.document.right() - 20.0f);
        float tableHeight = table.getTotalHeight();
        if (tableHeight < this.currY - 150.0f) {
            this.currY = table.writeSelectedRows(0, -1, this.document.left(), this.currY - 5.0f, this.docWriter.getDirectContent());
            drawSeperator();
            return;
        }
        this.pendingTable = table;
        this.document.newPage();
    }

    private void drawSeperator() {
        LineSeparator lineSeparator = new LineSeparator(BaseField.BORDER_WIDTH_THIN, 100.0f, WebColors.getRGBColor("#E3E3E3"), 1, BaseField.BORDER_WIDTH_THIN);
        this.currY -= 10.0f;
        lineSeparator.draw(cb, this.document.left(), 20.0f, this.document.right(), this.currY + 20.0f, this.currY);
    }



    //======================== INNER CLASS ======================================

    class HeaderFooter extends PdfPageEventHelper {
        int count = 0;
        String header;
        PdfTemplate total;

        HeaderFooter() {

        }

       public void setHeader(String header) {
            this.header = header;
        }

        public void onOpenDocument(PdfWriter writer, Document document) {
        }

        public void onStartPage(PdfWriter writer, Document document) {
            DocClinicInfo clinicInfo=null;
            if(PrescriptionInfo.docClinicInfoList.size()>0){
                clinicInfo=PrescriptionInfo.docClinicInfoList.get(0);
            }
            DocumentException de;
            IOException e;
            this.count++;
            this.total = writer.getDirectContent().createTemplate(30.0f, 40.0f);
            PdfPTable table2;
                table2 = new PdfPTable(2);
                try {
                    table2.setWidthPercentage(100.0f);
                    table2.setWidths(new int[]{2, 2});
                    table2.setTotalWidth(document.right() - 20.0f);
                    table2.getDefaultCell().setBorder(0);

                    PdfPCell cell = new PdfPCell(new Paragraph("DESIGNATION"));
                    PdfPTable nestedTable = new PdfPTable(1);
                    nestedTable.setWidthPercentage(100.0f);
                    nestedTable.getDefaultCell().setBorder(0);
                    int [] iArr = new int[1];
                    nestedTable.setWidths(new int[]{7});

                    cell = new PdfPCell(new Paragraph(memory.getPref(memory.KEY_DOC_NAME), setDrBigFont()));
                    cell.setBorder(0);
                    nestedTable.addCell(cell);

                    for (DesignationInfo designationInfo:PrescriptionInfo.designationList){
                        cell = new PdfPCell(new Paragraph(designationInfo.getT_desig_name()+", "+designationInfo.getOther(), new Font(GeneratePDF.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                        cell.setBorder(0);
                        nestedTable.addCell(cell);
                    }

                    cell = new PdfPCell(new Paragraph("Mobile: "+memory.getPref(memory.KEY_DOC_PHONE1)+" "+memory.getPref(memory.KEY_DOC_PHONE2), new Font(GeneratePDF.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setBorder(0);
                    nestedTable.addCell(cell);

                    cell = new PdfPCell(new Paragraph("Email: "+memory.getPref(memory.KEY_DOC_EMAIL), new Font(GeneratePDF.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setBorder(0);
                    nestedTable.addCell(cell);

                    cell.addElement(nestedTable);
                    cell.setBorder(0);
                    table2.addCell(cell);

                    nestedTable = new PdfPTable(1);
                    nestedTable.setWidthPercentage(100.0f);
                    nestedTable.getDefaultCell().setBorder(0);
                    iArr = new int[1];
                    nestedTable.setWidths(new int[]{7});

                    String clinicValue="";
                    if(clinicInfo != null){
                        clinicValue=clinicInfo.getT_clinic_other();
                    }
                    cell = new PdfPCell(new Paragraph(clinicValue, new Font(GeneratePDF.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setBorder(0);
                    cell.setHorizontalAlignment(2);
                    nestedTable.addCell(cell);

                    clinicValue="";
                    if(clinicInfo != null){
                        clinicValue=clinicInfo.getT_clinic_address();
                    }
                    cell = new PdfPCell(new Paragraph(clinicValue, new Font(GeneratePDF.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setBorder(0);
                    cell.setHorizontalAlignment(2);
                    nestedTable.addCell(cell);

                    clinicValue="";
                    if(clinicInfo != null){
                        clinicValue=clinicInfo.getT_clinic_mobile();
                    }
                    cell = new PdfPCell(new Paragraph("Mobile : "+clinicValue, new Font(GeneratePDF.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setBorder(0);
                    cell.setHorizontalAlignment(2);
                    nestedTable.addCell(cell);

                    clinicValue="";
                    if(clinicInfo != null && !clinicInfo.getT_clinic_visit_day().equals("")){
                        clinicValue="Visit Time : "+clinicInfo.getT_clinic_visit_day();
                        if(!clinicInfo.getT_clinic_visit_time1().equals("")){
                            clinicValue+=" "+clinicInfo.getT_clinic_visit_time1();
                        }
                        if(!clinicInfo.getT_clinic_visit_time2().equals("")){
                            clinicValue+=" and "+clinicInfo.getT_clinic_visit_time2();
                        }

                    }
                    cell = new PdfPCell(new Paragraph(clinicValue, new Font(GeneratePDF.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setBorder(0);
                    cell.setHorizontalAlignment(2);
                    nestedTable.addCell(cell);

                    cell.addElement(nestedTable);
                    cell.setBorder(0);
                    table2.addCell(cell);

                    /*PdfPCell cell = new PdfPCell(new Phrase("Dr. Kasob Chandra Shil", setDrBigFont()));
                    cell.setBorder(0);
                    table2.addCell(cell);
                    table2.getDefaultCell().setBorder(0);
                    cell = new PdfPCell(new Phrase("chabedalam@gmail.com", new Font(GeneratePDF.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setPaddingTop(5.0f);
                    cell.setHorizontalAlignment(2);
                    cell.setBorder(0);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase("Doctor degree"+ " , " + "Speciality", new Font(GeneratePDF.setRobotoRegular(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setBorder(0);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase("Mobile NO ", new Font(GeneratePDF.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setHorizontalAlignment(2);
                    cell.setBorder(0);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase("Reg no. " + "Reg NO ", new Font(GeneratePDF.setRobotoRegular(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setBorder(0);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase("Clinic Address", new Font(GeneratePDF.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setHorizontalAlignment(2);
                    cell.setBorder(0);
                    table2.addCell(cell);*/
                    table2.setSpacingAfter(7.0f);
                    table2.setSpacingBefore(7.0f);
                    GeneratePDF.this.currY = table2.writeSelectedRows(0, -1, document.left(), document.top(), writer.getDirectContent());
                    table2 = GeneratePDF.this.createPatientInfoTable();
                    table2.setTotalWidth(document.right() - 20.0f);
                    table2.setSpacingBefore(7.0f);
                    table2.setSpacingAfter(7.0f);
                    GeneratePDF.this.currY = table2.writeSelectedRows(0, -1, document.left(), GeneratePDF.this.currY - 10.0f, writer.getDirectContent());
                } catch (DocumentException e2) {
                    de = e2;
                } catch (IOException e3) {
                    e = e3;
                    e.printStackTrace();
                }

        }

        private Font setDrBigFont() throws DocumentException, IOException {
            return new Font(GeneratePDF.setRobotoBold(), 16.0f, 0, BaseColor.BLACK);
        }

        public void onEndPage(PdfWriter writer, Document document) {
            Log.d(TAG,"hit onEndPage method");
            try {
                float rectangleWidth = (document.right() - 150.0f);
                float X = document.left() + rectangleWidth;
                float X2 = X + rectangleWidth;
                String nextVisit="";
                if(!PrescriptionInfo.nextVisit.equals("")){
                    nextVisit="Next Visit Date :"+PrescriptionInfo.nextVisit;
                }
                GeneratePDF.createHeadings(GeneratePDF.cb, document.left(), 21.0f, nextVisit);
                GeneratePDF.createHeadings(GeneratePDF.cb, BaseField.BORDER_WIDTH_THIN + rectangleWidth, 21.0f, "Doctor's signature");
                GeneratePDF.createHeadings(GeneratePDF.cb, 275.0f, 10.0f, "Page " + this.count);
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(this.total, 0, new Phrase(String.valueOf(writer.getPageNumber() - 1)), BaseField.BORDER_WIDTH_MEDIUM, BaseField.BORDER_WIDTH_MEDIUM, 0.0f);
        }
    }

    //=========================== END INNER CLASS ==============================

    protected static void createHeadings(PdfContentByte cb, float x, float y, String text) throws DocumentException, IOException {
        cb.beginText();
        cb.setFontAndSize(setRobotoRegular(), HtmlUtilities.DEFAULT_FONT_SIZE);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }

    public static BaseFont setRobotoThin() throws DocumentException, IOException {
        return BaseFont.createFont("assets/fonts/Roboto-Thin.ttf", "Cp1252", false);
    }

    public static BaseFont setRobotoRegular() throws DocumentException, IOException {
        return BaseFont.createFont("assets/fonts/Roboto-Regular.ttf", "Cp1252", false);
    }

    public static BaseFont setRobotoBold() throws DocumentException, IOException {
        return BaseFont.createFont("assets/fonts/Roboto-Bold.ttf", "Cp1252", false);
    }

    public static BaseFont setRobotoLight() throws DocumentException, IOException {
        return BaseFont.createFont("assets/fonts/Roboto-Light.ttf", "Cp1252", false);
    }
}
