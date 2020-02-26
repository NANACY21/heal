package com.personal.pojo;

public class Job_apply {
    private Long id;

    private Long userId;

    private Long positionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    @Override
    public String toString() {
        return "Job_apply{" +
                "id=" + id +
                ", userId=" + userId +
                ", positionId=" + positionId +
                '}';
    }
}