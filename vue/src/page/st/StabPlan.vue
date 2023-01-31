<template>
  <FormWithHeader v-bind="searchForm" @button-click="onClickSearchBtn" />

  <AUIGridWithHeader
    v-bind="gridForSearchResult"
    @grid-created="(proxy) => $setState('gridForSearchResult.$grid', proxy)"
    @grid-button-click="onClickBtnInGrid"
  />

  <FormWithHeader v-bind="stabItemSearchForm" @form-event="stabItemSearchFormEvent" />

  <FormWithHeader v-bind="stabInfoRegForm" />

  <StabItemSearchModal
    :show="stabItemSearchModal.show"
    @close="hideModal('stabItemSearchModal')"
    @select="setStabItemSearchForm"
  />

  <FileAttacherModal :show="fileAttacherModal.show" @close="hideModal('fileAttacherModal')" />

  <InputReasonModal
    :show="inputReasonModal.show"
    @modalReturnDataEvent="modalReturnDataEvent"
    @close="hideModal('inputReasonModal')"
  />

  <DesignateApproversModal
    :show="designateApproversModal.show"
    @close="this.hideModal('designateApproversModal')"
  />

  <StabDetailPlanRegModal
    :show="stabDetailPlanRegModal.show"
    :height="stabDetailPlanRegModal.height"
    :sbtAnsProc="stabDetailPlanRegModal.sbtAnsProc"
    :selectedItem="stabDetailPlanRegModal.selectedItem"
    :disable="stabDetailPlanRegModal.disable"
    @close="hideModal('stabDetailPlanRegModal')"
  />

  <RequestApproverModal
    :show="requestApproverModal.show"
    :aprReqDiv="requestApproverModal.aprReqDiv"
    @close="hideModal('requestApproverModal')"
    @modalReturnDataEvent="preAllApprove"
  ></RequestApproverModal>

  <ActionBar :buttons="buttonGroups.buttons" @button-click="onEventsButton" />
</template>

<script>
import {
  StabItemSearchModal,
  FileAttacherModal,
  DesignateApproversModal,
  StabDetailPlanRegModal,
  InputReasonModal,
  RequestApproverModal,
} from '@/page/modal';
import { FormUtil } from '@/util';

import values from './values/stabPlan';

