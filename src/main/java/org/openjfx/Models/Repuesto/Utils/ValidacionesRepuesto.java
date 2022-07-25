package org.openjfx.Models.Repuesto.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionesRepuesto {


    public static final Pattern VALID_NOMBRE_REPUESTO =
            Pattern.compile("^[a-z A-Z]{4,20}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_MARCA =
            Pattern.compile("^[a-z A-Z]{4,20}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_CANTIDAD =
            Pattern.compile("^([1-9][0-9]{0,2})$", Pattern.CASE_INSENSITIVE);


    // Verifica si el nombre del repuesto es correcto
    public static boolean validarNombreRepuesto(String nombre) {
        Matcher matcher = VALID_NOMBRE_REPUESTO.matcher(nombre);
        return matcher.find();
    }

    // Verifica si la marca del repuesto es correcta
    public static boolean validarMarcaRepuesto(String marca) {
        Matcher matcher = VALID_MARCA.matcher(marca);
        return matcher.find();
    }

    // Verifica si la cantidad es correcta
    public static boolean validarCantidadRepuesto(String cantidad) {
        Matcher matcher = VALID_CANTIDAD.matcher(cantidad);
        return matcher.find();
    }
}
