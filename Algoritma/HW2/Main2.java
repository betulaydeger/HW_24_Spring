import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.util.Comparator;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
// Author: Betül Aydeğer
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

    // OPT(i,j)=max{OPT(i,j−1),max{p[j]−p[x]+OPT(i−1,x) for 1≤x<j}} x maxes j-1 de
    // hazır olsun
    // i alım satım sayısı, j gün sayısı
    static class TaskA {
        public static double letsGetRich(double[] p, int k) {
            double[][] OPT = new double[k + 1][p.length];
            double maxes[] = new double[p.length]; // - p[x] + OPT[i - 1][x]
            maxes[0] = -p[0];
            for (int i = 1; i <= k; i++) {
                for (int j = 1; j < p.length; j++) {
                    double max = p[j] + maxes[j - 1];
                    OPT[i][j] = Math.max(OPT[i][j - 1], max);
                    maxes[j] = Math.max(maxes[j - 1], -p[j] + OPT[i - 1][j]);

                }
            }

            return OPT[k][p.length - 1] * 1000;

        }

        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int k = in.nextInt();
            double[] p = new double[n];
            for (int i = 0; i < n; i++)
                p[i] = in.nextDouble();
            System.out.println(letsGetRich(p, k));
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

        public double nextDouble() {
            return Double.parseDouble(next());
        }

    }
}
