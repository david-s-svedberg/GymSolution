package com.dosolves.gym.domain.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.UserUpdateableItemsController;

@RunWith(RobolectricTestRunner.class)
public class UserUpdateableItemsControllerTest {

	
	private static final int POSITION = 3456;
	private static final String NEW_ITEM_NAME = "NEW_ITEM_NAME";
	@Mock
	CreateItemDialogShower createItemDialogShowerMock;
	@Mock
	ItemOptionMenuDialogShower itemOptionMenuDialogShowerMock; 
	
	UserUpdateableItemsControllerMock sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new UserUpdateableItemsControllerMock(createItemDialogShowerMock, itemOptionMenuDialogShowerMock);
	}
	
	@Test
	public void onReadyToGetData_updates_categories(){
		sut.onReadyToGetData();
		assertTrue(sut.handleUpdateItemsCalled());
	}
	
	@Test
	public void shows_create_dialog_when_user_input_requests_it(){
		sut.onAddItemRequested();
		
		verify(createItemDialogShowerMock).show(sut);		
	}
	
	@Test
	public void shows_menu_item_option_when_user_input_requests_it(){
		sut.onItemMenuRequested(POSITION);
		
		verify(itemOptionMenuDialogShowerMock).show(POSITION, sut);
	}
	
	@Test
	public void calls_handleItemShouldBeOpened_on_subtype_when_open_is_requested(){
		sut.onOpenItemRequested(POSITION);
		
		assertTrue(sut.handleItemShouldBeOpenedCalledWith(POSITION));
	}
	
	@Test
	public void calls_handleItemShouldBeCreated_on_subtype_when_requested(){
		sut.onItemShouldBeCreated(NEW_ITEM_NAME);
		
		assertTrue(sut.handleItemShouldBeCreatedCalledWith(NEW_ITEM_NAME));
	}
	
	@Test
	public void calls_handleItemShouldBeDeleted_on_subtype_when_requested(){
		sut.onItemShouldBeDeleted(POSITION);
		
		assertTrue(sut.handleItemShouldBeDeletedCalled(POSITION));
	}
	
	@Test
	public void updates_items_after_deletion(){
		sut.onItemShouldBeDeleted(POSITION);
		
		assertTrue(sut.handleUpdateItemsCalled());
	}
	
	@Test
	public void updates_items_after_creation(){
		sut.onItemShouldBeCreated(NEW_ITEM_NAME);
		
		assertTrue(sut.handleUpdateItemsCalled());
	}
	
	
	
	private class UserUpdateableItemsControllerMock extends UserUpdateableItemsController{
		
		private boolean handleUpdateItemsCalled = false;
		private boolean handleItemShouldBeOpenedCalled = false;
		private int position = -1;
		private String newItemName = "";

		public UserUpdateableItemsControllerMock(CreateItemDialogShower createItemDialogShower, ItemOptionMenuDialogShower itemOptionMenuDialogShower) {
			super(createItemDialogShower,itemOptionMenuDialogShower);
		}

		public boolean handleItemShouldBeDeletedCalled(int position) {
			return this.position == position;
		}

		public boolean handleItemShouldBeCreatedCalledWith(String newItemName) {
			return this.newItemName .equals(newItemName);
		}

		public boolean handleItemShouldBeOpenedCalledWith(int position) {
			return handleItemShouldBeOpenedCalled && this.position == position ;
		}

		public boolean handleUpdateItemsCalled(){
			return this.handleUpdateItemsCalled;
		}
		
		@Override
		protected void handleUpdateItems() {
			this.handleUpdateItemsCalled = true;
		}
		@Override
		protected void handleItemShouldBeOpened(int positionOfItemToBeOpened) {
			this.handleItemShouldBeOpenedCalled = true;
			this.position = positionOfItemToBeOpened;
		}
		
		@Override
		protected void handleItemShouldBeCreated(String newItemName) {
			this.newItemName = newItemName;	
		}

		@Override
		protected void handleItemShouldBeDeleted(int positionOfItemToBeDeleted) {
			this.position = positionOfItemToBeDeleted;			
		}
	}
	
}
