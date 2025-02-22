/*
 * Copyright 2019 StreamSets Inc.
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
package com.streamsets.pipeline.stage.destination.rabbitmq;

import com.streamsets.pipeline.api.Config;
import com.streamsets.pipeline.api.StageUpgrader;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRabbitTargetUpgrader {
  @Test
  public void testUpgradeV1ToV2() {
    List<Config> configs = new ArrayList<>();
    String prefix = "dataFormatConfig.";
    configs.add(new Config(prefix + "avroSchema", ""));

    StageUpgrader.Context context = Mockito.mock(StageUpgrader.Context.class);
    Mockito.doReturn(1).when(context).getFromVersion();
    Mockito.doReturn(2).when(context).getToVersion();

    RabbitTargetUpgrader upgrader = new RabbitTargetUpgrader();
    Map<String, Object> map = configListToMap(upgrader.upgrade(configs, context));

    Assert.assertEquals(9, map.size());

    Assert.assertTrue(map.containsKey(prefix + "avroSchemaSource"));
    Assert.assertEquals("INLINE", map.get(prefix + "avroSchemaSource"));

    Assert.assertTrue(map.containsKey(prefix + "registerSchema"));
    Assert.assertEquals(false, map.get(prefix + "registerSchema"));

    Assert.assertTrue(map.containsKey(prefix + "schemaRegistryUrlsForRegistration"));
    Assert.assertEquals(new ArrayList<>(), map.get(prefix + "schemaRegistryUrlsForRegistration"));

    Assert.assertTrue(map.containsKey(prefix + "schemaRegistryUrls"));
    Assert.assertEquals(new ArrayList<>(), map.get(prefix + "schemaRegistryUrls"));

    Assert.assertTrue(map.containsKey(prefix + "schemaLookupMode"));
    Assert.assertEquals("AUTO", map.get(prefix + "schemaLookupMode"));

    Assert.assertTrue(map.containsKey(prefix + "subject"));
    Assert.assertEquals("", map.get(prefix + "subject"));

    Assert.assertTrue(map.containsKey(prefix + "subjectToRegister"));
    Assert.assertEquals("", map.get(prefix + "subjectToRegister"));

    Assert.assertTrue(map.containsKey(prefix + "schemaId"));
    Assert.assertEquals(0, map.get(prefix + "schemaId"));
  }

  @Test
  public void testUpgradeV2ToV3() {
    List<Config> configs = new ArrayList<>();
    configs.add(new Config("conf.basicPropertiesConfig.setAMQPMessageProperties", true));

    StageUpgrader.Context context = Mockito.mock(StageUpgrader.Context.class);
    Mockito.doReturn(2).when(context).getFromVersion();
    Mockito.doReturn(3).when(context).getToVersion();

    RabbitTargetUpgrader upgrader = new RabbitTargetUpgrader();
    Map<String, Object> map = configListToMap(upgrader.upgrade(configs, context));

    Assert.assertEquals(2, map.size());

    Assert.assertTrue(map.containsKey("conf.basicPropertiesConfig.setAMQPMessageProperties"));
    Assert.assertEquals(true, map.get("conf.basicPropertiesConfig.setAMQPMessageProperties"));

    Assert.assertTrue(map.containsKey("conf.basicPropertiesConfig.setExpiration"));
    Assert.assertEquals(true, map.get("conf.basicPropertiesConfig.setExpiration"));
  }

  private Map<String, Object> configListToMap(List<Config> configs) {
    Map<String, Object> map = new HashMap<>();
    for(Config c : configs) {
      map.put(c.getName(), c.getValue());
    }
    return map;
  }

}
