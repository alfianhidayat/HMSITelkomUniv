package parser;
import info.androidhive.slidingmenu.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.EventLogTags.Description;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class SingleMenuItemActivity  extends Activity {
	
	// XML node keys
	static final String KEY_NAME = "title";
	static final String KEY_DATE = "pubDate";
	static final String KEY_DESC = "content:encoded";
	private ShareActionProvider mShareActionProvider;
	
	private String name = null;
	private String date = null;
	private String description = null;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get XML values from previous intent
        name = in.getStringExtra(KEY_NAME);
        date = in.getStringExtra(KEY_DATE);
        description = in.getStringExtra(KEY_DESC);
//        String name_date = "<h3>"+ name +"</h3>"+cost;
//        WebView view = (WebView) findViewById(R.id.webview);
        WebView view = (WebView) findViewById(R.id.web_description);
        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblDate = (TextView) findViewById(R.id.date_label);
//        TextView lblDesc = (TextView) findViewById(R.id.description);
        
//        lblDesc.setText(android.text.Html.fromHtml(description).toString());
        lblName.setText(name);
        lblDate.setText(date);

        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setBuiltInZoomControls(true);
        view.loadData(description, "text/html; charset=UTF-8", null);
        
//        view.loadData(description, "text/html", "UTF-8");
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
	
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{   
	    onBackPressed();
	    return true;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.action_share, menu);
		MenuItem item = menu.findItem(R.id.menu_item_share);
		mShareActionProvider = (ShareActionProvider)item.getActionProvider();
		
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setAction(Intent.ACTION_SEND);
		i.setType("text/plain");
		String message = "["+name+"]\n"+"Date:"+date+"\n\n";
		message += android.text.Html.fromHtml(description).toString();
		message += "\n\nSend From HMSI-Tel Android Application";
		i.putExtra(Intent.EXTRA_TEXT, message);
		mShareActionProvider.setShareIntent(i);
		
		return super.onCreateOptionsMenu(menu);
	}
}
