import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Meowington on 6/16/2015.
 */
public class IOHelper {

    enum Direction {N, NE, E, SE, S, SW, W, NW}

    private static char[][] board = new char[13][25];

    public static void loadTrie (Trie trie) {

        try {
            FileReader fr = new FileReader(new File("./src/dictionary.txt"));
            BufferedReader br = new BufferedReader(fr);

            String cur;
            while ( (cur = br.readLine()) != null)
                trie.addWord(cur);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadBoardInput (String input) {


        loadNewBoard();

        int x;
        int y;

        for (int i = 0; i < input.length(); i++) {
            x = indexToXCoord(i);
            y = indexToYCoord(i);

                board[y][x] = input.toLowerCase().charAt(i);
        }
    }

    public static void printSolution(ArrayList<Integer> indices) {

        // Modify the board with the solution.
        int prev;
        int next;

        int x;
        int y;

        for (int i = 0; i < indices.size() - 1; i++) {
            prev = indices.get(i);
            next = indices.get(i + 1);

            x = indexToXCoord(prev);
            y =indexToYCoord(prev);

            if (i == 0)
                toUpper(x, y);

            drawLine(x, y, getDirection(prev, next));
        }

        // Now print the board.
        //
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++)
                System.out.print(board[i][j]);

            System.out.println();
        }
    }

    private static void toUpper(int x, int y) {
        String s = board[y][x] + "";
        s = s.toUpperCase();
        board[y][x] = s.charAt(0);
    }

    private static void drawLine (int x, int y, Direction dir) {
        switch(dir) {
            case N: board[y - 1][x] = board[y - 2][x] = '|';
                break;
            case NE: board[y - 1][x + 2] = board[y - 2][x + 4] = '/';
                break;
            case E: board[y][x - 2] = board[y][x - 3] = board[y][x - 4] = '-';
                break;
            case SE: board[y + 1][x + 2] = board[y + 2][x + 4] = '\\';
                break;
            case S: board[y + 1][x] = board[y + 2][x] = '|';
                break;
            case SW: board[y + 1][x - 2] = board[y + 2][x - 4] = '/';
                break;
            case W: board[y][x + 2] = board[y][x + 3] = board[y][x + 4] = '-';
                break;
            case NW: board[y - 1][x - 2] = board[y - 2][x - 4] = '\\';
                break;
        }
    }

    private static int indexToYCoord(int index) {

        if (0 <= index && index <= 3)
            return 2;
        else if (4 <= index && index <= 7)
            return 5;
        else if (8 <= index && index <= 11)
            return 8;
        else if (12 <= index && index <= 15)
            return 11;

        System.out.printf("Error going to y coordinate (%d)\n", index);
        return -1;
    }

    private static int indexToXCoord(int index) {

        switch (index % 4) {
            case 0: return 3;
            case 1: return 9;
            case 2: return 15;
            case 3: return 21;
        }

        System.out.printf("Error going to x coordinate (%d)\n", index);
        return -1;
    }

    private static Direction getDirection (int from, int to) {

        if (from - 4 == to)
            return Direction.N;
        else if (from - 3 == to)
            return Direction.NE;
        else if (from + 1 == to)
            return Direction.W;
        else if (from + 5 == to)
            return Direction.SE;
        else if (from + 4 == to)
            return Direction.S;
        else if (from + 3 == to)
            return Direction.SW;
        else if (from - 1 == to)
            return Direction.E;
        else if (from - 5 == to)
            return Direction.NW;
        else {
            System.out.printf("Bad input! %d %d\n", from, to);
            return null;
        }
    }

    private static void loadNewBoard() {
        loadBlankBoard();
    }

    private static void loadBlankBoard() {
        String s[] = new String[]{
                        " _____ _____ _____ _____",
                        "|     |     |     |     |",
                        "|     |     |     |     |",
                        "|_____|_____|_____|_____|",
                        "|     |     |     |     |",
                        "|     |     |     |     |",
                        "|_____|_____|_____|_____|",
                        "|     |     |     |     |",
                        "|     |     |     |     |",
                        "|_____|_____|_____|_____|",
                        "|     |     |     |     |",
                        "|     |     |     |     |",
                        "|_____|_____|_____|_____|"
        };

        for (int i = 0; i < s.length; i++)
            loadRow(s[i], i);
    }

    private static void loadRow(String s, int row) {
        for (int i = 0; i < s.length(); i++) {
            board[row][i] = s.charAt(i);
        }
    }
}
