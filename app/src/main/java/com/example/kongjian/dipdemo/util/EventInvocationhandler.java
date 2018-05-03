package com.example.kongjian.dipdemo.util;

import android.app.Activity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by user on 2018/5/3.
 */

public class EventInvocationhandler implements InvocationHandler{
    private Activity target;
    private Map<String, Method> methodMap;

    public EventInvocationhandler(Activity activity, Map<String, Method> methodMap) {
        this.target = activity;
        this.methodMap = methodMap;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method--->onClick
        //onClick--->mHandle
        Method m = methodMap.get(method.getName());
        if (method != null) {
            return m.invoke(target, args);
        }
        return method.invoke(proxy,args);
    }

}
