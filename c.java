import java.util.regex.Pattern;

public final class c {
    public static final String RED = "\u001B[31m";
    public static final String YEL = "\u001B[33m";
    public static final String DEF = "\u001B[0m";

    public static final String test1 = "(a + b) + (a" ;
    // public static final String test1 = "-a + b * c + d";
    public static final String ans1 = "-a b c * + d +";



    /*
    
    ((
    (
    (a a) a)
    
    
    (((a b) c) d e f g)

    (((a + b + c a d) a) s)
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
