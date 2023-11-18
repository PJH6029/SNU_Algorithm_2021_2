package Problem4;

import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. Compile the file with the following command. The class file named Solution4 would be created when you compile the source code.
       javac Solution4.java -encoding UTF8

   2. Run the compiled program with the following command. Output file(output.txt) should be created after the program is finished
       java Solution4

   - The encoding of the source code should be UTF8
   - You can use the 'time' command to measure your algorithm.
       time java Solution4
   - You can also halt the program with the 'timeout' command.
       timeout 0.5 java Solution4
       timeout 1 java Solution4
 */

class Solution4 {
	static final int max_n = 1000;

	static int n, H;
	static int[] h = new int[max_n], d = new int[max_n-1];
	static int Answer;

	static int[][] dpInclusive = new int[max_n][10001];
	static int[][] dpExclusive = new int[max_n][10001];

	final static int mod = 1000000;

	public static void main(String[] args) throws Exception {
		/*
		   Read the input from input.txt
		   Write your answer to output.txt
		 */
		BufferedReader br = new BufferedReader(new FileReader("src/Problem4/input.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("src/Problem4/output.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   n is the number of blocks, and H is the max tower height
			   read each height of the block to h[0], h[1], ... , h[n-1]
			   read the heights of the holes to d[0], d[1], ... , d[n-2]
			 */
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken()); H = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < n; i++) {
				h[i] = Integer.parseInt(stk.nextToken());
			}
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < n-1; i++) {
				d[i] = Integer.parseInt(stk.nextToken());
			}


			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   YOUR CODE HERE
			   Save your answer to the variable Answer
			 */
			/////////////////////////////////////////////////////////////////////////////////////////////
			Answer = 0;

			for (int h_ = 0; h_ <= H; h_++) {
				dpInclusive[0][h_] = (h[0] <= h_) ? 1 : 0;
				dpExclusive[0][h_] = 1;
			}


			for (int i = 1; i < n; i++) {
				for (int h_ = 0; h_ <= H; h_++) {
					dpExclusive[i][h_] = (dpInclusive[i-1][h_] + dpExclusive[i-1][h_]) % mod;

					if (h[i] > h_) {
						dpInclusive[i][h_] = 0;
					} else {
						int val = 0;
						if (h_ - h[i] + d[i - 1] >= 0 ) {
							val += dpInclusive[i-1][h_ - h[i] + d[i - 1]];
						}
						if (h_ - h[i] >= 0) {
							val += dpExclusive[i-1][h_ - h[i]];
						}

						dpInclusive[i][h_] = val % mod;
					}
				}
			}

			Answer = (dpExclusive[n-1][H] + dpInclusive[n-1][H] - 1) % mod;

			// Print the answer to output.txt
			pw.println("#" + test_case + " " + Answer);
			// To ensure that your answer is printed safely, please flush the string buffer while running the program
			pw.flush();
		}

		br.close();
		pw.close();
	}
}

