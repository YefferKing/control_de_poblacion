package com.gestion.empleados.controlador;

import com.gestion.empleados.entidades.*;
import com.gestion.empleados.servicio.AdultosService;
import com.gestion.empleados.servicio.EntidadesService;
import com.gestion.empleados.servicio.EpsService;
import com.gestion.empleados.servicio.MenoresICBFService;
import com.gestion.empleados.util.paginacion.PageRender;
import com.gestion.empleados.util.reportes.AdultosExporterExcel;
import com.gestion.empleados.util.reportes.MenoresICBFExporterExcel;
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
public class MenoresICBFController {

    @Autowired
    private MenoresICBFService menoresICBFService;

    @Autowired
    private EpsService epsService;

    @Autowired
    private EntidadesService entidadesService;

    @GetMapping("/certificadoMenoresICBF/{id}")
    public ResponseEntity<byte[]> getCertificado(@PathVariable Long id) throws IOException, InvalidFormatException {
        MenoresICBF menoresICBF = menoresICBFService.findOne(id);

        String consecutivo = menoresICBF.getConsecutivo();
        String concatenar = menoresICBF.getPrimerApellido() + " " + menoresICBF.getSegundoApellido() + " " + menoresICBF.getPrimerNombre() + " " + menoresICBF.getSegundoNombre();
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
                "MENORES ICBF");

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

