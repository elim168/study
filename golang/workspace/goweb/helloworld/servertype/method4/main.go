package main

import (
	"fmt"
	"net/http"
	"time"
)

type MyHandler struct {
}

// 自定义的处理器必须实现Handler接口的ServeHTTP方法，签名如下。
func (this *MyHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "使用自定义的Server", r.URL.Path)
}

func main() {
	// 不指定Server的Handler时将使用默认的DefaultServeMux，此时就可以通过http.Handle()或http.HandleFunc()创建请求路径对应的处理器了。
	http.Handle("/myServer", &MyHandler{})
	// 如果想对创建的Server进行更精细化的管理，则可以自己创建Server。默认通过http.ListenAndServe使用的是底层自动创建的Server。
	server := http.Server{
		ReadTimeout: 10 * time.Second,
		Addr:        "localhost:8999",
		// Handler:     &MyHandler{}, // 使用自定义的Handler，如果不传递则还是会使用默认的DefaultServeMux
	}

	// 调用的方法就需要改为server的ListenAndServe()
	server.ListenAndServe()

}
