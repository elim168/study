package main

import "fmt"

func main() {
	/**
	The make built-in function allocates and initializes an object of type slice, map, or chan (only).
	Like new, the first argument is a type, not a value.
	Unlike new, make's return type is the same as the type of its argument, not a pointer to it.
	The specification of the result depends on the type:
	 Slice: The size specifies the length. The capacity of the slice is
	 equal to its length. A second integer argument may be provided to
	 specify a different capacity; it must be no smaller than the
	 length. For example, make([]int, 0, 10) allocates an underlying array
	 of size 10 and returns a slice of length 0 and capacity 10 that is
	 backed by this underlying array.
	 Map: An empty map is allocated with enough space to hold the
	 specified number of elements. The size may be omitted, in which case
	 a small starting size is allocated.
	 Channel: The channel's buffer is initialized with the specified
	 buffer capacity. If zero, or the size is omitted, the channel is
	 unbuffered.
	*/
	// 创建一个slice。此时它的容量和长度都为5
	a := make([]int, 5)
	printSlice("a", a)
	// 创建一个slice，指定长度为0,容量为5
	b := make([]int, 0, 5)
	printSlice("b", b)
	// 切分第0-2个元素，即[0,0]
	c := b[:2]
	printSlice("c", c)
	// 切分第2-5个元素，即[0,0,0]
	d := c[2:5]
	printSlice("d", d)

	/**
	make出来的slice进行切片后底层数据是没有共用的。
	*/
	d[0] = 10
	printSlice("b", b)
	c[0] = 20
	printSlice("b", b)

	/**
	直接数组切片出来的slice，其底层数据是共用的，改变了f[0]后也改变了e中相应的元素的值
	 */
	e := []int{1, 2, 3, 4, 5}
	f := e[0:3]
	f[0] = 50
	printSlice("e", e)

	// append会生成新的slice对象
	d = append(d, 1, 2, 3, 4, 5, 7)
	println(fmt.Sprint(d)) //[0 0 0 1 2 3 4 5 7]

}

func printSlice(s string, x []int) {
	fmt.Printf("%s len=%d cap=%d %v\n",
		s, len(x), cap(x), x)
}
