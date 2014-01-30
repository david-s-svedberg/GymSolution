package com.dosolves.gym.dbtest;

import android.test.suitebuilder.annotation.SmallTest;


public class CategoryStrucutreTest extends DbStrucutreTest  {

	@SmallTest
	public void testdb_contains_category_table(){
		assertTrue(tableExists("Categories"));
	}
	
	@SmallTest
	public void testdb_contains_id_column_in_category_table(){
		assertTrue(columnExists("Categories", "Id"));
	}
	
	@SmallTest
	public void testdb_contains_name_column_in_category_table(){
		assertTrue(columnExists("Categories", "Name"));
	}	
	
}
