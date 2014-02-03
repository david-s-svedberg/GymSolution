package com.dosolves.gym.domain.category.data;

import java.util.List;

import com.dosolves.gym.domain.DataAccess;
import com.dosolves.gym.domain.category.Category;

public class CursorCategoryRetriever implements CategoryRetriever {
	
	private DataAccess dao;
	private CursorCategoryFactory categoryFactory; 
	
	public CursorCategoryRetriever(DataAccess dao,CursorCategoryFactory categoryFactory) {
		this.dao = dao;
		this.categoryFactory = categoryFactory;
	}

	@Override
	public List<Category> getCategories() {	
		return categoryFactory.CreateCategories(dao.get(CategoryStructureGiver.CATEGORY_TYPE_NAME_PLURAL));
	}

}
