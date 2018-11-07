package twork.video.status.activity.video;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import twork.video.status.Globals;
import twork.video.status.R;
import twork.video.status.adapter.video.MainCategoryListAdapter;
import twork.video.status.interf.ItemClickListener;
import twork.video.status.object.maincate.MainCateDataObject;
import twork.video.status.object.maincate.MainCateObject;
import twork.video.status.utils.AppUtils;
import twork.video.status.utils.Constant;

public class VideoCategoryActivity extends AppCompatActivity implements ItemClickListener {
    String TAG = getClass().getSimpleName();
    Globals globals;

    @BindView(R.id.rcv_video_cate)
    RecyclerView rcv_video_cate;

    @BindView(R.id.mAdView)
    AdView mAdView;

    List<MainCateDataObject> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_category);
        ButterKnife.bind(this);
        globals = ((Globals) getApplicationContext());

        if (globals.isNetworkConnected()) {
            mAdView.setVisibility(View.VISIBLE);
            call_category_service();
            admob_init();
            full_screen_ads();
        } else {
            mAdView.setVisibility(View.GONE);
            globals.toastValidateMessage(getContext(), "Please check internet connection..");
        }
    }

    private void call_category_service() {
        if (AppUtils.isInternetConnected(getContext())) {
            globals.dialogShow(getContext());
            String mStringUrl = "http://mirchikart.com/videostatus/api/api.php?req=cactegory";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(mStringUrl)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //do failure stuff
                    globals.dialogDismiss();
                    AppUtils.showAlertDialog(getContext(), "Opps", "Something wrong...");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //do success stuff
                    globals.dialogDismiss();
                    parseDetailResponse(response.body().string());
                }
            });
        } else {
            AppUtils.showAlertDialog(getContext(), "Opps", "Something wrong...");
        }
    }

    private void parseDetailResponse(String responce) {
        Log.e(TAG, responce);
        try {
            JsonElement json = new JsonParser().parse(responce);
            Type type = new TypeToken<MainCateObject>() {
            }.getType();
            MainCateObject mainCateObject = new Gson().fromJson(json, type);
            if (mainCateObject.getSuccess() == 1) {
                dataList = mainCateObject.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        set_adapter_data();
                    }
                });
            } else {
                AppUtils.showAlertDialog(getContext(), "Opps", "Something wrong...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void set_adapter_data() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rcv_video_cate.setLayoutManager(layoutManager);

        MainCategoryListAdapter adapter = new MainCategoryListAdapter(getContext(), dataList);
        rcv_video_cate.setAdapter(adapter);
        rcv_video_cate.setNestedScrollingEnabled(false);

        adapter.setClickListener(this);
    }

    private void admob_init() {

        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                //.addTestDevice("FEC16BB7FA178FC8EE9883D651E352B3")
                .build();

        mAdView.loadAd(adRequest);
    }

    //full screen ads
    private void full_screen_ads() {
        final InterstitialAd mInterstitialAd;

        mInterstitialAd = new InterstitialAd(getContext());

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view, String val_var, int position) {
        Intent mIntent = new Intent(getContext(), VideoListActivity.class);
        mIntent.putExtra(Constant.CATEGORY_ID, dataList.get(position).getCategory_id());
        mIntent.putExtra(Constant.CATEGORY_NAME, dataList.get(position).getCategory_name());
        startActivity(mIntent);
    }
}
