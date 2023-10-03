package com.gestion.empleados.util.reportes;

import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.entidades.HabitanteCalle;
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

public class HabitantecalleExporterExcel {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;

    private List<HabitanteCalle> listaHabitantescalle;

    public  HabitantecalleExporterExcel(List<HabitanteCalle> listaHabitantescalle) {
        this.listaHabitantescalle = listaHabitantescalle;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("HabitantesCalle");
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

    }

    private void escribirDatosDeLaTabla() {
        int nueroFilas = 1;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        for(HabitanteCalle habitanteCalle : listaHabitantescalle) {
            Row fila = hoja.createRow(nueroFilas ++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(habitanteCalle.getId());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(habitanteCalle.getPrimerNombre());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(habitanteCalle.getSegundoNombre());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(habitanteCalle.getPrimerApellido());
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(habitanteCalle.getSegundoApellido());
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(habitanteCalle.getFechaNacimiento());
            hoja.autoSizeColumn(5);
            celda.setCellStyle(estilo);

            celda = fila.createCell(6);
            celda.setCellValue(habitanteCalle.getSexo());
            hoja.autoSizeColumn(6);
            celda.setCellStyle(estilo);

            celda = fila.createCell(7);
            celda.setCellValue(habitanteCalle.getConsecutivo());
            hoja.autoSizeColumn(7);
            celda.setCellStyle(estilo);

            celda = fila.createCell(8);
            celda.setCellValue(habitanteCalle.getTipoDocumento());
            hoja.autoSizeColumn(8);
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
