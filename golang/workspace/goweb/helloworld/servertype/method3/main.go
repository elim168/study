package main

import (
	"fmt"
	"net/http"
)

// 请求路径的处理器除了可以使用处理器函数外，也可以定义自己的处理器。

type MyHandler struct {
}

// 自定义的处理器必须实现Handler接口的ServeHTTP方法，签名如下。
func (this *MyHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "使用自定义的处理器处理所有请求", r.URL.Path)
}

func main() {

	// 第二个参数是handler。The handler is typically nil, in which case the DefaultServeMux is used.
	// 这里传入自定义的Handler，则在请求时请求任意的路径都会被MyHandler处理。MyHandler内部其实可以再根据请求路径分配给不同的Handler处理，
	// 默认的DefaultServeMux就是这么干的。
	http.ListenAndServe("localhost:8999", &MyHandler{})

}
