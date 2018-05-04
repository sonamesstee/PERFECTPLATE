package com.example.sonamessteetenzin.perfectplate;

import retrofit.http.FormUrlEncoded;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Field;

/**
 * Created by Sonam ESSTEE Tenzin on 4/10/2018.
 */

public interface ApiInterface {

    @FormUrlEncoded

    @POST("upload.php")

    Call<ImageClass> uploadImage(@Field("title") String title, @Field("image") String image);
}
