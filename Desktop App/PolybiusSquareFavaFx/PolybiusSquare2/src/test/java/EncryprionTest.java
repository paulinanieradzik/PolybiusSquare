
import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.math.Encryption;
import pl.polsl.math.PolybiusException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * A class that contains unit test of the class Decryprion
 *
 * @author Paulina Nieradzik
 * @version 1.1.
 */
public class EncryprionTest {

    /**
     * Encryption class object use in all unit tests
     */
    private Encryption encryption;

    /**
     * Method performed before each test, initializes a new object of Encryption
     */
    @BeforeEach
    public void setUp() {
        encryption = new Encryption();
        for(int i=0; i<5; i++)
        {
            encryption.polybiusSquare.add(new ArrayList());
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                encryption.polybiusSquare.get(i).add(j, (char) (i * 5 + j + 65));
            }
        }
        encryption.polybiusSquare.get(1).set(4, 'K');
        for (int i = 2; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                encryption.polybiusSquare.get(i).add(j, (char) (i * 5 + j + 65 + 1));
            }
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
            assertTrue(expectedResult.equals(encryption.encryption(givenWord)), "Error during encoding.");
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
            encryption.encryption(word);
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
            encryption.encryption(null);
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
            assertEquals(encryption.encryption(""), list, "Error during encoding when given word is empty.");
        } catch (PolybiusException exception) {
            fail("Exception was thrown unexpectedly, while encryption");
        }
    }
}
