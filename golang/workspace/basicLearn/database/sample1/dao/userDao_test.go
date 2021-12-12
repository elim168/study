package dao

import (
	"fmt"
	"testing"

	"example.com/elim/basic/database/sample1/model"
)

func TestAddUser1(t *testing.T) {
	userDao := &UserDao{}
	user := model.User{
		Username: "zhangsan",
		Password: "123456",
		Age:      18,
	}
	err := userDao.AddUser1(user)
	if err != nil {
		panic(err)
	}
}

// 测试AddUser2
func TestAddUser2(t *testing.T) {
	userDao := &UserDao{}
	user := model.User{
		Username: "lisi",
		Password: "123456",
		Age:      19,
	}
	err := userDao.AddUser2(user)
	if err != nil {
		panic(err)
	}
}

func TestGetById(t *testing.T) {
	userDao := &UserDao{}
	user, err := userDao.GetById(10)
	if err != nil {
		fmt.Println(err)
		t.Fatal(err)
	}
	fmt.Println("查询回来的用户是：", user)
}

func TestGetAll(t *testing.T) {
	userDao := &UserDao{}
	users, err := userDao.GetAll()
	if err != nil {
		t.Fatal(err)
	}
	for i, user := range users {
		fmt.Printf("%d------------%v \n", i+1, user)
	}
}
