import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Meowington on 6/16/2015.
 */
public class Trie {

    private int id = 1;
    public static class Node {

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

            if ( ((Node)other).getLetter() == null) {
                return this.letter == null;
            }

            return ((Node)other).getLetter().charValue() == this.letter.charValue();
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
