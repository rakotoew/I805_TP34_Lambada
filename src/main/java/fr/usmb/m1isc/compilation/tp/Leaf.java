package fr.usmb.m1isc.compilation.tp;

import java.util.ArrayList;

public class Leaf extends TreeNode{

    private Object value;

    public Leaf(Object value){
        this.value = value;
    }

    public Object getValue(){
        return value;
    }

    @Override
    public ArrayList<String> getVars(){
        return new ArrayList<>();
    }

    @Override
    public String getCode(){
        if(value.toString() == "input"){
            return "\tin eax\n";
        }
        return "\tmov eax, "+this.value.toString()+"\n";
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
