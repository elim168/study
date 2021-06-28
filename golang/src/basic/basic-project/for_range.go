package main

import "fmt"

func main() {


	/**
	范围(range)for 循环的 range 格式可以对 slice 或者 map 进行迭代循环。
	当使用 for 循环遍历一个 slice 时，每次迭代 range 将返回两个值。 第一个是当前下标(序号)，第二个是该下标所对应元素的一个拷贝。
	package main

	import "fmt"

	var pow = []int{1, 2, 4, 8, 16, 32, 64, 128}

	func main() {
	    for i, v := range pow {
	        fmt.Printf("2**%d = %d\n", i, v)
	    }
	}
	Go
	可以通过赋值给 _ 来忽略序号和值。
	如果只需要索引值，去掉 “ , value ” 的部分即可。
	package main

	import "fmt"

	func main() {
	    pow := make([]int, 10)
	    for i := range pow {
	        pow[i] = 1 << uint(i)
	    }
	    for _, value := range pow {
	        fmt.Printf("%d\n", value)
	    }
	}
	*/

	var pow = []int{1, 2, 4, 8, 16, 32, 64, 128}
	for i, v := range pow {
		fmt.Printf("2**%d = %d\n", i, v)
	}


	// 忽略值
	pow = make([]int, 10)
	for i := range pow {
		pow[i] = 1 << uint(i)
	}
	// 忽略索引，索引用_表示
	for _, value := range pow {
		fmt.Printf("%d\n", value)
	}

}
