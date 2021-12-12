package controller

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"strconv"
	"time"

	"example.com/goweb/bookstore/dao"
	"example.com/goweb/bookstore/model"
	"example.com/goweb/bookstore/utils"
	"github.com/google/uuid"
)

// 添加购物车
func AddBook2Cart(w http.ResponseWriter, r *http.Request) {
	user := utils.GetUserFromSession(r)
	if user == nil {
		w.Write([]byte("请先登录！"))
		return
	}
	bookIdStr := r.PostFormValue("bookId")
	if bookIdStr == "" {
		w.Write([]byte("参数上送有误，缺少bookId"))
		return
	}
	bookId, err := strconv.Atoi(bookIdStr)
	if err != nil {
		fmt.Println("bookId上送有误，解析失败", err)
		w.Write([]byte("bookId上送有误"))
		return
	}
	book, _ := dao.BookDao.GetById(bookId)
	if book == nil {
		w.Write([]byte("参数上送错误，图书不存在"))
		return
	}
	cart, err := dao.CartDao.GetByUserId(user.Id)
	if err != nil {
		fmt.Println("获取购物车信息失败", err)
		w.Write([]byte("添加失败"))
		return
	}
	if cart == nil {
		// 用户还没有购物车
		cart = &model.Cart{
			UserId: user.Id,
		}
		// tx, err := utils.Db.Begin()
		// 同一个事务需要通过tx.Exec()执行SQL。所以这里的事务不会生效
		// tx.Exec("")
		// 插入后会给cart添加id
		err = dao.CartDao.Insert(cart)
		if err == nil {
			err = insertCartItem(cart.Id, bookId, 1)
		}
	} else {
		// 用户的购物车已经存在
		var cartItems []*model.CartItem
		cartItems, err = dao.CartDao.GetCartItemByCartId(cart.Id)
		if err != nil {
			fmt.Println("查询购物项异常", err)
			utils.ResponseText(w, "系统异常")
			return
		}
		var cartItem *model.CartItem
		for _, v := range cartItems {
			if v.BookId == bookId {
				cartItem = v
				break
			}
		}
		// 原来购物车已经存在这一项了，直接更新数量
		if cartItem != nil {
			err = dao.CartDao.UpdateCartItemNum(cartItem.Id, cartItem.Nums+1)
		} else {
			err = insertCartItem(cart.Id, bookId, 1)
		}

	}
	if err != nil {
		fmt.Println("添加购物项异常.err=", err)
		utils.ResponseText(w, "系统异常")
		return
	}

	w.Write([]byte(fmt.Sprintf("添加[%v]到购物车成功", book.Title)))
}

// 购物车信息展示
func GetCartInfo(w http.ResponseWriter, r *http.Request) {
	log.Default().Println("进入到购物车信息展示")
	user := utils.GetUserFromSession(r)
	if user == nil {
		fmt.Println("用户未登录，跳转到登录页面")
		http.Redirect(w, r, "pages/user/login.html", http.StatusTemporaryRedirect)
		return
	}
	cart, err := dao.CartDao.GetByUserId(user.Id)
	if err == nil && cart != nil {
		var cartItems []*model.CartItem
		cartItems, err = dao.CartDao.GetCartItemByCartId(cart.Id)
		if err == nil {
			for _, item := range cartItems {
				book, _ := dao.BookDao.GetById(item.BookId)
				item.Book = *book
			}
			cart.Items = cartItems
		}
	}
	if err != nil {
		fmt.Println("获取购物车信息错误：", err)
		utils.ResponseText(w, "获取购物车信息错误")
	} else {
		var data map[string]interface{} = make(map[string]interface{})
		data["User"] = user
		data["Cart"] = cart
		utils.Response(w, "cart/cart", data)
	}
}

// 清空购物车
func DeleteCart(w http.ResponseWriter, r *http.Request) {
	user := utils.GetUserFromSession(r)
	if user == nil {
		http.Redirect(w, r, "pages/user/login.html", http.StatusTemporaryRedirect)
		return
	}
	cart, err := dao.CartDao.GetByUserId(user.Id)
	if err == nil {
		if cart == nil {
			utils.ResponseText(w, "购物车ID上送错误")
			return
		} else {
			cartId, _ := strconv.Atoi(r.FormValue("id"))
			if cartId != cart.Id {
				utils.ResponseText(w, "购物车ID上送错误")
				return
			}
			err = dao.CartDao.DeleteCart(cartId)
		}
	}
	if err == nil {
		GetCartInfo(w, r)
		return
	} else {
		fmt.Println("清空购物车失败，err=", err)
		utils.ResponseText(w, "清空购物车失败")
	}
}

