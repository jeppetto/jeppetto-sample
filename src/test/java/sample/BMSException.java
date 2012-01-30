package sample;


public class BMSException extends Exception {

    //-------------------------------------------------------------
    // Constructors
    //-------------------------------------------------------------

    public BMSException() {
    }


    public BMSException(String message) {
        super(message);
    }


    public BMSException(String message, Throwable cause) {
        super(message, cause);
    }


    public BMSException(Throwable cause) {
        super(cause);
    }

}
