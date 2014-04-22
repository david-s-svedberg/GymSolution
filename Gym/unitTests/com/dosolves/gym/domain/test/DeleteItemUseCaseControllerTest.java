package com.dosolves.gym.domain.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.dosolves.gym.domain.DeleteItemUseCaseController;
import com.dosolves.gym.domain.DeleteItemUseCaseControllerImpl;
import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.UserAsker;
import com.dosolves.gym.domain.UserResponseListener;
import com.dosolves.gym.domain.data.ItemHasSubItemsChecker;

public class DeleteItemUseCaseControllerTest {

	@Mock
	ItemHasSubItemsChecker itemHasSubItemsCheckerMock;
	@Mock
	UserAsker userAskerMock;
	@Mock
	ItemDeleter itemDeleterMock;
	
	
	private static final int ITEM_ID = 12;
	
	DeleteItemUseCaseController sut;
		
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new DeleteItemUseCaseControllerImpl(itemHasSubItemsCheckerMock, userAskerMock, itemDeleterMock);
	}
	
	@Test
	public void checks_if_item_has_subitems(){
		sut.deleteItemRequested(ITEM_ID);
		
		verify(itemHasSubItemsCheckerMock).hasSubItems(ITEM_ID);
	}
	
	@Test
	public void asks_user_if_item_should_really_be_deleted_if_item_has_sub_items(){
		when(itemHasSubItemsCheckerMock.hasSubItems(ITEM_ID)).thenReturn(true);
		
		sut.deleteItemRequested(ITEM_ID);
		
		verify(userAskerMock).shouldParentItemBeDeleted(any(UserResponseListener.class));
	}
	
	@Test
	public void does_not_asks_user_if_item_should_really_be_deleted_if_item_has_no_sub_items(){
		when(itemHasSubItemsCheckerMock.hasSubItems(ITEM_ID)).thenReturn(false);
		
		sut.deleteItemRequested(ITEM_ID);
		
		verifyZeroInteractions(userAskerMock);
	}
	
	@Test
	public void deletes_item_if_user_wants_it_so(){
		when(itemHasSubItemsCheckerMock.hasSubItems(ITEM_ID)).thenReturn(true);
		
		doAnswer(new Answer<Void>(){
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				UserResponseListener callback = (UserResponseListener) invocation.getArguments()[0];
				callback.yes();
				return null;
			}
			
		}).when(userAskerMock).shouldParentItemBeDeleted(any(UserResponseListener.class));
		
		sut.deleteItemRequested(ITEM_ID);
		
		verify(itemDeleterMock).deleteItem(ITEM_ID);
	}
	
	@Test
	public void dont_deletes_item_if_user_wants__doesnt_want_to(){
		when(itemHasSubItemsCheckerMock.hasSubItems(ITEM_ID)).thenReturn(true);
		
		doAnswer(new Answer<Void>(){
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				UserResponseListener callback = (UserResponseListener) invocation.getArguments()[0];
				callback.no();
				return null;
			}
			
		}).when(userAskerMock).shouldParentItemBeDeleted(any(UserResponseListener.class));
		
		sut.deleteItemRequested(ITEM_ID);
		
		verifyZeroInteractions(itemDeleterMock);
	}
	
	@Test
	public void deletes_item_if_item_has_no_subitems(){
		when(itemHasSubItemsCheckerMock.hasSubItems(ITEM_ID)).thenReturn(false);
		
		sut.deleteItemRequested(ITEM_ID);
		
		verify(itemDeleterMock).deleteItem(ITEM_ID);
	}
	
}
