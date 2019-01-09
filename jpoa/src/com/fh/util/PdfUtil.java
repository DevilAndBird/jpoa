package com.fh.util;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtil {

	/**
	 * 
	 * @Title: turnToPdf
	 * @Description: 生成QR码
	 * author：tangqm
	 * 2018年7月12日
	 * @param basePath
	 * @return
	 */
    public static String turnToPdf(String basePath){
        File file = new File(basePath);
        if(!file.exists()){
             file.mkdirs();
        }

        String path = "";

        try {
            //创建文件
            Document document = new Document();
            //建立一个书写器
            path = basePath+"QRCODE.pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));

            //打开文件
            document.open();

            //中文字体,解决中文不能显示问题
//            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            BaseFont bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/RTWSYUEGOTRIAL-REGULAR.TTF", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);    
            Font font = new Font(bfChinese);//正常字体
            Font fontBold = new Font(bfChinese, 12, Font.BOLD);//正常加粗字体
            Font fontBig = new Font(bfChinese, 20);//大字体
            Font fontBigBold = new Font(bfChinese, 42, Font.BOLD);//加粗大字体
            

            //添加主题
//            Paragraph theme = new Paragraph("行李QR码", fontBigBold);
//            theme.setAlignment(Element.ALIGN_CENTER);
//            document.add(theme);

          Image jpqr = Image.getInstance("D:\\上海景沛\\z.jpg");
          //插入一个图片
          //设置图片位置的x轴和y周
          jpqr.setAbsolutePosition(205f, 400f);
          //设置图片的宽度和高度
//          jpqr.scaleAbsolute(176, 175);
          document.add(jpqr);
          
          Image backgroundimg = Image.getInstance("D:\\上海景沛\\backgound.png");
          //插入一个图片
          //设置图片位置的x轴和y周
          backgroundimg.setAbsolutePosition(99f, 149f);
         //设置图片的宽度和高度
//         image.scaleAbsolute(200, 200);
         document.add(backgroundimg);
            
          Paragraph peopleInfo = new Paragraph("包给我:123456", fontBigBold);
          peopleInfo.setAlignment(Element.ALIGN_CENTER);
          document.add(peopleInfo);

            //关闭文档
            document.close();
            //关闭书写器
            writer.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return path;
    }
    
    public static void main(String[] args) {
    	turnToPdf("D:\\上海景沛\\");
	}


}
