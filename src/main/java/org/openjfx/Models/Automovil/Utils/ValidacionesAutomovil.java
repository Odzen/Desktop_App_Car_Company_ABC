package org.openjfx.Models.Automovil.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionesAutomovil {
    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                    + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                    + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_CILINDRAJE =
            Pattern.compile("^[0-9]{3,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_MODELO =
            Pattern.compile("^[A-Z a-z 0-9]{1,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_MARCA =
            Pattern.compile("^[A-Z a-z]{3,20}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PLACA =
            Pattern.compile("^[A-Z]{3}-[0-9]{3}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_COLOR =
            Pattern.compile("^[A-Z a-z]{2,20}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_AÑO =
            Pattern.compile("[0-9]{4}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PRECIO =
            Pattern.compile("[0-9]{8,9}$", Pattern.CASE_INSENSITIVE);


    public static final Pattern VALID_DATE =
            Pattern.compile("^((?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:(?:0[13578]|1[02])(-)31)|((0[1,3-9]|1[0-2])(-)(29|30))))$|^(?:(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(-)02(-)29)$|^(?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:0[1-9])|(?:1[0-2]))(-)(?:0[1-9]|1\\d|2[0-8])$", Pattern.CASE_INSENSITIVE);


    // Verifica si un fecha es correcta
    // Con el formato YYYY-MM-DD (within the range 1600-2999 year)
    public static boolean validarFecha(String fecha) {
        Matcher matcher = VALID_DATE.matcher(fecha);
        return matcher.find();
    }

    // Verifica si el nombre es correcto
    public static boolean validarModelo(String modelo) {
        Matcher matcher = VALID_MODELO.matcher(modelo);
        return matcher.find();
    }

    // Verifica si la marca es correcta
    public static boolean validarMarca(String marca) {
        Matcher matcher = VALID_MARCA.matcher(marca);
        return matcher.find();
    }


    // Verifica si la color es correcta
    public static boolean validarColor(String color) {
        Matcher matcher = VALID_COLOR.matcher(color);
        return matcher.find();
    }

    // Verifica si la PLACA es correcta
    public static boolean validarPlaca(String placa) {
        Matcher matcher = VALID_PLACA.matcher(placa);
        return matcher.find();
    }

    // Verifica si el cilindraje es correcto
    public static boolean validarCilindraje(String cilindraje) {
        Matcher matcher = VALID_CILINDRAJE.matcher(cilindraje);
        return matcher.find();
    }

    // Verifica si la PLACA es correcta
    public static boolean validarAño(String año) {
        Matcher matcher = VALID_AÑO.matcher(año);
        return matcher.find();
    }
    // Verifica si el cilindraje es correcto
    public static boolean validarPrecio(String precio) {
        Matcher matcher = VALID_PRECIO.matcher(precio);
        return matcher.find();
    }




}
