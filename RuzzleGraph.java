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
    private HashMap<String, int[]> solutionToIndices;
    private ArrayList<int[]> indices;
    private ArrayList<String> solutions;

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

        solutionToIndices = new HashMap<>();
        solutions = new ArrayList<>();
        this.indices = new ArrayList<>();
    }

    public void solve(Trie trie) {

        class Tuple <K, V, T, S, D> {
            K x;
            V y;
            T z;
            S w;
            D t;

            Tuple(K x, V y, T z, S w, D t) {
                this.x = x;
                this.y = y;
                this.z = z;
                this.w = w;
                this.t = t;
            }
        }

        solutions.clear();

        LinkedList<Tuple<RNode, int[], String, Trie.Node, Integer>> frontier = new LinkedList<>();
        Tuple<RNode, int[], String, Trie.Node, Integer> cur;
        int indices[];

        for (int i = 0; i < 16; i++) {


            frontier.clear();

            // Each tuple has the current state of its graph.
            //
            frontier.push(
                    new Tuple<>(
                            rnodes[i],
                            indices = new int[16],
                            rnodes[i].getId() + "",
                            trie.getRoot().hasChild(rnodes[i].getId()),
                            0
                    )
            );

            // Mark all indices as unused.
            //
            for (int j = 0; j < indices.length; j++)
                indices[j] = -1;


            while ( !frontier.isEmpty() ) {

                cur = frontier.pop();

                // If we visited this node, backtrack.
                //
                if (contains(cur.y, cur.x.getLocation(), cur.t))
                    continue;

                // Increase the depth of our search and mark the last
                // visited spot as the current node.
                //
                cur.y[cur.t++] = cur.x.getLocation();

                if (cur.w.isWord()) {
                    this.indices.add(cur.y);
                    this.solutions.add(cur.z);
                    this.solutionToIndices.put(cur.z, cur.y);
                }


                for (RNode x : cur.x.getChildren()) {

                    Trie.Node n = cur.w.hasChild(x.getId());
                    if (n != null)
                        frontier.push(new Tuple<>(x, cur.y.clone(), cur.z + x.getId(), n, cur.t));

                }

            } // while
        } // for
    }

    public void solveRecursively(Trie trie) {

        Trie.Node root = trie.getRoot();
        int indices[];

        for (int i = 0; i < 16; i++) {

            indices = new int[16];
            java.util.Arrays.fill(indices, -1);

            solveRecursively(
                    root.hasChild(rnodes[i].getId()),
                    0,
                    indices,
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
    private void solveRecursively(Trie.Node root, int mask, int[] indices, String soFar, int index, int depth) {

        // Have we visited this already?
        //
        if ( (mask & (1 << rnodes[index].getLocation())) != 0 || root == null || indices == null)
            return;

        // Mark as visited;
        //
        mask |= 1 << rnodes[index].getLocation();
        indices[depth] = rnodes[index].getLocation();

        // If this is a solution, save it.
        //
        if (root.isWord()) {
            if (!this.solutions.contains(soFar)) {
                this.indices.add(indices);
                this.solutions.add(soFar);
                solutionToIndices.put(soFar, indices);
            }
        }

        // Recurse!
        //
        for (RNode x : rnodes[index].getChildren()) {
           solveRecursively(root.hasChild(x.getId()), mask, indices.clone(), soFar + x.getId(), x.getLocation(), depth + 1);
        }
    }

    private boolean contains (int[] arr, int x, int k) {
        for (int i = 0; i < k; i++)
            if (arr[i] == x)
                return true;

        return false;
    }

    public HashMap<String, int[]> getHashmap() {
        return this.solutionToIndices;
    }

    public ArrayList<int[]> getIndices() {
        return this.indices;
    }

    public ArrayList<String> getSolutions() {
        return this.solutions;
    }
}
