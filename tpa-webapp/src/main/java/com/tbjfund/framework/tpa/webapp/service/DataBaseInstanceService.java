package com.tbjfund.framework.tpa.webapp.service;

import com.tbjfund.framework.tpa.webapp.ZkHolder;
import com.tbjfund.framework.tpa.webapp.ZkTemplate;
import com.tbjfund.framework.tpa.webapp.model.Database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sidawei on 16/4/26.
 */
public class DataBaseInstanceService {

    public static void add(final Database temp) {
        ZkTemplate.doInZookeeper(new ZkTemplate.Template() {
            @Override
            public void run(ZkHolder holder) {
                boolean needSet = false;
                List<Database> databaseList = holder.getArray("/tpa/databases", Database.class);
                if (databaseList == null) {
                    databaseList = new ArrayList<Database>();
                }
                if (databaseList.size() == 0) {
                    databaseList.add(temp);
                    needSet = true;
                } else {
                    boolean exists = false;
                    for (Database database : databaseList) {
                        if (database.equals(temp)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        databaseList.add(temp);
                        needSet = true;
                    }
                }
                if (needSet) {
                    holder.set("/tpa/databases", databaseList);
                }
            }
        });
    }

    public static void delete(final Database temp){
        ZkTemplate.doInZookeeper(new ZkTemplate.Template() {
            @Override
            public void run(ZkHolder holder) {
                boolean needSet = false;
                List<Database> databaseList = holder.getArray("/tpa/databases", Database.class);
                if (databaseList != null && databaseList.size() != 0){
                    for (Iterator<Database> ite = databaseList.iterator(); ite.hasNext();){
                        Database database = ite.next();
                        if (database.equals(temp)){
                            ite.remove();
                            needSet = true;
                        }
                    }
                    if (needSet){
                        holder.set("/tpa/databases", databaseList);
                    }
                }
            }
        });
    }
}
