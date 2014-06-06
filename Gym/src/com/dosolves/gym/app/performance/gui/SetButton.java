package com.dosolves.gym.app.performance.gui;

import java.util.Date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.dosolves.gym.R;
import com.dosolves.gym.app.gui.ActionModeEndingListener;
import com.dosolves.gym.domain.performance.Set;
import com.dosolves.gym.utils.ResourcesUtils;
import com.dosolves.gym.utils.StringUtils;

public class SetButton extends ToggleButton {

	private static final int CENTER_OFFSET_DP = 8;
	private static final float SHADOW_DY_DP = -1f;
	private static final float SHADOW_DX_DP = -1.3f;

	private static final int centerOffsetPx;
	private static final float shadowDyPx;
	private static final float shadowDxPx;
	
	private static final Paint textPaint = new Paint();
	
	
	private Rect clipBounds = new Rect();
	private Rect textBounds = new Rect();
	
	private Set set;
	private SetContextualMenuHandler contextHandler;

	static{
		centerOffsetPx = (int) ResourcesUtils.convertDpToPx(CENTER_OFFSET_DP);
		
		shadowDxPx = ResourcesUtils.convertDpToPx(SHADOW_DX_DP);
		shadowDyPx = ResourcesUtils.convertDpToPx(SHADOW_DY_DP);
		
		textPaint.setTextSize(ResourcesUtils.getDimension(R.dimen.button_text_size));
		textPaint.setShadowLayer(1f, shadowDxPx, shadowDyPx, Color.BLACK);
	}
	
	// Seemingly unused constructor is used by the designer-tool when rendering
	@SuppressWarnings("deprecation")
	public SetButton(Context context) {
		this(context, new Set(1,1,12,50.5,new Date(2000,1,1)), null);		
	}
	
	public SetButton(Context context,Set set, SetContextualMenuHandler contextHandler) {
		super(context);
		this.contextHandler = contextHandler;
		this.set = set;
		
		this.setBackgroundResource(R.drawable.set_button);
		
		setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SetButton.this.contextHandler.setSetCheckedState(SetButton.this.set, isChecked);
			}
		});
		contextHandler.addActionModeEndingListener(new ActionModeEndingListener() {
			
			@Override
			public void onActionModeEnding() {
				SetButton.this.setChecked(false);
			}
			
		});
		
		removeDefaultText();
		
	}
	
	private void removeDefaultText() {
		this.setTextOff("");
		this.setTextOn("");
		this.setChecked(false);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.getClipBounds(clipBounds);
		
		drawReps(canvas);
		
		if(set.getWeight() > 0)
			drawWeight(canvas);
		
	}

	private void drawReps(Canvas canvas) {
		String reps = Integer.toString(set.getReps());
		
		float repsX = findCenterXForString(reps, clipBounds);
		float repsY = findCenterYForLowerText(reps, clipBounds);
		
		textPaint.setColor(Color.LTGRAY);
		canvas.drawText(reps, repsX, repsY, textPaint);
	}

	private void drawWeight(Canvas canvas) {
		String weight = StringUtils.doubleToStringRemoveTrailingZeros(set.getWeight());
		
		float weightX = findCenterXForString(weight, clipBounds) - shadowDxPx; //The shadow makes the darker text look un-aligned
		float weightY = findCenterYForUpperText(clipBounds);
		
		textPaint.setColor(Color.DKGRAY);
		canvas.drawText(weight, weightX, weightY, textPaint);
	}
	
	private float findCenterYForUpperText(Rect rect) {		
		return (rect.height()/2) - centerOffsetPx;
	}
	
	private float findCenterYForLowerText(String text, Rect rect) {
		textPaint.getTextBounds(text, 0, text.length(), textBounds);
		return (rect.height()/2) + (textBounds.height()) + centerOffsetPx;
	}

	private float findCenterXForString(String text, Rect rect) {
		return (rect.width()/2) - (textPaint.measureText(text)/2);
	}
	
}
