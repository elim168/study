package main

import (
	"fmt"
	"time"
)

func main() {

	fmt.Println(time.Now())
	// 分别取年月日	2021 June 22
	fmt.Println(time.Now().Year(), time.Now().Month(), time.Now().Day())
	// 一次取得年月日  2021 June 22
	year, month, day := time.Now().Date()
	// 月份默认是英文表示。取其数字可以使用如下方式。2021 June 6 22
	fmt.Println(year, month, int(month), day)

	date := time.Date(2021, 11, 1, 10, 10, 10, 0, time.Local)
	fmt.Println(date)// 2021-11-01 10:10:10 +0800 CST

	fmt.Println(time.Now().Weekday()) // Tuesday
	fmt.Println(int(time.Now().Weekday())) // 取星期的数字表示。第一天是星期天，从0开始。

	fmt.Println(date.After(time.Now()))
	fmt.Println(date.Before(time.Now()))
	fmt.Println(date.Equal(time.Now()))
	fmt.Println(date.Sub(time.Now()))//3157h25m32.268332642s
	fmt.Println(date.Sub(time.Now()).Hours())//3157.395506310397

	// 往后推30天，如果是减可以传递负数123
	fmt.Println(time.Now().Add(time.Hour * 24 * 30), time.Now().Add(time.Hour * 24 * -30))

	// 当前时间的秒表示
	seconds := time.Now().Unix()
	// 当前时间的纳秒
	nanosecond := time.Now().UnixNano()
	// 毫秒可以通过纳秒进行计算
	millSecond := nanosecond / 1E6
	fmt.Println(seconds, millSecond, nanosecond)



}
