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
	fmt.Fprintln(w, "使用自定义的处理器", r.URL.Path)
}

func main() {

	// 通过Handle方法指定请求路径对应的处理器。
	http.Handle("/", &MyHandler{})
	// 第二个参数是handler。The handler is typically nil, in which case the DefaultServeMux is used.
	// 因为传入的默认是nil将使用DefaultServeMux，所以我们上面才能通过http.Handle("/", &MyHandler{})指定请求路径的处理器。
	http.ListenAndServe("localhost:8999", nil)

}
