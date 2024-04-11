import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.*;

// Author: Betül Aydeğer
// articulationPoints method finds the articulation points in an undirected graph
// An articulation point is a vertex whose removal increases the number of connected components in the graph
// Not: If instead of using iterator, you use a for loop to iterate over the neighbors, the code will be slower
public class Main2 {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskA solver = new TaskA();
        solver.solve(in, out);
        out.close();
    }

    static class TaskA {

        private static class DFSstate {
            int u, parent, children, state;
            Iterator<Integer> it; // Iterator to iterate over the neighbors of the node

            public DFSstate(int u, int parent) {
                this.u = u; // Node number
                this.parent = parent; // Parent of the node
                this.children = 0; // Number of children of the node
                this.state = 0; // 0 means not visited, 1 means visited
            }
        }

        public int[] articulationPoints(List<List<Integer>> adj) {
            int numberOfArticulationPoints = 0; // Number of articulation points because we need to create an array with
                                                // the size of the number of articulation points
            boolean[] visited = new boolean[adj.size()]; // visited array to keep track of visited nodes
            int[] disc = new int[adj.size()]; // discovery time of each node
            int[] low = new int[adj.size()]; // low time of each node
            // low time means the earliest visited vertex reachable from subtree rooted with
            boolean[] isArticulationPoint = new boolean[adj.size()]; // to keep track of articulation points
            int time = 0; // global time

            Deque<DFSstate> stack = new ArrayDeque<>();

            // It is like DFS but we know that input graph is connected so we don't need to
            // for loop for each node
            DFSstate root = new DFSstate(0, -1); // root node
            stack.push(root); // root node is pushed to the stack
            while (!stack.isEmpty()) {
                DFSstate current = stack.peek();
                if (current.state == 0) { // If the node is not visited
                    disc[current.u] = low[current.u] = ++time; // update discovery and low time
                    visited[current.u] = true; // mark the node as visited
                    current.it = adj.get(current.u).iterator(); // get the iterator of the neighbors
                }

                boolean pushed = false; // to check if a child is pushed to the stack
                while (current.it.hasNext()) {
                    int v = current.it.next();
                    if (current.state == 0 && v == current.parent) // if the child is parent, skip
                        continue; // Skip the parent

                    if (!visited[v]) {
                        // First time seeing this vertex, so push it on the stack
                        stack.push(new DFSstate(v, current.u));
                        current.state = 1; // Mark state as returning from child
                        pushed = true; // Mark that a child was pushed
                        break; // Exit to process the child
                    } else {
                        // Update low value of u for parent function calls
                        low[current.u] = Math.min(low[current.u], disc[v]);
                    }
                }

                if (!pushed) { // If no child was pushed, we're done with this node
                    if (current.parent != -1) { // If the node is not the root
                        low[current.parent] = Math.min(low[current.parent], low[current.u]); // Update low value of
                                                                                             // parent
                        if (low[current.u] >= disc[current.parent]) { // If the low value of the child is greater than
                                                                      // the discovery time of the parent
                            if (current.parent != 0) // If the parent is not the root
                                isArticulationPoint[current.parent] = true; // Mark the parent as articulation point
                            else
                                root.children++; // If the parent is the root, increment the number of children
                        }
                    }
                    // Pop the current state as we're done processing this node
                    stack.pop();
                }
            }
            if (root.children > 1) { // If the root has more than one child then it is an articulation point
                isArticulationPoint[root.u] = true;
            }

            for (int j = 0; j < adj.size(); j++) { // Count the number of articulation points
                if (isArticulationPoint[j]) {
                    numberOfArticulationPoints++;
                }
            }

            int[] articulationPoints = new int[numberOfArticulationPoints]; // Create an array with the size of the
                                                                            // number of articulation points
            int k = 0;
            for (int j = 0; j < adj.size(); j++) {
                if (isArticulationPoint[j]) {
                    articulationPoints[k++] = j; // Fill the array with the articulation points
                }
            }

            return articulationPoints;
        }

        public void solve(InputReader in, PrintWriter out) {

            int n = in.nextInt();
            int m = in.nextInt();

            // Initialize the adjacency list
            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }

            // Read m lines with u and v, and construct the adjacency list
            for (int i = 0; i < m; i++) {
                int u = in.nextInt();
                int v = in.nextInt();
                u--;
                v--;
                adj.get(u).add(v);
                adj.get(v).add(u);
            }

            int[] points = articulationPoints(adj);

            System.out.println(points.length);
            for (int i = 0; i < points.length; i++) {
                System.out.print((points[i] + 1) + " ");
            }
            System.out.println();
        }

    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

    }
}
