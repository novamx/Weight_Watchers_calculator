package nekodon.nadav;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.android.vending.billing.IInAppBillingService;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends Activity   {



	protected static final String TAG = null;

	EditText hel,valueeaten,pah,fat,siv;
	TextView totalfor100,totalforeat;
	double grameat,pointsfor100;
	static NumberFormat formatter = new DecimalFormat("#0.0");
	ImageButton Button_reset;
	String yyy;
	int x=0;
	InterstitialAd mInterstitialAd;
	IInAppBillingService mService;
	IabHelper mHelper;
	Button ButtonPremium;
	int checknum;
	SeekBar SeekBarhel,Seekbarpah,Seekbarfat,Seekbarsiv,SeekbarBygrm;
	boolean mIsPremium = false;
	int flaghelbonimtouch=0;
	final String SKU_PREMIUM = "finalfinal";
	static final int RC_REQUEST = 10001;
	int checkIfOk = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		hel = (EditText)findViewById(R.id.editText);
		pah = (EditText) findViewById(R.id.editText5);
		fat = (EditText) findViewById(R.id.editText6);
		siv = (EditText) findViewById(R.id.editText7);
		valueeaten = (EditText) findViewById(R.id.editText8);
		totalfor100 = (TextView) findViewById(R.id.textview2);
		totalforeat = (TextView) findViewById(R.id.textview1);
		Button_reset = (ImageButton) findViewById(R.id.imageButton);
		ButtonPremium = (Button) findViewById(R.id.imageButton2);
		SeekBarhel = (SeekBar) findViewById(R.id.SeekBarhel);
		Seekbarfat = (SeekBar) findViewById(R.id.seekBarfat);
		Seekbarsiv = (SeekBar) findViewById(R.id.seekBarsiv);
		Seekbarpah = (SeekBar) findViewById(R.id.seekBarpah);
		SeekbarBygrm = (SeekBar) findViewById(R.id.SeekBarBygrm);

		hel.addTextChangedListener(new TextWatcher() {
//
			public void afterTextChanged(Editable s) {

			}

			public void beforeTextChanged(CharSequence s, int start,
										  int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start,
									  int before, int count) {
				if(hel.getText().toString().equals("")){
					hel.setText("0");
					hel.selectAll();
				}
				totalfor100.setText(checkresult(ToTal2(hel,pah,fat,siv))+"");

			}
		});
		pah.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {

			}

			public void beforeTextChanged(CharSequence s, int start,
										  int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start,
									  int before, int count) {
				if(s.equals("")){
					pah.setText("0");
				}
				totalfor100.setText(checkresult(ToTal2(hel,pah,fat,siv))+"");

			}
		});
		fat.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {

			}

			public void beforeTextChanged(CharSequence s, int start,
										  int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start,
									  int before, int count) {
				if(s.equals("")){
					fat.setText("0");
				}
				totalfor100.setText(checkresult(ToTal2(hel,pah,fat,siv))+"");

			}
		});
		siv.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {

			}

			public void beforeTextChanged(CharSequence s, int start,
										  int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start,
									  int before, int count) {
				if(s.equals("")){
					siv.setText("0");
				}
				totalfor100.setText(checkresult(ToTal2(hel,pah,fat,siv))+"");


			}
		});
		siv.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN)
				{
					switch (keyCode)
					{
						case KeyEvent.KEYCODE_DPAD_CENTER:
						case KeyEvent.KEYCODE_ENTER:
							valueeaten.requestFocus();
							return true;
						default:
							break;
					}
				}
				return false;
			}
		});
		valueeaten.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN)
				{
					switch (keyCode)
					{
						case KeyEvent.KEYCODE_DPAD_CENTER:
						case KeyEvent.KEYCODE_ENTER:
						hideKeyboard();
							return true;
						default:
							break;
					}
				}
				return false;
			}
		});
		SeekBarhel.setMax(300);
		SeekBarhel.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int min = 200;
			int progress = 0;

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progressvalue,
										  boolean fromUser) {
				// TODO Auto-generated method stub
				if(min>progressvalue){
					progress = progressvalue;

					double value = ((float)progress / 10.0);
					hel.setText(value+"");
				}else{

					progress = progressvalue;

					progress = progress / 10;
					progress = progress * 10;

					hel.setText((progress/10)+"");
				}

				if(progress==0){
					hel.setText("0");
				}

				totalfor100.setText(checkresult((ToTal2(hel,pah,fat,siv))));

			}
		});

		Seekbarpah.setMax(700);
		Seekbarpah.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int min = 400;
			int progress = 0;
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progressvalue,
										  boolean fromUser) {
				// TODO Auto-generated method stub
				if(min>progressvalue){
					progress = progressvalue;

					double value = ((float)progress / 10.0);
					pah.setText(value+"");
				}else{

					progress = progressvalue;

					progress = progress / 10;
					progress = progress * 10;

					pah.setText((progress/10)+"");
				}

				if(progress==0){
					pah.setText("0");
				}

				totalfor100.setText(checkresult((ToTal2(hel,pah,fat,siv))));
			}
		});

		Seekbarfat.setMax(600);
		Seekbarfat.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int min = 500;
			int progress = 0;
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progressvalue,
										  boolean fromUser) {
				// TODO Auto-generated method stub
				if(min>progressvalue){
					progress = progressvalue;

					double value = ((float)progress / 10.0);
					fat.setText(value+"");
				}else{

					progress = progressvalue;

					progress = progress / 10;
					progress = progress * 10;

					fat.setText((progress/10)+"");
				}

				if(progress==0){
					fat.setText("0");
				}

				totalfor100.setText(checkresult((ToTal2(hel,pah,fat,siv))));

			}
		});


		Seekbarsiv.setMax(150);
		Seekbarsiv.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int min = 100;
			int progress = 0;
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progressvalue,
										  boolean fromUser) {
				// TODO Auto-generated method stub
				if(min>progressvalue){
					progress = progressvalue;

					double value = ((float)progress / 10.0);
					siv.setText(value+"");
				}else{

					progress = progressvalue;

					progress = progress / 10;
					progress = progress * 10;

					siv.setText((progress/10)+"");
				}

				if(progress==0){
					siv.setText("0");
				}

				totalfor100.setText(checkresult((ToTal2(hel,pah,fat,siv))));



			}
		});

		SeekbarBygrm.setMax(1000);
		SeekbarBygrm.incrementProgressBy(10);
		SeekbarBygrm.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progress = 0;
			int ok = 0;

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
					if(ok==0){
						SeekbarBygrm.setProgress(0);
					}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if(checkIfOk == 0) {
					if (siv.getText().toString().equals("0") && fat.getText().toString().equals("0") && pah.getText().toString().equals("0") && hel.getText().toString().equals("0")) {
						Toast.makeText(MainActivity.this, "Insert some values first", Toast.LENGTH_SHORT).show();
						SeekbarBygrm.setProgress(0);
						ok = 0;
						hel.requestFocus();
					}else{
						ok = 1;
					}
				}else{
					totalforeat.setText("0");
					final Dialog d = new Dialog(MainActivity.this);
					//		         ,android.R.style.Theme_Dialog
					d.requestWindowFeature(Window.FEATURE_NO_TITLE);
					//				d.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
					d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
					d.setContentView(R.layout.dialogopen);

					Button b1 = (Button) d.findViewById(R.id.buttonRate);
					Button b2 = (Button) d.findViewById(R.id.Button02);
					Button b3 = (Button) d.findViewById(R.id.button2);
					TextView text = (TextView) d.findViewById(R.id.textView1);
					text.setTextColor(Color.BLACK);
					b1.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) {
							Intent i = new Intent(android.content.Intent.ACTION_VIEW);
							i.setData(Uri.parse("https://play.google.com/store/apps/details?id=nekodon.nadav"));
							startActivity(i);
						}
					});
					b2.setOnClickListener(new OnClickListener()
					{
						public void onClick(View v) {
							mHelper.launchPurchaseFlow(MainActivity.this, SKU_PREMIUM, RC_REQUEST,
									mPurchaseFinishedListener, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");						d.dismiss();
						}

					});
					b3.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) {
							SeekbarBygrm.setProgress(0);
							d.dismiss();
						}
					});
					d.show();
				}
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progresValue,
										  boolean fromUser) {
				// TODO Auto-generated method stub

				if(ok==0){
					SeekbarBygrm.setProgress(0);

				}else {

					progress = progresValue;

					progress = progress / 10;
					progress = progress * 10;

					valueeaten.setText(String.valueOf(progress));


					if (valueeaten.getText().toString().equals("")) {
						valueeaten.setText("0");
					} else {
						grameat = Double.parseDouble(valueeaten.getText().toString());
						pointsfor100 = Double.parseDouble(totalfor100.getText().toString());


						yyy = formatter.format((pointsfor100 * (double) (grameat / 100)));


						double yyy2 = Double.parseDouble(yyy);
						x = (int) yyy2;

						yyy2 = Double.parseDouble(yyy) - x;

						if ((yyy2) > 0.25 && (yyy2 < 0.75)) {
							totalforeat.setText((x + 0.5) + "");
						}
						if ((yyy2) > 0.75) {
							totalforeat.setText((x + 1) + "");
						}
						if ((yyy2) < 0.25) {
							totalforeat.setText((x) + "");
						}
					}
				}
			}

		});

		if(mIsPremium == false){
		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
					.addTestDevice("284C628A80680C07E21AE13728ADE937")
					.addTestDevice("0E9830DF43C4EB440157B8C079727CF9")
					.build();
			mAdView.loadAd(adRequest);
		}

		valueeaten.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {

			}

			public void beforeTextChanged(CharSequence s, int start,
										  int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start,
									  int before, int count) {


				if(valueeaten.getText().toString().equals("")){
					grameat = 0;
				}else{
					grameat = Double.parseDouble(valueeaten.getText().toString());
				}
				pointsfor100 = Double.parseDouble(totalfor100.getText().toString());


					yyy = formatter.format((pointsfor100*(double)(grameat/100)));


					double yyy2 = Double.parseDouble(yyy);
					x = (int)yyy2;

					yyy2 = Double.parseDouble(yyy) - x;

					if ((yyy2) > 0.25 && (yyy2 <0.75)){
						totalforeat.setText((x+0.5)+"");
					}
					if ((yyy2) > 0.75){
						totalforeat.setText((x+1)+"");
					}
					if ((yyy2) < 0.25){
						totalforeat.setText((x)+"");
					}

			}
		});


