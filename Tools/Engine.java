public class Engine {

    public String message = "";
    public String pf = "";

    private String expression = "";

    // checks for errors before proceeding
    public String validate(String expression) {

        System.out.println(c.DEF + "-".repeat(24));

        this.expression = expression;

        boolean isValid = true;
        long start = System.nanoTime();
        double time;
        

        if(!ErrorHandler.tokenSize(expression)) {
            time = (System.nanoTime() - start) / 1000000.0;

            System.out.println(time + " ms");
            System.out.println(c.RED + "ERROR : Invalid token amount!" + c.DEF);
            
            message = "<p>" + time + " ms -> <b style=\"color: red\">ERROR : Invalid token amount!</b></p>";
            isValid = false;
        }
        else {
            int pos = ErrorHandler.invalidChar(expression);

            if(pos > -1) {
                time = (System.nanoTime() - start) / 1000000.0;
                
                System.out.println(time + " ms");
                System.out.println(
                    c.RED + "ERROR : Invalid character!\n" +
                    " ".repeat(pos) + "v\n"  + c.DEF +
                    expression + "\n"
                );
                
                message = 
                    "<p>" + time + " ms -> <b style=\"color: red\">ERROR : Invalid character!</b></p>" +
                    "<p>" + expression.substring(0, pos) +
                    "<i style=\"color: red\">" + expression.substring(pos, pos+1) + "</i>" +
                    expression.substring(pos+1) + "</p>";

                isValid = false;
            }
            else {
                pos = ErrorHandler.maformedExp(expression);

                if(pos > -1) {
                    time = (System.nanoTime() - start) / 1000000.0;
                    
                    System.out.println(time + " ms");
                    System.out.println(
                        c.RED + "ERROR : Malformed expression!\n" 
                        + " ".repeat(pos) + "v\n"  + c.DEF +
                        expression + "\n"
                    );
    
                    message = 
                        "<p>" + time + " ms -> <b style=\"color: red\">ERROR : Malformed expression!</b></p>" +
                        "<p>" + expression.substring(0, pos) +
                        "<i style=\"color: red\">" + expression.substring(pos, pos+3) + "</i>" +
                        expression.substring(pos+3) + "</p>";

                    isValid = false;
                }
                else {
                    pos = ErrorHandler.zeroDiv(expression);

                    if(pos > -1) {
                        time = (System.nanoTime() - start) / 1000000.0;

                        System.out.println(time + " ms");
                        System.out.println(
                            c.RED + "ERROR : Division by zero!\n" +
                            " ".repeat(pos) + "v\n"  + c.DEF +
                            expression + "\n"
                        );

                        message = 
                            "<p>" + time + " ms -> <b style=\"color: red\">ERROR : Division by zero!</b></p>" +
                            "<p>" + expression.substring(0, pos) +
                            "<u style=\"color: red\">" + expression.substring(pos, pos+3) + "</u>" +
                            expression.substring(pos+3) + "</p>";

                        isValid = false;
                    }
                    else {
                        String error = ErrorHandler.mismatchedPar(expression);
                        
                        if(error == null) {
                            time = (System.nanoTime() - start) / 1000000.0;

                            System.out.println(time + " ms");

                            message = 
                                "<p>" + time + " ms -> <b style=\"color: green\">VALIDATION</b></p><br>";
                        }
                        else {
                            time = (System.nanoTime() - start) / 1000000.0;
                            
                            System.out.println(time + " ms");
                            System.out.println(
                                c.RED + "ERROR : Mismatched parenthesis!\n" +
                                error + "\n" + c.DEF +
                                expression + "\n"
                            );

                            pos = error.indexOf("v");

                            message = 
                                "<p>" + time + " ms -> <b style=\"color: red\">ERROR : Mismatched parenthesis!</b></p>" +
                                "<p>" + expression.substring(0, pos) +
                                "<u style=\"color: red\">" + expression.substring(pos, pos+1) + "</u>" +
                                expression.substring(pos+1) + "</p>";

                            isValid = false;
                        }
                    }
                }
            } 
        } 

        // PROCEED
        if(isValid) {
            System.out.println(c.YEL + expression + c.DEF);
            return expression;
        } 
        else return null;

    }


    // conversion functtion
    public Queue<String> convert(String[] token) {

        System.out.println();

        Stack<String> stack = new Stack<>(String.class, token.length);
        Queue<String> postfix = new Queue<>(String.class, token.length);
        
        int stackPrecedence = -1, currentPrecedence = 0;
        int index = 0;

        
        long start = System.nanoTime();
        do {

            // INSERT OPERANDS INTO QUEUE
            if(token[index].matches("\\d+|[A-Z]")) 
                postfix.enqueue(token[index]);
                

            else {                 
                currentPrecedence = Helpers.fetchPrecedence(token[index]);
                
                // PUSH OPERATORS INTO STACK
                if(stack.isEmpty() || stackPrecedence < currentPrecedence || token[index].equals("(")) {
                    stack.push(token[index]);
                    stackPrecedence = currentPrecedence;
                }

                    
                // POP OPERATORS FROM STACK
                else {
                    
                    // parenthesis group found - pop operators until opening parenthesis
                    if(token[index].contains(")")) {
                        while(!stack.getTop().equals("(")) {
                            postfix.enqueue(stack.pop());   
                        }

                        stack.pop();
                        stackPrecedence = Helpers.fetchPrecedence(stack.getTop());
                    }

                    else if(stackPrecedence >= currentPrecedence) {

                        // exponent
                        if(stackPrecedence == currentPrecedence && stackPrecedence == 2) {
                            stack.push(token[index]);
                            stackPrecedence = currentPrecedence;
                        }

                        // higher or equal level
                        else {
                            do {
                                postfix.enqueue(stack.pop());   
                                stackPrecedence = Helpers.fetchPrecedence(stack.getTop());
                            }
                            while(stackPrecedence >= currentPrecedence && !stack.isEmpty());
                            
                            stack.push(token[index]);
                            stackPrecedence = Helpers.fetchPrecedence(stack.getTop());
                        }
                    }
                    
                }
            }
            
            index++;
        }
        while(index < token.length);


        while(!stack.isEmpty()) postfix.enqueue(stack.pop());   


        double time = (System.nanoTime() - start) / 1000000.0;
        System.out.println(time  + " ms" + c.YEL);

        message = "<p>" + time + " ms -> <b style=\"color: green\">CONVERSION</b></p<br>";

        pf = postfix.iterate();
        System.out.println(c.DEF);

        return postfix;
    }


    // evaluation postfix solution
    public double evaluate(Queue<String> queue) {

        System.out.println();

        boolean isValid = true;
        int n = 0;;
        long start = System.nanoTime();

        String token;
        Stack<Double> solution = new Stack<>(Double.class, queue.getCapacity());

        do {
            token = queue.dequeue();
            
            if(token.matches("[A-Z]")) {
                // error = error.concat(token);

                solution.push(-1.0);
                isValid = false;
            }
            else {
                
                // OPERATORS
                if("+-*/%^".contains(token)) {
                    double R = solution.pop();
                    double L = solution.pop();

                    if((token.equals("/") || token.equals("%")) && R == 0) {
                        solution.push(0.0);
                        isValid = false; // zero div checker
                    }
                    else solution.push(Helpers.compute(L, R, token));
                }

                // OPERANDS
                else {
                    solution.push(Double.parseDouble(token));
                    n++;
                } 
                
            }
        }
        while(!queue.isEmpty() && isValid && !solution.getTop().isInfinite());


        double time = (System.nanoTime() - start) / 1000000.0;
        System.out.println(time +  " ms" + c.YEL + "\n" +solution.getTop());
        
                    
        if(isValid && !solution.getTop().isInfinite()) {
            message = "<p><br>" + time + " ms -> <b style=\"color: green\">EVALUATION</b></p>";

            System.out.println(solution.getTop());
            return solution.getTop();
        }
        else {
            if(solution.getTop().isInfinite()) {
                message = "<p><br>" + time + " ms -> <b style=\"color: red\">ERROR : Expression too large - overflow!</b></p>";

                return Double.POSITIVE_INFINITY;
            }
            else if(solution.getTop() < 0) {
                int pos = expression.indexOf(token);

                System.out.println(c.RED + "ERROR : Can't evaluate an expression with varibles\n" + c.DEF);

                message = 
                    "<p><br>" + time + " ms -> <b style=\"color: red\">ERROR : Can't evaluate an expression with varibles!</b></p>" +
                    "<p>" + expression.substring(0, pos) +
                    "<u style=\"color: red\">" + expression.substring(pos, pos+1) + "</u>" +
                    expression.substring(pos+1) + "</p>";

                return -1;
            }
            else {
                // String l[] = expression.spl
                System.out.println(n);
                
                int e = 0;
                do {
                    if(Character.isDigit(expression.charAt(e)) && Character.isSpaceChar(expression.charAt(e+1))) n--;
                    e++;
                }
                while(n > 0);
                // e--;

                int i = e;
                do i--;
                while(!((expression.charAt(i) == '(') && (expression.charAt(i-2) == '/' || expression.charAt(i-2) == '%')));

                System.out.println(c.RED + "ERROR : Division by zero!\n" + c.DEF);

                message = 
                    "<p><br>" + time + " ms -> <b style=\"color: red\">ERROR : Division by zero!</b></p>" +
                    "<p>" + expression.substring(0, i-2) +
                    "<u style=\"color: red\">" + expression.substring(i-2, e+2) + "</u>" +
                    expression.substring(e+2) + "</p>";

                return 0;
            }
        } 

    }

    
}

// 9 - 6 / 10 + 1 % 9 ^ 4 + 6 * 4 + 1 - 10 - 10 ^ 10 ^ 2 + 3 * 4 - 10 + ( 1 + ( 8 ^ 9 - 8 ^ 10 / ( 1 ^ 3 % 1 )^ 7 % ( 4 + 10 * 2 * 5 - 4 + 3 % 7 % 3 - 1 )+ ( 3 ^ 6 - 5 - 7 )+ 10 % 6 + ( 9 + 4 )- 3 ))