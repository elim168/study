package main

import (
	"fmt"
	"time"
)

func main() {

	now := time.Now()
	fmt.Println(now)
	// golang已经提供了一系列的日期格式，如RFC3339，这些格式都是针对外国人的习惯的。
	fmt.Println(now.Format(time.RFC3339))
	dateStr := now.Format(time.RFC3339)
	// 基于字符串解析时间
	date,err := time.Parse(time.RFC3339, dateStr)
	if err != nil {
		panic(err)
	}
	fmt.Println("解析后的时间是：", date)
	fmt.Println("解析后的时间是：", date.Format("2006-01-02 15:04:05"))
	//you can also supply custom layouts. Layouts must use the
	// reference time `Mon Jan 2 15:04:05 MST 2006` to show the
	// pattern with which to format/parse a given time/string.
	// The example time must be exactly as shown: the year 2006,
	// 15 for the hour, Monday for the day of the week, etc.
	// 自定义格式中可以出现年月日，年必须是2006,月必须是1,日必须是2,时间必须是3/15，分必须是4,秒必须是5
	fmt.Println("解析后的时间是：", date.Format("2006-01-02 15:04:05"))//解析后的时间是： 2021-06-22 21:54:33
	fmt.Println("解析后的时间是：", date.Format("2006-1-02 3:04:05"))//解析后的时间是： 2021-6-22 9:54:33

	fmt.Println(time.Parse("2006-01-02", "2022-12-31"))


}
