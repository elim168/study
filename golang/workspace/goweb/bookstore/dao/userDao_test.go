package dao

import (
	"fmt"
	"testing"

	"example.com/goweb/bookstore/model"
)

func TeestInsertUser(t *testing.T) {
	user := &model.User{
		Username: "admin",
		Password: "123456",
		Email:    "admin@sina.com",
	}
	err := UserDao.InsertUser(user)
	if err != nil {
		t.Fatal(err)
	}
}

func TestGetUserByUsername(t *testing.T) {
	user, err := UserDao.GetUserByUsername("admin111")
	if err != nil {
		t.Fatal(err)
	}
	fmt.Println("查询成功，user=", user)
}
