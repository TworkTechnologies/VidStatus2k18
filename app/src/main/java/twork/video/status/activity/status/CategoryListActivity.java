package twork.video.status.activity.status;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import twork.video.status.Globals;
import twork.video.status.R;
import twork.video.status.adapter.status.CategoryListAdapter;
import twork.video.status.adapter.status.StatusListAdapter;
import twork.video.status.db.SqlLiteDbHelper;
import twork.video.status.interf.ItemClickListener;
import twork.video.status.object.status.CatagoryList;
import twork.video.status.utils.Constant;

public class CategoryListActivity extends AppCompatActivity implements ItemClickListener {
    String TAG = getClass().getSimpleName();
    Globals globals;

    @BindView(R.id.rcv_category_list)
    RecyclerView rcv_category_list;

    @BindView(R.id.mAdView)
    AdView mAdView;

    String cate_table = "";

    private SqlLiteDbHelper dbHelper = null;
    private String[] table_message = new String[]{"english_status", "hindi_status", "marathi_status", "punjabi_status", "gujarati_status", "tamil_status", "telugu_status", "kannad_status", "bangla_status"};

    List<CatagoryList> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        ButterKnife.bind(this);
        globals = ((Globals) getApplicationContext());

        if (getIntent().getExtras() != null) {
            try {
                cate_table = getIntent().getStringExtra(Constant.STATUS_TABLE);
            } catch (Exception e) {

            }
        }
        init();

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

    public void init() {
        this.dbHelper = new SqlLiteDbHelper(this);
        try {
            this.dbHelper.CopyDataBaseFromAsset();
        } catch (IOException e) {
            globals.toastValidateMessage(getContext(), "Something Wrong...");
        }
        this.dbHelper.openDataBase();

        dataList = this.dbHelper.GetCategory(cate_table);
    }

    private void set_all_data() {
        set_adapter_data();
    }

    private void set_adapter_data() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv_category_list.setLayoutManager(layoutManager);

        CategoryListAdapter adapter = new CategoryListAdapter(getContext(), dataList);
        rcv_category_list.setAdapter(adapter);
        rcv_category_list.setNestedScrollingEnabled(false);

        adapter.setClickListener(this);
    }

    @Override
    public void onClick(View view, String val_var, int position) {
        Intent intent = new Intent(getContext(), StatusListActivity.class);
        intent.putExtra("catId", dataList.get(position).getCategoryid());
        intent.putExtra("tableName", table_message[position]);
        intent.putExtra("catTitle", dataList.get(position).getCategory());
        startActivity(intent);
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
}
