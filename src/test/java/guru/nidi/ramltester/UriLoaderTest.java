/*
 * Copyright (C) 2014 Stefan Niederhauser (nidin@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package guru.nidi.ramltester;

import org.junit.Ignore;
import org.junit.Test;

import static guru.nidi.ramltester.util.TestUtils.getEnv;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
public class UriLoaderTest {
    @Test
    public void file() {
        assertNotNull(RamlLoaders.loadFromUri("file://" + getClass().getResource("simple.raml").getFile()));
    }

    @Test
    public void classpath() {
        assertNotNull(RamlLoaders.loadFromUri("classpath://guru/nidi/ramltester/simple.raml"));
    }

    @Test
    @Ignore
    public void url() {
        assertNotNull(RamlLoaders.loadFromUri("http://todo"));
    }

    @Test
    public void apiPortal() {
        assertNotNull(RamlLoaders.loadFromUri("apiportal://" + getEnv("API_PORTAL_USER") + ":" + getEnv("API_PORTAL_PASS") + "/test.raml"));
    }

    @Test
    @Ignore
    public void apiDesigner() {
        assertNotNull(RamlLoaders.loadFromUri("apidesigner://todo"));
    }
}
