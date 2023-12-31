package com.gestion.empleados.controlador;

import com.gestion.empleados.entidades.*;
import com.gestion.empleados.servicio.EntidadesService;
import com.gestion.empleados.servicio.EpsService;
import com.gestion.empleados.servicio.IndigenasService;
import com.gestion.empleados.util.paginacion.PageRender;
import com.gestion.empleados.util.reportes.IndigenasExporterExcel;
import com.lowagie.text.DocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class IndigenasController {

    @Autowired
    private IndigenasService indigenasService;

    @Autowired
    private EpsService epsService;

    @Autowired
    private EntidadesService entidadesService;

    @GetMapping("/certificadoIndigena/{id}")
    public ResponseEntity<byte[]> getCertificado(@PathVariable Long id) throws IOException, InvalidFormatException {
        Indigenas indigenas = indigenasService.findOne(id);

        String consecutivo = indigenas.getConsecutivo();
        String concatenar = indigenas.getPrimerApellido() + " " + indigenas.getSegundoApellido() + " " + indigenas.getPrimerNombre() + " " + indigenas.getSegundoNombre();
        String ultimosCuatroDigitos = "";

        if (consecutivo.length() >= 4) {
            ultimosCuatroDigitos = consecutivo.substring(consecutivo.length() - 4);
        }
        XWPFDocument document = new XWPFDocument();

        XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);

        XWPFParagraph imageParagraphh = header.createParagraph();

        imageParagraphh.setAlignment(ParagraphAlignment.CENTER);

        String imagePathh = "src/main/resources/templates/img/alcaldia.png";

        XWPFRun runn = imageParagraphh.createRun();
        runn.addPicture(new FileInputStream(imagePathh), XWPFDocument.PICTURE_TYPE_JPEG, "alcaldia.png", Units.toEMU(80), Units.toEMU(80));


        XWPFParagraph numCerti = document.createParagraph();
        numCerti.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun numCertiCentrado = numCerti.createRun();
        numCertiCentrado.setBold(true);
        numCertiCentrado.setFontSize(18);
        numCertiCentrado.setText("CERTIFICADO NUMERO: " + ultimosCuatroDigitos);
        numCertiCentrado.addCarriageReturn();

        XWPFParagraph titile = document.createParagraph();
        titile.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titlecentrado = numCerti.createRun();
        titlecentrado.setBold(true);
        titlecentrado.setFontSize(13);
        titlecentrado.setText("CERTIFICADO DE ASIGNACIÓN DE CÓDIGO DE IDENTIFICACIÓN\n" +
                "INDIGENAS");

        XWPFParagraph subtitile = document.createParagraph();
        subtitile.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun subtitlecentrado = numCerti.createRun();
        subtitlecentrado.setBold(true);
        subtitlecentrado.setText("EL SUSCRITO SUBSECRETARIO Y EL PROFESIONAL UNIVERSITARIO DE EL AREA ASEGURAMIENTO\n" +
                "Y CONTROL DE ATENCION EN SALUD");

        XWPFParagraph certifica = document.createParagraph();
        certifica.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun certificaCentrado = subtitile.createRun();
        certificaCentrado.setText("CERTIFICA QUE: ");
        certificaCentrado.addCarriageReturn();

        XWPFParagraph paragraphJustificado = document.createParagraph();
        paragraphJustificado.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun runJustificado = paragraphJustificado.createRun();

        runJustificado.setText("Que, La, " + indigenas.getEntidades().getDescripcion() + " a través de la funcionario (a) " + indigenas.getNombreFuncionario() + ", solicito la asignación de\n" +
                "código de identificación del usuario " + concatenar +  " , de fecha de nacimiento " + indigenas.getFechaNacimiento() +" y\n" +
                "perteneciente al grupo de población INDIGENAS a fecha de radicación " + indigenas.getFechaRegistro() + " Que, la ENTIDAD TERRITORIAL , consolidará y reportará la\n" +
                "Plataforma de Intercambio de Información PISIS del Sistema Integral de Información de la Protección Social-SISPRO.");

        XWPFParagraph paragraphIzquierda = document.createParagraph();
        paragraphIzquierda.setAlignment(ParagraphAlignment.LEFT); // Alinea el párrafo a la izquierda
        XWPFRun runIzquierda = paragraphIzquierda.createRun();

        runIzquierda.setText("Que, por lo anterior esta Secretaría de Salud ha asignado el código de identificación así:");
        runIzquierda.addCarriageReturn();

        XWPFParagraph espacio = document.createParagraph();
        espacio.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun espacio1 = paragraphIzquierda.createRun();

        espacio1.setText("");
        espacio1.addCarriageReturn();

        XWPFParagraph item1 = document.createParagraph();
        item1.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun1 = paragraphIzquierda.createRun();

        itemrun1.setText("NOMBRE DEL USUARIO: " + concatenar);
        itemrun1.addCarriageReturn();

        XWPFParagraph item2 = document.createParagraph();
        item2.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun2 = paragraphIzquierda.createRun();

        itemrun2.setText("FECHA DE NACIMIENTO: " + indigenas.getFechaNacimiento());
        itemrun2.addCarriageReturn();

        XWPFParagraph item3 = document.createParagraph();
        item3.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun3 = paragraphIzquierda.createRun();

        itemrun3.setText("TIPO DE DOCUMENTO: " + indigenas.getTipoDocumento());
        itemrun3.addCarriageReturn();

        XWPFParagraph item4 = document.createParagraph();
        item4.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun4 = paragraphIzquierda.createRun();

        itemrun4.setText("NUMERO DE DOCUMENTO: " + indigenas.getConsecutivo());
        itemrun4.addCarriageReturn();

        XWPFParagraph item5 = document.createParagraph();
        item5.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun5 = paragraphIzquierda.createRun();

        itemrun5.setText("NOMBRE DE POBLACION: " + "INDIGENA");
        itemrun5.addCarriageReturn();

        XWPFParagraph item6 = document.createParagraph();
        item6.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun6 = paragraphIzquierda.createRun();

        itemrun6.setText("TIPO DE POBLACION: " + "17");
        itemrun6.addCarriageReturn();

        XWPFParagraph item7 = document.createParagraph();
        item7.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun7 = paragraphIzquierda.createRun();

        itemrun7.setText("EAPB: " + indigenas.getEps().getDescripcion());
        itemrun7.addCarriageReturn();

        XWPFParagraph item7_1 = document.createParagraph();
        item7_1.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun7_1 = paragraphIzquierda.createRun();

        itemrun7_1.setText("DIRECCIÓN: " + indigenas.getDireccion());
        itemrun7_1.addCarriageReturn();

        XWPFParagraph item7_2 = document.createParagraph();
        item7_2.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun7_2 = paragraphIzquierda.createRun();

        itemrun7_2.setText("NOMBRE RESGUARDO: " + indigenas.getNombreResguardo());
        itemrun7_2.addCarriageReturn();


        XWPFParagraph espacio2 = document.createParagraph();
        espacio2.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun espacio21 = paragraphIzquierda.createRun();

        espacio21.setText("");
        espacio21.addCarriageReturn();

        XWPFParagraph item8 = document.createParagraph();
        item8.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun itemrun8 = paragraphIzquierda.createRun();

        itemrun8.setText("Lo anterior dando cumplimiento a la Resolución 4622 de 2016, y al Decreto 780 de 2016, Decreto\n" +
                "064 del 2020");
        itemrun8.addCarriageReturn();

        XWPFParagraph espacio3 = document.createParagraph();
        espacio3.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun espacio23 = paragraphIzquierda.createRun();

        espacio23.setText("");
        espacio23.addCarriageReturn();

        XWPFParagraph item9 = document.createParagraph();
        item9.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun itemrun9 = paragraphIzquierda.createRun();

        itemrun9.setText("Dada en San José de Cúcuta");
        itemrun9.addCarriageReturn();

        String imagePath = "src/main/resources/templates/img/Firma_pulido.png"; // Reemplaza con la ubicación de tu imagen
        XWPFParagraph imageParagraph = document.createParagraph();
        XWPFRun run = paragraphIzquierda.createRun();
        run.addPicture(new FileInputStream(imagePath), XWPFDocument.PICTURE_TYPE_PNG, "Firma_pulido.png", Units.toEMU(90), Units.toEMU(40));

        XWPFParagraph item10 = document.createParagraph();
        item10.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun itemrun10 = paragraphIzquierda.createRun();

        itemrun10.setText("");
        itemrun10.addCarriageReturn();

        XWPFParagraph item11 = document.createParagraph();
        item11.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun itemrun11 = paragraphIzquierda.createRun();

        itemrun11.setText("ISRAEL ALEJANDRO PULIDO ARIAS ");
        itemrun11.setFontSize(10);
        itemrun11.setBold(true);
        itemrun11.addCarriageReturn();

        XWPFParagraph item12 = document.createParagraph();
        item12.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun itemrun12 = paragraphIzquierda.createRun();

        itemrun12.setText("PROFESIONAL UNIVERSITARIO ASEGURAMIENTO");
        itemrun12.setFontSize(10);
        itemrun12.setBold(true);
        itemrun12.addCarriageReturn();

        XWPFParagraph item13 = document.createParagraph();
        item13.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun itemrun13 = paragraphIzquierda.createRun();

        itemrun13.setText("Proyecto: Israel Alejandro Pulido Arias- ingeniero de Sistemas Aseguramiento\n" +
                "Revisó y aprobó: Franklin Alexis Hernández Peñaloza - Subsecretario de Aseguramiento y Control en Salud\n");
        itemrun13.setFontSize(9);
        itemrun13.addCarriageReturn();

        XWPFParagraph item14 = document.createParagraph();
        item14.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun itemrun14 = paragraphIzquierda.createRun();

        itemrun14.setText("Correo E: " + "aseguramiento.salud@alcaldiadecucuta.gov.co");
        itemrun14.setUnderline(UnderlinePatterns.SINGLE);
        itemrun14.setFontSize(9);
        itemrun14.addCarriageReturn();

        int lastIndex = document.getParagraphs().size() - 1;
        while (lastIndex >= 0) {
            XWPFParagraph lastParagraph = document.getParagraphs().get(lastIndex);
            if (lastParagraph.getRuns().isEmpty() || lastParagraph.getText().trim().isEmpty()) {
                document.removeBodyElement(lastIndex);
                lastIndex--;
            } else {
                break;
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "certificado.docx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(outputStream.toByteArray());
    }

    @GetMapping("/buscarIndigenas")
    public String buscarIndigenas(@RequestParam(name = "input", required = false) String input, Model modelo) {
        List<Indigenas> indigenas;
        if (input != null && !input.isEmpty()) {
            indigenas = indigenasService.findByAtributos(input);
        } else {
            indigenas = indigenasService.findAll();
        }
        modelo.addAttribute("titulo", "Resultado de la Búsqueda");
        modelo.addAttribute("indigenas", indigenas);
        return "listarIndigenas";
    }

    @GetMapping("/formIndigenas")
    public String mostrarFormularioDeRegistrarIndigenas(Map<String,Object> modelo, Model model) {
        Indigenas indigenas = new Indigenas();
        List<Eps> listaEps = epsService.findAll();
        List<Entidades> listaEntidades = entidadesService.findAll();
        model.addAttribute("listaEps", listaEps);
        model.addAttribute("listaEntidades", listaEntidades);
        modelo.put("indigenas", indigenas);
        modelo.put("titulo", "Registro de Indígenas");
        return "formIndigenas";
    }

    @PostMapping("/formIndigenas")
    public String guardarIndigenas(@Valid Indigenas indigenas, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status, @RequestParam("archivoAdjunto") MultipartFile archivoAdjunto) {
        if (indigenas.getId() == null) {
            Indigenas existingPPL = indigenasService.findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
                    indigenas.getPrimerNombre(),
                    indigenas.getSegundoNombre(),
                    indigenas.getPrimerApellido(),
                    indigenas.getSegundoApellido(),
                    indigenas.getFechaNacimiento()
            );
            if (existingPPL != null) {
                flash.addFlashAttribute("error_message", "Registro ya existe en la base de datos");
                return "redirect:/formIndigenas?error=true";
            }
            String nuevoConsecutivo = indigenasService.obtenerUltimoConsecutivoDesdeBaseDeDatos();
            indigenas.setConsecutivo(nuevoConsecutivo);
        }
        if (!archivoAdjunto.isEmpty()) {
            try {
                String tipoMIME = archivoAdjunto.getContentType();

                if (!tipoMIME.equals(MediaType.APPLICATION_PDF_VALUE)) {
                    flash.addFlashAttribute("error_message", "El archivo adjunto debe ser un PDF.");
                    return "redirect:/formIndigenas?error_doc=true";
                }
                byte[] bytesArchivo = archivoAdjunto.getBytes();
                indigenas.setArchivoAdjunto(bytesArchivo);
            } catch (IOException e) {
                flash.addFlashAttribute("error_message", "Error al procesar el archivo adjunto.");
                return "redirect:/formIndigenas?error_doc=true";
            }
        }
        int edad = calcularEdad(indigenas.getFechaNacimiento());
        if (edad >= 18) {
            indigenas.setTipoDocumento("AS");
        } else {
            indigenas.setTipoDocumento("MS");
        }
        String mensaje = (indigenas.getId() != null) ? "Indigena ha sido editado con éxito" : "Indigena registrado con éxito";
        indigenasService.save(indigenas);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listarIndigenas";
    }


    @GetMapping("/editIndigenas/{id}")
    public String editarIndigenas(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash,Model model) {
        Indigenas indigenas = indigenasService.findOne(id);
        List<Eps> listaEps = epsService.findAll();
        List<Entidades> listaEntidades = entidadesService.findAll();
        if (indigenasService != null) {
            model.addAttribute("listaEps", listaEps);
            model.addAttribute("listaEntidades", listaEntidades);
            modelo.put("indigenas", indigenas);
            modelo.put("titulo", "Actualización de Indígenas");
        } else {
            flash.addFlashAttribute("error", "El ID del Indigena no existe en la base de datos");
            return "redirect:/listarIndigenas";
        }

        return "formIndigenas";
    }

    private int calcularEdad(Date fechaNacimiento) {
        Calendar fechaNac = Calendar.getInstance();
        fechaNac.setTime(fechaNacimiento);
        Calendar fechaActual = Calendar.getInstance();
        int edad = fechaActual.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
        if (fechaActual.get(Calendar.DAY_OF_YEAR) < fechaNac.get(Calendar.DAY_OF_YEAR)) {
            edad--;
        }
        return edad;
    }

    @GetMapping({"/", "/listarIndigenas"})
    public String listarIndigenas(Model modelo) {
        List<Indigenas> indigenas = indigenasService.findAll();
        Date fechaActual = new Date();
        for (Indigenas ind : indigenas) {
            Date fechaRegistro = ind.getFechaRegistro();
            Calendar calendarRegistro = Calendar.getInstance();
            calendarRegistro.setTime(fechaRegistro);
            calendarRegistro.add(Calendar.MONTH, 4);
            if (fechaActual.after(calendarRegistro.getTime())) {
                ind.setAlerta(true);
            } else {
                ind.setAlerta(false);
            }
        }
        modelo.addAttribute("titulo", "Agregar Indigena");
        modelo.addAttribute("indigenas", indigenas);
        modelo.addAttribute("page", null);
        boolean mostrarAlerta = indigenas.stream().anyMatch(Indigenas::isAlerta);
        modelo.addAttribute("mostrarAlerta", mostrarAlerta);
        return "listarIndigenas";
    }

    @GetMapping("/descargarIndigenas/{id}")
    public ResponseEntity<byte[]> descargarICBF(@PathVariable Long id) {
        Indigenas indigenas = indigenasService.findOne(id);
        if (indigenas != null && indigenas.getArchivoAdjunto() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "archivo_adjunto_" + id + ".pdf");
            return new ResponseEntity<>(indigenas.getArchivoAdjunto(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/eliminarIndigenas/{id}")
    public String eliminarIndigenas(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if(id > 0) {
            indigenasService.delete(id);
            flash.addFlashAttribute("success", "Eliminado con exito");
        }
        return "redirect:/listarIndigenas";
    }

    @GetMapping("/exportarIndigenasExcel")
    public void exportarListadoDeIndigenasEnExcel(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Indigenas_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<Indigenas> indigenas = indigenasService.findAll();

        IndigenasExporterExcel exporter = new IndigenasExporterExcel(indigenas);
        exporter.exportar(response);
    }
}
