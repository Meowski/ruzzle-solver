import java.util.*;
import java.util.function.Function;

/**
 * Created by Meowington on 6/16/2015.
 */
public class Trie {

    private int id = 1;
    public static class Node implements Comparable<Node> {

        private LinkedList<Node> children;
        private Character letter;
        private boolean isWord;
        private int id;
        
        public Node (Character letter, int id) {

            this.children = new LinkedList<>();
            this.isWord = false;
            this.letter = letter;
            this.id = id;
        }

        public void setIsWord() {
            this.isWord = true;
        }

        public boolean isWord() {

            return this.isWord;
        }

        public int getId() {
            return this.id;
        }

        public Node addChild (Node other) {
            if (!this.children.contains(other))
                this.children.add(other);

            return other;
        }

        public Character getLetter() {
            return this.letter;
        }

        public Node hasChild (Character ch) {
            for (Node x : children)
                if (x.getLetter().charValue() == ch.charValue())
                    return x;

            return null;
        }

        public LinkedList<Node> getChildren() {

            return this.children;
        }

        @Override
        public boolean equals( Object other ) {
            if (other == null)
                return false;
            if (! (other instanceof Node))
                return false;

            if ( ((Node)other).letter == null) {
                return this.letter == null;
            }

            return ((Node)other).letter.charValue() == this.letter.charValue();
        }

        @Override
        public int compareTo( Node other ) {

            // To catch both null case.
            if (this.letter == null && other.letter != null)
                return -1;
            else if (this.letter != null && other.letter == null)
                return 1;
            else if (this.letter == null)
                return 0;


            return this.letter.compareTo(other.letter);
        }

        @Override
        public int hashCode () {
            return this.id;
        }

        @Override
        public String toString() {
            return this.letter == null ? "" : this.letter.toString();
        }
    }

    private Node root;

    public Trie () {
        root = new Node(null, 0);
    }

    public int traverseSize () {

        Node cur = root;
        Stack<Node> frontier = new Stack<>();
        Set<Node> visited = new HashSet<>();
        frontier.push(cur);

        int result = 0;
        while (!frontier.empty()) {

            cur = frontier.pop();
            if (visited.contains(cur))
                continue;

            visited.add(cur);
            cur.getChildren().forEach(frontier::push);
            result++;
        }

        return result;
    }

    public void minimize() {

        Set<Node> F = new HashSet<>();
        Set<Node> NF = new HashSet<>();
        List<Character> A = new ArrayList<>(50);

        // Given a node, get the set that it belongs to.
        //
        HashMap<Node, Set<Node>> map = new HashMap<>();

        Set<Set<Node>> T, P;
        T = new HashSet<>();
        P = new HashSet<>();

        Function< Set<Node>, List<Set<Node>> > split = (Set<Node> states) -> {

            Set<Node> s1, s2;

            ArrayList<Set<Node>> ans = new ArrayList<>();


            for (Character ch : A) {

                s1 = new HashSet<>();
                s2 = new HashSet<>();

                // For every node, pick the first equivalence set
                // from the first transition from the first Node, and
                // see if every other node maps to that set. If it doesn't,
                // split on that set!
                //
                boolean first = true;
                Node transition = null;
                for (Node node : states) {
                    if (first) {

                        // Note, null is the failure state.  Hash maps naturally
                        // map null to null, which is what we want.
                        //
                        transition = node.hasChild(ch);
                        s1.add(node);
                        first = false;
                    }
                    else {

                       if (map.get(node.hasChild(ch)) == map.get(transition))
                           s1.add(node);
                       else
                           s2.add(node);
                    }
                }

                // If we found stuff that was different (s2 - the different set - is non empty)
                // then return these two sets.
                //
                if (!s2.isEmpty()) {

                    ans.add(s1);
                    ans.add(s2);

                    // Update the mapping.
                    //
                    for (Node n : s1) {
                        map.remove(n);
                        map.put(n, s1);
                    }
                    for (Node n : s2) {
                        map.remove(n);
                        map.put(n, s2);
                    }

                    return ans;
                }
            }

            // If we never fund anything different, just return the original set.
            //
            ans.add(states);
            return  ans;
        };


        // Main algorithm.
        //
        initPartition(F, NF, map);
        initAlphabet(A);


        A.sort(Character::compare);
        for (Character x : A)
            System.out.printf("%s, ", x.toString());
        System.out.printf("\nfinal: %d\nnotFinal: %d\n\n", F.size(), NF.size());


        T.add(F);
        T.add(NF);
        while ( ! T.equals(P) ) {
            P = T;
            T = new HashSet<>();
            for (Set<Node> set : P)
                split.apply(set).stream().forEach(T::add);
        }


        /*
        System.out.print("{");
        for (Set<Node> nodeSet : P) {
            System.out.print("(");
            for (Node node : nodeSet) {
                System.out.printf("%d,", node.getId());
            }
            System.out.print("),");
        }
        System.out.print("}\n\n");
        */


        System.out.printf("Distinguishable states: %d\n", P.size());

        // Now link up equivalent classes. We'll pick the first
        // state that the first element maps to to link
        // all other nodes up.
        //

        boolean first = true;
        Node eltToMapTo = null;
        for (Set<Node> nodeSet : P) {
            for (Node node : nodeSet) {
                if (first) {

                }
            }
        }

    }

    private void initAlphabet (List<Character> alphabet) {

        Node cur = root;
        Stack<Node> frontier = new Stack<>();
        frontier.push(cur);

        while (!frontier.empty()) {

            cur = frontier.pop();

            if ( cur.getLetter() != null )
                if ( !alphabet.contains(cur.getLetter()) )
                    alphabet.add(cur.getLetter());

            cur.getChildren().forEach(frontier::push);
        }
    }

    // Traverse our tree and create two set partitions, final
    // and non-final states.
    //
    private void initPartition (Set<Node> word, Set<Node> notWord, HashMap<Node, Set<Node>> map) {

        Node cur = root;
        Stack<Node> frontier = new Stack<>();
        frontier.push(cur);

        while (!frontier.empty()) {

            cur = frontier.pop();

            if (cur.isWord) {
                word.add(cur);
                map.put(cur, word);
            }
            else {
                notWord.add(cur);
                map.put(cur, notWord);
            }

            cur.getChildren().forEach(frontier::push);
        }
    }

    public void addWord (String s) {

        Node cur = root;
        Node next;
        for (int i = 0; i < s.length(); i++) {

            next = cur.hasChild( s.charAt(i) );
            if ( next != null) {
                cur = next;
            }
            else {
                Node toAdd;
                for ( ; i < s.length(); i++) {
                    toAdd = new Node (s.charAt(i), this.id++);
                    cur = cur.addChild(toAdd);
                }
            }
        }

        // In any case, cur points to the last final state
        // for the word, so mark it as such.
        //
        cur.setIsWord();
    }

    public Trie.Node getRoot () {
        return this.root;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        class Pair<U, V> {
            U x;
            V y;
            Pair(U x, V y) {
                this.x = x;
                this.y = y;
            }
        }

        LinkedList< Pair<Node, String> > frontier = new LinkedList<>();
        frontier.push(new Pair<>(root, root.toString()));
        Pair<Node, String> cur;
        while ( !frontier.isEmpty() ) {

            cur = frontier.pop();
            if (cur.x.isWord())
                sb.append("\n").append(cur.y);

            for (Node x : cur.x.getChildren())
                frontier.push(new Pair<>(x,cur.y + x.toString()));

        }

        return sb.toString();
    }
}
