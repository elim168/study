package main

import "fmt"

func main() {

	var loop = true
	for loop {
		fmt.Println("-----------------------------------------家庭记账软件-------------------------------------")
		fmt.Println("欢迎使用家庭记账软件，请选择菜单：")
		fmt.Println("1. 家庭记账明细")
		fmt.Println("2. 录入收入信息")
		fmt.Println("3. 录入支出信息")
		fmt.Println("4. 退出")
		var menu string
		fmt.Scanln(&menu)

		switch menu {
		case "1":
			fmt.Println("家庭记账明细如下：")
		case "2":
			fmt.Println("请录入收入信息")
		case "3":
			fmt.Println("请录入支出信息")
		case "4":
			fmt.Println("你退出了该软件的使用……")
			loop = false
		default:
			fmt.Println("选择的菜单错误……")
		}
	}

}
