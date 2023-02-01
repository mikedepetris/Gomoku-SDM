package it.units.gomokusdm;

import java.util.EventListener;

public interface PlayerTimerExpiredEventListener extends EventListener {

    void onPlayerTimerExpired(Player player);

}
