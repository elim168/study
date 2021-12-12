package main

import (
	"fmt"
	"math"
)

/**
给定一个非负整数数组 nums 和一个整数 m ，你需要将这个数组分成 m 个非空的连续子数组。

设计一个算法使得这 m 个子数组各自和的最大值最小。



示例 1：

输入：nums = [7,2,5,10,8], m = 2
输出：18
解释：
一共有四种方法将 nums 分割为 2 个子数组。 其中最好的方式是将其分为 [7,2,5] 和 [10,8] 。
因为此时这两个子数组各自的和的最大值为18，在所有情况中最小。

示例 2：

输入：nums = [1,2,3,4,5], m = 2
输出：9

示例 3：

输入：nums = [1,4,4], m = 3
输出：4



提示：

    1 <= nums.length <= 1000
    0 <= nums[i] <= 106
    1 <= m <= min(50, nums.length)

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/split-array-largest-sum
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
*/

// solution1 分隔成多个子数组后，各个子数组的和的最大值一定在原数组的最大值n1和原数组的所有值的和n2之间。
// 那么我们在n1和n2之间取值k来分割数组为m段，然后取这个分割值的最小值即为答案。
// 我们采用二分法的思想来使值k慢慢逼近答案
func solution1(nums []int, m int) int {

	var n1, n2 int
	for _, num := range nums {
		n2 += num
		if num > n1 {
			n1 = num
		}
	}

	for n1 < n2 {
		mid := n1 + (n2-n1)/2
		splitCount := countSplitNums(nums, mid)
		if splitCount == m {
			// 分段数跟预计的一致，但是选取的分段和不一定是最小的。我们把它往左靠，尽量取一个更小的。如果它就是最终的最大的，n1会慢慢靠过来，最终会n1==n2，此时取n1的结果即可。
			n2 = mid
		} else if splitCount < m {
			// 分段数比预计的少则选取的分段和太大了，需要减小分段和，n2往左靠。
			// 因为二分法最终的结果是会趋近左边的n1的，此时n2不能减1。减1可能导致n2-1最终取不到了。
			n2 = mid
		} else {
			// 分段数比预计的多则选取的分段和太小了，需要增加分段和（>mid），n1往右靠
			n1 = mid + 1
		}
	}

	return n1
}

// 对nums进行分组，每组的和不超过maxSum，返回最终的分组数
func countSplitNums(nums []int, maxSum int) int {
	// 一开始就需要有一组，考虑所有的情况加起来都不超过maxSum的情形
	count, sum := 1, 0
	for _, num := range nums {
		if sum+num > maxSum {
			count++
			// num属于新的一组的第一个元素，此时新的sum就等于num
			sum = num
		} else {
			sum += num
		}
	}
	return count
}

// solution2 动态规划[1 2 3 4 5 6]
// 把长度为n的数组分成m段，每段的和的最大值的最小值最小。
// 设定函数f[i][j]为长度为i的数组分成j段后每段和的最大值的最小值。考虑最后一段从k开始，则f[i][j] = max(f[k-1][j-1], sum(k,i))。k的取值范围是[j,i]
func solution2(nums []int, m int) int {

	n := len(nums)
	// 表示每一个位置到开始位置所有元素的和。包括该位置自身，且计算的起始位置是从1开始。
	sums := make([]int, n+1)
	for i, num := range nums {
		sums[i+1] = sums[i] + num
	}

	f := make([][]int, n+1)
	// 初始化f的第2层的元素长度为m+1
	for i := range f {
		f[i] = make([]int, m+1)
		// j=0是一个不合理的值，初始化为一个非常大的值，这样就会是选择不到的
		f[i][0] = math.MaxInt32
	}

	f[0][0] = 0

	for i := 1; i <= n; i++ {
		for j := 1; j <= min(i, m); j++ {
			// 初始化f[i][j]为一个比较大的值。这样在求min的时候才能忽略初始值
			f[i][j] = math.MaxInt64
			for k := 1; k <= i; k++ {
				// 对于f[i][j]有多种分法，每次选择了k后取各部分和的最大值，最后取所有分法的最小值即为f[i][j]的解。
				f[i][j] = min(f[i][j], max(f[k-1][j-1], sums[i]-sums[k-1]))
			}
		}
	}

	// 返回f[n][m]，即表示n个数的数组分为m段后每段的和的最大值的最小值
	return f[n][m]

}

func min(x, y int) int {
	if x < y {
		return x
	}
	return y
}

func max(x, y int) int {
	if x > y {
		return x
	}
	return y
}

// 解决方案函数
type solutionFunc func([]int, int) int

func printSolution(nums []int, m int, fn solutionFunc) {
	res := fn(nums, m)
	fmt.Printf("数组:%v分割成%v个子数组后，最大和的最小值为：%v \n", nums, m, res)
}

func main() {
	printSolution([]int{7, 2, 5, 10, 8}, 2, solution1)
	printSolution([]int{7, 2, 5, 10, 8}, 2, solution2)
	printSolution([]int{1, 2, 3, 4, 5}, 2, solution1)
	printSolution([]int{1, 2, 3, 4, 5}, 2, solution2)
	printSolution([]int{1, 4, 4}, 3, solution1)
	printSolution([]int{1, 4, 4}, 3, solution2)
}
