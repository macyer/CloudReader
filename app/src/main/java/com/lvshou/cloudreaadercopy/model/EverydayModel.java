package com.lvshou.cloudreaadercopy.model;

import com.lvshou.cloudreaadercopy.bean.GankIoDayBean;
import com.lvshou.cloudreaadercopy.http.HttpClient;
import com.lvshou.cloudreaadercopy.http.RequestImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
    
    public void getRecyclerData(RequestImpl request){
        Disposable disposable = HttpClient.Builder
                .getGankIoServer()
                .getGankIoDay(year,month,day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankIoDayBean>() {
                    @Override
                    public void accept(GankIoDayBean gankIoDayBean) throws Exception {
                        request.loadSuccess(gankIoDayBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        request.loadFail(throwable);
                    }
                });
        request.addDisposable(disposable);
        
    }
}
