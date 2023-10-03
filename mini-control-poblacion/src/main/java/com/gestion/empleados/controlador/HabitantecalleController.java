package com.gestion.empleados.controlador;


import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.entidades.HabitanteCalle;
import com.gestion.empleados.servicio.HabitantecalleService;
import com.gestion.empleados.util.paginacion.PageRender;
import com.gestion.empleados.util.reportes.EmpleadoExporterExcel;
import com.gestion.empleados.util.reportes.HabitantecalleExporterExcel;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class HabitantecalleController {

    @Autowired
    private HabitantecalleService habitantecalleService;

    @GetMapping("/formHabitanteCalle")
    public String mostrarFormularioDeRegistrarHabitanteCalle(Map<String,Object> modelo) {
        HabitanteCalle habitanteCalle = new HabitanteCalle();
        modelo.put("habitanteCalle", habitanteCalle);
        modelo.put("titulo", "Registro de Habitante de Calle");
        return "formHabitanteCalle";
    }

    @PostMapping("/formHabitanteCalle")
    public String guardarHabitanteCalle(@Valid HabitanteCalle habitanteCalle, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
        /*if(result.hasErrors()) {
            modelo.addAttribute("titulo", "Registro de cliente");
            return "formHabitanteCalle";
        }*/
        String nuevoConsecutivo = habitantecalleService.obtenerUltimoConsecutivoDesdeBaseDeDatos();
        habitanteCalle.setConsecutivo(nuevoConsecutivo);
        int edad = calcularEdad(habitanteCalle.getFechaNacimiento());
        if (edad >= 18) {
            habitanteCalle.setTipoDocumento("AS");
        } else {
            habitanteCalle.setTipoDocumento("MS");
        }
        String mensaje = (habitanteCalle.getId() != null) ? "El Habitante de calle ha sido editato con exito" : "Habitante de calle registrado con exito";
        habitantecalleService.save(habitanteCalle);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listarHabitanteCalle";
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

    @GetMapping({"/","/listarHabitanteCalle"})
    public String listarHabitantes(@RequestParam(name = "page",defaultValue = "0") int page,Model modelo) {
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<HabitanteCalle> habitanteCalle = habitantecalleService.findAll(pageRequest);
        PageRender<HabitanteCalle> pageRender = new PageRender<>("/listarHabitanteCalle", habitanteCalle);

        modelo.addAttribute("titulo","Agregar Población");
        modelo.addAttribute("habitanteCalle",habitanteCalle);
        modelo.addAttribute("page", pageRender);

        return "listarHabitanteCalle";
    }

    @GetMapping("/formHabitanteCalle/{id}")
    public String editarHabitanteCalle(@PathVariable(value = "id") Long id,Map<String, Object> modelo,RedirectAttributes flash) {
        HabitanteCalle habitanteCalle = null;
        if(id > 0) {
            habitanteCalle = habitantecalleService.findOne(id);
            if(habitanteCalle == null) {
                flash.addFlashAttribute("error", "El ID del Habitante de calle no existe en la base de datos");
                return "redirect:/listarHabitanteCalle";
            }
        }
        else {
            flash.addFlashAttribute("error", "El ID del Habitante de calle no puede ser cero");
            return "redirect:/listarHabitanteCalle";
        }

        modelo.put("habitanteCalle",habitanteCalle);
        modelo.put("titulo", "Edición de Habitante de calle");
        return "formHabitanteCalle";
    }

    @GetMapping("/exportarHabitanteExcel")
    public void exportarListadoDeHabitanteCalleEnExcel(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Habitante_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<HabitanteCalle> habitanteCalle = habitantecalleService.findAll();

        HabitantecalleExporterExcel exporter = new HabitantecalleExporterExcel(habitanteCalle);
        exporter.exportar(response);
    }
}
