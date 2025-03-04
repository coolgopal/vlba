package edu.vlba.speaker;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import androidx.annotation.Nullable;

import java.util.Locale;

public class VLBASpeakerTTS {
    private static VLBASpeakerTTS vlbaSpeakerTTSInstance = null;
    TextToSpeech textToSpeech;

    public static synchronized VLBASpeakerTTS getInstance()
    {
        if (null == vlbaSpeakerTTSInstance)
        {
            vlbaSpeakerTTSInstance = new VLBASpeakerTTS();
        }

        return vlbaSpeakerTTSInstance;
    }

    public void initialize(Context context)
    {
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR)
                {
                    int available = textToSpeech.isLanguageAvailable(new Locale("en_IN"));
                    if (available == TextToSpeech.LANG_AVAILABLE)
                    {
                        textToSpeech.setLanguage(new Locale("en_IN"));
                    } else
                    {
                        textToSpeech.setLanguage(Locale.getDefault());
                    }
                }
            }
        });
    }

    public void playText(String text)
    {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
