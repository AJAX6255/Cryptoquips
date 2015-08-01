package com.henningta.cryptoquips.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.henningta.cryptoquips.utils.AppController;
import com.henningta.cryptoquips.data.CryptoquipData;
import com.henningta.cryptoquips.data.DataAdapter;
import com.henningta.cryptoquips.views.EmptyRecyclerView;
import com.henningta.cryptoquips.utils.PrecachingLayoutManager;
import com.henningta.cryptoquips.R;
import com.henningta.cryptoquips.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	public static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// init toolbar
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		toolbar.setTitle(R.string.app_name);
		setSupportActionBar(toolbar);

		// init list
		final EmptyRecyclerView quipsList = (EmptyRecyclerView)findViewById(R.id.quips_list);
		quipsList.setHasFixedSize(true);
		RelativeLayout emptyView = (RelativeLayout)findViewById(R.id.empty_view);
		quipsList.setEmptyView(emptyView);

		// init loading thingy
		final ProgressBar progressReq = (ProgressBar)emptyView.findViewById(R.id.progress_req);
		progressReq.setIndeterminate(true);

		// init emptyView text
		final TextView tvNoData = (TextView)emptyView.findViewById(R.id.tv_no_data);

		// use a linear layout manager
		RecyclerView.LayoutManager layoutManager = new PrecachingLayoutManager(this);
		quipsList.setLayoutManager(layoutManager);

		if (!Utils.hasInternetConnection(this)) {
			progressReq.setVisibility(View.GONE);
			tvNoData.setVisibility(View.VISIBLE);
			Toast.makeText(this, "No internet connection available", Toast.LENGTH_SHORT).show();
			return;
		}

		// http strings
		final String url = "http://www.cecildaily.com/diversions/cryptoquip/";

		// http request
		StringRequest strReq = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Document doc = Jsoup.parse(response);
						Elements div = doc.select("div#center-one-index");
						Elements ul = div.select("ul.blox-recent-list");
						Elements listElements = ul.select("li");

						// init data list
						ArrayList<CryptoquipData> quipsData = new ArrayList<>();

						for (Element li : listElements) {
							Element link = li.select("a").get(0);
							Element img = link.select("img").get(0);

							String title = Utils.formatCryptoquipTitle(li.text());
							String imgSrc = img.attr("src");
							if (imgSrc.contains("?")) {
								imgSrc = imgSrc.substring(0, imgSrc.indexOf("?"));
							}

							CryptoquipData cryptoquipData = new CryptoquipData(title, imgSrc);
							quipsData.add(cryptoquipData);
						}

						// set adapter
						DataAdapter dataAdapter = new DataAdapter(quipsData);
						quipsList.setAdapter(dataAdapter);
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				progressReq.setVisibility(View.GONE);
				tvNoData.setVisibility(View.VISIBLE);
				Toast.makeText(MainActivity.this, "Error reading data", Toast.LENGTH_SHORT).show();
			}
		});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(strReq, TAG);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		AppController.getInstance().cancelPendingRequests(TAG);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_about:
				AboutDialog dialog = new AboutDialog();
				dialog.show(getFragmentManager(), AboutDialog.TAG);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

}
