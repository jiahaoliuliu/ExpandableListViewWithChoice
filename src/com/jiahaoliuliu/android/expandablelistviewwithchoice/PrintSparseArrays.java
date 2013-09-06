package com.jiahaoliuliu.android.expandablelistviewwithchoice;

import android.os.Bundle;
import android.app.Activity;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.widget.TextView;

public class PrintSparseArrays {

	public static String sparseArrayToString(SparseArray<?> sparseArray) {
	    StringBuilder result = new StringBuilder();
	    if (sparseArray == null) {
	        return "null";
	    }

	    result.append('{');
	    for (int i = 0; i < sparseArray.size(); i++) {
	        result.append(sparseArray.keyAt(i));
	        result.append(" => ");
	        if (sparseArray.valueAt(i) == null) {
	            result.append("null");
	        } else {
	        	Object object = sparseArray.valueAt(i);
	        	if (object instanceof SparseBooleanArray) {
	        		result.append(sparseBooleanArrayToString((SparseBooleanArray)object));
	        	} else {
		            result.append(sparseArray.valueAt(i).toString());
	        	} 
	        }
	        if(i < sparseArray.size() - 1) {
	            result.append(", ");
	        }
	    }
	    result.append('}');
	    return result.toString();
	}

	public static String sparseBooleanArrayToString(SparseBooleanArray sparseBooleanArray) {
	    StringBuilder result = new StringBuilder();
	    if (sparseBooleanArray == null) {
	        return "null";
	    }

	    result.append('{');
	    for (int i = 0; i < sparseBooleanArray.size(); i++) {
	        result.append(sparseBooleanArray.keyAt(i));
	        result.append(" => ");
	        
	        result.append(sparseBooleanArray.valueAt(i));

	        if(i < sparseBooleanArray.size() - 1) {
	            result.append(", ");
	        }
	    }
	    result.append('}');
	    return result.toString();
	}

}