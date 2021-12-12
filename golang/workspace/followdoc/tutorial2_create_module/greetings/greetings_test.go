package greetings

import (
	"testing"
	"regexp"
)
/*
文件名必须是XXX_test.go形式；包名必须与需要测试的包相同。
方法名必须是TestXXX形式，方法参数必须是*testing.T。
之后在包路径下运行go test即可运行该包下所有的XXX_test.go文件。
go test的输出结果是非常简洁的，文件测试通过后只会有一行；可以通过
go test -v运行测试，其会输出每个测试方法的运行情况。
*/
func TestHelloName(t *testing.T) {

	name := "Gladys"
	want := regexp.MustCompile(`\b` + name + `\b`)
	msg, err := Hello("Gladys")
	if !want.MatchString(msg) || err != nil {
		t.Fatalf(`Hello("Gladys") = %q,%v, want match for %#q, nil`, msg, err, want)
	}

}


func TestHelloEmpty(t *testing.T) {

	msg, err := Hello("")
	if msg != "" || err == nil {
		t.Fatalf(`Hello("") = %q, %v, want "",error`, msg, err)
	}

}


