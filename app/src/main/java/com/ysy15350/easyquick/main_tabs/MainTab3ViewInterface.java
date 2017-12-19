package com.ysy15350.easyquick.main_tabs;

import api.base.model.Response;

public interface MainTab3ViewInterface {

    public void micro_payCallback(boolean isCache, Response response);

    public void user_infoCallback(boolean isCache, Response response);

    public void adlistCallback(boolean isCache, Response response);

    public void checkUserinfoCallback(boolean isCache, Response response);

}
