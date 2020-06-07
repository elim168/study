package com.elim.study.hadoop.mr.friendrecommend;

import org.apache.hadoop.io.WritableComparable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Step2Key implements WritableComparable<Step2Key> {

    /**
     * 第一个用户的名称
     */
    private String user1;
    /**
     * 第二个用户的名称
     */
    private String user2;
    /**
     * 两个用户共同好友的数量
     */
    private int commonFriendCount;

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.user1);
        out.writeUTF(this.user2);
        out.writeInt(this.commonFriendCount);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.user1 = in.readUTF();
        this.user2 = in.readUTF();
        this.commonFriendCount = in.readInt();
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public int getCommonFriendCount() {
        return commonFriendCount;
    }

    public void setCommonFriendCount(int commonFriendCount) {
        this.commonFriendCount = commonFriendCount;
    }

    private static final Logger logger = LoggerFactory.getLogger(Step2Key.class);

    @Override
    public int compareTo(Step2Key o) {
        //先按user1排序，可以把同一个用户的数据排到一起，再按共同好友数量排序，最后按user2排序。
        int result = this.user1.compareTo(o.user1);
        if (result != 0) {
            return result;
        }
        result = Integer.compare(this.commonFriendCount, o.commonFriendCount);
        if (result != 0) {
            return -result;//按共同好友数进行倒序排列
        }
        return this.user2.compareTo(o.user2);
    }

    @Override
    public String toString() {
        return "Step2Key{" +
                "user1='" + user1 + '\'' +
                ", user2='" + user2 + '\'' +
                ", commonFriendCount=" + commonFriendCount +
                '}';
    }
}
