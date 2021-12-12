package main

import (
	"fmt"
	"time"
)

func main() {
	go func() {
		// panic后整个程序就退出了
		panic("Error Happend!")
	}()

	go func() {
		for {
			time.Sleep(time.Millisecond * 1000)
			fmt.Println("Now is:", time.Now())
		}
	}()

	var a = "bcdddddflsfjlsfjlsjflsjflsjfsjflsfjlsjfjsfjfffffffffffffffffffffffffffffffffflsjflsjflajlfjaljfljasfljslfjlsjflsjfljsalfjsljfsljfl	sjfljalfjslfjlsjflsjflsjfl"

	fmt.Println(a)
	time.Sleep(time.Second * 10)

}
