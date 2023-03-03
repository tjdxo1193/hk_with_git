package lims.api.ms.service.impl;

import lims.api.common.exception.NoUpdatedDataException;
import lims.api.common.service.ApproveService;
import lims.api.common.vo.ApproveVO;
import lims.api.ms.dao.WrapTestManageDao;
import lims.api.ms.enums.ApproveRequestDivType;
import lims.api.ms.enums.PItemType;
import lims.api.ms.enums.SpecProgress;
import lims.api.ms.service.WrapTestManageService;
import lims.api.ms.vo.ItemManageVO;
import lims.api.ms.vo.SpecManageVO;
import lims.api.ms.vo.WrapTestManageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WrapTestManageServiceImpl implements WrapTestManageService {
	
	private final WrapTestManageDao wrapTestManageDao;
	private final ApproveService approveService;

	@Override
	public List<WrapTestManageVO> getList(WrapTestManageVO param) {
		return wrapTestManageDao.getList(param);
	}

	@Override
	public WrapTestManageVO getOne(WrapTestManageVO param) {
		return wrapTestManageDao.getOne(param);
	}
	
	@Override
	public List<WrapTestManageVO> getSapList(WrapTestManageVO param) {
		return wrapTestManageDao.getSapList(param);
	}
	
	@Override
	public List<WrapTestManageVO> getVersion(WrapTestManageVO param) {
		return wrapTestManageDao.getVersion(param);
	}
	
	@Override
	public List<WrapTestManageVO> getTestItem(WrapTestManageVO param) {
		return wrapTestManageDao.getTestItem(param);
	}
	
	@Override
	public List<WrapTestManageVO> getSpec(WrapTestManageVO param) {
		param.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());
		return wrapTestManageDao.getSpec(param);
	}
	
	@Override
	@Transactional
	public Integer putQmPkGa(WrapTestManageVO baseData, List<WrapTestManageVO> testItemList, List<WrapTestManageVO> deleteTestItemList) {
		int result = 0;
		
		String plntCd = baseData.getPlntCd();
		Integer aitmSpecIdx = baseData.getAitmSpecIdx();
		
		// QM_PITM_AITM_SPEC(품목 시험항목 규격) 테이블(부모)에 데이터가 없다.
		if(plntCd == null || aitmSpecIdx == null) {
			String pkgaDiv = baseData.getPkgaDiv();
			String sapPrdha = baseData.getSapPrdha();
			
			// _SPEC
			WrapTestManageVO qmPitmAitmSpec = new WrapTestManageVO();
			qmPitmAitmSpec.setPlntCd(plntCd);
			qmPitmAitmSpec.setAitmSpecVer(1);
			
			this.insertVersion(qmPitmAitmSpec);
			int searchedAitmSpecIdx = qmPitmAitmSpec.getSearchedAitmSpecIdx();
			
			// _AITM
			testItemList.forEach(vo -> {
				vo.setAitmSpecIdx(searchedAitmSpecIdx);
				vo.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());
			});
			
			// _PKGA
			baseData = testItemList.get(0);
			baseData.setAitmSpecIdx(searchedAitmSpecIdx);
			baseData.setPkgaDiv(pkgaDiv);
			baseData.setSapPrdha(sapPrdha);
			baseData.setPkgaVer(1);
			baseData.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());
			
		// 기존에 저장된 버전이 있는 경우
		} else {
			// 저장된 버전이 있고, 승인된 경우
			if(baseData.getSpecProcCd().equals(SpecProgress.APPROVED.getCode())) {
				// _PKGA
				WrapTestManageVO beforeBaseData = baseData;
				beforeBaseData.setUseVerYn("N");
				this.updateWrapTestUseYn(beforeBaseData);
				
				// _SPEC
				int newAitmSpecVer = baseData.getPkgaVer() + 1;
				baseData.setAitmSpecVer(newAitmSpecVer);
				baseData.setPkgaVer(newAitmSpecVer);
				baseData.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());
				this.insertVersion(baseData);
				
				// _AITM
				int searchedAitmSpecIdx = baseData.getSearchedAitmSpecIdx();
				testItemList.forEach(item -> item.setAitmSpecIdx(searchedAitmSpecIdx));
				
				baseData.setAitmSpecIdx(searchedAitmSpecIdx);
				
			// 데이터는 있지만 아직 임시저장 상태일 경우
			} else if(baseData.getSpecProcCd().equals(SpecProgress.TEMPORARY_STORAGE.getCode())) {
				WrapTestManageVO beforeBaseData = baseData;
				this.updateWrapTest(beforeBaseData);
			// 데이터가 있고, 승인되지도 않고, 임시저장도 아닌 경우
			} else {
				baseData.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());
			}
		}
		
		this.putWrapTest(baseData);
		
		for(WrapTestManageVO vo : testItemList) {
			this.putTestItem(vo);
			result++;
		}
		
		for(WrapTestManageVO vo : deleteTestItemList) {
			this.deleteTestItem(vo);
			result++;
		}
		
		return result;
	}
	
	@Override
	public Integer insertVersion(WrapTestManageVO param) {
		return wrapTestManageDao.insertVersion(param);
	}
	
	@Override
	public Integer putTestItem(WrapTestManageVO param) {
		return wrapTestManageDao.putTestItem(param);
	}
	
	@Override
	public Integer deleteTestItem(WrapTestManageVO param) {
		return wrapTestManageDao.deleteTestItem(param);
	}
	
	
	@Override
	public Integer putWrapTest(WrapTestManageVO param) {
		return wrapTestManageDao.putWrapTest(param);
	}
	
	@Override
	public Integer updateWrapTest(WrapTestManageVO param) {
		return wrapTestManageDao.updateWrapTest(param);
	}
	
	@Override
	public Integer updateWrapTestUseYn(WrapTestManageVO param) {
		return wrapTestManageDao.updateWrapTestUseYn(param);
	}
	
	@Override
	public void approval(WrapTestManageVO param) {
		param.setSpecProcCd(SpecProgress.APPROVED.getCode());
		// 먼저는 해당 sapprdha 코드를 가지고 있는 품목 정보들을 모두 가져온다.(포장재, delyn, useVeryn, 승인완료) pitmCd, pitmVer
		// 그 품목정보들의 규격서중에.. 포장재 이전버전에 aitmidx를 가지고 있는 규격서들을 찾는다.
		Set<String> codes = PItemType.getCodesRelatedToSpec();
		String pItemTypeNotInClauseCondition = "'" +String.join("','", codes) + "'";
		param.setPitmTyp(pItemTypeNotInClauseCondition);
		List<ItemManageVO> itemList = wrapTestManageDao.getItemListBySapPrdha(param);

		for(ItemManageVO ivo : itemList){

			SpecManageVO specInfo = wrapTestManageDao.findSpecListByItemInfo(ivo);

			if(SpecProgress.TEMPORARY_STORAGE.equals(specInfo.getSpecProcCd())
			||SpecProgress.REVIEW_RETURN.equals(specInfo.getSpecProcCd())){

				// TODO 규격 IDX 만 변경
				wrapTestManageDao.updateAitmIdxByTemporaryStorage(specInfo);
				continue;
			}

			if(SpecProgress.REQUEST_REVIEW.equals(specInfo.getSpecProcCd())
					||SpecProgress.APPROVAL_REJECTION.equals(specInfo.getSpecProcCd())
					||SpecProgress.APPROVAL_REQUEST.equals(specInfo.getSpecProcCd())){
				// TODO 버전업 규격 새로 임시저장에 aitmIdx 새거 넣고 기존거 규격삭제
				specInfo.setSpecProcCd(SpecProgress.SPEC_REMOVE.getCode());
				wrapTestManageDao.updateProcessCodeToSpecRemove(specInfo); // 기존거 규격삭제

			}else if(SpecProgress.APPROVED.equals(specInfo.getSpecProcCd())){

				// TODO 버전업 규격 새로 임시저장에 aitmIdx 새거 넣고 기존거 N
				wrapTestManageDao.updateSpecUseVerYnN(specInfo); // 기존거 USE_VER_YN => N

			}

			specInfo.setSpecProcCd(SpecProgress.TEMPORARY_STORAGE.getCode());
			wrapTestManageDao.insertVersionUpBySapPrdha(specInfo); // 새로 임시저장, N , N, max+1
		}

		ApproveVO approveInfo = setApproveVO(param);

		if (param.getPkgaSpecAprIdx() != null) {
			approveInfo.setAprIdx(param.getPkgaSpecAprIdx());
			approveService.approve(param.getPkgaSpecAprIdx());
		} else {
			approveService.create(approveInfo);
			param.setPkgaSpecAprIdx(approveInfo.getAprIdx());
		}

		param.setUseVerYn("Y");
		param.setDelYn("N");
		int result = wrapTestManageDao.approval(param);

		List<WrapTestManageVO> beforeVersionList = wrapTestManageDao.getBeforeVersionList(param);

		for(WrapTestManageVO beforeVersion : beforeVersionList) {
			wrapTestManageDao.updateApprovalSideEffect(beforeVersion);
		}

		if (result == 0) {
			throw new NoUpdatedDataException();
		}
	}

	@Override
	public String getSapPrdhaDuplicateCheck(WrapTestManageVO param) {
		return wrapTestManageDao.getSapPrdhaDuplicateCheck(param);
	}

	private ApproveVO setApproveVO(WrapTestManageVO param) {
		ApproveVO approveInfo = new ApproveVO();
		approveInfo.setPlntCd(param.getPlntCd());
		approveInfo.setAprIp(param.getAprIp());
		approveInfo.setAprReqDiv(ApproveRequestDivType.PACKAGING_TEST_VERSION.getCode());
		approveInfo.setAprUid(param.getLoginUserUid());
		approveInfo.setAprRea(param.getAprRea());
		return approveInfo;
	}

}