/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.controller;

import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ferenc
 */
@RestController
public class ToolsController {

    @CrossOrigin
    @RequestMapping(value = "/tools/valiData/{fecha}", method = GET, produces = "application/json")
    public String valiData(@PathVariable String fecha) {
        JsonObject res = new JsonObject();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNac = LocalDate.parse(fecha, fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);

//        res.addProperty("años", periodo.getYears());
//        res.addProperty("meses", periodo.getMonths());
//        res.addProperty("dias", periodo.getDays());
        if (periodo.getYears() >= 18 && periodo.getDays() >= 1) {
            res.addProperty("mayor", true);
            return res.toString();
        } else {
            res.addProperty("mayor", false);
            return res.toString();
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/tools/valiRut/{rut}", method = GET, produces = "application/json")
    public String valiRut(@PathVariable String rut) {
        JsonObject res = new JsonObject();

        if (validarRut(rut)) {
            res.addProperty("rut_valido", true);
            return res.toString();
        }else{
            res.addProperty("rut_valido", false);
            return res.toString();
        }

    }

    public boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }

}
