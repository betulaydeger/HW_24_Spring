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

        public int independentSet(int n, int m, int u[], int v[], int weight[]) {
            // Grafı temsil etmek için bir adj list oluştur.
            List<Integer>[] adj = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<>();
            }
            for (int i = 0; i < m; i++) {
                adj[u[i]].add(v[i]);
                adj[v[i]].add(u[i]);
            }

            // dp dizilerini ve ziyaret kontrolünü hazırla
            int[][] dp = new int[n][2]; // 0: Düğüm dahil edilmedi, 1: Düğüm dahil edildi
            boolean[] visited = new boolean[n];

            // DFS ile her düğümü ziyaret ederek dp değerlerini hesapla
            dfs(0, -1, adj, weight, dp, visited);

            // Kök düğüm için maksimum değeri döndür (dahil edilip edilmemesine bağlı
            // olarak)
            return Math.max(dp[0][0], dp[0][1]);
        }

        // DFS fonksiyonu ile dp dizilerini doldur
        private void dfs(int node, int parent, List<Integer>[] adj, int[] weight, int[][] dp, boolean[] visited) {
            visited[node] = true;
            int withNode = weight[node]; // Bu düğüm dahil edildiğinde
            int withoutNode = 0; // Bu düğüm dahil edilmediğinde

            for (int child : adj[node]) {
                if (child != parent) {
                    dfs(child, node, adj, weight, dp, visited);
                    withNode += dp[child][0]; // Eğer bu düğüm seçilirse, çocuk seçilemez
                    withoutNode += Math.max(dp[child][0], dp[child][1]); // Bu düğüm seçilmezse, çocuk için en iyi durum
                }
            }

            dp[node][0] = withoutNode;
            dp[node][1] = withNode;
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
