package com.example.sonamessteetenzin.perfectplate;

/**
 * Created by Sonam ESSTEE Tenzin on 4/13/2018.
 */


        import android.content.Context;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    //constructor
    private MySingleton(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized MySingleton getmInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    //method for adding requestQueue
    public <T>void addtoRequestqueue(Request<T> request)
    {
        requestQueue.add(request);
    }

}
