package com.ysy15350.easyquick.mine;

import api.base.model.Response;

public interface MyInfoViewInterface {


    public void store_infoCallback(boolean isCache, Response response);

    public void save_infoCallback(boolean isCache, Response response);

    public void store_save_infoCallback(boolean isCache, Response response);

    /**
     * 上传头像回调
     *
     * @param response
     */
    public void uppictureCallback(Response response);

}
