import java.io.*;
import java.util.StringTokenizer;

public class Solution1_boj {
    static long[][] d;
    final static long INF = Long.MAX_VALUE;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int E = Integer.parseInt(st.nextToken());

        d = new long[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                d[i][j] = INF;
            }
        }
        for (int i = 1; i <= N; i++) {
            d[i][i] = 0;
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int dst = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            d[src][dst] = Math.min(d[src][dst], weight);
        }

        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (d[i][k] != INF && d[k][j] != INF) {
                        d[i][j] = Math.min(d[i][k] + d[k][j], d[i][j]);
                    }
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            StringBuilder write = new StringBuilder();
            for (int j = 1; j <= N; j++) {
                if (d[i][j] != INF) {
                    write.append(d[i][j]).append(" ");
                } else {
                    write.append(0).append(" ");
                }
            }
            System.out.println(write.toString().trim());
        }
    }

}
