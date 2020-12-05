package com.fdu.swcontest.Util;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ParseSignature {

    public static class MethodX{
        public String methodClass;
        public String methodName;
        public ArrayList<String> methodParams = new ArrayList<>();
    }

    public static MethodX getMethodX(String signature){
        MethodX methodX = new MethodX();
        String[] splitArr = signature.split("\\(");
        String classAndMath = splitArr[0].trim();
        String params = splitArr[1].substring(0, splitArr[1].lastIndexOf(")"));
        methodX.methodClass = classAndMath.substring(0, classAndMath.lastIndexOf('.'));
        methodX.methodName = classAndMath.substring(classAndMath.lastIndexOf('.') + 1);
        String[] paramList = null;
        if(params != null && params.length() > 0){
            paramList = params.split(",");
            for(String param : paramList){
                methodX.methodParams.add(param.trim());
            }
        }
        return methodX;
    }
}
