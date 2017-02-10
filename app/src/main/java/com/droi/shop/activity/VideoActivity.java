package com.droi.shop.activity;

import android.app.Activity;
import android.os.Bundle;

import com.droi.shop.R;
/*import com.hyphenate.chat.EMClient;
import com.hyphenate.media.EMLocalSurfaceView;
import com.hyphenate.media.EMOppositeSurfaceView;*/

/**
 * Created by marek on 2017/2/9.
 */

public class VideoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
      /*  EMLocalSurfaceView localSurface = (EMLocalSurfaceView) findViewById(R.id.video);
        EMOppositeSurfaceView oppositeSurface = (EMOppositeSurfaceView) findViewById(R.id.opposite);
        EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);*/
    }
}
