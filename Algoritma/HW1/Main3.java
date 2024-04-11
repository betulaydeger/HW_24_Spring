import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import java.io.BufferedReader;

// Author: Betül Aydeğer
//Not: input format is adj matrix

// findSink method finds the sink in a directed graph
// A sink is a vertex with no outgoing edges
// Starting from the [0][0] element, we move to the right if there is an edge, otherwise we move down
// If we reach the end of the row, we check if the current vertex is a sink by checking if there is an edge to any other vertex
public class Main3 {

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

        public int findSink(int[][] adj) {
            int n = adj.length; // number of vertices
            int i = 0, j = 0; // i is the row number, j is the column number
            while (i < n && j < n) { // while we are not at the end of the matrix
                if (adj[i][j] == 1) { // if there is an edge
                    i++; // move to the next row
                } else {
                    j++; // move to the next column
                }
            }
            j = 0; // reset j
            while (j < n) {
                if (adj[i][j] == 1) { // if there is an edge from the sink to any other vertex
                    return -1; // return -1 because the sink is not a sink
                }
                j++; // move to the next column
            }

            return i; // return the sink
        }

        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int adj[][] = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++)
                    adj[i][j] = in.nextInt();
            }
            int result = findSink(adj);
            System.out.println(result);
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