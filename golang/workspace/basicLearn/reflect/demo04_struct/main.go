package main

import (
	"fmt"
	"reflect"
)

type Student struct {
	Name     string `json:"name"`
	Age      int    `json:"age"`
	Sex      string
	interest string
}

func (this Student) SayHello() {
	fmt.Println("Hello ", this.Name)
}

func (this Student) AddAge(age int) int {
	res := this.Age + age
	this.Age = res
	return res
}

func testReflectStruct(v interface{}) {

	rtype := reflect.TypeOf(v)
	// 结构体的字段数量
	numField := rtype.NumField()
	fmt.Println("结构体的字段数量是：", numField)
	// 取第1个字段，实际上就是Age字段。Field返回的类型是reflect.StructField
	ageField := rtype.Field(1)
	fmt.Println("字段名是：", ageField.Name)
	fmt.Println("tag中的属性josn的值是：", ageField.Tag.Get("json"))

	// 要访问Field的值得基于值类型的反射对象来
	rvalue := reflect.ValueOf(v)
	// reflect.Value取Field，返回的是reflectValue类型
	ageFieldValue := rvalue.Field(1)
	ageValue := ageFieldValue.Int()
	fmt.Println("通过反射取得字段age的值是：", ageValue)

	// 还可以通过字段名来取反射的Field。rtype、rvalue都可以取
	nameFieldValue := rvalue.FieldByName("Name")
	fmt.Println("字段Name的值是：", nameFieldValue.String())

	// rtype基于字段名取出来的Field又不一样，它会返回两个值，第二个值表示取得的字段是否存在
	nameField, ok := rtype.FieldByName("Name")
	if ok {
		fmt.Println("基于反射的类型按照字段名称获取Name字段成功，字段类型是：", nameField.Type.Kind())
	}

	// 基于反射获取方法。方法数量这里只能拿到可导出的方法的数量。即方法名是大写的方法数量
	numMethod := rtype.NumMethod()
	fmt.Println("通过类型反射取到了类型定义的方法数量为：", numMethod)
	// 取第一个方法。通过索引获取方法时，会按照可导出方法的方法名进行排序后的索引获取，而不是方法定义的顺序。所以0号索引取出来的就是AddAge方法。
	var addMethod reflect.Method = rtype.Method(0)

	// 输出AddAge
	fmt.Println(addMethod.Name)

	// 基于反射的reflect.Value对象也是可以取到方法的
	rvalueNumMethod := rvalue.NumMethod()
	fmt.Println("通过值对象反射取到了类型定义的方法数量为：", rvalueNumMethod)

	// 通过值反射对象获取的方法的类型是reflect.Value，而不是上面类型反射取出的reflect.Method
	var sayHelloMethod reflect.Value = rvalue.MethodByName("SayHello")
	// 方法调用，不送参数，所以指定参数为nil
	sayHelloMethod.Call(nil)

	addAgeMethod := rvalue.Method(0)
	// 通过反射调用方法时入参是reflect.Value类型的切片，出参也是reflect.Value类型的切片
	var outParams []reflect.Value = addAgeMethod.Call([]reflect.Value{reflect.ValueOf(20)})
	addAgeMethodResult := outParams[0].Int()
	fmt.Println("通过反射调用方法取到的返回结果是：", addAgeMethodResult)

}

func main() {

	var stu = Student{
		Name: "ZhangSan",
		Age:  30,
		Sex:  "男",
	}

	stu.SayHello()

	testReflectStruct(stu)

	// 在上面的方法中通过反射调用了AddAge方法改变了对象的Age值，但是这里输出的还是未改变的值。要能改变的话需要方法的接收者是指针，反射的也是指针
	fmt.Println(stu.Age)

	// 基于反射设置字段的值
	// 通过反射改变字段的值需要基于结构体对象的指针进行反射。之后取reflect.Value.Elem()，再取字段进行设值，如下面这样。
	reflect.ValueOf(&stu).Elem().FieldByName("Name").SetString("张三")
	// 张三
	fmt.Println(stu.Name)

}
