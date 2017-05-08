package com.eternal.express.realm;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author qiuyongheng
 * @time 2017/5/8  13:40
 * @desc 创建realm数据库
 */

public class RealmHelper {
    public static final String DATABASE_NAME = "Espresso.realm";

    public static Realm newRealmInstance() {
        return Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(RealmHelper.DATABASE_NAME)
                .build());
    }
}
