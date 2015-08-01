package com.henningta.cryptoquips.data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.henningta.cryptoquips.utils.AppController;
import com.henningta.cryptoquips.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

	private ArrayList<CryptoquipData> dataset;
	private ImageLoader imageLoader;

	public DataAdapter(ArrayList<CryptoquipData> dataset) {
		this.dataset = dataset;
		this.imageLoader = AppController.getInstance().getImageLoader();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.quip_card,
				parent,
				false);
		return new ViewHolder(v, imageLoader);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		CryptoquipData entry = dataset.get(position);
		holder.bindEntry(entry);
	}

	@Override
	public int getItemCount() {
		return dataset.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		private ImageLoader imageLoader;
		private ImageView imgSrc;
		private TextView tvTitle;

		public ViewHolder(View view, ImageLoader imageLoader) {
			super(view);
			this.imageLoader = imageLoader;
			this.imgSrc = (ImageView)view.findViewById(R.id.img_src);
			this.tvTitle = (TextView)view.findViewById(R.id.tv_quip_title);
		}

		private void bindEntry(CryptoquipData entry) {
			tvTitle.setText(entry.getTitle());
			String url = entry.getImgSrc();

			// load image
			imageLoader.get(url, ImageLoader.getImageListener(imgSrc, android.R.color.transparent,
					R.mipmap.ic_fa_frown_o));
		}

	}

}