//		final String app_id = "nekodon.nadav";



		Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
		serviceIntent.setPackage("com.android.vending");
		bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlcWs6jHuexabGQLvV4ke5wlGe4CRTdm9N8ycwzGKLSLKRdzzCPJnyr+UtgeHe0Zv2JMpYTaTMPelXIp4EfCO+He7a6aTIEzYFzpVgg5W2NJecqVEsQFpiCA0gJ/CZg7M+arSASeCQidIGcUOB5mtLz/5o1QnFU7MONjryogWp3Ttr7qbi8hCIfflnaYckea5S8RAsWd3mRgOI0hC2dIOhcY5OL57DoWFAScp1enTesAX0ahobsRUXF39Hnxj/CH0kY2mAcNabn9QfcOtFsOEkTGadfujkzWTbWTkVGeERAwaQpKHrMPzlZzaHRdBrgQjiWuAOU8GmtAPMtuqjPSzgwIDAQAB";
		mHelper = new IabHelper(this, base64EncodedPublicKey);

		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				Log.d(TAG, "Setup finished.");

				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					complain("Problem setting up in-app billing: " + result);
					return;
				}

				// Have we been disposed of in the meantime? If so, quit.
				if (mHelper == null) return;

				// IAB is fully set up. Now, let's get an inventory of stuff we own.
				Log.d(TAG, "Setup successful. Querying inventory.");
				mHelper.queryInventoryAsync(mGotInventoryListener);
			}
		});
