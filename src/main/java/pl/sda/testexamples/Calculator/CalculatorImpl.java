package pl.sda.testexamples.Calculator;

import com.google.common.base.Preconditions;

public class CalculatorImpl implements Calculator {

    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public int deduct(int a, int b) {
        return a-b;
    }

    @Override
    public int multiply(int a, int b) {
        return a*b;
    }

    @Override
    public int divide(int a, int b) {
        Preconditions.checkArgument(b!=0, "Second argument can't be zero");
        return a/b;
    }

    @Override
    public Double divideDouble(Double a, Double b) {
        Preconditions.checkArgument(a!=null && b!=null,"Both arguments can't be null");
        Preconditions.checkArgument(b!=0,"Second argument can't be zero");
        return a/b;
    }
}
