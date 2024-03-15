package com.hydraql.console;

import java.util.HashMap;
import java.util.Map;

/**
 * string util
 *
 * @author leo.jie (weixiao.me@aliyun.com)
 */
public class StringUtil {
    public static String reverse(String str) {
        return new StringBuffer(str).reverse().toString();
    }

    public static boolean startWithIgnoreCase(String str, String targetStr) {
        if (isBlank(str) || isBlank(targetStr)) {
            return false;
        }
        return str.startsWith(targetStr.toLowerCase()) || str.startsWith(targetStr.toUpperCase());
    }

    public static boolean isBlank(String str) {
        return isBlank((CharSequence) str);
    }

    private static boolean isBlank(CharSequence str) {
        final int length;
        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (!isBlankChar(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static boolean isBlankChar(int c) {
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a'
                || c == '\u0000'
                || c == '\u3164'
                || c == '\u2800'
                || c == '\u180e';
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 驼峰命名法转换为下划线
     *
     * @param camelCaseName 驼峰命名法的字段名
     * @return 下划线字段名
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder builder = new StringBuilder();

        if (isNotBlank(camelCaseName)) {
            builder.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    builder.append("_");
                    builder.append(Character.toLowerCase(ch));
                } else {
                    builder.append(ch);
                }
            }
        }
        return builder.toString();
    }

    /**
     * 下划线命名转换为驼峰
     *
     * @param underscoreName 下划线字段名
     * @return 驼峰命名法
     */
    public static String camelCaseName(String underscoreName) {
        StringBuilder builder = new StringBuilder();
        if (isNotBlank(underscoreName)) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        builder.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        builder.append(ch);
                    }
                }
            }
        }
        return builder.toString();
    }

    public static Map<String, String> parsePropertyToMapFromStr(String prop) {
        if (isBlank(prop)) {
            return new HashMap<>(0);
        }
        Map<String, String> property = new HashMap<>(4);
        for (String p : prop.split(";")) {
            if (isBlank(p)) {
                continue;
            }
            if (!p.contains("=")) {
                continue;
            }
            String[] ps = p.split("=");
            if (ps.length == 2) {
                property.put(ps[0], ps[1]);
            }
        }
        return property;
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        } else {
            int pads = size - str.length();
            if (pads <= 0) {
                return str;
            } else {
                return pads > 8192 ? leftPad(str, size, String.valueOf(padChar)) : repeat(padChar, pads).concat(str);
            }
        }
    }

    public static String repeat(char ch, int repeat) {
        if (repeat <= 0) {
            return "";
        } else {
            char[] buf = new char[repeat];

            for (int i = repeat - 1; i >= 0; --i) {
                buf[i] = ch;
            }

            return new String(buf);
        }
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (isBlank(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (padLen == 1 && pads <= 8192) {
                return leftPad(str, size, padStr.charAt(0));
            } else if (pads == padLen) {
                return padStr.concat(str);
            } else if (pads < padLen) {
                return padStr.substring(0, pads).concat(str);
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for (int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return (new String(padding)).concat(str);
            }
        }
    }

    public static boolean isCreateVirtualTableCommand(String command) {
        if (StringUtil.isBlank(command)) {
            return false;
        }
        command = command.toLowerCase().trim();
        return command.matches("(?i).*create\\s+virtual\\s+table.*") && !command.startsWith("ruby_exec show");
    }

    public static boolean isShowVirtualTablesCommand(String command) {
        if (StringUtil.isBlank(command)) {
            return false;
        }
        command = command.toLowerCase().trim();
        return command.matches("(?i).*show\\s+virtual\\s+tables.*");
    }

    public static boolean isShowCreateVirtualTableCommand(String command) {
        if (StringUtil.isBlank(command)) {
            return false;
        }
        command = command.toLowerCase().trim();
        return command.matches("(?i).*show\\s+create\\s+virtual\\s+table.*");
    }

    public static boolean isShowDropVirtualTableCommand(String command) {
        if (StringUtil.isBlank(command)) {
            return false;
        }
        command = command.toLowerCase().trim();
        return command.matches("(?i).*drop\\s+virtual\\s+table.*");
    }

    public static void main(String[] args) {
        String sql = "create virtual table test:test_sql (\n" +
                " row_key string isrowkey,\n" +
                " f1:id string nullable,\n" +
                " f1:name string nullable,\n" +
                " f1:age int nullable,\n" +
                " f1:job string nullable,\n" +
                " f1:pay double nullable,\n" +
                " f2:address string nullable,\n" +
                " f2:commuter string nullable\n" +
                " );";
        System.out.println(isCreateVirtualTableCommand(sql.replaceAll("\n", "")));

        System.out.println(isShowVirtualTablesCommand("show virtual tables;"));
        System.out.println(isShowCreateVirtualTableCommand("show create virtual table test"));
        System.out.println(isShowDropVirtualTableCommand("drop virtual table test:test_hql;"));
    }
}
