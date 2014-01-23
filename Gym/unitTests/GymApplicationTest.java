import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;

import com.dosolves.gym.app.ContextSetter;
import com.dosolves.gym.app.GymApplication;
import com.dosolves.gym.app.gui.ContextProvider;

@RunWith(RobolectricTestRunner.class)
public class GymApplicationTest {

	@Mock
	ContextSetter setterMock;
	@Mock
	ContextProvider contextProviderMock;
	@Mock
	ActivityLifecycleCallbacks callbackMock;
	
	GymApplication sut;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new GymApplication(setterMock, callbackMock, contextProviderMock);
	}
	
	@Test
	public void registers_context_on_create(){
		sut.onCreate();
		verify(setterMock).setContext(any(Context.class));
	}
	
	//Can't manage this...
//	@Test
//	public void registers_for_ActivityLifecycleCallbacks(){
//		sut.onCreate();
//		
//		getActivity();
//		
//		verify(callbackMock).onActivityCreated(any(Activity.class),any(Bundle.class));
//	}
	
}