func DeleteCartItem(w http.ResponseWriter, r *http.Request) {
	user := utils.GetUserFromSession(r)
	if user == nil {
		log.Default().Println("用户未登录")
		http.Redirect(w, r, "pages/user/login.html", http.StatusTemporaryRedirect)
		return
	}
	cartItemId, _ := strconv.Atoi(r.FormValue("id"))
	cartItem, err := dao.CartDao.GetCartItemById(cartItemId)
	if err == nil {
		if cartItem == nil {
			utils.ResponseText(w, "参数上送错误，当前用户的购物车没有该项目")
			return
		}
		var cart *model.Cart
		cart, err = dao.CartDao.GetByUserId(user.Id)
		if err == nil {
			if cart == nil || cart.Id != cartItem.CartId {
				log.Default().Printf("准备删除的购物项id=%d不是属于当前用户的购物项,userId=%d \n", cartItemId, user.Id)
				utils.ResponseText(w, "参数上送错误，当前用户的购物车没有该项目")
				return
			}
			err = dao.CartDao.DeleteCartItem(cartItemId)
		}

	}
	if err != nil {
		log.Default().Println("系统异常，删除失败", err)
		utils.ResponseText(w, "系统异常")
	} else {
		GetCartInfo(w, r)
	}
}

func UpdateCartItem(w http.ResponseWriter, r *http.Request) {
	user := utils.GetUserFromSession(r)
	if user == nil {
		log.Default().Println("用户未登录")
		http.Redirect(w, r, "pages/user/login.html", http.StatusTemporaryRedirect)
		return
	}
	cartItemId, _ := strconv.Atoi(r.PostFormValue("cartItemId"))
	bookCount, _ := strconv.Atoi(r.PostFormValue("bookCount"))
	err := dao.CartDao.UpdateCartItemNum(cartItemId, bookCount)
	if err == nil {
		var cart *model.Cart
		cart, err = dao.CartDao.GetByUserId(user.Id)
		if err == nil && cart != nil {
			var cartItems []*model.CartItem
			var updatedItem *model.CartItem
			cartItems, err = dao.CartDao.GetCartItemByCartId(cart.Id)
			if err == nil {
				for _, item := range cartItems {
					book, _ := dao.BookDao.GetById(item.BookId)
					item.Book = *book
					if item.Id == cartItemId {
						updatedItem = item
					}
				}
				cart.Items = cartItems
			}
			data := make(map[string]interface{})
			data["Amount"] = fmt.Sprintf("%.2f", updatedItem.Amount())
			data["TotalAmount"] = fmt.Sprintf("%.2f", cart.TotalAmount())
			data["TotalCount"] = cart.TotalCount()
			var jsonByte []byte
			jsonByte, err = json.Marshal(data)
			if err == nil {
				w.Write(jsonByte)
				return
			}
		}
	}

	log.Default().Println("系统异常", err)
	utils.ResponseText(w, "系统异常")
}

// 购物车结算 /checkout
func Checkout(w http.ResponseWriter, r *http.Request) {
	user := utils.GetUserFromSession(r)
	if user == nil {
		fmt.Println("用户未登录，跳转到登录页面")
		http.Redirect(w, r, "pages/user/login.html", http.StatusTemporaryRedirect)
		return
	}
	cart, err := dao.CartDao.GetByUserId(user.Id)
	var orderId string
	if err == nil {
		if cart == nil {
			log.Default().Println("购物车不存在,userId=", user.Id)
			utils.ResponseText(w, "购物车为空")
			return
		}
		var cartItems []*model.CartItem
		cartItems, err = dao.CartDao.GetCartItemByCartId(cart.Id)
		if err == nil {
			if cartItems == nil {
				utils.ResponseText(w, "购物车为空")
				return
			}
			// 通过UUID生成订单号
			orderId = uuid.NewString()
			var orderItems []*model.OrderItem
			for _, item := range cartItems {
				book, _ := dao.BookDao.GetById(item.BookId)
				item.Book = *book
				orderItem := &model.OrderItem{
					Nums:      item.Nums,
					Amount:    item.Amount(),
					OrderId:   orderId,
					Title:     item.Book.Title,
					Author:    item.Book.Author,
					Price:     item.Book.Price,
					ImagePath: item.Book.ImagePath,
				}
				orderItems = append(orderItems, orderItem)
			}
			cart.Items = cartItems

			order := &model.Order{
				Id:          orderId,
				CreateTime:  time.Now(),
				UserId:      user.Id,
				TotalNum:    cart.TotalCount(),
				TotalAmount: cart.TotalAmount(),
				State:       model.ORDER_STATE_PREPARE,
			}
			err = dao.OrderDao.InsertOrder(order)
			if err == nil {
				for _, orderItem := range orderItems {
					e := dao.OrderDao.InsertOrderItem(orderItem)
					// 这些操作其实都应该放在一个事务中。这里只是简单处理，不考虑事务-

					// 下单成功后还可以同步更新库存
					if e != nil {
						log.Default().Println("插入订单项失败", e)
					}
				}
			}
		}
	}
	if err != nil {
		log.Default().Println("系统异常", err)
		utils.ResponseText(w, "系统异常")
		return
	}
	// 到订单结果页
	data := make(map[string]interface{})
	data["OrderId"] = orderId
	data["Username"] = user.Username
	utils.Response(w, "cart/checkout", data)
}

func insertCartItem(cartId, bookId, nums int) error {
	cartItem := &model.CartItem{
		CartId: cartId,
		BookId: bookId,
		Nums:   nums,
	}
	err := dao.CartDao.InsertItem(cartItem)
	return err
}
