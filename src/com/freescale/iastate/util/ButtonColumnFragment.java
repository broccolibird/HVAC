/**
 * 
 */
package com.freescale.iastate.util;

import com.freescale.iastate.hvac.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Namara
 * 
 */
public class ButtonColumnFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.column_button_fragment,
				container, false);

		Button button1 = (Button) view.findViewById(R.id.col_button1);
		button1.setText("Day");
		button1.setOnClickListener(dayClick);
		Button button2 = (Button) view.findViewById(R.id.col_button2);
		button2.setText("Week");
		button2.setOnClickListener(weekClick);
		Button button3 = (Button) view.findViewById(R.id.col_button3);
		button3.setText("Month");
		button3.setOnClickListener(monthClick);
		Button button4 = (Button) view.findViewById(R.id.col_button4);
		button4.setText("Year");
		button4.setOnClickListener(yearClick);

		return view;
	}

	private OnClickListener dayClick = new OnClickListener() {
		public void onClick(View v) {
			Toast.makeText(getActivity(),
					"Wake up in the morning and whatdyawantmost? day!",
					Toast.LENGTH_LONG).show();
		}

	};

	private OnClickListener weekClick = new OnClickListener() {
		public void onClick(View v) {
			Toast.makeText(getActivity(),
					"Wake up in the morning and whatdyawantmost? week!",
					Toast.LENGTH_LONG).show();
		}

	};
	
	private OnClickListener monthClick = new OnClickListener() {
		public void onClick(View v) {
			Toast.makeText(getActivity(),
					"Wake up in the morning and whatdyawantmost? month!",
					Toast.LENGTH_LONG).show();
		}

	};
	
	private OnClickListener yearClick = new OnClickListener() {
		public void onClick(View v) {
			Toast.makeText(getActivity(),
					"Wake up in the morning and whatdyawantmost? year!",
					Toast.LENGTH_LONG).show();
		}

	};
}