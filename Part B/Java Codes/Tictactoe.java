import java.util.*;

class Tictactoe {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        char[][] board = new char[3][3];
        int i,j,row,col,val;

        for(i=0;i<board.length;i++){
            for(j=0;j<board.length;j++){
                board[i][j] = ' ';
            }
        }

        boolean GameOver = false;
        char player = 'X';

        while(!GameOver){

            display(board);
            System.out.print("Enter the place to be entered : ");
            val = sc.nextInt();
            
            if(val >= 1 && val<=9 ){
                row = (val-1)/3;
                col = (val-1)%3;

                if(board[row][col] == ' '){
                    board[row][col] = player;
                    GameOver = over(board,player); 
                    player = (player=='X')? 'O' : 'X' ;
                }
                else{
                    System.out.println("ERROR : The location is already used");
                }
            }
            else{
                System.out.println("ERROR : Invalid Location to place a value");
            }        
        }
    
    for(i=0;i<3;i++){
        for(j=0;j<3;j++){
            if(board[i][j] == ' '){
                display(board);
                player = (player=='X')? 'O' : 'X' ; 
                System.out.println("Player "+player+ " has won the game");
                return;
            }
        }
    }

    display(board);
    System.out.println("Game Over : DRAW");
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

}