package com.gestion.empleados.util.reportes;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HabitantecalleExporterPDF {


    public static byte[] generarCertificado(String numeroCertificado, String primerNombre, String segundoNomre, String primerApellido, String segundoApellido, String sexo, Date fechaNacimiento, String tipoDocumento) throws Exception {

        // Crear un documento PDF
        Document documento = new Document();
        documento.open();

        // Agregar el contenido del certificado al documento
        Paragraph parrafo = new Paragraph("Certificado de asignación de código de identificación");
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(parrafo);

        parrafo = new Paragraph("Número de certificado: " + numeroCertificado);
        parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        documento.add(parrafo);

        parrafo = new Paragraph("Fecha de emisión: " + primerNombre);
        parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        documento.add(parrafo);

        parrafo = new Paragraph("Nombre del usuario: " + segundoNomre);
        parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        documento.add(parrafo);

        parrafo = new Paragraph("Fecha de nacimiento: " + primerApellido);
        parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        documento.add(parrafo);

        parrafo = new Paragraph("Tipo de documento: " + segundoApellido);
        parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        documento.add(parrafo);

        parrafo = new Paragraph("Número de documento: " + sexo);
        parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        documento.add(parrafo);

        // Formatea la fecha en un formato legible, por ejemplo, "dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaNacimientoStr = dateFormat.format(fechaNacimiento);

        parrafo = new Paragraph("Fecha de nacimiento: " + fechaNacimientoStr);
        parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        documento.add(parrafo);

        parrafo = new Paragraph("Tipo de población: " + tipoDocumento);
        parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        documento.add(parrafo);

        // Cerrar el documento
        documento.close();

        // Crear un array de bytes para almacenar el documento
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Escribir el documento en el array de bytes
        PdfWriter.getInstance(documento, baos);

        // Obtener el contenido del documento en formato PDF
        byte[] certificado = baos.toByteArray();

        return certificado;
    }
}
