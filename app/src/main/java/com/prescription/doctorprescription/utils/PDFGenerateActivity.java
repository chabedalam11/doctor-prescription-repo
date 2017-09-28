package com.prescription.doctorprescription.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
/*import com.ezest.prescription.dto.Doctor;
import com.ezest.prescription.dto.MedicalSupply;
import com.ezest.prescription.dto.Medicine;
import com.ezest.prescription.dto.Note;
import com.ezest.prescription.dto.Patient;
import com.ezest.prescription.dto.PrescriptionDetails;
import com.ezest.prescription.dto.Test;
import com.ezest.prescription.util.AppConstants;
import com.ezest.prescription.util.AppUtil;
import com.ezest.prescription.util.BaseAsyncTask;
import com.ezest.prescription.util.CustomLogs;
import com.ezest.prescription.util.DateUtils;
import com.ezest.prescription.util.Gender;*/
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class PDFGenerateActivity extends Activity{
    /*private static BaseFont bfBold;
    static PdfContentByte cb;
    private static Doctor doctor = null;
    private final String TAG = "PDFGenerateActivity";
    private final int bottomMargin = 150;
    private int check = 0;
    private int count = 0;
    float currY;
    PdfWriter docWriter;
    Document document;
    private final int leftMargin = 20;
    Image myImg;
    private PDFObject pdfObject = null;
    PdfPTable pendingTable;
    private final int rightMargin = 20;
    private final int topMargin = 20;

    private class GeneratePDFTask extends BaseAsyncTask<Void, String, Boolean> {
        public GeneratePDFTask(Activity mActivity) {
            super(mActivity);
            setProgressMessage(PDFGenerateActivity.this.getString(C0081R.string.loading));
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(Void... params) {
            super.doInBackground(params);
            PDFGenerateActivity.this.generatePDFObject();
            try {
                PDFGenerateActivity.doctor = PDFGenerateActivity.this.pdfObject.getDoctor();
                PDFGenerateActivity.this.createPDF();
                ArrayList<Object> objects = PDFGenerateActivity.this.pdfObject.getGenericPrescriptions();
                int notePos = -1;
                for (int i = 0; i < objects.size(); i++) {
                    Object object = objects.get(i);
                    if (object instanceof Note) {
                        notePos = i;
                    } else {
                        PDFGenerateActivity.this.parsePrescriptions(object);
                    }
                }
                if (notePos != -1) {
                    PDFGenerateActivity.this.parsePrescriptions(objects.get(notePos));
                }
                PDFGenerateActivity.this.document.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e2) {
                e2.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            PDFGenerateActivity.this.displayPdf();
        }
    }

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
            DocumentException de;
            IOException e;
            this.count++;
            this.total = writer.getDirectContent().createTemplate(30.0f, 40.0f);
            PdfPTable table = new PdfPTable(2);
            PdfPTable table2;
            try {
                table2 = new PdfPTable(2);
                try {
                    table2.setWidthPercentage(100.0f);
                    table2.setWidths(new int[]{2, 2});
                    table2.setTotalWidth(document.right() - 20.0f);
                    table2.getDefaultCell().setBorder(0);
                    PdfPCell cell = new PdfPCell(new Phrase("Dr. " + new StringBuilder(String.valueOf(PDFGenerateActivity.doctor.getF_name())).append(" ").append(PDFGenerateActivity.doctor.getM_name()).append(" ").append(PDFGenerateActivity.doctor.getL_name()).toString(), setDrBigFont()));
                    cell.setBorder(0);
                    table2.addCell(cell);
                    table2.getDefaultCell().setBorder(0);
                    cell = new PdfPCell(new Phrase(PDFGenerateActivity.doctor.getEmail_id(), new Font(PDFGenerateActivity.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setPaddingTop(5.0f);
                    cell.setHorizontalAlignment(2);
                    cell.setBorder(0);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase(PDFGenerateActivity.doctor.getDegree() + " , " + PDFGenerateActivity.doctor.getSpeciality(), new Font(PDFGenerateActivity.setRobotoRegular(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setBorder(0);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase(PDFGenerateActivity.doctor.getContact_number(), new Font(PDFGenerateActivity.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setHorizontalAlignment(2);
                    cell.setBorder(0);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase("Reg no. " + PDFGenerateActivity.doctor.getReg_number(), new Font(PDFGenerateActivity.setRobotoRegular(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setBorder(0);
                    table2.addCell(cell);
                    cell = new PdfPCell(new Phrase(PDFGenerateActivity.doctor.getClinic_address(), new Font(PDFGenerateActivity.setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
                    cell.setHorizontalAlignment(2);
                    cell.setBorder(0);
                    table2.addCell(cell);
                    table2.setSpacingAfter(7.0f);
                    table2.setSpacingBefore(7.0f);
                    PDFGenerateActivity.this.currY = table2.writeSelectedRows(0, -1, document.left(), document.top(), writer.getDirectContent());
                    table2 = PDFGenerateActivity.this.createPatientInfoTable(PDFGenerateActivity.this.pdfObject.getPatient(), PDFGenerateActivity.this.pdfObject.getPrescriptionDetails());
                    table2.setTotalWidth(document.right() - 20.0f);
                    table2.setSpacingBefore(7.0f);
                    table2.setSpacingAfter(7.0f);
                    PDFGenerateActivity.this.currY = table2.writeSelectedRows(0, -1, document.left(), PDFGenerateActivity.this.currY - 10.0f, writer.getDirectContent());
                    table2 = PDFGenerateActivity.this.createHeaderTable();
                    table2.setSpacingBefore(7.0f);
                    table2.setSpacingAfter(7.0f);
                    PDFGenerateActivity.this.currY = table2.writeSelectedRows(0, -1, document.left(), PDFGenerateActivity.this.currY - 10.0f, writer.getDirectContent());
                    PDFGenerateActivity.this.checkForAnyPendingtableToWrite();
                } catch (DocumentException e2) {
                    de = e2;
                } catch (IOException e3) {
                    e = e3;
                    e.printStackTrace();
                }
            } catch (DocumentException e4) {
                de = e4;
                table2 = table;
                throw new ExceptionConverter(de);
            } catch (IOException e5) {
                e = e5;
                table2 = table;
                e.printStackTrace();
            }
        }

        private Font setDrBigFont() throws DocumentException, IOException {
            return new Font(PDFGenerateActivity.setRobotoBold(), 16.0f, 0, BaseColor.BLACK);
        }

        public void onEndPage(PdfWriter writer, Document document) {
            this.total = writer.getDirectContent().createTemplate(30.0f, 40.0f);
            PdfPTable table = new PdfPTable(3);
            try {
                table.setWidthPercentage(100.0f);
                table.setWidths(new int[]{3, 3, 3});
                table.setTotalWidth(document.right() - 20.0f);
                table.setLockedWidth(true);
                table.getDefaultCell().setBorder(0);
                float rectangleWidth = (document.right() - 20.0f) / BaseField.BORDER_WIDTH_THICK;
                table.addCell(new Phrase("\nDispensed By :\nName and Address of Medical Store", new Font(PDFGenerateActivity.setRobotoRegular(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#000000"))));
                table.addCell(new Phrase("If entire prescription is not dispensed, specify name or number of medicines and quantity dispensed.", new Font(PDFGenerateActivity.setRobotoRegular(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#000000"))));
                table.addCell(new Phrase("\n \n \nDoctor's signature & stamp :", new Font(PDFGenerateActivity.setRobotoRegular(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#000000"))));
                float X = document.left() + rectangleWidth;
                Rectangle rectange = new Rectangle(document.left(), 73.0f, X - ((float) 10), 35.0f);
                rectange.setBorder(15);
                rectange.setBorderWidth(0.5f);
                rectange.setBorderColor(BaseColor.BLACK);
                document.add(rectange);
                float X2 = X + rectangleWidth;
                rectange = new Rectangle(BaseField.BORDER_WIDTH_THIN + X, 73.0f, X2 - ((float) 10), 35.0f);
                rectange.setBorder(15);
                rectange.setBorderWidth(0.5f);
                rectange.setBorderColor(BaseColor.BLACK);
                document.add(rectange);
                rectange = new Rectangle(BaseField.BORDER_WIDTH_THIN + X2, 73.0f, (X2 + rectangleWidth) - ((float) 10), 35.0f);
                rectange.setBorder(15);
                rectange.setBorderWidth(0.5f);
                rectange.setBorderColor(BaseColor.BLACK);
                document.add(rectange);
                PDFGenerateActivity.createHeadings(PDFGenerateActivity.cb, document.left(), 21.0f, "Date :");
                PDFGenerateActivity.createHeadings(PDFGenerateActivity.cb, BaseField.BORDER_WIDTH_THIN + X2, 21.0f, "Date :");
                PDFGenerateActivity.createHeadings(PDFGenerateActivity.cb, 275.0f, 10.0f, "Page " + this.count);
                table.writeSelectedRows(0, -1, document.left() - BaseField.BORDER_WIDTH_MEDIUM, document.bottom() - 20.0f, writer.getDirectContent());
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(this.total, 0, new Phrase(String.valueOf(writer.getPageNumber() - 1)), BaseField.BORDER_WIDTH_MEDIUM, BaseField.BORDER_WIDTH_MEDIUM, 0.0f);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GeneratePDFTask(this).execute(new Void[0]);
    }

    protected void onPause() {
        super.onPause();
        finish();
    }

    private void displayPdf() {
        File pdfFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "prescription/prescription.pdf");
        try {
            if (pdfFile.exists()) {
                Uri path = Uri.fromFile(pdfFile);
                Intent pdfIntent = new Intent("android.intent.action.VIEW");
                pdfIntent.setDataAndType(path, "application/pdf");
                pdfIntent.setFlags(PagedChannelRandomAccessSource.DEFAULT_TOTAL_BUFSIZE);
                startActivity(pdfIntent);
                return;
            }
            Toast.makeText(this, "File not found", 1).show();
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Please Install HPeprint", 1).show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void generatePDFObject() {
        this.pdfObject = new GeneratePdfDataObject(this, getIntent().getStringExtra(AppConstants.INTENT_PDF_PATIENT_ID), getIntent().getStringExtra(AppConstants.INTENT_PDF_PRESCRIPTION_ID)).generatePdfDataObject();
    }

    private void addTable(PdfPTable table) {
        table.setTotalWidth(this.document.right() - 20.0f);
        float tableHeight = table.getTotalHeight();
        CustomLogs.printLog("PDFGenerateActivity", "------------ Table Height " + tableHeight + " Width " + table.getTotalWidth());
        if (tableHeight < this.currY - 150.0f) {
            this.currY = table.writeSelectedRows(0, -1, this.document.left(), this.currY - 5.0f, this.docWriter.getDirectContent());
            drawSeperator();
            CustomLogs.printLog("PDFGenerateActivity", "------------ CurrY " + this.currY);
            return;
        }
        CustomLogs.printLog("PDFGenerateActivity", "------------ Add New PAge ");
        this.pendingTable = table;
        this.document.newPage();
    }

    private void drawSeperator() {
        LineSeparator lineSeparator = new LineSeparator(BaseField.BORDER_WIDTH_THIN, 100.0f, WebColors.getRGBColor("#E3E3E3"), 1, BaseField.BORDER_WIDTH_THIN);
        this.currY -= 10.0f;
        lineSeparator.draw(cb, this.document.left(), 20.0f, this.document.right(), this.currY + 20.0f, this.currY);
    }

    private void parsePrescriptions(Object object) throws DocumentException, IOException {
        PdfPTable table;
        if (object instanceof Medicine) {
            table = createMedicineInfoTable((Medicine) object);
            table.setSpacingBefore(7.0f);
            table.setSpacingAfter(7.0f);
            CustomLogs.printLog("PDFGenerateActivity", "---------- Add MedicineTable");
            addTable(table);
            this.count++;
            this.check++;
            checkCounter(this.count, this.check);
        } else if (object instanceof MedicalSupply) {
            table = createMedicalSupplyTable((MedicalSupply) object);
            table.setSpacingBefore(7.0f);
            table.setSpacingAfter(7.0f);
            CustomLogs.printLog("PDFGenerateActivity", "---------- Add MedicalSupplyTable");
            addTable(table);
            this.check++;
            checkCounter(this.count, this.check);
        } else if (object instanceof Test) {
            table = createTestTable((Test) object);
            table.setSpacingBefore(7.0f);
            table.setSpacingAfter(7.0f);
            CustomLogs.printLog("PDFGenerateActivity", "---------- Add TestTable");
            addTable(table);
            this.check++;
            checkCounter(this.count, this.check);
        } else if (object instanceof Note) {
            table = createNoteTable((Note) object);
            table.setSpacingBefore(7.0f);
            CustomLogs.printLog("PDFGenerateActivity", "---------- Add NoteTable");
            addTable(table);
            this.check++;
            checkCounter(this.count, this.check);
        }
    }

    private void checkCounter(int count1, int check1) throws DocumentException, IOException {
    }

    *//*protected void createPDF() throws IOException {
        this.document = new Document(PageSize.A4, 20.0f, 20.0f, 20.0f, 150.0f);
        try {
            String path = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/Prescription").toString();
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Log.d("PDFCreator", "PDF Path: " + path);
            this.docWriter = PdfWriter.getInstance(this.document, new FileOutputStream(new File(dir, "prescription.pdf")));
            this.docWriter.setBoxSize("art", new Rectangle(TabSettings.DEFAULT_TAB_INTERVAL, 54.0f, 559.0f, 788.0f));
            this.docWriter.setPageEvent(new HeaderFooter());
            this.document.open();
            cb = this.docWriter.getDirectContent();
            initializeFonts();
            Log.d("OK", "done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e2) {
            e2.printStackTrace();
        }
    }*//*

    private PdfPTable createHeaderTable() throws DocumentException, MalformedURLException, IOException {
        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setBorder(0);
        table.setTotalWidth(this.document.right() - 20.0f);
        table.setWidthPercentage(100.0f);
        table.setWidths(new int[]{6, 5, 4});
        BaseColor myColor = WebColors.getRGBColor("#EBEBEB");
        PdfPCell cell = new PdfPCell(new PdfPCell(setImage((int) C0081R.drawable.rx600), false));
        cell.setPadding(4.0f);
        cell.setVerticalAlignment(1);
        cell.setFixedHeight(25.0f);
        cell.setBackgroundColor(myColor);
        cell.setBorder(0);
        cell.setColspan(4);
        table.addCell(cell);
        return table;
    }

    public String getValidField(String str) {
        if (str == null || str.length() == 0) {
            return "-";
        }
        return str;
    }

    private PdfPTable createPatientInfoTable(Patient patient, PrescriptionDetails pdeatils) throws DocumentException, IOException {
        BaseColor myColor = WebColors.getRGBColor("#EBEBEB");
        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        int[] iArr = new int[3];
        table.setWidths(new int[]{6, 5, 4});
        Font font = new Font(setRobotoBold(), 14.0f, 0, BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase("Prescription Serial No.: " + pdeatils.getPrescription_id(), font));
        cell.setColspan(2);
        cell.setVerticalAlignment(1);
        cell.setFixedHeight(25.0f);
        cell.setBackgroundColor(myColor);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Date: " + pdeatils.getPrescription_date(), font));
        cell.setBorder(0);
        cell.setHorizontalAlignment(2);
        cell.setBackgroundColor(myColor);
        cell.setVerticalAlignment(1);
        cell.setFixedHeight(20.0f);
        cell.setColspan(5);
        table.addCell(cell);
        table.setSpacingAfter(10.0f);
        cell = new PdfPCell(new Paragraph(patient.getfName()));
        PdfPTable nestedTable = new PdfPTable(3);
        nestedTable.setWidthPercentage(100.0f);
        nestedTable.getDefaultCell().setBorder(0);
        iArr = new int[3];
        nestedTable.setWidths(new int[]{1, 10, 1});
        if (TextUtils.equals(Gender.MALE.getGender(), patient.getGender())) {
            this.myImg = setImage(C0081R.drawable.male_dark);
        } else {
            this.myImg = setImage(C0081R.drawable.female_dark);
        }
        PdfPCell cellImage = new PdfPCell(this.myImg, false);
        cell.setVerticalAlignment(1);
        cellImage.setBorder(0);
        cellImage.setPaddingTop(6.0f);
        cellImage.setPaddingLeft(BaseField.BORDER_WIDTH_THICK);
        cellImage.setPaddingBottom(BaseField.BORDER_WIDTH_THICK);
        nestedTable.addCell(cellImage);
        cell = new PdfPCell(new Paragraph(patient.getfName() + " " + patient.getlName(), new Font(setRobotoBold(), 14.0f, 0, BaseColor.BLACK)));
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
        this.myImg = setImage(C0081R.drawable.phone);
        PdfPCell cellPhoneImage = new PdfPCell(this.myImg, false);
        cellPhoneImage.setBorder(0);
        nestedTable2.addCell(PdfObject.NOTHING);
        nestedTable2.addCell(cellPhoneImage);
        cell = new PdfPCell(new Paragraph(getValidField(patient.getPhone()), new Font(setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
        cell.setBorder(0);
        cell.setVerticalAlignment(4);
        nestedTable2.addCell(cell);
        nestedTable2.getDefaultCell().setBorder(0);
        cell.addElement(nestedTable2);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("mail"));
        PdfPTable pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100.0f);
        pdfPTable.getDefaultCell().setBorder(0);
        iArr = new int[2];
        pdfPTable.setWidths(new int[]{1, 8});
        this.myImg = setImage(C0081R.drawable.mail);
        PdfPCell cellMailImage = new PdfPCell(this.myImg, false);
        cellMailImage.setVerticalAlignment(4);
        cellMailImage.setBorder(0);
        pdfPTable.addCell(cellMailImage);
        cell = new PdfPCell(new Paragraph(getValidField(patient.getEmail()), new Font(setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
        cell.setColspan(2);
        cell.setVerticalAlignment(4);
        cell.setBorder(0);
        pdfPTable.addCell(cell);
        pdfPTable.getDefaultCell().setBorder(0);
        cell.addElement(pdfPTable);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Address"));
        PdfPTable nastedAddressTable = new PdfPTable(3);
        nastedAddressTable.setWidthPercentage(100.0f);
        nastedAddressTable.getDefaultCell().setBorder(0);
        iArr = new int[3];
        nastedAddressTable.setWidths(new int[]{1, 1, 13});
        this.myImg = setImage(C0081R.drawable.address);
        PdfPCell cellAddressImage = new PdfPCell(this.myImg, false);
        cellAddressImage.setVerticalAlignment(1);
        cellAddressImage.setBorder(0);
        nastedAddressTable.addCell(PdfObject.NOTHING);
        nastedAddressTable.addCell(cellAddressImage);
        cell = new PdfPCell(new Paragraph(getValidField(patient.getAddress()), new Font(setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
        cell.setColspan(3);
        cell.setVerticalAlignment(4);
        cell.setBorder(0);
        nastedAddressTable.addCell(cell);
        nastedAddressTable.getDefaultCell().setBorder(0);
        cell.addElement(nastedAddressTable);
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
        cell = new PdfPCell(new Paragraph(Gender.MALE.getGender(patient.getGender()), setValueFont()));
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
        cell = new PdfPCell(new Paragraph(DateUtils.getYearMonthsDays(patient.getbDate()), setValueFont()));
        cell.setVerticalAlignment(1);
        cell.setBorder(0);
        nestedAgeTable.addCell(cell);
        cell.addElement(nestedAgeTable);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Weight"));
        pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100.0f);
        pdfPTable.getDefaultCell().setBorder(0);
        iArr = new int[2];
        pdfPTable.setWidths(new int[]{3, 5});
        cell = new PdfPCell(new Paragraph("Weight", setKeyFont()));
        cell.setBorder(0);
        pdfPTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(patient.getWeight() + " kgs", setValueFont()));
        cell.setBorder(0);
        pdfPTable.addCell(cell);
        cell.addElement(pdfPTable);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Patient Note"));
        pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(100.0f);
        pdfPTable.getDefaultCell().setBorder(0);
        iArr = new int[3];
        pdfPTable.setWidths(new int[]{1, 1, 13});
        pdfPTable.addCell(PdfObject.NOTHING);
        cell = new PdfPCell(new Paragraph(patient.getNote(), new Font(setRobotoThin(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"))));
        cell.setColspan(3);
        cell.setBorder(0);
        pdfPTable.addCell(cell);
        nastedAddressTable.getDefaultCell().setBorder(0);
        cell.addElement(pdfPTable);
        cell.setBorder(0);
        table.addCell(cell);
        return table;
    }

    public Font setKeyFont() throws DocumentException, IOException {
        return new Font(setRobotoLight(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, WebColors.getRGBColor("#808080"));
    }

    public Font setValueFont() throws DocumentException, IOException {
        return new Font(setRobotoRegular(), (float) HtmlUtilities.DEFAULT_FONT_SIZE, 0, BaseColor.BLACK);
    }

    private Image setImage(int maleDark) throws BadElementException, MalformedURLException, IOException {
        float eighteen = getResources().getDimension(C0081R.dimen.v18dp);
        float twenty = getResources().getDimension(C0081R.dimen.v20dp);
        float sixsteen = getResources().getDimension(C0081R.dimen.v16dp);
        int w = (int) eighteen;
        int h = (int) eighteen;
        switch (maleDark) {
            case C0081R.drawable.address:
                w = (int) twenty;
                h = (int) eighteen;
                break;
            case C0081R.drawable.mail:
                w = (int) twenty;
                h = (int) sixsteen;
                break;
            case C0081R.drawable.male_dark:
                w = (int) twenty;
                h = (int) eighteen;
                break;
            case C0081R.drawable.phone:
                w = (int) eighteen;
                h = (int) eighteen;
                break;
            case C0081R.drawable.rx600:
                w = (int) twenty;
                h = (int) eighteen;
                break;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), maleDark);
        Bitmap.createScaledBitmap(bitmap, w, h, true).compress(CompressFormat.PNG, 100, stream);
        bitmap.recycle();
        return Image.getInstance(stream.toByteArray());
    }

    public PdfPTable createMedicineInfoTable(Medicine medicine) throws DocumentException, IOException {
        String str;
        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        int[] iArr = new int[3];
        table.setWidths(new int[]{6, 5, 4});
        PdfPCell cell = new PdfPCell(new Paragraph("MEDICINE"));
        PdfPTable nestedTable = new PdfPTable(2);
        nestedTable.setWidthPercentage(100.0f);
        nestedTable.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nestedTable.setWidths(new int[]{1, 10});
        PdfPCell cellImage = new PdfPCell(setImage((Object) medicine), false);
        cellImage.setBorder(0);
        cellImage.setPadding(4.0f);
        nestedTable.addCell(cellImage);
        PdfPCell cell2 = new PdfPCell(new Phrase(AppUtil.getMedicineNameInRequiredFormat(medicine), new Font(setRobotoBold(), 14.0f, 0, BaseColor.BLACK)));
        cell2.setBorder(0);
        cell2.setHorizontalAlignment(0);
        cell2.setVerticalAlignment(0);
        nestedTable.addCell(cell2);
        cell.setColspan(2);
        cell.addElement(nestedTable);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("Quantity"));
        PdfPTable nestedTable2 = new PdfPTable(2);
        nestedTable2.setWidthPercentage(100.0f);
        nestedTable2.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nestedTable2.setWidths(new int[]{3, 4});
        cell = new PdfPCell(new Paragraph("Quantity", setKeyFont()));
        cell.setBorder(0);
        nestedTable2.addCell(cell);
        cell = new PdfPCell(new Paragraph((medicine.getDispense_quantity()) + " " + medicine.getDispense_unit(), setValueFont()));
        cell.setPaddingBottom(5.0f);
        cell.setBorder(0);
        nestedTable2.addCell(cell);
        cell.addElement(nestedTable2);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("Dose"));
        PdfPTable nestedTable3 = new PdfPTable(2);
        nestedTable3.setWidthPercentage(100.0f);
        nestedTable3.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nestedTable3.setWidths(new int[]{1, 5});
        nestedTable3.addCell(PdfObject.NOTHING);
        PdfPTable nestedTableDose = new PdfPTable(2);
        nestedTableDose.setWidthPercentage(100.0f);
        nestedTableDose.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nestedTableDose.setWidths(new int[]{3, 7});
        PdfPCell cell1 = new PdfPCell(new Paragraph("Dose", setKeyFont()));
        cell1.setBorder(0);
        nestedTableDose.addCell(cell1);
        cell1 = new PdfPCell(new Paragraph(medicine.getDose() + " " + medicine.getDose_unit(), setValueFont()));
        cell1.setBorder(0);
        cell1.setBorder(0);
        nestedTableDose.addCell(cell1);
        cell1.addElement(nestedTableDose);
        nestedTable3.addCell(cell1);
        cell.addElement(nestedTable3);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("frequency"));
        PdfPTable nestedTable4 = new PdfPTable(2);
        nestedTable4.setWidthPercentage(100.0f);
        nestedTable4.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nestedTable4.setWidths(new int[]{3, 4});
        cell = new PdfPCell(new Paragraph("Frequency", setKeyFont()));
        cell.setBorder(0);
        nestedTable4.addCell(cell);
        cell = new PdfPCell(new Paragraph(medicine.getFrequency(), setValueFont()));
        cell.setBorder(0);
        nestedTable4.addCell(cell);
        cell.addElement(nestedTable4);
        cell.setBorder(0);
        table.addCell(cell);
        table.addCell(PdfObject.NOTHING);
        cell = new PdfPCell(new Phrase("Direction"));
        PdfPTable nastedAddressTable = new PdfPTable(2);
        nastedAddressTable.setWidthPercentage(100.0f);
        nastedAddressTable.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nastedAddressTable.setWidths(new int[]{1, 5});
        nastedAddressTable.addCell(PdfObject.NOTHING);
        StringBuilder stringBuilder = new StringBuilder();
        if (medicine.getAf_bf().equals(AppConstants.DOSE_AFTER_FOOD)) {
            str = "After Food";
        } else {
            str = "Before Food";
        }
        cell = new PdfPCell(new Paragraph(stringBuilder.append(str).toString()));
        cell.setBorder(0);
        nastedAddressTable.addCell(cell);
        cell.addElement(nastedAddressTable);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Duration"));
        PdfPTable nestedAgeTable = new PdfPTable(2);
        nestedAgeTable.setWidthPercentage(100.0f);
        nestedAgeTable.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nestedAgeTable.setWidths(new int[]{3, 4});
        cell = new PdfPCell(new Paragraph("Duration", setKeyFont()));
        cell.setBorder(0);
        nestedAgeTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(medicine.getDuration_in_days() + " days", setValueFont()));
        cell.setBorder(0);
        nestedAgeTable.addCell(cell);
        cell.addElement(nestedAgeTable);
        cell.setBorder(0);
        table.addCell(cell);
        table.addCell(" ");
        cell = new PdfPCell(new Phrase("Add"));
        PdfPTable nastedAddressTabl = new PdfPTable(2);
        nastedAddressTabl.setWidthPercentage(100.0f);
        nastedAddressTabl.getDefaultCell().setBorder(0);
        iArr = new int[2];
        nastedAddressTabl.setWidths(new int[]{1, 14});
        nastedAddressTabl.addCell(PdfObject.NOTHING);
        cell = new PdfPCell(new Paragraph(medicine.getDirections(), setValueFont()));
        cell.setBorder(0);
        cell.setColspan(3);
        nastedAddressTabl.addCell(cell);
        cell.addElement(nastedAddressTabl);
        cell.setBorder(0);
        table.addCell(cell);
        table.addCell(PdfObject.NOTHING);
        table.addCell(PdfObject.NOTHING);
        return table;
    }

    public PdfPTable createMedicalSupplyTable(MedicalSupply medicalSupply) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        table.setWidths(new int[]{6, 5, 4});
        PdfPCell cell = new PdfPCell(new Paragraph("Bandaid"));
        PdfPTable nestedTable = new PdfPTable(2);
        nestedTable.setWidthPercentage(100.0f);
        nestedTable.getDefaultCell().setBorder(0);
        nestedTable.setWidths(new int[]{1, 10});
        PdfPCell cellImage = new PdfPCell(setImage((Object) medicalSupply), false);
        cellImage.setPaddingTop(4.0f);
        cellImage.setBorder(0);
        nestedTable.addCell(cellImage);
        cell = new PdfPCell(new Paragraph(medicalSupply.getMedical_supply_name(), new Font(setRobotoBold(), 14.0f, 0, BaseColor.BLACK)));
        cell.setBorder(0);
        cell.setColspan(2);
        nestedTable.addCell(cell);
        cell.addElement(nestedTable);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("quantity"));
        PdfPTable nestedTable1 = new PdfPTable(2);
        nestedTable1.setWidthPercentage(100.0f);
        nestedTable1.getDefaultCell().setBorder(0);
        nestedTable1.setWidths(new int[]{3, 4});
        cell = new PdfPCell(new Paragraph("Quantity", setKeyFont()));
        cell.setBorder(0);
        nestedTable1.addCell(cell);
        cell = new PdfPCell(new Paragraph(AppUtil.getIntOrDuble(medicalSupply.getDispense_quantity().doubleValue()) + " " + medicalSupply.getDispense_unit(), setValueFont()));
        cell.setBorder(0);
        cell.setVerticalAlignment(1);
        cell.setPaddingBottom(7.0f);
        nestedTable1.addCell(cell);
        cell.addElement(nestedTable1);
        table.addCell(cell);
        new PdfPCell(new Paragraph("Bandaid")).setColspan(2);
        PdfPTable nestedTable3 = new PdfPTable(2);
        nestedTable3.setWidthPercentage(100.0f);
        nestedTable3.getDefaultCell().setBorder(0);
        nestedTable3.setWidths(new int[]{1, 14});
        nestedTable3.addCell(PdfObject.NOTHING);
        cell = new PdfPCell(new Paragraph(medicalSupply.getDirections(), setValueFont()));
        cell.setColspan(3);
        cell.setBorder(0);
        nestedTable3.addCell(cell);
        cell.addElement(nestedTable3);
        cell.setBorder(0);
        table.addCell(cell);
        new PdfPCell(new Paragraph("OtherNotes")).setColspan(2);
        PdfPTable otherNoteTable = new PdfPTable(2);
        otherNoteTable.setWidthPercentage(100.0f);
        otherNoteTable.getDefaultCell().setBorder(0);
        otherNoteTable.setWidths(new int[]{1, 14});
        otherNoteTable.addCell(PdfObject.NOTHING);
        cell = new PdfPCell(new Paragraph(medicalSupply.getOther_notes(), setValueFont()));
        cell.setColspan(3);
        cell.setBorder(0);
        otherNoteTable.addCell(cell);
        cell.addElement(otherNoteTable);
        cell.setBorder(0);
        table.addCell(cell);
        return table;
    }

    public PdfPTable createTestTable(Test test) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        table.setWidths(new int[]{6, 5, 3});
        PdfPCell cell = new PdfPCell(new Paragraph("Test"));
        PdfPTable nestedTable2 = new PdfPTable(2);
        nestedTable2.setWidthPercentage(100.0f);
        nestedTable2.getDefaultCell().setBorder(0);
        nestedTable2.setWidths(new int[]{1, 13});
        PdfPCell cellImage = new PdfPCell(setImage((Object) test), false);
        cellImage.setPadding(4.0f);
        cellImage.setBorder(0);
        nestedTable2.addCell(cellImage);
        cell = new PdfPCell(new Paragraph(test.getTest_name(), new Font(setRobotoBold(), 14.0f, 0, BaseColor.BLACK)));
        cell.setBorder(0);
        cell.setColspan(3);
        nestedTable2.addCell(cell);
        cell.addElement(nestedTable2);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("Test"));
        PdfPTable nestedTable = new PdfPTable(2);
        nestedTable.setWidthPercentage(100.0f);
        nestedTable.getDefaultCell().setBorder(0);
        nestedTable.setWidths(new int[]{1, 13});
        nestedTable.addCell(PdfObject.NOTHING);
        cell = new PdfPCell(new Paragraph(test.getDirections(), setValueFont()));
        cell.setColspan(3);
        cell.setBorder(0);
        nestedTable.addCell(cell);
        cell.addElement(nestedTable);
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("OtherNotes"));
        PdfPTable otherNoteTable = new PdfPTable(2);
        otherNoteTable.setWidthPercentage(100.0f);
        otherNoteTable.getDefaultCell().setBorder(0);
        otherNoteTable.setWidths(new int[]{1, 13});
        otherNoteTable.addCell(PdfObject.NOTHING);
        cell = new PdfPCell(new Paragraph(test.getOther_notes(), setValueFont()));
        cell.setColspan(3);
        cell.setBorder(0);
        otherNoteTable.addCell(cell);
        cell.addElement(otherNoteTable);
        cell.setBorder(0);
        table.addCell(cell);
        return table;
    }

    public PdfPTable createNoteTable(Note note) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100.0f);
        table.setWidths(new int[]{6, 5, 3});
        PdfPCell cell = new PdfPCell(new Paragraph("NOTE"));
        PdfPTable nestedTable = new PdfPTable(2);
        nestedTable.setWidthPercentage(100.0f);
        nestedTable.getDefaultCell().setBorder(0);
        nestedTable.setWidths(new int[]{1, 14});
        PdfPCell cellImage = new PdfPCell(setImage((Object) note), false);
        cellImage.setPaddingTop(4.0f);
        cellImage.setBorder(0);
        nestedTable.addCell(cellImage);
        cell = new PdfPCell(new Paragraph(note.getNote(), new Font(setRobotoBold(), 14.0f, 0, BaseColor.BLACK)));
        cell.setColspan(3);
        cell.setBorder(0);
        nestedTable.addCell(cell);
        cell.addElement(nestedTable);
        cell.setBorder(0);
        table.addCell(cell);
        return table;
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

    protected static void createHeadings(PdfContentByte cb, float x, float y, String text) throws DocumentException, IOException {
        cb.beginText();
        cb.setFontAndSize(setRobotoRegular(), HtmlUtilities.DEFAULT_FONT_SIZE);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }

    public Image setImage(Object obj) throws IOException, BadElementException {
        Bitmap bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int w = 18;
        int h = 18;
        if (obj instanceof Note) {
            bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), C0081R.drawable.note);
            w = 18;
            h = 20;
        } else if (obj instanceof Medicine) {
            bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), C0081R.drawable.cap_gray_small);
        } else if (obj instanceof MedicalSupply) {
            bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), C0081R.drawable.addbag);
        } else {
            bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), C0081R.drawable.test_small_gray);
        }
        Bitmap.createScaledBitmap(bitmap, w, h, true).compress(CompressFormat.PNG, 100, stream);
        bitmap.recycle();
        return Image.getInstance(stream.toByteArray());
    }

    private void checkForAnyPendingtableToWrite() {
        if (this.pendingTable != null) {
            addTable(this.pendingTable);
            this.pendingTable = null;
        }
    }

    public void onPdfGenerateSuccess(String pdfAbsolutePath) {
    }

    public static BaseFont setRobotoBold() throws DocumentException, IOException {
        return BaseFont.createFont("assets/fonts/Roboto-Bold.ttf", "Cp1252", false);
    }

    public static BaseFont setRobotoMidium() throws DocumentException, IOException {
        return BaseFont.createFont("assets/fonts/Roboto-Medium.ttf", "Cp1252", false);
    }

    public static BaseFont setRobotoThin() throws DocumentException, IOException {
        return BaseFont.createFont("assets/fonts/Roboto-Thin.ttf", "Cp1252", false);
    }

    public static BaseFont setRobotoRegular() throws DocumentException, IOException {
        return BaseFont.createFont("assets/fonts/Roboto-Regular.ttf", "Cp1252", false);
    }

    public static BaseFont setRobotoLight() throws DocumentException, IOException {
        return BaseFont.createFont("assets/fonts/Roboto-Light.ttf", "Cp1252", false);
    }

    public void onPdfGenerateError() {
    }*/
}
