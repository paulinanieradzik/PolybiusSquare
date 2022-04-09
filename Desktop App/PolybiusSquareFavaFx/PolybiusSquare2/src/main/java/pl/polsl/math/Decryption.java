package pl.polsl.math;

import java.util.*;

/**
 * A class resposible for decryption given word.
 *
 * @author Paulina Nieradzik
 * @version 2.1.
 */
public class Decryption extends PolybiusSquare {

    /**
     * String to which the decrypted word is written.
     */
    private String decodedWord = "";

    /**
     * Method thet dectypt given word.
     *
     * @param givenWord a word given by the user
     * @return descrypted word
     * @throws pl.polsl.math.PolybiusException when code are incorrect - given word is
     * null or letter code are not from range
     */
    public String decryption(List<Integer> givenWord) throws PolybiusException {
        decodedWord = "";
        if (givenWord == null) {
            throw new PolybiusException("Given word is null");
        } else {
            for (int letterCode : givenWord) {
                int row = letterCode / 10;
                int column = letterCode % 10;
                if (row < 1 || row > 5 || column < 1 || column > 5) {
                    throw new PolybiusException("Numbers are not form range 1 to 5!");
                } else {
                    decodedWord = decodedWord + polybiusSquare.get(row - 1).get(column - 1);
                }
            }
            return decodedWord;
        }
    }
}
