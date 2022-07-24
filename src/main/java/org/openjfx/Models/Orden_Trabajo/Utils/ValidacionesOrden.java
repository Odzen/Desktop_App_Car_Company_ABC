package org.openjfx.Models.Orden_Trabajo.Utils;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionesOrden {
    public static final Pattern VALID_CEDULA =
            Pattern.compile("^[0-9]{8,20}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PLACA =
            Pattern.compile("^[A-Z]{3}-[0-9]{3}$", Pattern.CASE_INSENSITIVE);

    // Verifica si la cédula del cliente es correcta
    public static boolean validarCedulaCliente(String cedula) {
        Matcher matcher = VALID_CEDULA.matcher(cedula);
        return matcher.find();
    }

    // Verifica si la PLACA es correcta
    public static boolean validarPlaca(String placa) {
        Matcher matcher = VALID_PLACA.matcher(placa);
        return matcher.find();
    }
}

