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

package com.eternal.express.retrofit;


/**
 * @author qiuyongheng
 * @time 2017/5/8  13:40
 * @desc
 */

public class Api {

    // Base API
    public static final String API_BASE = "http://www.kuaidi100.com/";

    // Get status of a specific number
    public static final String PACKAGE_STATE = API_BASE + "query";

    // Auto recognize the company of a package number
    public static final String COMPANY_QUERY = "autonumber/autoComNum";

}
