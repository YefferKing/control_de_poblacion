package com.gestion.empleados.controlador;

import com.gestion.empleados.entidades.Empleado;
import com.gestion.empleados.entidades.Eps;
import com.gestion.empleados.entidades.HabitanteCalle;
import com.gestion.empleados.servicio.EpsService;
import com.gestion.empleados.servicio.HabitantecalleService;
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
public class EpsController {

    @Autowired
    private EpsService epsService;

    @GetMapping("/formEps")
    public String mostrarFormularioDeEps(Map<String,Object> modelo) {
        Eps eps = new Eps();
        modelo.put("eps", eps);
        modelo.put("titulo", "Registro de Eps");
        return "formEps";
    }

    @PostMapping("/formEps")
    public String guardarEps(@Valid Eps eps, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
        String mensaje = (eps.getId() != null) ? "La Eps ha sido editato con exito" : "Eps registrado con exito";

        epsService.save(eps);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listarEps";
    }

    @GetMapping({"/", "/listarEps"})
    public String listarEps(Model modelo) {
        List<Eps> eps = epsService.findAll();

        modelo.addAttribute("titulo","Agregar Eps");
        modelo.addAttribute("eps",eps);
        modelo.addAttribute("page", null);

        return "listarEps";
    }

    @GetMapping("/eliminarEps/{id}")
    public String eliminarEps(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if(id > 0) {
            epsService.delete(id);
            flash.addFlashAttribute("success", "Eliminado con exito");
        }
        return "redirect:/listarEps";
    }
}
