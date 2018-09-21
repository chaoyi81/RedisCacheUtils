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
//// * RedisUtil������
//// * @author Administrator
//// *
//// */
////
////public final class RedisUtil {
////    
////    //Redis������IP
////    private static String ADDR = "127.0.0.1";
////    
////    //Redis�Ķ˿ں�
////    private static int PORT = 6379;
////    
////    //��������
////    private static String AUTH = "123456";
////    
////    //��������ʵ���������Ŀ��Ĭ��ֵΪ8��
////    //�����ֵΪ-1�����ʾ�����ƣ����pool�Ѿ�������maxActive��jedisʵ�������ʱpool��״̬Ϊexhausted(�ľ�)��
////    private static int MAX_ACTIVE = 1024;
////    
////    //����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����Ĭ��ֵҲ��8��
////    private static int MAX_IDLE = 200;
////    
////    //�ȴ��������ӵ����ʱ�䣬��λ���룬Ĭ��ֵΪ-1����ʾ������ʱ����������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException��
////    private static int MAX_WAIT = 10000;
////    
////    private static int TIMEOUT = 10000;
////    
////    //��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue����õ���jedisʵ�����ǿ��õģ�
////    private static boolean TEST_ON_BORROW = true;
////    
////    private static JedisPool jedisPool = null;
////    
////    /**
////     * ��ʼ��Redis���ӳ�
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
////     * ��ȡJedisʵ��
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
////     * �ͷ�jedis��Դ
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
//    private static final int PORT = 6379;         // �˿�
//    private static final String AUTH="";          // ����(ԭʼĬ����û������)
//    private static int   MAX_ACTIVE = 1024;       // ���������
//    private static int   MAX_IDLE = 200;          // ������������
//    private static int   MAX_WAIT = 10000;        // �������ʱ��
//    private static int   TIMEOUT = 10000;         // ��ʱʱ��
//    private static boolean BORROW = true;         // ��borrowһ������ʱ�Ƿ���ǰ����validate����
//    private static JedisPool pool = null;
//    private static Logger logger = Logger.getLogger(RedisUtils.class);
//    /**
//     * ��ʼ���̳߳�
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
//     * ��ȡ����
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
//            logger.info("���ӳ������쳣");
//            return null;
//        }
//        
//    }
//    /**
//     * @Description:����ʧЧʱ��
//     * @param @param key
//     * @param @param seconds
//     * @param @return 
//     * @return boolean ��������
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
//            logger.debug("����ʧЧʧ��.");
//        }finally {
//            getColse(jedis);
//        }
//    }
//     
//     
//    /**
//     * @Description:�������
//     * @param @param key
//     * @param @param obj
//     * @param @return 
//     * @return boolean ��������
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
//            logger.debug("�����������쳣.");
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
//     * @Description:�洢key~value
//     * @param @param key
//     * @param @param value 
//     * @return void ��������
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
//            logger.debug("�����������쳣.");
//            return false;
//        }finally {
//            getColse(jedis);
//        }
//        return false;
//    }
//    /**
//     * @Description:ɾ��key
//     * @param @param key
//     * @param @return 
//     * @return boolean ��������
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
//            logger.debug("ɾ��key�쳣.");
//            return false;
//        }finally {
//            getColse(jedis);
//        }
//        return false;
//    }
//     
//    /**
//     * @Description: �ر�����
//     * @param @param jedis 
//     * @return void ��������
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