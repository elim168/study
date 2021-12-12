package main

import (
	"html/template"
	"net/http"
)

// 响应信息来自于模板文件。go run main.go执行程序要进入到template这层文件夹下才能取到模板，否则就是文件不存在

func template1(w http.ResponseWriter, r *http.Request) {
	// 解析模板文件，响应第一个文件对应的模板
	// t, err := template.ParseFiles("tpls/template1.html")
	// if err != nil {
	// 	panic(err)
	// }
	// 上面四行等价于下面这一行。Must()函数会确保返回的err不为nil，否则就panic
	t := template.Must(template.ParseFiles("tpls/template1.html"))
	// 把模板文件以指定的数据（这里传的是nil）渲染后的内容写入w。
	t.Execute(w, nil)
}

func template2(w http.ResponseWriter, r *http.Request) {
	// 解析模板文件，响应第一个文件对应的模板
	t := template.Must(template.ParseFiles("tpls/template2.html"))
	// 把模板文件以指定的数据（这里传的是hello world）渲染后的内容写入w。
	t.Execute(w, "hello world")
}

func template3(w http.ResponseWriter, r *http.Request) {
	// 解析模板文件，也可以同时指定多个模板文件，响应第一个文件对应的模板
	t := template.Must(template.ParseFiles("tpls/template2.html", "tpls/template1.html"))
	// 如果需要选择使用的模板，则可以使用ExecuteTemplate()
	t.ExecuteTemplate(w, "template1.html", nil)
}

func template4(w http.ResponseWriter, r *http.Request) {
	// 解析模板文件，也可以同时指定多个模板文件，响应第一个文件对应的模板
	t := template.Must(template.ParseFiles("tpls/template2.html", "tpls/template1.html", "tpls/template4.html"))
	// 数据也可以传一个结构体对象或map等其它对象
	data := make(map[string]string)
	data["p1"] = "value1"
	data["p2"] = "value21"
	data["p3"] = "value31"
	data["p4"] = "value41"
	data["p5"] = "value51"
	data["p6"] = "value61"
	t.ExecuteTemplate(w, "template4.html", data)
}

func template5(w http.ResponseWriter, r *http.Request) {
	// 可以通过ParseGlob()按照表达式获取模板文件
	t := template.Must(template.ParseGlob("tpls/*.html"))
	// 通过参数决定取哪个模板文件
	tpl := r.FormValue("tpl")
	t.ExecuteTemplate(w, tpl+".html", nil)
}

func main() {

	http.HandleFunc("/template1", template1)
	http.HandleFunc("/template2", template2)
	http.HandleFunc("/template3", template3)
	http.HandleFunc("/template4", template4)
	http.HandleFunc("/template5", template5)
	http.ListenAndServe("localhost:8999", nil)

}
