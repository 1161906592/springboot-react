package com.zhn.common.annotation;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExportEntityField {
    String EnName() default "字段名";
    String CnName() default "中文名";
}
