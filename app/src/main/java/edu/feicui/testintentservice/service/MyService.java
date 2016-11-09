package edu.feicui.testintentservice.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * IntentService
 * 需要使用启动型服务  请求在工作线程中处理
 * 工作完成之后会自动自杀
 * Created by jiaXian on 2016/10/24.
 */
public class MyService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *  name Used to name the worker thread, important only for debugging.
     *             工作线程名
     */
    public MyService() {
        super("MyService---");
    }

    /**
     * 运行在子线程当中
     *  在启动服务的时候调用
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
       int index=intent.getIntExtra("index",-1);
        //请求数据
        try {
            Thread.sleep(3000);
            //上传完毕  通知Activity刷新UI  广播
            Intent intent1=new Intent();
            intent1.setAction("edu.feicui.upLoadFinish");
            intent1.putExtra("index",index);
            sendBroadcast(intent1);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("aaa", "onDestroy: 结束");
    }
}
