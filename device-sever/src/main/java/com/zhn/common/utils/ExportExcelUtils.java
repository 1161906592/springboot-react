package com.zhn.common.utils;

import com.zhn.common.annotation.ExportEntityField;
import com.zhn.common.exception.BusinessException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ExportExcelUtils {
    public static <T> void export(String excelName, List<T> list, Class<T> c, HttpServletResponse response) throws Exception {
        // 设置response头信息
        response.reset();
        // 改成输出excel文件
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=" + new String(excelName.getBytes("gb2312"), StandardCharsets.ISO_8859_1) + ".xls");
        try {
            // 创建一个WorkBook,对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            // 在Workbook中，创建一个sheet，对应Excel中的工作薄（sheet）
            HSSFSheet sheet = wb.createSheet(excelName);
            // 设置 边距、页眉、页脚
            HSSFPrintSetup printSetup = sheet.getPrintSetup();
            // 打印方向，true：横向，false：纵向(默认)
            printSetup.setLandscape(true);
            printSetup.setHeaderMargin(0.2);
            printSetup.setFooterMargin(0.2);
            // 设置打印缩放为88%
            // printSetup.setScale((short) 55);
            printSetup.setFitHeight((short) 0);
            printSetup.setFitWidth((short) 1);
            Footer footer = sheet.getFooter();
            // 设置页数
            footer.setCenter("第" + HeaderFooter.page() + "页，共 " + HeaderFooter.numPages() + "页");
            Header header = sheet.getHeader();
            // 自定义页眉,并设置页眉 左中右显示信息
            // 居中
            header.setCenter("Center Header");
            // 靠左
            header.setLeft(HSSFHeader.font("宋体", "") + HSSFHeader.fontSize((short) 16) + excelName + ".xls");
            // 创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            // 设置边框
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 左边框
            style.setBorderTop(HSSFCellStyle.BORDER_THIN); // 上边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN); // 右边框
            // 创建一个居中格式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 上下居中
            //设置字体
            HSSFFont font = wb.createFont();
            font.setFontName("宋体");

            style.setFont(font);
            // 填充工作表
            // 获取需要转出的excel表头的map字段
            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
            // 循环注解里面的值 填入Link集合
            Field[] declaredFields = c.getDeclaredFields();

            for (Field declaredField : declaredFields) {
                // 获取注解对象
                ExportEntityField declaredAnnotation = declaredField.getDeclaredAnnotation(ExportEntityField.class);
                if (declaredAnnotation != null) {
                    fieldMap.put(declaredAnnotation.EnName(), declaredAnnotation.CnName());
                }
            }
            fillSheet(sheet, list, fieldMap, style);
            // 将文件输出
            OutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new BusinessException("导出Excel失败！");
        }
    }

    public static Field getFieldByName(String fieldName, Class<?> clazz) {
        // 拿到本类的所有字段
        Field[] selfFields = clazz.getDeclaredFields();
        // 如果本类中存在该字段，则返回
        for (Field field : selfFields) {
            // 如果本类中存在该字段，则返回
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        // 否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            // 递归
            return getFieldByName(fieldName, superClazz);
        }
        // 如果本类和父类都没有，则返回空
        return null;
    }

    public static Object getFieldValueByName(String fieldName, Object o) throws Exception {
        Object value = null;
        // 根据字段名得到字段对象
        Field field = getFieldByName(fieldName, o.getClass());
        // 如果该字段存在，则取出该字段的值
        if (field != null) {
            // 类中的成员变量为private,在类外边使用属性值，故必须进行此操作
            field.setAccessible(true);
            // 获取当前对象中当前Field的value
            value = field.get(o);
        } else {
            throw new Exception(o.getClass().getSimpleName() + "类不存在字段名 "
                    + fieldName);
        }
        return value;
    }

    public static Object getFieldValueByNameSequence(String fieldNameSequence, Object o) throws Exception {
        Object value = null;
        // 将fieldNameSequence进行拆分
        String[] attributes = fieldNameSequence.split("\\.");
        if (attributes.length == 1) {
            value = getFieldValueByName(fieldNameSequence, o);
        } else {
            // 根据数组中第一个连接属性名获取连接属性对象，如student.department.name
            Object fieldObj = getFieldValueByName(attributes[0], o);
            // 截取除第一个属性名之后的路径
            String subFieldNameSequence = fieldNameSequence.substring(fieldNameSequence.indexOf(".") + 1);
            // 递归得到最终的属性对象的值
            value = getFieldValueByNameSequence(subFieldNameSequence, fieldObj);
        }
        return value;
    }

    public static <T> void fillSheet(HSSFSheet sheet, List<T> list, LinkedHashMap<String, String> fieldMap, HSSFCellStyle style) throws Exception {
        // 定义存放英文字段名和中文字段名的数组
        String[] enFields = new String[fieldMap.size()];
        String[] cnFields = new String[fieldMap.size()];
        // 填充数组
        int count = 0;
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            enFields[count] = entry.getKey();
            cnFields[count] = entry.getValue();
            count++;
        }
        // 存储最大列宽
        Map<Integer, Integer> maxWidth = new HashMap<>();
        HSSFRow row = sheet.createRow((int) 0);
        // 填充表头
        for (int i = 0; i < cnFields.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(cnFields[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
            // 设置自适应宽高
            maxWidth.put(i, cell.getStringCellValue().getBytes().length * 256 + 200);
        }
        // 填充内容
        for (int index = 0; index < list.size(); index++) {
            row = sheet.createRow(index + 1);
            // 获取单个对象
            T item = list.get(index);
            int j = 0;
            for (String enField : enFields) {
                HSSFCell createCell = row.createCell(j);
                Object objValue = getFieldValueByNameSequence(enField, item);
                String fieldValue = objValue == null ? "" : objValue.toString();
                createCell.setCellValue(fieldValue);
                // 这里把宽度最大限制到15000
                int length = Math.min(15000, createCell.getStringCellValue().getBytes().length * 256 + 200);
                maxWidth.put(j, Math.max(length, maxWidth.get(j)));
                j++;
                createCell.setCellStyle(style);
            }
        }

        // 列宽自适应
        for (int i = 0; i < cnFields.length; i++) {
            sheet.setColumnWidth(i, maxWidth.get(i));
        }
    }
}
