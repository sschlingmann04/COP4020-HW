package plc.homework;

import java.util.regex.Pattern;

/**
 * Contains {@link Pattern} constants, which are compiled regular expressions.
 * See the assignment page for resources on regexes as needed.
 */
public class Regex {

    public static final Pattern
            EMAIL = Pattern.compile("[A-Za-z0-9._\\-]+@[A-Za-z0-9-]*\\.[a-z]{2,3}"),
            EVEN_STRINGS = Pattern.compile("((.|\\n|\\r){2}){5,10}"),
            INTEGER_LIST = Pattern.compile("\\[((0*[1-9]\\d*, ?)*0*[1-9]\\d*)?]"),
            NUMBER = Pattern.compile("^[+-]?0*\\d+(\\.\\d+0*)?$"),
            STRING = Pattern.compile("^\"([^'\"\\\\]| |\\\\[bnrt'\"\\\\])*\"$");

}
