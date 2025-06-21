import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class assembler{

    //Char array to string
    public static String toString(char[] a)
    {
        // Creating object of String class
        String string = new String(a);
 
        return string;
    }

    //String comparing
    public static int stringCompare(String str1, String str2)
    {
  
        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);
  
        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);
  
            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }
  
        if (l1 != l2) {
            return l1 - l2;
        }

        else {
            return 0;
        }
    }
    public static void main(String[] args){

        //Defining
        String input;
        int n=0,i=-1,j,k,tem,just,tem2=0,tem3,label_index = -1;
        int tem4=0,tem5=0,f=0,tem6=0,tem7=0,tem8=0;

        int c1=0,c2=0,c3=0,c4=0,c5=0,c6=0;
        int[] d = new int[3];
        char[] de = new char[3];
        String comp="0000000",jump,dest,C_total;
        int[] ju = new int[3];
        char[] jum = new char[3];
        String co="0000000";


        //Getting the number of lines in the code
        try {
        BufferedReader Reader = new BufferedReader(new FileReader("F:/Ganesh/Amrita/Subjects/Sem 2/EOC- 2/practise/vm/factorial.asm"));
        
            while((input = Reader.readLine()) != null){
            n++;
            }
            Reader.close();
            } catch (IOException e1){
            e1.printStackTrace();
        }

        String[] Input = new String[n];

        //Getting the values to a String array
        try {
            
        BufferedReader reader = new BufferedReader(new FileReader("F:/Ganesh/Amrita/Subjects/Sem 2/EOC- 2/practise/vm/factorial.asm"));
    
                while((input = reader.readLine()) != null){
                    i++;
                    Input[i] = input;
                }
                reader.close();
    
            } catch (IOException e1){
                e1.printStackTrace();
            }

        //Removing spaces

        for(i=0;i<n;i++){
            Input[i] = Input[i].replaceAll(" ", "");  
        }
      
        //Defining
        String temp;

        //Removing comments
        for(i=0;i<n;i++){
            temp = Input[i];

            char[] temp2 = new char[temp.length()];

            for(j=0;j<temp.length();j++){
                temp2[j] = temp.charAt(j); 
            }
            
            for(j=0 ; j<temp.length()-1 ; j++){
                if(temp2[j] == '/' && temp2[j+1] == '/'){
                    
                    break;
                }

            }

            tem = j;
            if(j<temp.length()-1){
            
            char[] temp4 = new char[tem];

            for(k=0;k<tem;k++){
                temp4[k] = temp2[k];
            }

            String temp3;
            temp3 = toString(temp4);

            Input[i] = temp3;}
            
        }

        //Finding the total number of lines without space
        tem = 0;
        for(i=0;i<n;i++){
            temp = Input[i];
            
            if(temp.length() != 0){
                tem++;
            }          
            
        }

        just=tem;
        
        String[] cleaned = new String[just];

        //Removing blank lines
        k=0;
        for(i=0;i<n;i++){
            temp = Input[i];
         
            if(temp.length() != 0){
                cleaned[k] = Input[i];
                k++;
            }

        }


        //Checking labels and storing theier line numbers
        //Calculating the number of lines without labels
        
        int non_label_lines=0;
        for(i=0;i<just;i++){
            temp = cleaned[i];

            for(j=0;j<temp.length();j++){
                if(temp.charAt(j) == '('){
                    non_label_lines++;
                    break;
                }
            }
        }

        non_label_lines = just - non_label_lines;
        //System.out.println(non_label_lines);

        String[] label_removed = new String[non_label_lines];
        String[] labels = new String[just - non_label_lines];
        int[] label_line_numbers = new int[just - non_label_lines];
        String[] Output = new String[non_label_lines];

        for(i=0;i<just;i++){
            temp = cleaned[i];

            tem2=1;
            tem=0;
            for(j=0;j<temp.length();j++){
                tem4=0;
                if(temp.charAt(j) == '('){
                    tem = j;
                    label_index++;
                    tem4++;
                    break;
                }
                
            }

            for(j=tem+1;j<temp.length();j++){
                if(temp.charAt(j) == ')'){
                    tem2 = j;
                    break;
                }
            }

            if(tem4 == 1){
            char[] temp2 = new char[tem2 - (tem+1)];
            
            tem3 = 0;
            for(k=tem+1;k<tem2;k++){
                temp2[tem3] = temp.charAt(k);
                tem3++;

            }

            labels[label_index] = toString(temp2);
            label_line_numbers[label_index] = i-f;
            f++;
        }
        else
        {   
                label_removed[tem5] = temp;
                tem5++;
            }

        }

        String[] label_replaced = new String[non_label_lines];
        
        label_replaced = label_removed;

        for(i=0;i<non_label_lines;i++){
            temp = label_replaced[i];
            tem4 = 0;

            for(j=0;j<temp.length();j++){
                
                if(temp.charAt(j) == '@'){
                    

                    char[] temp2 = new char[temp.length() - (j+1)];
                    for(tem5 = j+1; tem5 < temp.length();tem5++){                        
                        temp2[tem4] = temp.charAt(tem5);
                        tem4++; 
                    }
                    String temp5;
                    temp5 = toString(temp2);
                                        
            
                    for(k=0;k<=label_index;k++){
                        tem2 = stringCompare(labels[k],temp5);
                        if(tem2 == 0){
                            temp5 = "@"+label_line_numbers[k];
                            label_replaced[i] = temp5;
                            break;                            

                        }
                    }
                    
                    break;

                }
            }

            
        }

        String[] variable = new String[non_label_lines];
        variable = label_replaced;

        for(i=0;i<non_label_lines;i++){
            temp = variable[i];
            tem4=0;
            tem7=0;

            for(j=0;j<temp.length();j++){

                if(temp.charAt(j) == '@'){

                    char[] temp2 = new char[temp.length() - (j+1)];
                    for(tem5 = j+1; tem5 < temp.length();tem5++){                        
                        temp2[tem4] = temp.charAt(tem5);
                        tem4++; 
                    }
                    String temp5;
                    temp5 = toString(temp2);

                    int a1 = stringCompare(temp5,"SCREEN");
                    int a2 = stringCompare(temp5,"KBD");
                    int a3 = stringCompare(temp5,"SP");
                    int a4 = stringCompare(temp5,"R0");
                    int a5 = stringCompare(temp5,"LCL");
                    int a6 = stringCompare(temp5,"R1");
                    int a7 = stringCompare(temp5,"ARG");
                    int a8 = stringCompare(temp5,"R2");
                    int a9 = stringCompare(temp5,"THIS");
                    int a10 = stringCompare(temp5,"R3");
                    int a11 = stringCompare(temp5,"THAT");
                    int a12 = stringCompare(temp5,"R4");
                    int a13 = stringCompare(temp5,"R5");
                    int a14 = stringCompare(temp5,"R6");
                    int a15 = stringCompare(temp5,"R7");
                    int a16 = stringCompare(temp5,"R8");
                    int a17 = stringCompare(temp5,"R9");
                    int a18 = stringCompare(temp5,"R10");
                    int a19 = stringCompare(temp5,"R11");
                    int a20 = stringCompare(temp5,"R12");
                    int a21 = stringCompare(temp5,"R13");
                    int a22 = stringCompare(temp5,"R14");
                    int a23 = stringCompare(temp5,"R15");



                    if(a1==0){
                        variable[i] = "0100000000000000";
                    }
                    else if(a2==0){
                        variable[i] = "0110000000000000";
                    }
                    else if(a3==0 || a4==0){
                        variable[i] = "0000000000000000";
                    }
                    else if(a5==0 || a6==0){
                        variable[i] = "0000000000000001";
                    }
                    else if(a7==0 || a8==0){
                        variable[i] = "0000000000000010";
                    }
                    else if(a9==0 || a10==0){
                        variable[i] = "0000000000000011";
                    }
                    else if(a11==0 || a12==0){
                        variable[i] = "0000000000000100";
                    }
                    else if(a13==0){
                        variable[i] = "0000000000000101";
                    }
                    else if(a14==0){
                        variable[i] = "0000000000000110";
                    }
                    else if(a15==0){
                        variable[i] = "0000000000000111";
                    }
                    else if(a16==0){
                        variable[i] = "0000000000001000";
                    }
                    else if(a17==0){
                        variable[i] = "0000000000001001";
                    }
                    else if(a18==0){
                        variable[i] = "0000000000001010";
                    }
                    else if(a19==0){
                        variable[i] = "0000000000001011";
                    }
                    else if(a20==0){
                        variable[i] = "0000000000001100";
                    }
                    else if(a21==0){
                        variable[i] = "0000000000001101";
                    }
                    else if(a22==0){
                        variable[i] = "0000000000001110";
                    }
                    else if(a23==0){
                        variable[i] = "0000000000001111";
                    }
                    
                    else{
                    
                    tem7 = 0;

                    for(k=0;k<temp5.length();k++){
                        if(temp2[k] != '1' && temp2[k] != '2' && temp2[k] != '3' && temp2[k] != '4' && temp2[k] != '5' && temp2[k] != '6' && temp2[k] != '7' && temp2[k] != '8' && temp2[k] != '9' && temp2[k] != '0' ){
                                tem7++;
                        }
                        else{
                            break;
                        }
                    }

                    if(tem7 != 0){
                        tem8++;
                    }

                    break;
                }
            }
        }
    }
    
    String[] variable_name = new String[tem8];

    tem6=0;
    for(i=0;i<non_label_lines;i++){
        temp = variable[i];
        tem4=0;
        tem7=0; 

        for(j=0;j<temp.length();j++){

            if(temp.charAt(j) == '@'){

                tem7 = 0;
                char[] temp2 = new char[temp.length() - (j+1)];
                for(tem5 = j+1; tem5 < temp.length();tem5++){                        
                    temp2[tem4] = temp.charAt(tem5);
                    tem4++; 
                }
                String temp5;
                temp5 = toString(temp2);

                for(k=0;k<temp5.length();k++){
                    if(temp2[k] != '1' && temp2[k] != '2' && temp2[k] != '3' && temp2[k] != '4' && temp2[k] != '5' && temp2[k] != '6' && temp2[k] != '7' && temp2[k] != '8' && temp2[k] != '9' && temp2[k] != '0' ){
                            tem7++;
                    }
                    else{
                        break;
                    }
                }

                if(tem7 != 0){
                    variable_name[tem6] = temp5;
                    tem6++;
                }

            }
        }
    }

        variable_name = Arrays.stream(variable_name).distinct().toArray(String[]::new);

        tem = 0;

        for(i=0;i<non_label_lines;i++){
            temp = variable[i];

            if(temp.charAt(0) == '@'){
                tem++;
            }

        }

        String[] A_ins = new String[tem];

        tem6=0;
        for(i=0;i<non_label_lines;i++){
            temp = variable[i];

            if(temp.charAt(0) == '@'){
                A_ins[tem6] = temp.replaceAll("@","");
                tem6++;
            }

        }


        tem7 = 16;
        String[] variable_register = new String[variable_name.length];
        k=0;

        for(i=0;i<variable_name.length;i++){
            tem6=0;

            String s = Integer.toString(tem7);

            for(String mg : A_ins){
                int var1 = stringCompare(s,mg);
                if(var1 == 0){
                    tem6 = 1;
                }
            }

            if(tem6 == 1){
                tem7++;
                i--;
            }
            else{
            variable_register[k] = s;
            k++;
            tem7++;
            }
        }

        for(i=0;i<variable_name.length;i++){
            temp = variable_name[i];

            for(j=0;j<non_label_lines;j++){

                String temp2 = "@" +temp;

                int var1 = stringCompare(temp2,variable[j]);

                if(var1 == 0){
                    variable[j] = variable[j].replaceAll(temp,variable_register[i]);
                }
            }
        }

        for(i=0;i<non_label_lines;i++){
            temp = variable[i];
            if(temp.charAt(0) == '@'){

                variable[i] = variable[i].replaceAll("@","");

                int var2 = Integer.parseInt(variable[i]);
                int[] bin = new int[15];
                char[] bina = new char[15];
                
                for(j=0;j<15;j++){
                    bin[j] = var2%2;
                    var2 = var2/2;
                }
                for(j=0;j<15;j++){
                    bina[14-j] = (char)(bin[j]+48);
                }

                variable[i] = toString(bina);
                variable[i] = "0"+variable[i];


            }
        }

        String[] C_ins = new String[non_label_lines];

        C_ins = variable;


        for(i=0;i<non_label_lines;i++){
            temp = C_ins[i];
            tem6 = 0;

            for(j=0;j<temp.length();j++){
                if(temp.charAt(j) != '0' && temp.charAt(j) != '1'){
                    tem6 = 1;
                    break;
                }
            }

            if(tem6 == 1){

                for(j=0;j<temp.length();j++){
                    tem7 = 0;
                    c3=0;
                    if(temp.charAt(j) == '='){
                        tem7 = 1;
                        c1 = j;
                        c5=j;
                        c3=1;
                        break;
                    }
                }

                if(tem7 == 1){
                    d[0]=0;
                    d[1]=0;
                    d[2]=0;

                    for(j=0;j<c1;j++){

                        if(temp.charAt(j) == 'A'){
                            d[2]=1;
                        }
                        else if(temp.charAt(j) == 'M'){
                            d[0]=1;
                        }
                        else if(temp.charAt(j) == 'D'){
                            d[1]=1;
                        }
                    }

                    for(j=0;j<3;j++){
                        de[2-j] = (char) (d[j]+48);
                    }

                    dest = toString(de);
;
                }
                else{
                    dest = "000";
                }

                for(j=0;j<temp.length();j++){
                    tem7 = 0;
                    c4=0;
                    if(temp.charAt(j) == ';'){
                        tem7 = 1;
                        c1 = j;
                        c6=j;
                        c4=1;
                        break;
                    }
                }

                if(tem7 == 1){
                    ju[0] = 0;
                    ju[1] = 0;
                    ju[2] = 0;

                    for(j=c1+1,c2=0;j<temp.length();j++,c2++){
                        jum[c2] = temp.charAt(j);
                    }

                    jump = toString(jum);

                    int a1 = stringCompare(jump,"JMP");
                    int a2 = stringCompare(jump,"JEQ");
                    int a3 = stringCompare(jump,"JNE");
                    int a4 = stringCompare(jump,"JGE");
                    int a5 = stringCompare(jump,"JLE");
                    int a6 = stringCompare(jump,"JGT");
                    int a7 = stringCompare(jump,"JLT");

                    if(a1 == 0){
                        jump = "111";
                    }
                    else if(a2 == 0){
                        jump = "010";
                    }
                    else if(a3 == 0){
                        jump = "101";
                    }
                    else if(a4 == 0){
                        jump = "011";
                    }
                    else if(a5 == 0){
                        jump = "110";
                    }
                    else if(a6 == 0){
                        jump = "001";
                    }
                    else if(a7 == 0){
                        jump = "100";
                    }

                    
                }
                else{
                    jump = "000";
                }


                co="";   

                if(c3 == 1 && c4 ==1){                    
                    for(k=c5+1;k<c6;k++){
                        co = co + temp.charAt(k);
                    }
                }
                else if(c3==1){
                    for(k=c5+1;k<temp.length();k++){
                        co = co + temp.charAt(k);
                    }                    
                }
                else if(c4 ==1){
                    for(k=0;k<c6;k++){
                        co = co + temp.charAt(k);
                    }
                }
                else{
                    co = "0000000";
                }

                if(co.length() == 1){

                    int a1 = stringCompare(co,"0");
                    int a2 = stringCompare(co,"1");
                    int a3 = stringCompare(co,"D");
                    int a4 = stringCompare(co,"A");
                    int a5 = stringCompare(co,"M");

                    if(a1 == 0){
                        comp = "0101010";
                    }
                    else if(a2 == 0){
                        comp = "0111111";
                    }
                    else if(a3 == 0){
                        comp = "0001100";
                    }
                    else if(a4 == 0){
                        comp = "0110000";
                    }
                    else if(a5 == 0){
                        comp = "1110000";
                    }

                }
                else if(co.length() == 2){

                    int a1 = stringCompare(co,"-1");
                    int a2 = stringCompare(co,"!D");
                    int a3 = stringCompare(co,"!A");
                    int a4 = stringCompare(co,"!M");
                    int a5 = stringCompare(co,"-D");
                    int a6 = stringCompare(co,"-A");
                    int a7 = stringCompare(co,"-M");

                    if(a1 == 0){
                        comp = "0111010";
                    }
                    else if(a2 == 0){
                        comp = "0001101";
                    }
                    else if(a3 == 0){
                        comp = "0110001";
                    }
                    else if(a4 == 0){
                        comp = "1110001";
                    }
                    else if(a5 == 0){
                        comp = "0001111";
                    }
                    else if(a6 == 0){
                        comp = "0110011";
                    }
                    else if(a7 == 0){
                        comp = "1110011";
                    }

                }
                else if(co.length() == 3){

                    int a1= stringCompare(co,"D+1");
                    int a2= stringCompare(co,"1+D");
                    int a3= stringCompare(co,"A+1");
                    int a4= stringCompare(co,"1+A");
                    int a5= stringCompare(co,"M+1");
                    int a6= stringCompare(co,"1+M");
                    int a7= stringCompare(co,"D-1");
                    int a8= stringCompare(co,"A-1");
                    int a9= stringCompare(co,"M-1");
                    int a10= stringCompare(co,"D+A");
                    int a11= stringCompare(co,"A+D");
                    int a12= stringCompare(co,"D+M");
                    int a13= stringCompare(co,"M+D");
                    int a14= stringCompare(co,"D-A");
                    int a15= stringCompare(co,"D-M");
                    int a16= stringCompare(co,"A-D");
                    int a17= stringCompare(co,"M-D");
                    int a18= stringCompare(co,"D&A");
                    int a19= stringCompare(co,"A&D");
                    int a20= stringCompare(co,"D&M");
                    int a21= stringCompare(co,"M&D");
                    int a22= stringCompare(co,"D|A");
                    int a23= stringCompare(co,"A|D");
                    int a24= stringCompare(co,"M|D");
                    int a25= stringCompare(co,"D|M");

                    if(a1 == 0 || a2==0){
                        comp = "0011111";
                    }
                    else if(a3 == 0 || a4==0){
                        comp = "0110111";
                    }
                    else if(a5 == 0 || a6==0){
                        comp = "1110111";
                    }
                    else if(a7 == 0){
                        comp = "0001110";
                    }
                    else if(a8 == 0){
                        comp = "0110010";
                    }
                    else if(a9 == 0){
                        comp = "1110010";
                    }
                    else if(a10 == 0 || a11==0){
                        comp = "0000010";
                    }
                    else if(a12 == 0 || a13==0){
                        comp = "1000010";
                    }
                    else if(a14 == 0){
                        comp = "0010011";
                    }
                    else if(a15== 0){
                        comp = "1010011";
                    }
                    else if(a16 == 0){
                        comp = "0000111";
                    }
                    else if(a17 == 0){
                        comp = "1000111";
                    }
                    else if(a18 == 0 || a19==0){
                        comp = "0000000";
                    }
                    else if(a20 == 0 || a21==0){
                        comp = "1000000";
                    }
                    else if(a22 == 0 || a23 == 0){
                        comp = "0010101";
                    }
                    else if(a24 == 0 || a25==0){
                        comp = "1010101";
                    }
                }

                C_total = "111" + comp + dest + jump;
                
                C_ins[i] = C_total;
            }
            
        }
        

        Output = C_ins;
        
        //Passing output to hack file
         try {
         BufferedWriter writer = new BufferedWriter(new FileWriter("F:/Ganesh/Amrita/Subjects/Sem 2/EOC- 2/practise/vm/factorial.hack"));
             for(i=0;i<non_label_lines-1;i++){
                 writer.write(Output[i]+"\n");
               }
             writer.write(Output[non_label_lines-1]);
             writer.close();
         } catch (IOException e2){
             e2.printStackTrace();
         }


    }
}