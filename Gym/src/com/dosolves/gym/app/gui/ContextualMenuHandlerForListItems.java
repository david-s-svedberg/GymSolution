package com.dosolves.gym.app.gui;

import android.view.ActionMode;
import android.widget.AbsListView.MultiChoiceModeListener;


public class ContextualMenuHandlerForListItems extends ContextualMenuHandlerBase implements MultiChoiceModeListener{

	private PositionToIdTranslator positionTranslator;
	
	public ContextualMenuHandlerForListItems(PositionToIdTranslator positionTranslator) {
		this.positionTranslator = positionTranslator;		
	}
 
	@Override
	public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {
		handleItemSelectionChanged(actionMode, positionTranslator.getId(position), checked);
	}
	
}
