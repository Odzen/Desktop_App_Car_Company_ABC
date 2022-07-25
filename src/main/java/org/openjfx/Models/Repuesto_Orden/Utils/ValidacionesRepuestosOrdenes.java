package org.openjfx.Models.Repuesto_Orden.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionesRepuestosOrdenes {

    public static final Pattern VALID_CANTIDAD =
            Pattern.compile("^[1-50]{1,3}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_IDS =
            Pattern.compile("^[1-1000]{1,4}$", Pattern.CASE_INSENSITIVE);


    // Verifica Ids
    public static boolean validarIds(String id) {
        Matcher matcher = VALID_IDS.matcher(id);
        return matcher.find();
    }

    // Verifica si la cantidad es correcta
    public static boolean validarCantidadRepuestoOrden(String cantidad) {
        Matcher matcher = VALID_CANTIDAD.matcher(cantidad);
        return matcher.find();
    }
}
