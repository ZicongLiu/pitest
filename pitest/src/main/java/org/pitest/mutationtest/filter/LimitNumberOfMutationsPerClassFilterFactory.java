package org.pitest.mutationtest.filter;

import java.util.Properties;

import org.pitest.classpath.CodeSource;

public class LimitNumberOfMutationsPerClassFilterFactory implements MutationFilterFactory {

  public MutationFilter createFilter(Properties props, CodeSource source, int maxMutationsPerClass) {
    if ( maxMutationsPerClass > 0 ) {
    return new LimitNumberOfMutationPerClassFilter(maxMutationsPerClass);
    } else {
      return UnfilteredMutationFilter.INSTANCE;
    }
  }

  public String description() {
    return "Default limit mutations plugin";
  }
  
}
