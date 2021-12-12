package main

import "fmt"

/*
* 选择排序。
* 它的思想是：第1次从整个数组中找到最小的数字和第0个位置交换，第n次找到第n小的数和第n-1个位置交换
 */

// 入参是切片，切片是引用传递，如果改成数组，则需要使用指针。
func selectSort(nums []int) {
	if len(nums) < 2 {
		return
	}

	// 最后一次只剩一个数字，不用比较了，所以比较的是len(nums)-1次
	for i := 0; i < len(nums)-1; i++ {
		// 第一次假设最小值就是第一个值，记录最小值的索引，因为之后需要拿每次剩余数字中最小的值进行位置交换。第i次的最小值的索引默认为i
		minIndex := i
		for j := i + 1; j < len(nums); j++ {
			if nums[j] < nums[minIndex] {
				minIndex = j
			}
		}
		// 最小值的索引更换了才进行交换
		if minIndex != i {
			nums[minIndex], nums[i] = nums[i], nums[minIndex]
		}
		fmt.Printf("第%d次选择后的结果为：%v\n", i+1, nums)
	}

}

func printSort(nums []int) {
	fmt.Println("排序前：", nums)
	selectSort(nums)
	fmt.Println("排序后：", nums)
}

func main() {
	printSort([]int{9, 5, 16, 23, 68, 92, 37, 21, 7, 10, 3})
	printSort([]int{5, 4, 3, 2, 1, 6})
}
