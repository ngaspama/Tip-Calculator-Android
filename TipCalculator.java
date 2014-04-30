package com.ngaspama.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableRow;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdSize;

public class TipCalculator extends Activity {

	private static final String BILL_TOTAL = "BILL_TOTAL";
	private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";
	private static final String TEST_DEVICE_ID = "3F8237BFF7935B6EC45A053F04F5BE08";
	 /** The view to show the ad. */
	  private AdView adView;

	  /* Your ad unit id. Replace with your actual ad unit id. */
	  private static final String AD_UNIT_ID = "ca-app-pub-744907015007755/9533060820";
	
	private double currentBillTotal; // Total amount of money
	private int currentCustomPercent; // current percentage of tip 
	private EditText tip10EditText; // string for 10% 
	private EditText total10EditText; // value of 10% of tip
	private EditText tip15EditText; // string for 15% 
	private EditText total15EditText; // value of 15% of tip
	private EditText billEditText; // string for the total amount
	private EditText tip20EditText; // string for 20%
	private EditText total20EditText; // value of 20% of tip
	private TextView customTipTextView; // bar percentage
	private EditText tipCustomEditText; // String for the percentage bar
	private EditText totalCustomEditText; // String the total amount for the percentage bar

	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			currentCustomPercent = seekBar.getProgress();
			updateCustom();
		}
	};
	
	private TextWatcher billEditTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			try {
				currentBillTotal = Double.parseDouble(s.toString());	
			}
			catch(NumberFormatException e) {
				currentBillTotal = 0.0;
			}
			updateStandard();
			updateCustom();
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		
		@Override
		public void afterTextChanged(Editable s) {
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		
		// Create an ad.
	    adView = new AdView(this);
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);

	    // Add the AdView to the view hierarchy. The view will have no size
	    // until the ad is loaded.
	    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
	    linearLayout.addView(adView);	
	    
	 // Create an ad request. Check logCat output for the hashed device ID to
	    // get test ads on a physical device.
	    AdRequest adRequest = new AdRequest.Builder()
	    	.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	    	.addTestDevice(TEST_DEVICE_ID)
	    	.build();
	    adView.loadAd(adRequest);
		
		
		

		if (savedInstanceState == null) {
			currentBillTotal = 0.0;
			currentCustomPercent = 18;
		} else {
			currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);
			currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
		}

		tip10EditText = (EditText) findViewById(R.id.tip10EditText);
		total10EditText = (EditText) findViewById(R.id.total10EditText);

		tip15EditText = (EditText) findViewById(R.id.tip15EditText);
		total15EditText = (EditText) findViewById(R.id.total15EditText);

		tip20EditText = (EditText) findViewById(R.id.tip20EditText);
		total20EditText = (EditText) findViewById(R.id.total20EditText);

		customTipTextView = (TextView) findViewById(R.id.customTipTextView);

		tipCustomEditText = (EditText) findViewById(R.id.tipCustomEditText);
		totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);

		billEditText = (EditText) findViewById(R.id.billEditText);
		billEditText.addTextChangedListener(billEditTextWatcher);
		
		SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
		customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);

	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putDouble(BILL_TOTAL, currentBillTotal);
		outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
	}
	
	private void updateStandard() {
		double tenPercentTip = currentBillTotal * .1;
		double tenPercentTotal = currentBillTotal + tenPercentTip;
		
		tip10EditText.setText(String.format(" %.02f", tenPercentTip));		
		total10EditText.setText(String.format(" %.02f", tenPercentTotal));
		
		double fifteenPercentTip = currentBillTotal * .15;
		double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;
		
		tip15EditText.setText(String.format(" %.02f", fifteenPercentTip));
		total15EditText.setText(String.format(" %.02f", fifteenPercentTotal));		
		
		double twentyPercentTip = currentBillTotal * .20;
		double twentyPercentTotal = currentBillTotal + twentyPercentTip;
		
		tip20EditText.setText(String.format(" %.02f", twentyPercentTip));
		total20EditText.setText(String.format(" %.02f", twentyPercentTotal));
	}
	
	private void updateCustom() {
		customTipTextView.setText(currentCustomPercent + " %");
		double customTipAmount = currentBillTotal * currentCustomPercent * .01;
		double customTotalAmount = currentBillTotal + customTipAmount;
		
		tipCustomEditText.setText(String.format(" %.02f", customTipAmount));
		totalCustomEditText.setText(String.format(" %.02f", customTotalAmount));
	}
	
	 @Override
	  public void onResume() {
	    super.onResume();
	    if (adView != null) {
	      adView.resume();
	    }
	  }

	  @Override
	  public void onPause() {
	    if (adView != null) {
	      adView.pause();
	    }
	    super.onPause();
	  }

	  /** Called before the activity is destroyed. */
	  @Override
	  public void onDestroy() {
	    // Destroy the AdView.
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }

}
