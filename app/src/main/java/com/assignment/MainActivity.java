package com.assignment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.toString();
    TextView mBatteryPercentageTv;
    ImageView mBatteryLevelImV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        // init layout
        setContentView(R.layout.activity_main);
        // init textview and imageView
        mBatteryPercentageTv = (TextView) findViewById(R.id.textview_battery_level);
        mBatteryLevelImV = (ImageView) findViewById(R.id.image_view_battery_level);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // register broadCastReceiver for ACTION_BATTERY_CHANGED
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReciever, batteryLevelFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // unregister broadCastReceiver
        unregisterReceiver(batteryLevelReciever);
    }

    // battery level receiver
    BroadcastReceiver batteryLevelReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // get battery level
            int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            Log.d(TAG, "onReceive: " + batteryLevel);
            updateBatteryLevel(batteryLevel); // update UI
        }
    };

    // This method will update battery level image and textView
    private void updateBatteryLevel(int batteryLevel) {
        mBatteryPercentageTv.setText(batteryLevel + "%");
        if (batteryLevel == 0) {
            mBatteryLevelImV.setImageResource(R.drawable.ic_battery_empty);
        } else if (batteryLevel < 10) {
            mBatteryLevelImV.setImageResource(R.drawable.ic_battery_low);
        } else if (batteryLevel < 50 && batteryLevel > 10) {
            mBatteryLevelImV.setImageResource(R.drawable.ic_battery_below_half);
        } else if (batteryLevel == 50) {
            mBatteryLevelImV.setImageResource(R.drawable.ic_battery_half);
        } else if (batteryLevel > 50 && batteryLevel < 100) {
            mBatteryLevelImV.setImageResource(R.drawable.ic_battery_above_half);
        } else if (batteryLevel == 100) {
            mBatteryLevelImV.setImageResource(R.drawable.ic_battery_full);
        }
    }
}
