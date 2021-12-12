package controller

import (
	"net/http"
	"strconv"
	"text/template"

	"example.com/goweb/bookstore/dao"
	"example.com/goweb/bookstore/model"
	"example.com/goweb/bookstore/utils"
)

// 首页的处理器
func IndexHandler(w http.ResponseWriter, r *http.Request) {
	t := template.Must(template.ParseFiles("views/index.html"))

	pageNoStr := r.FormValue("pageNo")
	var pageNo int
	if pageNoStr == "" {
		pageNo = 1
	} else {
		pageNo, _ = strconv.Atoi(pageNoStr)
	}
	pageSize := 4
	page, err := dao.BookDao.GetPageBooks(pageNo, pageSize)
	if err != nil {
		w.Write([]byte(err.Error()))
		return
	}
	page.LoginUser = utils.GetUserFromSession(r)
	indexPage := &model.IndexPage{
		Page: *page,
	}
	t.Execute(w, indexPage)
}

func GetPageBooksByPriceHandler(w http.ResponseWriter, r *http.Request) {
	var (
		minPrice float64
		maxPrice float64
	)
	minPriceStr := r.FormValue("min")
	maxPriceStr := r.FormValue("max")
	if minPriceStr != "" {
		minPrice, _ = strconv.ParseFloat(minPriceStr, 64)
	}
	if maxPriceStr != "" {
		maxPrice, _ = strconv.ParseFloat(maxPriceStr, 64)
	}

	pageNoStr := r.FormValue("pageNo")
	var pageNo int
	if pageNoStr == "" {
		pageNo = 1
	} else {
		pageNo, _ = strconv.Atoi(pageNoStr)
	}
	pageSize := 4
	page, err := dao.BookDao.GetPageBooksByPrice(pageNo, pageSize, minPrice, maxPrice)
	if err != nil {
		w.Write([]byte(err.Error()))
		return
	}
	page.LoginUser = utils.GetUserFromSession(r)
	indexPage := &model.IndexPage{
		Page:     *page,
		MinPrice: minPrice,
		MaxPrice: maxPrice,
	}

	t := template.Must(template.ParseFiles("views/index.html"))
	t.Execute(w, indexPage)

}
