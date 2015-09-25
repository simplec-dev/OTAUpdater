package com.simplec.phonegap.plugins.otaupdater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
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
		
		    Log.v(LOG_TAG, "getting names");
		    List<String> f = new ArrayList<String>();
		    listAssetFiles("", f);
			for(String f1 : f){
			    Log.v(LOG_TAG, "names: " + f1);
			}

		
		String newUrl = url;
		if (url.startsWith("file:///android_asset/www/index.html")) {
			newUrl = "file:///android_asset/www/error.html";
		}

		try {
			Uri targetUri = resourceApi.remapUri(Uri.parse(newUrl));
			newUrl = targetUri.toString();
		} catch (IllegalArgumentException e) {
			//newUrl = url;
		}
		
		Log.d(LOG_TAG, "navigation to URL: "+newUrl);
		super.loadUrl(newUrl, clearNavigationStack);
	}
	
	public String resolveFileUrl(String url) {
		if (url.startsWith("file:///android_asset/")) {
			String f = stripFileProtocol(url).substring("/android_asset/".length());

			AssetFileDescriptor fd = null;
			try {
				fd = cordova.getActivity().getAssets().openFd(f);
		//		return fd.getFileDescriptor().
			} catch (Exception e) {
				Log.v(LOG_TAG, "error: " + e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String stripFileProtocol(String uriString) {
		if (uriString.startsWith("file://")) {
			return Uri.parse(uriString).getPath();
		}
		return uriString;
	}
	
	private boolean listAssetFiles(String path, List<String> files) {

	    String [] list;
	    try {
	        list = cordova.getActivity().getAssets().list(path);
	        if (list.length > 0) {
	            // This is a folder
	            for (String file : list) {
	                if (!listAssetFiles(path + "/" + file, files))
	                    return false;
	            }
	        } else {
	        	files.add(path);
	        }
	    } catch (IOException e) {
	        return false;
	    }

	    return true; 
	} 
}
