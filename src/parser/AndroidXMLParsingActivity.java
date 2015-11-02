package parser;

import info.androidhive.slidingmenu.MainActivity;
import info.androidhive.slidingmenu.R;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidXMLParsingActivity extends ListActivity  {

	// All static variables
	static final String URL = "http://hmsi.bis.telkomuniversity.ac.id/feed";
	// XML node keys
	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "title";
	static final String KEY_DATE = "pubDate";
	static final String KEY_DESC = "content:encoded";
	static final String KEY_URL_IMG = "img";
	static final String KEY_DESCRIPTION = "";
	String tmpDesc = "";
	final ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
	

	private ProgressDialog pDialog;
//	private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
        new loadRSS().execute(URL);
		
		

		// selecting single ListView item
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String date = ((TextView) view.findViewById(R.id.cost)).getText().toString();
				String description = menuItems.get(position).get(KEY_DESCRIPTION).toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(KEY_NAME, name);
				in.putExtra(KEY_DATE, date);
				in.putExtra(KEY_DESC, description);
				startActivity(in);
			}
		});
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{  
		Intent i = null;
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			i = new Intent(this, MainActivity.class);
			startActivity(i);
			break;
			case R.id.action_refresh:
				new loadRSS().execute(URL);
				break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	class loadRSS extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			pDialog = new ProgressDialog(AndroidXMLParsingActivity.this);
			pDialog.setMessage("Loading recent articles...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String url = params[0];
			
			XMLParser parser = new XMLParser();
			
			String xml = parser.getXmlFromUrl(URL); // getting XML
			Document doc = parser.getDomElement(xml); // getting DOM element

			NodeList nl = doc.getElementsByTagName(KEY_ITEM);
			// looping through all item nodes <item>
			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				// adding each child node to HashMap key => value
				map.put(KEY_ID, parser.getValue(e, KEY_ID));
				map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
				String date = parser.getValue(e, KEY_DATE);
				date = date.substring(0, date.length()-5);
				map.put(KEY_DATE, date);
				tmpDesc = parser.getValue(e, KEY_DESC);
				String desc = "";
				if(tmpDesc.length()>100){
					desc = android.text.Html.fromHtml(tmpDesc).toString();
					desc = desc.substring(0, 120) + "..";
					desc = android.text.Html.fromHtml(desc).toString();
//					Matcher m = REMOVE_TAGS.matcher(desc);
//					desc = Jsoup.parse(desc).text();
//					desc = m.replaceAll("");
				}
				map.put(KEY_DESC, desc);
				map.put(KEY_DESCRIPTION, tmpDesc);

				// adding HashList to ArrayList
				menuItems.add(map);
			}

			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed items into listview
					 * */
					// Adding menuItems to ListView
					ListAdapter adapter = new SimpleAdapter(AndroidXMLParsingActivity.this, menuItems,
							R.layout.list_item,
							new String[] { KEY_NAME, KEY_DESC, KEY_DATE }, new int[] {
									R.id.name, R.id.description, R.id.cost });

					setListAdapter(adapter);
				}
			});
			return null;
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			pDialog.dismiss();
//			super.onPostExecute(result);
			
		}
		
	}
}