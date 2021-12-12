package main

// 引用的包的前缀是包的模块名example.com/user/hello，之后的包morestrings是放在模块的morestrings目录下的。
import (
	"fmt"
	"example.com/user/hello/morestrings"
	"github.com/google/go-cmp/cmp"
)

func main() {
	fmt.Println("Hello World!")
	fmt.Println(morestrings.ReverseRunes("Hello World! Hello Golang!"))
	fmt.Println(cmp.Diff("Hello World", "Hello Golang"))
}
