package pl.sda.testexamples.Mathematician;

import lombok.AllArgsConstructor;
import pl.sda.testexamples.Calculator.Calculator;

@AllArgsConstructor
public class Mathematician {

    private Calculator calculator;

    public int add(int a, int b) {

        if (a>100 || b>100) {
            return calculator.add(a,b);
        }

        return a+b;
    }

}
