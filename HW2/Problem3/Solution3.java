import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. Compile the file with the following command. The class file named Solution3 would be created when you compile the source code.
       javac Solution3.java -encoding UTF8

   2. Run the compiled program with the following command. Output file(output.txt) should be created after the program is finished
       java Solution3

   - The encoding of the source code should be UTF8
   - You can use the 'time' command to measure your algorithm.
       time java Solution3
   - You can also halt the program with the 'timeout' command.
       timeout 0.5 java Solution3
       timeout 1 java Solution3
 */

class Solution3 {
	static final int max_n = 1000000;

	static int n;
	static int[][] A = new int[3][max_n];
	static int Answer;

	static int[][] dp = new int[max_n][6];

	static int getScore(int col, int pattern) {
		int a = A[0][col], b = A[1][col], c = A[2][col];
		switch (pattern) {
			case 0: return -a + b;
			case 1: return a - b;
			case 2: return a - c;
			case 3: return b - c;
			case 4: return -b + c;
			case 5: return -a + c;
		}

		return 0;
	}

	public static void main(String[] args) throws Exception {
		/*
		   Read the input from input.txt
		   Write your answer to output.txt
		 */
		BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			for (int i = 0; i < 3; i++) {
				stk = new StringTokenizer(br.readLine());
				for (int j = 0; j < n; j++) {
					A[i][j] = Integer.parseInt(stk.nextToken());
				}
			}


			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   YOUR CODE HERE
			   Save your answer to the variable Answer
			 */
			/////////////////////////////////////////////////////////////////////////////////////////////
			Answer = 0;

			for (int p = 0; p < 6; p++) {
				dp[0][p] = getScore(0, p);
			}

			for (int col = 1; col < n; col++) {
				// compatible pattern: (pattern + 2) % 6, (pattern + 4) % 6
				for (int p = 0; p < 6; p++) {
					int max = Integer.MIN_VALUE;
					for (int t = 1; t < 3; t++) {
						int compatiblePattern = (p + 2 * t) % 6;
						if (dp[col - 1][compatiblePattern] > max) {
							max = dp[col - 1][compatiblePattern];
						}
					}
					dp[col][p] = max + getScore(col, p);
				}
			}

			int retMax = Integer.MIN_VALUE;
			for (int p = 0; p < 6; p++) {
				if (dp[n-1][p] > retMax) {
					retMax = dp[n-1][p];
				}
			}
			Answer = retMax;


			// Print the answer to output.txt
			pw.println("#" + test_case + " " + Answer);
			// To ensure that your answer is printed safely, please flush the string buffer while running the program
			pw.flush();
		}

		br.close();
		pw.close();
	}
}

