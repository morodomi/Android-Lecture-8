package net.morodomi.lecture8;

import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Activity for Android Lecture 8
 * Making Twitter Client
 * @author Masahiro Morodomi <morodomi at gmail.com>
 *
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
	}

	@Override
	protected void onResume() {
		super.onResume();
		// if user is not logged in, start LoginActivity to login.
		// with the result of LoginActivity, get Twitter token.
		SharedPreferences prefs = getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
		String token = prefs.getString("token", null);
		String tokenSecret = prefs.getString("token_secret", null);
		if(token == null || tokenSecret == null) {
			// start LoginActivity
			Intent intent = new Intent();
		}
	}
}