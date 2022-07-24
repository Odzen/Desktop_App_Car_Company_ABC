package org.openjfx.Models.Cliente.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionesCotizacion {



    public static final Pattern VALID_CEDULA =
            Pattern.compile("^[0-9]{8,20}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_DATE =
            Pattern.compile("^((?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:(?:0[13578]|1[02])(-)31)|((0[1,3-9]|1[0-2])(-)(29|30))))$|^(?:(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(-)02(-)29)$|^(?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:0[1-9])|(?:1[0-2]))(-)(?:0[1-9]|1\\d|2[0-8])$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PLACA =
            Pattern.compile("^[A-Z]{3}-[0-9]{3}$", Pattern.CASE_INSENSITIVE);


    // Verifica si un fecha es correcta
    // Con el formato YYYY-MM-DD (within the range 1600-2999 year)
    public static boolean validarFecha(String fecha) {
        Matcher matcher = VALID_DATE.matcher(fecha);
        return matcher.find();
    }


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

}
