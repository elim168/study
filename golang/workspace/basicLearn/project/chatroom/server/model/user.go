package model

type User struct {
	UserId   int    `json:"userId"`
	Password string `json:"password"`
	Name     string `json:"name"`
}
