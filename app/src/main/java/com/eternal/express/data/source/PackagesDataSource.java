/*
 *  Copyright(c) 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eternal.express.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;

/**
 * 提供数据model的操作接口
 */

public interface PackagesDataSource {

    /**
     * 获取所有的快递数据
     * @return
     */
    Observable<List<Package>> getPackages();

    /**
     * 根据快递包名获取数据
     * @param packNumber
     * @return
     */
    Observable<Package> getPackage(@NonNull final String packNumber);

    /**
     * 保存快递数据
     * @param pack
     */
    void savePackage(@NonNull Package pack);

    /**
     * 删除快递数据
     * @param packageId
     */
    void deletePackage(@NonNull String packageId);

    /**
     * 刷新快递数据
     * @return
     */
    Observable<List<Package>> refreshPackages();

    /**
     * 根据快递ID刷新数据
     * @param packageId
     * @return
     */
    Observable<Package> refreshPackage(@NonNull String packageId);

    /**
     *
     */
    void setAllPackagesRead();

    /**
     *
     * @param packageId
     * @param readable
     */
    void setPackageReadable(@NonNull String packageId, boolean readable);

    /**
     *
     * @param packageId
     * @return
     */
    boolean isPackageExist(@NonNull String packageId);

    /**
     *
     * @param packageId
     * @param name
     */
    void updatePackageName(@NonNull String packageId, @NonNull String name);

    /**
     *
     * @param keyWords
     * @return
     */
    Observable<List<Package>> searchPackages(@NonNull String keyWords);

}