        runJustificado.setText("Que, La, " + menoresICBF.getEntidades().getDescripcion() + " a través de la funcionario (a) " + menoresICBF.getNombreFuncionario() + ", solicito la asignación de\n" +
                "código de identificación del usuario " + concatenar +  " , de fecha de nacimiento " + menoresICBF.getFechaNacimiento() +" y\n" +
                "perteneciente al grupo de población MENORES ICBF a fecha de radicación " + menoresICBF.getFechaRegistro() + " Que, la ENTIDAD TERRITORIAL , consolidará y reportará la\n" +
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

        itemrun2.setText("FECHA DE NACIMIENTO: " + menoresICBF.getFechaNacimiento());
        itemrun2.addCarriageReturn();

        XWPFParagraph item3 = document.createParagraph();
        item3.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun3 = paragraphIzquierda.createRun();

        itemrun3.setText("TIPO DE DOCUMENTO: " + menoresICBF.getTipoDocumento());
        itemrun3.addCarriageReturn();

        XWPFParagraph item4 = document.createParagraph();
        item4.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun4 = paragraphIzquierda.createRun();

        itemrun4.setText("NUMERO DE DOCUMENTO: " + menoresICBF.getConsecutivo());
        itemrun4.addCarriageReturn();

        XWPFParagraph item5 = document.createParagraph();
        item5.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun5 = paragraphIzquierda.createRun();

        itemrun5.setText("NOMBRE DE POBLACION: " + "MENORES ICBF");
        itemrun5.addCarriageReturn();

        XWPFParagraph item6 = document.createParagraph();
        item6.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun6 = paragraphIzquierda.createRun();

        itemrun6.setText("TIPO DE POBLACION: " + "2");
        itemrun6.addCarriageReturn();

        XWPFParagraph item7 = document.createParagraph();
        item7.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun itemrun7 = paragraphIzquierda.createRun();

        itemrun7.setText("EAPB: " + menoresICBF.getEps().getDescripcion());
        itemrun7.addCarriageReturn();

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

    @GetMapping("/buscarMenoresICBF")
    public String buscarMenoresICBF(@RequestParam(name = "input", required = false) String input, Model modelo) {
        List<MenoresICBF> menoresICBF;
        if (input != null && !input.isEmpty()) {
            menoresICBF = menoresICBFService.findByAtributos(input);
        } else {
            menoresICBF = menoresICBFService.findAll();
        }
        modelo.addAttribute("titulo", "Resultado de la Búsqueda");
        modelo.addAttribute("menoresICBF", menoresICBF);
        return "listarMenoresICBF";
    }

    @GetMapping("/formMenoresICBF")
    public String mostrarFormularioDeRegistrarMenoresICBF(Map<String,Object> modelo, Model model) {
        MenoresICBF menoresICBF = new MenoresICBF();
        List<Eps> listaEps = epsService.findAll();
        List<Entidades> listaEntidades = entidadesService.findAll();
        model.addAttribute("listaEps", listaEps);
        model.addAttribute("listaEntidades", listaEntidades);
        modelo.put("menoresICBF", menoresICBF);
        modelo.put("titulo", "Registro de Menores ICBF");
        return "formMenoresICBF";
    }

    @PostMapping("/formMenoresICBF")
    public String guardaMenoresICBF(@Valid MenoresICBF menoresICBF, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
        if (menoresICBF.getId() == null) {
            MenoresICBF existingPPL = menoresICBFService.findByPrimerNombreAndSegundoNombreAndPrimerApellidoAndSegundoApellidoAndFechaNacimiento(
                    menoresICBF.getPrimerNombre(),
                    menoresICBF.getSegundoNombre(),
                    menoresICBF.getPrimerApellido(),
                    menoresICBF.getSegundoApellido(),
                    menoresICBF.getFechaNacimiento()
            );
            if (existingPPL != null) {
                flash.addFlashAttribute("error_message", "Registro ya existe en la base de datos");
                return "redirect:/formMenoresICBF?error=true";
            }
            String nuevoConsecutivo = menoresICBFService.obtenerUltimoConsecutivoDesdeBaseDeDatos();
            menoresICBF.setConsecutivo(nuevoConsecutivo);
        }
        menoresICBF.setTipoDocumento("MS");
        String mensaje = (menoresICBF.getId() != null) ? "Menor ICBF ha sido editado con éxito" : "Menor ICBF registrado con éxito";
        menoresICBFService.save(menoresICBF);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listarMenoresICBF";
    }


    @GetMapping("/editMenoresICBF/{id}")
    public String editarMenoresICBF(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash,Model model) {
        MenoresICBF menoresICBF = menoresICBFService.findOne(id);
        List<Eps> listaEps = epsService.findAll();
        List<Entidades> listaEntidades = entidadesService.findAll();
        if (menoresICBFService != null) {
            model.addAttribute("listaEps", listaEps);
            model.addAttribute("listaEntidades", listaEntidades);
            modelo.put("menoresICBF", menoresICBF);
            modelo.put("titulo", "Actualización de Menores ICBF");
        } else {
            flash.addFlashAttribute("error", "El ID del Menor no existe en la base de datos");
            return "redirect:/listarMenoresICBF";
        }

        return "formMenoresICBF";
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

    @GetMapping({"/", "/listarMenoresICBF"})
    public String listarMenoresICBF(Model modelo) {
        List<MenoresICBF> menoresICBF = menoresICBFService.findAll();
        Date fechaActual = new Date();
        for (MenoresICBF menor : menoresICBF) {
            Date fechaRegistro = menor.getFechaRegistro();
            Calendar calendarRegistro = Calendar.getInstance();
            calendarRegistro.setTime(fechaRegistro);
            calendarRegistro.add(Calendar.MONTH, 4);
            if (fechaActual.after(calendarRegistro.getTime())) {
                menor.setAlerta(true);
            } else {
                menor.setAlerta(false);
            }
        }
        modelo.addAttribute("titulo", "Agregar Menor ICBF");
        modelo.addAttribute("menoresICBF", menoresICBF);
        modelo.addAttribute("page", null);
        boolean mostrarAlerta = menoresICBF.stream().anyMatch(MenoresICBF::isAlerta);
        modelo.addAttribute("mostrarAlerta", mostrarAlerta);
        return "listarMenoresICBF";
    }

    @GetMapping("/exportarMenoresICBFExcel")
    public void exportarListadoDeIndigenasEnExcel(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=ICBF" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<MenoresICBF> menoresICBF = menoresICBFService.findAll();

        MenoresICBFExporterExcel exporter = new MenoresICBFExporterExcel(menoresICBF);
        exporter.exportar(response);
    }
}
