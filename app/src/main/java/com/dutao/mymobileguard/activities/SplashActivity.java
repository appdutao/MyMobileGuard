package com.dutao.mymobileguard.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.dutao.mymobileguard.R;
import com.dutao.mymobileguard.base.BaseActivity;
import com.dutao.mymobileguard.db.dao.VirusDao;
import com.dutao.mymobileguard.util.UIUtil;

public class SplashActivity extends BaseActivity
{
    private TextView tv_splash_version;
    private TextView tv_info;
    
    private PackageInfo packageInfo;
    private SharedPreferences sp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        tv_info = (TextView) findViewById(R.id.tv_info);
        
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);//透明度动画渐变
        alphaAnimation.setDuration(2000);
        findViewById(R.id.rl_splash_root).startAnimation(alphaAnimation);//欢迎页面透明度渐变
        
        //拷贝资产目录中数据库文件
        copyDB("address.db");
        
        copyDB("antivirus.db");
        
        //创建快捷图标
        createShortCut();
        
        //更新病毒库
        updateVirusDB();
        
        //检查更新

//            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//            tv_splash_version.setText(packageInfo.versionCode);
//            tv_info.setText(packageInfo.versionName);
            
            sp = getSharedPreferences("config", MODE_PRIVATE);
            boolean autoUpdate = sp.getBoolean("autoUpdate", false);
            if(autoUpdate){
                Log.i("SplashActivity", "勾选自动更新，调用更新方法");
                Builder builder = new Builder(SplashActivity.this);
                builder.setTitle("更新提示");
                builder.setMessage("更新内容");
                builder.setPositiveButton("确定更新", new OnClickListener()
                {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        UIUtil.makeToast(SplashActivity.this, "正在更新");
                        loadMainUI();
                    }
                });
                builder.setNegativeButton("下次再说", new OnClickListener()
                {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        loadMainUI();
                    }
                });
            }else{
                loadMainUI();
            }                        
            
    }

    /**
     * 更新病毒库
     */
    private void updateVirusDB()
    {
        new Thread(){

            @Override
            public void run()
            {
                //1.检查数据库文件是否存在
                if(VirusDao.isVirusExists()){
                   //2.获取当前病毒库版本号
                   String virusVersion = VirusDao.getVirusVersion();
                   //3.向服务器发送请求
                   String serverVirusVersion = "2000";
                   if(!virusVersion.equals(serverVirusVersion)){
                     //4.向数据库插入数据
//                       VirusDao.updateVirusVersion(serverVirusVersion);
                   }
                }
            }
            
        }.start();
    }

    /**
     * 创建快捷图标
     */
    private void createShortCut()
    {
        //点击快捷方式时要做的事情
        Intent shortCutIntent = new Intent();
        shortCutIntent.setAction("com.dutao.mymobileguard.action.main");
        shortCutIntent.addCategory("android.intent.category.DEFAULT");
        
        Intent intent = new Intent();//发给系统的广播
        intent.putExtra("duplicate", false);//只允许创建一个快捷方式
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "安全卫士-快捷方式");
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.app));
        
        sendBroadcast(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    /**
     * 从资产目录中拷贝数据
     * 
     * @param dbName
     */
    private void copyDB(final String dbName){
        new Thread(){
            @Override
            public void run()
            {
                try
                {
                    File dbFile = new File(getFilesDir(),dbName);
                    if(dbFile.exists()&&dbFile.length()>0){
                        Log.i("SplashActivity", "数据库文件已存在，无需拷贝");
                        return;
                    }
                    InputStream inputStream = getAssets().open(dbName);
                    FileOutputStream outputStream = openFileOutput(dbName, MODE_PRIVATE);
                    
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while((len=inputStream.read()) != -1){
                        outputStream.write(buffer, 0, len);
                    }
                    inputStream.close();
                    outputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            
        }.start();
    }
    /**
     * 加载主页面
     */
    public void loadMainUI(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
