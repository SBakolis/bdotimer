package com.penstack.dbobosstimer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;

import java.net.MalformedURLException;
import java.net.URL;

public class GdprHelper {

    private static final String PUBLISHER_ID = "pub-6028798031014902";
    private static final String PRIVACY_URL = "https://penstackteam.github.io/bdobosstimerpolicy/";
    //private static final String MARKET_URL_PAID_VERSION = "market://details?id=com.example.app.pro";
    final String PREFS_NAME = "BDO_TIMER_PREFS";

     String GDPRCONSENT = "-1";
    final int NOCONSENTGIVEN = 0;
    final int CONSENTGIVEN = 1;
    SharedPreferences prefs;

    private final Context context;
    private  int con = 0 ;

    private ConsentForm consentForm;

    public GdprHelper(Context context) {
        this.context = context;
    }

    // Initialises the consent information and displays consent form if needed
    public void initialise() {
        ConsentInformation consentInformation = ConsentInformation.getInstance(context);
        consentInformation.requestConsentInfoUpdate(new String[]{PUBLISHER_ID}, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                // User's consent status successfully updated.
                if (consentStatus == ConsentStatus.UNKNOWN) {
                    displayConsentForm();
                }
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                // Consent form error. Would be nice to have proper error logging. Happens also when user has no internet connection
                if (BuildConfig.BUILD_TYPE.equals("debug")) {
                    Toast.makeText(context, errorDescription, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Resets the consent. User will be again displayed the consent form on next call of initialise method
    public void resetConsent() {
        ConsentInformation consentInformation = ConsentInformation.getInstance(context);
        consentInformation.reset();
    }

    private void displayConsentForm() {

        consentForm = new ConsentForm.Builder(context, getPrivacyUrl())
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {
                        // Consent form has loaded successfully, now show it
                        consentForm.show();
                    }

                    @Override
                    public void onConsentFormOpened() {
                        // Consent form was displayed.
                    }

                    @Override
                    public void onConsentFormClosed(
                            ConsentStatus consentStatus ,Boolean userPrefersAdFree) { //,Boolean userPrefersAdFree
                        // Consent form was closed. This callback method contains all the data about user's selection, that you can use.
                        if (userPrefersAdFree) {
                           // redirectToPaidVersion();
                        }
                        GDPRConsentR(consentStatus);
                    }

                    @Override
                    public void onConsentFormError(String errorDescription) {
                        // Consent form error. Would be nice to have some proper logging
                        if (BuildConfig.BUILD_TYPE.equals("debug")) {
                          //  Toast.makeText(context, errorDescription, Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .build();
        consentForm.load();
    }

    private URL getPrivacyUrl() {
        URL privacyUrl = null;
        try {
            privacyUrl = new URL(PRIVACY_URL);
        } catch (MalformedURLException e) {
            // Since this is a constant URL, the exception should never(or always) occur
            e.printStackTrace();
        }
        return privacyUrl;
    }

    private int GDPRConsentR(ConsentStatus consentStatus)
    {


        switch(consentStatus)
        {
            case PERSONALIZED:
                //prefs.edit().putInt(GDPRCONSENT, CONSENTGIVEN).apply();
                con =  1;
               // Log.d("TAG", "done");
                break;
            case NON_PERSONALIZED:
                //prefs.edit().putInt(GDPRCONSENT, NOCONSENTGIVEN).apply();
                con = 0;
               // Log.d("TAG", "done0");
                break;
            case UNKNOWN:
                con = 0;
        }
        return con;
    }

    public int getCon()
    {
        return con;
    }

  /*  private void redirectToPaidVersion() {
        Intent i = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(MARKET_URL_PAID_VERSION));
        context.startActivity(i);
    } */
}

