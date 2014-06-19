/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jiubang.ggheart.apps.desks.diy.frames.screen;

import java.lang.ref.SoftReference;
import java.net.URISyntaxException;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.BaseColumns;
import android.provider.LiveFolders;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gau.go.launcherex.R;
import com.go.util.Utilities;
import com.go.util.graphics.FastBitmapDrawable;
import com.jiubang.core.message.IMessageHandler;
import com.jiubang.core.message.IMsgType;
import com.jiubang.ggheart.apps.desks.diy.IDiyMsgIds;
import com.jiubang.ggheart.data.info.ScreenLiveFolderInfo;

class LiveFolderAdapter extends CursorAdapter {
	private boolean mIsList;
	private LayoutInflater mInflater;
	private IMessageHandler mMessageHandler;

	private final HashMap<String, Drawable> mIcons = new HashMap<String, Drawable>();
	private final HashMap<Long, SoftReference<Drawable>> mCustomIcons = new HashMap<Long, SoftReference<Drawable>>();

	LiveFolderAdapter(IMessageHandler messageHandler, Context context, ScreenLiveFolderInfo info,
			Cursor cursor) {
		super(context, cursor, true);
		mMessageHandler = messageHandler;
		// mIsList = info.mDisplayMode == LiveFolders.DISPLAY_MODE_LIST;
		mIsList = true;// liveFolder的布局都是列表的形式
		mInflater = LayoutInflater.from(context);

		if (mMessageHandler != null) {
			mMessageHandler.handleMessage(this, IMsgType.SYNC, IDiyMsgIds.SCREEN_FOLDER_EVENT,
					IScreenFolder.START_MANAGING_CURSOR, getCursor(), null);
		}
	}

	static Cursor query(Context context, ScreenLiveFolderInfo info) {
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(info.mUri, null, null, null,
					LiveFolders.NAME + " ASC");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cursor;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view;
		final ViewHolder holder = new ViewHolder();
		if (mInflater == null) {
			mInflater = LayoutInflater.from(context);
		}

		if (!mIsList) {
			view = mInflater.inflate(R.layout.application_boxed, parent, false);
		} else {
			view = mInflater.inflate(R.layout.application_list, parent, false);
			holder.description = (TextView) view.findViewById(R.id.description);
			holder.icon = (ImageView) view.findViewById(R.id.icon);
		}

		holder.name = (TextView) view.findViewById(R.id.name);
		holder.idIndex = cursor.getColumnIndexOrThrow(BaseColumns._ID);
		holder.nameIndex = cursor.getColumnIndexOrThrow(LiveFolders.NAME);
		holder.descriptionIndex = cursor.getColumnIndex(LiveFolders.DESCRIPTION);
		holder.intentIndex = cursor.getColumnIndex(LiveFolders.INTENT);
		holder.iconBitmapIndex = cursor.getColumnIndex(LiveFolders.ICON_BITMAP);
		holder.iconResourceIndex = cursor.getColumnIndex(LiveFolders.ICON_RESOURCE);
		holder.iconPackageIndex = cursor.getColumnIndex(LiveFolders.ICON_PACKAGE);

		view.setTag(holder);

		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final ViewHolder holder = (ViewHolder) view.getTag();

		holder.id = cursor.getLong(holder.idIndex);
		final Drawable icon = loadIcon(context, cursor, holder);

		holder.name.setText(cursor.getString(holder.nameIndex));

		if (!mIsList) {
			holder.name.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
		} else {
			final boolean hasIcon = icon != null;
			holder.icon.setVisibility(hasIcon ? View.VISIBLE : View.GONE);
			if (hasIcon) {
				holder.icon.setImageDrawable(icon);
			}

			if (holder.descriptionIndex != -1) {
				final String description = cursor.getString(holder.descriptionIndex);
				if (description != null) {
					holder.description.setText(description);
					holder.description.setVisibility(View.VISIBLE);
				} else {
					holder.description.setVisibility(View.GONE);
				}
			} else {
				holder.description.setVisibility(View.GONE);
			}
		}

		if (holder.intentIndex != -1) {
			try {
				holder.intent = Intent.parseUri(cursor.getString(holder.intentIndex), 0);
			} catch (URISyntaxException e) {
				// Ignore
			}
		} else {
			holder.useBaseIntent = true;
		}
	}

	private Drawable loadIcon(Context context, Cursor cursor, ViewHolder holder) {
		Drawable icon = null;
		byte[] data = null;

		if (holder.iconBitmapIndex != -1) {
			data = cursor.getBlob(holder.iconBitmapIndex);
		}

		if (data != null) {
			final SoftReference<Drawable> reference = mCustomIcons.get(holder.id);
			if (reference != null) {
				icon = reference.get();
			}

			if (icon == null) {
				try {
					// TODO 如果发生索引错乱，可以考虑默认图
					final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
					icon = new FastBitmapDrawable(Utilities.createBitmapThumbnail(bitmap, context));
					mCustomIcons.put(holder.id, new SoftReference<Drawable>(icon));
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (holder.iconResourceIndex != -1 && holder.iconPackageIndex != -1) {
			final String resource = cursor.getString(holder.iconResourceIndex);
			icon = mIcons.get(resource);
			if (icon == null) {
				try {
					final PackageManager packageManager = context.getPackageManager();
					Resources resources = packageManager.getResourcesForApplication(cursor
							.getString(holder.iconPackageIndex));
					final int id = resources.getIdentifier(resource, null, null);
					icon = Utilities.createIconThumbnail(resources.getDrawable(id), context);
					mIcons.put(resource, icon);
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return icon;
	}

	void cleanup() {
		for (Drawable icon : mIcons.values()) {
			icon.setCallback(null);
		}
		mIcons.clear();

		for (SoftReference<Drawable> icon : mCustomIcons.values()) {
			final Drawable drawable = icon.get();
			if (drawable != null) {
				drawable.setCallback(null);
			}
		}
		mCustomIcons.clear();

		final Cursor cursor = getCursor();
		if (cursor != null) {
			try {
				cursor.close();
			} finally {
				if (mMessageHandler != null) {
					mMessageHandler.handleMessage(this, IMsgType.SYNC,
							IDiyMsgIds.SCREEN_FOLDER_EVENT, IScreenFolder.STOP_MANAGING_CURSOR,
							cursor, null);
				}
			}
		}
	}

	static class ViewHolder {
		TextView name;
		TextView description;
		ImageView icon;

		Intent intent;
		long id;
		boolean useBaseIntent;

		int idIndex;
		int nameIndex;
		int descriptionIndex = -1;
		int intentIndex = -1;
		int iconBitmapIndex = -1;
		int iconResourceIndex = -1;
		int iconPackageIndex = -1;
	}
}