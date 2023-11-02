package com.gestion.empleados.util.reportes;

import com.gestion.empleados.entidades.HabitanteCalle;
import com.gestion.empleados.entidades.PoblacionPrivada;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PPLExporterExcel {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;

    private List<PoblacionPrivada> listaPPL;

    public  PPLExporterExcel(List<PoblacionPrivada> listaPPL) {
        this.listaPPL = listaPPL;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("PoblacionPrivada");
    }

    private void escribirCabeceraDeTabla() {
        Row fila = hoja.createRow(0);

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(16);
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);
        celda.setCellValue("ID");
        celda.setCellStyle(estilo);

        celda = fila.createCell(1);
        celda.setCellValue("Pirmer Nombre");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Segundo Nombre");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("Primer Apellido");
        celda.setCellStyle(estilo);

        celda = fila.createCell(4);
        celda.setCellValue("Segundo Apellido");
        celda.setCellStyle(estilo);

        celda = fila.createCell(5);
        celda.setCellValue("Fecha de Nacimiento");
        celda.setCellStyle(estilo);

        celda = fila.createCell(6);
        celda.setCellValue("Sexo");
        celda.setCellStyle(estilo);

        celda = fila.createCell(7);
        celda.setCellValue("Consecutivo");
        celda.setCellStyle(estilo);

        celda = fila.createCell(8);
        celda.setCellValue("Tipo documento");
        celda.setCellStyle(estilo);

        celda = fila.createCell(9);
        celda.setCellValue("Tipo documento Actual");
        celda.setCellStyle(estilo);

        celda = fila.createCell(10);
        celda.setCellValue("Numero documento Actual");
        celda.setCellStyle(estilo);



    }

    private void escribirDatosDeLaTabla() {
        int nueroFilas = 1;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        for(PoblacionPrivada poblacionPrivada : listaPPL) {
            Row fila = hoja.createRow(nueroFilas ++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(poblacionPrivada.getId());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(poblacionPrivada.getPrimerNombre());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(poblacionPrivada.getSegundoNombre());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(poblacionPrivada.getPrimerApellido());
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(poblacionPrivada.getSegundoApellido());
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(poblacionPrivada.getFechaNacimiento());
            hoja.autoSizeColumn(5);
            celda.setCellStyle(estilo);

            celda = fila.createCell(6);
            celda.setCellValue(poblacionPrivada.getSexo());
            hoja.autoSizeColumn(6);
            celda.setCellStyle(estilo);

            celda = fila.createCell(7);
            celda.setCellValue(poblacionPrivada.getConsecutivo());
            hoja.autoSizeColumn(7);
            celda.setCellStyle(estilo);

            celda = fila.createCell(8);
            celda.setCellValue(poblacionPrivada.getTipoDocumento());
            hoja.autoSizeColumn(8);
            celda.setCellStyle(estilo);

            celda = fila.createCell(9);
            celda.setCellValue(poblacionPrivada.getTipoDocumentoActual());
            hoja.autoSizeColumn(9);
            celda.setCellStyle(estilo);

            celda = fila.createCell(10);
            celda.setCellValue(poblacionPrivada.getNumeroDocumentoActual());
            hoja.autoSizeColumn(10);
            celda.setCellStyle(estilo);
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        escribirCabeceraDeTabla();
        escribirDatosDeLaTabla();

        ServletOutputStream outPutStream = response.getOutputStream();
        libro.write(outPutStream);

        libro.close();
        outPutStream.close();
    }
}
