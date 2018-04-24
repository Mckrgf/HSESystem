package www.supcon.com.hsesystem.Activity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;


import www.supcon.com.hsesystem.Base.BaseActivity;
import www.supcon.com.hsesystem.R;
import www.supcon.com.hsesystem.Utils.DataTransform;

public class NFCActivity extends BaseActivity {


    //nfc控制器
    private NfcAdapter mNfcAdapter;
    //读取出来的id
    private String mId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
//        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
//
//        try {
//            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
//                //处理该intent
//                processIntent(getIntent());
//                ParseID();
//            }
//        } catch (Exception e) {
//            Log.i(TAG,e.toString());
//        }
    }



    @Override
    protected void onNewIntent(Intent intent) {
        try {
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
                //处理该intent
                processIntent(intent);
                ParseID();
            }
        } catch (Exception e) {

        }
    }

    private static final String TAG = "NFCActivity";
    private void ParseID(){
        Log.i(TAG,"mid: " + mId);
        finish();
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    private String processIntent(Intent intent) {
        //取出封装在intent中的TAG
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        byte[] uidBytes = tagFromIntent.getId();
           mId = DataTransform.bytesToHexString(uidBytes);

        return mId;
    }

}
