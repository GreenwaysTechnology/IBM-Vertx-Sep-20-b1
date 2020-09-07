package com.ibm.vertx.fp.principles;

@FunctionalInterface
interface Closure {
    void getValue(int value);
}

class ReturnFunction {
    public Closure getFunction() {
        //syntax 1;
        //  Closure closure = value -> System.out.println(value);
        //return closure;
        //syntax 2;
        return a -> System.out.println(a);
    }
}

public class FunctionAsReturnValue {
    public static void main(String[] args) {
        ReturnFunction returnFunction = new ReturnFunction();
        Closure closure = null;
        closure = returnFunction.getFunction();
        closure.getValue(1000);
    }
}
