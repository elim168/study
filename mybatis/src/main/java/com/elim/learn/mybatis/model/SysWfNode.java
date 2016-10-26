package com.elim.learn.mybatis.model;

/**
 * 流程节点
 * @author Elim
 * 2016年10月15日
 */
public class SysWfNode {
	
    private Integer nodeId;//主键

    private Integer processId;//流程实例ID
    
    private SysWfProcess process;//关联的流程实例	

    private String nodeCode;//节点编号

    private String nodeName;//节点名称

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

	/**
	 * @return the process
	 */
	public SysWfProcess getProcess() {
		return process;
	}

	/**
	 * @param process the process to set
	 */
	public void setProcess(SysWfProcess process) {
		this.process = process;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SysWfNode [nodeId=").append(nodeId).append(", processId=").append(processId)
				.append(", process=").append(process).append(", nodeCode=").append(nodeCode).append(", nodeName=")
				.append(nodeName).append("]");
		return builder.toString();
	}
	
}