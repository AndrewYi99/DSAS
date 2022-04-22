package com.dsas.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 */
@Target(ElementType.METHOD)//注解放置的目标位置即方法级别
@Retention(RetentionPolicy.RUNTIME)//注解在哪个阶段执行
@Documented
public @interface OperationLogAnnotation {
    String operationModel() default ""; // 操作模块

    String operationType() default "";  // 操作类型

    String operationDesc() default "";  // 操作说明

}
