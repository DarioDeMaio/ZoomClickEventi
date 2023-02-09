package test;


import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import user.manager.UserManager;

import static org.junit.Assert.assertEquals;

public class TestUserManager {


    @Test
    public void testCheckIdByEmail(){
        String email = "demaiodario@gmail.com";
        //when(UserManager.checkIdByEmail(email), true);
        MockedStatic<UserManager> mock = Mockito.mockStatic(UserManager.class);
        boolean m = false;
        OngoingStubbing<Object> b = mock.when(()->UserManager.checkIdByEmail(email)).thenReturn(m);
        mock.close();
        assertEquals(true, b);
    }

}
