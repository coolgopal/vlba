package edu.vlba.messagesender;

import android.app.Activity;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class VLBASMSSender {
    public boolean sendMessageToGuardian(Activity fromActivity, String phoneNumber, String messageBody)
    {
        // request SMS permission
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, messageBody, null, null);

            //TODO: Try out SMS send using intent
//            Intent sendSmsIntent = new Intent(Intent.ACTION_VIEW);
//            sendSmsIntent.setDataAndType(Uri.parse("smsto:"), "vnd.android-dir/mms-sms");
//            sendSmsIntent.putExtra("address", "9141230016");
//            sendSmsIntent.putExtra("sms_body", "Soumik has arrived to school.");
//            startActivity(sendSmsIntent);

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
