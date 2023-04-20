package fr.usmb.m1isc.compilation.tp;

import java_cup.runtime.Symbol;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception  {
		LexicalAnalyzer yy;
		if (args.length > 0)
			yy = new LexicalAnalyzer(new FileReader(args[0])) ;
		else
			yy = new LexicalAnalyzer(new InputStreamReader(System.in)) ;
		@SuppressWarnings("deprecation")
		parser p = new parser (yy);
		Symbol s = p.parse( );
		String code = ((TreeNode)s.value).compile();

		String filename = args[0].substring(args[0].lastIndexOf("\\")+1,args[0].lastIndexOf("."));
		BufferedWriter writer = new BufferedWriter(new FileWriter( filename+".asm", false ));

		writer.append( code );
		writer.close();
    }

}
