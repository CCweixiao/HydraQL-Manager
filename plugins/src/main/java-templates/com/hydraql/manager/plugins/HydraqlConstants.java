package com.hydraql.manager.plugins;

/**
 * Hydraql constants from compilation time by maven.
 */
public final class HydraqlConstants {
  /* Hadoop version, specified in maven property. **/
  public static final String HYDRAQL_HBASE_VERSION = "${hydraql.hbase.version}";

  private HydraqlConstants() {} // prevent instantiation
}