//
		if(mIsPremium == false){
			AdView mAdView = (AdView) findViewById(R.id.adView);
			AdRequest adRequest = new AdRequest.Builder()
					.addTestDevice("284C628A80680C07E21AE13728ADE937")
					.addTestDevice("0E9830DF43C4EB440157B8C079727CF9")
					.build();
			mAdView.loadAd(adRequest);
		}

		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId("ca-app-pub-6162413570337571/5567700042");
		requestNewInterstitial();
		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				if(flaghelbonimtouch ==0){

				}else{
					requestNewInterstitial();
					if(checknum<=5 && checknum>=1){
						HowMuchLeft();
					}else{


					}
				}
			}
		});

		requestNewInterstitial();



//		hel.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {hel.selectAll();
//			}
//		});
//		pah.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {pah.setText("");
//			}
//		});
//		fat.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {fat.setText("");
//			}
//		});
//		siv.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				siv.setText("");
//
//			}
//		});
		valueeaten.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(siv.getText().toString().equals("0")&&fat.getText().toString().equals("0")&&pah.getText().toString().equals("0")&&hel.getText().toString().equals("0"))
				{
					Toast.makeText(MainActivity.this, "Insert some values first", Toast.LENGTH_SHORT).show();
					SeekbarBygrm.setProgress(0);
					valueeaten.setText("0");
					hel.requestFocus();

				}
			}
		});

		Button_reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mInterstitialAd.isLoaded() && mIsPremium==false) {
					mInterstitialAd.show();
				}
				flaghelbonimtouch++;
				Seekbarfat.setProgress(0);
				Seekbarpah.setProgress(0);
				Seekbarsiv.setProgress(0);
				SeekBarhel.setProgress(0);
				SeekbarBygrm.setProgress(0);

				siv.setText("0");
				pah.setText("0");
				hel.setText("0");
				fat.setText("0");
				valueeaten.setText("0");
				totalfor100.setText("0");
				totalforeat.setText("0");

				updateUi2();
				// after the ads back i need to change this to updateUi2
			}
		});

		ButtonPremium.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				Log.d(TAG, "Upgrade button clicked; launching purchase flow for upgrade.");

				/* TODO: for security, generate your payload here for verification. See the comments on
				 *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
				 *        an empty string, but on a production app you should carefully generate this. */
				String payload = "";

				mHelper.launchPurchaseFlow(MainActivity.this, SKU_PREMIUM, RC_REQUEST,
						mPurchaseFinishedListener, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");

			}
		});



	}

	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
			Log.d(TAG, "Query inventory finished.");

			// Have we been disposed of in the meantime? If so, quit.
			if (mHelper == null) return;

			// Is it a failure?
			if (result.isFailure()) {
				complain("Failed to query inventory: " + result);
				return;
			}

			Log.d(TAG, "Query inventory was successful.");

			/*
			 * Check for items we own. Notice that for each purchase, we check
			 * the developer payload to see if it's correct! See
			 * verifyDeveloperPayload().
			 */

			// Do we have the premium upgrade?
			Purchase premiumPurchase = inventory.getPurchase(SKU_PREMIUM);
			mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
			Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));

			updateUi();
			
			Log.d(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};

	// User clicked the "Upgrade to Premium" button.
	public void onUpgradeAppButtonClicked(View arg0) {
		Log.d(TAG, "Upgrade button clicked; launching purchase flow for upgrade.");

		/* TODO: for security, generate your payload here for verification. See the comments on
		 *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
		 *        an empty string, but on a production app you should carefully generate this. */
		String payload = "";

		mHelper.launchPurchaseFlow(this, SKU_PREMIUM, RC_REQUEST,
				mPurchaseFinishedListener, payload);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
		if (mHelper == null) return;

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			// not handled, so handle it ourselves (here's where you'd
			// perform any handling of activity results not related to in-app
			// billing...
			super.onActivityResult(requestCode, resultCode, data);
		}
		else {
			Log.d(TAG, "onActivityResult handled by IABUtil.");
		}
	}

	boolean verifyDeveloperPayload(Purchase p) {
		String payload = p.getDeveloperPayload();

		/*
		 * TODO: verify that the developer payload of the purchase is correct. It will be
		 * the same one that you sent when initiating the purchase.
		 *
		 * WARNING: Locally generating a random string when starting a purchase and
		 * verifying it here might seem like a good approach, but this will fail in the
		 * case where the user purchases an item on one device and then uses your app on
		 * a different device, because on the other device you will not have access to the
		 * random string you originally generated.
		 *
		 * So a good developer payload has these characteristics:
		 *
		 * 1. If two different users purchase an item, the payload is different between them,
		 *    so that one user's purchase can't be replayed to another user.
		 *
		 * 2. The payload must be such that you can verify it even when the app wasn't the
		 *    one who initiated the purchase flow (so that items purchased by the user on
		 *    one device work on other devices owned by the user).
		 *
		 * Using your own server to store and verify developer payloads across app
		 * installations is recommended.
		 */

		return true;
	}

	// Callback for when a purchase is finished
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

			// if we were disposed of in the meantime, quit.
			if (mHelper == null) return;

			if (result.isFailure()) {
				complain("Error purchasing: " + result);
				return;
			}
			if (!verifyDeveloperPayload(purchase)) {
				complain("Error purchasing. Authenticity verification failed.");
				return;
			}

			Log.d(TAG, "Purchase successful.");


			if (purchase.getSku().equals(SKU_PREMIUM)) {
				// bought the premium upgrade!
				Log.d(TAG, "Purchase is premium upgrade. Congratulating user.");
				alert("Thank you for upgrading to premium!");
				mIsPremium = true;
				updateUi();
			}

		}   
	};

	public void updateUi2() {
		//      findViewById(R.id.SeekBarBygrm).setVisibility(mIsPremium ? View.GONE : View.VISIBLE);
		File f_num = new File(getApplicationContext().getFilesDir(),"num");
		String num = FileManager.readFromFile(f_num);

		if(num.equals("")){
			num = "5";
			FileManager.writeToFile(f_num, num);
		}else if(mIsPremium == true) {
			valueeaten.setEnabled(true);
			SeekbarBygrm.setEnabled(true);
			ButtonPremium.setText(R.string.you_are_premium);
		}else {
			checknum = Integer.parseInt(FileManager.readFromFile(f_num));
			if(checknum<=5 && checknum>1){
				valueeaten.setEnabled(true);
				SeekbarBygrm.setEnabled(true);
				checknum--;
				FileManager.writeToFile(f_num, checknum+"");

			}else{
				valueeaten.setEnabled(false);
//				SeekbarBygrm.setEnabled(false);
				checkIfOk = 1;
				checknum--;

				final Dialog d = new Dialog(MainActivity.this);
				//		         ,android.R.style.Theme_Dialog
				d.requestWindowFeature(Window.FEATURE_NO_TITLE);
				//				d.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
				d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);		
				d.setContentView(R.layout.dialogopen);
				
				Button b1 = (Button) d.findViewById(R.id.buttonRate);
				Button b2 = (Button) d.findViewById(R.id.Button02);
				Button b3 = (Button) d.findViewById(R.id.button2);
				TextView text = (TextView) d.findViewById(R.id.textView1);
				text.setTextColor(Color.BLACK);
				b1.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						Intent i = new Intent(android.content.Intent.ACTION_VIEW);
						i.setData(Uri.parse("https://play.google.com/store/apps/details?id=nekodon.nadav"));
						startActivity(i);
					}
				});
				b2.setOnClickListener(new OnClickListener()
				{
					public void onClick(View v) {
						mHelper.launchPurchaseFlow(MainActivity.this, SKU_PREMIUM, RC_REQUEST,
								mPurchaseFinishedListener, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");						d.dismiss();
					} 

				});
				b3.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						d.dismiss();
					} 
				});
				d.show();

			}

		}
	}

	public void updateUi() {
		//    findViewById(R.id.SeekBarBygrm).setVisibility(mIsPremium ? View.GONE : View.VISIBLE);
		
		if(mIsPremium == false){
			AdView mAdView = (AdView) findViewById(R.id.adView);
			AdRequest adRequest = new AdRequest.Builder()
					.addTestDevice("284C628A80680C07E21AE13728ADE937")
					.addTestDevice("0E9830DF43C4EB440157B8C079727CF9")
					.build();
			mAdView.loadAd(adRequest);
		}
		
		File f_num = new File(getApplicationContext().getFilesDir(),"num");
		String num = FileManager.readFromFile(f_num);

		if(num.equals("")){
			num = "5";
			FileManager.writeToFile(f_num, num);
			HowMuchLeftfor5();
		}else if(mIsPremium == true) {
			valueeaten.setEnabled(true);
			SeekbarBygrm.setEnabled(true);
			ButtonPremium.setText("you are premium");
			ButtonPremium.setEnabled(false);

		}else {
			checknum = Integer.parseInt(FileManager.readFromFile(f_num));
			if(checknum<=5 && checknum>1){
				valueeaten.setEnabled(true);
				SeekbarBygrm.setEnabled(true);
				checknum--;
				HowMuchLeft();
				FileManager.writeToFile(f_num, checknum+"");

			}else{
				valueeaten.setEnabled(false);
//				SeekbarBygrm.setEnabled(false);
				checkIfOk = 1;
				checknum--;

				final Dialog d = new Dialog(MainActivity.this);
				//		         ,android.R.style.Theme_Dialog
				d.requestWindowFeature(Window.FEATURE_NO_TITLE);
				//				d.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
				d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);		
				d.setContentView(R.layout.dialogopen);
				
				Button b1 = (Button) d.findViewById(R.id.buttonRate);
				Button b2 = (Button) d.findViewById(R.id.Button02);
				Button b3 = (Button) d.findViewById(R.id.button2);
				TextView text = (TextView) d.findViewById(R.id.textView1);
				text.setTextColor(Color.BLACK);
				b1.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						Intent i = new Intent(android.content.Intent.ACTION_VIEW);
						i.setData(Uri.parse("https://play.google.com/store/apps/details?id=nekodon.nadav"));
						startActivity(i);
					}
				});
				b2.setOnClickListener(new OnClickListener()
				{
					public void onClick(View v) {
						ButtonPremium.performClick();
						d.dismiss();
					} 

				});
				b3.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						d.dismiss();
					} 
				});


				d.show();

			}
		}
	}
	void complain(String message) {
		Log.e(TAG, "**** TrivialDrive Error: " + message);
		alert("Error: " + message);
	}
	void alert(String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(this);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		Log.d(TAG, "Showing alert dialog: " + message);
		bld.create().show();
	}

	private void hideKeyboard() {   
		// Check if no view has focus:
		View view = this.getCurrentFocus();
		if (view != null) {
			InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static String checkresult (double a){
		if((a * 10.0 )%10 == 5){
			return(a+"");
		}else{
			return((int)a+"");
		}
	}

	public void LikeCloseApp(){
		if(flaghelbonimtouch == 0){

		}else{
//			requestNewInterstitial();
			if(checknum<=5 && checknum>=1){
				Toast.makeText(getApplicationContext(), getString(R.string.you_have)+" "+checknum+" "+
						getString(R.string.uses)+" " + getString(R.string.clicks_left), Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), getString(R.string.you_have)+" "+checknum+" "+
						getString(R.string.uses)+" " + getString(R.string.clicks_left), Toast.LENGTH_SHORT).show();
			}else{
				Toast toast;
				toast = Toast.makeText(getApplicationContext(), getString(R.string.you_dont_have_premium),
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.AXIS_X_SHIFT|Gravity.CENTER_HORIZONTAL,0, 0);
				toast.show();

				Toast toast2;
				toast2 = Toast.makeText(getApplicationContext(), getString(R.string.you_dont_have_premium),
						Toast.LENGTH_LONG);
				toast2.setGravity(Gravity.AXIS_X_SHIFT|Gravity.CENTER_HORIZONTAL, 0, 0);
				toast2.show();

			}
		}
	}

	public static double ToTal2(EditText hel, EditText pah,EditText fat,EditText siv){
		double h;
		double p;
		double f;
		double s;
		if(hel.getText().toString().equals("")){
			h = 0;
		}else{
			h =  Double.parseDouble((hel.getText().toString()));
		}
		if(pah.getText().toString().equals("")){
			p = 0;
		}else{
			p =  Double.parseDouble((pah.getText().toString()));
		}
		if(fat.getText().toString().equals("")){
			f = 0;
		}else{
			f =  Double.parseDouble((fat.getText().toString()));
		}
		if(siv.getText().toString().equals("")){
			s = 0;
		}else{
			s =  Double.parseDouble((siv.getText().toString()));
		}



		double test = ((16*h) + (19*p) + (45*f) - (s*14))/175.0;

		if((test+"").equals("")){
			return 0;

		}else{

			int x = (int)test;
			double yyy2;
			yyy2 = Double.parseDouble(test+"") - x;

			if ((yyy2) > 0.25 && (yyy2 <0.75)){
				return x+0.5;
			}
			if ((yyy2) > 0.75){
				return x+1;
			}
			return x;
		}
	}

	ServiceConnection mServiceConn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name,
									   IBinder service) {
			mService = IInAppBillingService.Stub.asInterface(service);
		}
	};


	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mService != null) {
			unbindService(mServiceConn);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void HowMuchLeft(){
		Toast toast;
		toast = Toast.makeText(getApplicationContext(),  getString(R.string.you_have)+" "+checknum+" "+
				getString(R.string.uses)+" " + getString(R.string.clicks_left),
				Toast.LENGTH_LONG);
		toast.setGravity(Gravity.AXIS_X_SHIFT|Gravity.TOP, 0, 100);

		toast.show();

		Toast toast2;
		toast2 = Toast.makeText(getApplicationContext(),  getString(R.string.you_have)+" "+checknum+" "+
				getString(R.string.uses)+" " + getString(R.string.clicks_left),
				Toast.LENGTH_SHORT);
		toast2.setGravity(Gravity.AXIS_X_SHIFT|Gravity.TOP, 0, 100);
		toast2.show();
	}
	private void HowMuchLeftfor5(){
		Toast toast;
		toast = Toast.makeText(getApplicationContext(),  getString(R.string.you_have)+" "+5+" "+
				getString(R.string.uses)+" " + getString(R.string.clicks_left),
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.AXIS_X_SHIFT|Gravity.TOP, 0, 100);
		toast.show();

		Toast toast2;
		toast2 = Toast.makeText(getApplicationContext(),  getString(R.string.you_have)+" "+5+" "+
				getString(R.string.uses)+" " + getString(R.string.clicks_left),
				Toast.LENGTH_SHORT);
		toast2.setGravity(Gravity.AXIS_X_SHIFT|Gravity.TOP, 0, 100);
		toast2.show();
	}

	private void requestNewInterstitial() {
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("284C628A80680C07E21AE13728ADE937")
				.addTestDevice("0E9830DF43C4EB440157B8C079727CF9")

				.build();

		mInterstitialAd.loadAd(adRequest);
	}







	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}




}
