package com.personal.pojo.web;

import java.util.Vector;

/**行业树
 * 衍生的实体 为了匹配前端element ui的数据格式 级联选择器
 * @author 李箎
 */
public class Node {
    private String value;
    private String label;
    private Vector<Node> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Vector<Node> getChildren() {
        return children;
    }

    public void setChildren(Vector<Node> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
