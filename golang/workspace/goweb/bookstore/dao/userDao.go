package dao

import (
	"database/sql"
	"fmt"

	"example.com/goweb/bookstore/model"
	"example.com/goweb/bookstore/utils"
)

var UserDao *userDao

type userDao struct {
}

func init() {
	UserDao = &userDao{}
}

func (userDao *userDao) GetUserByUsername(username string) (*model.User, error) {
	sqlStr := "select id, username, password, email from t_user where username=?"
	row := utils.Db.QueryRow(sqlStr, username)
	err := row.Err()
	if err != nil {
		return nil, err
	}

	var user model.User
	err = row.Scan(&user.Id, &user.Username, &user.Password, &user.Email)
	if err != nil {
		if err == sql.ErrNoRows {
			// 没有数据时直接返回空
			return nil, nil
		}
		fmt.Println("查询用户失败", err)
		return nil, err
	}
	return &user, nil
}

func (userDao *userDao) InsertUser(user *model.User) (err error) {
	sql := "insert into t_user(username, password, email) values (?,?,?)"
	result, err := utils.Db.Exec(sql, user.Username, user.Password, user.Email)
	if err != nil {
		return
	}
	rows, err := result.RowsAffected()
	fmt.Println("插入成功 rows=", rows)
	return
}
