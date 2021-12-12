package model

// 购物车
type Cart struct {
	Id     int
	UserId int
	Items  []*CartItem
}

// 购物车中的一项
type CartItem struct {
	Id     int
	BookId int
	Nums   int
	CartId int
	Book   Book
}

// 获取购物项的金额
func (cartItem *CartItem) Amount() float64 {
	return cartItem.Book.Price * float64(cartItem.Nums)
}

func (cart *Cart) TotalCount() int {
	items := cart.Items
	if items == nil {
		return 0
	}
	sum := 0
	for _, item := range items {
		sum += item.Nums
	}
	return sum
}

func (cart *Cart) TotalAmount() float64 {
	items := cart.Items
	if items == nil {
		return 0
	}
	var sum float64 = 0
	for _, item := range items {
		sum += item.Amount()
	}
	return sum
}
