package www.supcon.com.hsesystem.Base;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import www.supcon.com.hsesystem.Activity.NFCActivity;
import www.supcon.com.hsesystem.R;

/**
 * Created by yaobing on 2018/3/30.
 * Description xxx
 */

public abstract class BaseActivity extends AppCompatActivity {

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent = null;

    private IntentFilter tagDetected = null;
    private IntentFilter[] intentFiltersArray;
    String[] [] techListsArray;


    public boolean bUploadOK = false;
    public Handler mPostHandler = null;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getMyApplication().addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initNFC();
    }

    private void initNFC(){
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this,NFCActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        tagDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);//ACTION_TECH_DISCOVERED
        try {
            tagDetected.addDataType("*/*");
        }

        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[] { tagDetected, };
        techListsArray = new String[][] {
                new String[] { NfcF.class.getName() },
                new String[] { NfcA.class.getName() },
                new String[] { NfcB.class.getName() },
                new String[] { NfcV.class.getName() },
                new String[] { Ndef.class.getName() },
                new String[] { IsoDep.class.getName()},
                new String[] { MifareClassic.class.getName() },
                new String[] { MifareUltralight.class.getName()}};
    }

    //获得application
    public BaseApplication getMyApplication() {
        Application application = getApplication();
        return (BaseApplication) application;
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, intentFiltersArray,techListsArray);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //恢复默认状态
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    /**
     * 使用Activity.this的地方统一使用getMe替代
     *
     * @return 当前上下文
     */
    public Context getMe() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMyApplication().removeActivity(this);
    }
}
