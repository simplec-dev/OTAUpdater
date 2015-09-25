package com.simplec.phonegap.plugins.otaupdater;

import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

public class SimpleCOTAWebviewEngine extends SystemWebViewEngine {
	public static final String LOG_TAG = "SimpleCOTAWebviewEngine";

	public SimpleCOTAWebviewEngine(Context context, CordovaPreferences preferences) {        
		super(context, preferences);

        Log.d(LOG_TAG, "SimpleCOTAWebviewEngine(context, preferences)");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        	WebView.enableSlowWholeDocumentDraw();
	        Log.d(LOG_TAG, "enableSlowWholeDocumentDraw()");
        }
	}

	public SimpleCOTAWebviewEngine(SystemWebView webView) {
		super(webView);

        Log.d(LOG_TAG, "SimpleCOTAWebviewEngine(webview)");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        	WebView.enableSlowWholeDocumentDraw();
	        Log.d(LOG_TAG, "enableSlowWholeDocumentDraw()");
        }
	}

	@Override
	public void loadUrl(String url, boolean clearNavigationStack) {
		String newUrl = url;
		if (url.startsWith("file:///android_asset/www/index.html")) {
			newUrl = "file:///android_asset/www/error.html";
		}
		
		Log.d(LOG_TAG, "navigation to URL: "+newUrl);
		super.loadUrl(newUrl, clearNavigationStack);
	}
	
}
