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
public class ButtonColumnFragment extends Fragment implements OnClickListener{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    View view = inflater.inflate(R.layout.column_button_fragment,
            container, false);

	Button button1 = (Button) view.findViewById(R.id.col_button1);
	button1.setOnClickListener(this);
	Button button2 = (Button) view.findViewById(R.id.col_button2);
	button2.setOnClickListener(this);
	Button button3 = (Button) view.findViewById(R.id.col_button3);
	button3.setOnClickListener(this);
	Button button4 = (Button) view.findViewById(R.id.col_button4);
	button4.setOnClickListener(this);

	return view;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View arg0) {
		Toast.makeText(getActivity(), "BleepBloop", Toast.LENGTH_LONG).show();
		
	}
}