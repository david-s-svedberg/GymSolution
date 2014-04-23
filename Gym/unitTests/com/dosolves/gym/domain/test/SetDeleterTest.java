package com.dosolves.gym.domain.test;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.performance.data.SetDeleter;
import com.dosolves.gym.domain.performance.data.SetUpdater;

public class SetDeleterTest {

	private static final int ITEM_ID = 2456;
	
	@Mock
	SetUpdater setUpdaterMock;
	
	ItemDeleter sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new SetDeleter(setUpdaterMock);
	}
	
	@Test
	public void deletes_set(){
		sut.deleteItem(ITEM_ID);
		
		verify(setUpdaterMock).delete(ITEM_ID);
	}
}
