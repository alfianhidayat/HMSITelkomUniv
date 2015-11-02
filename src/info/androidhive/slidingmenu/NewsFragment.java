package info.androidhive.slidingmenu;



import parser.AndroidXMLParsingActivity;
import parser.CheckConnection;
import android.app.Application;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class NewsFragment extends Fragment {
	
	
	public NewsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        boolean online = CheckConnection.isOnline(getActivity());
        if(online){
        	Intent intent = new Intent(getActivity(), AndroidXMLParsingActivity.class);
            startActivity(intent);
        }else{
        	Toast t = Toast.makeText(getActivity(), "Check your connection", 1000);
        	t.show();
        }
        
        return rootView;
    }
	

	
	
}
