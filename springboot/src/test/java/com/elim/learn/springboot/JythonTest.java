package com.elim.learn.springboot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.Test;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class JythonTest {

    @Test
    public void test() {
        Properties props = new Properties();
        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.import.site","false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec("import sys");
            interpreter.exec("sys.path.append('D:/ProgramFiles/Python/Python36/Lib')");//jython自己的
            interpreter.exec("sys.path.append('D:/ProgramFiles/Python/Python36/Lib/site-packages')");//jython自己的
//          interpreter.exec("sys.path.append('F:/workspace/wxserver/WebContent/py')");//我们自己写的
            interpreter.execfile("D:/work/test/jython/var_group.py");
            
            
            Integer result = interpreter.get("aa", Integer.class);
            System.out.println("The Result is : " + result);
        }
    }
    
    @Test
    public void testSimple() {
        Properties props = new Properties();
        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.import.site","false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.exec("import sys");
            interpreter.exec("sys.path.append('D:/ProgramFiles/Python/Python36/Lib')");//jython自己的
            interpreter.exec("sys.path.append('D:/ProgramFiles/Python/Python36/Lib/site-packages')");//jython自己的
            interpreter.execfile("D:/work/test/jython/python_test.py");
            Integer result = interpreter.get("aa", Integer.class);
            System.out.println("The Result is : " + result);
        }
    }
    
    @Test
    public void test2() {
        Properties props = new Properties();
        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.import.site","false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interp = new PythonInterpreter();
        
        interp.exec("import sys");
//        interp.exec("sys.path.append('D:/ProgramFiles/Python/Python36/Lib')");//jython自己的
//        interp.exec("sys.path.append('D:/ProgramFiles/Python/Python36/Lib/site-packages')");//jython自己的
//        interp.exec("sys.path.append('F:/workspace/wxserver/WebContent/py')");//我们自己写的
//        interp.exec("import sys");
        interp.exec("print sys");
        interp.set("a", new PyInteger(42));
        interp.exec("print a");
        interp.exec("x = 2+2");
        PyObject x = interp.get("x");
        System.out.println("x: " + x);
        
        interp.close();
    }
    
    @Test
    public void test3() throws Exception {
        Process process = Runtime.getRuntime().exec("python D:/work/test/jython/var_group.py");
        int waitFor = process.waitFor();
        if (waitFor == 0) {
            InputStream is = process.getInputStream();
            System.out.println("结果是：");
            System.out.println(this.streamToString(is));
        } else {
            InputStream errorStream = process.getErrorStream();
            System.out.println("调用异常：");
            System.out.println(this.streamToString(errorStream));
        }
    }
    
    private String streamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(is, baos);
        String result = new String(baos.toByteArray());
        return result;
    }
    
    @Test
    public void test4() {
        Properties props = new Properties();
        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.import.site","false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();
        String source = "def MakeupMissing(x):\r\n" + 
                "   # :param x: 输入的数字\r\n" + 
                "    if x<0:\r\n" + 
                "        return -1\r\n" + 
                "    else:\r\n" + 
                "        return x*20\r\n" + 
                "\r\n" + 
                "#######测试       \r\n" + 
                "aa=MakeupMissing(-1);\r\n" + 
                "print (aa)";
        interpreter.exec(source);
        Integer result = interpreter.get("aa", Integer.class);
        System.out.println("--------result:   " + result);
        
        
        PyFunction func = interpreter.get("MakeupMissing", PyFunction.class);
        PyObject result2 = func.__call__(new PyInteger(10));
        System.out.println(result2.asInt());
        System.out.println(result2);
        
        interpreter.close();
    }
    
}
