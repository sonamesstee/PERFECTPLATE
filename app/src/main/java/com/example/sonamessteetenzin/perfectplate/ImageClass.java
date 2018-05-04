package com.example.sonamessteetenzin.perfectplate;

import com.google.gson.annotations.SerializedName;


    /**
     * Created by Sonam ESSTEE Tenzin on 4/10/2018.
     */
    public class ImageClass {

        @SerializedName("title")
        private String Title;

        @SerializedName("image")
        private String Image;

        @SerializedName("response")
        private String Response;

        public String getResponse() {
            return Response;
        }

    }


