package com.example.yasamanro.bhangarigame;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

import java.util.ArrayList;

public class BLEServiceContainer {

    private BluetoothLeService mBluetoothLeService;
    private BluetoothDevice mDevice;
    ArrayList<BluetoothGattService> mGattservices;

    private static BLEServiceContainer __instance;

    static {
        Log.d("SINGLETON", "Instantiated static block");
        __instance = new BLEServiceContainer();
    }

    private BLEServiceContainer () { }

    public static BLEServiceContainer getInstance(){
        return __instance;
    }

    public void setBleService(BluetoothLeService service){
        mBluetoothLeService = service;
    }

    public void setmDevice (BluetoothDevice device){
        mDevice = device;
    }

    public void setmGattservices (ArrayList<BluetoothGattService> services) {
        mGattservices = new ArrayList<>(services);
    }

    public BluetoothLeService getBleService() {
        return mBluetoothLeService;
    }

    public BluetoothDevice getmDevice(){
        return mDevice;
    }

    public ArrayList<BluetoothGattService> getmGattservices(){
        return mGattservices;
    }
}


