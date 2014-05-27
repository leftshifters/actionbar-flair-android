package io.leftshift.flair;

import android.app.Activity;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {

    //Minimum and maximum values for random number generation
    private static final int MIN = 1;
    private static final int MAX = 9;

    private static final int INITIAL_DELAY = 0;
    private static final int REPEATING_PERIOD = 2;

    private int mNotificationsCount = 0;
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                int number = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
                updateNotificationsBadge(number);
            }
        }, INITIAL_DELAY, REPEATING_PERIOD, TimeUnit.SECONDS);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(this, icon, mNotificationsCount);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_notifications) {
            // TODO: display unread notifications.
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateNotificationsBadge(final int count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNotificationsCount = count;

                // force the ActionBar to re-layout its MenuItems.
                // onCreateOptionsMenu(Menu) will be called again.
                invalidateOptionsMenu();
            }
        });
    }
}
