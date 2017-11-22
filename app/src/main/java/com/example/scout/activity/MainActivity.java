package com.example.scout.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scout.R;
import com.example.scout.common.BaseActivity;
import com.example.scout.common.Constants;
import com.example.scout.socket.TcpCallback;
import com.example.scout.socket.TcpSocketManager;
import com.example.scout.view.CircleViewByImage;
import com.example.scout.view.TouchImageView;
import com.example.scout.view.VideoSurfaceView;
import com.linkcard.media.LinkVideoCore;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MainActivity extends BaseActivity implements CircleViewByImage.ActionCallback {


    @BindView(R.id.show_view)
    VideoSurfaceView showView;
    @BindView(R.id.ll_control)
    LinearLayout llControl;
    @BindView(R.id.move_view)
    CircleViewByImage moveView;


    private LinkVideoCore linkVideoCore;

    private boolean isShow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        linkVideoCore = new LinkVideoCore();
        linkVideoCore.sysinit("192.168.11.123");

        isShow = true;

    }

    @Override
    protected void initDb() {

    }

    @Override
    protected void initData() {
        TcpSocketManager.getInstance().startTcp(MainActivity.this);
    }

    @Override
    protected void setListener() {
        moveView.setCallback(this);
    }


    @Override
    protected void onDestroy() {
        linkVideoCore.sysuninit();
        TcpSocketManager.getInstance().endTcp();
        super.onDestroy();
    }


    @OnClick({R.id.show_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_view:
                if (isShow) {
                    isShow = false;
                    llControl.setVisibility(View.GONE);
                } else {
                    isShow = true;
                    llControl.setVisibility(View.VISIBLE);
                }
                break;
        }
    }



    @Override
    public void forwardMove() {//上
        TcpSocketManager.getInstance().sendTextMessageByTcp("2", null);
    }

    @Override
    public void backMove() {//下
        TcpSocketManager.getInstance().sendTextMessageByTcp("8", null);
    }

    @Override
    public void leftMove() {//左
        TcpSocketManager.getInstance().sendTextMessageByTcp("4", null);
    }

    @Override
    public void rightMove() {
        TcpSocketManager.getInstance().sendTextMessageByTcp("6", null);
    }

    @Override
    public void actionUp() {//**
        TcpSocketManager.getInstance().sendTextMessageByTcp("5", null);
    }


    private void modifySsid() {
        OkHttpUtils
                .get()
                .url(Constants.API.CONTROL_CMD_SET_WIFI_SSID)
                .addParams("ssid", "ssid")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                    }
                });
    }

    private void modifyPass() {
        OkHttpUtils
                .get()
                .url(Constants.API.CONTROL_CMD_SET_WIFI_PWD)
                .addParams("pswd", "87654321")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                    }
                });
    }

}
