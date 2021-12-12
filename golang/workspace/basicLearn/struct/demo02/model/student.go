package model

// student 结构体student是非导出的，外部不可直接使用它的类型。但是通过指针可以使用它的方法等
type student struct {
	Name string
	Age  int
	sex  string
}

// 绑定导出的方法
func (s *student) GetSex() string {
	return s.sex
}

// NewStudent 创建非导出的student的实例，返回student对象的指针
func NewStudent(name, sex string, age int) *student {
	return &student{
		name,
		age,
		sex,
	}
}
