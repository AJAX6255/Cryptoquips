package com.henningta.cryptoquips;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class PrecachingLayoutManager extends LinearLayoutManager {

	private static final int DEFAULT_EXTRA_LAYOUT_SPACE = 600;
	private int extraLayoutSpace = -1;
	private Context context;

	public PrecachingLayoutManager(Context context) {
		super(context);
		this.context = context;
	}

	public PrecachingLayoutManager(Context context, int extraLayoutSpace) {
		super(context);
		this.context = context;
		this.extraLayoutSpace = extraLayoutSpace;
	}

	public PrecachingLayoutManager(Context context, int orientation, boolean reverseLayout) {
		super(context, orientation, reverseLayout);
		this.context = context;
	}

	public void setExtraLayoutSpace(int extraLayoutSpace) {
		this.extraLayoutSpace = extraLayoutSpace;
	}

	@Override
	protected int getExtraLayoutSpace(RecyclerView.State state) {
		if (extraLayoutSpace > 0) {
			return extraLayoutSpace;
		}
		return DEFAULT_EXTRA_LAYOUT_SPACE;
	}
}
