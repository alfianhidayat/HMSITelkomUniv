package info.androidhive.slidingmenu;

import parser.CheckConnection;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class FileFragment extends Fragment{
	
	private View rootView;
	private WebView view;
	public FileFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_file, container, false);
        
        
        boolean online = CheckConnection.isOnline(getActivity());
        if(online){
        	
	        	Toast t = Toast.makeText(getActivity(), "Waiting..", Toast.LENGTH_LONG);
	        	t.show();
	            view = (WebView) rootView.findViewById(R.id.webfile);
	            view.getSettings().setJavaScriptEnabled(true);
	            view.setWebViewClient(new WebViewClient());
	            view.setDownloadListener(new DownloadListener() {
					
					@Override
					public void onDownloadStart(String url, String userAgent,
							String contentDisposition, String mimetype, long contentLength) {
	                    Intent intent = new Intent(Intent.ACTION_VIEW); 
	                    intent.setData(Uri.parse(url));
	                    startActivity(intent); 
						
					}
				});
	            view.loadUrl("http://filehmsi.bis.telkomuniversity.ac.id");
        }else{
        	Toast t = Toast.makeText(getActivity(), "Check your connection", 1000);
        	t.show();
        	Intent i = new Intent(getActivity(), MainActivity.class);
        	startActivity(i);
        }
        return rootView;
    }


}
