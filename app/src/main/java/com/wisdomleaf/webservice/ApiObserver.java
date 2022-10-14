package com.wisdomleaf.webservice;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.wisdomleaf.mvvm.model.APIError;


public class ApiObserver<T> implements Observer<DataWrapper<T>> {

    private ChangeListener<T> mChangeListener;
    private static final int ERROR_CODE = 0;

    public ApiObserver(ChangeListener<T> mChangeListener) {
        this.mChangeListener = mChangeListener;
    }

    @Override
    public void onChanged(@Nullable DataWrapper<T> mDataWrapper) {
        if (mDataWrapper != null) {
            if (mDataWrapper.getApiError() != null) {
                mChangeListener.onError(mDataWrapper.getApiError());
            } else {
                mChangeListener.onSuccess(mDataWrapper.getData());
            }
        }
    }

    public interface ChangeListener<T> {
        void onSuccess(T dataWrapper);
        void onError(APIError error);
    }
}