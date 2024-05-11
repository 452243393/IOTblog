package com.yj.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yj.exception.SystemException;
import com.yj.service.UploadService;
import com.yj.utils.AppHttpCodeEnum;
import com.yj.utils.PageUtils;
import com.yj.utils.PathUtils;
import com.yj.utils.ResponseResult;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
@Component
@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    private String accessKey;
    private String secretKey;
    private String bucket;
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件类型
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对原始文件名进行判断
        if(!originalFilename.endsWith(".png")&&!originalFilename.endsWith(".jpg")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //如果判断通过，文件上传到oss
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img,filePath);//名称定义：年/月/日/XXX.png或者XXX.jpg
        return ResponseResult.successResult(url);
    }
    private String uploadOss(MultipartFile imgFile,String filePath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
//        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken,null,null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return "http://s6l6h2kz4.bkt.clouddn.com/"+key;
            } catch (QiniuException ex) {
               Response r = ex.response;
                    try {
                        System.err.println(r.bodyString());
                    } catch (QiniuException ex2) {
                    }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "www";
    }
}
