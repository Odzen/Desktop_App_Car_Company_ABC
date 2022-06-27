package org.openjfx.Models.Usuario.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?!.*\\\\s)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                    + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                    + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_CEDULA =
            Pattern.compile("^[0-9]{8,20}$", Pattern.CASE_INSENSITIVE);



    // Verifica si el email es correcto
    public static boolean validarEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    // Verifica si la cédula es correcta
    public static boolean validarCedula(String cedula) {
        Matcher matcher = VALID_CEDULA.matcher(cedula);
        return matcher.find();
    }


    // Verifica si la contraseña es correcta
    public static boolean validarPassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
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
