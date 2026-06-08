import java.util.Random;

public final class Helpers {

    // string clean up
    public static String normalise(String raw) {

        String expression = raw
            .replaceAll("([\\^*\\/%+\\-\\(\\)])", " $0 ") // adds spaces on operators
            .replaceAll("\\s+", " ") // removes extra spaces
            // .replaceAll("(\\()\\s", "(") // "( " -> "("
            // .replaceAll("\\s\\)", ")") // " )" -> ")"
            .trim()
            .toUpperCase(); 

        return expression;

    }


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


    // check if operand is a variable
    public static boolean isVar(String token) {

        if(token.matches("[A-Z]")) return true;
        else return false;

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
            case "^" -> result = (int) Math.pow(X, Y);
        }

        return result;

    }


    // automatically generate randomise numerical expressions
    public static String generateExpression(int maxTokens, int maxValue) {

        Random r = new Random();
        
        String exp = "";
        
        char operators[] = {'+', '-', '*', '/', '%', '^'};
        int pCount = 0; // number of parentheses
        
        boolean oSwitch = true;
        
        int length = r.nextInt(5, maxTokens);
        
        length = length % 2 == 0 ? length + 1 : length;
        for (int i = 0; i < length; i++) {

            // switch 
            if(oSwitch) {
                // adds parentheses
                if(r.nextBoolean() && r.nextBoolean() && r.nextBoolean()){
                    exp = exp.concat("(");
                    pCount++;
                }


                exp = exp.concat(r.nextInt(maxValue) + " ");
                exp = exp.concat(operators[r.nextInt(0, 6)] + " ");
                exp = exp.concat(r.nextInt(maxValue) + " ");

                
                // closing parenthesis
                while(pCount > 0 && r.nextBoolean() && r.nextBoolean() && r.nextBoolean()) {
                    exp = exp.concat(")");
                    pCount--;
                }
            }
            else {
                exp = exp.concat(operators[r.nextInt(0, 6)] + " ");
                exp = exp.concat(r.nextInt(maxValue) + " ");
                exp = exp.concat(operators[r.nextInt(0, 6)] + " ");
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
