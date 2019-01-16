package com.hzy.baidufacedetect;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 人脸检测与属性分析
 */
public class FaceDetect {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String detect(byte[] bt) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", Base64Util.encode(bt));//将byte[]图片转化为BASE64
            map.put("image_type", "BASE64");
            /**
             * 此处只要微笑程度，具体需要参数，请参阅
             * http://ai.baidu.com/docs#/Face-Detect-V3/top
             * 内的face_field参数
             */
            map.put("face_field", "expression");
            String param = GsonUtils.toJson(map);//map转json
            String accessToken = AuthService.getAuth();//获取accessToken


            String result = HttpUtil.post(url, accessToken, "application/json", param);
            Log.e("FaceDetect--result", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}