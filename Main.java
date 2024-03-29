import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Meowington on 6/16/2015.
 */
public class Main {

    public static void main(String argv[]) {

        Trie trie = new Trie();

        if (argv.length == 1)
            IOHelper.loadTrie(trie, argv[0]);
        else
            IOHelper.loadTrie(trie, "./src/dictionary.txt");

        System.out.println("Dictionary loaded!\n");
        // System.out.printf("Dictionary state size (num trie nodes): %d\n\n", trie.traverseSize());

        System.out.printf("%s\n%s\n%s\n%s\n\n",
                "Enter the letters, all next to each other, in the order that you would read them",
                "Hit enter to get next solution",
                "(left to right, top to bottom).", "To exit, enter 'q' up to two times.");

        System.out.printf("%s\n\n",
                "For example, enter 'aaaabbbbccccdddd'");

        Scanner scanner = new Scanner(System.in);
        String answer;

        do {

            System.out.print("Enter a graph: \n\t");
            answer = scanner.nextLine();

            if (answer.equals("q"))
                break;
            else if (answer.length() != 16) {
                System.out.println("Invalid input length.");
                continue;
            }

            RuzzleGraph graph = new RuzzleGraph(answer);
            graph.solveRecursively(trie);

            Collections.sort(graph.getSolutions(),
                    (x, y) -> {
                        if (x.length() > y.length())
                            return -1;
                        if (x.length() < y.length())
                            return 1;
                        return 0;
                    }
            );

            System.out.printf("\nTotal solutions: %d\n", graph.getSolutions().size());
            System.out.printf("Longest solutions: %d\n",
                    graph.getSolutions().stream().reduce("", (s, s2) -> {
                        if (s.length() < s2.length())
                            return s2;
                        else
                            return s;
                    }).length()
            );

            for (String x : graph.getSolutions()) {

                System.out.println(x.toUpperCase());
                IOHelper.loadBoardInput(answer);

                IOHelper.printSolution(graph.getHashmap().get(x));

                String s = scanner.nextLine();
                if (!s.equals(""))
                    break;
            }


        } while(true);

    }

}
