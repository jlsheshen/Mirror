package com.mirroreye.mirror.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ${jl} on 16/6/14.
 */
public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;//OkHttp的管理者
    private OkHttpClient mOkHttpClient;//OkHttp对象
    private Handler mDelivery;//给UI发送消息的Handler
    private Gson mGson;

    private static final String TAG = "OkHttpClientManager";

    /**
     * OkHttplientManger的方法
     */
    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();//new一个OkHttp的对象
        //http来获取网页上的Json值,对于一般的网站来说都会有cookie检测
        // 如果你的cookie未被检测到，会被服务器认为是不合法的访问，相应的客服端不会取得值。
        //cookie enable 可用的网页缓存
        //创建一个Handler来读相应的cookie的输出流和输入流
        //CookieManager的构造方法的参数:
        //第一个参数:CookieStore 当等于null时用系统默认的
        //第二个参数:网页缓存的策略
        //有三种策略:ACCEPT_ALL 接收所有的网页缓存
        //ACCEPT_NONE:不再接收网页缓存
        //ACCEPT_ORIGINAL_SERVER:只接受服务器的网页缓存
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        //线程分为有消息循环的和没有消息循环的线程，有消息循环的线程一般都会有一个Looper
        //我们的主线程（UI线程）就是一个消息循环的线程
        //Looper.getMainLooper() 获得UI线程的Lopper
        //Handle的初始化函数,如果没有参数，就默认使用的是当前的Looper
        // 如果有Looper参数，就是用对应的线程的Looper
        //这是获得UI线程的looper
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    /**
     * OkHttpClientManager的单例
     *
     * @return
     */
    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    //在这里通过调用构造方法创建OkHttp的单例
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;//返回管理者
    }



    /**
     * 同步的Get请求
     *
     * @param url
     * @return Response
     * @throws IOException
     */
    private Response _getAsyn(String url) throws IOException {
        //创建一个request的请求
        final Request request = new Request.Builder().url(url).build();
        //创建一个call对象
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;//返回一个响应的对象
    }

    /**
     * 同步的get请求
     *
     * @param url
     * @return 字符串
     * @throws IOException
     */
    private String _getAsString(String url) throws IOException {
        Response execute = _getAsyn(url);
        return execute.body().string();//返回字符串
    }

    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsyn(String url, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        //需要加入调度队列
        deliveryResult(callback, request);
    }

    /**
     * 同步的post请求
     *
     * @param url
     * @param params post的参数
     * @return response
     * @throws IOException
     */
    private Response _post(String url, Param... params) throws IOException {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }

    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     * @throws IOException
     */
    private String _postAsString(String url, Param... params) throws IOException {
        Response response = _post(url, params);
        return response.body().string();
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Param... params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsyn(String url, final ResultCallback callback, Map<String, String> params) {
        //
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }

    /**
     * 同步基于post的文件上传
     *
     * @param url
     * @param files
     * @param fileKeys
     * @param params
     * @return
     * @throws IOException
     */
    private Response _post(String url, File[] files, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        return mOkHttpClient.newCall(request).execute();
    }
    /**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件不带参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliveryResult(callback, request);
    }

    /**
     * 这是加载图片的
     * @param view
     * @param url
     * @param errorResId
     */
    private void _displayImage(final ImageView view,final String url,final int errorResId)
    {
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                setErrorResId(view,errorResId);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = null;
                try
                {
                    is = response.body().byteStream();
                    ImageUtils.ImageSize actualImageSize = ImageUtils.getImageSize(is);
                    ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(view);
                    int inSampleSize = ImageUtils.calculateInSampleSize(actualImageSize, imageViewSize);
                    try
                    {
                        is.reset();
                    } catch (IOException e)
                    {
                        response = _getAsyn(url);
                        is = response.body().byteStream();
                    }

                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = false;
                    ops.inSampleSize = inSampleSize;
                    final Bitmap bm = BitmapFactory.decodeStream(is, null, ops);
                    mDelivery.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            view.setImageBitmap(bm);
                        }
                    });
                } catch (Exception e)
                {
                    setErrorResId(view, errorResId);

                } finally
                {
                    if (is != null) try
                    {
                        is.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void setErrorResId(final ImageView view, final int errorResId){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                view.setImageResource(errorResId);
            }
        });
    }

    /**************************************** 对外公布的方法*******************************/
    public static Response getAsyn(String url) throws IOException {
        return getInstance()._getAsyn(url);
    }


    public static String getAsString(String url) throws IOException {
        return getInstance()._getAsString(url);
    }

    public static void getAsyn(String url, ResultCallback callback) {
        getInstance()._getAsyn(url, callback);
    }

    public static Response post(String url, Param... params) throws IOException {
        return getInstance()._post(url, params);
    }

    public static String postAsString(String url, Param... params) throws IOException {
        return getInstance()._postAsString(url, params);
    }
    public static void postAsyn(String url,final ResultCallback callback,Param...params){
        getInstance()._postAsyn(url, callback, params);
    }
    public static void postAsyn(String url,final ResultCallback callback,
                                Map<String,String>params){
        getInstance()._postAsyn(url, callback, params);
    }

    //以下都是文件上传的对外方法的代码
    public static Response post(String url, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        return getInstance()._post(url, files, fileKeys, params);
    }

    public static Response post(String url, File file, String fileKey) throws IOException
    {
        return getInstance()._post(url, file, fileKey);
    }

    public static Response post(String url, File file, String fileKey, Param... params) throws IOException
    {
        return getInstance()._post(url, file, fileKey, params);
    }

    public static void postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        getInstance()._postAsyn(url, callback, files, fileKeys, params);
    }


    public static void postAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException
    {
        getInstance()._postAsyn(url, callback, file, fileKey);
    }


    public static void postAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws IOException
    {
        getInstance()._postAsyn(url, callback, file, fileKey, params);
    }

    /**
     * 图片展示的对外方法
     * @param view
     * @param url
     * @param errorResId
     * @throws IOException
     */
    public static void displayImage(final ImageView view, String url, int errorResId) throws IOException
    {
        getInstance()._displayImage(view, url, errorResId);
    }


    public static void displayImage(final ImageView view, String url)
    {
        getInstance()._displayImage(view, url, -1);
    }


    /***********************************************************************************************/
    /**
     * post文件上传
     *
     * @param url
     * @param files
     * @param fileKeys
     * @param params
     * @return
     */
    private Request buildMultipartFormRequest(String url, File[] files, String[] fileKeys, Param[] params) {
        params = validateParam(params);
        MultipartBuilder builder = new MultipartBuilder()
                .type(MultipartBuilder.FORM);
        for (Param param : params) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url).post(requestBody).build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private Param[] validateParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else return params;
    }


    private Param[] map2Params(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private static final String SESSION_KEY = "Set-Cookie";
    private static final String mSessionKey = "JSESSIONID";

    private Map<String, String> mSessions = new HashMap<String, String>();

    private void deliveryResult(final ResultCallback callback, Request request) {
        //加入调度队列
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            /**
             * 失败的回调
             * @param request
             * @param e
             */
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            /**
             * 成功的回调
             * @param response
             */
            @Override
            public void onResponse(final Response response) {
                try {
                    final String string = response.body().string();
                    if (callback.mType == String.class) {
                        sendSuccessResultCallback(string, callback);
                    } else {
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessResultCallback(o, callback);
                    }

                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                } catch (com.google.gson.JsonParseException e) {
                    //Json解析的错误
                    sendFailedStringCallback(response.request(), e, callback);
                }
            }
        });
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }

    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        //post的时候，参数是包含在请求体中的
        //通过FormEncodingBuilder,添加多个String键值对,然后去构造RequestBody
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    /**
     * 结果回调的抽象类
     *
     * @param <T> 该泛型就是自己的数据类的泛型
     */
    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            //得到subclass的一个父类(超类)
            Type superclass = subclass.getGenericSuperclass();
            //判断superclass是不是它的实例 是就返回true 不是就返回false
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            //这是一个接口(参数化的)
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }

    /**
     * 这是键值对
     */
    public static class Param {
        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }



}
