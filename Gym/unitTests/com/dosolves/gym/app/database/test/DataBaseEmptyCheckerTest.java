package com.dosolves.gym.app.database.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dosolves.gym.app.database.DataBaseEmptyChecker;
import com.dosolves.gym.app.database.DataBaseEmptyCheckerImpl;
import com.dosolves.gym.domain.data.DataAccess;

public class DataBaseEmptyCheckerTest {

	DataBaseEmptyChecker sut;
	
	@Mock
	DataAccess dataAccessMock;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	
		sut = new DataBaseEmptyCheckerImpl(dataAccessMock);		
	}
	
	@Test
	public void checks_if_there_are_any_categories_in_db() {
		sut.isDbEmpty();
		
		verify(dataAccessMock).exists("Categories");
	}
	
	@Test
	public void returns_true_if_no_categories_exists() {
		when(dataAccessMock.exists("Categories")).thenReturn(false);
		
		assertTrue(sut.isDbEmpty());
	}
	
}
