package org.openjfx.Models.Cotizacion.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionesCotizacion {



    public static final Pattern VALID_CEDULA =
            Pattern.compile("^[0-9]{8,20}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PLACA =
            Pattern.compile("^[A-Z]{3}-[0-9]{3}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_ID_ORDEN =
            Pattern.compile("^([1-9][0-9]{0,2})$", Pattern.CASE_INSENSITIVE);




    // Verifica si la cédula es correcta
    public static boolean validarCedula(String cedula) {
        Matcher matcher = VALID_CEDULA.matcher(cedula);
        return matcher.find();
    }
    // Verifica si la PLACA es correcta
    public static boolean validarPlaca(String placa) {
        Matcher matcher = VALID_PLACA.matcher(placa);
        return matcher.find();
    }
    // Verifica si el id de la orden tiene el formato correcto
    public static boolean validarIdOrden(String id) {
        Matcher matcher = VALID_ID_ORDEN.matcher(id);
        return matcher.find();
    }


}
