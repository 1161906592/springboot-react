package com.medicinalManage.service.batch;

import com.medicinalManage.common.PageParams;
import com.medicinalManage.common.PageResultEntity;
import com.medicinalManage.model.batch.BatchDto;
import com.medicinalManage.model.batch.BatchQueryDo;
import com.medicinalManage.model.batch.BatchVo;

public interface BatchService {
    PageResultEntity<BatchVo> getBatchList(BatchQueryDo batchQueryDo, PageParams pageParams);
    void addBatch(BatchDto batchDto) throws Exception;
    void updateBatch(String batchId, BatchDto batchDto) throws Exception;
    void deleteBatch(String batchId);
}
