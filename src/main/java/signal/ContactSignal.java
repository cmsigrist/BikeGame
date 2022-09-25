package signal;

import math.Contact;
import math.ContactListener;

/**
 * Contact listener keeping track of any collision, which can be used as a signal.
 */
public class ContactSignal implements ContactListener, Signal {

    private int counter;

    @Override
    public void beginContact(Contact contact) {
        ++counter;
    }

    @Override
    public void endContact(Contact contact) {
        --counter;
    }
    
    @Override
    public float getIntensity() {
        return counter > 0 ? 1.0f : 0.0f;
    }

}
