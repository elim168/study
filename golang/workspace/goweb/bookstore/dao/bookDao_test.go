package dao

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"testing"
)

func TestInsert(t *testing.T) {
	file, err := os.Open("/home/elim/dev/projects/study/golang/workspace/goweb/bookstore/utils/books.sql")
	if err != nil {
		t.Fatal(err)
	}
	reader := bufio.NewReader(file)
	for {
		line, err := reader.ReadString('\n')
		if err != nil {
			if err == io.EOF {
				break
			}
			fmt.Println("读取文件出错；", err)
			break
		}
		fmt.Println(line)
		// result, err := utils.Db.Exec(line)
		// fmt.Println("sql执行结果为：", result, err)
	}
	// utils.Db.Exec()

}

func TestGetAllBooks(t *testing.T) {
	books, err := BookDao.GetAllBooks()
	fmt.Println(books, err)
}
