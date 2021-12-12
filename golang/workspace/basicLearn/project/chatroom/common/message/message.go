package message

const (
	LoginMesType            = "loginMes"
	LoginResMesType         = "loginResMes"
	RegisterMesType         = "registerMes"
	RegisterResMesType      = "registerResMes"
	NotifyUserStatusMesType = "notifyUserStatusMes"
	SmsMesType              = "smsMes"
)

const (
	StatusOnline = iota
	StatusOffline
)

// 通信的消息类型
type Message struct {
	// 消息类型
	Type string `json:"type"`
	// 消息体
	Data string `json:"data"`
}

// 登录消息
type LoginMes struct {
	UserId   int    `json:"userId"`
	Password string `json:"password"`
}

// 登录的响应消息
type LoginResMes struct {
	Code  int    `json:"code"`
	Error string `json:"error"`
	// 登录成功时返回当前登录用户的名字
	Name          string `json:"name"`
	OnlineUserIds []int  `json:"onlineUserIds"`
}

// 注册信息
type RegisterMes struct {
	User User `json:"user"`
}

// 注册的响应消息
type RegisterResMes struct {
	Code  int    `json:"code"`
	Error string `json:"error"`
}

// 通知用户的状态的消息
type NotifyUserStatusMes struct {
	UserId int `json:"userId"`
	Status int `json:"status"`
}

// 消息
type SmsMes struct {
	User
	Content string `json:"content"`
}
