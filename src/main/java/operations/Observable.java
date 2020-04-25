package operations;

import chainParticipants.*;

public interface Observable {

    void attach(Party party);

    void detach(Party party);

    void notify(Transaction transaction);
}
