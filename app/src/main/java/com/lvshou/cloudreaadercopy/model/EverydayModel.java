package com.lvshou.cloudreaadercopy.model;

import com.lvshou.cloudreaadercopy.app.ConstantsImageUrl;
import com.lvshou.cloudreaadercopy.bean.AndroidBean;
import com.lvshou.cloudreaadercopy.bean.FrontpageBean;
import com.lvshou.cloudreaadercopy.http.HttpClient;
import com.lvshou.cloudreaadercopy.http.RequestImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import com.lvshou.cloudreaadercopy.bean.FrontpageBean.ResultBeanXXXXXXXXXXXXXX.FocusBean.ResultBeanX;

/**
 * Created by Lenovo on 2017/12/5.
 */

public class EverydayModel {

    private String year = "2016";
    private String month = "11";
    private String day = "24";
    private static final String HOME_ONE = "home_one";
    private static final String HOME_TWO = "home_two";
    private static final String HOME_SIX = "home_six";

    public void setData(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void getBannerData(RequestImpl request) {
        Disposable disposable = HttpClient.Builder.getTingIoServer().getFrontpage()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map((Function<FrontpageBean, List<ResultBeanX>>) bean -> {
                    if (null != bean && null != bean.getResult() && null != bean.getResult().getFocus() && null != bean.getResult().getFocus().getResult()) {
                        return bean.getResult().getFocus().getResult();
                    }
                    return new ArrayList<>();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> {
                    if (strings.size() > 0) request.loadSuccess(strings);
                    else request.loadFail(new Throwable("size = 0"));
                }, throwable -> request.loadFail(throwable));
        request.disposable(disposable);
    }

    public void getRecyclerData(RequestImpl request) {
        Disposable disposable = HttpClient.Builder
                .getGankIoServer()
                .getGankIoDay(year, month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(gankIoDayBean -> {
                    List<List<AndroidBean>> lists = new ArrayList<>();
                    if (!gankIoDayBean.isError() && gankIoDayBean.getCategory() != null && gankIoDayBean.getCategory().size() > 0) {
                        for (String title : gankIoDayBean.getCategory()) {
                            List<AndroidBean> list = gankIoDayBean.getCategoryList(title);
                            if (gankIoDayBean.getCategoryList(title) != null && gankIoDayBean.getCategoryList(title).size() > 0) {
                                //没有将size大于3的集合拆分，另外需要添加图片
//                                    AndroidBean bean = new AndroidBean();
//                                    bean.setType_title(title);
//                                    ArrayList<AndroidBean> androidBeen = new ArrayList<>();
//                                    androidBeen.add(bean);
//                                    lists.add(androidBeen);
//                                    lists.add(list);
                                //=========================
                                addUrlList(lists, list, title);
                            }
                        }
                    }
                    return lists;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(arrayLists -> request.loadSuccess(arrayLists), 
                        throwable -> request.loadFail(throwable));
        request.disposable(disposable);
    }

    // subList没有实现序列化！缓存时会出错！
    private void addUrlList(List<List<AndroidBean>> lists, List<AndroidBean> arrayList, String typeTitle) {
        // title
        AndroidBean bean = new AndroidBean();
        bean.setType_title(typeTitle);
        ArrayList<AndroidBean> androidBeen = new ArrayList<>();
        androidBeen.add(bean);
        lists.add(androidBeen);

        int androidSize = arrayList.size();
        //小鱼三个
        if (androidSize > 0 && androidSize < 4) {
            lists.add(addUrlList(arrayList, androidSize));
        } else if (androidSize >= 4) {
            //大约三个分成两个集合
            ArrayList<AndroidBean> list1 = new ArrayList<>();
            ArrayList<AndroidBean> list2 = new ArrayList<>();

            for (int i = 0; i < androidSize; i++) {
                if (i < 3) {
                    list1.add(getAndroidBean(arrayList, i, androidSize));
                } else if (i < 6) {
                    list2.add(getAndroidBean(arrayList, i, androidSize));
                }
            }
            lists.add(list1);
            lists.add(list2);
        }
    }

    private AndroidBean getAndroidBean(List<AndroidBean> arrayList, int i, int androidSize) {

        AndroidBean androidBean = new AndroidBean();
        // 标题
        androidBean.setDesc(arrayList.get(i).getDesc());
        // 类型
        androidBean.setType(arrayList.get(i).getType());
        // 跳转链接
        androidBean.setUrl(arrayList.get(i).getUrl());
        // 随机图的url
        if (i < 3) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_SIX_URLS[getRandom(3)]);//三小图
        } else if (androidSize == 4) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_ONE_URLS[getRandom(1)]);//一图
        } else if (androidSize == 5) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_TWO_URLS[getRandom(2)]);//两图
        } else if (androidSize >= 6) {
            androidBean.setImage_url(ConstantsImageUrl.HOME_SIX_URLS[getRandom(3)]);//三小图
        }
        return androidBean;
    }


    private List<AndroidBean> addUrlList(List<AndroidBean> arrayList, int androidSize) {
        List<AndroidBean> tempList = new ArrayList<>();
        for (int i = 0; i < androidSize; i++) {
            AndroidBean androidBean = new AndroidBean();
            // 标题
            androidBean.setDesc(arrayList.get(i).getDesc());
            // 类型
            androidBean.setType(arrayList.get(i).getType());
            // 跳转链接
            androidBean.setUrl(arrayList.get(i).getUrl());
//            DebugUtil.error("---androidSize:  " + androidSize);
            // 随机图的url
            if (androidSize == 1) {
                androidBean.setImage_url(ConstantsImageUrl.HOME_ONE_URLS[getRandom(1)]);//一图
            } else if (androidSize == 2) {
                androidBean.setImage_url(ConstantsImageUrl.HOME_TWO_URLS[getRandom(2)]);//两图
            } else if (androidSize == 3) {
                androidBean.setImage_url(ConstantsImageUrl.HOME_SIX_URLS[getRandom(3)]);//三图
            }
            tempList.add(androidBean);
        }
        return tempList;
    }

    /**
     * 取不同的随机图，在每次网络请求时重置
     */
    private int getRandom(int type) {
        int urlLength = 0;
        if (type == 1) {
            urlLength = ConstantsImageUrl.HOME_ONE_URLS.length;
        } else if (type == 2) {
            urlLength = ConstantsImageUrl.HOME_TWO_URLS.length;
        } else if (type == 3) {
            urlLength = ConstantsImageUrl.HOME_SIX_URLS.length;
        }
        Random random = new Random();
        int randomInt = random.nextInt(urlLength);
        return randomInt;
    }
}
