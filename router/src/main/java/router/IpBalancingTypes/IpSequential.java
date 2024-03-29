package main.java.router.IpBalancingTypes;

import java.util.List;

import main.java.router.utils.LogTerminal;

public class IpSequential implements IpInt {

    private int position = 0;

    private static IpSequential instance = null;

    public static IpSequential getInstance()
    {
        if (instance == null)
            instance = new IpSequential();

        return instance;
    }

    @Override
    public int getIp(List<String> ipAddr) {
        if (ipAddr.size()  > 1 && this.position < ipAddr.size() ) {
            this.position++;
            if(this.position == ipAddr.size()) this.position = 0;
        }
        LogTerminal.showlogipposition("SEQUENTIAL", this.position);
        return position;
    }
}