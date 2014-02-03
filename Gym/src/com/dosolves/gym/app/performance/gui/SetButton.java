package com.dosolves.gym.app.performance.gui;

import java.util.Date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.Button;

import com.dosolves.gym.domain.performance.Set;

public class SetButton extends Button {

	private Rect clipBounds;
	private Paint paint = new Paint();
	
	private Set set;
	private SetClickedCallback callback;

	public SetButton(Context context) {
		this(context, new Set(1,1,12,50.5,new Date(2000,1,1)));		
	}
	
	public SetButton(Context context,Set set) {
		super(context);
		this.set = set;
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callback.onSetClicked(SetButton.this.set);
			}
		});
	}
	
	public void setSetClickedCallback(SetClickedCallback callback){
		this.callback = callback;		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.getClipBounds(clipBounds);		
		canvas.drawText(Integer.toString(set.getReps()), clipBounds.left + 2, clipBounds.top + 2, paint);
		canvas.drawText(Integer.toString(set.getReps()), clipBounds.left + 2, clipBounds.top + 22, paint);
	}
	
}
