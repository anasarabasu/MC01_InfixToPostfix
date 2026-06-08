
// unary remember
// division by zero

import java.util.LinkedList;

public class Program {
    public static void main(String[] args) {

        System.out.println();
        
        Engine engine = new Engine();
        
        int loop = 10;
        for (int i = 0; i < loop; i++) {
            
            String s = Helpers.generateExpression(50, 100);
            String exp = Helpers.normalise(s);

            System.out.print("Validation : ");
            exp = engine.validate(exp);

            if(exp != null) {
                System.out.print("\n\nConversion : ");
                Queue<String> q = engine.convert(exp.split(" "));
                
                System.out.print("\n\nEvaluation : ");
                System.out.println(engine.evaluate(q));
                
                System.out.println(c.DEF + "\n" + "-".repeat(24) + "\n");
            }

        }

        
        // double ave = total / loop / 1000000.0;
        // System.out.println(
        //     "\n" +
        //     "-".repeat(24) +
        //     "\nAvg: " + ave + " ms"
        // );


        
        

    }
}