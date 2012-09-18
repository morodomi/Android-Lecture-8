package net.morodomi.lecture8;

import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Activity for Android Lecture 8
 * Making Twitter Client
 * @author Masahiro Morodomi <morodomi at gmail.com>
 * @version 1.0.0 updated on 2012-09-13
 */
public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// create OAuth Consumer and Provider
		Config.consumer = new CommonsHttpOAuthConsumer(Config.TWITTER_CONSUMER_KEY, Config.TWITTER_CONSUMER_SECRET);
		Config.provider = new DefaultOAuthProvider(
				Config.TWITTER_REQUEST_TOKEN_URL,
				Config.TWITTER_AUTHORIZE_URL,
				Config.TWITTER_ACCESS_TOKEN_URL
		);
		// set click event on post button
		findViewById(R.id.main_post).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// start new PostActivity
				Intent intent = new Intent(MainActivity.this, PostActivity.class);
				startActivity(intent);
			}
		});
		// set click event on search button
		findViewById(R.id.main_search).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// start new SearchActivity
				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		// if user is not logged in, launch browser to login.
		// with the result of BrowserActivity, get Twitter token.
		SharedPreferences prefs = getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
		// get saved token and token secret
		String token = prefs.getString("token", null);
		String tokenSecret = prefs.getString("token_secret", null);
		// if there is no token or token secret
		if(token == null || tokenSecret == null) {
			// launch browser to login
			try {
				String url = Config.provider.retrieveRequestToken(Config.consumer, Config.TWITTER_CALLBACK);
				this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			} catch(Exception e) {
				Log.e(Config.TAG, e.getMessage(), e);
			}
		}
		// if token and token secret are not null, set to consumer
		Config.consumer.setTokenWithSecret(token, tokenSecret);

		// get timeline
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Uri uri = intent.getData();
        if (uri!=null && uri.toString().startsWith(Config.TWITTER_CALLBACK)) {
            String verifier=uri.getQueryParameter(
                oauth.signpost.OAuth.OAUTH_VERIFIER);
            try {
                Config.provider.retrieveAccessToken(Config.consumer,verifier);

                //トークンの書き込み
                SharedPreferences pref=getSharedPreferences(
                    "token",MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();
    			editor.putString("token", Config.consumer.getToken());
    			editor.putString("token_secret", Config.consumer.getTokenSecret());
                editor.commit();
                
                // get timeline
                
            } catch(Exception e) {
            	Log.e(Config.TAG, e.getMessage(), e);
            }
        }
	}
}