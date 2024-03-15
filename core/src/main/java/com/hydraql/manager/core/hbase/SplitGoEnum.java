package com.hydraql.manager.core.hbase;

/**
 * @author leojie 2020/10/7 9:48 下午
 */
public enum SplitGoEnum {
    /**
     * 预分区策略，十六进制前缀
     */
    HEX_STRING_SPLIT("HexStringSplit"),
    /**
     * 十进制前缀
     */
    DECIMAL_STRING_SPLIT("DecimalStringSplit"),
    /**
     * 随机字符串前缀
     */
    UNIFORM_SPLIT("UniformSplit");

    private final String splitGo;

    SplitGoEnum(String splitGo) {
        this.splitGo = splitGo;
    }

    public String getSplitGo() {
        return splitGo;
    }

    public static SplitGoEnum getSplitGoEnum(String splitGo) {
        for (SplitGoEnum splitGoEnum : SplitGoEnum.values()) {
            if (splitGoEnum.splitGo.equals(splitGo)) {
                return splitGoEnum;
            }
        }
        return null;
    }

}
