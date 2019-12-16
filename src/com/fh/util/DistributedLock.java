package com.fh.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.concurrent.CountDownLatch;

/**
 * 分布式锁的实现工具类，通过节点来控制多个服务
 * @Auther: tangqm
 * @Date: 2019/3/6 10:26
 * @Description:
 */
public class DistributedLock {

    private CuratorFramework client = null;//zookeeper客户端
    //用于挂起请求，等待上一个请求释放
    private static CountDownLatch zkLockLatch = new CountDownLatch(1);

    private static final String ZK_LOCK_PROJECT = "zk_lock";

    private static final String DISTRIBUTED_LOCK = "distributed_lock";

    public DistributedLock(CuratorFramework client) {
        this.client = client;
    }

    /**
     * 初始化，如果没有分布式父节点则创建
     */
    public void init(){
        //根目录 ZKLocks/zk_lock/distributed_lock
        client = client.usingNamespace("ZKLocks-Namespace");
        try {
            if(client.checkExists().forPath("/"+ZK_LOCK_PROJECT)==null){
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath("/"+ZK_LOCK_PROJECT);
            }
        } catch (Exception e) {
            LoggerUtil.error("zookeeper客户端连接失败",e);
        }
    }
    //获取分布式锁
    public void getLock(){
        while(true){
            try {
                //临时节点 如果锁创建成功则表示锁没有被占用
                client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath("/"+ZK_LOCK_PROJECT+"/"+DISTRIBUTED_LOCK);
                LoggerUtil.info("获取分布式锁成功");
            } catch (Exception e) {
                //阻塞线程
                try {
                    LoggerUtil.info("获取分布式锁失败...");
                    //如果没有获取到锁，需要重新设置同步资源值
                    if(zkLockLatch.getCount()<0){
                        zkLockLatch = new CountDownLatch(1);
                    }
                    zkLockLatch.await();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    /**
     * 增加分布式锁的监听
     * @param path
     * @throws Exception
     */
    public void addWatcherToLock(String path) throws Exception {
        final PathChildrenCache cache = new PathChildrenCache(client,path,true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                if(pathChildrenCacheEvent.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)){
                    String path = pathChildrenCacheEvent.getData().getPath();
                    if(path.contains(DISTRIBUTED_LOCK)){
                        LoggerUtil.info("释放计算器，让当前请求获得分布式锁");
                        zkLockLatch.countDown();
                    }
                }
            }
        });
    }
    //释放分布式锁
    public boolean releaseLock(){
        try {
            if(client.checkExists().forPath("/"+ZK_LOCK_PROJECT+"/"+DISTRIBUTED_LOCK)!=null){
                client.delete().forPath("/"+ZK_LOCK_PROJECT+"/"+DISTRIBUTED_LOCK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        LoggerUtil.info("分布式锁释放完毕");
        return true;
    }

}
