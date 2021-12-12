package dao

import (
	"fmt"
	"math"

	"example.com/goweb/bookstore/model"
	"example.com/goweb/bookstore/utils"
)

var BookDao *bookDao

type bookDao struct {
}

// 获取所有的图书信息，不分页的那种。
func (bookDao *bookDao) GetAllBooks() ([]*model.Book, error) {
	sqlstr := "select id, title, author, price, sales, stock, img_path from t_book"
	rows, err := utils.Db.Query(sqlstr)
	if err != nil {
		return nil, err
	}
	var books []*model.Book
	for rows.Next() {
		var book model.Book
		err := rows.Scan(&book.Id, &book.Title, &book.Author, &book.Price, &book.Sales, &book.Stock, &book.ImagePath)
		if err != nil {
			return nil, err
		}
		books = append(books, &book)
	}
	return books, nil
}

// 获取图书信息，带分页的那种。
func (bookDao *bookDao) GetPageBooks(pageNo, pageSize int) (*model.Page, error) {
	var (
		total     int
		totalPage int
	)
	totalSql := "select count(*) from t_book"
	totalRow := utils.Db.QueryRow(totalSql)
	err := totalRow.Scan(&total)
	if err != nil {
		return nil, err
	}
	if total%pageSize == 0 {
		totalPage = total / pageSize
	} else {
		totalPage = total/pageSize + 1
	}
	sqlstr := "select id, title, author, price, sales, stock, img_path from t_book order by id limit ?,?"
	rows, err := utils.Db.Query(sqlstr, (pageNo-1)*pageSize, pageSize)
	if err != nil {
		return nil, err
	}
	var books []interface{}
	for rows.Next() {
		var book model.Book
		err := rows.Scan(&book.Id, &book.Title, &book.Author, &book.Price, &book.Sales, &book.Stock, &book.ImagePath)
		if err != nil {
			return nil, err
		}
		books = append(books, &book)
	}
	page := &model.Page{
		PageNo:    pageNo,
		PageSize:  pageSize,
		Total:     total,
		TotalPage: totalPage,
		Data:      books,
	}
	return page, nil
}

// 获取图书信息，带分页的那种。
func (bookDao *bookDao) GetPageBooksByPrice(pageNo, pageSize int, minPrice, maxPrice float64) (*model.Page, error) {
	var (
		total     int
		totalPage int
	)
	if maxPrice == 0 {
		maxPrice = math.MaxFloat32
	}
	totalSql := "select count(*) from t_book where price >= ? and price <=?"
	totalRow := utils.Db.QueryRow(totalSql, minPrice, maxPrice)
	err := totalRow.Scan(&total)
	if err != nil {
		return nil, err
	}
	if total%pageSize == 0 {
		totalPage = total / pageSize
	} else {
		totalPage = total/pageSize + 1
	}
	sqlstr := "select id, title, author, price, sales, stock, img_path from t_book where price >= ? and price <=? order by id limit ?,?"
	rows, err := utils.Db.Query(sqlstr, minPrice, maxPrice, (pageNo-1)*pageSize, pageSize)
	if err != nil {
		return nil, err
	}
	var books []interface{}
	for rows.Next() {
		var book model.Book
		err := rows.Scan(&book.Id, &book.Title, &book.Author, &book.Price, &book.Sales, &book.Stock, &book.ImagePath)
		if err != nil {
			return nil, err
		}
		books = append(books, &book)
	}
	page := &model.Page{
		PageNo:    pageNo,
		PageSize:  pageSize,
		Total:     total,
		TotalPage: totalPage,
		Data:      books,
	}
	return page, nil
}

func (bookDao *bookDao) AddBook(book *model.Book) error {
	sqlStr := "INSERT INTO t_book (title, author ,price, sales , stock , img_path) VALUES(?, ?, ?, ?, ?, ?)"
	result, err := utils.Db.Exec(sqlStr, book.Title, book.Author, book.Price, book.Sales, book.Stock, book.ImagePath)
	if err == nil {
		id, _ := result.LastInsertId()
		count, _ := result.RowsAffected()
		fmt.Printf("添加图书成功，新加入的ID: %v, 插入成功行数：%v \n", id, count)
	}
	return err
}

func (bookDao *bookDao) UpdateBook(book *model.Book) error {
	sqlStr := "UPDATE t_book set title=?, author=? ,price=?, sales=? , stock=? , img_path=? where id=?"
	result, err := utils.Db.Exec(sqlStr, book.Title, book.Author, book.Price, book.Sales, book.Stock, book.ImagePath, book.Id)
	if err == nil {
		count, _ := result.RowsAffected()
		fmt.Printf("修改图书的成功，更新行数：%v \n", count)
	}
	return err
}

func (bookDao *bookDao) GetById(id int) (*model.Book, error) {
	sqlstr := "select id, title, author, price, sales, stock, img_path from t_book where id=?"
	row := utils.Db.QueryRow(sqlstr, id)

	var book model.Book
	err := row.Scan(&book.Id, &book.Title, &book.Author, &book.Price, &book.Sales, &book.Stock, &book.ImagePath)
	return &book, err
}

func (bookDao *bookDao) DeleteById(id int) error {
	sqlStr := "delete from t_book where id=?"
	_, err := utils.Db.Exec(sqlStr, id)
	return err
}

func init() {
	BookDao = &bookDao{}
}
