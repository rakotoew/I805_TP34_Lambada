package fr.usmb.m1isc.compilation.tp;

public enum Types {
    INPUT("input"),
    OUTPUT("output"),
    INT(""),
    BOOL(""),
    VAR(""),
    ASSIGN("="),
    PLUS("+"),
    MINUS("-"),
    MUL("*"),
    DIV("/"),
    MOD("%"),
    AND("and"),
    OR("or"),
    NOT("not"),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    SEQUENCE(";");

    private String ope;

    private Types(String ope){
        this.ope = ope;
    }

}
