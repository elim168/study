package main

import (
	"fmt"
	"os"
)

func main() {

	dir, err := os.ReadDir("/home/elim/dev/projects/golang/followdoc/tutorial3_web/gowiki/data")
	if err != nil {
		panic(err)
	}
	var items = make([]string, len(dir))
	fmt.Println(len(dir))
	for i := 0; i < len(dir); i++ {
		name := dir[i].Name()
		items[i] = name[:len(name) - len(".txt")]
	}
	fmt.Println(items)

}
