package lims.api.sy.service;

import lims.api.sy.vo.DocumentVO;

import java.util.List;

public interface DocumentService {

    List<DocumentVO> getAllDocuments(DocumentVO param);

    DocumentVO create(DocumentVO param);

    DocumentVO update(DocumentVO param);

}