package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"html/template"
	"errors"
	"regexp"
	"os"
)


type Page struct {

	Title string
	Body []byte

}


func (p *Page) save() error {

	filename := "data/" + p.Title + ".txt"
	return ioutil.WriteFile(filename, p.Body, 0600)
}

func loadPage(title string) (*Page, error) {
	filename := "data/" + title + ".txt"
	body, err := ioutil.ReadFile(filename)
	if err != nil {
		return nil, err
	}
	return &Page{Title: title, Body: body}, nil
}

func mainTest() {
	p1 := &Page{Title: "TestPage", Body: []byte("This is a sample Page.")}
	p1.save()
	p2, _ := loadPage("TestPage")
	fmt.Println(string(p2.Body))
}

// 处理器方法，用来处理请求
func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hi there, I love %s!", r.URL.Path[1:])
}

// 测试基本的http操作的
func main2() {
	// 指定路径的处理器函数。/表示处理所有的请求
	http.HandleFunc("/", handler)
	// 监听所有网卡的8080端口。执行了ListenAndServe后会阻塞。中断后就交给log.Fatal处理。
	log.Fatal(http.ListenAndServe(":8080", nil))
}

var validPath = regexp.MustCompile("^/(edit|save|view)/([a-zA-Z0-9]+)$")

func getTitle(w http.ResponseWriter, r *http.Request) (string, error) {
	m := validPath.FindStringSubmatch(r.URL.Path)
	if m == nil {
		http.NotFound(w, r)
		return "", errors.New("invalid Page Title")
	}
	//fmt.Println("获取到的TItle为：", m)
	// 0为整个URL，之后的分组就按照定义的分组位置来。title对应的分组索引就为2
	return m[2], nil
}

// 缓存模板
var templates = template.Must(template.ParseFiles("tmpl/edit.html", "tmpl/view.html", "tmpl/index.html"))



// 响应模板
func renderTemplate(w http.ResponseWriter, tmpl string, p *Page) {
	//t, err := template.ParseFiles(tpl + ".html")
	// 模板路径放到了tmpl目录后，模板名称还是文件名
	err := templates.ExecuteTemplate(w, tmpl + ".html", p)
	
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	/*
	err = t.Execute(w, p)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
	*/
}

func makeHandler(fn func (http.ResponseWriter, *http.Request, string)) http.HandlerFunc {

	return func(w http.ResponseWriter, r *http.Request) {
		//title, err := getTitle(w, r)
		m := validPath.FindStringSubmatch(r.URL.Path)
		if m == nil {
			http.NotFound(w, r)
			return
		}
		title := m[2]
		fn(w, r, title)
	}

}

// 查看
func viewHandler(w http.ResponseWriter, r *http.Request, title string) {
	//title := r.URL.Path[len("/view/"):]
	/*
	title, err := getTitle(w, r)
	if err != nil {
		return
	}
	*/
	p, err := loadPage(title)
	if err != nil {
		// 访问的页面不存在时重定向到编辑页面，响应码为302
		http.Redirect(w, r, "/edit/" + title, http.StatusFound)
		return
	}
	renderTemplate(w, "view", p)
}

// 展示编辑表单
func editHandler(w http.ResponseWriter, r *http.Request, title string) {
	//title := r.URL.Path[len("/edit/"):]
	/*
	title, err := getTitle(w, r)
	if err != nil {
		return
	}
	*/
	p, err := loadPage(title)
	if err != nil {
		p = &Page{Title: title}
	}
	renderTemplate(w, "edit", p)
}

func saveHandler(w http.ResponseWriter, r *http.Request, title string) {
	//title := r.URL.Path[len("/save/"):]
	/*
	title, err := getTitle(w, r)
	if err != nil {
		return
	}
	*/
	// 取表单值
	body := r.FormValue("body")
	p := &Page{Title: title, Body: []byte(body)}
	err := p.save()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	// 保存成功后重定向到查看页面
	http.Redirect(w, r, "/view/" + title, http.StatusFound)
}

func homeHandler(w http.ResponseWriter, r *http.Request) {
	dir, err := os.ReadDir("data")
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
	var items = make([]string, len(dir))
	for i := 0; i < len(dir); i++ {
		name := dir[i].Name()
		items[i] = name[:len(name) - len(".txt")]
	}
	var datas = map[string]interface{}{"items": items}
	err = templates.ExecuteTemplate(w, "index.html", datas)
        if err != nil {
                http.Error(w, err.Error(), http.StatusInternalServerError)
                return
        }

}

func main() {
	http.HandleFunc("/view/", makeHandler(viewHandler))
	http.HandleFunc("/edit/", makeHandler(editHandler))
	http.HandleFunc("/save/", makeHandler(saveHandler))
	http.HandleFunc("/", homeHandler)
	log.Fatal(http.ListenAndServe(":8080", nil))
}
