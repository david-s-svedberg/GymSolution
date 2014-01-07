package com.dosolves.gym;

import java.util.ArrayList;

import android.database.Cursor;

public class CursorCategoryRepository implements CategoryRepository {

	private static final String CATEGORIES = "Categories";
	
	private DataAccess dao;
	
	public CursorCategoryRepository(DataAccess dao) {
		this.dao = dao;
	}

	@Override
	public Iterable<Category> getCategories() {	
		ArrayList<Category> categories = new ArrayList<Category>();
		
		Cursor categoriesCursor = dao.get(CATEGORIES);
		
		return categories;
	}

}
