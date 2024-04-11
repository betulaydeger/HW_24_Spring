import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.util.Arrays;

// Author: Betül Aydeğer
// The problem:
/* As input, we are given an integer array M of size n representing the ages of men in the society, 
and an integer array W of size n representing the ages of women in the society, and an integer
k. The goal is to obtain a maximum sized matching between men and women such that no
person is matched with someone whose age differs by more than k. For instance, if k = 36,
somebody at the age of 1286 can be matched with someone with age 1250−1322.
The output of your program should be a single integer: the size of the maximum matching.
 */

public class Main5 {

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
        public int match(int[] M, int[] W, int k) {
            Arrays.sort(M);
            Arrays.sort(W);
            // Two pointers technique
            // it can be started from the beginning or the end
            int i = M.length - 1, j = W.length - 1;
            int maxMatch = 0;
            while (i >= 0 && j >= 0) { // while there are woman and man to be matched
                if (Math.abs(M[i] - W[j]) <= k) { // if the difference is less than or equal to k then they can be
                                                  // matched
                    maxMatch++;
                    i--;
                    j--;
                } else if (M[i] < W[j]) {
                    j--;
                } else {
                    i--;
                }
            }
            return maxMatch;
        }

        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int k = in.nextInt();
            int[] M = new int[n];
            int[] W = new int[n];
            for (int i = 0; i < n; i++)
                M[i] = in.nextInt();
            for (int i = 0; i < n; i++)
                W[i] = in.nextInt();
            int maxMatch = match(M, W, k);
            out.println(maxMatch);
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
