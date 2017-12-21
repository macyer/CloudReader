package com.macyer.cloudreaadercopy.http;

import io.reactivex.disposables.Disposable;

/**
 * Created by Lenovo on 2017/12/5.
 */

public interface RequestImpl<T> {
    
    void loadSuccess(T t);
    
    void loadFail(Throwable throwable);
    
    void disposable(Disposable disposable);
    
}
