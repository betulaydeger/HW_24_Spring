import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.*;

/* Author: Betül Aydeğer */
/*numberOfShortestPaths method counts the number of distinct shortest paths from a source node to all other nodes in an undirected graph.*/
public class Main {

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
        // Modified BFS to count the number of shortest paths
        // distance=-1 means the node is not visited
        // node which is not visited has same number of d.s.p as the its ancestor
        public int[] numberOfShortestPaths(List<List<Integer>> adj, int s) {
            int n = adj.size(); // number of vertices
            int[] distance = new int[n]; // distance from source
            int[] result = new int[n]; // number of shortest paths

            for (int i = 0; i < n; i++) {
                distance[i] = -1; // -1 means not visited
                result[i] = 0; // 0 means no shortest path
            }

            distance[s] = 0;
            result[s] = 1;
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(s);
            while (!queue.isEmpty()) {
                int u = queue.poll();
                for (int v : adj.get(u)) {
                    if (distance[v] == -1) { // if the node is not visited
                        queue.offer(v);
                        distance[v] = distance[u] + 1;
                        result[v] = result[u]; // the number of shortest path of v is the same as u
                    } else if (distance[v] == distance[u] + 1) { // if the node is visited before and it is child of u
                        result[v] += result[u]; // then add the number of shortest path of u to v
                    }
                }
            }
            return result;
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

            int s = in.nextInt();
            s--;

            int[] costs = numberOfShortestPaths(adj, s);

            for (int i = 0; i < costs.length; i++) {
                System.out.print(costs[i] + " ");
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