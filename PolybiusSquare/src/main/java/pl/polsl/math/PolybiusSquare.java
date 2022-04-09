package pl.polsl.math;

import java.util.*;

/**
 * A class resposible for decryption and encryption given word.
 *
 * @author Paulina Nieradzik
 * @version 3.0.
 */
public class PolybiusSquare {

    /**
     * Array which contains all letters and their code.
     */
    private final List<List<Character>> polybiusSquare = new ArrayList<>(5);

    /**
     * String to which the decrypted word is written.
     */
    private String decodedWord = "";

    /**
     * Array which contains history of decryption
     */
    private List<String> historyOfDecode = new ArrayList<>();

    /**
     * Array which contains history of encryption
     */
    private List<String> historyOfEncode = new ArrayList<>();

    /**
     * Constructor of PolybiusSquare. Fills the table with letters.
     */
    public PolybiusSquare() {
        for (int i = 0; i < 5; i++) {
            polybiusSquare.add(new ArrayList());
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                polybiusSquare.get(i).add(j, (char) (i * 5 + j + 65));
            }
        }
        polybiusSquare.get(1).set(4, 'K');
        for (int i = 2; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                polybiusSquare.get(i).add(j, (char) (i * 5 + j + 65 + 1));
            }
        }
    }

    /**
     * Method thet dectypt given word.
     *
     * @param givenWord a word given by the user
     * @return descrypted word
     * @throws pl.polsl.math.PolybiusException when code are incorrect - given
     * word is null or letter code are not from range
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

    /**
     * Method that encrypts the given word.
     *
     * @param givenWord word given by the user
     * @return encrypted word
     * @throws pl.polsl.math.PolybiusException when word contains non-English
     * letter or character or when parameter - given word - is null
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
     * Method which returns list of decoded words.
     *
     * @return list od decoded words
     */
    public List<String> getHistoryOfDecoding() {
        return historyOfDecode;
    }

    /**
     * Method which returns list of encoded words.
     *
     * @return list of encoded words
     */
    public List<String> getHistoryOfEncoding() {
        return historyOfEncode;
    }

    /**
     * Method which add elements to historyOfDecode list
     *
     * @param code cod of word
     * @param decodedWord decoded word
     */
    public void addToHistoryOfDecode(String code, String decodedWord) {
        historyOfDecode.add(code);
        historyOfDecode.add(decodedWord);
    }

    /**
     * Method which add elements to historyOfEncode list
     *
     * @param word word
     * @param encodeWord encoded word
     */
    public void addToHistoryOfEncode(String word, String encodeWord) {
        historyOfEncode.add(word);
        historyOfEncode.add(encodeWord);
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
