import java.io.*;
import java.util.*;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

public class Solution3 {
    final static int numTc = 2;
    static int[][] edges;
    static List<List<Edge>> adjEdges;
    static long[] distances;
    final static long INF = Long.MAX_VALUE;

    private static class Edge {
        int dst;
        int w;
        Edge(int dst, int w) {
            this.dst = dst;
            this.w = w;
        }
    }

    private static void improvedBellmanFord(int N, int E, int startV) {
        for (int i = 1; i <= N; i++) {
            distances[i] = INF;
        }
        distances[startV] = 0;

        boolean[] inQueue = new boolean[N + 1];
        int[] numVisited = new int[N + 1];
        Deque<Integer> q = new LinkedList<>();
        q.offer(startV);
        inQueue[startV] = true;
        numVisited[startV]++;

        while (!q.isEmpty()) {
            int v = q.poll();
            inQueue[v] = false;
            List<Edge> outEdges = adjEdges.get(v);
            for (Edge edge : outEdges) {
                if (distances[v] != INF && distances[edge.dst] > distances[v] + edge.w) {
                    distances[edge.dst] = distances[v] + edge.w;
                    if (!inQueue[edge.dst]) {
                        if (++numVisited[edge.dst] >= N) {
                            // neg cycle
                            return;
                        }
                        q.offer(edge.dst);
                        inQueue[edge.dst] = true;
                    }
                }
            }
        }
    }

    private static void naiveBellmanFord(int N, int E, int startV) {
        for (int i = 1; i <= N; i++) {
            distances[i] = INF;
        }
        distances[startV] = 0;

        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < E; j++) {
                int[] edge = edges[j];
                if (distances[edge[0]] != INF) {
                    distances[edge[1]] = Math.min(distances[edge[1]], distances[edge[0]] + edge[2]);
                }
            }
        }

//        // check negative cycle
//        for (int i = 0; i < E; i++) {
//            if (distances[edges[i][0]] != INF && distances[edges[i][1]] > distances[edges[i][0]] + edges[i][2]) {
//                return;
//            }
//        }
    }

    public static void main(String[] args) throws Exception{
        File file = new File("input3.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        File writeFile = new File("output3.txt");
        if (!writeFile.exists()) {
            writeFile.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(writeFile));
        int tc = 1;

        while (tc <= numTc) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());

            edges = new int[E][3]; // src, dst, w
            adjEdges = new ArrayList<>();
            for (int i = 0; i < N + 1; i++) {
                adjEdges.add(new ArrayList<>());
            }
            distances = new long[N + 1];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < E; i++) {
                int src = Integer.parseInt(st.nextToken());
                int dst = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                edges[i][0] = src;
                edges[i][1] = dst;
                edges[i][2] = w;

                adjEdges.get(src).add(new Edge(dst, w));
            }

            String writeStr = "#" + tc;
            bw.write(writeStr);
            bw.newLine();

            long currTime = System.currentTimeMillis();
            naiveBellmanFord( N, E, 1);
            float executeTime = (float) (System.currentTimeMillis() - currTime) / 1000;
            writeResult(bw, N, executeTime);


            currTime = System.currentTimeMillis();
            improvedBellmanFord(N, E, 1);
            executeTime = (float) (System.currentTimeMillis() - currTime) / 1000;
            writeResult(bw, N, executeTime);

            tc++;
        }
        bw.flush();
        bw.close();
        br.close();
    }

    private static void writeResult(BufferedWriter bw, int n, float executeTime) throws IOException {
        StringBuilder write = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            write.append(distances[i]).append(" ");
        }
        bw.write(write.toString().trim());
        bw.newLine();
        bw.write(Float.toString(executeTime));
        bw.newLine();
    }
}
