import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.text.html.Option;

import java.io.BufferedReader;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main1 {

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
        // Let OPT(i, j) be the min empty space left when the first i books are placed
        // on the first j shelves.

        public static double bookPlacement(double[] thickness, double L) {

            int numberOfBooks = thickness.length;
            double[][] OPT = new double[numberOfBooks][numberOfBooks];
            for (int i = 0; i < numberOfBooks; i++) {
                for (int j = 0; j < numberOfBooks; j++) {
                    OPT[i][j] = L;
                }
            }

            dp[0][0] = L - thickness[0];
            double[] lastKbookSumThickness = new double[numberOfBooks];
            lastKbookSumThickness[numberOfBooks - 1] = thickness[numberOfBooks - 1];
            for (int i = numberOfBooks - 2; i >= 0; i--) {
                lastKbookSumThickness[i] = lastKbookSumThickness[i + 1] + thickness[i];
            }

            for (int i = 1; i < numberOfBooks; i++) {
                System.out.println("i: " + lastKbookSumThickness[i]);
            }
            return dp[numberOfBooks - 1];
            /*
             * int numberOfBooks = thickness.length;
             * double[][] left = new double[numberOfBooks + 1][numberOfBooks + 1];
             * 
             * // if i^th book is placed on j^th shelf, left[i][j] is the minimum empty
             * space
             * // left
             * for (int i = 0; i <= numberOfBooks; i++) {
             * for (int j = 0; j <= numberOfBooks; j++) {
             * left[i][j] = L; // If we don't place any book, the empty space is L
             * }
             * }
             * 
             * left[0][0] = 0; // Başlangıç durumu
             * 
             * // Dinamik programlama ile çözüm
             * for (int i = 1; i <= numberOfBooks; i++) {
             * double thicknessSum = 0;
             * for (int j = i; j >= 1; j--) {
             * thicknessSum += thickness[j - 1];
             * if (thicknessSum <= L) {
             * for (int k = 0; k < j; k++) {
             * left[i][k + 1] = Math.min(left[i][k + 1], Math.max(left[j - 1][k], L -
             * thicknessSum));
             * }
             * } else {
             * break; // Rafın kapasitesini aştık.
             * }
             * }
             * }
             * 
             * // En iyi sonucu bulma
             * double result = Double.MAX_VALUE;
             * for (double res : left[numberOfBooks]) {
             * if (res < result)
             * result = res;
             * }
             * 
             * return result; // En az boş bırakılan maksimum uzunluk
             */
        }

        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            double L = in.nextDouble();
            double[] thickness = new double[n];
            for (int i = 0; i < n; i++)
                thickness[i] = in.nextDouble();
            System.out.println(bookPlacement(thickness, L));
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