package com.jqz.umengproject;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Map;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt;
    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */


        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MainActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };



    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();
            Set<Map.Entry<String, String>> entries = data.entrySet();
            for (Map.Entry entry:entries){
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                Log.i("111", "onComplete: key:"+key+",value:"+value);
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(MainActivity.this, "失败：" + t.getMessage(),                                     Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    private Button bt_umeng;
    private Button bt_weibo;
    private Button bt_weixin;
    private Button bt_QQ;
    private Button bt_loginWeb;
    private Button bt_loginweChat;
    private Button bt_loginQQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkPremission();
    }

    private void checkPremission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }

    //QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加：
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }

    private void initView() {
        bt = (Button) findViewById(R.id.bt_umeng);

        bt.setOnClickListener(this);
        bt_umeng = (Button) findViewById(R.id.bt_umeng);
        bt_umeng.setOnClickListener(this);
        bt_weibo = (Button) findViewById(R.id.bt_weibo);
        bt_weibo.setOnClickListener(this);
        bt_weixin = (Button) findViewById(R.id.bt_weixin);
        bt_weixin.setOnClickListener(this);
        bt_QQ = (Button) findViewById(R.id.bt_QQ);
        bt_QQ.setOnClickListener(this);
        bt_loginWeb = (Button) findViewById(R.id.bt_loginWeb);
        bt_loginWeb.setOnClickListener(this);
        bt_loginweChat = (Button) findViewById(R.id.bt_loginweChat);
        bt_loginweChat.setOnClickListener(this);
        bt_loginQQ = (Button) findViewById(R.id.bt_loginQQ);
        bt_loginQQ.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_umeng://点击按钮打开分享面板的代码如下：
                new ShareAction(MainActivity.this).withText("你好我是通过靳前志开发的软件发送的微博内容").setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener).open();
                break;

            case R.id.bt_weibo://不带面板
//                new ShareAction(MainActivity.this)
//                        .setPlatform(SHARE_MEDIA.SINA)//传入平台
//                        .withText("hello")//分享内容
//                        .setCallback(umShareListener)//回调监听器
//                        .share();
                shareMySelf(SHARE_MEDIA.SINA);//分享图片到微博
                break;
            case R.id.bt_weixin:
//                new ShareAction(MainActivity.this)
//                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
//                        .withText("hello")//分享内容
//                        .setCallback(umShareListener)//回调监听器
//                        .share();
                shareMySelf(SHARE_MEDIA.WEIXIN);//分享图片到微信
                break;
            case R.id.bt_QQ:
//                new ShareAction(MainActivity.this)
//                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
//                        .withText("hello")//分享内容
//                        .setCallback(umShareListener)//回调监听器
//                        .share();
                shareMySelf(SHARE_MEDIA.QQ);//分享图片到QQ
                break;
            case R.id.bt_loginWeb:
                login(SHARE_MEDIA.SINA);
                break;
            case R.id.bt_loginweChat:
                login(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.bt_loginQQ:
                login(SHARE_MEDIA.QQ);

                break;
        }
    }

    private void login(SHARE_MEDIA sina) {
        UMShareAPI mShareAPI = UMShareAPI.get(this);
        mShareAPI.getPlatformInfo(MainActivity.this, sina, authListener);
    }


    /**
     * 单个
     *
     * @param type
     */
    private void shareMySelf(SHARE_MEDIA type) {
        UMImage image = new UMImage(MainActivity.this, "http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1ykabxj30k00pracv.jpg");//网络图片
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        new ShareAction(MainActivity.this)
                .setPlatform(type)//传入平台
                .withText("hello 1909B 新")//分享内容
                .withMedia(image)//分享的图片
                .setCallback(umShareListener)//回调监听器
                .share();
    }


    /**
     * 带面板的分享：一次包含多个分享平台  此处只包含 微博 QQ 微信
     */
    private void shareByBox() {
        UMImage image = new UMImage(MainActivity.this, "http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1ykabxj30k00pracv.jpg");//网络图片
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        new ShareAction(MainActivity.this)
                .withText("hello 1909B")
                .withMedia(image)//分享图片
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener).open();
    }
}
