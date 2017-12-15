package fitnessroom.hxs.com.sharesdk;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareUtil {

    private Context mContext;
    /**
     * title   分享title
     */
    private String title;
    /**
     * 分享内容
     */
    private String content;
    /**
     * 分享图片链接
     */
    private String iamgePath;
    /**
     * 是否展示拷贝链接按钮
     */
    private boolean isShowCopyUrl;

    public ShareUtil(Context context) {
        this.mContext = context;

    }

    /**
     * @param title 分享title
     */
    public ShareUtil setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @param content 分享内容
     */
    public ShareUtil setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * @param iamgePath 分享图片
     */
    public ShareUtil setIamgePath(String iamgePath) {
        this.iamgePath = iamgePath;
        return this;
    }

    /**
     * @param isShowCopyUrl 是否展示拷贝链接按钮
     */
    public ShareUtil setShowCopyUrl(boolean isShowCopyUrl) {
        this.isShowCopyUrl = isShowCopyUrl;
        return this;
    }


    /**
     * 分享
     *
     * @param url 分享连接
     */
    public void showShare(String url) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        if (!TextUtils.isEmpty(url) && isShowCopyUrl) {
            Bitmap enableLogo = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ssdk_oks_classic_alipay);
            String label = "复制链接";
            final String finalUrl = url;
            View.OnClickListener listener = new View.OnClickListener() {
                public void onClick(View v) {
                    // 从API11开始android推荐使用android.content.ClipboardManager
                    // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(finalUrl);
                    Toast.makeText(mContext, "复制链接成功", Toast.LENGTH_LONG).show();
                }
            };
            oks.setCustomerLogo(enableLogo, label, listener);
        }
        // 自定义回调
        // oks.setCallback(this);
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        if (!TextUtils.isEmpty(title)) {
            oks.setTitle(title);
        } else {
            oks.setTitle(" ");
        }
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段

        if (TextUtils.isEmpty(content)) {
            content = "点击查看详情";
        }
        oks.setText(content);
        //差异化分享用于oks区分平台不同参数不同
        final String finalContent = content;
        final String urlTemp = url;
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (SinaWeibo.NAME.equals(platform.getName())) {
                    if (finalContent.length() >= 20) {
                        String lastContent = finalContent.substring(0, 20);
                        paramsToShare.setText(lastContent + urlTemp);
                    } else {
                        paramsToShare.setText(finalContent + urlTemp);
                    }
                }
            }
        });
        // imagePath是图片的url，Linked-In以外的平台都支持此参数
        if (TextUtils.isEmpty(iamgePath)) {
            oks.setImagePath(initImagePath());
        } else {
            oks.setImageUrl(iamgePath);
        }
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // oks.setComment("评论");
        //TODO
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(mContext.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        // oks.setSiteUrl(initImagePath());
        //分享回调监听
        oks.setCallback(new MyPlatformActionListener());
        // 启动分享GUI
        oks.show(mContext);
    }

    /**
     * 获取路径
     */
    private String initImagePath() {
        File file = new File(mContext.getCacheDir(), "icon.png");
        try {
            if (!file.exists()) {
                file.createNewFile();
                Bitmap pic = BitmapFactory.decodeResource(
                        mContext.getResources(), R.drawable.ssdk_logo);
                FileOutputStream fos = new FileOutputStream(file);
                pic.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }
            return file.getAbsolutePath();
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    private class MyPlatformActionListener implements PlatformActionListener {

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            String mPlatform = platform.getName();
            // 成功
            int resId = ResHelper.getStringRes(mContext, "ssdk_oks_share_completed");
            if ("wechat.friends".contains(mPlatform)) {
                mPlatform = "10";
            } else if ("wechat.moments".contains(mPlatform)) {
                mPlatform = "20";
            } else if ("tencent.qq.QQ".contains(mPlatform)) {
                mPlatform = "30";
            } else if ("tencent.qzone.QZone".contains(mPlatform)) {
                mPlatform = "40";
            } else {
                mPlatform = "50";
            }
            toast(mContext.getString(resId));
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            // 失败
            String expName = throwable.getClass().getSimpleName();
            if ("WechatClientNotExistException".equals(expName)
                    || "WechatTimelineNotSupportedException".equals(expName)
                    || "WechatFavoriteNotSupportedException".equals(expName)) {
                toast("ssdk_wechat_client_inavailable");
            } else if ("GooglePlusClientNotExistException".equals(expName)) {
                toast("ssdk_google_plus_client_inavailable");
            } else if ("QQClientNotExistException".equals(expName)) {
                toast("ssdk_qq_client_inavailable");
            } else if ("YixinClientNotExistException".equals(expName)
                    || "YixinTimelineNotSupportedException".equals(expName)) {
                toast("ssdk_yixin_client_inavailable");
            } else if ("KakaoTalkClientNotExistException".equals(expName)) {
                toast("ssdk_kakaotalk_client_inavailable");
            } else if ("KakaoStoryClientNotExistException".equals(expName)) {
                toast("ssdk_kakaostory_client_inavailable");
            } else if ("WhatsAppClientNotExistException".equals(expName)) {
                toast("ssdk_whatsapp_client_inavailable");
            } else if ("FacebookMessengerClientNotExistException".equals(expName)) {
                toast("ssdk_facebookmessenger_client_inavailable");
            } else {
                toast("ssdk_oks_share_failed");
            }
        }

        @Override
        public void onCancel(Platform platform, int i) {
            // 取消
            toast("ssdk_oks_share_canceled");
        }
    }


    private void toast(final String resOrName) {
        UIHandler.sendEmptyMessage(0, new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                int resId = ResHelper.getStringRes(mContext, resOrName);
                if (resId > 0) {
                    Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, resOrName, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }


    /**
     * 第三方登陆授权
     *
     * @param name          授权类型名
     * @param loginCallBack 结果回调
     * @see WechatMoments#NAME
     * @see QQ#NAME
     */
    public static void thirdPartylogin(String name, final LoginCallBack loginCallBack) {
        final Platform _platform = ShareSDK.getPlatform(name);
//        _platform.SSOSetting(true);  //设置false表示使用SSO授权方式
        /**
         * 由于回调是非主程，所以要用handler切换到主线程
         */
        final Handler handle = new Handler();
        _platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        loginCallBack.onComplete(_platform.getDb());
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        loginCallBack.onComplete(null);
                    }
                });
            }

            @Override
            public void onCancel(Platform platform, int i) {
                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        loginCallBack.onComplete(null);
                    }
                });
            }
        }); // 设置分享事件回调

        _platform.authorize();//单独授权
    }

    /**
     * 第三方登录授权回调接口
     */
    public interface LoginCallBack {
        /**
         * 回调
         *
         * @param platformDb 如果为空，为授权失败
         */
        void onComplete(@Nullable PlatformDb platformDb);
    }
}
