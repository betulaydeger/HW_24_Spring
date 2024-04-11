import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// Author: Betül Aydeğer
// Single source shortest path problem with non-negative weights in a directed graph
// Known that not known better way to solve this problem than all-pairs shortest path problem
// Implementation of Dijkstra's algorithm using a Priority Queue because it is faster than using an array(small constant factor)
// The graph is directed and the cost of the edges is given
public class Main4 {

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

        static class Pair implements Comparable<Pair> {
            int node;
            int cost;

            Pair(int node, int cost) {
                this.node = node;
                this.cost = cost;
            }

            @Override
            public int compareTo(Pair other) {
                return Integer.compare(this.cost, other.cost);
            }
        }

        public long shortestPath(List<List<Pair>> adj, int s, int t) {
            int n = adj.size(); // Number of nodes
            long[] dist = new long[n]; // Array to keep the shortest distance from the source node to each node
            for (int i = 0; i < n; i++) // Initialize the distances to infinity
                dist[i] = Long.MAX_VALUE;
            dist[s] = 0; // Distance from the source node to itself is 0

            PriorityQueue<Pair> pq = new PriorityQueue<>();
            pq.add(new Pair(s, 0));

            while (!pq.isEmpty()) {
                Pair current = pq.poll();
                if (current.node == t) // If the target node is reached
                    break; // break the loop

                for (Pair edge : adj.get(current.node)) { // Iterate over the neighbors of the current node
                    if (dist[current.node] + edge.cost < dist[edge.node]) { // If the new distance is smaller
                        dist[edge.node] = dist[current.node] + edge.cost; // Update the distance
                        pq.add(new Pair(edge.node, (int) dist[edge.node])); // Add the neighbor to the priority queue
                    }
                }
            }

            return dist[t] == Long.MAX_VALUE ? -1 : dist[t]; // Return -1 if the target node is not reachable
        }

        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt(); // Number of nodes
            int m = in.nextInt(); // Number of edges
            // Initialize the adjacency list with pairs (node, cost)
            List<List<Pair>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }

            // Read m lines with u, v, and cost, and construct the adjacency list
            for (int i = 0; i < m; i++) {
                int u = in.nextInt();
                int v = in.nextInt();
                u--;
                v--;
                int cost = in.nextInt(); // Assuming the cost of the edge is given
                adj.get(u).add(new Pair(v, cost)); // Assuming the graph is directed
            }

            // Now you can call shortestPath with the adjacency list and source and target
            // nodes
            // For example:
            int s = in.nextInt();
            int t = in.nextInt();
            s--;
            t--;
            long shortestPathCost = shortestPath(adj, s, t);
            System.out.println(shortestPathCost);
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