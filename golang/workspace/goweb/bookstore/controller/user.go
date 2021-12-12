package controller

import (
	"log"
	"net/http"

	"example.com/goweb/bookstore/dao"
	"example.com/goweb/bookstore/model"
	"example.com/goweb/bookstore/utils"
)

// 注册
func Regist(w http.ResponseWriter, r *http.Request) {
	username := r.PostFormValue("username")
	password := r.PostFormValue("password")
	email := r.PostFormValue("email")
	user, err := dao.UserDao.GetUserByUsername(username)
	if err != nil {
		log.Default().Println("查询用户信息失败：", err)
		utils.Response(w, "user/regist", "系统错误")
		return
	}
	if user != nil {
		log.Default().Println("用户名已存在：", username)
		utils.Response(w, "user/regist", "用户名已存在")
		return
	}
	user = &model.User{
		Username: username,
		Password: password,
		Email:    email,
	}
	err = dao.UserDao.InsertUser(user)
	if err != nil {
		log.Default().Println("注册用户失败：", err)
		utils.Response(w, "user/regist", "系统错误")
		return
	}
	utils.Response(w, "user/regist_success", nil)
}

// 注册时校验用户名是否存在
func CheckUserName(w http.ResponseWriter, r *http.Request) {
	username := r.PostFormValue("username")
	user, err := dao.UserDao.GetUserByUsername(username)
	if err != nil {
		log.Default().Println("查询用户失败：", err)
		utils.ResponseText(w, "系统异常")
		return
	}
	if user != nil {
		utils.ResponseText(w, "用户名已存在")
	} else {
		utils.ResponseText(w, "<font style='color: green'>用户名可以使用</font>")
	}
}

// 登录
func Login(w http.ResponseWriter, r *http.Request) {

	username := r.PostFormValue("username")
	password := r.PostFormValue("password")
	user, err := dao.UserDao.GetUserByUsername(username)
	if err != nil {
		log.Default().Println("查询用户信息失败：", err)
		utils.Response(w, "user/login", "系统错误")
		return
	}
	if user == nil || user.Password != password {
		log.Default().Printf("用户名%v或密码%v错误\n", user == nil, user != nil && user.Password != password)
		utils.Response(w, "user/login", "用户名或密码错误")
		return
	}
	log.Default().Println("登录成功：", username)

	err = utils.SaveUserToSession(w, r, user)
	if err != nil {
		log.Default().Println("保存会话信息错误：", err)
		utils.Response(w, "user/login", "系统错误")
	}
	utils.Response(w, "user/login_success", user)

}

// 登录
func Logout(w http.ResponseWriter, r *http.Request) {
	utils.DeleteSession(w, r)
	http.Redirect(w, r, "/index", http.StatusTemporaryRedirect)
}
