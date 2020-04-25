import org.junit.*;
import channels.*;

import static org.junit.Assert.*;

public class PaymentChannelTest {

    @Test
    public void checkGetPaymentChannelSingleton() {
        PaymentChannel initialChannel = PaymentChannel.getPaymentChannel(null, null);
        PaymentChannel gottenChannel = PaymentChannel.getPaymentChannel();
        assertEquals(initialChannel, gottenChannel);
    }

}

