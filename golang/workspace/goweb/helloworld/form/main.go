package main

import (
	"fmt"
	"net/http"
)

/**
从表单中获取参数
*/

func handler(w http.ResponseWriter, r *http.Request) {
	// 调用ParseForm()后会把URL中的查询参数和Form表单的参数进行解析，并把它们添加到r.Form中。
	// Form表单中的参数除了会添加到r.Form中外，还会添加到r.PostForm中。当同一个参数既在查询参数中有，又
	// 在Form表单中存在时，r.Form中会同时有该参数中的值，而PostForm中只会有Form表单提交的值。
	// r.Form和r.PostForm都是url.Values类型的值，而url.Values类型是map[string][]string类型，即一个参数可以拥有多个值。
	r.ParseForm()
	fmt.Fprintln(w, "从Form中获取的参数是：", r.Form)
	fmt.Fprintln(w, "从PostForm中获取的参数是：", r.PostForm)

	// 解析Multipart类型的Form表单。里面会根据需要调用r.ParseForm()
	// r.ParseMultipartForm(1024 * 1024 * 100)
	// fmt.Println(w, "从MultipartForm中获取到的参数是：", r.MultipartForm)

	// 上面获取Form表单参数的方式需要先调用ParseForm()解析表单。其实有一个更快速的获取表单参数的方式，就是FormValue("参数名")，
	// 其内部会根据需要调用ParseForm()。这样就不需要先显式的拿到r.Form或r.PostForm再获取表单参数了。
	name := r.FormValue("name")
	// 表单和查询参数中拥有同一个参数时，通过这种方式获取的参数，表单中的该参数拥有更高的优先级。
	fmt.Fprintln(w, "从Form表单获取到的参数name是：", name)

	// 类似的还有PostFormValue()和FormFile()
	// r.PostFormValue("name")
	// r.FormFile("fileName")
}

func main() {

	http.HandleFunc("/form", handler)
	http.ListenAndServe("localhost:8999", nil)

}
