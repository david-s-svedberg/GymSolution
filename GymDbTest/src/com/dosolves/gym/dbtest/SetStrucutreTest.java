package com.dosolves.gym.dbtest;

import android.test.suitebuilder.annotation.SmallTest;


public class SetStrucutreTest extends DbStrucutreTest  {

	@SmallTest
	public void testdb_contains_sets_table(){
		assertTrue(tableExists("Sets"));
	}
	
	@SmallTest
	public void testdb_contains_id_column_in_sets_table(){
		assertTrue(columnExists("Sets", "Id"));
	}
	
	@SmallTest
	public void testdb_contains_reps_column_in_sets_table(){
		assertTrue(columnExists("Sets", "Reps"));
	}
	
	@SmallTest
	public void testdb_contains_weight_column_in_sets_table(){
		assertTrue(columnExists("Sets", "Weight"));
	}
	
	@SmallTest
	public void testdb_contains_date_column_in_sets_table(){
		assertTrue(columnExists("Sets", "Date"));
	}
	
	@SmallTest
	public void testdb_contains_exerciseId_column_in_sets_table(){
		assertTrue(columnExists("Sets", "ExerciseId"));
	}	
	
}
