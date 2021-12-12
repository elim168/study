package dao

import (
	"database/sql"
	"fmt"
	"log"

	"example.com/goweb/bookstore/model"
	"example.com/goweb/bookstore/utils"
)

var OrderDao *orderDao

type orderDao struct {
}

func init() {
	OrderDao = &orderDao{}
}

// 插入订单信息
func (orderDao *orderDao) InsertOrder(order *model.Order) error {
	sqlStr := "insert into t_order(id,create_time,total_num,total_amount,state,user_id) values(?,?,?,?,?,?)"
	_, err := utils.Db.Exec(sqlStr, order.Id, order.CreateTime, order.TotalNum, order.TotalAmount, order.State, order.UserId)
	return err
}

func (orderDao *orderDao) GetOrdersByUserId(userId int) ([]*model.Order, error) {
	sqlStr := "select id, create_time, total_num, total_amount,state,user_id from t_order where user_id=? order by create_time desc"
	rows, err := utils.Db.Query(sqlStr, userId)
	if err == sql.ErrNoRows {
		return nil, nil
	} else if err != nil {
		return nil, err
	}
	var orders []*model.Order
	for rows.Next() {
		var order model.Order
		err = rows.Scan(&order.Id, &order.CreateTime, &order.TotalNum, &order.TotalAmount, &order.State, &order.UserId)
		if err != nil {
			fmt.Println("订单信息设置错误", err)
		} else {
			orders = append(orders, &order)
		}

	}
	return orders, nil
}

// 订单管理时获取所有的订单
func (orderDao *orderDao) GetOrders() ([]*model.Order, error) {
	sqlStr := "select id, create_time, total_num, total_amount,state,user_id from t_order order by create_time desc"
	rows, err := utils.Db.Query(sqlStr)
	if err == sql.ErrNoRows {
		return nil, nil
	} else if err != nil {
		return nil, err
	}
	var orders []*model.Order
	for rows.Next() {
		var order model.Order
		err = rows.Scan(&order.Id, &order.CreateTime, &order.TotalNum, &order.TotalAmount, &order.State, &order.UserId)
		if err != nil {
			fmt.Println("订单信息设置错误", err)
		} else {
			orders = append(orders, &order)
		}

	}
	return orders, nil
}

func (orderDao *orderDao) GetOrderById(id string) (*model.Order, error) {
	sqlStr := "select id, create_time, total_num, total_amount,state,user_id from t_order where id=? "
	row := utils.Db.QueryRow(sqlStr, id)
	var order model.Order
	err := row.Scan(&order.Id, &order.CreateTime, &order.TotalNum, &order.TotalAmount, &order.State, &order.UserId)
	if err == sql.ErrNoRows {
		return nil, nil
	} else if err != nil {
		return nil, err
	}
	items, err := orderDao.getItemsByOrderId(id)
	if err != nil {
		log.Default().Println("获取订单详细信息出错", err)
	} else {
		order.Items = items
	}
	return &order, nil
}

func (orderDao *orderDao) SendOrder(id string) error {
	sqlStr := "update t_order set state=? where id=? and state=?"
	_, err := utils.Db.Exec(sqlStr, model.ORDER_STATE_SENDING, id, model.ORDER_STATE_PREPARE)
	return err
}

func (orderDao *orderDao) TakeOrder(id string, userId int) error {
	sqlStr := "update t_order set state=? where id=? and state=? and user_id=?"
	_, err := utils.Db.Exec(sqlStr, model.ORDER_STATE_FINISH, id, model.ORDER_STATE_SENDING, userId)
	return err
}

/*
nums INT NOT NULL,
amount DOUBLE(11,2) NOT NULL,
title VARCHAR(100) NOT NULL,
author VARCHAR(100) NOT NULL,
price DOUBLE(11,2) NOT NULL,
img_path VARCHAR(100) NOT NULL,
order_id VARCHAR(100) NOT NULL
*/
func (orderDao *orderDao) InsertOrderItem(orderItem *model.OrderItem) error {
	sqlStr := "insert into t_order_item(nums,amount,title,author,price,img_path,order_id) values(?,?,?,?,?,?,?)"
	_, err := utils.Db.Exec(sqlStr, orderItem.Nums, orderItem.Amount, orderItem.Title, orderItem.Author, orderItem.Price, orderItem.ImagePath, orderItem.OrderId)
	return err
}

func (orderDao *orderDao) getItemsByOrderId(orderId string) ([]*model.OrderItem, error) {
	sqlStr := "select id, nums,amount,title,author,price,img_path,order_id from t_order_item where order_id=?"
	rows, err := utils.Db.Query(sqlStr, orderId)
	if err == sql.ErrNoRows {
		return nil, nil
	} else if err != nil {
		return nil, err
	}
	var orderItems []*model.OrderItem
	for rows.Next() {
		var orderItem model.OrderItem
		err = rows.Scan(&orderItem.Id, &orderItem.Nums, &orderItem.Amount, &orderItem.Title, &orderItem.Author, &orderItem.Price, &orderItem.ImagePath, &orderItem.OrderId)
		if err != nil {
			fmt.Println("订单项信息设置错误", err)
		} else {
			orderItems = append(orderItems, &orderItem)
		}

	}
	return orderItems, nil
}
