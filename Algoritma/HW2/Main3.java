import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.*;

import org.w3c.dom.events.Event;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
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

        public int switchCount(int[] a, int[] b) {
            Arrays.sort(a);
            Arrays.sort(b);
            int count = 0;
            for (int i = 0; i < a.length - 1; i++) {
                if (a[i + 1] > b[i])
                    count++;
            }

            return count + 1;

        }

        public void solve(InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int[] a = new int[n];
            int[] b = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
                b[i] = in.nextInt();
            }
            int result = switchCount(a, b);
            System.out.println(result);
        }
    }

    static class Event {
        int time;
        int type; // 1 for entering, -1 for leaving

        public Event(int time, int type) {
            this.time = time;
            this.type = type;
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
    }
}

/*
 * /*
 * 
 * int n = a.length;
 * // Olayları (hem giriş hem de çıkış) tek bir diziye birleştiriyoruz ve
 * // sıralıyoruz.
 * Event[] events = new Event[2 * n];
 * for (int i = 0; i < n; i++) {
 * events[2 * i] = new Event(a[i], 1); // Giriş olayı
 * events[2 * i + 1] = new Event(b[i], -1); // Çıkış olayı
 * }
 * 
 * // Olayları zamanına göre sıralayın. Eğer zamanlar aynıysa, çıkışları
 * // girişlerden önce işleyin.
 * Arrays.sort(events, (e1, e2) -> e1.time != e2.time ? e1.time - e2.time :
 * e1.type - e2.type);
 * 
 * int count = 0; // Işıkların açıldığı sayısı
 * int present = 0; // Odada şu an bulunan kişi sayısı
 * 
 * for (Event e : events) {
 * if (e.type == 1) { // Birisi odaya giriyor
 * if (present == 0) { // Oda boşsa, ışıklar açılır
 * count++;
 * }
 * present++;
 * } else { // Birisi odadan çıkıyor
 * present--;
 * }
 * }
 * 
 * return count;
 */