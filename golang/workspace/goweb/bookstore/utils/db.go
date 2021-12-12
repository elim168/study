package utils

import (
	"database/sql"
	"fmt"
	"log"

	"github.com/go-sql-driver/mysql"
)

var (
	Db  *sql.DB
	err error
)

func init() {

	cfg := mysql.Config{
		User:                 "root",
		Passwd:               "123456",
		Net:                  "tcp",
		Addr:                 "172.17.0.3:3306",
		DBName:               "bookstore",
		AllowNativePasswords: true,
		ParseTime:            true,
	}
	//root:123456@tcp(172.17.0.3:3306)/test_db?checkConnLiveness=false&maxAllowedPacket=0
	fmt.Println("数据库连接参数是：", cfg.FormatDSN())

	Db, err = sql.Open("mysql", cfg.FormatDSN()) // // Db抽取为预定义的变量时，err也必须这么定义。否则用:=定义的话Db会被当作一个局部变量。
	if err != nil {
		log.Fatal(err)
	}

	// Ping verifies a connection to the database is still alive, establishing a connection if necessary
	pingErr := Db.Ping()
	if pingErr != nil {
		log.Fatal("数据库连接失败", pingErr)
	}

	fmt.Println("Connected 数据库连接成功")

}
