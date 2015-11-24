package com.kaskys.speedreadinginformation.app.presenter.impl;

import android.content.Context;
import com.kaskys.speedreadinginformation.app.interactor.PictureTypeContainerInteractor;
import com.kaskys.speedreadinginformation.app.interactor.impl.PictureTypeContainerInteractorImpl;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.ui.view.PictureView;

/**
 * Created by 卡你基巴 on 2015/11/21.
 */
public class PictureTypeContainerPresenterImpl implements Presenter{
    private Context mContext;
    private PictureView mPictureView;
    private PictureTypeContainerInteractor mPictureTypeContainerInteractor;

    public PictureTypeContainerPresenterImpl(Context context, PictureView pictureView){
        if(null == pictureView){
            throw new IllegalArgumentException("Presenter must not be Null");
        }
        mContext = context;
        mPictureView = pictureView;
        mPictureTypeContainerInteractor = new PictureTypeContainerInteractorImpl();
    }

    @Override
    public void initialized() {
        mPictureView.initializePagerViews(mPictureTypeContainerInteractor.initializePagerViews(mContext));
    }
}
