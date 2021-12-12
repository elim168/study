package model

type Book struct {
	Id int
	// 书名
	Title string
	// 价格
	Price float64
	// 作者
	Author string
	// 销量
	Sales int
	// 库存
	Stock int
	// 图片地址
	ImagePath string
}
