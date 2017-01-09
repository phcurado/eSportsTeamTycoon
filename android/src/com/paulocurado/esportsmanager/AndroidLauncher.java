package com.paulocurado.esportsmanager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.paulocurado.esportsmanager.EsportsManager;

public class AndroidLauncher extends AndroidApplication implements AdHandler {
	private static final String TAG = "AndroidLuncher";
	protected AdView adView;

	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;

	protected Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case SHOW_ADS:
				{
					adView.setVisibility(View.VISIBLE);
					break;
				}
				case HIDE_ADS:
				{
					adView.setVisibility(View.GONE);
					break;
				}
			}
		}
	};
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout = new RelativeLayout(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new EsportsManager(), config);
		layout.addView(gameView);

		adView = new AdView(this);

		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				int visibility = adView.getVisibility();
				adView.setVisibility(adView.GONE);
				adView.setVisibility(visibility);
				Log.i(TAG, "Ad Loaded");
			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-8347306151656257/8773995922");

		AdRequest.Builder builder =  new AdRequest.Builder();
		builder.addTestDevice("C2D823303668241A0F6FF64C5E04A3D6");
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		layout.addView(adView, adParams);
		adView.loadAd(builder.build());

		setContentView(layout);


		//initialize(new EsportsManager(), config);

	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}
}
