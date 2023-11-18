import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. Compile the file with the following command. The class file named Solution1 would be created when you compile the source code.
       javac Solution1.java -encoding UTF8

   2. Run the compiled program with the following command. Output file(output.txt) should be created after the program is finished
       java Solution1

   - The encoding of the source code should be UTF8
   - You can use the 'time' command to measure your algorithm.
       time java Solution1
   - You can also halt the program with the 'timeout' command.
       timeout 0.5 java Solution1
       timeout 1 java Solution1
 */

class Solution1 {

	/*
	   Since the results cannot be covered with the boundary of int type, use long type for the variable of the result.
	   Overflowed cases will be counted in zero points.
	   We assume that Answer[0] is the number of "a", Answer[1] is the number of "b", Answer[2] is the number of "c".
	*/
	static int n;                           // string length
	static String s;                        // string sequence
	static long[] Answer = new long[3];

	final static char[][] table = {
			{1, 1, 0},
			{2, 1, 0},
			{0, 2, 2}
	};

	static long[][][] dp = new long[30][30][3];

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
			s = br.readLine();

			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   YOUR CODE HERE
			   You can massage string s whatever you want.
			   Save your answer to the variable Answer(long type!)
			 */
			/////////////////////////////////////////////////////////////////////////////////////////////
			Answer[0] = 0;  // the number of a
			Answer[1] = 0;  // the number of b
			Answer[2] = 0;  // the number of c

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					for (int k = 0; k < 3; k++) {
						dp[i][j][k] = 0;
					}
				}
			}

			for (int i = 0; i < n - 1; i++) {
				dp[i][i][s.charAt(i) - 'a']++;
				dp[i][i + 1][table[s.charAt(i) - 'a'][s.charAt(i + 1) - 'a']]++;
			}
			dp[n-1][n-1][s.charAt(n-1) - 'a']++;

			for (int len = 3; len <= n; len++) {
				for (int i = 0; i < n - len + 1; i++) {
					int j = i + len - 1;
					dp[i][j][0] = dp[i][j][1] = dp[i][j][2] = 0;
					for (int k = i; k < j; k++) {
						dp[i][j][0] += (dp[i][k][0] * dp[k + 1][j][2] + dp[i][k][1] * dp[k + 1][j][2] + dp[i][k][2] * dp[k + 1][j][0]);
						dp[i][j][1] += (dp[i][k][0] * dp[k + 1][j][0] + dp[i][k][0] * dp[k + 1][j][1] + dp[i][k][1] * dp[k + 1][j][1]);
						dp[i][j][2] += (dp[i][k][1] * dp[k + 1][j][0] + dp[i][k][2] * dp[k + 1][j][1] + dp[i][k][2] * dp[k + 1][j][2]);
					}
				}
			}

			for (int i = 0; i < 3; i++) {
				Answer[i] = dp[0][n - 1][i];
			}

			// Print the answer to output.txt
			pw.println("#" + test_case + " " + Answer[0] + " " + Answer[1] + " " + Answer[2]);
			// To ensure that your answer is printed safely, please flush the string buffer while running the program
			pw.flush();
		}

		br.close();
		pw.close();
	}
}

