package com.ruoyi.common.utils.file;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImgFileTools {

    private static final Logger logger = LoggerFactory.getLogger(ImgFileTools.class.getSimpleName());

    public static void main(String[] args) throws IOException {
        File img = new File("C:\\Users\\19542\\Desktop\\260a28a708231a9ce74935eb96ff84bb.jpg");
        if (img.exists() && img.length() > 0) {
            FileOutputStream fos = new FileOutputStream("C:\\Users\\19542\\Desktop\\2.jpg");
//            if(thumbnail(img,1920,1080,fos)){
            if (thumbnail_w_h(img, 1366, 768, fos)) {
                System.out.println("ok");
            }
        }
    }

    private static final int[][] Resolution = {
            {1080, 1920}, {768, 1024}, {480, 800}, {320, 480}, {240, 320}, {50, 50}, {25, 25}
    };

    /**
     * 循环压缩，直到比100k小
     *
     * @param prefix   前缀，访问的时候会用到，RuoYiConfig.getProfile()
     * @param filePath 原图路径；再把 /profile 换掉
     * @return 新图片路径，不带前缀
     */
    public static String compressionLess(String prefix, String filePath, long sizeKb) throws Exception {
        filePath = filePath.replace("/profile", "");
        //拿到原图
        File img = new File(prefix + filePath);
        //不存在，或者比 100k 小，直接结束
        if (!img.exists() || img.length() < sizeKb * 1024) {
            return filePath;
        }
        //根据点分割下，然后获得新路径
        String[] split = filePath.split("\\.");
        //这个应该是最后的格式
        String fix = split[split.length - 1];
        split[split.length - 1] = "";
        //原名称
        String fileName = String.join(".", split);

        //需要开始循环了
        for (int i = 0; i < Resolution.length; i++) {
            String newFileName = fileName + i + "." + fix;
            FileOutputStream fos = new FileOutputStream(prefix + newFileName);
            thumbnail_w_h(img, Resolution[i][0], Resolution[i][1], fos);
            fos.close();
            fos.flush();
            //拿到新文件
            File newFile = new File(prefix + newFileName);
            if (newFile.length() < sizeKb * 1024) {
                //比100k小，可以返回了
                return "/profile" + newFileName;
            }
        }
        return filePath;
    }

    /**
     * 循环压缩，直到比100k小
     *
     * @param prefix   前缀，访问的时候会用到，RuoYiConfig.getProfile()
     * @param filePath 原图路径；再把 /profile 换掉
     * @return 新图片路径，不带前缀
     */
    public static String compressionLessByGoogle(String prefix, String filePath, long sizeKb) throws Exception {

        filePath = filePath.replace("/profile", "");
        //拿到原图
        File img = new File(prefix + filePath);
        //不存在，或者比 100k 小，直接结束
        if (!img.exists() || img.length() < sizeKb * 1024) {
            return filePath;
        }

        //根据点分割下，然后获得新路径
        String[] split = filePath.split("\\.");
        //这个应该是最后的格式
        String fix = split[split.length - 1];
        split[split.length - 1] = "";
        //原名称
        String fileName = String.join(".", split);

        //需要开始循环了
        for (int i = 1; i < 100; i++) {
            String newFileName = fileName + i + "." + fix;
            Thumbnails.of(prefix + filePath)
                    .scale(Math.pow(0.5, i))
                    .outputQuality(0.8f)
                    .toFile(prefix + newFileName);
            //拿到新文件
            File newFile = new File(prefix + newFileName);
            if (newFile.length() < sizeKb * 1024) {
                //比100k小，可以返回了
                return "/profile" + newFileName;
            }
        }
        return filePath;
    }

    /**
     * 按比例进行格式化
     *
     * @param multipartFile 待处理文件
     * @param imgOutPath    输出位置
     * @param width         目标宽度 height 目标高度 （按比例小的进行压缩）
     * @return 0 失败 1 成功
     */
    public static boolean formatRatio(MultipartFile multipartFile, String imgOutPath, int width, int height) {
        return formatImg(multipartFile, imgOutPath, false, width, height);
    }

    /**
     * 按比例进行格式化 默认1920*1080
     *
     * @param multipartFile 待处理文件
     * @param imgOutPath    输出位置
     */
    public static boolean formatRatioDefault(MultipartFile multipartFile, String imgOutPath) {
        return formatImg(multipartFile, imgOutPath, false, 1920, 1080);
    }

    /**
     * 按固定宽高格式化
     *
     * @param multipartFile 待处理文件
     * @param fixedWidth    目标宽度 fixHigh 目标高度
     * @param imgOutPath    输出位置
     */
    public static boolean formatFixed(MultipartFile multipartFile, String imgOutPath, int fixedWidth, int fixHigh) {
        return formatImg(multipartFile, imgOutPath, true, fixedWidth, fixHigh);
    }

    /**
     * @param multipartFile 待处理文件
     * @param isFixed       是否按固定宽高压缩
     * @param width         固定时为固定宽度否者为待对比宽度 hight 固定时为固定高度否者为待对比高度
     * @param imgOutPath    输出位置
     */
    private static boolean formatImg(MultipartFile multipartFile, String imgOutPath, boolean isFixed, int width, int height) {
        //转file方式1 也是之前的默认方式
        CommonsMultipartFile cf = (CommonsMultipartFile) multipartFile;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File img = fi.getStoreLocation(); //会在项目的根目录的临时文件夹下生成一个临时文件 *.tmp
        //转file方式2
//        File img = CJFileUtils.multipartFileToFile(multipartFile);
        if (img.exists() && img.length() > 0) {
            try {
                boolean result = false;
                FileOutputStream fos = new FileOutputStream(imgOutPath);
                if (isFixed) {
                    result = thumbnail(img, width, height, fos);
                } else {
                    result = thumbnail_w_h(img, width, height, fos);
                }
                if (null != fos) fos.close();
                return result;
            } catch (Exception e) {
                logger.debug("格式化图片异常");
                return false;
            } finally {
                deleteTempFile(img); // 最后删除缓存文件
            }
        }
        return false;
    }

    /**
     * 按照宽高比例较小者压缩
     *
     * @param img width height file文件 待对比宽度 待对比高度
     * @param out 输出流
     * @throws IOException
     */
    private static boolean thumbnail_w_h(File img, int width, int height, OutputStream out) throws IOException {
        BufferedImage bi = ImageIO.read(img);
        double srcWidth = bi.getWidth(); // 源图宽度
        double srcHeight = bi.getHeight(); // 源图高度
        double scale = 1;
        if (srcWidth > width || srcHeight > height) {
            scale = height / srcHeight < width / srcWidth ? height / srcHeight : width / srcWidth;
        }
        return thumbnail(img, (int) (srcWidth * scale), (int) (srcHeight * scale), out);
    }

    /**
     * 按照固定宽高原图压缩
     *
     * @param img   file文件
     * @param width 需要输出的宽度 height 需要输出的高度
     * @param out   输出流
     * @throws IOException
     */
    private static boolean thumbnail(File img, int width, int height, OutputStream out) throws IOException {
        BufferedImage BI = ImageIO.read(img);
        Image image = BI.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        g.drawImage(image, 0, 0, null); // 绘制处理后的图
        g.dispose();
        return ImageIO.write(tag, "JPG", out);
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    private static void deleteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

}