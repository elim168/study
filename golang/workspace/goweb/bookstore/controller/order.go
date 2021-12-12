package controller

import (
	"fmt"
	"log"
	"net/http"

	"example.com/goweb/bookstore/dao"
	"example.com/goweb/bookstore/utils"
)

// 进入订单列表
func GetMyOrder(w http.ResponseWriter, r *http.Request) {
	user := utils.GetUserFromSession(r)
	if user == nil {
		fmt.Println("用户未登录，跳转到登录页面")
		http.Redirect(w, r, "pages/user/login.html", http.StatusTemporaryRedirect)
		return
	}
	orders, err := dao.OrderDao.GetOrdersByUserId(user.Id)
	if err != nil {
		log.Default().Println("读取订单信息异常", err)
		utils.ResponseText(w, "系统异常")
		return
	}
	data := make(map[string]interface{})
	data["Username"] = user.Username
	data["Orders"] = orders
	utils.Response(w, "order/order", data)
}

func GetOrderInfo(w http.ResponseWriter, r *http.Request) {
	user := utils.GetUserFromSession(r)
	if user == nil {
		fmt.Println("用户未登录，跳转到登录页面")
		http.Redirect(w, r, "pages/user/login.html", http.StatusTemporaryRedirect)
		return
	}
	orderId := r.FormValue("orderId")
	order, err := dao.OrderDao.GetOrderById(orderId)
	if err != nil {
		log.Default().Println("查询订单详细信息异常", err)
		utils.ResponseText(w, "系统异常")
		return
	}
	if order == nil || order.UserId != user.Id {
		log.Default().Printf("非法访问，订单不存在%v或不是本人的\n", order == nil)
		utils.ResponseText(w, "订单不存在")
		return
	}
	utils.Response(w, "order/order_info", order)
}

// 订单管理，获取订单
func GetOrders(w http.ResponseWriter, r *http.Request) {
	user := utils.GetUserFromSession(r)
	if user == nil {
		fmt.Println("用户未登录，跳转到登录页面")
		http.Redirect(w, r, "pages/user/login.html", http.StatusTemporaryRedirect)
		return
	}
	if user.Username != "admin" {
		utils.ResponseText(w, "对不起，您无权访问该页面")
		return
	}
	orders, err := dao.OrderDao.GetOrders()
	if err != nil {
		log.Default().Println("读取订单信息异常", err)
		utils.ResponseText(w, "系统异常")
		return
	}
	utils.Response(w, "order/order_manager", orders)
}

// 发货
func SendOrder(w http.ResponseWriter, r *http.Request) {
	user := utils.GetUserFromSession(r)
	if user == nil {
		fmt.Println("用户未登录，跳转到登录页面")
		http.Redirect(w, r, "pages/user/login.html", http.StatusTemporaryRedirect)
		return
	}
	if user.Username != "admin" {
		utils.ResponseText(w, "对不起，您无权访问该页面")
		return
	}
	orderId := r.FormValue("orderId")
	dao.OrderDao.SendOrder(orderId)
	http.Redirect(w, r, "getOrders", http.StatusTemporaryRedirect)
}

// 确认收货
func TakeOrder(w http.ResponseWriter, r *http.Request) {
	user := utils.GetUserFromSession(r)
	if user == nil {
		fmt.Println("用户未登录，跳转到登录页面")
		http.Redirect(w, r, "pages/user/login.html", http.StatusTemporaryRedirect)
		return
	}
	orderId := r.FormValue("orderId")
	err := dao.OrderDao.TakeOrder(orderId, user.Id)
	if err != nil {
		utils.ResponseText(w, "系统异常")
		return
	}
	http.Redirect(w, r, "getMyOrder", http.StatusTemporaryRedirect)
}
