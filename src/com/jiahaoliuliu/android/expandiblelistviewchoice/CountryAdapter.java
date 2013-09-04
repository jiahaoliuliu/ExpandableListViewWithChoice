package com.jiahaoliuliu.android.expandiblelistviewchoice;

import android.R;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.CheckedTextView;

import java.util.List;

public class CountryAdapter extends BaseExpandableListAdapter {

	private static final String LOG_TAG = CountryAdapter.class.getSimpleName();
	private List<Country> countries;
	private LayoutInflater inflater;
	private SparseArray<SparseBooleanArray> checkedPositions;
	
	public CountryAdapter(Context context, List<Country> countries) {
		this.countries = countries;
		inflater = LayoutInflater.from(context);
		checkedPositions = new SparseArray<SparseBooleanArray>();
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return countries.get(groupPosition).getCities().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.simple_list_item_multiple_choice, parent, false);
		}

		((CheckedTextView)convertView).setText(getChild(groupPosition, childPosition).toString());

		if (checkedPositions.get(groupPosition) != null) {
			((CheckedTextView)convertView).setChecked(checkedPositions.get(groupPosition).get(childPosition, false));
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return countries.get(groupPosition).getCities().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return countries.get(groupPosition).getName();
	}

	@Override
	public int getGroupCount() {
		return countries.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.simple_expandable_list_item_1, parent, false);
		}
		
		((TextView)convertView).setText(getGroup(groupPosition).toString());
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	/**
	 * Update the list of the positions checked and update the view
	 * @param groupPosition The position of the group which has been checked
	 * @param childPosition The position of the child which has been checked
	 */
	public void setClicked(int groupPosition, int childPosition) {
		SparseBooleanArray checkedChildPositions = checkedPositions.get(groupPosition);
		// if in the group there was not any child checked
		if (checkedChildPositions == null) {
			checkedChildPositions = new SparseBooleanArray();
			// By default, the status of a child is not checked
			// So a click will enable it
			checkedChildPositions.put(childPosition, true);
			checkedPositions.put(groupPosition, checkedChildPositions);
		} else {
			boolean oldState = checkedChildPositions.get(childPosition);
			checkedChildPositions.put(childPosition, !oldState);
		}
		
		// Notify that some data has been changed
		notifyDataSetChanged();
		Log.v(LOG_TAG, "List position updated");
	}

	/**
	 * Method used to get the actual state of the checked lists
	 * @return The list of the all the positions checked
	 */
	public SparseArray<SparseBooleanArray> getCheckedPositions() {
		return checkedPositions;
	}
}
