import java.util.regex.Matcher;
import java.util.regex.Pattern;

import temp.*;

// unary remember
// division by zero

public class Program {
    public static void main(String[] args) {
        Engine engine = new Engine();
        String string = "a+b-a-c" ;

    
        engine.checkInput("a + b @ c - d");
engine.checkInput("x # y + z * w - u");
engine.checkInput("p $ q / r + s - t");
engine.checkInput("m ^ n + o & p - q");
engine.checkInput("a + b ! c * d - e");
engine.checkInput("x % y + z - w * v");
engine.checkInput("k + l @ m # n - o");
engine.checkInput("r & s + t ^ u / v");

engine.checkInput("a  +   b + c    - d * e");
engine.checkInput("x\t+\ty\t*\tz\t-\tw");
engine.checkInput("p +  q  *   r -  s + t");
engine.checkInput("m    +n+   o   - p * q");
engine.checkInput("a + b +    c - d * e");
engine.checkInput("x +    y * z -   w + v");
engine.checkInput("k\t\t+\t l + m - n * o");
engine.checkInput("r + s    + t    - u * v");

engine.checkInput("+ a b + c - d * e");
engine.checkInput("a + + b * c - d + e");
engine.checkInput("a b + c - - d * e + f");
engine.checkInput("a + b * * c - d + e");
engine.checkInput("a + b c - d * e + f");
engine.checkInput("a + (b + c - d * e +) e");
engine.checkInput("a + b - * c + d - e");
engine.checkInput("a + b - c * / d + e");
engine.checkInput("a ++ b + c - d * e");
engine.checkInput("a + b - c ** d + e");
engine.checkInput("a + b - c + ( ) d * e");

engine.checkInput("( a + b * c - d + e");
engine.checkInput("a + b ) * c - d + e");
engine.checkInput("( a + ( b + c ) - d * e");
engine.checkInput("a + ( b + c - d ) ) * e");
engine.checkInput("( ( a + b ) * c - d + e");
engine.checkInput("a + b * ( c - d + e ) )");
engine.checkInput("( a + b * ( c - d ) + e");
engine.checkInput("a + ( b + c * d - e");

engine.checkInput("a + b / 0 - c * d + e");
engine.checkInput("x / 0 + y * z - w + v");
engine.checkInput("( a + b ) / 0 - c + d * e");
engine.checkInput("a + b - c / 0 + d - e");
engine.checkInput("( x + y + z ) / 0 + w * v");
engine.checkInput("a / 0 / b + c - d + e");
engine.checkInput("a + b + c - d / 0");
engine.checkInput("( a + b - c + d ) / 0 + e");

    
    }
}


/*

1 / 2 * 0
1 / ()


*/