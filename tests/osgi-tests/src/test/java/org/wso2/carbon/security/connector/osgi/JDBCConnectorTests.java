/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.security.connector.osgi;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;
import org.ops4j.pax.exam.testng.listener.PaxExam;
import org.osgi.framework.BundleContext;
import org.testng.annotations.Listeners;
import org.wso2.carbon.kernel.utils.CarbonServerInfo;
import org.wso2.carbon.osgi.test.util.CarbonSysPropConfiguration;
import org.wso2.carbon.osgi.test.util.OSGiTestConfigurationUtils;
import org.wso2.carbon.security.caas.user.core.bean.Action;
import org.wso2.carbon.security.caas.user.core.bean.Permission;
import org.wso2.carbon.security.caas.user.core.bean.Resource;
import org.wso2.carbon.security.caas.user.core.service.RealmService;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

/**
 * Carbon Security JDBC connector OSGi tests.
 */
@Listeners(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
class JDBCConnectorTests {

    static final String DEFAULT_USERNAME = "admin";
    static final String DEFAULT_ROLE = "admin";
    static final String DEFAULT_GROUP = "is";
    static final String DEFAULT_USER_ID = "41dadd2aea6e11e59ce95e5517507c66";
    static final String DEFAULT_ROLE_ID = "985b79ecfcdf11e586aa5e5517507c66";
    static final String DEFAULT_GROUP_ID = "a422aa98ecf411e59ce95e5517507c66";
    static final String DEFAULT_PERMISSION_ID = "f61a1c240df011e6a1483e1d05defe78";
    static final String DEFAULT_IDENTITY_STORE = "JDBCIdentityStore";
    static final String DEFAULT_CREDENTIAL_STORE = "JDBCCredentialStore";
    static final String DEFAULT_AUTHORIZATION_STORE = "JDBCAuthorizationStore";
    static final Resource DEFAULT_RESOURCE = new Resource("reg", "root/resource/id",
            "41dadd2aea6e11e59ce95e5517507c66", "JDBCIdentityStore");
    static final Action ACTION_ADD = new Action("reg", "add");
    static final Permission DEFAULT_PERMISSION = new Permission(DEFAULT_RESOURCE, ACTION_ADD);

    @Inject
    protected BundleContext bundleContext;

    @Inject
    protected RealmService realmService;

    @Inject
    protected CarbonServerInfo carbonServerInfo;

    @Configuration
    public Option[] createConfiguration() {

        List<Option> optionList = new ArrayList<>();

        optionList.add(mavenBundle()
                .groupId("org.slf4j")
                .artifactId("slf4j-api")
                .versionAsInProject());
        optionList.add(mavenBundle()
                .groupId("org.wso2.orbit.com.nimbusds")
                .artifactId("nimbus-jose-jwt")
                .versionAsInProject());
        optionList.add(mavenBundle()
                .groupId("net.minidev.wso2")
                .artifactId("json-smart")
                .versionAsInProject());
        optionList.add(mavenBundle()
                .groupId("org.wso2.carbon.datasources")
                .artifactId("org.wso2.carbon.datasource.core")
                .versionAsInProject());
        optionList.add(mavenBundle()
                .groupId("org.wso2.carbon.jndi")
                .artifactId("org.wso2.carbon.jndi")
                .versionAsInProject());
        optionList.add(mavenBundle()
                .groupId("org.wso2.carbon.messaging")
                .artifactId("org.wso2.carbon.messaging")
                .versionAsInProject());
        optionList.add(mavenBundle()
                .groupId("org.wso2.carbon.caching")
                .artifactId("org.wso2.carbon.caching")
                .versionAsInProject());
        optionList.add(mavenBundle()
                .groupId("org.wso2.carbon.security.caas")
                .artifactId("org.wso2.carbon.security.caas")
                .versionAsInProject());
        optionList.add(mavenBundle()
                .groupId("org.wso2.carbon.security.userstore")
                .artifactId("org.wso2.carbon.security.userstore.jdbc")
                .versionAsInProject());
        optionList.add(mavenBundle()
                .groupId("commons-io.wso2")
                .artifactId("commons-io")
                .versionAsInProject());
        optionList.add(mavenBundle()
                .groupId("com.zaxxer")
                .artifactId("HikariCP")
                .versionAsInProject());
        optionList.add(mavenBundle()
                .groupId("com.h2database")
                .artifactId("h2")
                .versionAsInProject());

        CarbonSysPropConfiguration sysPropConfiguration = new CarbonSysPropConfiguration();
        sysPropConfiguration.setCarbonHome(System.getProperty("carbon.home"));
        sysPropConfiguration.setServerKey("carbon-security");
        sysPropConfiguration.setServerName("WSO2 Carbon Security Server");
        sysPropConfiguration.setServerVersion("1.0.0");

        optionList = OSGiTestConfigurationUtils.getConfiguration(optionList, sysPropConfiguration);

        return optionList.toArray(new Option[optionList.size()]);
    }
}
