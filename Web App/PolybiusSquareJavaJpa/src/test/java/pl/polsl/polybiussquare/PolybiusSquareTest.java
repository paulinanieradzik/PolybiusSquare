package pl.polsl.polybiussquare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.polsl.math.PolybiusException;
import pl.polsl.math.PolybiusSquare;

/**
 *
 * @author Paulina
 */
public class PolybiusSquareTest {

    /**
     * PolybiusSquare class object use in all unit tests
     */
    private PolybiusSquare polybiusSquare;

    /**
     * Method performed before each test, initializes a new object of
     * PolybiusSquare
     */
    @BeforeEach
    public void setUp() {
        polybiusSquare = new PolybiusSquare();
    }

    /**
     * Parametrized test which checks if decoding is correct
     *
     * @param givenWord list of letter code which is decode
     * @param expectedResult word which is expected
     */
    @ParameterizedTest
    @MethodSource("streamOfWordCodeAndExpecterResult")
    public void testDecryption(List<Integer> givenWord, String expectedResult) {
        try {
            assertEquals(polybiusSquare.decryption(givenWord), expectedResult, "Error during decryption");
        } catch (PolybiusException exception) {
            fail("The exception was thrown when shouldn't be");
        }
    }

    /**
     * Method which return stream of argument - list of code letter and expected
     * word
     *
     * @return stream od argument
     */
    static Stream<Arguments> streamOfWordCodeAndExpecterResult() {
        return Stream.of(
                arguments(Arrays.asList(24, 11, 51, 11), "IAVA"),
                arguments(Arrays.asList(52, 54, 14, 55, 24, 11, 31), "WYDZIAL"),
                arguments(Arrays.asList(24, 33, 21, 34, 42, 32, 11, 44, 54, 25, 11), "INFORMATYKA"),
                arguments(Arrays.asList(35, 42, 34, 22, 42, 11, 32, 34, 52, 11, 33, 24, 15), "PROGRAMOWANIE"),
                arguments(Arrays.asList(33, 15, 44, 12, 15, 11, 33, 43), "NETBEANS")
        );
    }

    /**
     * Parametrized test which check whether in the case of wrong letter code is
     * used method thrown an exception
     *
     * @param givenWord list of letter code with errors
     */
    @ParameterizedTest
    @MethodSource("streamOfWordCodeWithWrongLetterCode")
    public void testDecryptionWhenLetterCodeIsOutOfTheScope(List<Integer> givenWord) {
        try {
            polybiusSquare.decryption(givenWord);
            fail("An exception about used a number greater than 5 or less than 1 should be thrown.");
        } catch (PolybiusException exception) {
            assertTrue(exception.getMessage().contains("range"), "Unexpected message!");
        }
    }

    /**
     * Method which return stream of argument - list of expected code letter
     * with at lest one wrong code
     *
     * @return stream od argument
     */
    static Stream<Arguments> streamOfWordCodeWithWrongLetterCode() {
        return Stream.of(
                arguments(Arrays.asList(24, 00, 51, 11)),
                arguments(Arrays.asList(10, 15, 64)),
                arguments(Arrays.asList(24, 33, 21, 66, 42, 32, 11, 44, 54, 25, 11)),
                arguments(Arrays.asList(35, 99, 34, 22, 42, 11, 37, 34, 52, 11, 33, 24, 15))
        );
    }

    /**
     * Test decryption when parameter is empty array
     */
    @Test
    public void testDecryptionWhenParameterIsEmptyArray() {
        try {
            List<Integer> list = new ArrayList();
            assertEquals(polybiusSquare.decryption(list), "", "Error during decryption when parameter is empty array");
        } catch (PolybiusException exception) {
            fail("The exception was thrown when shouldn't be");
        }
    }
    
    /**
     * Parametrized test which checks if encoding is correct
     *
     * @param expectedResult list of letter code which is expected
     * @param givenWord word whose encoding is checked
     */
    @ParameterizedTest
    @MethodSource("streamWithListOfCodeAndResult")
    public void testEncryption(List<Integer> expectedResult, String givenWord) {
        try {
            assertTrue(expectedResult.equals(polybiusSquare.encryption(givenWord)), "Error during encoding.");
        } catch (PolybiusException exception) {
            fail("Exception was thrown unexpectedly, while encryption");
        }
    }

    /**
     * Method which return stream of argument - list of expected code letter and
     * word
     *
     * @return stream od argument
     */
    static Stream<Arguments> streamWithListOfCodeAndResult() {
        return Stream.of(
                arguments(Arrays.asList(24, 11, 51, 11), "java"),
                arguments(Arrays.asList(11, 15, 24), "AEI"),
                arguments(Arrays.asList(24, 33, 21, 34, 42, 32, 11, 44, 54, 25, 11), "InFoRmATykA"),
                arguments(Arrays.asList(35, 42, 34, 22, 42, 11, 32, 34, 52, 11, 33, 24, 15), "PROGRAMOWANIE"),
                arguments(Arrays.asList(33, 15, 44, 12, 15, 11, 33, 43), "NETBEANS")
        );
    }

    /**
     * Parametrized test which check whether in the case of invalid character is
     * used method thrown an exception
     *
     * @param word word containings invalid character
     */
    @ParameterizedTest
    @ValueSource(strings = {"matematyk4", "dodawać", "minus-", "@test"})
    public void testEncryprionWhenInvalidCharacterIsUsed(String word) {
        try {
            polybiusSquare.encryption(word);
            fail("An exception about non-English letter or charakter should be thrown.");
        } catch (PolybiusException exception) {
            assertTrue(exception.getMessage().contains("non-English"), "Unexpected message!");
        }
    }

    /**
     * Test exception when parameter of encryption() was null
     */
    @Test
    public void testEncryptionIfGivenWordIsNullShouldThrownException() {
        try {
            polybiusSquare.encryption(null);
            fail("An exception about the given word is null should be thrown.");
        } catch (PolybiusException exception) {
            assertTrue(exception.getMessage().contains("null"), "Unexpected message!");
        }
    }

    /**
     * Test encryption when parameter is empty string 
     */
    @Test
    public void testEncryptionWhenParameterIsEmptyString() {
        try {
            List<Integer> list = new ArrayList();
            assertEquals(polybiusSquare.encryption(""), list, "Error during encoding when given word is empty.");
        } catch (PolybiusException exception) {
            fail("Exception was thrown unexpectedly, while encryption");
        }
    }
}
