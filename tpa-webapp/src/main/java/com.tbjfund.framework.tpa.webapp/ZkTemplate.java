package com.tbjfund.framework.tpa.webapp;

/**
 * Created by sidawei on 16/4/26.
 */
public class ZkTemplate {

    public static void doInZookeeper(Template template){
        ZkHolder holder = new ZkHolder();
        try {
            template.run(holder);
        }finally {
            holder.close();
        }
    }

    public interface Template{
        void run(ZkHolder holder);
    }

}
