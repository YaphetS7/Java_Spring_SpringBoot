package app;

import app.model.User;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class Cache   {
    private final static MemCachedClient client;
    private static final String CACHE = "cache";

    static {
        String[] servers = {"host.docker.internal:11211"};
        SockIOPool pool = SockIOPool.getInstance(CACHE);
        pool.setMinConn(2);
        pool.setMaxConn(20);
        pool.setServers(servers);
        pool.setFailover(true);
        pool.setInitConn(30);
        pool.setMaintSleep(90);
        pool.setSocketTO(3000);
        pool.setAliveCheck(true);
        pool.initialize();

        client = new MemCachedClient(CACHE);
        client.flushAll();
    }

    public static void add(User user){

        System.out.println(client.add(user.getLogin() + user.getPassword(), user.getId()));
        System.out.println(client.keyExists(user.getLogin() + user.getPassword()));
    }

    public static boolean in(String cookie){
        System.out.println(cookie);
        return client.keyExists(cookie);
    }

    public static boolean del(String cookie){
        return client.delete(cookie);
    }

}
