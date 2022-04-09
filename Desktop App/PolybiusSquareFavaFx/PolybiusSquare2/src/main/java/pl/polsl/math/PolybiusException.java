package pl.polsl.math;

/**
 * A class that handles all exeptions that occur when encoding or decoding.
 *
 * @author Paulina Nieradzik
 * @version 1.1.
 */
public class PolybiusException extends Exception {

    /**
     * Non-parameter constructor
     */
    public PolybiusException() {

    }

    /**
     * Exception class constructor
     *
     * @param message message to display
     */
    public PolybiusException(String message) {
        super(message);
    }

}
