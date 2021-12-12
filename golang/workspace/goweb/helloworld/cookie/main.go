package main

import (
	"fmt"
	"net/http"
)

func SetCookie(w http.ResponseWriter, r *http.Request) {
	cookie := http.Cookie{
		Name:  "key1",
		Value: "value1",
	}
	cookie2 := http.Cookie{
		Name:   "key2",
		Value:  "value2",
		MaxAge: 30, // 存活时间，单位是秒
	}
	http.SetCookie(w, &cookie)
	http.SetCookie(w, &cookie2)
	w.Write([]byte("Cookie设置成功"))
}

func GetCookie(w http.ResponseWriter, r *http.Request) {
	// 一次取所有的Cookie，返回的是Cookie对象的指针
	cookies := r.Cookies()
	fmt.Fprintf(w, "得到的所有的Cookie是：%v/n", cookies)

	// 也可以根据名字取具体Cookie的值
	cookie1, err := r.Cookie("key1")
	if err == http.ErrNoCookie {
		fmt.Fprintf(w, "名称为key1的Cookie不存在,错误信息为：%v", err)
	} else {
		// 这里%v和%+v没有区别
		fmt.Fprintf(w, "得到的名称为key1的Cookie的值为%v,整个Cookie对象的值为：%v,%+v", cookie1.Value, cookie1, cookie1)
	}

}

func main() {
	http.HandleFunc("/setCookie", SetCookie)
	http.HandleFunc("/getCookie", GetCookie)
	http.ListenAndServe(":8999", nil)
}
