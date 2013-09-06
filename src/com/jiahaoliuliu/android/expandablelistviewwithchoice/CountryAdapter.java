package com.jiahaoliuliu.android.expandablelistviewwithchoice;

import android.R;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.CheckedTextView;

import java.util.List;

public class CountryAdapter extends BaseExpandableListAdapter {

	/**
	 * Multiple choice for all the groups
	 */
	public static final int CHOICE_MODE_MULTIPLE = AbsListView.CHOICE_MODE_MULTIPLE;

	// TODO: Coverage this case
	// Example:
	//https://github.com/commonsguy/cw-omnibus/blob/master/ActionMode/ActionModeMC/src/com/commonsware/android/actionmodemc/ActionModeDemo.java
	public static final int CHOICE_MODE_MULTIPLE_MODAL = AbsListView.CHOICE_MODE_MULTIPLE_MODAL;

	/**
	 * No child could be selected
	 */
	public static final int CHOICE_MODE_NONE = AbsListView.CHOICE_MODE_NONE;

	/**
	 * One single choice per group
	 */
	public static final int CHOICE_MODE_SINGLE_PER_GROUP = AbsListView.CHOICE_MODE_SINGLE;

	/**
	 * One single choice for all the groups
	 */
	public static final int CHOICE_MODE_SINGLE_ABSOLUTE = 10001;

	private static final String LOG_TAG = CountryAdapter.class.getSimpleName();
	private List<Country> countries;
	private LayoutInflater inflater;
	private SparseArray<SparseBooleanArray> checkedPositions;

	// The default choice is the multiple one
	private int choiceMode = CHOICE_MODE_MULTIPLE;;
	
	public CountryAdapter(Context context, List<Country> countries) {
		this.countries = countries;
		inflater = LayoutInflater.from(context);
		checkedPositions = new SparseArray<SparseBooleanArray>();
	}

	public CountryAdapter(Context context, List<Country> countries, int choiceMode) {
		this(context, countries);
		// For now the choice mode CHOICE_MODE_MULTIPLE_MODAL
		// is not implemented
		if (choiceMode == CHOICE_MODE_MULTIPLE_MODAL) {
			throw new RuntimeException("The choice mode CHOICE_MODE_MULTIPLE_MODAL " +
					"has not implemented yet");
		}
		this.choiceMode = choiceMode;
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
		Log.v(LOG_TAG, "Getting the child view for child " + childPosition + " in the group " + groupPosition);

		if (convertView == null) {
			Log.v(LOG_TAG, "\t The convert view was null");
			convertView = inflater.inflate(R.layout.simple_list_item_multiple_choice, parent, false);
		}

		((CheckedTextView)convertView).setText(getChild(groupPosition, childPosition).toString());

		if (checkedPositions.get(groupPosition) != null) {
			Log.v(LOG_TAG, "\t \t The child checked position has been saved");
			boolean isChecked = checkedPositions.get(groupPosition).get(childPosition);
			Log.v(LOG_TAG, "\t \t \t Is child checked: " + isChecked);
			((CheckedTextView)convertView).setChecked(isChecked);
		// If it does not exist, mark the checkBox as false
		} else {
			((CheckedTextView)convertView).setChecked(false);
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
		switch (choiceMode) {
		case CHOICE_MODE_MULTIPLE:
			SparseBooleanArray checkedChildPositionsMultiple = checkedPositions.get(groupPosition);
			// if in the group there was not any child checked
			if (checkedChildPositionsMultiple == null) {
				checkedChildPositionsMultiple = new SparseBooleanArray();
				// By default, the status of a child is not checked
				// So a click will enable it
				checkedChildPositionsMultiple.put(childPosition, true);
				checkedPositions.put(groupPosition, checkedChildPositionsMultiple);
			} else {
				boolean oldState = checkedChildPositionsMultiple.get(childPosition);
				checkedChildPositionsMultiple.put(childPosition, !oldState);
			}
			break;
		// TODO: Implement it
		case CHOICE_MODE_MULTIPLE_MODAL:
			throw new RuntimeException("The choice mode CHOICE_MODE_MULTIPLE_MODAL " +
					"has not implemented yet");
		case CHOICE_MODE_NONE:
			checkedPositions.clear();
			break;
		case CHOICE_MODE_SINGLE_PER_GROUP:
			SparseBooleanArray checkedChildPositionsSingle = checkedPositions.get(groupPosition);
			// If in the group there was not any child checked
			if (checkedChildPositionsSingle == null) {
				checkedChildPositionsSingle = new SparseBooleanArray();
				// By default, the status of a child is not checked
				checkedChildPositionsSingle.put(childPosition, true);
				checkedPositions.put(groupPosition, checkedChildPositionsSingle);
			} else {
				boolean oldState = checkedChildPositionsSingle.get(childPosition);
				// If the old state was false, set it as the unique one which is true
				if (!oldState) {
					checkedChildPositionsSingle.clear();
					checkedChildPositionsSingle.put(childPosition, !oldState);
				} // Else does not allow the user to uncheck it
			}
			break;
		// This mode will remove all the checked positions from other groups
		// and enable just one from the selected group
		case CHOICE_MODE_SINGLE_ABSOLUTE:
			checkedPositions.clear();
			SparseBooleanArray checkedChildPositionsSingleAbsolute = new SparseBooleanArray();
			checkedChildPositionsSingleAbsolute.put(childPosition, true);
			checkedPositions.put(groupPosition, checkedChildPositionsSingleAbsolute);
			break;
		}

		// Notify that some data has been changed
		notifyDataSetChanged();
		Log.v(LOG_TAG, "List position updated");
		Log.v(LOG_TAG, PrintSparseArrays.sparseArrayToString(checkedPositions));
	}

	public int getChoiceMode() {
		return choiceMode;
	}

	/**
	 * Set a new choice mode. This will remove
	 * all the checked positions
	 * @param choiceMode
	 */
	public void setChoiceMode(int choiceMode) {
		this.choiceMode = choiceMode;
		// For now the choice mode CHOICE_MODEL_MULTIPLE_MODAL
		// is not implemented
		if (choiceMode == CHOICE_MODE_MULTIPLE_MODAL) {
			throw new RuntimeException("The choice mode CHOICE_MODE_MULTIPLE_MODAL " +
					"has not implemented yet");
		}
		checkedPositions.clear();
		Log.v(LOG_TAG, "The choice mode has been changed. Now it is " + this.choiceMode);
	}

	/**
	 * Method used to get the actual state of the checked lists
	 * @return The list of the all the positions checked
	 */
	public SparseArray<SparseBooleanArray> getCheckedPositions() {
		return checkedPositions;
	}

}
