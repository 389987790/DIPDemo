package com.example.kongjian.dipdemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by user on 2018/5/3.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
/**
 * 事件三要素
 * 1、设置监听方法
 * 2、事件监听类型
 * 3、事件触发后执行回调方法的名称
 * */
public @interface EventBase {
    /**设置事件监听方法*/
    String listenerSetter();
    /**设置监听类型*/
    Class<?> listenerType();
    /**设置事件触发后回调方法的名称*/
    String callbackMethod();
}
