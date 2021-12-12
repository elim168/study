package main

import "fmt"

// 格式化   gofmt -w main.go

func quickSort(nums []int, start, end int) {
	if start >= end {
		return
	}
	// 选择start作为基准
	i, j := start+1, end
	for i < j {
		// 找到第一个比基准数大的数
		for i < j && nums[i] <= nums[start] {
			i++
		}
		// 从右往左找到第一个比基准数小的数
		for i < j && nums[j] > nums[start] {
			j--
		}
		if i < j {
			// 大的和小的交换一次位置，可以继续遍历
			nums[i], nums[j] = nums[j], nums[i]
		}
	}
	// 有可能刚好走过去越界了，所以要减1
	if nums[i] > nums[start] {
		i--
	}
	nums[i], nums[start] = nums[start], nums[i]
	quickSort(nums, start, i-1)
	quickSort(nums, i+1, end)
}

func sortAndPrint(nums []int) {
	fmt.Println("before sort:", nums)
	quickSort(nums, 0, len(nums)-1)
	fmt.Println("after sort:", nums)
}

func main() {
	nums := []int{8, 7, 6, 5, 4, 3, 2, 1, 9}
	sortAndPrint(nums)
	nums = []int{9, 9, 8, 6, 6, 8, 10, 2, 3, 5, 10, 2, 22, 55, 9}
	sortAndPrint(nums)
	sortAndPrint([]int{4, 1, 2, 3, 5, 6, 7})
	sortAndPrint([]int{1, 1, 1, 1, 1, 1, 1})
}