export default {
  name: 'StabPlanPage',
  components: {
    StabItemSearchModal,
    FileAttacherModal,
    DesignateApproversModal,
    StabDetailPlanRegModal,
    InputReasonModal,
    RequestApproverModal,
  },
  data() {
    const { searchForm, gridForSearchResult, stabItemSearchForm, stabInfoRegForm, buttonGroups } =
      this.$copy(values);
    return {
      searchForm: {
        ...searchForm.static,
        forms: searchForm.forms(),
      },

      gridForSearchResult: {
        ...gridForSearchResult.static,
        columns: gridForSearchResult.columns(),
        event: {
          cellDoubleClick: (e) => this.getItemInfoInGrid(e),
        },
      },

      stabItemSearchForm: {
        ...stabItemSearchForm.static,
        forms: stabItemSearchForm.forms(),
      },

      stabInfoRegForm: {
        ...stabInfoRegForm.static,
        forms: stabInfoRegForm.forms(),
      },

      buttonGroups: {
        buttons: buttonGroups.buttons,
      },

      stabItemSearchModal: {
        show: false,
      },
      fileAttacherModal: {
        show: false,
      },
      inputReasonModal: {
        show: false,
        updateType: null,
      },
      designateApproversModal: {
        show: false,
      },
      stabDetailPlanRegModal: {
        show: false,
        selectedItem: null,
        sbtAnsProc: null,
        disable: false,
      },
      requestApproverModal: {
        show: false,
        updateType: null,
        aprReqDiv: 'S0050002',
      },
    };
  },
  mounted() {
    this.doInit();
  },
  methods: {
    doInit() {
      this.fetchSearchResult();
      this.resetUpdateType();
      this.resetButtons();
      this.resetStabItemSearchForm();
      this.resetStabInfoRegForm();
    },
    //조회폼
    onClickSearchBtn({ name }) {
      if (name == 'search') {
        this.doInit();
      }
    },
    async fetchSearchResult() {
      const { $grid } = this.gridForSearchResult;
      const { forms } = this.searchForm;

      const parameter = FormUtil.getData(forms);
      const data = await $grid
        ._useLoader(() => this.$axios.get('st/stabPlan', parameter))
        .then(({ data }) => data);

      $grid.setGridData(data);
    },
    getItemInfoInGrid(event) {
      FormUtil.enable(this.stabInfoRegForm.forms);
      const selectedItem = event.item;

      FormUtil.setData(this.stabItemSearchForm.forms, selectedItem);
      FormUtil.setData(this.stabInfoRegForm.forms, selectedItem);
      let disabledBtnName;
      let enabledBtnName;

      //폼안에 버튼 disable, enabled
      this.btnTypeChangeInform();

      // 안정성품목조회 폼 정보 입력
      this.setTxtinfo(selectedItem);

      const { sbtAnsProc } = selectedItem;
      if (
        this.isSaved(sbtAnsProc) ||
        this.isApproveReject(sbtAnsProc) ||
        this.isStopReject(sbtAnsProc)
      ) {
        disabledBtnName = ['stopRequest', 'stopCancelRequest', 'save'];
        enabledBtnName = ['approveRequest', 'update', 'delete', 'init'];

        // 등록 폼에서 수정 못하는 요소들
        FormUtil.disable(this.stabInfoRegForm.forms, [
          'ansTrmCd',
          'ansStrDt',
          'docNo',
          'sbtAnsPlnNo',
        ]);
        this.stabDetailPlanRegModal.disable = false;
      } else if (
        this.isStopRequest(sbtAnsProc) ||
        this.isStopCancelRequest(sbtAnsProc) ||
        this.isResultApproved(sbtAnsProc)
      ) {
        disabledBtnName = [
          'stopRequest',
          'stopCancelRequest',
          'approveRequest',
          'save',
          'update',
          'delete',
        ];
        enabledBtnName = ['init'];
        FormUtil.disable(this.stabInfoRegForm.forms);
        this.stabDetailPlanRegModal.disable = true;
      } else if (this.isStop(sbtAnsProc) || this.isStopCancelReject(sbtAnsProc)) {
        disabledBtnName = ['stopRequest', 'approveRequest', 'save', 'update', 'delete'];
        enabledBtnName = ['stopCancelRequest', 'init'];
        FormUtil.disable(this.stabInfoRegForm.forms);
        this.stabDetailPlanRegModal.disable = true;
      } else if (this.isApproveRequest(sbtAnsProc)) {
        disabledBtnName = ['stopCancelRequest', 'approveRequest', 'save', 'update', 'delete'];
        enabledBtnName = ['stopRequest', 'init'];
        FormUtil.disable(this.stabInfoRegForm.forms);
        this.stabDetailPlanRegModal.disable = true;
      } else if (this.isApproved(sbtAnsProc) || this.isStopCancel(sbtAnsProc)) {
        disabledBtnName = ['stopCancelRequest', 'approveRequest', 'save', 'delete'];
        enabledBtnName = ['stopRequest', 'update', 'init'];
        FormUtil.disable(this.stabInfoRegForm.forms);
        this.stabDetailPlanRegModal.disable = true;
      } else {
        disabledBtnName = [];
        enabledBtnName = [];
        this.stabDetailPlanRegModal.disable = true;
      }

      this.disabledBtnInBtnGroups(disabledBtnName);
      this.enabledBtnInBtnGroups(enabledBtnName);
    },
    setStabDetailPlanRegModalPropsSelectedItem(item = null) {
      this.stabDetailPlanRegModal.selectedItem = item;
    },
    resetUpdateType() {
      this.inputReasonModal.updateType = null;
    },
    resetStabItemSearchForm() {
      this.stabItemSearchForm.forms = values.stabItemSearchForm.forms();
    },
    resetStabInfoRegForm() {
      this.stabInfoRegForm.forms = values.stabInfoRegForm.forms();
    },
    resetButtons() {
      const disabledBtnName = [
        'stopCancelRequest',
        'approveRequest',
        'stopRequest',
        'update',
        'delete',
      ];
      const enabledBtnName = ['save', 'init'];

      this.disabledBtnInBtnGroups(disabledBtnName);
      this.enabledBtnInBtnGroups(enabledBtnName);
    },

    btnTypeChangeInform() {
      FormUtil.disableButtons(this.stabItemSearchForm.forms[0]._multiForms, ['search']);
      FormUtil.enableButtons(this.stabItemSearchForm.forms[0]._multiForms, [
        'detailPlanReg',
        'itemReg',
      ]);
    },
    disabledBtnInBtnGroups(btnName) {
      FormUtil.disableButtons(this.buttonGroups.buttons, btnName);
    },
    enabledBtnInBtnGroups(btnName) {
      FormUtil.enableButtons(this.buttonGroups.buttons, btnName);
    },

    // 조회결과 그리드
    onClickBtnInGrid({ dataField }) {
      if (dataField == 'fileAttacher') {
        this.showModal('fileAttacherModal');
      }
    },

    // 자사품목 - 안정성품목조회
    stabItemSearchFormEvent(event) {
      if (event.originEvent === 'search') {
        this.showModal('stabItemSearchModal');
      }
      if (event.originEvent === 'itemReg') {
        this.showModal('stabDetailPlanRegModal');
        this.setStabDetailPlanRegModalModalProp();
      }
    },

    // 모달 띄우기, 가리기
    showModal(modalName) {
      this.$setState(modalName, { show: true });
    },
    hideModal(modalName) {
      this.$setState(modalName, { show: false });
    },

    setStabDetailPlanRegModalModalProp() {
      const stabItemSearchFormForms = this.stabItemSearchForm.forms;
      const stabInfoRegFormForms = this.stabInfoRegForm.forms;
      const item = FormUtil.getData(stabItemSearchFormForms);
      const sbtPlnIdx = FormUtil.getValue(stabInfoRegFormForms, 'sbtPlnIdx');

      if (item) {
        const selectedItem = { ...item, sbtPlnIdx };
        this.setStabDetailPlanRegModalPropsSelectedItem(selectedItem);
      }

      return;
    },

    onEventsButton({ name }) {
      if (name == 'stopRequest') {
        this.setUpdateTypeAndInputReasonModalShowOn('stopRequest');
      }
      if (name == 'stopCancelRequest') {
        this.requestApproverModal.show = true;
        this.requestApproverModal.updateType = 'stopCancelRequest';
      }
      if (name == 'approveRequest') {
        this.requestApproverModal.show = true;
        this.requestApproverModal.updateType = 'approveRequest';
      }
      if (name == 'save') {
        this.stabItemSearchForm.forms.validate().then(() =>
          this.stabInfoRegForm.forms.validate().then(() => {
            this.save();
          }),
        );
      }
      if (name == 'update') {
        this.stabInfoRegForm.forms.validate().then(() => {
          this.setUpdateTypeAndInputReasonModalShowOn('update');
        });
      }
      if (name == 'delete') {
        this.setUpdateTypeAndInputReasonModalShowOn('delete');
      }
      if (name == 'reset') {
        this.doInit();
      }
    },
    setStabItemSearchForm(item) {
      this.setTxtinfo(item);
    },
    setTxtinfo(item) {
      const { forms } = this.stabItemSearchForm;
      const txtinfo1 =
        'Initial 시험번호 : ' +
        (item.ansNo ? item.ansNo : '') +
        '\n' +
        '제조번호 : ' +
        (item.lotNo ? item.lotNo : '');

      const txtinfo2 =
        '채취자 : ' +
        (item.clltUidNm ? item.clltUidNm : '') +
        '\n' +
        '채취일자 : ' +
        (item.clltDt ? item.clltDt : '') +
        '\n' +
        '제조일자 : ' +
        (item.makDt ? item.makDt : '');

      const txtinfo3 =
        '보관조건 : ' +
        (item.strgTermsNm ? item.strgTermsNm : '') +
        '\n' +
        '안정성검체량 : ' +
        (item.sbtSmpVol ? item.sbtSmpVol : '') +
        ' ' +
        (item.smpVolUnitNm ? item.smpVolUnitNm : '');

      FormUtil.setData(forms, { ...item, txtinfo1, txtinfo2, txtinfo3 });
    },
    setUpdateTypeAndInputReasonModalShowOn(updateType = null) {
      this.inputReasonModal.updateType = updateType;
      this.showModal('inputReasonModal');
    },
    stopRequest({ rjtReaDiv, rjtRea }) {
      // 시험중단 요청
      const { forms } = this.stabInfoRegForm;
      const data = FormUtil.getData(forms);
      const parameter = { ...data, rjtReaDiv, rjtRea };

      this.$eSign(() => this.$axios.put('st/stabPlan/stopRequest', parameter))
        .then(() => {
          this.$info(this.$message.info.stopRequest);
          this.doInit();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    stopCancelRequest({ aprReqUid, aprUid }) {
      // 시험중단취소 요청
      const { forms } = this.stabInfoRegForm;
      const data = FormUtil.getData(forms);
      const parameter = { ...data, aprReqUid, aprUid };

      this.$eSign(() => this.$axios.put('st/stabPlan/stopCancelRequest', parameter))
        .then(() => {
          this.$info(this.$message.info.stopCancelRequest);
          this.doInit();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    approveRequest({ aprReqUid, aprUid }) {
      // 승인요청
      if (!aprReqUid || !aprUid) {
        return;
      }

      const { forms } = this.stabInfoRegForm;
      const data = FormUtil.getData(forms);
      const parameter = { ...data, aprReqUid, aprUid };

      this.$eSign(() => this.$axios.put('st/stabPlan/approveRequest', parameter))
        .then(() => {
          this.$info(this.$message.info.approveRequest);
          this.doInit();
        })
        .catch(() => {
          this.$error(this.$message.error.updateData);
        });
    },
    preAllApprove({ aprReqUid, aprUid }) {
      const updateType = this.requestApproverModal.updateType;

      if (updateType === 'approveRequest') {
        this.approveRequest({ aprReqUid, aprUid });
      } else if (updateType === 'stopCancelRequest') {
        this.stopCancelRequest({ aprReqUid, aprUid });
      }
    },
    save() {
      // 저장
      const stabInfoRegFormForms = this.stabInfoRegForm.forms;
      const stabItemSearchFormForms = this.stabItemSearchForm.forms;

      const stabInfoRegFormData = FormUtil.getData(stabInfoRegFormForms);
      const stabItemSearchFormData = FormUtil.getData(stabItemSearchFormForms);
      const pitmCd = stabItemSearchFormData?.pitmCd;
      const ansIdx = stabItemSearchFormData?.ansIdx;

      const parameter = { ...stabInfoRegFormData, pitmCd, ansIdx };

      this.$eSign(() => this.$axios.post('st/stabPlan', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.doInit();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });
    },

    modalReturnDataEvent({ rjtReaDiv, rjtRea }) {
      const updateType = this.inputReasonModal.updateType;

      if (!updateType || !rjtReaDiv || !rjtRea) {
        return;
      }

      if (updateType == 'update') {
        this.update({ rjtReaDiv, rjtRea });
      } else if (updateType == 'delete') {
        this.delete({ rjtReaDiv, rjtRea });
      } else if (updateType == 'stopRequest') {
        this.stopRequest({ rjtReaDiv, rjtRea });
      } else if (updateType == 'stopCancelRequest') {
        this.requestApproverModal.show = true;
      }
    },

    preUpdate() {
      this.inputReasonModal.updateType = 'update';
      this.showModal('inputReasonModal');
    },
    update({ rjtReaDiv, rjtRea }) {
      // 수정
      const { forms } = this.stabInfoRegForm;
      const stabInfoRegFormData = FormUtil.getData(forms);
      const parameter = { ...stabInfoRegFormData, rjtReaDiv, rjtRea };

      this.$eSign(() => this.$axios.put('st/stabPlan', parameter))
        .then(() => {
          this.$info(this.$message.info.saved);
          this.doInit();
        })
        .catch(() => {
          this.$error(this.$message.error.createData);
        });

      this.resetUpdateType();
    },
    delete({ rjtReaDiv, rjtRea }) {
      // 삭제
      const { forms } = this.stabInfoRegForm;
      const stabInfoRegFormData = FormUtil.getData(forms);
      const parameter = { ...stabInfoRegFormData, rjtReaDiv, rjtRea };

      this.$eSign(() => this.$axios.post('st/stabPlan/delete', parameter))
        .then(() => {
          this.$info(this.$message.info.deleted);
          this.doInit();
        })
        .catch(() => {
          this.$error(this.$message.error.deleteData);
        });
      this.resetUpdateType();
    },

    //위탁품목 - 품목정보등록

    isSaved(sbtAnsProc) {
      return sbtAnsProc === 'S0290100';
    },
    isApproveRequest(sbtAnsProc) {
      return sbtAnsProc === 'S0290200';
    },
    isApproveReject(sbtAnsProc) {
      return sbtAnsProc === 'S0290210';
    },
    isApproved(sbtAnsProc) {
      return sbtAnsProc === 'S0290300';
    },
    isStopRequest(sbtAnsProc) {
      return sbtAnsProc === 'S0290400';
    },
    isStopReject(sbtAnsProc) {
      return sbtAnsProc === 'S0290410';
    },
    isStop(sbtAnsProc) {
      return sbtAnsProc === 'S0290500';
    },
    isStopCancelRequest(sbtAnsProc) {
      return sbtAnsProc === 'S0290600';
    },
    isStopCancelReject(sbtAnsProc) {
      return sbtAnsProc === 'S0290610';
    },
    isStopCancel(sbtAnsProc) {
      return sbtAnsProc === 'S0290700';
    },
    isResultApproved(sbtAnsProc) {
      return sbtAnsProc === 'S0290800';
    },
  },
};
</script>

<style></style>
