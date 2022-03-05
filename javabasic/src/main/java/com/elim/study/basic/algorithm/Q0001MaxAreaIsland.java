package com.elim.study.basic.algorithm;

/**
 *
 * 给定一个包含了一些 0 和 1 的非空二维数组 grid 。
 * 一个 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在水平或者竖直方向上相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
 * 找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为 0 。)
 *
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,1,1,0,1,0,0,0,0,0,0,0,0],
 *  [0,1,0,0,1,1,0,0,1,0,1,0,0],
 *  [0,1,0,0,1,1,0,0,1,1,1,0,0],
 *  [0,0,0,0,0,0,0,0,0,0,1,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 * 对于上面这个给定矩阵应返回 6。注意答案不应该是 11 ，因为岛屿只能包含水平或垂直的四个方向的 1 。
 *
 * 示例 2:
 *
 * [[0,0,0,0,0,0,0,0]]
 * 对于上面这个给定的矩阵, 返回 0。
 *
 * @author Elim
 * 21-5-24
 */
public class Q0001MaxAreaIsland {

  public static void main(String[] args) {

    int[][] grid = new int[][]{
            {0,0,1,0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,1},
            {0,1,1,0,1,0,0,0,0,0,0,0,1},
            {0,1,0,0,1,1,0,0,1,0,1,0,1},
            {0,1,0,0,1,1,0,0,1,1,1,0,1},
            {0,0,0,0,0,0,0,0,0,0,1,0,1},
            {0,0,0,0,0,0,0,1,1,1,0,1,1},
            {0,0,0,0,0,0,0,1,1,0,0,0,0}};

    Q0001MaxAreaIsland obj = new Q0001MaxAreaIsland();
    int maxArea = obj.maxAreaOfIsland(grid);
    System.out.println("最大的面积是：" + maxArea);
  }

  public int maxAreaOfIsland(int[][] grid) {
    int maxRow = grid.length;
    int maxCol = grid[0].length;
    int maxValue = 0;
    for (int i=0; i<maxRow; i++) {
      for (int j=0; j<maxCol; j++) {
        int value = grid[i][j];
        if (value == 0) {
          continue;
        }
        int maxArea = this.maxArea(grid, i, j);
        maxValue = Math.max(maxValue, maxArea);
      }
    }
    return maxValue;
  }

  private int maxArea(int[][] grid, int row, int col) {
    int maxRow = grid.length;
    int maxCol = grid[0].length;
    int maxArea = 0;
    // 节点的索引已经超出范围了，或者所在位置的值已经为0了则返回
    if (row < 0 || col < 0 || row >= maxRow || col >= maxCol || grid[row][col] == 0) {
      return maxArea;
    }
    maxArea += 1;
    // 为了避免重复计算当前位置的值，设置当前位置的值为0。
    grid[row][col] = 0;
    // 分别寻找当前位置的上下左右的面积
    maxArea += this.maxArea(grid, row - 1, col);
    maxArea += this.maxArea(grid, row + 1, col);
    maxArea += this.maxArea(grid, row, col - 1);
    maxArea += this.maxArea(grid, row, col + 1);
    return maxArea;
  }

}
