// Copyright 2018 The Go Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package shift_test

import (
	"testing"

	"github.com/Go-zh/tools/go/analysis/analysistest"
	"github.com/Go-zh/tools/go/analysis/passes/shift"
)

func Test(t *testing.T) {
	testdata := analysistest.TestData()
	analysistest.Run(t, testdata, shift.Analyzer, "a")
}
