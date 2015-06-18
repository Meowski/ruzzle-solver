import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Meowington on 6/16/2015.
 */
class RuzzleGraph {

    private RNode[] rnodes;
    private HashMap<String, ArrayList<Integer>> solutionToIndices;
    private ArrayList<Integer> indices;
    private ArrayList<String> solutions;

    private ArrayList<Integer> arrIndex;

    public static class RNode {

        private int location;
        private char id;
        private LinkedList<RNode> children;

        public RNode (int location, char id) {
            this (location, id, new LinkedList<RNode>());
        }

        public RNode (int location, char id, LinkedList<RNode> children) {
            this.location = location;
            this.id = id;
            this.children = children;
        }

        public void addChild (RNode child) {
            if (!this.children.contains(child))
                this.children.add(child);
        }

        public void addChildren (Collection<RNode> children) {
            for (RNode RNode : children)
                if (!this.children.contains(RNode))
                    this.children.add(RNode);
        }

        public LinkedList<RNode> getChildren () {
            return this.children;
        }

        public int getLocation () {
            return this.location;
        }

        public char getId() {
            return this.id;
        }

        @Override
        public boolean equals( Object other ) {
            if (other == null)
                return false;
            else if (! (other instanceof RNode))
                return false;
            else return ((RNode)other).getLocation() == this.location &&
                        ((RNode)other).getId() == (this.id);
        }

        @Override
        public int hashCode() {
            return this.location;
        }
    }

    public RuzzleGraph (String problem) {

        problem = problem.trim();
        if (problem.length() != 16)
            throw new InvalidParameterException();

        rnodes = new RNode[16];
        for (int i = 0; i < 16; i++)
            rnodes[i] = new RNode(i, problem.charAt(i));

        // Link up the graph.
        rnodes[0].addChild(rnodes[1]);
        rnodes[0].addChild(rnodes[4]);
        rnodes[0].addChild(rnodes[5]);

        rnodes[1].addChild(rnodes[0]);
        rnodes[1].addChild(rnodes[4]);
        rnodes[1].addChild(rnodes[5]);
        rnodes[1].addChild(rnodes[6]);
        rnodes[1].addChild(rnodes[2]);

        rnodes[2].addChild(rnodes[1]);
        rnodes[2].addChild(rnodes[5]);
        rnodes[2].addChild(rnodes[6]);
        rnodes[2].addChild(rnodes[7]);
        rnodes[2].addChild(rnodes[3]);

        rnodes[3].addChild(rnodes[2]);
        rnodes[3].addChild(rnodes[6]);
        rnodes[3].addChild(rnodes[7]);

        rnodes[4].addChild(rnodes[0]);
        rnodes[4].addChild(rnodes[1]);
        rnodes[4].addChild(rnodes[5]);
        rnodes[4].addChild(rnodes[9]);
        rnodes[4].addChild(rnodes[8]);

        rnodes[5].addChild(rnodes[0]);
        rnodes[5].addChild(rnodes[1]);
        rnodes[5].addChild(rnodes[2]);
        rnodes[5].addChild(rnodes[6]);
        rnodes[5].addChild(rnodes[10]);
        rnodes[5].addChild(rnodes[9]);
        rnodes[5].addChild(rnodes[8]);
        rnodes[5].addChild(rnodes[4]);

        rnodes[6].addChild(rnodes[1]);
        rnodes[6].addChild(rnodes[2]);
        rnodes[6].addChild(rnodes[3]);
        rnodes[6].addChild(rnodes[7]);
        rnodes[6].addChild(rnodes[11]);
        rnodes[6].addChild(rnodes[10]);
        rnodes[6].addChild(rnodes[9]);
        rnodes[6].addChild(rnodes[5]);

        rnodes[7].addChild(rnodes[2]);
        rnodes[7].addChild(rnodes[3]);
        rnodes[7].addChild(rnodes[6]);
        rnodes[7].addChild(rnodes[10]);
        rnodes[7].addChild(rnodes[11]);

        rnodes[8].addChild(rnodes[4]);
        rnodes[8].addChild(rnodes[5]);
        rnodes[8].addChild(rnodes[9]);
        rnodes[8].addChild(rnodes[13]);
        rnodes[8].addChild(rnodes[12]);

        rnodes[9].addChild(rnodes[4]);
        rnodes[9].addChild(rnodes[5]);
        rnodes[9].addChild(rnodes[6]);
        rnodes[9].addChild(rnodes[10]);
        rnodes[9].addChild(rnodes[14]);
        rnodes[9].addChild(rnodes[13]);
        rnodes[9].addChild(rnodes[12]);
        rnodes[9].addChild(rnodes[8]);

        rnodes[10].addChild(rnodes[5]);
        rnodes[10].addChild(rnodes[6]);
        rnodes[10].addChild(rnodes[7]);
        rnodes[10].addChild(rnodes[11]);
        rnodes[10].addChild(rnodes[15]);
        rnodes[10].addChild(rnodes[14]);
        rnodes[10].addChild(rnodes[13]);
        rnodes[10].addChild(rnodes[9]);

        rnodes[11].addChild(rnodes[6]);
        rnodes[11].addChild(rnodes[7]);
        rnodes[11].addChild(rnodes[10]);
        rnodes[11].addChild(rnodes[14]);
        rnodes[11].addChild(rnodes[15]);

        rnodes[12].addChild(rnodes[8]);
        rnodes[12].addChild(rnodes[9]);
        rnodes[12].addChild(rnodes[13]);

        rnodes[13].addChild(rnodes[8]);
        rnodes[13].addChild(rnodes[9]);
        rnodes[13].addChild(rnodes[10]);
        rnodes[13].addChild(rnodes[14]);
        rnodes[13].addChild(rnodes[12]);

        rnodes[14].addChild(rnodes[9]);
        rnodes[14].addChild(rnodes[10]);
        rnodes[14].addChild(rnodes[11]);
        rnodes[14].addChild(rnodes[15]);
        rnodes[14].addChild(rnodes[13]);

        rnodes[15].addChild(rnodes[10]);
        rnodes[15].addChild(rnodes[11]);
        rnodes[15].addChild(rnodes[14]);

        this.solutionToIndices = new HashMap<String, ArrayList<Integer>>();
        this.solutions = new ArrayList<>();
        this.indices = new ArrayList<>();
        this.arrIndex = new ArrayList<Integer>();
    }

