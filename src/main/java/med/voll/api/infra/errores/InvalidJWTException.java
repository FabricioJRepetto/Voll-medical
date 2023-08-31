package med.voll.api.infra.errores;

public class InvalidJWTException extends RuntimeException {

    public InvalidJWTException(String s) {
        super(s);
    }
}
