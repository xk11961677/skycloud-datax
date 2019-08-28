package com.alibaba.datax.common.log;

import lombok.Data;

/**
 * @author
 */
@Data
public class DataxResult {
    //任务启动时刻
    private long startTimeStamp;
    //任务结束时刻
    private long endTimeStamp;
    //任务总时耗
    private long totalCosts;
    //任务平均流量
    private long byteSpeedPerSecond;
    //记录写入速度
    private long recordSpeedPerSecond;
    //读出记录总数
    private long totalReadRecords;
    //读写失败总数
    private long totalErrorRecords;
    //成功记录总数
    private long transformerSucceedRecords;
    // 失败记录总数
    private long transformerFailedRecords;
    // 过滤记录总数
    private long transformerFilterRecords;
    //字节数
    private long readSucceedBytes;
    //转换开始时间
    private long endTransferTimeStamp;
    //转换结束时间
    private long startTransferTimeStamp;
    //转换总耗时
    private long transferCosts;

    private String exceptionMessage;

    public DataxResult() {}

    public DataxResult(long startTimeStamp, long endTimeStamp, long totalCosts, long byteSpeedPerSecond, long recordSpeedPerSecond, long totalReadRecords, long totalErrorRecords, long transformerSucceedRecords, long transformerFailedRecords, long transformerFilterRecords, long readSucceedBytes, long endTransferTimeStamp, long startTransferTimeStamp, long transferCosts) {
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.totalCosts = totalCosts;
        this.byteSpeedPerSecond = byteSpeedPerSecond;
        this.recordSpeedPerSecond = recordSpeedPerSecond;
        this.totalReadRecords = totalReadRecords;
        this.totalErrorRecords = totalErrorRecords;
        this.transformerSucceedRecords = transformerSucceedRecords;
        this.transformerFailedRecords = transformerFailedRecords;
        this.transformerFilterRecords = transformerFilterRecords;
        this.readSucceedBytes = readSucceedBytes;
        this.endTransferTimeStamp = endTransferTimeStamp;
        this.startTransferTimeStamp = startTransferTimeStamp;
        this.transferCosts = transferCosts;
    }

    @Override
    public String toString() {
        return "DataxResult{" +
                "startTimeStamp=" + startTimeStamp +
                ", endTimeStamp=" + endTimeStamp +
                ", totalCosts=" + totalCosts +
                ", byteSpeedPerSecond=" + byteSpeedPerSecond +
                ", recordSpeedPerSecond=" + recordSpeedPerSecond +
                ", totalReadRecords=" + totalReadRecords +
                ", totalErrorRecords=" + totalErrorRecords +
                ", transformerSucceedRecords=" + transformerSucceedRecords +
                ", transformerFailedRecords=" + transformerFailedRecords +
                ", transformerFilterRecords=" + transformerFilterRecords +
                ", readSucceedBytes=" + readSucceedBytes +
                ", endTransferTimeStamp=" + endTransferTimeStamp +
                ", startTransferTimeStamp=" + startTransferTimeStamp +
                ", transferCosts=" + transferCosts +
                '}';
    }


    public void fillPropertiespublic(long startTimeStamp,
                                     long endTimeStamp,
                                     long totalCosts,
                                     long byteSpeedPerSecond,
                                     long recordSpeedPerSecond,
                                     long totalReadRecords,
                                     long totalErrorRecords,
                                     long transformerSucceedRecords,
                                     long transformerFailedRecords,
                                     long transformerFilterRecords,
                                     long readSucceedBytes,
                                     long endTransferTimeStamp,
                                     long startTransferTimeStamp,
                                     long transferCosts) {
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.totalCosts = totalCosts;
        this.byteSpeedPerSecond = byteSpeedPerSecond;
        this.recordSpeedPerSecond = recordSpeedPerSecond;
        this.totalReadRecords = totalReadRecords;
        this.totalErrorRecords = totalErrorRecords;
        this.transformerSucceedRecords = transformerSucceedRecords;
        this.transformerFailedRecords = transformerFailedRecords;
        this.transformerFilterRecords = transformerFilterRecords;
        this.readSucceedBytes = readSucceedBytes;
        this.endTransferTimeStamp = endTransferTimeStamp;
        this.startTransferTimeStamp = startTransferTimeStamp;
        this.transferCosts = transferCosts;
    }
}
