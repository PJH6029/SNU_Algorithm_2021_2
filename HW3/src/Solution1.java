import java.io.*;
import java.util.StringTokenizer;

public class Solution1 {
    static long[][] d;
    final static long INF = Long.MAX_VALUE;
    final static int numTc = 10;
    public static void main(String[] args) throws Exception {
        File file = new File("input1.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        File writeFile = new File("output1.txt");
        if (!writeFile.exists()) {
            writeFile.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(writeFile));
        int tc = 1;

        while (tc <= numTc) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());

            d = new long[N+1][N+1];

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    d[i][j] = INF;
                }
            }
            for (int i = 1; i <= N; i++) {
                d[i][i] = 0;
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < E; i++) {
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

            long sum = 0;
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    sum += d[i][j];
                }
            }
            sum = sum % 100000000;


            String writeStr = "#" + tc + " " + sum;
            bw.write(writeStr);
            bw.newLine();
            tc++;
        }
        bw.flush();
        bw.close();
        br.close();
    }
}
