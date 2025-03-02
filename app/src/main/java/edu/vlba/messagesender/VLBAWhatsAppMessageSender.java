package edu.vlba.messagesender;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.vlba.services.WhatsAppAccessibilityService;

public class VLBAWhatsAppMessageSender {
    public boolean sendMessageToGuardian(Fragment fromActivity, String phoneNumber, String messageBody)
    {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + messageBody));
            fromActivity.startActivity(intent);
            return true;
        } catch (Exception e)
        {
            Toast.makeText(fromActivity.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }


}
