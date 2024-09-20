package plc.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LexerTests {

    @ParameterizedTest
    @MethodSource
    void testIdentifier(String test, String input, boolean success) {
        test(input, Token.Type.IDENTIFIER, success);
    }

    private static Stream<Arguments> testIdentifier() {
        return Stream.of(
                // INITIAL GIVEN TESTS
                Arguments.of("Alphabetic I", "getName", true),
                Arguments.of("Alphanumeric I", "thelegend27", true),
                Arguments.of("Leading Hyphen", "-five", false),
                Arguments.of("Leading Digit", "1fish2fish3fishbluefish", false),

                // ADDITIONAL GIVEN TESTS
                Arguments.of("Single Character", "a", true),
                Arguments.of("Hyphenated", "a-b-c", true),
                Arguments.of("Underscores I", "___", true),

                // FINAL TEST CASES
                Arguments.of("Alphabetic II", "abc", true),
                Arguments.of("Alphanumeric II", "abc123", true),
                Arguments.of("Underscores II", "a_b_c", true),
                Arguments.of("Hyphens", "a-b-c", true),
                Arguments.of("Leading Underscore", "_abc", true),
                Arguments.of("Capitals", "ABC", true),
                Arguments.of("Digit Letters", "1abc", false),
                Arguments.of("Underscores III", "___", true),
                Arguments.of("Short Identifier", "a", true),
                Arguments.of("Long Identifier", "abcdefghijklmnopqrstuvwxyz012346789_-", true)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testInteger(String test, String input, boolean success) {
        test(input, Token.Type.INTEGER, success);
    }

    private static Stream<Arguments> testInteger() {
        return Stream.of(
                // INITIAL GIVEN TESTS
                Arguments.of("Single Digit I", "1", true),
                Arguments.of("Decimal", "123.456", false),
                Arguments.of("Signed Decimal", "-1.0", false),
                Arguments.of("Trailing Decimal", "1.", false),
                Arguments.of("Leading Decimal", ".5", false),

                // ADDITIONAL GIVEN TESTS
                Arguments.of("Signed Integer", "+123", true),
                Arguments.of("Leading Zeros I", "007", true),

                // FINAL TEST CASES
                Arguments.of("Single Digit II", "1", true),
                Arguments.of("Multiple Digits", "123", true),
                Arguments.of("Above Long Max", "123456789123456789123456789", true),
                Arguments.of("Positive Integer", "+1", true),
                Arguments.of("Leading Zeros II", "007", true)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testDecimal(String test, String input, boolean success) {
        test(input, Token.Type.DECIMAL, success);
    }

    private static Stream<Arguments> testDecimal() {
        return Stream.of(
                // INITIAL GIVEN TESTS
                Arguments.of("Integer", "1", false),
                Arguments.of("Multiple Digits I", "123.456", true),
                Arguments.of("Negative Decimal I", "-1.0", true),
                Arguments.of("Trailing Decimal", "1.", false),
                Arguments.of("Leading Decimal", ".5", false),

                // ADDITIONAL GIVEN TESTS
                Arguments.of("Leading Zeros", "007.0", true),
                Arguments.of("Double Decimal", "1..0", false),

                // FINAL TEST CASES
                Arguments.of("Single Digits", "1.0", true),
                Arguments.of("Multiple Digits II", "123.456", true),
                Arguments.of("Above Integer Precision", "9007199254740993.0", true),
                Arguments.of("Negative Decimal II", "-1.0", true),
                Arguments.of("Trailing Zeros", "111.000", true)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testCharacter(String test, String input, boolean success) {
        test(input, Token.Type.CHARACTER, success);
    }

    private static Stream<Arguments> testCharacter() {
        return Stream.of(
                // INITIAL GIVEN TESTS
                Arguments.of("Alphabetic I", "\'c\'", true),
                Arguments.of("Newline Escape", "\'\\n\'", true),
                Arguments.of("Empty I", "\'\'", false),
                Arguments.of("Multiple I", "\'abc\'", false),

                // ADDITIONAL GIVEN TESTS
                Arguments.of("Unterminated", "\'", false),
                Arguments.of("Newline", "\'\n\'", false),

                // FINAL TEST CASES
                Arguments.of("Empty II", "''", false),
                Arguments.of("Alphabetic II", "'c'", true),
                Arguments.of("Multiple II", "'abc'", false),
                Arguments.of("Digit", "'1'", true),
                Arguments.of("Unicode", "'ρ'", true),
                Arguments.of("Space", "' '", true),
                Arguments.of("Single Quote Escape", "'\''", false),
                Arguments.of("Backslash Escape", "'\\'", false),
                Arguments.of("Unterminated Newline", "'c\n'", false),
                Arguments.of("Unterminated Empty", "'", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testString(String test, String input, boolean success) {
        test(input, Token.Type.STRING, success);
    }

    private static Stream<Arguments> testString() {
        return Stream.of(
                // INITIAL GIVEN TESTS
                Arguments.of("Empty I", "\"\"", true),
                Arguments.of("Alphabetic I", "\"abc\"", true),
                Arguments.of("Newline Escape", "\"Hello,\\nWorld\"", true),
                Arguments.of("Unterminated I", "\"unterminated", false),
                Arguments.of("Invalid Escape I", "\"invalid\\escape\"", false),

                // ADDITIONAL GIVEN TESTS
                Arguments.of("Symbols I", "\"!@#$%^&*()\"", true),
                Arguments.of("Newline Unterminated", "\"unterminated\n\"", false),

                // FINAL TEST CASES
                Arguments.of("Empty II", "\"\"", true),
                Arguments.of("Single Character", "\"c\"", true),
                Arguments.of("Alphabetic II", "\"abc\"", true),
                Arguments.of("Numeric", "\"123\"", true),
                Arguments.of("Symbols II", "\"!@#$%^&*\"", true),
                Arguments.of("Unicode", "\"ρ★⚡\"", true),
                Arguments.of("Whitespace", "\" \b\t\"", true),
                Arguments.of("Escape", "\"Hello, \\nWorld!\"", true),
                Arguments.of("Alphabetic Escapes", "\"a\\bcdefghijklm\\nopq\\rs\\tuvwxyz\"", true),
                Arguments.of("Special Escapes", "\"sq\\'dq\\\"bs\\\\\"", true),
                Arguments.of("Invalid Escape II", "\"abc\\0123\"", false),
                Arguments.of("Unicode Escapes", "\"a\\u0000b\\u12ABc\"", false),
                Arguments.of("Unterminated II", "\"unterminated", false),
                Arguments.of("Unterminated Newline", "\"unterminated\n\"", false),
                Arguments.of("Unterminated Empty", "\"", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testOperator(String test, String input, boolean success) {
        //this test requires our lex() method, since that's where whitespace is handled.
        test(input, Arrays.asList(new Token(Token.Type.OPERATOR, input, 0)), success);
    }

    private static Stream<Arguments> testOperator() {
        return Stream.of(
                // INITIAL GIVEN TESTS
                Arguments.of("Character", "(", true),
                Arguments.of("Comparison", "<=", true),
                Arguments.of("Space I", " ", false),
                Arguments.of("Tab I", "\t", false),

                // ADDITIONAL GIVEN TESTS
                Arguments.of("Symbol", "$", true),
                Arguments.of("Plus Sign", "+", true),

                // FINAL TEST CASES
                Arguments.of("Remainder", "%", true),
                Arguments.of("Unicode", "ρ", true),
                Arguments.of("Less Than or Equals", "<=", true),
                Arguments.of("Greater Than", ">", true),
                Arguments.of("Not Equals", "!=", true),
                Arguments.of("Plus", "+", true),
                Arguments.of("Hyphen", "-", true),
                Arguments.of("Space II", " ", false),
                Arguments.of("Tab II", "\t", false),
                Arguments.of("Form Feed", "\f", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testWhitespace(String test, String input, List<Token> expected) {
        test(input, expected, true);
    }

    private static Stream<Arguments> testWhitespace() {
        return Stream.of(
                // INITIAL TEST CASES
                Arguments.of("Multiple Spaces I", "one   two", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "one", 0),
                        new Token(Token.Type.IDENTIFIER, "two", 6)
                )),
                Arguments.of("Trailing Newline", "token\n", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "token", 0)
                )),
                Arguments.of("Not Whitespace I", "one\btwo", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "one", 0),
                        new Token(Token.Type.IDENTIFIER, "two", 4)
                )),

                // FINAL TEST CASES
                Arguments.of("Space", "one two", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "one", 0),
                        new Token(Token.Type.IDENTIFIER, "two", 4)
                )),
                Arguments.of("Tab", "one\ttwo", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "one", 0),
                        new Token(Token.Type.IDENTIFIER, "two", 4)
                )),
                Arguments.of("Backspace", "one\btwo", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "one", 0),
                        new Token(Token.Type.IDENTIFIER, "two", 4)
                )),
                Arguments.of("Windows EOL", "one\r\ntwo", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "one", 0),
                        new Token(Token.Type.IDENTIFIER, "two", 5)
                )),
                Arguments.of("Mixed Whitespace", "one \b\n\r\ttwo", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "one", 0),
                        new Token(Token.Type.IDENTIFIER, "two", 8)
                )),
                Arguments.of("Multiple Spaces II", "one    two", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "one", 0),
                        new Token(Token.Type.IDENTIFIER, "two", 7)
                )),
                Arguments.of("Leading Whitespace", "    token", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "token", 4)
                )),
                Arguments.of("Trailing Whitespace", "token    ", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "token", 0)
                )),
                Arguments.of("Only Whitespace", "    ", Arrays.asList(

                )),
                Arguments.of("Not Whitespace II", "verticaltab\u000Bformfeed\f", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "verticaltab", 0),
                        new Token(Token.Type.IDENTIFIER, "formfeed", 12)
                ))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testMixedToken(String test, String input, List<Token> expected) {
        test(input, expected, true);
    }

    private static Stream<Arguments> testMixedToken() {
        return Stream.of(
                // INITIAL TEST CASES
                Arguments.of("Multiple Decimals I", "1.2.3", Arrays.asList(
                        new Token(Token.Type.DECIMAL, "1.2", 0),
                        new Token(Token.Type.OPERATOR, ".", 3),
                        new Token(Token.Type.INTEGER, "3", 4)
                )),
                Arguments.of("Equals Combinations I", "!====", Arrays.asList(
                        new Token(Token.Type.OPERATOR, "!=", 0),
                        new Token(Token.Type.OPERATOR, "==", 2),
                        new Token(Token.Type.OPERATOR, "=", 4)
                )),
                Arguments.of("Weird Quotes I", "\'\"\'string\"\'\"", Arrays.asList(
                        new Token(Token.Type.CHARACTER, "'\"'", 0),
                        new Token(Token.Type.IDENTIFIER, "string", 3),
                        new Token(Token.Type.STRING, "\"'\"", 9)
                )),

                // FINAL TEST CASES
                Arguments.of("Empty", "", Arrays.asList(

                )),
                Arguments.of("All Types", "abc 123 456.789 'c' \"string\" %", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "abc", 0),
                        new Token(Token.Type.INTEGER, "123", 4),
                        new Token(Token.Type.DECIMAL, "456.789", 8),
                        new Token(Token.Type.CHARACTER, "'c'", 16),
                        new Token(Token.Type.STRING, "\"string\"", 20),
                        new Token(Token.Type.OPERATOR, "%", 29)
                )),
                Arguments.of("Leading Digit", "1fish2fish3fishbluefish", Arrays.asList(
                        new Token(Token.Type.INTEGER, "1", 0),
                        new Token(Token.Type.IDENTIFIER, "fish2fish3fishbluefish", 1)
                )),
                Arguments.of("Leading Decimal", ".5", Arrays.asList(
                        new Token(Token.Type.OPERATOR, ".", 0),
                        new Token(Token.Type.INTEGER, "5", 1)
                )),
                Arguments.of("Double Decimal", "1..0", Arrays.asList(
                        new Token(Token.Type.INTEGER, "1", 0),
                        new Token(Token.Type.OPERATOR, ".", 1),
                        new Token(Token.Type.OPERATOR, ".", 2),
                        new Token(Token.Type.INTEGER, "0", 3)
                )),
                Arguments.of("Multiple Decimals II", "1.2.3", Arrays.asList(
                        new Token(Token.Type.DECIMAL, "1.2", 0),
                        new Token(Token.Type.OPERATOR, ".", 3),
                        new Token(Token.Type.INTEGER, "3", 4)
                )),
                Arguments.of("Number Method", "1.toString()", Arrays.asList(
                        new Token(Token.Type.INTEGER, "1", 0),
                        new Token(Token.Type.OPERATOR, ".", 1),
                        new Token(Token.Type.IDENTIFIER, "toString", 2),
                        new Token(Token.Type.OPERATOR, "(", 10),
                        new Token(Token.Type.OPERATOR, ")", 11)
                )),
                Arguments.of("Not Addition", "x+10", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "x", 0),
                        new Token(Token.Type.INTEGER, "+10", 1)
                )),
                Arguments.of("Inner String", "abc\"str\"123", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "abc", 0),
                        new Token(Token.Type.STRING, "\"str\"", 3),
                        new Token(Token.Type.INTEGER, "123", 8)
                )),
                Arguments.of("Weird Quotes II", "'\"'string\"'\"", Arrays.asList(
                        new Token(Token.Type.CHARACTER, "'\"'", 0),
                        new Token(Token.Type.IDENTIFIER, "string", 3),
                        new Token(Token.Type.STRING, "\"'\"", 9)
                )),
                Arguments.of("All The Quotes", "\"\"\"\"\"\"\"\"\"\"", Arrays.asList(
                        new Token(Token.Type.STRING, "\"\"", 0),
                        new Token(Token.Type.STRING, "\"\"", 2),
                        new Token(Token.Type.STRING, "\"\"", 4),
                        new Token(Token.Type.STRING, "\"\"", 6),
                        new Token(Token.Type.STRING, "\"\"", 8)
                )),
                Arguments.of("Equals Combinations II", "!====", Arrays.asList(
                        new Token(Token.Type.OPERATOR, "!=", 0),
                        new Token(Token.Type.OPERATOR, "==", 2),
                        new Token(Token.Type.OPERATOR, "=", 4)
                )),
                Arguments.of("Spaceship Operator", "<=>", Arrays.asList(
                        new Token(Token.Type.OPERATOR, "<=", 0),
                        new Token(Token.Type.OPERATOR, ">", 2)
                )),
                Arguments.of("Right Bitshift", ">>", Arrays.asList(
                        new Token(Token.Type.OPERATOR, ">", 0),
                        new Token(Token.Type.OPERATOR, ">", 1)
                )),
                Arguments.of("Null Character", "'\0'", Arrays.asList(
                        new Token(Token.Type.CHARACTER, "'\0'", 0)
                ))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testExamples(String test, String input, List<Token> expected) {
        test(input, expected, true);
    }

    private static Stream<Arguments> testExamples() {
        return Stream.of(
                // INITIAL TEST CASES
                Arguments.of("Example 1", "LET x = 5;", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "LET", 0),
                        new Token(Token.Type.IDENTIFIER, "x", 4),
                        new Token(Token.Type.OPERATOR, "=", 6),
                        new Token(Token.Type.INTEGER, "5", 8),
                        new Token(Token.Type.OPERATOR, ";", 9)
                )),
                Arguments.of("Example 2", "print(\"Hello, World!\");", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "print", 0),
                        new Token(Token.Type.OPERATOR, "(", 5),
                        new Token(Token.Type.STRING, "\"Hello, World!\"", 6),
                        new Token(Token.Type.OPERATOR, ")", 21),
                        new Token(Token.Type.OPERATOR, ";", 22)
                )),
                Arguments.of("Example 3", "one\btwo", Arrays.asList(
                        new Token(Token.Type.IDENTIFIER, "one", 0),
                        new Token(Token.Type.IDENTIFIER, "two", 4)
                ))

                // FINAL TEST CASES (might do later but it takes too long)
                // Arguments.of("Define", "DEF f(x, y, z) DO END", Arrays.asList()),
                // Arguments.of("Declaration", "", Arrays.asList()),
                // Arguments.of("Initialization", "", Arrays.asList()),
                // Arguments.of("Assignment", "", Arrays.asList()),
                // Arguments.of("If", "", Arrays.asList()),
                // Arguments.of("For", "", Arrays.asList()),
                // Arguments.of("While", "", Arrays.asList()),
                // Arguments.of("Binary", "", Arrays.asList()),
                // Arguments.of("Function", "", Arrays.asList()),
                // Arguments.of("Chain", "", Arrays.asList())
        );
    }

    // INITIAL TEST CASES (PARSE_EXCEPTION)
    @Test
    void testException_UnterminatedString() {
        ParseException exception = Assertions.assertThrows(ParseException.class,
                () -> new Lexer("\"unterminated").lex());
        Assertions.assertEquals(13, exception.getIndex());
    }

    @Test
    void testException_UnterminatedCharacter() {
        ParseException exception = Assertions.assertThrows(ParseException.class,
                () -> new Lexer("'c").lex());
        Assertions.assertEquals(2, exception.getIndex());
    }

    @Test
    void testException_InvalidEscape() {
        ParseException exception = Assertions.assertThrows(ParseException.class,
                () -> new Lexer("\"invalid\\escape\"").lex());
        Assertions.assertEquals(9, exception.getIndex());
    }

    // FINAL TEST CASES (PARSE_EXCEPTION)
    @Test
    void testException_EmptyCharacter() {
        ParseException exception = Assertions.assertThrows(ParseException.class,
                () -> new Lexer("''").lex());
        Assertions.assertEquals(1, exception.getIndex());
    }

    @Test
    void testException_NewlineUnterminatedString() {
        ParseException exception = Assertions.assertThrows(ParseException.class,
                () -> new Lexer("\"unterminated\n\"").lex());
        Assertions.assertEquals(13, exception.getIndex());
    }

    @Test
    void testException_UnicodeEscapes() {
        ParseException exception = Assertions.assertThrows(ParseException.class,
                () -> new Lexer("\"a\\u0000b\\u12ABc\"").lex());
        Assertions.assertEquals(3, exception.getIndex());
    }

    /**
     * Tests that lexing the input through {@link Lexer#lexToken()} produces a
     * single token with the expected type and literal matching the input.
     */
    private static void test(String input, Token.Type expected, boolean success) {
        try {
            if (success) {
                Assertions.assertEquals(new Token(expected, input, 0), new Lexer(input).lexToken());
            } else {
                Assertions.assertNotEquals(new Token(expected, input, 0), new Lexer(input).lexToken());
            }
        } catch (ParseException e) {
            Assertions.assertFalse(success, e.getMessage());
        }
    }

    /**
     * Tests that lexing the input through {@link Lexer#lex()} matches the
     * expected token list.
     */
    private static void test(String input, List<Token> expected, boolean success) {
        try {
            if (success) {
                Assertions.assertEquals(expected, new Lexer(input).lex());
            } else {
                Assertions.assertNotEquals(expected, new Lexer(input).lex());
            }
        } catch (ParseException e) {
            Assertions.assertFalse(success, e.getMessage());
        }
    }

}
