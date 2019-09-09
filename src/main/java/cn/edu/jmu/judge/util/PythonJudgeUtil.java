package cn.edu.jmu.judge.util;

import cn.edu.jmu.judge.entity.dto.JudgeResultDto;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.util.Properties;

/**
 * @author xeathen
 * @date 2019/9/8 10:47
 */
public class PythonJudgeUtil {


    public static void sqlJudge(Long sulutionID){
//        Properties props = new Properties();
//        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
//        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
//        props.put("python.import.site","false");
//        Properties preprops = System.getProperties();
//        PythonInterpreter.initialize(preprops, props, new String[0]);
//
//        PySystemState sys = Py.getSystemState();
//        System.out.println(sys.path.toString());    // previous
//        sys.path.add("C:\\Users\\Savage\\.m2\\repository\\org\\python\\jython-standalone\\2.7.0\\jython-standalone-2.7.0.jar\\Lib");
//        sys.path.add("C:\\Users\\Savage\\AppData\\Local\\Programs\\Python\\Python37-32\\Lib\\site-packages");
//        sys.path.add("C:\\Users\\Savage\\AppData\\Local\\Programs\\Python\\Python37-32\\Lib");
//        System.out.println(sys.path.toString());   // later

        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("./judger.py");
        PyFunction func = interpreter.get("main", PyFunction.class);
        PyObject pyObj = func.__call__(new PyLong(sulutionID));
//        JudgeResultDto result = new JudgeResultDto();
        System.out.println("answer = " + pyObj.toString());
    }
}
