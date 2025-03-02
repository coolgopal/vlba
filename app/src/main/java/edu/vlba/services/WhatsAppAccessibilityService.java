package edu.vlba.services;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.List;

public class WhatsAppAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (getRootInActiveWindow() == null) {
            return;
        }

        AccessibilityNodeInfoCompat rootInActiveWindow = AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());
        // WhatsApp Message EditText id
        List<AccessibilityNodeInfoCompat> messageNodeList = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry");
        if (messageNodeList == null || messageNodeList.isEmpty())
        {
            return;
        }

        // check if the WhatsApp message EditText field is filled with text
        // and starting with our prefix
        AccessibilityNodeInfoCompat messageField = messageNodeList.get(0);
        if (messageField.getText() == null || messageField.getText().length() == 0
        || !messageField.getText().toString().startsWith("VLBA:"))
        {
            return;
        }

        // WhasApp send button id
        List<AccessibilityNodeInfoCompat> sendMessageNodeInfoList = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send");
        if (sendMessageNodeInfoList == null || sendMessageNodeInfoList.isEmpty())
        {
            return;
        }

        AccessibilityNodeInfoCompat sendMessageButton = sendMessageNodeInfoList.get(0);
        if (!sendMessageButton.isVisibleToUser())
        {
            return;
        }

        // Now fire a click on the send button
        sendMessageButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);

        // Now go back to your app by clicking on the Android back button twice
        // First one to leave the conversation screen
        // Second one to leave WhatsApp
        try {
            Thread.sleep(1000); // hack for certain devices in which the immediate back click is too fast to handle
            performGlobalAction(GLOBAL_ACTION_BACK);
            Thread.sleep(500); // same hack as above
            performGlobalAction(GLOBAL_ACTION_BACK);
            Thread.sleep(500); // same hack as above
            performGlobalAction(GLOBAL_ACTION_BACK);
        } catch (InterruptedException ignored) {}
    }

    @Override
    public void onInterrupt() {

    }

}
