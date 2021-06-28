package main

import "fmt"

func main() {

	// for 循环一共有三种语法

	// 标准语法
	for i := 0; i < 10; i++ {
		fmt.Printf("第%d个元素的值是%d \n", i, i)
	}

	// 其中初始值和循环之后的内容是可以省略的
	i := 1
	for ; i < 10;  {
		fmt.Printf("省略初始值，第%d个元素的值是%d \n", i, i)
		i++
	}

	// 第二种语法。省略了初始值和循环之后的内容后就变成了如下形式，相当于while循环。golang中没有while循环，下面的形式就是它的while循环
	i = 1
	for i < 10 {
		fmt.Printf("while循环，第%d个元素的值是%d \n", i, i)
		i++
	}

	// 第三种语法。连条件也省略，相当于无限循环
	for {
		println("当前i的值为" + string(i))
		// 跳出循环。也可以使用continue
		break
	}

}
