package com.cpifppiramide.prueba1.controller;

import com.cpifppiramide.prueba1.dao.DAOFactory;
import com.cpifppiramide.prueba1.entidades.Ejemplar;
import com.cpifppiramide.prueba1.entidades.Prenda;
import com.cpifppiramide.prueba1.entidades.TipoPrenda;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PrendasController {

    @GetMapping("/prendas")
    String prendas(Model model){
        List<Prenda> prendas = DAOFactory.getInstance().getDaoPrendas().lista();
        model.addAttribute("prendas", prendas);
        return "index";
    }

    @GetMapping("/prendas/nueva")
    String nueva(Model model){
        List<String> tiposDePrenda = new ArrayList<>();
        for (TipoPrenda tipo : TipoPrenda.values()){
            tiposDePrenda.add(tipo.toString());
        }
        model.addAttribute("tipos", tiposDePrenda);
        return "nueva";
    }

    @GetMapping("/prendas/{marca}")
    String ver(Model model, @PathVariable String marca){
        Prenda prenda = DAOFactory.getInstance().getDaoPrendas().ver(marca);
        List<Ejemplar> ejemplares = DAOFactory.getInstance().getDaoEjemplares().get(prenda);
        prenda.setEjemplares(ejemplares);
        model.addAttribute("prenda", prenda);
        return "ver";
    }

    @PostMapping("/prendas/nueva")
    public String guardarPrenda(@RequestParam String marca,
                                @RequestParam String tipoPrenda) {
        Prenda prenda = new Prenda(marca, TipoPrenda.valueOf(tipoPrenda));
        DAOFactory.getInstance().getDaoPrendas().inserta(prenda);
        return "redirect:/prendas";
    }

}
