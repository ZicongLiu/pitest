package org.pitest.testapi;

import org.pitest.classinfo.ClassByteArraySource;
import org.pitest.plugin.ClientClasspathPlugin;

public interface TestPluginFactory extends ClientClasspathPlugin {

  Configuration createTestFrameworkConfiguration(final TestGroupConfig config,
      final ClassByteArraySource source);

}
