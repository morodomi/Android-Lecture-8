package net.morodomi.lecture8;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Masahiro Morodomi <morodomi at gmail.com>
 * @version 1.0.0 updated on 2012-09-13
 */
public class SearchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        // set click event on back button
        findViewById(R.id.search_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // move back to main activity.
                finish();
            }
        });
    }

}
