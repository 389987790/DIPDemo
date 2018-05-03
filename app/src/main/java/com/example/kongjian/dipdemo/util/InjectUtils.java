package com.example.kongjian.dipdemo.util;

import android.app.Activity;
import android.view.View;

import com.example.kongjian.dipdemo.R;
import com.example.kongjian.dipdemo.annotation.ContentView;
import com.example.kongjian.dipdemo.annotation.EventBase;
import com.example.kongjian.dipdemo.annotation.ViewInject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2018/5/3.
 */

public  class InjectUtils {
    public static void inject(Activity activity) {
        injectLayout(activity);
        injectView(activity);
        injectEvent(activity);
    }


    /**布局注入*/
    public static void injectLayout(Activity activity) {
        Class<?> clazz = activity.getClass();
        ContentView annotation = clazz.getAnnotation(ContentView.class);
        if (annotation == null) {
            return;
        }
        int layoutId = annotation.value();
        activity.setContentView(layoutId);
    }
    /**视图注入*/
    public static void injectView(Activity activity) {
        Class<?> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            int viewId = viewInject.value();
            View view = activity.findViewById(viewId);
            try {
                field.setAccessible(true);//设置为可改变的
                field.set(activity,view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**事件注入*/
    public static void injectEvent(Activity activity) {
        Class<?> clazz = activity.getClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Map<String, Method> methodMap = new HashMap<>();
        for (Method method : declaredMethods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                if (eventBase == null) {
                    return;
                }
                //事件三要素
                String listenerSetter = eventBase.listenerSetter();
                Class<?> listenerType = eventBase.listenerType();
                String callbackMethod = eventBase.callbackMethod();
                methodMap.put(callbackMethod, method);
                //控件对象
                try {
                    Method value = annotationType.getDeclaredMethod("value");
                    int[] viewIds = (int[]) value.invoke(annotation);
                    for (int id : viewIds) {
                        View view = activity.findViewById(id);
                        if (view == null) {
                            continue;
                        }
                        //获取setListener方法
                        Method setListener = view.getClass().getMethod(listenerSetter,listenerType);
                        EventInvocationhandler eventInvocationhandler = new EventInvocationhandler(activity,methodMap);
                        //设置setListener方法
                        //代理对象（动态代理）
                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, eventInvocationhandler);
                        setListener.invoke(view, proxy);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
