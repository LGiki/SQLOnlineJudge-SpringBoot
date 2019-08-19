package cn.edu.jmu.sqlonlinejudge.service.enumerate;

/**
 * @author sgh
 * @date 2019/6/20 17:27
 */
public interface BaseEnum<E extends Enum<?>, T> {

    /**
     * 真正与数据库进行映射的值
     */
    public T getValue();

    /**
     * 显示的信息
     */
    public String getDisplayName();
}