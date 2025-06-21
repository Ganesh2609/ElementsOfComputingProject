import java.util.*;

public class Computer {

    static char computer = ' ';

    public static void main(String[] args) {    

        Scanner sc = new Scanner(System.in);
        char[][] board = new char[3][3];
        char player=' ';
        System.out.print("Do you want to play as x(1) or o(2) : ");
        int pl = sc.nextInt();
        boolean GameOver = false;

        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                board[i][j] = ' ';
            }
        }

        if(pl==1){
            player = 'x';
            computer = 'o';
        }
        else if(pl == 2){
            player = 'x';
            computer = 'x';
        }
        else{
            System.out.println("ERROR : Enter the correct value");
            System.exit(1);
        }

        while(!(GameOver)){
            if(player == computer){
                board = move(board,computer);
                GameOver = over(board,player);
                if(player == 'x' ){
                    player = 'o';
                }
                else{
                    player = 'x';
                }
            }
            else{
                display(board);
                System.out.print("Enter the place to be entered : ");
                int val = sc.nextInt(),row,col;
                
                if(val >= 1 && val<=9 ){
                    row = (val-1)/3;
                    col = (val-1)%3;

                    if(board[row][col] == ' '){
                        board[row][col] = player;

                        GameOver = over(board,player);
                        if(player == 'x' ){
                            player = 'o';
                        }
                        else{
                            player = 'x';
                        }
                    }
                    else{
                        System.out.println("ERROR : The location is already used");
                    }
                }
                else{
                    System.out.println("ERROR : Invalid Location to place a value");
                }
            }
        }

        if(player == computer){
            if(computer == 'o'){
                player = 'x';
            }
            else{
                player = 'o';
            }
        }
        

        display(board);
        
        if(win(board,computer)){
            System.out.println("You have lost the game");
        }
        else if(win(board,player)){
            System.out.println("Congragulations! You have won the game");
        }
        else{
            System.out.println("Game : DRAW");
        }
    } 
    
    
    public static char[][] move(char[][] board,char player){

        char temp;
        if(computer == 'x'){
            temp = 'o';
        }
        else{
            temp = 'x';
        }
        
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] == ' '){
                    board[i][j] = computer;
                    if(win(board,computer)){
                        board[i][j] = computer;
                        return board;
                    }
                    board[i][j] = ' ';
                }
            }
        }


        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] == ' '){
                    board[i][j] = temp;
                    if(win(board,temp)){
                        board[i][j] = computer;
                        return board;
                    }
                    board[i][j] = ' ';
                }
            }
        }

        int k=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] == ' '){
                    k++;
                }
            }
        }

        int[] possible = new int[k];
        int k1=0;

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] == ' '){
                    board[i][j] = player;
                    int pos = possibility(board,player);
                    possible[k1] = pos;
                    k1++;
                    board[i][j] = ' '; 
                }
            }
        }

        int max = possible[0];
        int index = 0;
        for (int i = 0; i < possible.length; i++) 
        {
        if (max < possible[i]) 
        {
            max = possible[i];
            index = i;
        }
        }

        k=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] == ' '){
                    if(k==index){
                        board[i][j] = player;
                        return board;
                    }
                    k++;
                }
            }
        }

        return board;
    }

    public static int possibility(char[][] board,char player){
        
        int k=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] != ' '){
                    k++;
                }
            }
        }

        if(k==9){
            char temp;
            if(computer == 'x'){
                temp = 'o';
            }
            else{
                temp = 'x';
            }

            if(win(board,computer)){
                return 1;
            }
            else if(win(board,temp)){
                return -1;
            }
            else{
                return 0;
            }
        }
        else{
            if(player == 'x'){
                player = 'o';
            }
            else{
                player = 'x';
            }

            int pos=0;

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(board[i][j] == ' '){
                        board[i][j] = player;
                        pos += possibility(board, player);
                        board[i][j] = ' ';
                    }
                }
            }

            return pos;
        }
        
    }
    
    public static void display(char[][] board) {

        System.out.println("-------");

        for(int i=0;i<board.length;i++){
            System.out.print("|");
            for(int j=0;j<board.length;j++){
                System.out.print(board[i][j] + "|");
            }
            System.out.println("\n-------");
        }
    } 

    public static boolean over(char[][] board,char player){

        for(int i=0;i<3;i++){
            if(board[i][0] == player && board[i][1] == player && board[i][2] == player){
                return true;
            }
        }

        for(int i=0;i<3;i++){
            if(board[0][i] == player && board[1][i] == player && board[2][i] == player){
                return true;
            }
        }

        for(int i = 0 ; i<=2 ; i=i+2){
            if(board[0][i] == player && board[1][1] == player && board[2][2-i] == player){
                return true;
            }
        }

        int k=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] != ' '){
                    k++;
                }
            }
        }
        
        if(k==9){
            return true;
        }

        return false;
    }

    public static boolean win(char[][] board,char player){
        for(int i=0;i<3;i++){
            if(board[i][0] == player && board[i][1] == player && board[i][2] == player){
                return true;
            }
        }

        for(int i=0;i<3;i++){
            if(board[0][i] == player && board[1][i] == player && board[2][i] == player){
                return true;
            }
        }

        for(int i = 0 ; i<=2 ; i=i+2){
            if(board[0][i] == player && board[1][1] == player && board[2][2-i] == player){
                return true;
            }
        }


        return false;
    }


}
