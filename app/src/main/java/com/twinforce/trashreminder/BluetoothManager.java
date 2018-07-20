package com.twinforce.trashreminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import java.util.Set;

/**
 * Created by TF4 on 22/12/2016.
 */

public class BluetoothManager {
    public static int REQUEST_BLUETOOTH = 1;

    private Context mContext;
    private BluetoothAdapter btAdapter;
    public BluetoothManager(Context context){
        mContext = context;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        // Phone does not support Bluetooth so let the user know and exit.
        if (btAdapter == null) {
            new AlertDialog.Builder(mContext)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        if (!btAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            Activity activity = (Activity) mContext;
            activity.startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
    }

    public Set<BluetoothDevice> getPairedDevices(){
        Log.d("DEVICELIST", "Super called for DeviceListFragment onCreate\n");
        //deviceItemList = new ArrayList<DeviceItem>();

        return btAdapter.getBondedDevices();
    }
}
