package model

import "time"

const (
	ORDER_STATE_PREPARE = iota
	ORDER_STATE_SENDING
	ORDER_STATE_FINISH
)

// 订单信息
type Order struct {
	Id          string
	CreateTime  time.Time
	TotalNum    int
	TotalAmount float64
	State       int
	UserId      int
	Items       []*OrderItem
}

type OrderItem struct {
	Id        int
	Nums      int
	Amount    float64
	Title     string
	Author    string
	Price     float64
	ImagePath string
	OrderId   string
}

func (order *Order) CreateTimeStr() string {
	str := order.CreateTime.Format("2006-01-02 15:04:05")
	return str
}
