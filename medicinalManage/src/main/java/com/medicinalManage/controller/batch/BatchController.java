package com.medicinalManage.controller.batch;

import com.medicinalManage.common.PageParams;
import com.medicinalManage.common.PageResultEntity;
import com.medicinalManage.common.ResponseEntity;
import com.medicinalManage.model.batch.BatchDto;
import com.medicinalManage.model.batch.BatchQueryDo;
import com.medicinalManage.model.batch.BatchVo;
import com.medicinalManage.service.batch.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class BatchController {
    @Autowired
    BatchService batchService;

    @GetMapping("/batch/list")
    public ResponseEntity<PageResultEntity<BatchVo>> getBatchList(@Validated BatchQueryDo batchQueryDo, PageParams pageParams) {
        return new ResponseEntity<>(batchService.getBatchList(batchQueryDo, pageParams));
    }

    @PostMapping("/batch")
    public ResponseEntity addBatch(@Validated @RequestBody BatchDto batchDto) throws Exception {
        batchService.addBatch(batchDto);
        return new ResponseEntity();
    }

    @PutMapping("/batch")
    public ResponseEntity updateBatch(@NotNull(message = "批次id不能为空") @NotBlank(message = "批次id不能为空") String batchId, @Validated @RequestBody BatchDto batchDto) throws Exception {
        batchService.updateBatch(batchId, batchDto);
        return new ResponseEntity();
    }

    @DeleteMapping("/batch")
    public ResponseEntity deleteBatch(@NotNull(message = "批次id不能为空") @NotBlank(message = "批次id不能为空") String batchId) {
        batchService.deleteBatch(batchId);
        return new ResponseEntity();
    }
}
