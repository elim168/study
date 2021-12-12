package util

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
		DBName:               "test_db",
		AllowNativePasswords: true,
	}
	//root:123456@tcp(172.17.0.3:3306)/test_db?checkConnLiveness=false&maxAllowedPacket=0
	fmt.Println(cfg.FormatDSN())

	// Get a database handle。它会自动管理连接池。使用它我们全程没有直接通过connection进行操作
	// open这里可能不会创建数据库连接，如果要验证是否可以连接数据库成功，可以调用Ping()。官方说明如下。
	// Open may just validate its arguments without creating a connection to the database. To verify that the data source name is valid, call Ping.
	// The returned DB is safe for concurrent use by multiple goroutines and maintains its own pool of idle connections.
	// Thus, the Open function should be called just once. It is rarely necessary to close a DB.
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