    public void solve(Trie trie) {

        class Tuple <U, V, T, S, D> {
            U trieNode;
            V word;
            T mask;
            S index;
            D depth;

            Tuple(U trieNode, V solution, T mask, S index, D depth) {
                this.trieNode = trieNode;
                this.word = solution;
                this.mask = mask;
                this.index = index;
                this.depth = depth;
            }
        }

        solutions.clear();

        LinkedList<Tuple<Trie.Node, String, Integer, Integer, Integer>> frontier = new LinkedList<>();
        Tuple<Trie.Node, String, Integer, Integer, Integer> cur;

        for (int i = 0; i < 16; i++) {

            frontier.clear();

            // Each tuple has the current state of its graph.
            //
            frontier.push(
                    new Tuple<>(
                            trie.getRoot().hasChild(rnodes[i].getId()),
                            rnodes[i].getId() + "",
                            0,
                            i,
                            0
                    )
            );

            while ( !frontier.isEmpty() ) {

                cur = frontier.pop();

                // As we come off the stack, we need to remove all previous
                // entries in the solution array.
                //
                while ( cur.depth + 1 <= arrIndex.size() )
                    arrIndex.remove((int)cur.depth);

                // If we visited this node, skip it.
                //
                if ( (cur.mask & (1 << cur.index)) != 0) {
                    continue;
                }

                // Increase the depth of our search and mark the last
                // visited spot as the current node.
                //
                cur.mask |= 1 << cur.index;

                arrIndex.add(cur.index);
                cur.depth++;

                if (cur.trieNode.isWord() && !this.solutions.contains(cur.word)) {

                    this.solutions.add(cur.word);
                    this.solutionToIndices.put(cur.word, new ArrayList<Integer>(arrIndex));
                }

                boolean hadMove = false;
                for (RNode x : rnodes[cur.index].getChildren()) {

                    Trie.Node n = cur.trieNode.hasChild(x.getId());
                    if (n != null) {
                        hadMove = true;
                        frontier.push(new Tuple<>(n, cur.word + x.getId(), cur.mask, x.getLocation(), cur.depth));
                    }

                }

            } // while
        } // for
    }

    public void solveRecursively(Trie trie) {

        Trie.Node root = trie.getRoot();

        for (int i = 0; i < 16; i++) {

            solveRecursively(
                    root.hasChild(rnodes[i].getId()),
                    0,
                    "" + rnodes[i].getId(),
                    rnodes[i].getLocation(),
                    0
            );
        }
    }

    // The current Trie node we are at, a mask representing the visited squares, indices
    // of the current solution, the string constructed so far, and the index of the RNode
    // we are currently working with.
    //
    private void solveRecursively(Trie.Node root, int mask, String soFar, int index, int depth) {

        // Have we visited this already?
        //
        if ( (mask & (1 << index)) != 0 || root == null || indices == null)
            return;

        // Mark as visited;
        //
        mask |= 1 << index;
        arrIndex.add(index);

        // If this is a solution, save it.
        //
        if (root.isWord()) {
            if (!this.solutions.contains(soFar)) {

                this.solutions.add(soFar);
                this.solutionToIndices.put(soFar, new ArrayList<Integer>(arrIndex));
            }
        }

        // Recurse!
        //
        for (RNode x : rnodes[index].getChildren()) {
           solveRecursively(root.hasChild(x.getId()), mask, soFar + x.getId(), x.getLocation(), depth + 1);
        }

        // Finished our move, undo it.
        //
        arrIndex.remove(depth);
    }

    public HashMap<String, ArrayList<Integer>> getHashmap() {
        return this.solutionToIndices;
    }

    public ArrayList<Integer> getIndices() {
        return this.indices;
    }

    public ArrayList<String> getSolutions() {
        return this.solutions;
    }
}
