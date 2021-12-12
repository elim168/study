package utils

import (
	"fmt"
	"html/template"
	"net/http"
)

// 对模板响应信息的包装。view不需要指定前面的views和.html
func Response(w http.ResponseWriter, view string, data interface{}) {
	viewPath := fmt.Sprintf("views/pages/%s.html", view)
	t := template.Must(template.ParseFiles(viewPath))
	t.Execute(w, data)
}

// 直接响应文本数据
func ResponseText(w http.ResponseWriter, text string) {
	w.Write([]byte(text))
}
