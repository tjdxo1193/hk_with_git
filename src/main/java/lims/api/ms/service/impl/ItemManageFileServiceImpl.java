package lims.api.ms.service.impl;

import lims.api.ms.dao.MsElnCtRptFileDao;
import lims.api.ms.domain.MsElnCtRptFileKey;
import lims.api.ms.entity.MsElnCtRptFile;
import lims.api.ms.service.ItemManageFileService;
import lims.api.ms.vo.ItemManageVO;
import lims.api.ms.vo.MsElnCtRptFileVO;
import lims.api.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemManageFileServiceImpl implements ItemManageFileService {

    private final MsElnCtRptFileDao fileDao;

    @Override
    public List<MsElnCtRptFileVO> getMsElnCtRptFileList(String ctrptNo) {
        MsElnCtRptFileVO vo = new MsElnCtRptFileVO();
        vo.setCtrptNo(ctrptNo);
        vo.setCtId(ctrptNo);
        return fileDao.getMsElnCtRptFileList(vo);
    }

    @Override
    public MsElnCtRptFileKey save(List<MultipartFile> files) {
        return this.save(files, fileDao.nextIdx());
    }

    @Override
    public MsElnCtRptFileKey save(List<MultipartFile> files, String ctId) {
        MsElnCtRptFileKey result = new MsElnCtRptFileKey();

        if(files != null) {
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);

                MsElnCtRptFileVO seqParam = new MsElnCtRptFileVO();
                seqParam.setCtId(ctId);
                String ctSeq = fileDao.nextSeq(seqParam);

                MsElnCtRptFileVO saveParam = new MsElnCtRptFileVO();
                saveParam.setCtId(ctId);
                saveParam.setCtSeq(ctSeq);
                saveParam.setFileName(file.getOriginalFilename());
                saveParam.setData(FileUtil.toBytes(file));
//                saveParam.setFileType(file.getContentType());

                int savedCount = fileDao.save(saveParam);

                result.setCtId(ctId);

                if (savedCount == 0) {
                    throw new RuntimeException("Not saved file" + file.getName());
                }
            }
        }

        return result;
    }

    @Override
    public void deleteFile(MsElnCtRptFileKey removedFile) {
        String ctId = removedFile.getCtId();
        String ctSeq = removedFile.getCtSeq();

        MsElnCtRptFileVO param = new MsElnCtRptFileVO();
        param.setCtId(ctId);
        param.setCtSeq(ctSeq);
        int deletedCount = fileDao.delete(param);

        if(deletedCount == 0) {
            throw new RuntimeException("Failed delete a file. fileIdx(ctId, ctSeq): " + ctId + ", " + ctSeq);
        }
    }

}
