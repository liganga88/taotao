package com.taotao.fastdfs;

import com.taotao.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

public class TestFastDFS {

    @Test
    public void uploadFile() throws Exception{
        //1.向工程中添加jar包
        //2.创建一个配置文件。配置tracker服务器地址
        //3.加载配置文件
        ClientGlobal.init("E:\\workspace\\taotao\\manager-web\\src\\test\\resources\\client.conf");
        //4.创建一个TrackerClient对象。
        TrackerClient trackerClient = new TrackerClient();
        //5.使用TrackerClient对象获得trackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //6.创建一个StorageServer的引用null就可以
        StorageServer storageServer = null;
        //7.创建一个StorageClient对象。trackerServer,storageServer两个参数。
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //8.使用StorageClient对象上传文件
        String[] strings = storageClient.upload_file("D:\\纳车品\\奇真BSM详情页加主图\\详情页\\详情页.jpg","jpg",null);
        for(String string : strings){
            System.out.println(string);
        }
    }

    public void testFastDfsClient() throws Exception{
        FastDFSClient fastDFSClient = new FastDFSClient("E:\\workspace\\taotao\\manager-web\\src\\test\\resources\\client.conf");
        fastDFSClient.uploadFile("D:\\纳车品\\奇真BSM详情页加主图\\详情页\\详情页.jpg");
    }
}
