package dao

import (
	"fmt"

	"example.com/elim/basic/database/sample1/model"
	"example.com/elim/basic/database/sample1/util"
)

type UserDao struct {
}

// AddUser1 通过非预编译执行SQL
func (this *UserDao) AddUser1(user model.User) error {
	sql := "insert into t_user(username, password, age) values(?,?,?)"
	result, err := util.Db.Exec(sql, user.Username, user.Password, user.Age)
	if err != nil {
		fmt.Println("AddUser1出错：", err)
		return err
	}
	rowCount, err := result.RowsAffected()
	fmt.Println("插入成功的行数：", rowCount, err)
	return nil
}

// AddUser2 通过预编译执行SQL
func (this *UserDao) AddUser2(user model.User) error {
	sql := "insert into t_user(username, password, age) values(?,?,?)"
	stmt, err := util.Db.Prepare(sql)
	if err != nil {
		fmt.Println("AddUser2预编译出错：", err)
		return err
	}
	result, err := stmt.Exec(user.Username, user.Password, user.Age)
	if err != nil {
		fmt.Println("AddUser2插入数据异常：", err)
		return err
	}
	rowCount, err := result.RowsAffected()
	fmt.Println("AddUser2插入成功的行数：", rowCount, err)
	return nil
}

// 查询一行数据。使用QueryRow
func (this *UserDao) GetById(id int) (*model.User, error) {
	sql := "select id, username, password from t_user where id = ?"
	// row := util.Db.QueryRow(sql, id)
	stmt, err := util.Db.Prepare(sql)
	if err != nil {
		return nil, err
	}
	row := stmt.QueryRow(id)
	var user model.User
	err = row.Scan(&user.Id, &user.Username, &user.Password)
	return &user, err
}

// 查询所有数据。使用Query()可以查询多行数据
func (this *UserDao) GetAll() ([]*model.User, error) {
	sql := "select id, username, password from t_user"
	// 如果有需要第二个参数也是可以带参数的
	// rows, err := util.Db.Query(sql)
	stmt, err := util.Db.Prepare(sql)
	if err != nil {
		return nil, err
	}
	rows, err := stmt.Query()
	if err != nil {
		return nil, err
	}
	var users []*model.User
	for rows.Next() {
		var user model.User
		err := rows.Scan(&user.Id, &user.Username, &user.Password)
		if err != nil {
			return nil, err
		}
		users = append(users, &user)
	}

	return users, nil
}
