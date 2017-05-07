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

package com.eternal.express.mvp.packages;

/**
 * @author 邱永恒
 * @time 2017/5/3 21:42
 * @desc ${TODO}
 */

public enum PackageFilterType {

    /**
     * Do not filter the packages.
     */
    ALL_PACKAGES,

    /**
     * Filters only the on the way (not complete or delivered) packages.
     */
    ON_THE_WAY_PACKAGES,

    /**
     * Filters only the delivered packages.
     */
    DELIVERED_PACKAGES
}
