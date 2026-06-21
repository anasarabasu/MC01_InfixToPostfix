import java.util.Random;

public final class Helpers {


    // find the operator's level
    public static int fetchPrecedence(String token) {
        int level = -1;

        if(token != null) {
            if("+-".contains(token)) level = 0; 
            else if("*/%".contains(token)) level = 1; 
            else if("^".contains(token)) level = 2; 
        }

        return level;
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

        boolean caAddPar = false; // prevents situations like (123) + 456

        for (int i = 0; i < length; i++) {

            // switch 
            if(oSwitch) {
                caAddPar = true;

                // adds parentheses
                if(r.nextBoolean() && r.nextBoolean() && r.nextBoolean() && i < length-1){
                    caAddPar = false;
                    exp = exp.concat("( ");
                    pCount++;
                }

                exp = exp.concat(r.nextInt(Math.powExact(10, maxValue)) + 1 + " ");

                // closing parenthesis
                while(pCount > 0 && r.nextBoolean() && r.nextBoolean() && r.nextBoolean() && caAddPar) {
                    exp = exp.concat(")");
                    pCount--;
                }

            }
            else {
                exp = exp.concat(operators[r.nextInt(0, operators.length)] + " ");
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
