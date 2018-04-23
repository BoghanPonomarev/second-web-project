package ua.nure.ponomarev.model.holder.impl;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.nure.ponomarev.model.enity.User;
import ua.nure.ponomarev.model.holder.RequestedAccountHolder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Bogdan_Ponamarev.
 *
 * Class that work with tokens(such as email notification token,phone token).
 * That class holds User for particuler time until that time pass.
 */
@Component
public class RequestedAccountHolderImpl implements RequestedAccountHolder {

    private static Logger logger = LoggerFactory.getLogger(RequestedAccountHolderImpl.class);

    private Map<TokenKey, User> requested;
    /**
     * Default delay of requested accounts map cleaning
     */
    private static final int DEFAULT_DELAY = 5;
    /**
     * Init delay of requested accounts map cleaning
     */
    private static final int INIT_DELAY = 2;
    /**
     * Time in wich requested accounts will be banned for another one requested
     */
    private static final int HOUR_BAN_DELAY = 24;


    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor((Runnable r) ->
    {
        Thread th = new Thread(r);
        th.setDaemon(true);
        return th;
    });

    /**
     * Constructor that create operation in scheduler that will clean {@code requested} map
     */
    public RequestedAccountHolderImpl() {
        logger.info(RequestedAccountHolderImpl.class + " was initiated");
        requested = new ConcurrentHashMap<>();
        scheduler.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();
            for (TokenKey tk : requested.keySet()) {
                if (tk.getDeleteTime().isBefore(now)) {
                    requested.remove(tk);
                }
            }

        }, INIT_DELAY, DEFAULT_DELAY, TimeUnit.MINUTES);
    }

    @Override
    public int addToRequested(User requestedUser) {
        if(logger.isDebugEnabled()){
            logger.debug("There is new token for id - №"+requestedUser.getId()+ " user");
        }
        TokenKey tk = new TokenKey();
        requested.put(tk, requestedUser);
        return tk.getTokenId();
    }

    @Override
    public User getUserIfRequested(int tokenKeyId) {
        LocalDateTime now = LocalDateTime.now();
        for (TokenKey tk : requested.keySet()) {
            if (tk.getDeleteTime().isBefore(now)) {
                requested.remove(tk);
                if(logger.isDebugEnabled()){
                    logger.debug("Token was deleted (token №"+tokenKeyId + ")");
                }
            }
        }
        if(logger.isDebugEnabled()){
            logger.debug("There is no such token");
        }
        return requested.remove(new TokenKey(tokenKeyId));
    }


    /**
     * Class that holds time when token must be deleted
     * and also direct key token
     */
    @Getter
    class TokenKey {
        private LocalDateTime deleteTime;
        private int tokenId;

        TokenKey() {
            deleteTime = LocalDateTime.now().plus(HOUR_BAN_DELAY, ChronoUnit.HOURS);
            tokenId = generateToken();
            while (requested.containsKey(this)){
                tokenId = generateToken();
            }
        }

        TokenKey(int tokenId){
            deleteTime = LocalDateTime.now().plus(HOUR_BAN_DELAY, ChronoUnit.HOURS);
            this.tokenId = tokenId;
        }

        private int generateToken() {
            return (int) (Math.random() * 1000000) + 1000000;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TokenKey tokenKey = (TokenKey) o;

            return tokenId == tokenKey.tokenId;
        }

        @Override
        public int hashCode() {
           return tokenId;
        }
    }
}
