package fr.usmb.m1isc.compilation.tp;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private String operator;
    private TreeNode expr1, expr2;
    private static int nb_WHILE = 0;
    private static int nb_IF = 0;
    private static int nb_GT = 0;

    public TreeNode(){}

    public TreeNode(String operator, TreeNode expr1, TreeNode expr2){
        this.operator = operator;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public TreeNode getExpr1(){
        return expr1;
    }

    public TreeNode getExpr2(){
        return expr2;
    }

    public List<String> getVars() {
        List<String> vars = new ArrayList<>();
        if (operator == "let") {
            vars.add((String) ((Leaf)expr1).getValue());
        } else {
            List<String> expr1Vars = expr1.getVars();
            for(int i = 0; i < expr1Vars.size(); ++i){
                String var = expr1Vars.get(i);
                if(vars.indexOf(var)==-1){
                    vars.add(var);
                }
            }
            if(expr2 != null) {
                List<String> expr2Vars = expr2.getVars();
                for(int i = 0; i < expr2Vars.size(); ++i){
                    String var = expr2Vars.get(i);
                    if(vars.indexOf(var)==-1){
                        vars.add(var);
                    }
                }
            }
        }
        return vars;
    }

    public String getCode(){
        String code = "";
        int no_gt = 0;
        switch(operator){
            case "let":
                code += expr2.getCode();
                code += "\tmov "+((Leaf)expr1).getValue()+", eax\n";
                break;
            case "*":
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tmul eax, ebx\n";
                break;
            case "+":
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tadd eax, ebx\n";
                break;
            case "/":
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tdiv ebx, eax\n";
                code += "\tmov eax, ebx\n";
                break;
            case "-":
                code += expr1.getCode();
                code += "\tpush eax\n";
                if(expr2 != null){
                    code += expr2.getCode();
                    code += "\tpop ebx\n";
                } else {
                    code += "\tmov ebx, 0\n";
                }
                code += "\tsub ebx, eax\n";
                code += "\tmov eax, ebx\n";
                break;
            case "%":
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tpush ebx\n";
                code += "\tdiv ebx, eax\n";
                code += "\tmul eax, ebx\n";
                code += "\tpop ebx\n";
                code += "\tsub ebx, eax\n";
                code += "\tmov eax, ebx\n";
                break;
            case "output":
                code += expr1.getCode();
                code += "\tout eax\n";
                break;
            case ";":
                code += expr1.getCode();
                if(expr2 != null) {
                    code += expr2.getCode();
                }
                break;
            case "while":
                int no_while = ++nb_WHILE;
                code += "debut_while_" + no_while + ":\n";
                code += expr1.getCode();
                code += "\tjz sortie_while_" + no_while + "\n";
                code += expr2.getCode();
                code += "\tjmp debut_while_" + no_while + "\n";
                code += "sortie_while_" + no_while + ":\n";
                break;
            case "if":
                int no_if = ++nb_IF;
                code += expr1.getCode();
                code += "\tjz else_" + no_if + "\n";
                code += expr2.getExpr1().getCode();
                code += "\tjmp sortie_if_" + no_if + "\n";
                code += "else_" + no_if + ":\n";
                code += expr2.getExpr2().getCode();
                code += "sortie_if_" + no_if +":\n";
                break;
            case "<":
                no_gt = ++nb_GT;
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tsub eax, ebx\n";
                code += "\tjle faux_gt_" + no_gt + "\n";
                code += "\tmov eax, 1\n";
                code += "\tjmp sortie_gt_" + no_gt + "\n";
                code += "faux_gt_" + no_gt + ":\n";
                code += "\tmov eax, 0\n";
                code += "sortie_gt_" + no_gt + ":\n";
                break;
            case "<=":
                no_gt = ++nb_GT;
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tsub eax, ebx\n";
                code += "\tjl faux_gt_" + no_gt + "\n";
                code += "\tmov eax, 1\n";
                code += "\tjmp sortie_gt_" + no_gt + "\n";
                code += "faux_gt_" + no_gt + ":\n";
                code += "\tmov eax, 0\n";
                code += "sortie_gt_" + no_gt + ":\n";
                break;
            case ">":
                no_gt = ++nb_GT;
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tsub eax, ebx\n";
                code += "\tjge faux_gt_" + no_gt + "\n";
                code += "\tmov eax, 1\n";
                code += "\tjmp sortie_gt_" + no_gt + "\n";
                code += "faux_gt_" + no_gt + ":\n";
                code += "\tmov eax, 0\n";
                code += "sortie_gt_" + no_gt + ":\n";
                break;
            case ">=":
                no_gt = ++nb_GT;
                code += expr1.getCode();
                code += "\tpush eax\n";
                code += expr2.getCode();
                code += "\tpop ebx\n";
                code += "\tsub eax, ebx\n";
                code += "\tjg faux_gt_" + no_gt + "\n";
                code += "\tmov eax, 1\n";
                code += "\tjmp sortie_gt_" + no_gt + "\n";
                code += "faux_gt_" + no_gt + ":\n";
                code += "\tmov eax, 0\n";
                code += "sortie_gt_" + no_gt + ":\n";
                break;
            case "not":
                no_gt = ++nb_GT;
                code += expr1.getCode();
                code += "\tjnz faux_gt_" + no_gt + "\n";
                code += "\tmov eax, 1\n";
                code += "\tjmp sortie_gt_" + no_gt + "\n";
                code += "faux_gt_" + no_gt + ":\n";
                code += "\tmov eax, 0\n";
                code += "sortie_gt_" + no_gt + ":\n";
                break;
            case "or":
                no_gt = ++nb_GT;
                code += expr1.getCode();
                code += "\tjnz sortie_gt" + no_gt + "\n";
                code += expr2.getCode();
                code += "sortie_gt" + no_gt + ":\n";
                break;
            case "and":
                no_gt = ++nb_GT;
                code += expr1.getCode();
                code += "\tjz sortie_gt" + no_gt + "\n";
                code += expr2.getCode();
                code += "sortie_gt" + no_gt + ":\n";
                break;
        }
        return code;
    }

    public String compile(){
        List<String> vars = this.getVars();
        String dataSegment = "DATA SEGMENT\n";
        for(int i = 0; i < vars.size(); ++i){
            dataSegment += "\t"+vars.get(i)+" DD\n";
        }
        dataSegment += "DATA ENDS\n";
        String codeSegment = "CODE SEGMENT\n";
        codeSegment += this.getCode();
        codeSegment += "CODE ENDS\n";
        return dataSegment+codeSegment;
    }

    @Override
    public String toString() {
        String str = "";
        str += '(';
        str += operator + ' ';
        str += expr1.toString();
        if(expr2 != null){
            str += ' ';
            str += expr2.toString();
        }
        str += ')';
        return str;
    }
}
