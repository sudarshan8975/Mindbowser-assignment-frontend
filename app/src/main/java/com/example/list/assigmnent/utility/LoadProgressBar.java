package com.example.list.assigmnent.utility;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;
import com.example.list.assigmnent.R;

public class LoadProgressBar extends Dialog
{

	public LoadProgressBar(Activity activity)
	{
		super(activity);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		setCancelable(false);
	}//end cons LoadProgressBar

	protected void onCreate(Bundle bdn)
	{
			super.onCreate(bdn);
			setContentView(R.layout.dialog_box);
			ProgressBar p_bar=(ProgressBar)findViewById(R.id.load_pro_bar);
			p_bar.setMax(10);
			p_bar.setProgress(1);
			p_bar.setVisibility(ProgressBar.VISIBLE);
			
	}//end onCreate
	
	
}//end LoadProgressBar
