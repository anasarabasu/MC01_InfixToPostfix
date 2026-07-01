import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ErrorHandler {

    private String expression = "";
    public String getExpression() {return expression;}


    public ErrorHandler(String expression) {this.expression = expression;}


    // ERROR HANDLING : VALID TOKEN SIZE
    // O(1) = constant
    public static boolean tokenSize(String expression) {

        boolean canAdd = true;
        int size = 0;

        for(int i = 0; size < 5 && i < expression.length(); i++) { // 5
            if(Character.isLetterOrDigit(expression.charAt(i))) { // 4
                if(canAdd) {
                    size++; // 4
                    canAdd = false; // 4
                }
            }
            else {
                size++;
                canAdd = true;
            }
        }

        if(size < 5) // 1
            return false; // 1
        else 
            return true;
    }

    
    // ERROR HANDLING : INVALID CHARACTERS
    // O(n) = 1 + 1 + 1 + n + 1
    public static int invalidChar(String expression) {
        
                        // O(1)                                        // O(1)
        Matcher m = Pattern.compile("[^\\(\\)^*\\/%+\\-\\dA-Z]").matcher(expression);

        if(m.find()) // 1 + O(n)
            return m.start(); // 1
        else 
            return -1;

    }


    // ERROR HANDLING : MALFORMED EXPRESSIONS
    // O(n) = 1 + 1 + 1 + n + 1
    public static int maformedExp(String expression) {

        // O(1) + O(1)
        Matcher m = Pattern.compile(
            "[\\^*\\/%+-][\\^*\\/%+-]|" + // a + - b c
            "^[\\^*\\/%+-][\\dA-Z]|"+ // (+ a b) 
            "[\\dA-Z][\\^*\\/%+-]$|"+ // (a b +)
            // "[\\dA-Z][\\dA-Z]|" + // (a b)
            "\\d[A-Z]|[A-Z]\\d|" + // 2a
            "[\\^*\\/%+-]\\)|" + // + )
            "\\([\\^*\\/%+-]|" + // ( +
            "\\)[\\^*\\/%+-]$|" + // ) +
            "^[\\^*\\/%+-]\\(|" + // + (
            "\\)[\\dA-Z]|" +  // ) a
            "[\\dA-Z]\\(|" +  // b (
            "\\)\\(|" + // ) (
            "\\(\\)" // ( )
            // "\\([\\dA-Z]\\)" // ( a )
        ).matcher(expression); 

        if(m.find()) // 1 + O(n)
            return m.start(); // 1
        else 
            return -1; 

    }


    // ERROR HANDLING : MISMATCHED PARENTHESIS
    // O(n) = 1 + 1 + 1 + n + 1 + 1 + 2 + n + 3n + n + n + 2 
    public static int mismatchedPar(String expression) {

                    // O(1)                        // O(1)
        Matcher m = Pattern.compile("[\\(\\)]").matcher(expression);

        // 1 + O(n)
        if(m.find()) {
            Stack<Integer> s = new Stack<>(Integer.class, expression.length()); // 1

            char[] e = expression.toCharArray(); // 1
            int pair = 0, index = 0;  // 2
            do {
                switch(e[index]) {
                    case ')': // n * 1
                        // 3
                        pair--; // n * 1
                        
                        if(pair < 0) // n * 1
                            return index;
                        else 
                            s.pop(); // removes index of parentehsis with closing pair
                        
                        break;

                    case '(':
                        pair++;
                        s.push(index);
                        break;
                }
                
                index++; // n * 1
            }
            while(pair >= 0 && index < e.length); // n

            if(pair > 0 || pair == -1) // 1
                return s.getTop(); // 1
            else
                return -1;
        }    
        else 
            return -1; // no par in exp

    }


    // ERROR HANDLING : ZERO DIVISION
    // O(n) = 1 + 1 + 1 + n + 1
    public static int zeroDiv(String expression) {

                        // O(1)                         // O(1)
        Matcher m = Pattern.compile("\\/0|%0").matcher(expression);

        // O(n)
        if(m.find()) 
            return m.start(); // 1
        else 
            return -1;

    }

}