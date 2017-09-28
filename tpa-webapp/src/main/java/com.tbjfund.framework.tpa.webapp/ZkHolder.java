package com.tbjfund.framework.tpa.webapp;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * Created by sidawei on 16/4/26.
 */
public class ZkHolder {
    private final static String connectionString = "192.168.1.204:2181";
    private final static int connectionTimeout = 5000;
    private ZkClient zkClient;
    private Boolean zkInit = false;

    static {
        ZkClient zkClient = new ZkClient(connectionString, connectionTimeout);
        createNode("/tpa", zkClient);
        createNode("/tpa/databases", zkClient);
        createNode("/tpa/tables", zkClient);
        zkClient.close();
    }

    public ZkHolder() {
        zkClient = getClient();
    }

    public ZkClient getClient() {
        if (!zkInit){
            synchronized (zkInit){
                if (!zkInit){
                    zkClient = new ZkClient(connectionString, connectionTimeout);
                    zkInit = true;
                }
            }
        }
        return zkClient;
    }

    public static void createNode(String path, ZkClient client){
        if (!client.exists(path)) {
            client.create(path, "", CreateMode.PERSISTENT);
        }
    }

    public void createNode(String path){
        createNode(path, zkClient);
    }

    public void deleteNode(String path){
        zkClient.delete(path);
    }

    public <T> T get(String path, Class<T> clazz) {
        String data = getClient().readData(path);
        if (data == null || data.length() == 0){
            return null;
        }
        return JSON.parseObject(data, clazz);
    }

    public <T> List<T> getArray(String path, Class<T> clazz) {
        String data = getClient().readData(path);
        if (data == null || data.length() == 0){
            return null;
        }
        return JSON.parseArray(data, clazz);
    }

    public void set(String path, Object data){
        String str = JSON.toJSONString(data);
        getClient().writeData(path, str);
    }

    public void close(){
        this.zkClient.close();
    }
}
