package com.dosolves.gym.domain.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dosolves.gym.domain.CreateItemDialogShower;
import com.dosolves.gym.domain.DeleteItemUseCaseController;
import com.dosolves.gym.domain.ItemOptionMenuDialogShower;
import com.dosolves.gym.domain.RenameDialogShower;
import com.dosolves.gym.domain.UserUpdateableItemsController;

public class UserUpdateableItemsControllerTest {
	
	private static final int ITEM_ID = 1235;
	private static final String ITEM_NAME = "itemName";
	private static final int POSITION = 3456;
	private static final String NEW_ITEM_NAME = "NEW_ITEM_NAME";
	
	@Mock
	CreateItemDialogShower createItemDialogShowerMock;
	@Mock
	ItemOptionMenuDialogShower itemOptionMenuDialogShowerMock;
	@Mock
	RenameDialogShower renameDialogShowerMock;
	@Mock
	DeleteItemUseCaseController itemDeleteUseCaseMock;
	
	UserUpdateableItemsControllerMock sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new UserUpdateableItemsControllerMock(createItemDialogShowerMock, 
													itemOptionMenuDialogShowerMock,
													renameDialogShowerMock,
													itemDeleteUseCaseMock);
	}
	
	@Test
	public void onReadyToGetData_calls_handleUpdateItems(){
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
		
		verify(itemOptionMenuDialogShowerMock).show(POSITION, sut, sut);
	}
	
	@Test
	public void shows_rename_dialog_when_user_input_requests_it(){
		sut.setupMockItemName(ITEM_NAME);
		sut.onRenameDialogRequested(POSITION);
		
		verify(renameDialogShowerMock).show(POSITION, sut, ITEM_NAME);
		assertTrue(sut.getItemCurrentNameCalled());
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
	public void calls_getItemId_when_deletion_requested(){
		sut.onItemShouldBeDeleted(POSITION);
		
		assertTrue(sut.getItemIdCalled(POSITION));
	}
	
	@Test
	public void calls_delete_use_case_when_requested(){
		sut.setupMockItemId(ITEM_ID);
		sut.onItemShouldBeDeleted(POSITION);
		
		verify(itemDeleteUseCaseMock).deleteItemRequested(ITEM_ID);
	}
	
	@Test
	public void calls_handleItemShouldBeRenamed_on_subtype_when_requested(){
		sut.onItemShouldBeRenamed(POSITION, NEW_ITEM_NAME);
		
		assertTrue(sut.handleItemShouldBeRenamedCalled(POSITION, NEW_ITEM_NAME));
	}
	
	@Test
	public void updates_items_after_rename(){
		sut.onItemShouldBeRenamed(POSITION, NEW_ITEM_NAME);
		
		assertTrue(sut.handleUpdateItemsCalled());
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
		private String itemName;
		private boolean getItemCurrentNameCalled = false;
		private int itemId;
		
		public UserUpdateableItemsControllerMock(CreateItemDialogShower createItemDialogShower, ItemOptionMenuDialogShower itemOptionMenuDialogShower, RenameDialogShower renameDialogShower,DeleteItemUseCaseController deleteItemUseCase) {
			super(createItemDialogShower,itemOptionMenuDialogShower,renameDialogShower,deleteItemUseCase);			
		}

		public boolean getItemIdCalled(int position) {
			return this.position == position;
		}

		public boolean getItemCurrentNameCalled() {
			return getItemCurrentNameCalled ;
		}

		public void setupMockItemName(String itemName) {
			this.itemName = itemName;
		}
		
		public void setupMockItemId(int id) {
			this.itemId = id;
		}
		
		public boolean handleItemShouldBeRenamedCalled(int position, String expectedName) {
			return this.position == position && this.newItemName.equals(expectedName);			
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
		protected void handleItemShouldBeRenamed(int positionOfItemToBeRenamed, String newName) {
			this.position = positionOfItemToBeRenamed;
			this.newItemName = newName;
		}

		@Override
		protected String getItemCurrentName(int positionOfItem) {
			getItemCurrentNameCalled = true;
			return itemName;
		}

		@Override
		protected int getItemId(int positionOfItem) {
			position = positionOfItem;
			return itemId;
		}
		
	}
	
}
