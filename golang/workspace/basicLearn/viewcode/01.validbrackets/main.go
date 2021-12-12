package main

import "fmt"

// 判断一个括号是否是有效的。括号中只能包含小括号、中括号、大括号

func isValidBrackets(s string) bool {
	if len(s) < 2 {
		return false
	}
	var chars []rune

	for _, c := range s {
		switch c {
		case '(', '[', '{':
			chars = append(chars, c)
		case ')', ']', '}':
			clen := len(chars)
			if clen == 0 {
				return false
			}
			lastChar := chars[clen-1]
			if c == ')' && lastChar != '(' {
				return false
			}
			if c == ']' && lastChar != '[' {
				return false
			}
			if c == '}' && lastChar != '{' {
				return false
			}
			chars = chars[0 : len(chars)-1]
		}
	}
	return len(chars) == 0
}

func printResult(s string) {
	fmt.Println(s, "是一个有效的括号：", isValidBrackets(s))
}

func main() {
	printResult("()")
	printResult("([])")
	printResult("([)]")
	printResult("([()])")
	printResult("([()[[[]]](()){{{([])}}}])")
}
