package com.gestion.empleados.controlador;

import com.gestion.empleados.entidades.Entidades;
import com.gestion.empleados.entidades.Eps;
import com.gestion.empleados.servicio.EntidadesService;
import com.gestion.empleados.servicio.EpsService;
import com.gestion.empleados.util.paginacion.PageRender;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class EntidadesController {

    @Autowired
    private EntidadesService entidadesService;

    @GetMapping("/formEntidades")
    public String mostrarFormularioDeEntidades(Map<String,Object> modelo) {
        Entidades entidades = new Entidades();
        modelo.put("entidades", entidades);
        modelo.put("titulo", "Registro de Entidades");
        return "formEntidades";
    }

    @PostMapping("/formEntidades")
    public String guardarEntidades(@Valid Entidades entidades, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
        String mensaje = (entidades.getId() != null) ? "La Entidad ha sido editato con exito" : "Entidad registrado con exito";

        entidadesService.save(entidades);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listarEntidades";
    }

    @GetMapping({"/", "/listarEntidades"})
    public String listarEntidades(Model modelo) {
        List<Entidades> entidades = entidadesService.findAll();

        modelo.addAttribute("titulo","Agregar Entidades");
        modelo.addAttribute("entidades",entidades);
        modelo.addAttribute("page", null);

        return "listarEntidades";
    }

    @GetMapping("/eliminarEntidades/{id}")
    public String eliminarEntidades(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if(id > 0) {
            entidadesService.delete(id);
            flash.addFlashAttribute("success", "Eliminado con exito");
        }
        return "redirect:/listarEntidades";
    }
}
