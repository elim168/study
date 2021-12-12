package dao

import (
	"database/sql"
	"fmt"

	"example.com/goweb/bookstore/model"
	"example.com/goweb/bookstore/utils"
)

var CartDao *cartDao

type cartDao struct {
}

func init() {
	CartDao = &cartDao{}
}

func (cartDao *cartDao) Insert(cart *model.Cart) error {
	sqlStr := "insert into t_cart(user_id) values(?)"
	results, err := utils.Db.Exec(sqlStr, cart.UserId)
	if err == nil {
		id, err := results.LastInsertId()
		if err != nil {
			return err
		}
		cart.Id = int(id)
	}
	return err
}

func (cartDao *cartDao) InsertItem(cartItem *model.CartItem) error {
	sqlStr := "insert into t_cart_item(cart_id, book_id, nums) values(?,?,?)"
	results, err := utils.Db.Exec(sqlStr, cartItem.CartId, cartItem.BookId, cartItem.Nums)
	if err == nil {
		id, err := results.LastInsertId()
		if err != nil {
			return err
		}
		cartItem.Id = int(id)
	}
	return err
}

func (cartDao *cartDao) GetByUserId(userId int) (*model.Cart, error) {
	sqlStr := "select id, user_Id from t_cart where user_id = ?"
	var cart model.Cart
	row := utils.Db.QueryRow(sqlStr, userId)
	err := row.Scan(&cart.Id, &cart.UserId)
	if err == sql.ErrNoRows {
		return nil, nil
	} else if err != nil {
		return nil, err
	}
	return &cart, err
}

func (cartDao *cartDao) GetCartItemByCartId(cartId int) ([]*model.CartItem, error) {
	sqlStr := "select id, cart_id,book_id,nums from t_cart_item where cart_id = ?"
	rows, err := utils.Db.Query(sqlStr, cartId)
	if err == sql.ErrNoRows {
		return nil, nil
	} else if err != nil {
		return nil, err
	}
	var items []*model.CartItem
	for rows.Next() {
		var item model.CartItem
		err = rows.Scan(&item.Id, &item.CartId, &item.BookId, &item.Nums)
		if err != nil {
			return nil, err
		}
		items = append(items, &item)
	}
	return items, nil
}

func (cartDao *cartDao) GetCartItemById(cartItemId int) (*model.CartItem, error) {
	sqlStr := "select id, cart_id,book_id,nums from t_cart_item where id = ?"
	var cartItem model.CartItem
	row := utils.Db.QueryRow(sqlStr, cartItemId)
	err := row.Scan(&cartItem.Id, &cartItem.CartId, &cartItem.BookId, &cartItem.Nums)
	if err == sql.ErrNoRows {
		return nil, nil
	}
	return &cartItem, err
}

func (cartDao *cartDao) UpdateCartItemNum(id, nums int) error {
	sqlStr := "update t_cart_item set nums=? where id=?"
	_, err := utils.Db.Exec(sqlStr, nums, id)
	return err
}

func (cartDao *cartDao) DeleteCart(id int) error {
	tx, err := utils.Db.Begin()
	if err != nil {
		return err
	}
	_, err = tx.Exec("delete from t_cart_item where cart_id=?", id)
	if err == nil {
		_, err = tx.Exec("delete from t_cart where id=?", id)
	}
	if err == nil {
		err = tx.Commit()
	} else {
		e := tx.Rollback()
		fmt.Println("删除失败,err=", err, "回滚结果err=", e)
	}
	return err
}

func (cartDao *cartDao) DeleteCartItem(cartItemId int) error {
	_, err := utils.Db.Exec("delete from t_cart_item where id=?", cartItemId)
	return err
}
