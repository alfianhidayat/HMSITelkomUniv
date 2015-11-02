package parser;

	import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

	import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

	import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

	public class XMLParser {

		// constructor
		public XMLParser() {

		}

		/**
		 * Getting XML from URL making HTTP request
		 * @param url string
		 * */
		public String getXmlFromUrl(String url) {
			String xml = null;

			try {
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				xml = EntityUtils.toString(httpEntity);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// return XML
			return xml;
		}

		/**
		 * Getting XML DOM element
		 * @param XML string
		 * */
		public Document getDomElement(String xml){
			Document doc = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {

				DocumentBuilder db = dbf.newDocumentBuilder();

				InputSource is = new InputSource();
					is.setCharacterStream(new StringReader(xml));
					doc = db.parse(is);

				} catch (ParserConfigurationException e) {
					Log.e("Error: ", e.getMessage());
					return null;
				} catch (SAXException e) {
					Log.e("Error: ", e.getMessage());
					return null;
				} catch (IOException e) {
					Log.e("Error: ", e.getMessage());
					return null;
				}

				return doc;
		}

		/** Getting node value
		  * @param elem element
		  */
		 public final String getElementValue( Node elem ) {
			 Node child;
			 if( elem != null){
				 if (elem.hasChildNodes()){
					 for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
						 if( child.getNodeType() == Node.TEXT_NODE || ( child.getNodeType() == Node.CDATA_SECTION_NODE ) ){
	//	                	 String deString = child.getNodeValue().replaceAll("</(.*?)> | <(.*?)>","");
	//	                	 Log.e("Haloo", deString);
							 return child.getNodeValue();
						 }
					 }
				 }
			 }
			 return "";
		 }

		 /**
		  * Getting node value
		  * @param Element node
		  * @param key string
		  * */
		 public String getValue(Element item, String str) {
				NodeList n = item.getElementsByTagName(str);
				String a = this.getElementValue(n.item(0));
	//			Log.e("Haloooooooooooooo", a);
				return this.getElementValue(n.item(0));
		}
		 
		public String getRSSLinkFromURL(String url) {
				// RSS url
				String rss_url = null;

				try {
					// Using JSoup library to parse the html source code
					org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
					// finding rss links which are having link[type=application/rss+xml]
					org.jsoup.select.Elements links = doc
							.select("link[type=application/rss+xml]");
					
					Log.d("No of RSS links found", " " + links.size());
					
					// check if urls found or not
					if (links.size() > 0) {
						rss_url = links.get(0).attr("href").toString();
					} else {
						// finding rss links which are having link[type=application/rss+xml]
						org.jsoup.select.Elements links1 = doc
								.select("link[type=application/atom+xml]");
						if(links1.size() > 0){
							rss_url = links1.get(0).attr("href").toString();	
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}

				// returing RSS url
				return rss_url;
			}
	}
