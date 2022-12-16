package ec.edu.ups.moviles.Facturero.validaciones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionCorreoElectronico {
    private static final Logger log = LoggerFactory.getLogger(ValidacionCorreoElectronico.class);

    public static boolean validarCorreoElectronico(String correo){
        String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regx);
        //Create instance of matcher
        Matcher matcher = pattern.matcher(correo);

        return matcher.matches();
    }
}
