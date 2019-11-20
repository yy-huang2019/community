package com.isoft.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import com.isoft.community.exception.CustomizeErrorCode;
import com.isoft.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.UUID;

@Service
public class UCloudProvider {
    @Value("${ucloud.ufile.public-key}")
    private String public_key;
    @Value("${ucloud.ufile.private-key}")
    private String private_key;
    @Value("${ucloud.ufile.bucket-name}")
    private String bucketName;
    @Value("${ucloud.ufile.region}")
    private String region;
    @Value("${ucloud.ufile.proxySuffix}")
    private String proxySuffix;
    @Value("${ucloud.ufile.expiresDuration}")
    private Integer expiresDuration;

    //上传文件
    public String upload(InputStream fileStream,String mimeType ,String fileName){
        // 对象相关API的授权器
        ObjectAuthorization object_authorize = new UfileObjectLocalAuthorization(
                public_key, private_key);
        ObjectConfig config = new ObjectConfig(region,proxySuffix);

        String generateFileName;
        String[] filePaths = fileName.split("\\.");    // \\表示转义
        if(filePaths.length > 1){
            //设置上传名称
            generateFileName = UUID.randomUUID().toString()+ "." +filePaths[filePaths.length-1] ;
        }else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }

        try {
            PutObjectResultBean response = UfileClient.object(object_authorize, config)
                    //通过流的方式拿到该file
                    .putObject(fileStream, mimeType)
                    .nameAs(generateFileName)
                    .toBucket(bucketName)
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })
                    .execute();

                    if(response != null && response.getRetCode() == 0){
                        //通过ucloud的github找到相应的得到url方法
                        String url = UfileClient.object(object_authorize,config)
                                .getDownloadUrlFromPrivateBucket(generateFileName,bucketName,315360000)
                                .createUrl();
                        return url;           //通过UCloud上传得到ucloud的上传地址
                    }else {
                        throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
                    }

        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
    }
}
