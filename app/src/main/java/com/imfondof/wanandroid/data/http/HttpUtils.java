package com.imfondof.wanandroid.data.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.imfondof.wanandroid.BuildConfig;
import com.imfondof.wanandroid.other.utils.CheckNetwork;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtils {
    public final static int WAN_PAGE_SIZE = 20;//wanandroid每页加载的数据有20条，用于判断有没有更多数据

    public final static String API_WAN_ANDROID = "https://www.wanandroid.com/";
    public final static String API_GANK = "https://gank.io/api/v2/";

    private static HttpUtils instance;
    private static Context context;

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    private OkHttpClient getOkClient() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.readTimeout(30, TimeUnit.SECONDS);
        okBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okBuilder.writeTimeout(30, TimeUnit.SECONDS);
        okBuilder.addInterceptor(new HttpHeadInterceptor());
        // 持久化cookie
        okBuilder.addInterceptor(new HttpUtils.AddCookiesInterceptor(context)); //cookie持久化 非首次请求
        okBuilder.addInterceptor(new HttpUtils.ReceivedCookiesInterceptor(context)); //cookie持久化 首次请求

        // 添加缓存，无网访问时会拿缓存,只会缓存get请求
        //cache url
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        // 50 MiB
        int cacheSize = 50 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        okBuilder.addInterceptor(new AddCacheInterceptor(context));
        okBuilder.cache(cache);
        okBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE));
        return okBuilder.build();
    }

    public Retrofit.Builder getBuilder(String apiUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(getOkClient())
                .addConverterFactory(GsonConverterFactory.create())// 设置数据解析器
                .baseUrl(apiUrl);//设置远程地址
        return builder;
    }

    private Object wanAndroidHttps;

    public <T> T create(Class<T> a, String type) {
        if (wanAndroidHttps == null) {
            synchronized (BuildFactory.class) {
                if (wanAndroidHttps == null) {
                    wanAndroidHttps = getInstance()
                            .getBuilder(type)
                            .build()
                            .create(a);
                }
            }
        }
        return (T) wanAndroidHttps;
    }

    private class HttpHeadInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();
            builder.addHeader("Accept", "application/json;versions=1");
            if (CheckNetwork.isNetworkConnected(context)) {
                int maxAge = 60;
                builder.addHeader("Cache-Control", "public, max-age=" + maxAge);
            } else {
                int maxStale = 60 * 60 * 24 * 28;
                builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
            return chain.proceed(builder.build());
        }
    }

    private class AddCacheInterceptor implements Interceptor {
        private Context context;

        AddCacheInterceptor(Context context) {
            super();
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();
            Request request = chain.request();
            if (!CheckNetwork.isNetworkConnected(context)) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (CheckNetwork.isNetworkConnected(context)) {
                // read from cache
                int maxAge = 0;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                // tolerate 4-weeks stale
                int maxStale = 60 * 60 * 24 * 28;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }

    public static class ReceivedCookiesInterceptor implements Interceptor {
        private Context context;

        ReceivedCookiesInterceptor(Context context) {
            super();
            this.context = context;

        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            //这里获取请求返回的cookie
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                List<String> d = originalResponse.headers("Set-Cookie");
                // 返回cookie
                if (!TextUtils.isEmpty(d.toString())) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorConfig = sharedPreferences.edit();
                    String oldCookie = sharedPreferences.getString("cookie", "");

                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    // 之前存过cookie
                    if (!TextUtils.isEmpty(oldCookie)) {
                        String[] substring = oldCookie.split(";");
                        for (String aSubstring : substring) {
                            if (aSubstring.contains("=")) {
                                String[] split = aSubstring.split("=");
                                stringStringHashMap.put(split[0], split[1]);
                            } else {
                                stringStringHashMap.put(aSubstring, "");
                            }
                        }
                    }
                    String join = StringUtils.join(d, ";");
                    String[] split = join.split(";");

                    // 存到Map里
                    for (String aSplit : split) {
                        String[] split1 = aSplit.split("=");
                        if (split1.length == 2) {
                            stringStringHashMap.put(split1[0], split1[1]);
                        } else {
                            stringStringHashMap.put(split1[0], "");
                        }
                    }

                    // 取出来
                    StringBuilder stringBuilder = new StringBuilder();
                    if (stringStringHashMap.size() > 0) {
                        for (String key : stringStringHashMap.keySet()) {
                            stringBuilder.append(key);
                            String value = stringStringHashMap.get(key);
                            if (!TextUtils.isEmpty(value)) {
                                stringBuilder.append("=");
                                stringBuilder.append(value);
                            }
                            stringBuilder.append(";");
                        }
                    }
                    editorConfig.putString("cookie", stringBuilder.toString());
                    editorConfig.apply();
                }
            }

            return originalResponse;
        }
    }

    public static class AddCookiesInterceptor implements Interceptor {
        private Context context;

        AddCookiesInterceptor(Context context) {
            super();
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request.Builder builder = chain.request().newBuilder();
            SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
            String cookie = sharedPreferences.getString("cookie", "");
            builder.addHeader("Cookie", cookie);
            return chain.proceed(builder.build());
        }
    }
}
