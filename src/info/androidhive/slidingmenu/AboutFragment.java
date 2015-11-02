package info.androidhive.slidingmenu;

import parser.AndroidXMLParsingActivity;
import android.app.Fragment;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class AboutFragment extends Fragment {
	
	public NotificationManager manager = null;
	
	public AboutFragment(){}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        return rootView;
    }
}
