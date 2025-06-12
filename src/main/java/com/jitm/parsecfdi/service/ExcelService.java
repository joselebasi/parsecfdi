package com.jitm.parsecfdi.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.jitm.parsecfdi.pojo.Cfdi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {

    public ByteArrayInputStream writeExcel(String sheetName, ArrayList<Cfdi> lsCfdi) throws FileNotFoundException, IOException{

        //Create workbook in .xlsx format
        XSSFWorkbook workbook = new XSSFWorkbook();
        //For .xsl workbooks use new HSSFWorkbook();
        //Create Sheet
        Sheet sh = workbook.createSheet(sheetName);
        //Create top row with column headings
        //String[] columnHeadings = {"Fecha", "Emisor", "Regimen Fiscal", "RFC","Descripcion", "Importe", "IVA","Forma Pago","Metodo Pago", "Subtotal", "Total", "XML"};
        String[] columnHeadings = {"Fecha", "Emisor", "Regimen Fiscal", "RFC","Descripcion", "Importe", "IVA","Forma Pago","Metodo Pago", "XML"};

        //We want to make it bold with a foreground color.
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)12);
        headerFont.setColor(IndexedColors.BLACK.index);
        //Create a CellStyle with the font
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        //Create the header row
        Row headerRow = sh.createRow(0);
        //Iterate over the column headings to create columns
        for(int i=0;i<columnHeadings.length;i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeadings[i]);
            cell.setCellStyle(headerStyle);
        }
        //Freeze Header Row
        sh.createFreezePane(0, 1);
        CreationHelper creationHelper= workbook.getCreationHelper();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("MM/dd/yyyy"));
        int rownum =1;

        CellStyle style = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("0.000")); // custom number format
        for(Cfdi r : lsCfdi) {  
            
            //System.out.println("rownum-before"+(rownum));
            Row row = sh.createRow(rownum++);
            //System.out.println("rownum-after"+(rownum));
            row.createCell(0).setCellValue(r.getFecha());
            row.createCell(1).setCellValue(r.getEmisorNombre());
            row.createCell(2).setCellValue(r.getEmisorRegimenFiscal());
            row.createCell(3).setCellValue(r.getEmisorRfc());
            row.createCell(4).setCellValue(r.getDescripcionFactura());
            Cell cImporte = row.createCell(5);                
            cImporte.setCellValue(Double.parseDouble(r.getImporte()));
            cImporte.setCellStyle(style);

            Cell cIva = row.createCell(6);                
            cIva.setCellValue(Double.parseDouble(r.getIva()));
            cIva.setCellStyle(style);

            row.createCell(7).setCellValue(r.getFormapago());
            row.createCell(8).setCellValue(r.getMetodoPago());

            //Cell cSubTotal = row.createCell(9);                
            //cSubTotal.setCellValue(Double.parseDouble(r.getSubtotal()));
            //cSubTotal.setCellStyle(style);

            //Cell cTotal = row.createCell(10);                
            //cTotal.setCellValue(Double.parseDouble(r.getTotal()));
            //cTotal.setCellStyle(style);

            row.createCell(9).setCellValue(r.getXml());
        }
        //Autosize columns
        for(int i=0;i<columnHeadings.length;i++) {
            sh.autoSizeColumn(i);
        }
        //Write the output to file
        //FileOutputStream fileOut = new FileOutputStream("/home/acig/Documents/"+fileName);
        //workbook.write(fileOut);
        //fileOut.close();
        //workbook.close();
        //System.out.println("Completed");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    
}

public ByteArrayInputStream writeExcelTotal(String sheetName, ArrayList<Cfdi> lsCfdi) throws FileNotFoundException, IOException{

        //Create workbook in .xlsx format
        XSSFWorkbook workbook = new XSSFWorkbook();
        //For .xsl workbooks use new HSSFWorkbook();
        //Create Sheet
        Sheet sh = workbook.createSheet(sheetName);
        //Create top row with column headings
        String[] columnHeadings = {"Fecha", "Emisor", "Regimen Fiscal Emisor", "RFC Emisor",  "Receptor", "Regimen Fiscal Receptor", "RFC Receptor", "Uso CFDI", "Total", "IVA", "Retencion", "Subtotal", "Forma Pago", "Metodo Pago", "XML"};
        //We want to make it bold with a foreground color.
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)12);
        headerFont.setColor(IndexedColors.BLACK.index);
        //Create a CellStyle with the font
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        //Create the header row
        Row headerRow = sh.createRow(0);
        //Iterate over the column headings to create columns
        for(int i=0;i<columnHeadings.length;i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeadings[i]);
            cell.setCellStyle(headerStyle);
        }
        //Freeze Header Row
        sh.createFreezePane(0, 1);
        CreationHelper creationHelper= workbook.getCreationHelper();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("MM/dd/yyyy"));

        CellStyle style = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("0.000")); // custom number format

        int rownum =1;
        for(Cfdi r : lsCfdi) {
            //System.out.println("rownum-before"+(rownum));
            Row row = sh.createRow(rownum++);
            //System.out.println("rownum-after"+(rownum));
            row.createCell(0).setCellValue(r.getFecha());
            row.createCell(1).setCellValue(r.getEmisorNombre());
            row.createCell(2).setCellValue(r.getEmisorRegimenFiscal());
            row.createCell(3).setCellValue(r.getEmisorRfc());
            row.createCell(4).setCellValue(r.getReceptorNombre());
            row.createCell(5).setCellValue(r.getReceptorRegimenFiscal());
            row.createCell(6).setCellValue(r.getReceptorRfc());
            row.createCell(7).setCellValue(r.getUsoCFDI());

            Cell cTotal = row.createCell(8); 
            Double dTotal = r.getTotal().isEmpty()?Double.parseDouble("0.00"):Double.parseDouble(r.getTotal());             
            cTotal.setCellValue(dTotal);
            cTotal.setCellStyle(style);

            Cell cIva = row.createCell(9);
            Double dIva = r.getIva().isEmpty()?Double.parseDouble("0.00"):Double.parseDouble(r.getIva());                
            cIva.setCellValue(dIva);
            cIva.setCellStyle(style);

            Cell cIvaRetenido = row.createCell(10);
            Double dIvaRetenido = r.getIvaRetenido().isEmpty()?Double.parseDouble("0.00"):Double.parseDouble(r.getIvaRetenido());                
            cIvaRetenido.setCellValue(dIvaRetenido);
            cIvaRetenido.setCellStyle(style);

            Cell cSubTotal = row.createCell(11);                
            //cSubTotal.setCellValue(r.getSubtotal().isEmpty()?Double.parseDouble("0.00"):Double.parseDouble(r.getSubtotal()));
            cSubTotal.setCellValue(new BigDecimal(dTotal).subtract(new BigDecimal(dIva)).doubleValue());
            cSubTotal.setCellStyle(style);
            
            row.createCell(12).setCellValue(r.getFormapago());
            row.createCell(13).setCellValue(r.getMetodoPago());
            row.createCell(14).setCellValue(r.getXml());
        }
        //Autosize columns
        for(int i=0;i<columnHeadings.length;i++) {
            sh.autoSizeColumn(i);
        }
        //Write the output to file
        //FileOutputStream fileOut = new FileOutputStream("/home/acig/Documents/"+fileName);
        //workbook.write(fileOut);
        //fileOut.close();
        //workbook.close();
        //System.out.println("Completed");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    
}
    
}
