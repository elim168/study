package main

import (
	"fmt"
	"net/http"
)

// 处理器函数。方法签名必须保持这样，方法名和参数名可以随意
func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "使用自己创建的ServeMux启动服务器 :", r.URL.Path)
}

func main() {

	// 创建自己的ServeMux

	mux := http.NewServeMux()

	// 指定访问路径对应的处理器函数
	mux.HandleFunc("/", handler)
	// 第二个参数选择使用自己新建的ServeMux实例。
	http.ListenAndServe("localhost:8999", mux)

}
