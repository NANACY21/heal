package com.personal.pojo;

/**职位表，枚举表
 * @author 李箎
 */
public class Position_enum {
    private Long id;

    private String name;

    private Long tradeId;

    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Position_enum{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tradeId=" + tradeId +
                ", parentId=" + parentId +
                '}';
    }
}