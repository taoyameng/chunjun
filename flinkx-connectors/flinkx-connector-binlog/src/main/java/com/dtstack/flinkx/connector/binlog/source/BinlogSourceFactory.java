/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dtstack.flinkx.connector.binlog.source;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.data.RowData;

import com.dtstack.flinkx.connector.binlog.conf.BinlogConf;
import com.dtstack.flinkx.connector.binlog.inputformat.BinlogInputFormatBuilder;
import com.dtstack.flinkx.conf.SyncConf;
import com.dtstack.flinkx.source.SourceFactory;
import com.dtstack.flinkx.util.JsonUtil;

/**
 * @company: www.dtstack.com
 * @author: toutian
 * @create: 2019/7/4
 */
public class BinlogSourceFactory extends SourceFactory {

    private final BinlogConf binlogConf;

    public BinlogSourceFactory(SyncConf config, StreamExecutionEnvironment env) {
        super(config, env);
        binlogConf = JsonUtil.toObject(JsonUtil.toJson(config.getReader().getParameter()), BinlogConf.class);
        super.initFlinkxCommonConf(binlogConf);
    }

    @Override
    public DataStream<RowData> createSource() {
        BinlogInputFormatBuilder builder = new BinlogInputFormatBuilder();
        builder.setBinlogConf(binlogConf);
        return createInput(builder.finish());
    }
}
