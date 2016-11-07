package vcoolwind.com.compositivesample.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import vcoolwind.com.compositivesample.R;

/**
 * Created by BlackStone on 2016/11/4.
 */


public class ThirdRecycleViewActivity extends AppCompatActivity {

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    static final int SEND_SMS_REQUEST = 2;  // The request code


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_framlayout);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frameContainer);
        if (fragment == null) {
            fragment = new ThirdTitleFragmeng();
            fm.beginTransaction()
                    .add(R.id.frameContainer, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first_page, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_address:
                Toast.makeText(this, "U click add ", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.select_address:
                Toast.makeText(this, "U click search ", Toast.LENGTH_SHORT).show();
                pickContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    private void sendSMS(String phoneNumber, String msgContent) {
        Uri smsToUri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", msgContent);
        startActivityForResult(intent, SEND_SMS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(getClass().getSimpleName(), requestCode + " -- " + resultCode + " --");
        switch (requestCode) {
            case PICK_CONTACT_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactUri = data.getData();
                    String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                    Cursor cursor = getContentResolver()
                            .query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    // Retrieve the phone number from the NUMBER column
                    int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String number = cursor.getString(column);
                    Toast.makeText(this, "U click  " + number, Toast.LENGTH_SHORT).show();
                    sendSMS(number, "[hello]我就试试能不能在英文操作系统下发送中文短信");
                }
            case SEND_SMS_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Send SMS success ! ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Send SMS failed ! ", Toast.LENGTH_SHORT).show();
                }
        }

    }
}