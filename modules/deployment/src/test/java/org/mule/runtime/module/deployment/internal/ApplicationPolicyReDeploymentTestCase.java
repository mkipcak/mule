/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.module.deployment.internal;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;
import static org.mule.runtime.api.deployment.meta.Product.MULE;
import static org.mule.runtime.deployment.model.api.artifact.ArtifactDescriptorConstants.MULE_LOADER_ID;
import static org.mule.runtime.deployment.model.api.plugin.ArtifactPluginDescriptor.MULE_PLUGIN_CLASSIFIER;
import static org.mule.runtime.module.deployment.impl.internal.policy.PropertiesBundleDescriptorLoader.PROPERTIES_BUNDLE_DESCRIPTOR_LOADER_ID;
import static org.mule.runtime.module.deployment.internal.TestPolicyProcessor.invocationCount;

import org.mule.runtime.api.deployment.meta.MuleArtifactLoaderDescriptor;
import org.mule.runtime.api.deployment.meta.MulePolicyModel;
import org.mule.runtime.api.deployment.meta.MulePolicyModel.MulePolicyModelBuilder;
import org.mule.runtime.api.deployment.meta.Product;
import org.mule.runtime.core.api.policy.PolicyParametrization;
import org.mule.runtime.module.deployment.impl.internal.builder.ApplicationFileBuilder;
import org.mule.runtime.module.deployment.impl.internal.builder.ArtifactPluginFileBuilder;
import org.mule.runtime.module.deployment.impl.internal.builder.JarFileBuilder;
import org.mule.runtime.module.deployment.impl.internal.builder.PolicyFileBuilder;
import org.mule.tck.util.CompilerUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Contains test for application deployment with policies on the default domain
 */
public class ApplicationPolicyReDeploymentTestCase extends AbstractDeploymentTestCase {

  private static final String APP_WITH_SIMPLE_EXTENSION_CONFIG = "app-with-simple-extension-config.xml";
  private static final String CONNECTOR_URL = "src/test/resources/mule-objectstore-connector-1.1.1-mule-plugin.jar";

  private static final String OS_POLICY_NAME = "osPolicy";
  private static final String OS_POLICY_ID = "osPolicy";
  public static final String MULE_OBJECTSTORE_CONNECTOR = "mule-objectstore-connector";

  public ApplicationPolicyReDeploymentTestCase(boolean parallelDeployment) {
    super(parallelDeployment);
  }

  @Test
  public void appliesApplicationPolicy() throws Exception {
    policyManager.registerPolicyTemplate(policyWithPlugin().getArtifactFile());

    ApplicationFileBuilder applicationFileBuilder = createExtensionApplicationWithServices(APP_WITH_EXTENSION_PLUGIN_CONFIG, helloExtensionV1Plugin);
    addPackedAppFromBuilder(applicationFileBuilder);

    startDeployment();
    assertApplicationDeploymentSuccess(applicationDeploymentListener, applicationFileBuilder.getId());

    PolicyFileBuilder policy = policyWithPlugin();

    policyManager.addPolicy(applicationFileBuilder.getId(), policy.getArtifactId(),
        new PolicyParametrization(OS_POLICY_ID, poinparameters -> true, 1, emptyMap(), getResourceFile(format("/%s.xml", OS_POLICY_NAME)), emptyList()));

    executeApplicationFlow("main");
    assertThat(invocationCount, equalTo(1));

    policyManager.removePolicy(applicationFileBuilder.getId(), policy.getArtifactId());

    executeApplicationFlow("main");
    assertThat(invocationCount, equalTo(1));

    policyManager.addPolicy(applicationFileBuilder.getId(), policy.getArtifactId(),
        new PolicyParametrization(OS_POLICY_ID, poinparameters -> true, 1, emptyMap(), getResourceFile(format("/%s.xml", OS_POLICY_NAME)), emptyList()));

    executeApplicationFlow("main");
    assertThat(invocationCount, equalTo(2));
  }

  private PolicyFileBuilder policyWithPlugin() {
    Map<String, Object> map = new HashMap<>();
    map.put("store", "");
    MulePolicyModelBuilder mulePolicyModelBuilder = new MulePolicyModelBuilder()
        .setMinMuleVersion(MIN_MULE_VERSION).setName(OS_POLICY_NAME)
        .setRequiredProduct(Product.MULE)
        .withBundleDescriptorLoader(createBundleDescriptorLoader(OS_POLICY_NAME, MULE_POLICY_CLASSIFIER, PROPERTIES_BUNDLE_DESCRIPTOR_LOADER_ID))
        .withClassLoaderModelDescriptorLoader(new MuleArtifactLoaderDescriptor(MULE_LOADER_ID, map));

    return new PolicyFileBuilder(OS_POLICY_NAME)
        .describedBy(mulePolicyModelBuilder.build())
        .dependingOn(new JarFileBuilder(MULE_OBJECTSTORE_CONNECTOR, new File(CONNECTOR_URL)).withClassifier(MULE_PLUGIN_CLASSIFIER));
  }
}


