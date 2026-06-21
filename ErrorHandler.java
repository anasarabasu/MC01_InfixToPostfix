import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ErrorHandler {

    private String expression = "";
    public String getExpression() {return expression;}


    public ErrorHandler(String expression) {this.expression = expression;}


    // public boolean hasErrors() {
        
    //     boolean isValid = true;

    //     long start = System.nanoTime();
    //     double time;

    //     if(!tokenSize(expression)) {
    //         time = (System.nanoTime() - start) / 1000000.0;

    //         System.out.println(time + " ms");
    //         System.out.println(c.RED + "ERROR : Invalid token amount!" + c.DEF);

    //         // message = "<p>" + time + " ms -> <b style=\"color: red\">ERROR : Invalid token amount!</b></p>";

    //         return false;
    //     }
        
    //     // if)
    //     int s = 0, e = s+3;

    //     do {}

    //     return true;

    // }

    // ERROR HANDLING : VALID TOKEN SIZE
    public static boolean tokenSize(String expression) {
        
        int spacer = 0;
        
        for (int i = 0; spacer < 4 && i < expression.length(); i++) 
            if(Character.isSpaceChar(expression.charAt(i))) spacer++;
        
        
        if(spacer < 4) return false;
        else return true;
        
    }

    
    // // char by char checker
    // public void checkForErrors() {

    //     int i = 0;;
    //     boolean isValid = true;

    //     Matcher invalidChar = Pattern.compile("[^\\s\\(\\)^*\\/%+\\-\\dA-Z]").matcher(expression);
        
    //     Matcher malformedExp = Pattern.compile(
    //         "[\\^*\\/%+-]\\s[\\^*\\/%+-]|" + // a + - b c
    //         "^[\\^*\\/%+-]\\s[\\dA-Z]|"+ // (+ a b) 
    //         "[\\dA-Z]\\s[\\^*\\/%+-]$|"+ // (a b +)
    //         "[\\dA-Z]\\s[\\dA-Z]|" + // (a b)
    //         "[\\^*\\/%+-]\\s\\)|" + // + )
    //         "\\(\\s[\\^*\\/%+-]|" + // ( +
    //         "\\)\\s[\\dA-Z]|" +  // ) a
    //         "[\\dA-Z]\\s\\(|" +  // b (
    //         "\\)\\s\\(|" + // ) (
    //         "\\(\\s\\)" // ( )
    //     ).matcher(expression); 

    //     Matcher zeroDic = Pattern.compile("\\/\\s0").matcher(expression);


    //     long start = System.nanoTime();


    //     do {
    //         String search = expression.substring(i, i+2);
            

    //         i++;
    //     } 
    //     while (isValid);


    //     System.out.println((System.nanoTime() - start) / 1000000.0);
    
    // }


    // ERROR HANDLING : INVALID CHARACTERS
    public static int invalidChar(String expression) {
        
        Matcher m = Pattern.compile("[^\\s\\(\\)^*\\/%+\\-\\dA-Z]").matcher(expression);

        if(m.find()) return m.start();
        else return -1;

    }


    // ERROR HANDLING : MALFORMED EXPRESSIONS
    public static int maformedExp(String expression) {

        Matcher m = Pattern.compile(
            "[\\^*\\/%+-]\\s[\\^*\\/%+-]|" + // a + - b c
            "^[\\^*\\/%+-]\\s[\\dA-Z]|"+ // (+ a b) 
            "[\\dA-Z]\\s[\\^*\\/%+-]$|"+ // (a b +)
            "[\\dA-Z]\\s[\\dA-Z]|" + // (a b)
            "\\d[A-Z]|[A-Z]\\d|" + // 2a
            "[\\^*\\/%+-]\\s\\)|" + // + )
            "\\(\\s[\\^*\\/%+-]|" + // ( +
            "\\)\\s[\\^*\\/%+-]$|" + // ) +
            "^[\\^*\\/%+-]\\s\\(|" + // + (
            "\\)\\s[\\dA-Z]|" +  // ) a
            "[\\dA-Z]\\s\\(|" +  // b (
            "\\)\\s\\(|" + // ) (
            "\\(\\s\\)" // ( )
            // "\\(\\s[\\dA-Z]\\s\\)" // ( a )
        ).matcher(expression); 

        if(m.find()) return m.start();
        else return -1; 

    }

    public static String mismatchedPar(String expression) {

        Matcher m = Pattern.compile("[\\(\\)]").matcher(expression);

        if(m.find()) {
            String error = "";
            char[] e = expression.toCharArray();
            
            int pair = 0, index = 0;
            do {
                switch(e[index]) {
                    case ')':
                        pair--;
                        
                        if(pair < 0) error = error.concat("v");
                        else {
                            StringBuilder s = new StringBuilder(error);
                            error = s.deleteCharAt(s.lastIndexOf("v")).append(' ').toString();
                        }

                    default:
                        error = error.concat(" ");
                        break;

                    case '(':
                        pair++;
                        error = error.concat("v");
                        break;
                }
                
                index++;
            }
            while(pair >= 0  && index < e.length);

            if(pair > 0 || pair == -1) return error;
            else return null;
        }    
        else return null; // no par in exp

    }


    // ERROR HANDLING : ZERO DIVISION
    public static int zeroDiv(String expression) {

        Matcher m = Pattern.compile("\\/\\s0|%\\s0").matcher(expression);


        if(m.find()) return m.start();
        else return -1;

    }

}