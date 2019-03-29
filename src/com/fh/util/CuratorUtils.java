package com.fh.util;

import com.fh.common.constant.CacheMap;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @Auther: tangqm
 * @Date: 2019/2/28 16:35
 * @Description:
 */
public class CuratorUtils {

    protected  CuratorFramework client =null;

    final Logger logger = LoggerFactory.getLogger(CuratorUtils.class);
    String nodePath = "/server";

    public CuratorUtils(CuratorFramework curatorFramework){
        this.client = curatorFramework;
    }

    /**
     * 初始化
     */
    private void init() throws Exception {
        //命名空间
        client = client.usingNamespace("basecache");
        watchChildPath();
    }

    /**
     * 判断是否连接成功
     * @return
     */
    private boolean isStatus(){
        return client.isStarted();
    }

    /**
     * 新增节点
     * @param zkPath
     * @param data
     * @return
     * @throws Exception
     */
    public  String create(String zkPath, String data) throws Exception {
        return client.create().creatingParentsIfNeeded().forPath(zkPath, data.getBytes());
    }

    /**
     * 得到子节点
     * @param zkPath
     * @return
     * @throws Exception
     */
    public  List<String> getChildren(String zkPath) throws Exception {
        List<String> children = client.getChildren().forPath(zkPath);
        return children;
    }

    /**
     * 得到数据节点
     * @param zkPath
     * @return
     * @throws Exception
     */
    public  String getData(String zkPath) throws Exception {
        return new String(client.getData().forPath(zkPath));
    }

    /**
     * 修改节点
     * @param zkPath
     * @param data
     * @return
     * @throws Exception
     */
    public  Stat setData(String zkPath, String data) throws Exception {
        return client.setData().forPath(zkPath, data.getBytes());
    }

    /**
     * 删除节点
     * @param zkPath
     * @return
     * @throws Exception
     */
    public  Void delNode(String zkPath) throws Exception {
        return client.delete().forPath(zkPath);
    }

    /**
     * 监听
     */
    protected void watchChildPath() throws Exception {
         PathChildrenCache childrenCache = new PathChildrenCache(client,nodePath,true);
         childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
         childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
             @Override
             public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                 //预加载
//                 if(PathChildrenCacheEvent.Type.INITIALIZED.equals(pathChildrenCacheEvent.getType())){
//                     setAllCacheMap();
//                 }
                 //为指定属性赋值
                 if( PathChildrenCacheEvent.Type.CHILD_UPDATED.equals(pathChildrenCacheEvent.getType())){
                     String data = new String(pathChildrenCacheEvent.getData().getData());
                     setCacheMap(data);
                 }
             }
         });
     }

    /**
     * 为指定属性赋值
     * @param fieldname
     */
     protected void setCacheMap(String fieldname) {
         try {
             Class<?>c=CacheMap.class;
             Field declaredField = c.getDeclaredField(fieldname);
             if (declaredField == null) {
                 return;
             }
             Object value = RedisUtil.get(fieldname.trim());
             if (value == null) {
                 return;
             }
             logger.info("节点"+fieldname+"修改前："+CacheMap.PricingRule);
             Map<String,String> localCache = new Gson().fromJson(value.toString(), Map.class);
             logger.info("节点"+fieldname+"修改后："+localCache);
             declaredField.set(CacheMap.class,localCache);
         } catch (NoSuchFieldException e) {
             logger.error("无此字段:"+fieldname);
             return;
         } catch (Exception e) {
             logger.error(e.getLocalizedMessage());
         }
     }


//    /**
//     * 得到指定类的属性
//     * @param fields
//     * @param name
//     * @return
//     */
//    private  Field searchFields(Field[] fields, String name) {
////        String internedName = name.intern();
//        for (int i = 0; i < fields.length; i++) {
//            System.out.println(fields[i].getName().toString());
//            if (fields[i].getName().toString().equalsIgnoreCase(name)) {
//                return fields[i];
//            }
//        }
//        return null;
//    }

}
