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
package com.streamsets.pipeline.stage.origin.jython;

import com.streamsets.pipeline.stage.origin.scripting.AbstractScriptingDSource;
import com.streamsets.pipeline.stage.origin.scripting.ScriptingOriginTestUtil;

import org.junit.Test;

public class TestJythonSource {

  private static final Class DSOURCECLASS = JythonDSource.class;

  private static AbstractScriptingDSource getDSource() {
    return new JythonDSource();
  }

  @Test
  public void testResumeGenerateRecords() throws Exception {
    ScriptingOriginTestUtil.testResumeGenerateRecords(
        DSOURCECLASS,
        getDSource(),
        "GeneratorOriginScript.py"
    );
  }

  @Test
  public void testMultiThreadGenerateRecords() throws Exception {
    ScriptingOriginTestUtil.testMultiThreadGenerateRecords(
        DSOURCECLASS,
        getDSource(),
        "MultiGeneratorOriginScript.py"
    );
  }

  @Test
  public void testAllBindings() throws Exception {
    ScriptingOriginTestUtil.testAllBindings(
        DSOURCECLASS,
        getDSource(),
        "TestAllBindings.py"
    );
  }

  @Test
  public void testMultiThreadedOffset() throws Exception {
    ScriptingOriginTestUtil.testMultiThreadedOffset(
        DSOURCECLASS,
        getDSource(),
        "MultiGeneratorOriginScript.py"
    );
  }

  @Test
  public void testNullTypes() throws Exception {
    ScriptingOriginTestUtil.testNullTypes(
        DSOURCECLASS,
        getDSource(),
        "TestNullTypes.py"
    );
  }

}
