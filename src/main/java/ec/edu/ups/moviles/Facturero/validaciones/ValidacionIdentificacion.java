package ec.edu.ups.moviles.Facturero.validaciones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidacionIdentificacion {
    private static final Logger log = LoggerFactory.getLogger(ValidacionIdentificacion.class);

    /**
     * Validar cédula
     *
     * @param numero  Número de cédula
     *
     * @return Boolean
     */
    public boolean validarCedula(String numero) {
        // validaciones
        try {
            validarInicial(numero, 10);
            validarCodigoProvincia(numero.substring(0, 2));
            validarTercerDigito(Integer.parseInt(String.valueOf(numero.charAt(2))), "cedula");
            algoritmoModulo10(numero.substring(0, 9), Integer.parseInt(String.valueOf(numero.charAt(9))));
        } catch (IdentificacionException ex) {
            log.error("error al validar la cedula {}: " + ex.getMessage(), numero);
            return false;
        }

        return true;
    }

    /**
     * Validar RUC persona natural
     *
     * @param numero  Número de RUC persona natural
     *
     * @return Boolean
     */
    public boolean validarRucPersonaNatural(String numero)
    {
        // validaciones
        try {
            validarInicial(numero, 13);
            validarCodigoProvincia(numero.substring(0, 2));
            validarTercerDigito(Integer.parseInt(String.valueOf(numero.charAt(2))), "ruc_natural");
            validarCodigoEstablecimiento(Integer.parseInt(numero.substring(10, 13)));
            algoritmoModulo10(numero.substring(0, 9), Integer.parseInt(String.valueOf(numero.charAt(9))));
        } catch (IdentificacionException ex) {
            log.error("error al validar el ruc {} de persona natural: " + ex.getMessage(), numero);
            return false;
        }

        return true;
    }


    /**
     * Validar RUC sociedad privada
     *
     * @param numero  Número de RUC sociedad privada
     *
     * @return Boolean
     */
    public boolean validarRucSociedadPrivada(String numero)
    {

        // validaciones
        try {
            validarInicial(numero, 13);
            validarCodigoProvincia(numero.substring(0, 2));
            validarTercerDigito(Integer.parseInt(String.valueOf(numero.charAt(2))), "ruc_privada");
            validarCodigoEstablecimiento(Integer.parseInt(numero.substring(10,13)));
            algoritmoModulo11(numero.substring(0,9), Integer.parseInt(String.valueOf(numero.charAt(9))), "ruc_privada");
        } catch (IdentificacionException ex) {
            log.error("error al validar el ruc {} de sociedad privada: " + ex.getMessage(), numero);
            return false;
        }

        return true;
    }

    /**
     * Validar RUC sociedad publica
     *
     * @param numero  Número de RUC sociedad publica
     *
     * @return Boolean
     */
    public boolean validarRucSociedadPublica(String numero)
    {
        // validaciones
        try {
            validarInicial(numero, 13);
            validarCodigoProvincia(numero.substring(0,2));
            validarTercerDigito(Integer.parseInt(String.valueOf(numero.charAt(2))), "ruc_publica");
            validarCodigoEstablecimiento(Integer.parseInt(numero.substring(9,13)));
            algoritmoModulo11(numero.substring(0,8), Integer.parseInt(String.valueOf(numero.charAt(8))), "ruc_publica");
        } catch (IdentificacionException ex) {
            log.error("error al validar el ruc de sociedad publica: " + ex.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Validaciones iniciales para CI y RUC
     *
     * @param numero     CI o RUC
     * @param caracteres Cantidad de caracteres requeridos
     * @return boolean
     * @throws IdentificacionException uando valor esta vacio, cuando no es dígito y
     *                                 cuando no tiene cantidad requerida de caracteres
     */
    protected boolean validarInicial(String numero, int caracteres) throws IdentificacionException {
        if (numero == null || numero.isEmpty()) {
            log.error("Identificacion {} no puede estar vacia", numero);
            throw new IdentificacionException("Identificacion no puede estar vacia");
        }

        if (!numero.matches("[0-9]+")) {
            log.error("Identificacion {} ingresada solo puede tener dígitos", numero);
            throw new IdentificacionException("Identificacion ingresada solo puede tener dígitos");
        }

        if (numero.length() != caracteres) {
            log.error("Identificacion {} ingresada debe tener {} caracteres", numero, caracteres);
            throw new IdentificacionException("Identificacion ingresada debe tener " + caracteres + " caracteres");
        }

        return true;
    }

    /**
     * Validación de código de provincia (dos primeros dígitos de CI/RUC)
     *
     * @param numero Dos primeros dígitos de CI/RUC
     * @return boolean
     * @throws IdentificacionException Cuando el código de provincia no esta entre 00 y 24
     */
    protected boolean validarCodigoProvincia(String numero) throws IdentificacionException {
        if (Integer.parseInt(numero) < 0 || Integer.parseInt(numero) > 24) {
            throw new IdentificacionException("Codigo de Provincia '" + numero + "' (dos primeros dígitos) no deben ser mayor a 24 ni menores a 0");
        }

        return true;
    }

    /**
     * Validación de tercer dígito
     * <p>
     * Permite validad el tercer dígito del documento. Dependiendo
     * del campo tipo (tipo de identificación) se realizan las validaciones.
     * Los posibles valores del campo tipo son: cedula, ruc_natural, ruc_privada
     * <p>
     * Para Cédulas y RUC de personas naturales el terder dígito debe
     * estar entre 0 y 5 (0,1,2,3,4,5)
     * <p>
     * Para RUC de sociedades privadas el terder dígito debe ser
     * igual a 9.
     * <p>
     * Para RUC de sociedades públicas el terder dígito debe ser
     * igual a 6.
     *
     * @param numero tercer dígito de CI/RUC
     * @param tipo   tipo de identificador
     * @return boolean
     * @throws IdentificacionException Cuando el tercer digito no es válido. El mensaje
     *                                 de error depende del tipo de Idenficiación.
     */
    protected boolean validarTercerDigito(int numero, String tipo) throws IdentificacionException {
        switch (tipo) {
            case "cedula":
            case "ruc_natural":
                if (numero < 0 || numero > 5) {
                    throw new IdentificacionException("Tercer dígito '" +numero+ "' debe ser mayor o igual a 0 y menor a 6 para cédulas y RUC de persona natural");
                }
                break;
            case "ruc_privada":
                if (numero != 9) {
                    throw new IdentificacionException("Tercer dígito '" +numero+ "' debe ser igual a 9 para sociedades privadas");
                }
                break;
            case "ruc_publica":
                if (numero != 6) {
                    throw new IdentificacionException("Tercer dígito '" +numero+ "' debe ser igual a 6 para sociedades públicas");
                }
                break;
            default:
                throw new IdentificacionException("Tipo de Identificación '" + tipo + "' no existe.");
        }

        return true;
    }

    /**
     * Validación de código de establecimiento
     *
     * @param numero tercer dígito de CI/RUC
     * @return boolean
     * @throws IdentificacionException Cuando el establecimiento es menor a 1
     */
    protected boolean validarCodigoEstablecimiento(int numero) throws IdentificacionException {
        if (numero < 1) {
            throw new IdentificacionException("Código de establecimiento '" + numero + "' no puede ser 0");
        }

        return true;
    }

    /**
     * Algoritmo Modulo10 para validar si CI y RUC de persona natural son válidos.
     * <p>
     * Los coeficientes usados para verificar el décimo dígito de la cédula,
     * mediante el algoritmo “Módulo 10” son:  2. 1. 2. 1. 2. 1. 2. 1. 2
     * <p>
     * Paso 1: Multiplicar cada dígito de los digitosIniciales por su respectivo
     * coeficiente.
     * <p>
     * Ejemplo
     * digitosIniciales posicion 1  x 2
     * digitosIniciales posicion 2  x 1
     * digitosIniciales posicion 3  x 2
     * digitosIniciales posicion 4  x 1
     * digitosIniciales posicion 5  x 2
     * digitosIniciales posicion 6  x 1
     * digitosIniciales posicion 7  x 2
     * digitosIniciales posicion 8  x 1
     * digitosIniciales posicion 9  x 2
     * <p>
     * Paso 2: Sí alguno de los resultados de cada multiplicación es mayor a o igual a 10,
     * se suma entre ambos dígitos de dicho resultado. Ex. 12->1+2->3
     * <p>
     * Paso 3: Se suman los resultados y se obtiene total
     * <p>
     * Paso 4: Divido total para 10, se guarda residuo. Se resta 10 menos el residuo.
     * El valor obtenido debe concordar con el digitoVerificador
     * <p>
     * Nota: Cuando el residuo es cero(0) el dígito verificador debe ser 0.
     *
     * @param digitosIniciales   Nueve primeros dígitos de CI/RUC
     * @param digitoVerificador  Décimo dígito de CI/RUC
     * @return boolean
     * @throws IdentificacionException Cuando los digitosIniciales no concuerdan contra
     *                   el código verificador.
     */
    protected boolean algoritmoModulo10(String digitosIniciales, int digitoVerificador) throws IdentificacionException {
        int arrayCoeficientes[] = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int total = 0;
        for (int i = 0; i < digitosIniciales.length(); i++) {

            int valorPosicion = (Integer.parseInt(String.valueOf(digitosIniciales.charAt(i))) * arrayCoeficientes[i]);

            if (valorPosicion >= 10) {
                valorPosicion = (valorPosicion / 10 + valorPosicion % 10);
            }

            total += valorPosicion;
        }

        int residuo = total % 10;
        int resultado = 0;

        if (residuo == 0) {
            resultado = 0;
        } else {
            resultado = 10 - residuo;
        }

        if (resultado != digitoVerificador) {
            throw new IdentificacionException("Dígitos iniciales no validan contra Dígito Idenficador");
        }

        return true;
    }

    /**
     * Algoritmo Modulo11 para validar RUC de sociedades privadas y públicas
     * <p>
     * El código verificador es el decimo digito para RUC de empresas privadas
     * y el noveno dígito para RUC de empresas públicas
     * <p>
     * Paso 1: Multiplicar cada dígito de los digitosIniciales por su respectivo
     * coeficiente.
     * <p>
     * Para RUC privadas el coeficiente esta definido y se multiplica con las siguientes
     * posiciones del RUC:
     * <p>
     * Ejemplo
     * digitosIniciales posicion 1  x 4
     * digitosIniciales posicion 2  x 3
     * digitosIniciales posicion 3  x 2
     * digitosIniciales posicion 4  x 7
     * digitosIniciales posicion 5  x 6
     * digitosIniciales posicion 6  x 5
     * digitosIniciales posicion 7  x 4
     * digitosIniciales posicion 8  x 3
     * digitosIniciales posicion 9  x 2
     * <p>
     * Para RUC privadas el coeficiente esta definido y se multiplica con las siguientes
     * posiciones del RUC:
     * <p>
     * digitosIniciales posicion 1  x 3
     * digitosIniciales posicion 2  x 2
     * digitosIniciales posicion 3  x 7
     * digitosIniciales posicion 4  x 6
     * digitosIniciales posicion 5  x 5
     * digitosIniciales posicion 6  x 4
     * digitosIniciales posicion 7  x 3
     * digitosIniciales posicion 8  x 2
     * <p>
     * Paso 2: Se suman los resultados y se obtiene total
     * <p>
     * Paso 3: Divido total para 11, se guarda residuo. Se resta 11 menos el residuo.
     * El valor obtenido debe concordar con el digitoVerificador
     * <p>
     * Nota: Cuando el residuo es cero(0) el dígito verificador debe ser 0.
     *
     * @param digitosIniciales   Nueve primeros dígitos de RUC
     * @param digitoVerificador  Décimo dígito de RUC
     * @param tipo Tipo de identificador
     * @return boolean
     * @throws IdentificacionException Cuando los digitosIniciales no concuerdan contra
     *                   el código verificador.
     */
    protected boolean algoritmoModulo11(String digitosIniciales, int digitoVerificador, String tipo) throws IdentificacionException  {
        int total = 0;
        int valorPosicion = 0;

        if(tipo.equals("ruc_privada")) {
            int[] arrayCoeficientes = {4, 3, 2, 7, 6, 5, 4, 3, 2};

            for(int i = 0; i < digitosIniciales.length(); i++){
                valorPosicion = (Integer.parseInt(String.valueOf(digitosIniciales.charAt(i))) * arrayCoeficientes[i]);
                total += valorPosicion;
            }
        }else if(tipo.equals("ruc_publica")) {
            int[] arrayCoeficientes = {3, 2, 7, 6, 5, 4, 3, 2};

            for(int i = 0; i < digitosIniciales.length(); i++){
                valorPosicion = (Integer.parseInt(String.valueOf(digitosIniciales.charAt(i))) * arrayCoeficientes[i]);
                total += valorPosicion;
            }
        }else{
            throw new IdentificacionException("Tipo de Identificación no existe.");
        }

        int residuo = total % 11;
        int resultado = 0;

        if (residuo == 0) {
            resultado = 0;
        } else {
            resultado = 11 - residuo;
        }

        if (resultado != digitoVerificador) {
            throw new IdentificacionException("Dígitos iniciales no validan contra Dígito Idenficador");
        }

        return true;
    }

}
