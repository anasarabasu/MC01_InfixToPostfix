import java.util.regex.Pattern;

public final class Constants {
    public static final String test1 = "-a + b * c + d" ;
    // public static final String test1 = "-a + b * c + d";
    public static final String ans1 = "-a ";

    /*
    +
    ----
    0  1 2  3 4
    (a + b) - c

    0 1  2 3 4
    a + (b - c)

    0 1 2 3 4 
    a ^ 2 * 1
    -a b c * + d

    0 1 2 3 4
    ( a + b )

    if any of these arent next to a  num or alpa then mismathc
    +/-

    */

}
