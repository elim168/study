package main

import (
	"net/http"

	"example.com/goweb/bookstore/controller"
	"example.com/goweb/bookstore/utils"
)

func main() {
	// 处理静态资源。
	// 当一个请求的路径是/static/css/abc.css时，下面的StripPrefix去掉/static/前缀后剩下的内容是css/abc.css，其会在views/static路径下寻找css/abc.css文件。
	staticHandler := http.StripPrefix("/static/", http.FileServer(http.Dir("views/static")))
	// 指定/static/开始的请求由staticHandler处理。
	http.Handle("/static/", staticHandler)

	// 处理HTML文件。路径映射的最后一个“/”一定要包含。
	http.Handle("/pages/", http.StripPrefix("/pages/", http.FileServer(http.Dir("views/pages"))))

	// 用户注册
	http.HandleFunc("/regist", controller.Regist)
	// 注册时校验用户名是否可以使用
	http.HandleFunc("/checkUserName", controller.CheckUserName)
	// 用户登录
	http.HandleFunc("/login", controller.Login)
	// 用户登出
	http.HandleFunc("/logout", controller.Logout)

	// ******************图书管理
	// 图书列表页面
	http.HandleFunc("/getPageBooks", controller.GetPageBooks)
	// 图书添加/修改页面
	http.HandleFunc("/toUpdateBookPage", controller.ToUpdateBookPage)
	// 图书添加/修改提交
	http.HandleFunc("/updateOraddBook", controller.UpdateOrAddBook)
	// 删除图书
	http.HandleFunc("/deleteBook", controller.DeleteBook)

	// 首页相关
	// http.HandleFunc("/index", controller.IndexHandler)
	http.HandleFunc("/index", controller.GetPageBooksByPriceHandler)
	http.HandleFunc("/getPageBooksByPrice", controller.GetPageBooksByPriceHandler)

	// 购物车相关
	http.HandleFunc("/addBook2Cart", controller.AddBook2Cart)
	http.HandleFunc("/getCartInfo", controller.GetCartInfo)
	http.HandleFunc("/deleteCart", controller.DeleteCart)
	http.HandleFunc("/deleteCartItem", controller.DeleteCartItem)
	http.HandleFunc("/updateCartItem", controller.UpdateCartItem)
	http.HandleFunc("/checkout", controller.Checkout)

	// 订单相关
	http.HandleFunc("/getMyOrder", controller.GetMyOrder)
	http.HandleFunc("/getOrderInfo", controller.GetOrderInfo)
	http.HandleFunc("/getOrders", controller.GetOrders)
	http.HandleFunc("/sendOrder", controller.SendOrder)
	http.HandleFunc("/takeOrder", controller.TakeOrder)

	// 清理资源
	defer utils.SessionStore.Close()

	http.ListenAndServe("localhost:8999", nil)
}
