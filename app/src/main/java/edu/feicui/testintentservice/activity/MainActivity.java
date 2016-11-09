package edu.feicui.testintentservice.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.feicui.testintentservice.R;
import edu.feicui.testintentservice.service.MyService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    MediaPlayer和SeekBar
    MediaPlayer mediaPlayer;
    Button mBtnUploadPic;
    LinearLayout mLLContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mBtnUploadPic= (Button) findViewById(R.id.btn_upload_pic);
        mBtnUploadPic.setOnClickListener(this);
        mLLContent= (LinearLayout) findViewById(R.id.ll_content);

    }

    int i;
    @Override
    public void onClick(View v) {
        //启动服务
        Intent intent=new Intent(this, MyService.class);
        intent.putExtra("index", i);
        startService(intent);
        TextView mTv=new TextView(this);
        mTv.setText(Environment.getExternalStorageDirectory().getAbsolutePath()+"00000"+i+".jpg");
        mLLContent.addView(mTv);
        i++;
    }

    UpLoadFinishReciver upLoadFinishReciver=new UpLoadFinishReciver();

    /**
     * 注册
     */
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter=new IntentFilter();
        filter.addAction("edu.feicui.upLoadFinish");
        registerReceiver(upLoadFinishReciver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(upLoadFinishReciver);
    }

    public class UpLoadFinishReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int index=intent.getIntExtra("index",-1);
            TextView mTv= (TextView) mLLContent.getChildAt(index);
            mTv.setText(mTv.getText()+"----SUCCESS");
        }
    }
}
