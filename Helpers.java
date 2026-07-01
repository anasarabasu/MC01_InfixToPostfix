import java.util.Random;

public final class Helpers {


    // find the operator's level
    // O(1) = 1 + 1 + 1 + 1 + 1 + 1
    public static int fetchPrecedence(String token) {
        // int level = -1; // 1

        if(token != null) { // 1
            if("+-".contains(token)) // 1
                return 0; 
            else 
                if("*/%".contains(token)) // 1
                    return 1; 
                else 
                    if("^".contains(token)) // 1
                        return 2; // 1
        }
        return -1;

        // return level;
    }


    // computes depending on the operator
    public static double compute(double X, double Y, String operator) {
        
        double result = 0;

        switch (operator) {
            case "+" -> result = X + Y;
            case "-" -> result = X - Y;
            case "*" -> result = X * Y;
            case "/"  -> result = 1.0 * X / Y;
            case "%" -> result = X % Y;
            case "^" -> result = Math.pow(X, Y);
        }

        return result;

    }


    // automatically generate randomise numerical expressions
    public static String generateExpression(int maxTokens, int maxValue) {

        Random r = new Random();
        String exp = "";

        char operators[] = { // weighted
            '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', '+', 
            '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', 
            '*', '*', '*', '*',  
            '/', 
            '%', 
            '^'
        };

        boolean oSwitch = true;
        int pCount = 0; // number of parentheses
        
        // int length = r.nextInt(5, maxTokens+1);
        // length = length % 2 == 0 ? length + 1 : length;

        // int length = r.nextInt(5, maxTokens+1);
        int length = maxTokens % 2 == 0 ? maxTokens + 1 : maxTokens;

        for (int i = 0; i < length; i++) {

            // switch 
            if(oSwitch) {
                // adds parentheses
                // if(r.nextBoolean() && r.nextBoolean() && r.nextBoolean() && i < length-1){
                //     exp = exp.concat("(");
                //     pCount++;
                // }

                int x = maxTokens > 100 ? r.nextInt(maxValue+1) : r.nextInt(Math.powExact(10, maxValue));
                x = x == 0 ? x + 1 : x;
                exp = exp.concat(x+"");

                // // closing parenthesis
                // while(pCount > 0 && r.nextBoolean() && r.nextBoolean() && r.nextBoolean() && caAddPar) {
                //     exp = exp.concat(")");
                //     pCount--;
                // }

            }
            else {
                exp = exp.concat(operators[r.nextInt(0, operators.length)]+"");
            }
            
            oSwitch = !oSwitch;

        }

        while(pCount > 0) {
            exp = exp.concat(")");
            pCount--;
        }

        return exp;

    }

}
