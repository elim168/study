package controller

import (
	"fmt"
	"net/http"
	"strconv"

	"example.com/goweb/bookstore/dao"
	"example.com/goweb/bookstore/model"
	"example.com/goweb/bookstore/utils"
)

func GetBooks(w http.ResponseWriter, r *http.Request) {
	books, err := dao.BookDao.GetAllBooks()
	if err != nil {
		w.Write([]byte(err.Error()))
		return
	}
	utils.Response(w, "manager/book_manager", books)
}

func GetPageBooks(w http.ResponseWriter, r *http.Request) {
	pageNoStr := r.FormValue("pageNo")
	var pageNo int
	if pageNoStr == "" {
		pageNo = 1
	} else {
		pageNo, _ = strconv.Atoi(pageNoStr)
	}
	page, err := dao.BookDao.GetPageBooks(pageNo, 5)
	if err != nil {
		w.Write([]byte(err.Error()))
		return
	}
	utils.Response(w, "manager/book_manager", page)
}

// 图书添加/修改页面
func ToUpdateBookPage(w http.ResponseWriter, r *http.Request) {
	idStr := r.FormValue("bookId")
	var data interface{}
	if idStr != "" {
		id, _ := strconv.Atoi(idStr)
		book, err := dao.BookDao.GetById(id)
		if err == nil {
			data = book
		} else {
			fmt.Println("查询图书失败：", err)
		}
	}
	utils.Response(w, "manager/book_edit", data)
}

// 图书添加/修改提交
func UpdateOrAddBook(w http.ResponseWriter, r *http.Request) {
	idStr := r.PostFormValue("bookId")
	title := r.PostFormValue("title")
	priceStr := r.PostFormValue("price")
	author := r.PostFormValue("author")
	salesStr := r.PostFormValue("sales")
	stockStr := r.PostFormValue("stock")
	imagePath := "static/img/default.jpg"

	price, err := strconv.ParseFloat(priceStr, 64)
	if err != nil {
		w.Write([]byte("价格输入错误：" + err.Error()))
		return
	}
	sales, err := strconv.Atoi(salesStr)
	if err != nil {
		w.Write([]byte("销量输入错误：" + err.Error()))
		return
	}
	stock, err := strconv.Atoi(stockStr)
	if err != nil {
		w.Write([]byte("销量输入错误：" + err.Error()))
		return
	}

	book := &model.Book{
		Title:     title,
		Author:    author,
		Price:     price,
		Sales:     sales,
		Stock:     stock,
		ImagePath: imagePath,
	}
	if idStr != "" {
		// 修改
		id, _ := strconv.Atoi(idStr)
		book.Id = id
		err := dao.BookDao.UpdateBook(book)
		if err != nil {
			w.Write([]byte("修改图书失败：" + err.Error()))
			return
		}
	} else {
		// 新增
		err := dao.BookDao.AddBook(book)
		if err != nil {
			w.Write([]byte("图书添加失败：" + err.Error()))
			return
		}
	}
	// 添加或修改后回到列表页面，此时直接返回列表页面的视图
	GetPageBooks(w, r)
}

// 删除图书
func DeleteBook(w http.ResponseWriter, r *http.Request) {
	idStr := r.FormValue("bookId")
	id, _ := strconv.Atoi(idStr)
	err := dao.BookDao.DeleteById(id)
	if err != nil {
		w.Write([]byte("删除失败：" + err.Error()))
		return
	}
	GetPageBooks(w, r)
}
