package ua.nure.ponomarev.model.holder;

import ua.nure.ponomarev.model.enity.User;

/**
 * @author Bogdan_Ponamarev.
 *
 */
public interface RequestedAccountHolder {

    int addToRequested(User requestedUser);

    User getUserIfRequested(int tokenKeyId);
}
