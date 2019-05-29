package com.cpx.utils;

import java.io.FileInputStream;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;


public class QiniuyunUtils {
	
	
		private static Configuration cfg = null;
		private static Auth auth = null;
		
		//...生成上传凭证，然后准备上传
		private static String accessKey = "fkARWt7Hfzi6FjcJn5B1AikaQkZzQWW1uhkwKCoP";
		private static String secretKey = "IPJUyFAHgx4m6r-RLOGol298AuE_HQXQc5p-haR6";
		private static String bucket = "video-yun-file-001";
	
		static {
			//构造一个带指定Zone对象的配置类
			 cfg = new Configuration(Zone.zone1());
			 auth = Auth.create(accessKey, secretKey);
		}
		
		/**
		 * <pre>uploadFile(上传文件)   
		 * 创建人：李泽兴 lizexing@163.com      
		 * 创建时间：2019年4月25日 下午4:58:06    
		 * 修改人：李泽兴 lizexing@163.com       
		 * 修改时间：2019年4月25日 下午4:58:06    
		 * 修改备注： 
		 * @param fileInputStream
		 * @param imageName
		 * @return</pre>
		 */
		public static String uploadFile(FileInputStream fileInputStream,String imageName) {
			
			//...其他参数参考类注释
			UploadManager uploadManager = new UploadManager(cfg);
			
			String upToken = auth.uploadToken(bucket);
			try {
			    Response response = uploadManager.put(fileInputStream, imageName, upToken,null,null);
			    //解析上传成功的结果
			    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			    System.out.println(putRet.key);
			    System.out.println(putRet.hash);
			    return putRet.key;
			} catch (QiniuException ex) {
			    Response r = ex.response;
			    System.err.println(r.toString());
			    try {
			        System.err.println(r.bodyString());
			    } catch (QiniuException ex2) {
			        //ignore
			    }
			}
			return null;
		}
		
		
		public static void deleteFile(String imageName) {
			//构造一个带指定Zone对象的配置类
			Configuration cfg = new Configuration(Zone.zone0());
			
			Auth auth = Auth.create(accessKey, secretKey);
			BucketManager bucketManager = new BucketManager(auth, cfg);
			try {
			    bucketManager.delete(bucket, imageName);
			} catch (QiniuException ex) {
			    //如果遇到异常，说明删除失败
			    System.err.println(ex.code());
			    System.err.println(ex.response.toString());
			}
		}
	

		public static void main(String[] args) throws Exception {
			//构造一个带指定Zone对象的配置类
			Configuration cfg = new Configuration(Zone.zone1());
			//...其他参数参考类注释
			UploadManager uploadManager = new UploadManager(cfg);
			//...生成上传凭证，然后准备上传
			String accessKey = "fkARWt7Hfzi6FjcJn5B1AikaQkZzQWW1uhkwKCoP";
			String secretKey = "IPJUyFAHgx4m6r-RLOGol298AuE_HQXQc5p-haR6";
			String bucket = "video-yun-file-001";
			//如果是Windows情况下，格式是 D:\\qiniu\\test.png
			String localFilePath = "/home/qiniu/test.png";
			//默认不指定key的情况下，以文件内容的hash值作为文件名
			String key = "1.png";
			Auth auth = Auth.create(accessKey, secretKey);
			String upToken = auth.uploadToken(bucket);
			try {
				FileInputStream fileInputStream = new FileInputStream("C:\\Users\\lizexing\\Desktop\\作息时间.png");
			    Response response = uploadManager.put(fileInputStream, key, upToken,null,null);
			    //解析上传成功的结果
			    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			    System.out.println(putRet.key);
			    System.out.println(putRet.hash);
			} catch (QiniuException ex) {
			    Response r = ex.response;
			    System.err.println(r.toString());
			    try {
			        System.err.println(r.bodyString());
			    } catch (QiniuException ex2) {
			        //ignore
			    }
			}
		}
	
}
