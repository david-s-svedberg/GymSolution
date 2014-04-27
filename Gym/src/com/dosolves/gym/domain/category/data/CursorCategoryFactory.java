package com.dosolves.gym.domain.category.data;

import java.util.ArrayList;
import java.util.List;

import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.GymCursor;
import com.dosolves.gym.domain.category.Category;

public class CursorCategoryFactory {

	private DbStructureGiver categoryDbStructureGiver;

	public CursorCategoryFactory(DbStructureGiver categoryDbStructureGiver) {
		this.categoryDbStructureGiver = categoryDbStructureGiver;
	}

	public List<Category> CreateCategories(GymCursor cursor) {
		ArrayList<Category> categories = new ArrayList<Category>();
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			categories.add(createCategoryFromCursorAtCurrentPosition(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		
		return categories;
	}

	public Category CreateCategory(GymCursor cursor) {
		Category ret = null;
		
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			ret = createCategoryFromCursorAtCurrentPosition(cursor);
		}
		
		return ret;
	}

	private Category createCategoryFromCursorAtCurrentPosition(GymCursor cursor) {
		int id = cursor.getInt(categoryDbStructureGiver.getColumnIndex(CategoryStructureGiver.ID_PROPERTY_NAME));
		String name = cursor.getString(categoryDbStructureGiver.getColumnIndex(CategoryStructureGiver.NAME_PROPERTY_NAME));
		
		return new Category(id, name);
	}
	
}
