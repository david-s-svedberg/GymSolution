import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.app.Activity;

import com.dosolves.gym.app.ActivityCreatedListener;
import com.dosolves.gym.domain.ModelComposer;

@RunWith(RobolectricTestRunner.class)
public class ActivityCreatedListenerTest {

	@Mock
	ModelComposer composerMock;
	@Mock
	Activity activityMock;
	
	ActivityCreatedListener sut;

	
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		
		sut = new ActivityCreatedListener(composerMock);
	}
	
	@Test
	public void sends_activity_of_to_be_composed(){
		sut.onActivityCreated(activityMock, null);
		verify(composerMock).compose(activityMock);
	}
}
