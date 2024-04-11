import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.BufferedReader;
 
/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
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
 
        // Bir node için iki durum var: seçilmiş veya seçilmemiş 0:Seçilmemiş,
        // 1:Seçilmiş
        // OPT(node, 0) = max(OPT(child, 0), OPT(child, 1))
        // seçilmemiş durumda, çocuklar seçilebilir veya seçilmez
        // OPT(node, 1) = sum(OPT(child, 0)) + weight[node]
        // seçilir ise çocuklar seçilemez
        public int independentSet(int n, int m, int u[], int v[], int weight[]) {
            // Grafı temsil etmek için bir adj list oluşturdum.
            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }
 
            for (int i = 0; i < m; i++) {
                adj.get(u[i]).add(v[i]);
                adj.get(v[i]).add(u[i]);
            }
 
            int[][] OPT = new int[n][2]; // 0: Düğüm dahil edilmedi, 1: Düğüm dahil edildi
            boolean[] visited = new boolean[n]; // Düğümleri ziyaret etmek için kullanılır
 
            // DFS ile her düğümü ziyaret ederek OPT değerlerini hesaplayacağım
            dfs(0, -1, adj, weight, OPT, visited);
 
            // root için maksimum değeri döndür
            return Math.max(OPT[0][0], OPT[0][1]);
        }
 
        // DFS fonksiyonu
        private void dfs(int node, int parent, List<List<Integer>> adj, int[] weight, int[][] OPT, boolean[] visited) {
            visited[node] = true;
            int withNode = weight[node]; // Bu düğüm dahil edildiğinde olan sayı
            int withoutNode = 0; // Bu düğüm dahil edilmediğinde olan sayı
 
            for (int child : adj.get(node)) {
                if (child != parent) {
                    dfs(child, node, adj, weight, OPT, visited);
                    withNode += OPT[child][0]; // Eğer bu düğüm seçilirse, çocuk seçilemez
                    withoutNode += Math.max(OPT[child][0], OPT[child][1]); // Bu düğüm seçilmezse, çocuk için en iyi
                                                                           // durum
                }
            }
 
            OPT[node][0] = withoutNode;
            OPT[node][1] = withNode;
        }
 
        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int m = in.nextInt();
            int u[] = new int[m];
            int v[] = new int[m];
            int weight[] = new int[n];
            for (int i = 0; i < m; i++) {
                u[i] = in.nextInt();
                v[i] = in.nextInt();
                u[i]--;
                v[i]--;
            }
            for (int i = 0; i < n; i++)
                weight[i] = in.nextInt();
            System.out.println(independentSet(n, m, u, v, weight));
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
