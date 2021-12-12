package model

// 分页的对象信息
type Page struct {
	PageNo    int
	PageSize  int
	Total     int
	TotalPage int
	Data      []interface{}
	LoginUser *User
}

type IndexPage struct {
	Page
	MinPrice float64
	MaxPrice float64
}

func (page *Page) IsHasPrev() bool {
	return page.PageNo > 1
}

func (page *Page) IsHasNext() bool {
	return page.PageNo < page.TotalPage
}

func (page *Page) GetPrevPageNo() int {
	return page.PageNo - 1
}

func (page *Page) GetNextPageNo() int {
	return page.PageNo + 1
}
