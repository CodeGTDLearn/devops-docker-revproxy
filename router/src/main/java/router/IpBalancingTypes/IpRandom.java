package main.java.router.IpBalancingTypes;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import utils.LogTerminal;

public class IpRandom implements IpBalancingTypes.IpInt {

    @Override
    public int getIp(List<String> ipAddr) {
        int position = 0;
        if (ipAddr.size() == 1) {
            position = 0;
        } else if (ipAddr.size() > 1) {
            position = ThreadLocalRandom.current().nextInt(0, ipAddr.size() - 1);
        }
        LogTerminal.showlogipposition("RANDOM", position);
        return position;
    }
}