package com.cgn.talent.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Excel工具类
 *
 * @author CGN
 * @date 2024-01-10
 */
@Slf4j
public class ExcelUtils {

    /**
     * 导出Excel
     *
     * @param response HttpServletResponse
     * @param list 数据列表
     * @param fileName 文件名
     * @param clazz 实体类
     */
    public static void exportExcel(HttpServletResponse response, List<?> list, String fileName, Class<?> clazz) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName + ".xlsx");

            // 设置表头样式
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

            // 设置内容样式
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);

            // 策略
            HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                    new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

            // 写Excel
            EasyExcel.write(response.getOutputStream(), clazz)
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .sheet(fileName)
                    .doWrite(list);

        } catch (IOException e) {
            log.error("导出Excel失败", e);
            throw new RuntimeException("导出Excel失败：" + e.getMessage());
        }
    }

    /**
     * 导入Excel
     *
     * @param file 文件
     * @param clazz 实体类
     * @return 数据列表
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz) {
        try {
            return EasyExcel.read(file.getInputStream())
                    .head(clazz)
                    .sheet()
                    .doReadSync();
        } catch (IOException e) {
            log.error("导入Excel失败", e);
            throw new RuntimeException("导入Excel失败：" + e.getMessage());
        }
    }
}
