package twork.video.status;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import twork.video.status.activity.MenuPageActivity;
import twork.video.status.activity.video.VideoCategoryActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mIntent = new Intent(getContext(), MenuPageActivity.class);
                startActivity(mIntent);
                finish();
            }
        }, 5000);
    }

    private Context getContext() {
        return this;
    }
}
