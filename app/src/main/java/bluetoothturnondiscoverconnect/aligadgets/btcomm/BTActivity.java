package bluetoothturnondiscoverconnect.aligadgets.btcomm;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;


public class BTActivity extends ActionBarActivity {

    Button btEnableButton,btDisableButton,btBondedDevicesButton,btEnablePhoneDiscoveryButton ;
    boolean reqToTurnOnBluetooth=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt);

        //
        btEnableButton = (Button) findViewById(R.id.buttonBluetoothEnable);
        btDisableButton= (Button) findViewById(R.id.buttonBluetoothDisable);
        btBondedDevicesButton = (Button) findViewById(R.id.buttonBondedDevices);
        btEnablePhoneDiscoveryButton = (Button) findViewById(R.id.buttonEnablePhoneDiscovery);

        btEnableButton.setEnabled(true);
        btDisableButton.setEnabled(false);
        btEnablePhoneDiscoveryButton.setEnabled(false);
        btBondedDevicesButton.setEnabled(false);

        final BluetoothAdapter btAdapt = BluetoothAdapter.getDefaultAdapter();
        final String btAdaptName = btAdapt.getName();

        btEnableButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enableBluetooth(btAdapt);
                btEnableButton.setEnabled(false);
                btDisableButton.setEnabled(true);
                btEnablePhoneDiscoveryButton.setEnabled(true);
                btBondedDevicesButton.setEnabled(true);
            }
        });

        //disable device bluetooth setting
        btDisableButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                disableBluetooth(btAdapt);
                btDisableButton.setEnabled(false);
                btEnableButton.setEnabled(true);
                btEnablePhoneDiscoveryButton.setEnabled(false);
                btBondedDevicesButton.setEnabled(false);
            }
        });

        // Which devices are bonded to my Phone
        btBondedDevicesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //if there are devices bonded to my device
                //show the list of all devices
                  findBondedDevices(btAdapt);
                }

        });


        //code for phone bluetooth discovery settings
       // buttonEnablePhoneDiscovery
        btEnablePhoneDiscoveryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeDiscoverable();
            }
        });


       }

    //function to disable bluetooth
    private void disableBluetooth(BluetoothAdapter btAdapt) {
        if (btAdapt.isEnabled()) {
            btAdapt.disable();
            Toast.makeText(getApplicationContext(), "Device Bluetooth Switched off", Toast.LENGTH_SHORT).show();
        }
    }

    //function to enable bluetooth
    private void enableBluetooth(BluetoothAdapter btAdapt) {
        if (btAdapt.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Device Bluetooth setting already on", Toast.LENGTH_SHORT).show();
        } else {
            //code to enable Bluetooth
           // reqToTurnOnBluetooth = btAdapt.enable();

            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Device Bluetooth now " + reqToTurnOnBluetooth + " ", Toast.LENGTH_SHORT).show();

        }
    }


    //let other devices able to search me after
    // they turn on their phone bluetooth setting
    public void makeDiscoverable() {

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
        Toast.makeText(getApplicationContext(), "Discovery started", Toast.LENGTH_SHORT).show();

    }

    private void findBondedDevices(BluetoothAdapter btAdapt) {
        Set<BluetoothDevice> bondedDevicesList = btAdapt.getBondedDevices();
        if (bondedDevicesList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No bonded device", Toast.LENGTH_SHORT).show();
        }
        else {

            for(BluetoothDevice bt : bondedDevicesList) {
                Toast.makeText(getApplicationContext(), "Bonded device "+ bt.getName(), Toast.LENGTH_SHORT).show();
            }



        }
    }








}
