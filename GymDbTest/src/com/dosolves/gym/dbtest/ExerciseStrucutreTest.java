package com.dosolves.gym.dbtest;

import android.test.suitebuilder.annotation.SmallTest;


public class ExerciseStrucutreTest extends DbStrucutreTest  {

	@SmallTest
	public void testdb_contains_exercise_table(){
		assertTrue(tableExists("Exercises"));
	}
	
	@SmallTest
	public void testdb_contains_id_column_in_exercise_table(){
		assertTrue(columnExists("Exercises", "Id"));
	}
	
	@SmallTest
	public void testdb_contains_name_column_in_exercise_table(){
		assertTrue(columnExists("Exercises", "Name"));
	}
	
	@SmallTest
	public void testdb_contains_categoryId_column_in_exercise_table(){
		assertTrue(columnExists("Exercises", "CategoryId"));
	}	
	
}
