package plc.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Contains JUnit tests for {@link Regex}. A framework of the test structure 
 * is provided, you will fill in the remaining pieces.
 *
 * To run tests, either click the run icon on the left margin, which can be used
 * to run all tests or only a specific test. You should make sure your tests are
 * run through IntelliJ (File > Settings > Build, Execution, Deployment > Build
 * Tools > Gradle > Run tests using <em>IntelliJ IDEA</em>). This ensures the
 * name and inputs for the tests are displayed correctly in the run window.
 */
public class RegexTests {

    /**
     * This is a parameterized test for the {@link Regex#EMAIL} regex. The
     * {@link ParameterizedTest} annotation defines this method as a
     * parameterized test, and {@link MethodSource} tells JUnit to look for the
     * static method {@link #testEmailRegex()}.
     *
     * For personal preference, I include a test name as the first parameter
     * which describes what that test should be testing - this is visible in
     * IntelliJ when running the tests (see above note if not working).
     */
    @ParameterizedTest
    @MethodSource
    public void testEmailRegex(String test, String input, boolean success) {
        test(input, Regex.EMAIL, success);
    }

    /**
     * This is the factory method providing test cases for the parameterized
     * test above - note that it is static, takes no arguments, and has the same
     * name as the test. The {@link Arguments} object contains the arguments for
     * each test to be passed to the function above.
     */
    public static Stream<Arguments> testEmailRegex() {
        return Stream.of(
                Arguments.of("Alphanumeric", "thelegend27@gmail.com", true),
                Arguments.of("UF Domain", "otherdomain@ufl.edu", true),
                Arguments.of("Missing Domain Dot", "missingdot@gmailcom", false),
                Arguments.of("Symbols", "symbols#$%@gmail.com", false),
                Arguments.of("Missing Asperand", "whoknewtheatsignhadanamegmail.com", false),
                Arguments.of("0-character TLD", "email@ufl.", false),
                Arguments.of("1-character website", "september4@g.com", true),
                Arguments.of("\"-\" website", "sept4@-.com", true),
                Arguments.of("1-character TLD", "myemailgoeshere@outlook.c", false),
                Arguments.of("4-character TLD", "thisismyemail@mail.info", false),
                Arguments.of("Capital Letters in TLD", "unoriginalemail@gmail.COM", false),
                Arguments.of("Newline Character in Email", "iamrunningoutofideas\n@gmail.com", false),
                Arguments.of("No Characters Before \"@\"", "@outlook.com", false),
                Arguments.of("No Characters Between \"@\" and \".\"", "qwerty@.com", true),
                Arguments.of("2-character TLD", "finalemail@aol.co", true),
                Arguments.of("A Very \"Special\" Email", ".-_-.@aol.com", true),
                Arguments.of("Testing Subdomains", "myemail@cise.ufl.edu", false),
                Arguments.of("Empty String", "", false),
                Arguments.of("Digits Only (including TLD)", "72688@46245.266", false),
                Arguments.of("Digits Only (excluding TLD)", "72688@46245.com", true),

                // FINAL TEST CASES
                Arguments.of("Local Part Missing", "@domain.tld", false),
                Arguments.of("Local Part Numeric", "123@domain.tld", true),
                Arguments.of("Local Part Valid Symbols", "._-@domain.tld", true),
                Arguments.of("Local Part Invalid Characters", "#$%@domain.tld", false),
                Arguments.of("At Sign Missing", "localdomain.tld", false),
                Arguments.of("Domain Name Missing", "local@.tld", true),
                Arguments.of("Domain Name Numbers", "local@123.tld", true),
                Arguments.of("Domain Name Hyphen", "local@a-b-c.tld", true),
                Arguments.of("Domain Name No Underscore", "local@a_b_c.tld", false),
                Arguments.of("Domain Name Invalid Characters", "local@#$%.tld", false),
                Arguments.of("Domain Period Missing", "local@domaintld", false),
                Arguments.of("Domain Subdomains", "local@sub.domain.tld", false),
                Arguments.of("TLD Uppercase Characters", "local@domain.TLD", false),
                Arguments.of("TLD Invalid Characters", "local@domain.#$%", false),
                Arguments.of("TLD 0 Characters", "local@domain.", false),
                Arguments.of("TLD 1 Character", "local@domain.a", false),
                Arguments.of("TLD 2 Characters", "local@domain.ab", true),
                Arguments.of("TLD 4+ Characters", "local@domain.abcd", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testEvenStringsRegex(String test, String input, boolean success) {
        test(input, Regex.EVEN_STRINGS, success);
    }

    public static Stream<Arguments> testEvenStringsRegex() {
        return Stream.of(
                //what has ten letters and starts with gas?
                Arguments.of("10 Characters", "automobile", true),
                Arguments.of("14 Characters", "i<3pancakes10!", true),
                Arguments.of("6 Characters", "6chars", false),
                Arguments.of("13 Characters", "i<3pancakes9!", false),
                Arguments.of("32 Characters", "This sentence has 32 characters.", false),
                Arguments.of("37 Characters", "This sentence contains 37 characters.", false),
                Arguments.of("18 Characters", "ilovewafflesmore<3", true),
                Arguments.of("20 Characters", "kind of an edge case", true),
                Arguments.of("16 Characters", "entrepreneurship", true),
                Arguments.of("12 Characters", "12characters", true),
                Arguments.of("9 Characters", "character", false),
                Arguments.of("0 Characters", "", false),
                Arguments.of("Newline Characters", " \n \n \n \n \n", true),
                Arguments.of("Tab Characters", " \t \t \t \t \t", true),
                Arguments.of("Carriage Return Characters", " \r \r \r \r \r", true),
                Arguments.of("Backspace Characters", " \b \b \b \b \b", true),
                Arguments.of("Space Characters", "          ", true),
                Arguments.of("All Symbols", "!@#$%^&*()", true),
                Arguments.of("Lots of Backslashes (but not enough)", "\\\\\\\\\\\\\\\\\\", false),
                Arguments.of("Lots of Backslashes (but it is enough)", "\\\\\\\\\\\\\\\\\\\\", true),

                // FINAL TEST CASES
                Arguments.of("Alphabetic", "abcdefghijkl", true),
                Arguments.of("Numeric", "12345678901234", true),
                Arguments.of("Symbols", "!@#$%^&*()_+,.:;", true),
                Arguments.of("Unicode", "ρ★⚡ρ★⚡ρ★⚡ρ★⚡ρ★⚡ρ★⚡", true),
                Arguments.of("Whitespace", "\t \t \t \t \t \t", false),
                Arguments.of("Periods", "..........", true),
                Arguments.of("Empty", "", false),
                Arguments.of("Eight", "abcdefgh", false),
                Arguments.of("Nine", "abcdefghi", false),
                Arguments.of("Eleventeen", "abcdefghijk", false),
                Arguments.of("Nineteen", "abcdefghijklmnopqrs", false),
                Arguments.of("Twenty", "abcdefghijklmnopqrst", true),
                Arguments.of("Twenty-One", "abcdefghijklmnopqrstu", false),
                Arguments.of("Twenty-Two", "abcdefghijklmnopqrstuv", false),
                Arguments.of("Twenty-Six", "abcdefghijklmnopqrstuvwxyz", false),
                Arguments.of("BONUS! 10 Newlines", "\n\n\n\n\n\n\n\n\n\n", true)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testIntegerListRegex(String test, String input, boolean success) {
        test(input, Regex.INTEGER_LIST, success);
    }

    public static Stream<Arguments> testIntegerListRegex() {
        return Stream.of(
                Arguments.of("Single Element", "[1]", true),
                Arguments.of("Multiple Elements", "[1,2,3]", true),
                Arguments.of("Missing Brackets", "1,2,3", false),
                Arguments.of("Missing Commas", "[1 2 3]", false),
                Arguments.of("Empty List", "[]", true),
                Arguments.of("Trailing Comma", "[1, 2, 3,]", false),
                Arguments.of("Mixed Spaces", "[1,2, 3]", true),
                Arguments.of("Newline Character", "[1, 2, \\n3]", false),
                Arguments.of("0 in List", "[0, 1, 2, 3]", false),
                //Arguments.of("Double Spaces", "[1,  2,  3]", true),
                Arguments.of("01, 02, etc.", "[01, 02, 03]", true),
                Arguments.of("Multiple Zeros", "[00000000001, 00000000002, 00000000003]", true),
                Arguments.of("Missing Opening Bracket", "1, 2, 3]", false),
                Arguments.of("Missing Closing Bracket", "[1, 2, 3", false),
                //Arguments.of("Space Before Comma", "[1, 2 ,3]", true),
                //Arguments.of("Spaces Before and After Comma", "[1 , 2 , 3]", true),
                Arguments.of("Spaces Before First Integer", "[ 1, 2, 3]", false),
                Arguments.of("Spaces After Last Integer", "[1, 2, 3 ]", false),
                Arguments.of("Double Commas", "[1,,2,,3]", false),
                Arguments.of("Larger Integers", "[123, 456, 789]", true),

                // FINAL TEST CASES
                Arguments.of("Multiple Digits", "[123]", true),
                Arguments.of("Large Number", "[9223372036854775807]", true),
                Arguments.of("Decimal Number", "[1.0]", false),
                Arguments.of("Negative Number", "[-1]", false),
                Arguments.of("Zero", "[0]", false),
                Arguments.of("Alphabetic Character", "[a]", false),
                Arguments.of("Unicode Digit", "[႑]", false),
                Arguments.of("Multiple Spaces", "[1,  2]", false),
                Arguments.of("Leading Comma", "[,2,3]", false),
                Arguments.of("Trailing Comma", "[1,2,]", false),
                Arguments.of("Duplicate Commas", "[1,,2]", false),
                Arguments.of("Commas And Spaces", "[1, 2, 3]", true),
                Arguments.of("Mixed Commas & Spaces", "[1,2, 3]", true),
                Arguments.of("Empty List", "[]", true),
                Arguments.of("Large List", "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]", true),
                Arguments.of("BONUS! Leading Zeros", "[007]", true)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testNumberRegex(String test, String input, boolean success) {
        test(input, Regex.NUMBER, success);
    }

    public static Stream<Arguments> testNumberRegex() {
        return Stream.of(
                Arguments.of("Single Digit", "1", true),
                Arguments.of("Decimal Number", "123.456", true),
                Arguments.of("Negative Number With Trailing Zero", "-1.0", true),
                Arguments.of("Trailing Decimal", "1.", false),
                Arguments.of("Leading Decimal", ".5", false),
                Arguments.of("Multiple Decimal Points", "1.2.3", false),
                Arguments.of("Explicit \"+\" Sign", "+97", true),
                Arguments.of("Leading Zeros", "000123", true),
                Arguments.of("Non-Numerical Characters", "1a2b3c", false),
                Arguments.of("Long Decimal Number", "3.1415926535897932384626433832795028841972", true),
                Arguments.of("Multiple +/- Signs", "+-888", false),
                Arguments.of("Spaces Within Number", "9 7 2024", false),
                Arguments.of("+/- Sign Not at Beginning", "6+", false),
                Arguments.of("All Zeros", "+00000.00", true),
                Arguments.of("Negative Zero", "-0", true),
                Arguments.of("Just a Negative Sign", "-", false),
                Arguments.of("Newline Character in Number", "10\n.5", false),
                Arguments.of("Consecutive Decimal Points", "1..2", false),
                Arguments.of("Decimal Number <1", "0.5", true),
                Arguments.of("The Answer to Life, the Universe, and Everything", "42", true),

                // FINAL TEST CASES
                Arguments.of("Multiple Digit Integer", "123", true),
                Arguments.of("Above Long Max", "9223372036854775808", true),
                Arguments.of("Above Float Max", "340282346638528859811704183484516925440.1", true),
                Arguments.of("Positive Integer", "+1", true),
                Arguments.of("Negative Integer", "-123", true),
                Arguments.of("Positive Decimal", "+123.456", true),
                Arguments.of("Leading Zeros", "007", true),
                Arguments.of("Trailing Zeros", "1.000", true),
                Arguments.of("Plus Sign Only", "+", false),
                Arguments.of("Minus Sign Only", "-", false),
                Arguments.of("Pipe Sign", "|1", false),
                Arguments.of("Invalid Decimal Point", "1:5", false),
                Arguments.of("Leading Decimal With Sign", "-.9", false),
                Arguments.of("Characters", "six", false),
                Arguments.of("Unicode Digit", "႑", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testStringRegex(String test, String input, boolean success) {
        test(input, Regex.STRING, success);
    }

    public static Stream<Arguments> testStringRegex() {
        return Stream.of(
                Arguments.of("Empty String", "\"\"", true),
                Arguments.of("Hello World!", "\"Hello, World!\"", true),
                Arguments.of("Tab Escape", "\"1\\t2\"", true),
                Arguments.of("Unterminated String", "\"unterminated", false),
                Arguments.of("Invalid Escape", "\"invalid\\escape\"", false),
                Arguments.of("All Possible Symbols", "\"!@#$%^&*()[]{}-_=+\\\\|;:\\'\\\"<,>./?`~\"", true),
                Arguments.of("All Escape Characters", "\"\\b\\n\\r\\t\\'\\\"\\\\\"", true),
                Arguments.of("All Letters and Digits (Plus Space)", "\" ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                        "abcdefghijklmnopqrstuvwxyz0123456789\"", true),
                Arguments.of("Uninitiated String", "uninitiated\"", false),
                Arguments.of("Improper String Literal", "not a string literal", false),
                Arguments.of("String Terminated Too Early", "\"string has been\"cut off\"", false),
                Arguments.of("Just Spaces", "\"                             \"", true),
                Arguments.of("Double Quote-ception", "\"\\\"\\\"\\\"\\\"\\\"\\\"\\\"\\\"\"", true),
                Arguments.of("A REAL Empty String", "", false),
                Arguments.of("Backslash Without a Character After It", "\"aaaaaa\\\"", false),
                Arguments.of("Single Quotes", "\'not unnecessary escaping\'", false),
                Arguments.of("Literally Just Backslashes", "\"\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"", true),
                Arguments.of("Multiple Initiated Strings", "\"#1\"\"#2\"\"#3\"", false),
                Arguments.of("Single Double Quote", "\"", false),
                Arguments.of("EVERY Possible Character in ASCII Order", "\" !\\\"#$%&\\'()*+,-./0123456789" +
                        ":;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\"", true),

                // FINAL TEST CASES
                Arguments.of("Single Character", "\"c\"", true),
                Arguments.of("Alphabetic", "\"abc\"", true),
                Arguments.of("Numeric", "\"123\"", true),
                Arguments.of("Symbols", "\"!@#$%^&*\"", true),
                Arguments.of("Unicode", "\"ρ★⚡\"", true),
                Arguments.of("Whitespace", "\" \b\t\"", true),
                Arguments.of("Escape", "\"Hello, \\nWorld!\"", true),
                Arguments.of("Alphabetic Escapes", "\"a\\bcdefghijklm\\nopq\\rs\\tuvwxyz\"", true),
                Arguments.of("Special Escapes", "\"sq\\'dq\\\"bs\\\\\"", true),
                Arguments.of("Invalid Escape", "\"abc\\0123\"", false),
                Arguments.of("Unicode Escapes", "\"a\\u0000b\\u12ABc\"", false),
                Arguments.of("Missing Quotes", "abc", false),
                Arguments.of("Single Quotes", "'abc'", false),
                Arguments.of("Trailing Characters", "\"string\"abc", false),
                Arguments.of("Unterminated Empty", "\"", false)
        );
    }

    /**
     * Asserts that the input matches the given pattern. This method doesn't do
     * much now, but you will see this concept in future assignments.
     */
    private static void test(String input, Pattern pattern, boolean success) {
        Assertions.assertEquals(success, pattern.matcher(input).matches());
    }
}
