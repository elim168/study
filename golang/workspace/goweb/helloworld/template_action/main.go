package main

import (
	"net/http"
	"text/template"
)

func ifHandler(w http.ResponseWriter, r *http.Request) {
	t := template.Must(template.ParseFiles("if.html"))

	t.Execute(w, 18)
}

func rangeHandler(w http.ResponseWriter, r *http.Request) {
	t := template.Must(template.ParseFiles("range.html"))
	values := []string{"A", "B", "C", "D", "E"}
	t.Execute(w, values)
}

func withHandler(w http.ResponseWriter, r *http.Request) {
	t := template.Must(template.ParseFiles("with.html"))
	values := []string{"A", "B", "C", "D", "E"}
	t.Execute(w, values)
}

func templateHandler(w http.ResponseWriter, r *http.Request) {
	t := template.Must(template.ParseFiles("template1.html", "template2.html"))
	t.ExecuteTemplate(w, "template1.html", "~Hello Template")
}

func defineHandler(w http.ResponseWriter, r *http.Request) {
	t := template.Must(template.ParseFiles("define.html"))
	// 模板template1是在define.html中通过define标签定义的
	t.ExecuteTemplate(w, "template1", "~Hello Define")
}

func blockHandler(w http.ResponseWriter, r *http.Request) {
	t := template.Must(template.ParseFiles("block.html"))
	// 模板template1是在define.html中通过define标签定义的
	t.Execute(w, "~Hello Block")
}

func main() {
	http.HandleFunc("/if", ifHandler)
	http.HandleFunc("/range", rangeHandler)
	http.HandleFunc("/with", withHandler)
	http.HandleFunc("/template", templateHandler)
	http.HandleFunc("/define", defineHandler)
	http.HandleFunc("/block", blockHandler)
	http.ListenAndServe("localhost:8999", nil)
}
