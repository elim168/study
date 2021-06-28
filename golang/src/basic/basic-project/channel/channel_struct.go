package main

import "fmt"

type Person struct {
	id int
	name string
}


func main() {

	// channel也可以传送自定义对象
	c1 := make(chan Person)

	go func() {
		p1 := Person{1, "ZhangSan"}
		c1 <- p1
	}()

	p := <-c1
	fmt.Println("收到了通过c1传过来的person对象", p)

}
