public class Engine {

    public String message = "";
    public String pf = "";

    private String expression = "";


    // string clean up
    // O(n) = 1 + 2n
    // WHERE n IS RAW STRING LENGTH
    public String normalise(String raw) {

        System.out.println(c.DEF + "-".repeat(24));
        long start = System.nanoTime();

        String expression = raw // 1
            // removes spaces on operators
            // .replaceAll("([\\^*\\/%+\\-\\(\\)])", " $0 ") // O(n)
            // removes extra spaces
            // .trim() // O(n)
            .replaceAll("\\s+", "") // O(n)
            .toUpperCase(); // O(n)

        double time = (System.nanoTime() - start) / 1000000.0;
        System.out.println(time);
        System.out.println();

        message = "<p>" + time + " ms -> <b style=\"color: green\">NORMALISATION</b></p><br>";
        
        return expression;

    }
    

    // checks for errors before proceeding
    // O(n)
    // WHERE n IS TRIMMED STRING LENGTH
    public Queue<String> validate(String expression) {

        this.expression = expression;

        boolean isValid = true;
        
        double time;
        long start = System.nanoTime();
        

        // 1 + 
        if(!ErrorHandler.tokenSize(expression)) { // 1 + 1
            time = (System.nanoTime() - start) / 1000000.0;

            System.out.println(time);
            System.out.println(c.RED + "ERROR : Invalid token amount!" + c.DEF);
            
            message = "<p>" + time + " ms -> <b style=\"color: red\">ERROR : Invalid token amount!</b></p>";
            isValid = false;
        }
        else {
            int pos = ErrorHandler.invalidChar(expression); // 1 + 1 + 1 + 1 + n + 1

            // 1 +
            if(pos > -1) { 
                time = (System.nanoTime() - start) / 1000000.0;
                
                System.out.println(time);
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
                pos = ErrorHandler.maformedExp(expression); // 1 + 1 + 1 + 1 + n + 1

                // 1 +
                if(pos > -1) {
                    time = (System.nanoTime() - start) / 1000000.0;
                    
                    System.out.println(time);
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
                    pos = ErrorHandler.zeroDiv(expression); // 1 + 1 + 1 + 1 + n + 1

                    // 1+ 
                    if(pos > -1) {
                        time = (System.nanoTime() - start) / 1000000.0;

                        System.out.println(time);
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
                        // 1 + 1 + 1 + 1 + n + 1 + 1 + 2 + n + 3n + n + n + 2 
                        pos = ErrorHandler.mismatchedPar(expression); 

                        // + 1 
                        if(pos == -1) {
                            time = (System.nanoTime() - start) / 1000000.0;

                            System.out.println(time);

                            message = 
                                "<p>" + time + " ms -> <b style=\"color: green\">VALIDATION</b></p><br>";
                        }
                        else {
                            time = (System.nanoTime() - start) / 1000000.0;
                            
                            System.out.println(time);
                            System.out.println(
                                c.RED + "ERROR : Mismatched parenthesis!\n" +
                            //     // error + "\n" + c.DEF +
                                expression + "\n"
                            );

                            // // pos = error.indexOf("v");

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
            System.out.println();

            String a[] = expression.split("(?=[+\\-*\\/%^\\(\\)])|(?<=[+\\-*\\/%^\\(\\)])");
            Queue<String> q = new Queue<>(String.class, a.length);


            for (String string : a) q.enqueue(string);

            return q;
        } 
        else return null;

    }


    // conversion functtion
    // O(n) = n + an inner loop but its not a nested one
    // WHERE n IS TOKEN SIZE
    public Queue<String> convert(Queue<String> tokens) {

        System.out.println();

        Stack<String> stack = new Stack<>(String.class, tokens.getCapacity());
        Queue<String> postfix = new Queue<>(String.class, tokens.getCapacity());
        String token; 

        int stackPrecedence = -1, currentPrecedence = 0;
        
        long start = System.nanoTime();
        do {
            token = tokens.dequeue(); // n * (1 + 10)

            // INSERT OPERANDS INTO QUEUE
            if(token.matches("\\d+|[A-Z]")) // n
                // 7
                postfix.enqueue(token);
                

            else {                 
                // 

                currentPrecedence = Helpers.fetchPrecedence(token); // n * (1 + 6)
                
                // PUSH OPERATORS INTO STACK
                if(stack.isEmpty() || stackPrecedence < currentPrecedence || token.equals("(")) { // n
                    // 5

                    stack.push(token); 
                    stackPrecedence = currentPrecedence;
                }

                    
                // POP OPERATORS FROM STACK
                else {
                    //

                    // parenthesis group found - pop operators until opening parenthesis
                    // 1 +
                    if(token.equals(")")) { // 2
                        while(!stack.getTop().equals("(")) { // 
                            postfix.enqueue(stack.pop());   
                        }

                        stack.pop(); // 1
                        stackPrecedence = Helpers.fetchPrecedence(stack.getTop());  // 1 + 6 + 1
                    }

                    // 1 +
                    else if(stackPrecedence >= currentPrecedence) {
                        //

                        // exponent
                        if(stackPrecedence == currentPrecedence && stackPrecedence == 2) { // 1
                            stack.push(token); // 4
                            stackPrecedence = currentPrecedence; //1
                        }

                        // higher or equal level
                        else {
                            //

                            do {
                                postfix.enqueue(stack.pop());   
                                stackPrecedence = Helpers.fetchPrecedence(stack.getTop());
                            }
                            while(stackPrecedence >= currentPrecedence && !stack.isEmpty());
                            
                            stack.push(token); // 4
                            stackPrecedence = Helpers.fetchPrecedence(stack.getTop()); // 1 + 6 + 1
                        }
                    }
                    
                }
            }
        }
        while(!tokens.isEmpty()); // n


        while(!stack.isEmpty()) 
            postfix.enqueue(stack.pop()); 


        double time = (System.nanoTime() - start) / 1000000.0;
        System.out.println(time + c.YEL);

        message = "<p>" + time + " ms -> <b style=\"color: green\">CONVERSION</b></p<br>";

        pf = postfix.iterate();
        System.out.println(c.DEF);

        return postfix;
    }


    // evaluation postfix solution
    // O(n) = 38n
    // WHERE n IS CONVERTED EXPRESSION TOKEN SIZE (NO PARENTHESIS)
    public double evaluate(Queue<String> queue) {

        System.out.println();

        boolean isValid = true;
        int n = 0;;
        
        String token;
        Stack<Double> solution = new Stack<>(Double.class, queue.getCapacity());
        
        long start = System.nanoTime();
        do {
            token = queue.dequeue(); // n * (1 + 9)
            
            if(token.matches("[A-Z]")) { // n *  (1 + O(1))
                // 2

                solution.push(-1.0);
                isValid = false;
            }
            else {
                // 

                // OPERATORS
                if("+-*/%^".contains(token)) { // n * (1 + O(1))
                    // n * 23

                    double R = solution.pop(); // 1 + 7
                    double L = solution.pop(); // 1 + 7

                    if((token.equals("/") || token.equals("%")) && R == 0) { // 1 + 1
                        //5

                        solution.push(0.0); // 4
                        isValid = false; // zero div checker // 1
                    }
                    else 
                        solution.push(Helpers.compute(L, R, token)); // 4 + 3
                }

                // OPERANDS
                else {
                    solution.push(Double.parseDouble(token)); // 1 + O(n) <- n is token length
                    n++;
                } 
                
            }
        }
        while(!queue.isEmpty() && isValid && !solution.getTop().isInfinite()); // n


        double time = (System.nanoTime() - start) / 1000000.0;
        System.out.println(time + c.YEL + "\n" + solution.getTop());
        
                    
        if(isValid && !solution.getTop().isInfinite()) {
            message = "<p><br>" + time + " ms -> <b style=\"color: green\">EVALUATION</b></p>";
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
                int e = 0;
                do {
                    if(Character.isDigit(expression.charAt(e))) n--;
                    e++;
                }
                while(n > 0);
                // e--;

                int i = e;
                do i--;
                while(!((expression.charAt(i) == '(') && (expression.charAt(i-1) == '/' || expression.charAt(i-1) == '%')));

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