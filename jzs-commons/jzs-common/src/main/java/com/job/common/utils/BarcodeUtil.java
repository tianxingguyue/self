package com.job.common.utils;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by hzx
 * author hzx
 * 条形码生成工具
 */
public class BarcodeUtil {
    /**
     * 生成文件
     *
     * @param msg
     * @param path
     * @return
     */
    public static File generateFile(String msg, String path) {
        File file = new File(path);
        try {
            generate(msg, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * 生成字节
     *
     * @param msg
     * @return
     */
    public static byte[] generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }

    /**
     * 生成到流
     *
     * @param msg
     * @param ous
     */
    public static void generate(String msg, OutputStream ous) {
        if (StringUtils.isEmpty(msg) || ous == null) {
            return;
        }
        Code128Bean bean = new Code128Bean();
        // 精细度
        final int dpi = 150;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(1.2f / dpi);

        // 配置对象
        bean.setModuleWidth(moduleWidth);
        bean.setFontSize(15);
        bean.doQuietZone(false);
        String format = "image/png";
        try {
            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi, BufferedImage.TYPE_BYTE_BINARY, true, 0);
            // 生成条形码
            bean.generateBarcode(canvas, msg);
            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //将byte转成Base64,转换成条形码
    public static String getImageBase64(String no){
        String encode = Base64Utils.encodeToString(generate(no));
        return encode;
    }

    //将验证码生成base64
    public static String getImageBase64(BufferedImage image) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", arrayOutputStream);
            byte[] bytes = arrayOutputStream.toByteArray();
            String encode = Base64Utils.encodeToString(bytes);
            return encode;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }


}
