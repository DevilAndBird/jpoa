package com.fh.util;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import net.coobird.thumbnailator.builders.BufferedImageBuilder;

public class QRCodeUtil {
     
	private static String charset="utf-8";//定义字符编码
    private static String QrName="jpg";//定义类型
    private static int QrSize = 240;//定义size
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;


    /**
     * 生成二维码
     * @param content
     * @param imgPath
     */
    public static BufferedImage createQrimage(String content,String imgPath) throws Exception{
        Hashtable<EncodeHintType,Object> hash = new Hashtable<EncodeHintType,Object>();
        hash.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//越高存储越小,容错率越高
        hash.put(EncodeHintType.CHARACTER_SET,charset);
        int margin = 5;  //自定义白边边框宽度
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QrSize, QrSize, hash);//设置二维码参数
        bitMatrix = updateBit(bitMatrix, margin);  //生成新的bitMatrix
        int width = bitMatrix.getWidth();//获取比特矩阵的宽度
        int height = bitMatrix.getHeight();//获取比特矩阵的高度
        BufferedImage Qrimage = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);

        //开始画二维码
        for(int x = 0;x < width; x++){
            for(int y = 0;y < height; y++){
                Qrimage.setRGB(x, y, bitMatrix.get(x, y)? 0xFF000000:0xFFFFFFFF);
            }
        }

        if(imgPath == null || "".equals(imgPath)){//如果要插入的图片路径为空，直接返回图片
            return Qrimage;
        }

        QRCodeUtil.insertImage(Qrimage,imgPath);//调用insertImage函数插入图片
        return Qrimage;
    }

    private static BitMatrix updateBit(BitMatrix matrix, int margin){
        int tempM = margin*2;
       int[] rec = matrix.getEnclosingRectangle();   //获取二维码图案的属性
            int resWidth = rec[2] + tempM;
            int resHeight = rec[3] + tempM;
            BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
            resMatrix.clear();
        for(int i= margin; i < resWidth- margin; i++){   //循环，将二维码图案绘制到新的bitMatrix中
            for(int j=margin; j < resHeight-margin; j++){
                if(matrix.get(i-margin + rec[0], j-margin + rec[1])){
                    resMatrix.set(i,j);
                }
            }
        }
         return resMatrix;
    }

    /**
     * 插入内嵌图片
     * @param source
     * @param imgPath 要插入图片路径
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath) throws Exception {
        File file = new File(imgPath);
        if(!file.exists()){
            System.err.print(""+imgPath+"路径不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);//获得原宽度
        int height = src.getHeight(null);//获得源高度
//        if(needCompress){//比较要插入的图片的宽度是否大于设定的WIDTH=60像素宽
//            if(width>WIDTH){
//                width = WIDTH;
//            }
//            if(height>HEIGHT){//比较要插入的图片的高度是否大于设定的HEIGTH=60像素宽
//                height = HEIGHT;
//            }
//            Image image = src.getScaledInstance(width, height, //把image对象的getScaledInstance方法把图片缩小heightXwidth像素大小
//                    Image.SCALE_SMOOTH);
//            BufferedImage tag = new BufferedImage(width,height,///创建一个透明色的BufferedImage对象
//                    BufferedImage.TYPE_INT_RGB);
//            Graphics g = tag.getGraphics();//获得画笔
//            g.drawImage(image, 0, 0, null);//绘制指定图像中当前可用的image图像，图像的左上角位于该图形上下文坐标（0，0）的 (x, y)
//        }
        //开始画内嵌图片
        Graphics2D graph = source.createGraphics();
        //计算绘画坐标
        int x = (QrSize - width)/2-26;
        int y = (QrSize - height)/2-26;
        graph.drawImage(src, x, y, width, height, null);//内嵌坐标为（x,y)的地方
        Shape shape = new RoundRectangle2D.Float(x,y,width,width,6,6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        boolean hasAlpha = false;
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null),
                    image.getHeight(null), transparency);
        } catch (HeadlessException e) {
        }
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null),
                    image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

    public static void mkdirs(String destPath){
        File file = new File(destPath);
        if(!file.exists() && !file.isDirectory()){
            file.mkdirs();
        }
    }

    public static void encode(String QR, String backgroundPath, String qrLogoPath, String destPath) throws Exception{
        // 生成qr码（内嵌logo）
        BufferedImage image = QRCodeUtil.createQrimage(QR, qrLogoPath);
        // 获取背景图
        Image bg = Toolkit.getDefaultToolkit().getImage(backgroundPath);
        BufferedImage bg_buffered = toBufferedImage(bg);

        // background得到画笔对象
        Graphics g = bg_buffered.getGraphics();

        // 将qr码嵌入背景图
        g.drawImage(image,175,323, 310, 310,null);

        // 将qr码文字嵌入背景图
        g.setColor(Color.BLACK);
        Font font = new Font("宋体",Font.BOLD,70);
        g.setFont(font);

        g.drawString(QR.substring(4), 212, 692);

        // 检查是否有该路径，没有则创建
        mkdirs(destPath);
        ImageIO.write(bg_buffered, QrName, new File(destPath+"/" + QR + ".jpg"));
    }



    public static void main(String[] args) {
        try {
        	QRCodeUtil.encode("JPQR123457","D:\\QR\\bg.png", "D:\\QR\\logo.png","D:\\QR\\dest");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
