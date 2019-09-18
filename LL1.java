package Ll1Parser;

import java.util.*;
import java.io.*;
import java.lang.*;


import java.util.Stack;

/**
 * Josh Foster
 * IT 327
 * LL1 Parser Assignment
 * 9/17/2019
LL1
E -> TE'
E' -> +TE'
E'-> -TE'
T -> FT
T'-> *FT'
T' -> /FT'
T'-> empty
F -> (E)
F - > n
 
 
 ==============================================================================
         n        +            -         *          /          (             )         $        
--------------------------------------------------------------------------------
E| E->T->TE'  |          |           |           |           | T->TE'    |                
------------------------------------------------------------------------------
E'|           | E'->+TE' | E'->-TE'  |          |           |  E'->empty|         |E'->empty
--------------------------------------------------------------------------------
T| T-> FT'    |          |           |          |T-> FT'    |         |  
--------------------------------------------------------------------------------
E'|           | T'->empty|T'->empty  |  T'->FT' | T'->/FT'|T'->empty  | T'->empty
--------------------------------------------------------------------
F| F->n       |          |           |          | F->(E)    |         |                                                  
---------------------------------------------------------------------------------

===============================================================================
 */
public class LL1 {

	static Scanner console = new Scanner(System.in);
   public String input="";//String for expression input from user
    private int indexOfInput=-1;
    //Stack
    Stack <String> stack=new Stack<String>();
    //Table for grammar/rules
    String [][] table=
    {
    		//FOR SIMPLICITY IN PROGRAMMING E' = K and T'= G
        {"TK",null,null,null,null,"TK",null,null}
            ,
        {null,"+TK","-TK",null,null,null,"",""}
            ,
        {"FG",null,null,null,null,"FG",null,null}
            ,
        {null,"","","*FG","/FG",null,"",""}
            ,
  
        {"n",null,null,null,null,"(E)",null,null}
    
    
    };
    String [] nonTers={"E", "K","T","G","F"};
String [] terminals={"n","+","-","*","/","(",")","$"};


public LL1(String in)
{
this.input=in;
}

private  void pushRule(String rule)
{
for(int i=rule.length()-1;i>=0;i--)
{
char ch=rule.charAt(i);
String str=String.valueOf(ch);
push(str);
}
}

    //algorithm
public void algorithm()
{

    
    push(this.input.charAt(0)+"");//
    push("E");
    //Read one token from input
    
    String token=read();
    String top=null;
    
    do
    {
        top=this.pop();
        //if top is non-terminal
        if(isNonTerminal(top))
        {
        String rule=this.getRule(top, token);
        this.pushRule(rule);
        }
        else if(isTerminal(top))
        {
        if(!top.equals(token))
{
error("this token is not corrent , By Grammer rule . Token : ("+token+")");
}
else
{

token =read();

}
        }
        else
        {
        error("Never Happens , Because top : ( "+top+" )");
        }
        if(token.equals("$"))
        {
        break;
        }
        //if top is terminal
    
    }while(true);//out of the loop when $
    
   
    if(token.equals("$"))
        {
         System.out.println("Input is Accepted by LL1 parser");   
        }
    else
    {
     System.out.println("Input is not Accepted by LL1 parser");   
    }
}

    private boolean isTerminal(String s) {
               for(int i=0;i<this.terminals.length;i++)
        {
        if(s.equals(this.terminals[i]))
        {
        return true;
        }

        }
              return false;
    }

    private boolean isNonTerminal(String s) {
        for(int i=0;i<this.nonTers.length;i++)
        {
        if(s.equals(this.nonTers[i]))
        {
        return true;
        }

        }
              return false;
    }

    private String read() {
        indexOfInput++;
        char ch=this.input.charAt(indexOfInput);
String str=String.valueOf(ch);

        return str;
    }

    private void push(String s) {
     this.stack.push(s);   
    }
        private String pop() {
   return this.stack.pop();   
    }

    private void error(String message) {
        System.out.println(message);
        throw new RuntimeException(message);
    }
    public String getRule(String non,String term)
    {
        
    int row=getnonTermIndex(non);
    int column=getTermIndex(term);
    String rule=this.table[row][column];
    if(rule==null)
    {
    	System.out.println("This input it is not accepted by LL1 Parser");
    //error("There is no Rule by this , Non-Terminal("+non+") ,Terminal("+term+") ");
    }
    return rule;
    }

    private int getnonTermIndex(String non) {
       for(int i=0;i<this.nonTers.length;i++)
       {
       if(non.equals(this.nonTers[i]))
       {
       return i;
       }
       }
       error(non +" is not NonTerminal");
       return -1;
    }

    private int getTermIndex(String term) {
              for(int i=0;i<this.terminals.length;i++)
       {
       if(term.equals(this.terminals[i]))
       {
       return i;
       }
       }
       error(term +" is not Terminal");
       return -1;
    }
    //method to convert numbers into a single char for the parser to read
    private static String convertDigits(String input){
    	//create String Builder to convert numbers into a single character for the parser
    	StringBuilder inputString = new StringBuilder(input);
    	
    	for(int i=0;i<inputString.length();i++){
    		if(Character.isDigit(inputString.charAt(i)))
    			inputString.setCharAt(i,'n');		
    	}//end of for loop
    	
    	int pointer =0;//set pointer for loop
    	
    	//iterate through the characters to remove consecutive characters for parser to read expression
    	while(pointer <inputString.length()-1){
    		
    		if(inputString.charAt(pointer) == 'n' && inputString.charAt(pointer+1) == 'n')
    			inputString.deleteCharAt(pointer); //delete consecutive characters for parser
    		else
    			pointer++;
    		
    	}//end of while
    	
    	return inputString +"";
    }//end of convertDigits method
    
       
    public static void main(String[] args) {
        // TODO code application logic here
        String inputString="";
        System.out.println("Enter expression:");
        System.out.println();
        inputString = console.next() +"$";
        inputString = convertDigits(inputString);
        System.out.println(inputString);
        LL1 parser=new LL1(inputString);//expression input from user
        parser.algorithm();
       
  
    }//end of main method

}//end of ll1 class