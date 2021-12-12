package main

import (
	"database/sql"
	"fmt"
	"log"

	"github.com/go-sql-driver/mysql"
)

var db *sql.DB

func main() {
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
	var err error
	db, err = sql.Open("mysql", cfg.FormatDSN())
	if err != nil {
		log.Fatal(err)
	}

	pingErr := db.Ping()
	if pingErr != nil {
		log.Fatal(pingErr)
	}

	fmt.Println("Connected")

	albums, err := albumsByArtist("John Coltrane")
	if err != nil {
		log.Fatal(err)
	}
	fmt.Printf("Albums found: %+v\n", albums)

	alb, err := albumById(1)
	if err != nil {
		log.Fatal(err)
	}
	fmt.Printf("albumById found:%+v\n", alb)

	// 添加一条记录
	albId, err := addAlbum(Album{
		Title:  "The Modern sound of Betty Carter",
		Artist: "Betty Carter",
		Price:  49.99,
	})
	if err != nil {
		log.Fatal(err)
	}
	fmt.Printf("ID of added album: %v\n", albId)

}

type Album struct {
	ID     int64
	Title  string
	Artist string
	Price  float32
}

// 查询多条记录
func albumsByArtist(name string) ([]Album, error) {
	var albums []Album
	rows, err := db.Query("SELECT * FROM album WHERE artist = ?", name)
	if err != nil {
		return nil, fmt.Errorf("albumsByArtist %q: %v", name, err)
	}
	defer rows.Close()
	for rows.Next() {
		var alb Album
		if err := rows.Scan(&alb.ID, &alb.Title, &alb.Artist, &alb.Price); err != nil {
			return nil, fmt.Errorf("albumsByArtist %q: %v", name, err)
		}
		albums = append(albums, alb)
	}
	if err := rows.Err(); err != nil {
		return nil, fmt.Errorf("albumsByArtist %q: %v", name, err)
	}
	return albums, nil
}

// 查询单条记录
func albumById(id int64) (Album, error) {
	var alb Album
	row := db.QueryRow("SELECT * FROM album WHERE id = ?", id)
	if err := row.Scan(&alb.ID, &alb.Title, &alb.Artist, &alb.Price); err != nil {
		if err == sql.ErrNoRows {
			return alb, fmt.Errorf("albumById %d: no such album", id)
		}
		return alb, fmt.Errorf("albumById %d: %v", id, err)
	}
	return alb, nil
}

// 插入数据
func addAlbum(alb Album) (int64, error) {
	result, err := db.Exec("INSERT INTO album(title, artist, price) VALUES(?, ?, ?)", alb.Title, alb.Artist, alb.Price)
	if err != nil {
		return 0, fmt.Errorf("addAlbum: %v", err)
	}
	id, err := result.LastInsertId()
	if err != nil {
		return 0, fmt.Errorf("addAlbum: %v", err)
	}
	return id, nil
}
