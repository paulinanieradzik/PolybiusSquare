package pl.polsl.math;

import java.util.*;

/**
 * A class responsible for encrypting the given word.
 *
 * @author Paulina Nieradzik
 * @version 2.1.
 */
public class Encryption extends PolybiusSquare {

    /**
     * Method that encrypts the given word.
     *
     * @param givenWord word given by the user
     * @return encrypted word
     * @throws pl.polsl.math.PolybiusException when word contains non-English letter or
     * character or when parameter - given word - is null
     */
    public List<Integer> encryption(String givenWord) throws PolybiusException {
        if (givenWord == null) {
            throw new PolybiusException("Given word is null");
        } else {
            List<Integer> encryptedWord = new ArrayList<>();
            givenWord = givenWord.toUpperCase();
            for (int i = 0; i < givenWord.length(); i++) {
                int letterCode = encryptionLetter(givenWord.charAt(i));
                if (letterCode == 0) {
                    throw new PolybiusException("A non-English letter or character is used.");
                } else {
                    encryptedWord.add(letterCode);
                }
            }
            return encryptedWord;
        }
    }

    /**
     * Method that encrypts a specfic given letter
     *
     * @param letter letter to encrypt
     * @return cod of letter
     */
    private int encryptionLetter(char letter) {
        IntegerMath calculateCode = (i, j) -> {
            return (i + 1) * 10 + j + 1;
        };
        if (letter == 'J') {
            return 24;
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (letter == polybiusSquare.get(i).get(j)) {
                    return operate(i, j, calculateCode);
                }
            }
        }
        return 0;
    }

    /**
     * Specification of the lambda expression with two parameters
     */
    interface IntegerMath {

        int operation(int a, int b);
    }

    /**
     * Method which has lambda expression as a prarameter
     *
     * @param a first parameter of lambda
     * @param b second parameter of lambda
     * @param op lamda expression with two parameters
     * @return reuslt of operation
     */
    int operate(int a, int b, IntegerMath op) {
        return op.operation(a, b);
    }
}
