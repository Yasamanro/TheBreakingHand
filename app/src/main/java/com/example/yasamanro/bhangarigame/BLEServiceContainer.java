package com.example.yasamanro.bhangarigame;

import android.util.Log;

public class BLEServiceContainer {

    private BluetoothLeService mBluetoothLeService;

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

    public BluetoothLeService getBleService() {
        return mBluetoothLeService;
    }
}


