package com.dutao.mymobileguard.util;

import android.app.Activity;
import android.widget.Toast;

/**
 * Title: <br>
 * Description: <br>
 * Copyright: Copyright (c) 2007<br>
 * @author dutao
 * @version 1.0 
 * @date 2015-5-29
 */
public class UIUtil
{
    public static void makeToast(final Activity context,final CharSequence msg){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        if("main".equals(Thread.currentThread().getClass().getSimpleName())){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }else{
            context.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
