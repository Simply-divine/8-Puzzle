import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;


public class Board {
    private final char[] board;
    private int n;
    private int indexRow=-1;
    private int indexColumn=-1;
    private List<Board> neighbour=new ArrayList<>();

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles[0].length;
        board = new char[n*n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[n*i+j] = (char)tiles[i][j];
                if (board[n*i+j] == 0) {
                    indexRow = i;
                    indexColumn = j;
                }
            }
        }

    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {{1,3,0},{4,5,6},{7,8,2}};
        Board b=new Board(tiles);
        for (Board b1:b.neighbors()) {
            System.out.println(b1);
        }
    }

    private Board exch(char[] board, int indexRow, int indexColumn, int i, int indexColumn1) {
        int n1=n;
        int[][] tempboard=new int[n1][n1];
        for (int j = 0; j < n; j++) {
            for (int k = 0; k <n ; k++) {
                tempboard[j][k]=(int)board[n*j+k];
            }
        }
        int temp = tempboard[indexRow][indexColumn];
        tempboard[indexRow][indexColumn] = tempboard[i][indexColumn1];
        tempboard[i][indexColumn1] = temp;
        Board result=new Board(tempboard);
        return result;
    }
//    private void createGoalBoard(int n) {
//        goal = new int[n][n];
//        for (int i = 0; i <n ; i++) {
//            for (int j = 0; j <n ; j++) {
//                goal[i][j]=n*i+j+1;
//            }
//        }
//        goal[n-1][n-1]=;
//    }

    private boolean isIndex(int row, int column) {
        return row < n && row >= 0 && column >= 0 && column < n;
    }

    // string representation of this board
    public String toString() {
        String str;
        str = n + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                str += ((int)board[n*i+j]) + " ";
            }
            str += "\n";
        }
        return str;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[n*i+j] != (char)0)
                    if (board[n*i+j] != (char)n*i+j+1) {
                        count++;
                    }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0,dest,destRow,destColumn;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[n*i+j] == (char)n*i+j+1|| board[n*i+j] == (char)0) continue;
                dest = (int)board[n*i+j];
                destRow = (dest-1)/n;
                destColumn = (dest-1)%n;
                sum += Math.abs((destRow - i)) + Math.abs((destColumn - j));
            }
        }
        return sum;
    }



//    private int findColumn(int dest) {
//        return (dest-1) % n;
//    }

//    private int findRow(int dest) {
//        return (dest-1) / n;
//    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (that.n == this.n) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (this.board[n*i+j] != that.board[n*i+j]) {
                        return false;
                    }
                }
            }
            return true;
        } else return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if(neighbour.isEmpty()) {
            if (isIndex(indexRow + 1, indexColumn))
                neighbour.add(exch(board, indexRow, indexColumn, indexRow + 1, indexColumn));
            if (isIndex(indexRow, indexColumn + 1))
                neighbour.add(exch(board, indexRow, indexColumn + 1, indexRow, indexColumn));
            if (isIndex(indexRow, indexColumn - 1))
                neighbour.add(exch(board, indexRow, indexColumn - 1, indexRow, indexColumn));
            if (isIndex(indexRow - 1, indexColumn))
                neighbour.add(exch(board, indexRow - 1, indexColumn, indexRow, indexColumn));
        }
        return neighbour;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
            if(board[0]!=(char)0&&board[n*n-1]!=(char)0){return exch(board,0,0,n-1,n-1);}
            else if(board[0]==(char)0){return exch(board,0,1,n-1,n-1);}
            else if(board[n*n-1]==(char)0)return exch(board,0,0,n-2,n-1);
            return null;
////        while(board[tempRow1][tempColumn1]==0&&tempRow1==tempRow2)tempRow1 = StdRandom.uniform(n);
//        while(board[tempRow2][tempColumn2]==0)tempColumn2 = StdRandom.uniform(n);
//        return exch(board, tempRow1, tempColumn1, tempRow2, tempColumn2);

    }


}