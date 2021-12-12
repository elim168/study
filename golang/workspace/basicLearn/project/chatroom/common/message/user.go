package message

type User struct {
	UserId   int    `json:"userId"`
	Password string `json:"password"`
	Name     string `json:"name"`
	Status   int    `json:"status"`
}
