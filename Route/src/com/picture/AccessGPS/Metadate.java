package com.picture.AccessGPS;  
import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;  
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;  
/**
 * java��ȡ��Ƭ��Ϣ
 */
public class Metadate{
	public static void main(String[] args) throws Exception, Exception{  
        File file = new File("C:\\Users\\hp\\Desktop\\������.jpg");  
        File file1 = new File("E:\\�ֻ�����\\���2019.1.17\\��Ƭ\\IMG20190114114507.jpg");
        File file2 = new File("E:\\�ֻ�����\\���2019.1.17\\Camera\\IMG20180731104858.jpg");
        printImageTags(file);
        printImageTags(file1);
        printImageTags(file2);
    } 
    /** 
     * ��ȡ��Ƭ�������Ϣ 
     */ 
    private static void printImageTags(File file) throws ImageProcessingException, Exception{  
    	Metadata metadata = ImageMetadataReader.readMetadata(file);  
        for (Directory directory : metadata.getDirectories()) {  
            for (Tag tag : directory.getTags()) {  
                String tagName = tag.getTagName();  //��ǩ��
                String desc = tag.getDescription(); //��ǩ��Ϣ
                if (tagName.equals("Image Height")) {  
                	System.out.println("ͼƬ�߶�: "+desc);
                } else if (tagName.equals("Image Width")) {  
                	System.out.println("ͼƬ���: "+desc);
                } else if (tagName.equals("Date/Time Original")) {  
                	System.out.println("����ʱ��: "+desc);
                }else if (tagName.equals("GPS Latitude")) {  
                	System.err.println("γ�� : "+desc);
//                	System.err.println("γ��(�ȷ����ʽ) : "+pointToLatlong(desc));
                } else if (tagName.equals("GPS Longitude")) {  
                	System.err.println("����: "+desc);
//                	System.err.println("����(�ȷ����ʽ): "+pointToLatlong(desc));
                }
            }  
        }  
    }  
}
 