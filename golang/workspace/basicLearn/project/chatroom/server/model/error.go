package model

import "errors"

var (
	ERR_USER_NOT_EXIST = errors.New("用户不存在")
	ERR_USER_PWD_ERR   = errors.New("密码错误")
	ERR_USER_EXIST     = errors.New("用户已存在")
)
