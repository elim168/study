package main

import (
	"fmt"
	"io"
	"net/http"
)

func handler(w http.ResponseWriter, r *http.Request) {
	// 请求体的长度
	len := r.ContentLength
	body := make([]byte, len)
	rlen, err := r.Body.Read(body)
	if err != io.EOF {
		fmt.Fprintln(w, "读取请求体出错：", err)
		return
	}
	fmt.Fprintln(w, "读取到的请求体长度是：", rlen)
	fmt.Fprintln(w, "读取到的请求体内容是：", string(body))
}

func main() {

	http.HandleFunc("/body", handler)
	http.ListenAndServe("localhost:8999", nil)

}
