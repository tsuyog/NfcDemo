package com.tsuyogbasnet.nfcdemo;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    NfcAdapter nfcAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nfcAdapter= NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null){
            Toast.makeText(MainActivity.this, "NFC not Found",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        resolveIntent(intent);
    }

    public void resolveIntent(Intent intent){
        String action = intent.getAction();
        if (nfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || nfcAdapter.ACTION_TECH_DISCOVERED.equals(action) || nfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
            Parcelable[] rawMsg = intent.getParcelableArrayExtra(nfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msg = new NdefMessage[0];
            if (rawMsg !=null){
                msg = new NdefMessage[rawMsg.length];
                for (int i=0; i<=rawMsg.length; i++){
                    msg[i]=(NdefMessage)rawMsg[i];
                }
            }
            buildTagViews(msg);
        }
    }
    void buildTagViews(NdefMessage[] msgs){
        if (msgs == null || msgs.length == 0) {
            return;
        }

        String tagId = new String(msgs[0].getRecords()[0].getType());

        String body = new String(msgs[0].getRecords()[0].getPayload());
        Toast.makeText(MainActivity.this,body ,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
