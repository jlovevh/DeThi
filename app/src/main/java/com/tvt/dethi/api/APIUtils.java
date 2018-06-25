package com.tvt.dethi.api;

import com.tvt.dethi.utils.Const;

public class APIUtils {


    public  static JsonReponse getAPIClient(){
        return RetrofitClient.getClient(Const.BASE_URL).create(JsonReponse.class);
    }
}
