//package CustomAdapters;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//
//import ORClasses.CSVTransaction;
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//
//public class CSVTransactionAdapter extends ArrayAdapter<CSVTransaction>  {
//
//	Context ctx;
//	
//	public CSVTransactionAdapter(Context ctx, int textResourceId) {
//		// TODO Auto-generated constructor stub
//		super(ctx, textResourceId);
//		this.ctx = ctx;
//		try {
//			LoadArrayFromFile();
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private void LoadArrayFromFile() throws NumberFormatException, IOException
//	{
//		InputStream in = null;
//		try {
//			in = ctx.getAssets().open(CSV_PATH);
//			BufferedReader bufReader = new BufferedReader(new InputStreamReader(in));
//			String line;
//			while((line = bufReader.readLine()) != null)
//			{
//				String[] transactionData = line.split(",");
//				CSVTransaction currentTransaction = new CSVTransaction();
//				currentTransaction.set_date(transactionData[0]);
//				currentTransaction.set_storeName(transactionData[1]);
//				currentTransaction.set_amount(Double.valueOf(transactionData[2]));
//				this.add(currentTransaction);
//			}
//			bufReader.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		in.close();
//
//	}
//	
//	@Override
//	public View getView(final int pos, View convertView, final ViewGroup parent)
//	{
//		TextView txtView = (TextView) convertView;
//		if(txtView == null)
//		{
//			txtView = new TextView(parent.getContext());
//		}
//		txtView.setText(getItem(pos).get_storeName());
//		return txtView;
//	}
//
//}
