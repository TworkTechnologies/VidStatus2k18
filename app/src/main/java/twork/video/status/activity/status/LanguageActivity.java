package twork.video.status.activity.status;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import twork.video.status.Globals;
import twork.video.status.R;
import twork.video.status.adapter.status.LanguageListAdapter;
import twork.video.status.interf.ItemClickListener;
import twork.video.status.object.status.StatusCategoryObject;
import twork.video.status.utils.Constant;

public class LanguageActivity extends AppCompatActivity implements ItemClickListener {
    String TAG = getClass().getSimpleName();
    Globals globals;

    @BindView(R.id.rcv_status_cate)
    RecyclerView rcv_status_cate;

    private String[] dataObjects = new String[]{"ENGLISH", "HINDI", "MARATHI", "PUNJABI", "GUJARATI", "TAMIL", "TELUGU", "KANNADA", "BANGALI"};
    private String[] table_cat = new String[]{"english_cat", "hindi_cat", "marathi_cat", "punjabi_cat", "gujarati_cat", "tamil_cat", "telugu_cat", "kannad_cat", "bangla_cat"};

    List<StatusCategoryObject> dataList=new ArrayList<>();

    @BindView(R.id.mAdView)
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);
        globals = ((Globals) getApplicationContext());

        if (globals.isNetworkConnected()) {
            mAdView.setVisibility(View.VISIBLE);
            set_all_data();
            admob_init();
            full_screen_ads();
        } else {
            mAdView.setVisibility(View.GONE);
            globals.toastValidateMessage(getContext(), "Please check internet connection..");
        }

    }

    private void set_all_data() {
        for (int i = 0; i < dataObjects.length; i++) {
            StatusCategoryObject statusCategoryObject=new StatusCategoryObject();
            statusCategoryObject.setCate_name(dataObjects[i]);
            statusCategoryObject.setCate_key(table_cat[i]);
            dataList.add(statusCategoryObject);
        }

        set_adapter_data();
    }

    private void set_adapter_data() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv_status_cate.setLayoutManager(layoutManager);

        LanguageListAdapter adapter = new LanguageListAdapter(getContext(), dataList);
        rcv_status_cate.setAdapter(adapter);
        rcv_status_cate.setNestedScrollingEnabled(false);

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
        Intent mIntent = new Intent(getContext(), CategoryListActivity.class);
        mIntent.putExtra(Constant.STATUS_TABLE, dataList.get(position).getCate_key());
        startActivity(mIntent);
    }
}
