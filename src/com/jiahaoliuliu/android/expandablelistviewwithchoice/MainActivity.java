package com.jiahaoliuliu.android.expandablelistviewwithchoice;

import android.os.Bundle;
import android.app.Activity;

import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates expandable lists backed by a Simple Map-based adapter
 */
public class MainActivity extends Activity {

	private static List<Country> countries;
	private ExpandableListView expandableListView;
	private CountryAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LoadCountries();
		expandableListView = (ExpandableListView)findViewById(R.id.expandableListView);
		adapter = new CountryAdapter(this, countries);
		
		expandableListView.setAdapter(adapter);

		// Handle the click when the user clicks an any child
		expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				adapter.setClicked(groupPosition, childPosition);
				return false;
			}
		});
	}
	
	private void LoadCountries() {
	    countries = new ArrayList<Country>();

	    ArrayList<String> citiesAustralia = new ArrayList<String>(
	        Arrays.asList("Brisbane", "Hobart", "Melbourne", "Sydney"));
	    countries.add(new Country("Australia", citiesAustralia));

	    ArrayList<String> citiesChina = new ArrayList<String>(
	        Arrays.asList("Beijing", "Chuzhou", "Dongguan", "Shangzhou"));
	    countries.add(new Country("China", citiesChina));

	    ArrayList<String> citiesIndia = new ArrayList<String>(
	        Arrays.asList("Bombay", "Calcutta", "Delhi", "Madras"));
	    countries.add(new Country("India", citiesIndia));

	    ArrayList<String> citiesNewZealand = new ArrayList<String>(
	        Arrays.asList("Auckland", "Christchurch", "Wellington"));
	    countries.add(new Country("New Zealand", citiesNewZealand));

	    ArrayList<String> citiesRussia = new ArrayList<String>(
	        Arrays.asList("Moscow", "Kursk", "Novosibirsk", "Saint Petersburg"));
	    countries.add(new Country("Russia", citiesRussia));
	  }
}
