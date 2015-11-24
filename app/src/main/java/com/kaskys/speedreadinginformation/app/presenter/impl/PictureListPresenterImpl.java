package com.kaskys.speedreadinginformation.app.presenter.impl;

import android.content.Context;
import com.android.volley.VolleyError;
import com.kaskys.speedreadinginformation.app.bean.PictureData;
import com.kaskys.speedreadinginformation.app.interactor.PictureListInteractor;
import com.kaskys.speedreadinginformation.app.interactor.impl.PictureListInteractorImpl;
import com.kaskys.speedreadinginformation.app.listeners.OnRequestListener;
import com.kaskys.speedreadinginformation.app.presenter.DataListPresenter;
import com.kaskys.speedreadinginformation.app.presenter.Presenter;
import com.kaskys.speedreadinginformation.app.ui.fragment.child.PictureListFragment;
import com.kaskys.speedreadinginformation.app.ui.view.PictureListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 卡你基巴 on 2015/11/21.
 */
public class PictureListPresenterImpl implements DataListPresenter,OnRequestListener<PictureData>{
    private Context mContext;
    private PictureListView mPictureListView;
    private PictureListInteractor mPictureListInteractor;

    public PictureListPresenterImpl(Context context, PictureListView pictureListView){
        if(null == pictureListView){
            throw new IllegalArgumentException("Presenter must not be Null");
        }
        mContext = context;
        mPictureListView = pictureListView;
        mPictureListInteractor = new PictureListInteractorImpl(mContext,this);
    }

    @Override
    public void initialized(String id, int page) {
        mPictureListInteractor.getPictureDataFromNetwork(id,page,false);
    }

    @Override
    public void loadMore(String id, int page) {
        mPictureListInteractor.getPictureDataFromNetwork(id,page,true);
    }

    @Override
    public void onSuccess(PictureData data) {
        if(null != data.showapi_res_body){
            List<PictureData.Body.Detail> pictures = tidyData(data);
            mPictureListView.setupData(pictures);
        }
    }

    @Override
    public void onLoadMore(PictureData data) {

    }

    @Override
    public void onError(VolleyError volleyError) {
        mPictureListView.setupData(null);
    }

    private List<PictureData.Body.Detail> tidyData(PictureData data) {
        List<PictureData.Body.Detail> pictures = new ArrayList<PictureData.Body.Detail>();
        pictures.add(data.showapi_res_body.bean0);
        pictures.add(data.showapi_res_body.bean1);
        pictures.add(data.showapi_res_body.bean2);
        pictures.add(data.showapi_res_body.bean3);
        pictures.add(data.showapi_res_body.bean4);
        pictures.add(data.showapi_res_body.bean5);
        pictures.add(data.showapi_res_body.bean6);
        pictures.add(data.showapi_res_body.bean7);
        pictures.add(data.showapi_res_body.bean8);
        pictures.add(data.showapi_res_body.bean9);
        return pictures;
    }
}
