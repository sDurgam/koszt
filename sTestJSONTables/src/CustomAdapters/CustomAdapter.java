package CustomAdapters;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.s_expensemanager.MainActivity;
import com.example.s_expensemanager.R;


import ORClasses.Transaction;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class CustomAdapter extends ArrayAdapter<Transaction> 
{
	Context context;
	int layoutResourceId;
	ArrayList<Transaction> items = new ArrayList<Transaction>();
	ArrayAdapter<String> adapter;

	public CustomAdapter(Context context, int resource, ArrayList<Transaction> data)
	{
		super(context, resource, data);
		this.context = context;
		this.layoutResourceId = resource;
		this.items = data;
		adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, android.R.id.text1, MainActivity.categories);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View rowView = convertView;
		storeAttr storeAttrs = null;
		if(rowView == null)
		{
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			rowView = inflater.inflate(layoutResourceId, parent, false);
			storeAttrs = new storeAttr();
			storeAttrs.storeName = (TextView) rowView.findViewById(R.id.storeName);
			storeAttrs.categoryNames = (Spinner) rowView.findViewById(R.id.categoriesSpinner);
			rowView.setTag(storeAttrs);
		}
		else
		{
			storeAttrs = (storeAttr) rowView.getTag();
		}
		
		rowView.findViewById(R.id.listViewTransactions);

		Transaction item = items.get(position);
		if(item.getStoreName() != null)
			storeAttrs.storeName.setText(item.getStoreName());
		//populate spinner as well
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		storeAttrs.categoryNames.setAdapter(adapter);
		return rowView;
	}
	


	public static class storeAttr
	{
		TextView storeName;
		Spinner categoryNames;
	}

}
