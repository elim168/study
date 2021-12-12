/*
文件名以“_test.go”结尾的文件都会被当作为单元测试文件。测试文件一般与被测试文件同属于一个目录和包名。这样它就可以很方便的访问被测试的包

在当前目录下运行go test即可运行当前目录下的单元测试文件。它会输出单元测试的简要结果信息。

PASS
ok      example.com/elim/basic/testing/demo01   0.001s



运行“go test -v”则可以看到测试的详细信息，其会输出每个测试方法的运行结果。

=== RUN   TestAdd
--- PASS: TestAdd (0.00s)
=== RUN   TestSub
--- PASS: TestSub (0.00s)
=== RUN   TestMultiply
--- PASS: TestMultiply (0.00s)
=== RUN   TestDivide
--- PASS: TestDivide (0.00s)
PASS
ok      example.com/elim/basic/testing/demo01   0.001s

如果只想测试某个测试方法，可以使用“-test.run”指定需要测试的方法，比如下面的TestAdd

$ go test -v -test.run TestAdd
=== RUN   TestAdd
    calculate_test.go:38: 正常的输出可以使用Log()或Logf()
--- PASS: TestAdd (0.00s)
PASS
ok      example.com/elim/basic/testing/demo01   0.001s


也可以只指定测试单个的测试文件，此时需要在后面加上测试文件及相应的源文件，如下面的calculate_test.go和calculate.go
$ go test -v testing/demo01/calculate_test.go testing/demo01/calculate.go
=== RUN   TestAdd
    calculate_test.go:47: 正常的输出可以使用Log()或Logf()
--- PASS: TestAdd (0.00s)
=== RUN   TestSub
--- PASS: TestSub (0.00s)
=== RUN   TestMultiply
--- PASS: TestMultiply (0.00s)
=== RUN   TestDivide
--- PASS: TestDivide (0.00s)
PASS
ok      command-line-arguments  0.002s
*/

package calculate

import "testing"

/*
测试函数的函数名必须是Test打头，即TestXxx结构，其中Xxx部分的首字母必须大写。
*/
func TestAdd(t *testing.T) {
	res := add(10, 20)
	expected := 30
	if res != expected {
		t.Fatalf("add(10,20) Error, Expected=%v, actual=%v", expected, res)
	}
	t.Log("正常的输出可以使用Log()或Logf()")
}

// 该测试函数不会被执行，因为测试函数的Test后面部分的首字母必须是大写的。
func Testadd(t *testing.T) {
	res := add(10, 20)
	expected := 30
	if res != expected {
		t.Fatalf("add(10,20) Error, Expected=%v, actual=%v", expected, res)
	}
}

func TestSub(t *testing.T) {
	res := sub(100, 20)
	expected := 80
	if res != expected {
		t.Fatalf("add(100,20) Error, Expected=%v, actual=%v", expected, res)
	}
}

func TestMultiply(t *testing.T) {
	res := multiply(100, 20)
	expected := 2000
	if res != expected {
		t.Fatalf("add(100,20) Error, Expected=%v, actual=%v", expected, res)
	}
}

func TestDivide(t *testing.T) {
	res := divide(100, 20)
	expected := 5
	if res != expected {
		t.Fatalf("add(100,20) Error, Expected=%v, actual=%v", expected, res)
	}
}
