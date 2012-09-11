package net.morodomi.lecture8;

import android.app.Activity;
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
	}

	@Override
	protected void onResume() {
		super.onResume();
		// if user is not logged in, start LoginActivity to login.
		// with the result of LoginActivity, get Twitter token.
		SharedPreferences prefs = getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
	}
}