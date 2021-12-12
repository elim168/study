package main

import "fmt"

// 自定义数据结构Set。底层使用的是map，value是空结构体。所有的空结构体共享同一个地址，可以说是不占用空间。Key是任意类型。
type Set struct {
	m map[interface{}]struct{}
}

// 添加元素
func (s *Set) Add(value interface{}) {
	s.m[value] = struct{}{}
}

// 删除元素。删除成功则返回true
func (s *Set) Delete(value interface{}) bool {
	if s.Contains(value) {
		delete(s.m, value)
		return true
	}
	return false
}

// 判断是否包含某个元素
func (s *Set) Contains(value interface{}) bool {
	_, ok := s.m[value]
	return ok
}

// 获取集合的大小
func (s *Set) Size() int {
	return len(s.m)
}

// 清空集合
func (s *Set) Clear() {
	s.m = make(map[interface{}]struct{})
}

func NewSet() *Set {
	s := Set{}
	s.m = make(map[interface{}]struct{})
	return &s
}

func main() {
	s := NewSet()
	s.Add("A")
	s.Add("B")
	s.Add("C")
	s.Add("D")
	s.Add("A")
	fmt.Println(s.Size(), s.Contains("B"))

	s.Delete("C")
	fmt.Println(s.Size(), s.Contains("C"))
	s.Clear()

	fmt.Println(s.Size())
	s.Add("ABCDEFG")
	fmt.Println(s.Size())
}
