package com.medicinalManage.serviceImpl.batch;

import com.github.pagehelper.Page;
import com.medicinalManage.common.PageParams;
import com.medicinalManage.common.PageResultEntity;
import com.medicinalManage.dao.Dao;
import com.medicinalManage.exception.BusinessException;
import com.medicinalManage.model.batch.BatchDo;
import com.medicinalManage.model.batch.BatchDto;
import com.medicinalManage.model.batch.BatchQueryDo;
import com.medicinalManage.model.batch.BatchVo;
import com.medicinalManage.service.batch.BatchService;
import com.medicinalManage.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchServiceImpl implements BatchService {
    @Autowired
    Dao dao;

    @Override
    public PageResultEntity<BatchVo> getBatchList(BatchQueryDo batchQueryDo, PageParams pageParams) {
        Page<BatchVo> batchVoPage = dao.getPageList("com.medicinalManage.mapper.batch.BatchMapper.getBatchList", batchQueryDo, pageParams);
        return new PageResultEntity<>(batchVoPage);
    }

    @Override
    public void addBatch(BatchDto batchDto) throws Exception {
        BatchDo batchDo = new BatchDo();
        BeanUtils.copyProperties(batchDto, batchDo);
        String uuid = StringUtils.getUUid();
        batchDo.setBatchId(uuid);
        int count = dao.insert("com.medicinalManage.mapper.batch.BatchMapper.addBatch", batchDo);
        if (count == 0) {
            throw new BusinessException("批次名称已存在");
        }
    }

    @Override
    public void updateBatch(String batchId, BatchDto batchDto) throws Exception {
        BatchDo batchDo = new BatchDo();
        BeanUtils.copyProperties(batchDto, batchDo);
        batchDo.setBatchId(batchId);
        int count = dao.update("com.medicinalManage.mapper.batch.BatchMapper.updateBatch", batchDo);
        if (count == 0) {
            throw new BusinessException("批次名称已存在");
        }
    }

    @Override
    public void deleteBatch(String batchId) {
        dao.delete("com.medicinalManage.mapper.batch.BatchMapper.deleteBatch", batchId);
    }
}
