package main

import "fmt"

func sort(nums []int, start, end int) {
	if start >= end {
		return
	}
	low := start + 1
	high := end
	mid := start
	for low < high {
		for low < high && nums[low] < nums[mid] {
			low++
		}
		for low < high && nums[high] > nums[mid] {
			high--
		}
		// low指针位置的数字不比mid位置小，high位置不比mid位置大。low和high换一个位置，可能它们还可以继续比
		nums[low], nums[high] = nums[high], nums[low]
	}
	// 此时low左边的数字都比mid位置的小，右边位置的数字都比mid位置的大
	// 8 1 2 3 4 5 6 10 12 13
	if nums[low] > nums[mid] {
		low--
	}
	// 与低位的数字进行位置调换
	nums[mid], nums[low] = nums[low], nums[mid]
	sort(nums, start, low-1)
	sort(nums, low+1, end)
}

func quickSort(nums []int) []int {
	sort(nums, 0, len(nums)-1)
	return nums
}

func main() {

	fmt.Println(quickSort([]int{10, 9, 8, 7, 6, 5}))
	fmt.Println(quickSort([]int{10, 19, 28, 37, 46, 55}))
	fmt.Println(quickSort([]int{8, 10, 22, 55, 32, 7, 3}))

}
