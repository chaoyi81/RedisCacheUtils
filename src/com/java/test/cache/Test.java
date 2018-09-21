////package com.java.test.cache;
////
////package com.ming.redis;
////
////import redis.clients.jedis.Jedis;
////import redis.clients.jedis.JedisPool;
////import redis.clients.jedis.JedisPoolConfig;
////
////
/////**
//// * RedisUtil工具类
//// * @author Administrator
//// *
//// */
////
////public final class RedisUtil {
////    
////    //Redis服务器IP
////    private static String ADDR = "127.0.0.1";
////    
////    //Redis的端口号
////    private static int PORT = 6379;
////    
////    //访问密码
////    private static String AUTH = "123456";
////    
////    //可用连接实例的最大数目，默认值为8；
////    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
////    private static int MAX_ACTIVE = 1024;
////    
////    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
////    private static int MAX_IDLE = 200;
////    
////    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
////    private static int MAX_WAIT = 10000;
////    
////    private static int TIMEOUT = 10000;
////    
////    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
////    private static boolean TEST_ON_BORROW = true;
////    
////    private static JedisPool jedisPool = null;
////    
////    /**
////     * 初始化Redis连接池
////     */
////    static {
////        try {
////            JedisPoolConfig config = new JedisPoolConfig();
////            config.setMaxIdle(MAX_IDLE);
////            config.setMaxWaitMillis(MAX_WAIT);
////            config.setTestOnBorrow(TEST_ON_BORROW);
////            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////    
////    /**
////     * 获取Jedis实例
////     * @return
////     */
////    public synchronized static Jedis getJedis() {
////        try {
////            if (jedisPool != null) {
////                Jedis resource = jedisPool.getResource();
////                return resource;
////            } else {
////                return null;
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////            return null;
////        }
////    }
////    
////    /**
////     * 释放jedis资源
////     * @param jedis
////     */
////    public static void returnResource(final Jedis jedis) {
////        if (jedis != null) {
////            jedisPool.returnResource(jedis);
////        }
////    }
////}
//
//
//public class RedisUtils
//{
//    private static final String IP = "127.0.0.1"; // ip
//    private static final int PORT = 6379;         // 端口
//    private static final String AUTH="";          // 密码(原始默认是没有密码)
//    private static int   MAX_ACTIVE = 1024;       // 最大连接数
//    private static int   MAX_IDLE = 200;          // 设置最大空闲数
//    private static int   MAX_WAIT = 10000;        // 最大连接时间
//    private static int   TIMEOUT = 10000;         // 超时时间
//    private static boolean BORROW = true;         // 在borrow一个事例时是否提前进行validate操作
//    private static JedisPool pool = null;
//    private static Logger logger = Logger.getLogger(RedisUtils.class);
//    /**
//     * 初始化线程池
//     */
//    static
//    {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(MAX_ACTIVE);
//        config.setMaxIdle(MAX_IDLE);
//        config.setMaxWaitMillis(MAX_WAIT);
//        config.setTestOnBorrow(BORROW);
//        pool = new JedisPool(config, IP, PORT, TIMEOUT);
//    }
//     
//     
//    /**
//     * 获取连接
//     */
//    public static synchronized  Jedis getJedis()
//    {
//        try
//        {
//            if(pool != null)
//            {
//                return pool.getResource();
//            }
//            else
//            {
//                return null;
//            }
//        }
//        catch (Exception e) {
//            logger.info("连接池连接异常");
//            return null;
//        }
//        
//    }
//    /**
//     * @Description:设置失效时间
//     * @param @param key
//     * @param @param seconds
//     * @param @return 
//     * @return boolean 返回类型
//     */
//    public static void disableTime(String key,int seconds)
//    {
//        Jedis jedis = null;
//        try
//        {
//            jedis = getJedis();
//            jedis.expire(key, seconds);
//             
//        }
//        catch (Exception e) {
//            logger.debug("设置失效失败.");
//        }finally {
//            getColse(jedis);
//        }
//    }
//     
//     
//    /**
//     * @Description:插入对象
//     * @param @param key
//     * @param @param obj
//     * @param @return 
//     * @return boolean 返回类型
//     */
//    public static boolean addObject(String key,Object obj)
//    {
//         
//        Jedis jedis = null;
//        String value = JSONObject.toJSONString(obj);
//        try
//        {
//            jedis = getJedis();
//            String code = jedis.set(key, value);
//            if(code.equals("ok"))
//            {
//                return true;
//            }
//        }
//        catch (Exception e) {
//            logger.debug("插入数据有异常.");
//            return false;
//        }finally {
//            getColse(jedis);
//        }
//        return false;
//    }
//     
//     
//     
//    /**
//     * @Description:存储key~value
//     * @param @param key
//     * @param @param value 
//     * @return void 返回类型
//     */
//     
//    public static boolean addValue(String key,String value)
//    {
//        Jedis jedis = null;
//        try
//        {
//            jedis = getJedis();
//            String code = jedis.set(key, value);
//            if(code.equals("ok"))
//            {
//                return true;
//            }
//        }
//        catch (Exception e) {
//            logger.debug("插入数据有异常.");
//            return false;
//        }finally {
//            getColse(jedis);
//        }
//        return false;
//    }
//    /**
//     * @Description:删除key
//     * @param @param key
//     * @param @return 
//     * @return boolean 返回类型
//     */
//    public static boolean delKey(String key)
//    {
//        Jedis jedis = null;
//        try
//        {
//            jedis = getJedis();
//            Long code = jedis.del(key);
//            if(code > 1)
//            {
//                return true;
//            }
//        }
//        catch (Exception e) {
//            logger.debug("删除key异常.");
//            return false;
//        }finally {
//            getColse(jedis);
//        }
//        return false;
//    }
//     
//    /**
//     * @Description: 关闭连接
//     * @param @param jedis 
//     * @return void 返回类型
//     */
//     
//    public static void getColse(Jedis jedis)
//    {
//        if(jedis != null)
//        {
//            jedis.close();
//        }
//    }
//     
//}