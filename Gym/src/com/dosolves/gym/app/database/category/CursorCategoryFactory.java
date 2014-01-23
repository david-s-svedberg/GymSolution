package com.dosolves.gym.app.database.category;

import java.util.ArrayList;
import java.util.List;

import com.dosolves.gym.domain.DbStructureGiver;
import com.dosolves.gym.domain.category.Category;
import com.dosolves.gym.domain.category.CategoryStructureGiver;

import android.database.Cursor;

public class CursorCategoryFactory {

	private DbStructureGiver categoryDbStructureGiver;

	public CursorCategoryFactory(DbStructureGiver categoryDbStructureGiver) {
		this.categoryDbStructureGiver = categoryDbStructureGiver;
	}

	public List<Category> CreateCategories(Cursor categoriesCursor) {
		ArrayList<Category> categories = new ArrayList<Category>();
		
		categoriesCursor.moveToFirst();
		while(!categoriesCursor.isAfterLast()){
			int id = categoriesCursor.getInt(categoryDbStructureGiver.getColumnIndex(CategoryStructureGiver.ID_PROPERTY_NAME));
			String name = categoriesCursor.getString(categoryDbStructureGiver.getColumnIndex(CategoryStructureGiver.NAME_PROPERTY_NAME));
			
			categories.add(new Category(id, name));
			
			categoriesCursor.moveToNext();
		}
		categoriesCursor.close();
		
		return categories;
	}

	
	
}
