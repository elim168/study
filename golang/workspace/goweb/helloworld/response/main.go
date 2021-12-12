package main

import (
	"encoding/json"
	"net/http"
)

// 直接响应文本
func responseText(w http.ResponseWriter, r *http.Request) {
	w.Write([]byte("你好，中国"))
}

// 直接响应HTML
func responseHtml(w http.ResponseWriter, r *http.Request) {
	html := `
		<html>
			<head><title>网页标题</title></head>
			<body>
				<h1 style="color:red">中国红</h1>
			</body>
		</html>
	`
	w.Write([]byte(html))
}

type User struct {
	Id   int    `json:"id"`
	Name string `json:"name"`
	Age  int    `json:"age"`
}

// 响应JSON字符串
func responseJson(w http.ResponseWriter, r *http.Request) {
	// 指定响应的内容格式
	w.Header().Add("Content-Type", "application/json")
	user := User{
		Id:   1,
		Name: "张三",
		Age:  30,
	}
	jsonByte, err := json.Marshal(user)
	if err != nil {
		w.Write([]byte(err.Error()))
		return
	}
	w.Write(jsonByte)
}

// 还可以指定响应的状态码
func responseRedirect(w http.ResponseWriter, r *http.Request) {
	// 指定需要重定向的网址
	w.Header().Add("Location", "http://www.qq.com")
	// 指定响应的状态码
	w.WriteHeader(http.StatusFound)
}

func main() {
	http.HandleFunc("/response/text", responseText)
	http.HandleFunc("/response/html", responseHtml)
	http.HandleFunc("/response/json", responseJson)
	http.HandleFunc("/response/redirect", responseRedirect)
	http.ListenAndServe("localhost:8999", nil)
}
