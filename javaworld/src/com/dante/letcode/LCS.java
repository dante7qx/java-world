package com.dante.letcode;

import java.util.Arrays;

/**
 * 最大公共子序列
 * 
 * @author dante
 *
 */

public class LCS {
	/**
	 * 第一步：论证是否是动态规划问题 首先要证明最长公共子序列问题是动态规划问题,即符合动态规划算法的两个特点:最优子结构和重叠子问题 最优子结构:
	 * 记:Xi=﹤x1...xi﹥即X序列的前i个字符 (1≤i≤m)（前缀） Yj=﹤y1...yj﹥即Y序列的前j个字符 (1≤j≤n)（前缀）
	 * 假定Z=﹤z1...zk﹥∈LCS(X , Y)。 若xm=yn（最后一个字符相同），则问题化归成求Xm-1与Yn-1的LCS（LCS(X ,
	 * Y)的长度等于LCS(Xm-1 , Yn-1)的长度加1）。 若xm≠yn，则问题化归成求Xm-1与Y的LCS及X与Yn-1的LCS。LCS(X ,
	 * Y)的长度为：max{LCS(Xm-1 , Y)的长度, LCS(X , Yn-1)的长度}。 由于上述当xm≠yn的情况中，求LCS(Xm-1 ,
	 * Y)的长度与LCS(X , Yn-1)的长度，这两个问题不是相互独立的：两者都需要求LCS(Xm-1，Yn-1)的长度。
	 * 另外两个序列的LCS中包含了两个序列的前缀的LCS，故问题具有最优子结构性质。 重叠子问题：
	 * 在计算X和Y的最长公共子序列时，可能要计算出X和Yn-1及Xm-1和Y的最长公共子序列，
	 * 而这两个子问题都包含一个公共子问题，即计算Xm-1和Yn-1的最长公共子序列，因此最长公共子序列问题具有子问题重叠性质。
	 *
	 * 第二步：建立递归式 用c[i][j]记录序列Xi和Yj的最长公共子序列的长度。其中Xi=<x1, x2, …, xi>，Yj=<y1, y2, …,
	 * yj>。建立递归关系如下： 0 if i=0||j=0 c[i][j]= c[i-1][j-1]+1 if i,j>0&&x[i]==y[j]
	 * max(c[i][j-1],c[i-1][j]) if i,j>0&&x[i]!=y[j];
	 */

	/**
	 * 第三步：计算最优值 计算最长公共子序列长度的动态规划算法LCS_LENGTH(X,Y)以序列X=<x1, x2, …, xm>和Y=<y1, y2, …,
	 * yn>作为输入。 输出两个数组c[0..m ,0..n]和b[1..m
	 * ,1..n]。其中c[i,j]存储Xi与Yj的最长公共子序列的长度，b[i,j]记录指示c[i,j]的值是由哪一个子问题的解达到的，
	 * 这在构造最长公共子序列时要用到。最后，X和Y的最长公共子序列的长度记录于c[m,n]中。
	 * 在这里可以将数组b省去。事实上，数组元素c[i,j]的值仅由c[i-1,j-1]，c[i-1,j]和c[i,j-1]三个值之一确定，而数组元素b[i,j]也只是用来指示c[i,j]究竟由哪个值确定。
	 * 因此，在算法LCS中，我们可以不借助于数组b而借助于数组c本身临时判断c[i,j]的值是由c[i-1,j-1]，c[i-1,j]和c[i,j-1]中哪一个数值元素所确定，代价是Ο(1)时间。
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public int[][] lcsLength(char x[], char y[]) {
		int m = x.length;
		int n = y.length;
		int[][] c = new int[m + 1][n + 1];
		for (int i = 0; i < m + 1; i++)
			c[i][0] = 0;
		for (int j = 0; j < n + 1; j++)
			c[0][j] = 0;
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				// i,j从1开始，所以下面用i-1和j-1使得可以从数组0元素开始
				if (x[i - 1] == y[j - 1]) {
					c[i][j] = c[i - 1][j - 1] + 1;
				} else if (c[i - 1][j] >= c[i][j - 1]) {
					c[i][j] = c[i - 1][j];
				} else {
					c[i][j] = c[i][j - 1];
				}
			}
		}
		return c;
	}

	/**
	 * 第四步：构造最长公共子序列 lcs函数用来构造最长公共子序列,它使用在计算最优值中得到的c数组可以快速的 构造序列X=<x1, x2, …,
	 * xm>和Y=<y1, y2, …, yn>的最长公共子序列。
	 * 
	 * @param c
	 * @param x
	 * @param i
	 * @param j
	 */
	public void lcs(int c[][], char x[], int i, int j) {
		if (i == 0 || j == 0)
			return;
		if (c[i][j] == (c[i - 1][j - 1] + 1)) {
			lcs(c, x, i - 1, j - 1);
			// 注意c的长度要比x大1
            System.out.println(x[i-1]);
		} else if (c[i][j] == c[i - 1][j]) {
			lcs(c, x, i - 1, j);
		} else {
			lcs(c, x, i, j - 1);
		}
	}

	// 测试
	public static void main(String args[]) {
		char x[] = { 'a', 'c', 'b', 'c', 'a' };
		char y[] = { 'a', 'b', 'a', 'c' };
		LCS lcs = new LCS();
		int[][] c = lcs.lcsLength(x, y);
		print(c);
		lcs.lcs(c, x, x.length, y.length);
		print(c);
		
		
	}
	
	
	private static void print(int[][] arg) {
		for (int[] ints : arg) {
			System.out.println(Arrays.toString(ints));
		}
	}
}
