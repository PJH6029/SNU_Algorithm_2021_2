import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Solution2_boj {
    final static int numTc = 2;
    static int[][] edges;
    static int[] parent;
    static long cost;

    private static int find(int v) {
        if (parent[v] == v) {
            return v;
        } else {
            return find(parent[v]);
        }
    }

    private static void union(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);

        if (root1 < root2) {
            parent[root2] = root1;
        } else {
            parent[root1] = root2;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        edges = new int[E][3]; // v1, v2, w
        parent = new int[N];
        cost = 0;

        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            edges[i][0] = v1;
            edges[i][1] = v2;
            edges[i][2] = w; // max s.t.
        }

        Arrays.sort(edges, new Comparator<int[]>() {
            @Override
            public int compare(int[] e1, int[] e2) {
                return Integer.compare(e1[2], e2[2]);
            }
        });

        for (int i = 0; i < E; i++) {
            int v1 = edges[i][0];
            int v2 = edges[i][1];
            int w = edges[i][2];
            if (find(v1 - 1) != find(v2 - 1)) {
                union(v1 - 1, v2 - 1);
                cost += w;
            }
        }
        System.out.println(cost);
    }

}
