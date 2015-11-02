package info.androidhive.slidingmenu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class ProfilFragment extends Fragment {

	public ProfilFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_profil, container,
				false);
		return rootView;
	}
}
