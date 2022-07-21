package org.openjfx.Models.Automovil.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionesAutomovil {
    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                    + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                    + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_CILINDRAJE =
            Pattern.compile("^[0-9_a-z]{6,8}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_NOMBRE =
            Pattern.compile("^[a-z A-Z]{8,20}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_MARCA =
            Pattern.compile("^[a-z A-Z]{4,20}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PLACA =
            Pattern.compile("^[A-Z]{3}\\-[0-9]{3}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_DATE =
            Pattern.compile("^((?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:(?:0[13578]|1[02])(-)31)|((0[1,3-9]|1[0-2])(-)(29|30))))$|^(?:(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(-)02(-)29)$|^(?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:0[1-9])|(?:1[0-2]))(-)(?:0[1-9]|1\\d|2[0-8])$", Pattern.CASE_INSENSITIVE);


    // Verifica si un fecha es correcta
    // Con el formato YYYY-MM-DD (within the range 1600-2999 year)
    public static boolean validarFecha(String fecha) {
        Matcher matcher = VALID_DATE.matcher(fecha);
        return matcher.find();
    }

    // Verifica si el nombre es correcto
    public static boolean validarNombre(String nombre) {
        Matcher matcher = VALID_NOMBRE.matcher(nombre);
        return matcher.find();
    }

    // Verifica si la ciudad es correcta
    public static boolean validarMarca(String email) {
        Matcher matcher = VALID_MARCA.matcher(email);
        return matcher.find();
    }


    // Verifica si la PLACA es correcta
    public static boolean validarPlaca(String email) {
        Matcher matcher = VALID_PLACA.matcher(email);
        return matcher.find();
    }

    // Verifica si el cilindraje es correcto
    public static boolean validarCilindraje(String email) {
        Matcher matcher = VALID_CILINDRAJE.matcher(email);
        return matcher.find();
    }

    // Verifica si el telefono es correcto

    String[] validPhoneNumbers
            = {"2055550125","202 555 0125", "(202) 555-0125", "+111 (202) 555-0125",
            "636 856 789", "+111 636 856 789", "636 85 67 89", "+111 636 85 67 89"};
    public static boolean validarTelefono(String telefono) {
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(telefono);
        return matcher.find();
    }
}
