import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.math.Decryption;
import pl.polsl.math.PolybiusException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * A class that contains unit test of the class Decryprion
 *
 * @author Paulina Nieradzik
 * @version 1.0
 */
public class DecryptionTest {

    /**
     * Decryption class object use in all unit tests
     */
    private Decryption decryption;

    /**
     * Method performed before each test, initializes a new object of Decryption
     */
    @BeforeEach
    public void setUp() {
        decryption = new Decryption();
        for(int i=0; i<5; i++)
        {
            decryption.polybiusSquare.add(new ArrayList());
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                decryption.polybiusSquare.get(i).add(j, (char) (i * 5 + j + 65));
            }
        }
        decryption.polybiusSquare.get(1).set(4, 'K');
        for (int i = 2; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                decryption.polybiusSquare.get(i).add(j, (char) (i * 5 + j + 65 + 1));
            }
        }
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
            assertEquals(decryption.decryption(givenWord), expectedResult, "Error during decryption");
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
            decryption.decryption(givenWord);
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
            assertEquals(decryption.decryption(list), "", "Error during decryption when parameter is empty array");
        } catch (PolybiusException exception) {
            fail("The exception was thrown when shouldn't be");
        }
    }
}
