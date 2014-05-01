package com.dosolves.gym.domain.test;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.dosolves.gym.domain.DeleteItemUseCaseController;
import com.dosolves.gym.domain.DeleteItemUseCaseControllerImpl;
import com.dosolves.gym.domain.ItemDeleter;
import com.dosolves.gym.domain.ItemsDeletedListener;
import com.dosolves.gym.domain.UserAsker;
import com.dosolves.gym.domain.UserResponseListener;
import com.dosolves.gym.domain.data.ItemHasSubItemsChecker;

public class DeleteItemUseCaseControllerTest {

	private static final int ITEM_ID2 = 2534;
	private static final int ITEM_ID = 12;
	private ArrayList<Integer> ids;
	
	DeleteItemUseCaseController sut;
	
	
	@Mock
	ItemHasSubItemsChecker itemHasSubItemsCheckerMock;
	@Mock
	UserAsker userAskerMock;
	@Mock
	ItemDeleter itemDeleterMock;
	@Mock
	ItemsDeletedListener itemsDeletedListenerMock;
		
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		ids = new ArrayList<Integer>();
		ids.add(ITEM_ID);
		
		sut = new DeleteItemUseCaseControllerImpl(itemHasSubItemsCheckerMock, userAskerMock, itemDeleterMock);
	}
	
	@Test
	public void checks_if_item_has_subitems(){
		sut.deleteItemsRequested(ids, itemsDeletedListenerMock);
		
		verify(itemHasSubItemsCheckerMock).hasSubItems(ITEM_ID);
	}
	
	@Test
	public void asks_user_if_item_should_really_be_deleted_if_item_has_sub_items(){
		when(itemHasSubItemsCheckerMock.hasSubItems(ITEM_ID)).thenReturn(true);
		sut.deleteItemsRequested(ids, itemsDeletedListenerMock);
		
		
		verify(userAskerMock).shouldParentItemBeDeleted(any(UserResponseListener.class));
	}
	
	@Test
	public void does_not_asks_user_if_item_should_really_be_deleted_if_item_has_no_sub_items(){
		when(itemHasSubItemsCheckerMock.hasSubItems(ITEM_ID)).thenReturn(false);
		
		sut.deleteItemsRequested(ids,itemsDeletedListenerMock);
		
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
		
		sut.deleteItemsRequested(ids,itemsDeletedListenerMock);
		
		verify(itemDeleterMock).deleteItem(ITEM_ID);
	}
	
	@Test
	public void notifyes_deletion_if_parentitem_is_deleted_when_user_wants_it_so(){
		when(itemHasSubItemsCheckerMock.hasSubItems(ITEM_ID)).thenReturn(true);
		
		doAnswer(new Answer<Void>(){
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				UserResponseListener callback = (UserResponseListener) invocation.getArguments()[0];
				callback.yes();
				return null;
			}
			
		}).when(userAskerMock).shouldParentItemBeDeleted(any(UserResponseListener.class));
		
		sut.deleteItemsRequested(ids,itemsDeletedListenerMock);
		
		verify(itemsDeletedListenerMock).onItemsHasBeenDeleted();
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
		
		sut.deleteItemsRequested(ids,itemsDeletedListenerMock);
		
		verifyZeroInteractions(itemDeleterMock);
	}
	
	@Test
	public void deletes_item_if_item_has_no_subitems(){
		when(itemHasSubItemsCheckerMock.hasSubItems(ITEM_ID)).thenReturn(false);
		
		sut.deleteItemsRequested(ids,itemsDeletedListenerMock);
		
		verify(itemDeleterMock).deleteItem(ITEM_ID);
	}
	
	@Test
	public void notify_deletion_even_when_items_has_no_subitems(){
		when(itemHasSubItemsCheckerMock.hasSubItems(ITEM_ID)).thenReturn(false);
		
		sut.deleteItemsRequested(ids,itemsDeletedListenerMock);
		
		verify(itemsDeletedListenerMock).onItemsHasBeenDeleted();
	}
	
	@Test
	public void only_calls_userasker_once_even_if_multiple_items_has_subitems(){
		when(itemHasSubItemsCheckerMock.hasSubItems(ITEM_ID)).thenReturn(true);
		when(itemHasSubItemsCheckerMock.hasSubItems(ITEM_ID2)).thenReturn(true);
		
		ids.add(ITEM_ID2);
		sut.deleteItemsRequested(ids,itemsDeletedListenerMock);
		
		verify(userAskerMock, times(1)).shouldParentItemBeDeleted(any(UserResponseListener.class));
	}
	
}
