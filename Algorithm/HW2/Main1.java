import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.BufferedReader;
 
/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
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
    // TaskA: Book Placement
    // Author: Betül Aydeğer
 
    static class TaskA {
        // Let OPT[i] is the min of the max unused lengths for the first i books
        // OPT[i] = min(OPT[j-1], L - sum(j, i)) for all j = i, i-1, ..., 1
        // OPT[i] için son rafımda i'den j ye kadar kitaplar olursa, ilk OPT[j] kısmının
        // minimumu ve
        // bu rafı kıyaslamam maximum kalan alanlı rafı bulmam için yeterli olacak.
        // Sonra bunların minimumunu bulursam en iyi duruma ulaşmış olurum.
 
        public static double bookPlacement(double[] thickness, double L) {
 
            int n = thickness.length;
            double[] OPT = new double[n + 1];
            for (int i = 0; i <= n; i++) {
                OPT[i] = L; // initialize OPT[i] to L
            }
            OPT[0] = 0; // no book, no unused space
 
            for (int i = 1; i <= n; i++) {
                double sum = 0; // sum of thickness[j] for j = i, i-1, ..., 1
                for (int j = i; j > 0; j--) {
                    sum += thickness[j - 1];
                    if (sum > L)
                        break;
                    double unused = L - sum; // unused space for the last shelf
                    OPT[i] = Math.min(OPT[i], Math.max(OPT[j - 1], unused)); // update OPT[i]
                }
            }
 
            return OPT[n];
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
