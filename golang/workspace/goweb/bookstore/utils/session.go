package utils

import (
	"encoding/gob"
	"fmt"
	"net/http"

	"example.com/goweb/bookstore/model"
	"github.com/go-sql-driver/mysql"
	"github.com/srinathgs/mysqlstore"
)

var SessionStore *mysqlstore.MySQLStore
var sessionName = "sessionId"

func init() {
	// 注册需要被序列化和反序列化的结构体
	gob.Register(&model.User{})

	cfg := mysql.Config{
		User:                 "root",
		Passwd:               "123456",
		Net:                  "tcp",
		Addr:                 "172.17.0.3:3306",
		DBName:               "bookstore",
		AllowNativePasswords: true,
		ParseTime:            true, // 指定该参数可以把数据库中的日期字段转换为golang中的time.Time类型
	}
	var err error
	SessionStore, err = mysqlstore.NewMySQLStore(cfg.FormatDSN(), "table_session", "/", 1800, []byte("<SecretKey>"))
	if err != nil {
		panic(err)
	}

}

func SaveUserToSession(w http.ResponseWriter, r *http.Request, user *model.User) error {
	// 获取或创建一个session
	session, _ := SessionStore.Get(r, sessionName)
	session.Values["user"] = user
	// session.Values["userId"] = user.Id
	// session.Values["username"] = user.Username
	// fmt.Printf("SessionID=%v \n", session.ID)
	// err := SessionStore.Save(r, w, session)
	err := session.Save(r, w)
	return err
}

// 一开始查不回来缓存的会话信息。原来是从数据库查询数据时时间映射为golang的time.Time类型时报错。
//  unsupported Scan, storing driver.Value type []uint8 into type *time.Time
// 解决方案就是在数据库连接串上加上parseTime=True

func GetUserFromSession(r *http.Request) *model.User {
	session, _ := SessionStore.Get(r, sessionName)
	userObj := session.Values["user"]
	fmt.Println(r.URL.Path, "====userObj =============", userObj)
	if userObj == nil {
		return nil
	}
	user := userObj.(*model.User)
	return user
}

func DeleteSession(w http.ResponseWriter, r *http.Request) {
	session, _ := SessionStore.Get(r, sessionName)
	SessionStore.Delete(r, w, session)
}
