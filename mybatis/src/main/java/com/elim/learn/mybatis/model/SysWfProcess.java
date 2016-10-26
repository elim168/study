package com.elim.learn.mybatis.model;

import java.util.Date;
import java.util.List;

/**
 * 流程实例
 * @author Elim
 * 2016年10月15日
 */
public class SysWfProcess {
	
    private Integer id;

    private Integer templateId;//模板ID

    private Integer creator;

    private Date createTime;
    
    private List<SysWfNode> nodes;//包含的流程节点		

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	/**
	 * @return the nodes
	 */
	public List<SysWfNode> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(List<SysWfNode> nodes) {
		this.nodes = nodes;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SysWfProcess [id=").append(id).append(", templateId=").append(templateId).append(", creator=")
				.append(creator).append(", createTime=").append(createTime).append(", nodes=").append(nodes)
				.append("]");
		return builder.toString();
	}
	
}