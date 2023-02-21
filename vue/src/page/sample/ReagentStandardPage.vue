<template>
  <AUIGridSearch
    v-bind="{ ...reagentStandardGrid }"
    @button-click="onClickButton"
    @grid-created="(proxy) => $setState('reagentStandardGrid.$grid', proxy)"
    @form-event="onFormEvent"
  />

  <FormWithHeader v-bind="{ ...reagentStandardForm }" @form-event="onFormEvent"> </FormWithHeader>

  <ActionBar :buttons="mainButtons.buttons" space="none" @button-click="onClickButton"> </ActionBar>
</template>

<script>
import { FormUtil } from '@/util';
import objectUtil from '@/util/objectUtil';

import values from './values/reagentStandard';

const { reagentStandardGrid, reagentStandardForm, mainButtons } = objectUtil.copy(values);

export default {
  name: 'ReagentStandard',
  mounted() {
    this.getReagentStandard();
  },
  data() {
    return {
      reagentStandardGrid: {
        ...reagentStandardGrid.static,
        forms: reagentStandardGrid.forms(),
        columns: reagentStandardGrid.columns(),
        event: {
          cellDoubleClick: (e) => this.getReagentStandardByReagentId(e),
        },
      },
      reagentStandardForm: {
        ...reagentStandardForm.static,
        forms: reagentStandardForm.forms(),
      },
      mainButtons: {
        buttons: mainButtons,
      },
    };
  },
  methods: {
    async getReagentStandard() {
      const { $grid, forms } = this.reagentStandardGrid;
      const parameter = FormUtil.getData(forms);

      await $grid
        ._useLoader(() => this.$axios.get('/fake/products', parameter))
        .then(({ data }) => data);
    },
    getReagentStandardByReagentId(event) {
      const selectedItem = event.item;
      FormUtil.setData(this.reagentStandardForm.forms, selectedItem);

      this.activeButtons();
    },
    onClickButton({ name }) {
      if (name === 'select') {
        this.getReagentStandard();
        this.init();
        this.inActiveButtons();
      }
      if (name === 'save') {
        this.save();
      }
      if (name === 'update') {
        this.update();
      }
      if (name === 'delete') {
        this.delete();
      }
      if (name === 'init') {
        this.init();
        this.inActiveButtons();
      }
    },
    save() {
      alert('등록');
    },
    update() {
      alert('수정');
    },
    delete() {
      alert('삭제');
    },
    init() {
      const forms = reagentStandardForm.forms();
      this.$setState('reagentStandardForm', { forms });
    },
    activeButtons() {
      const buttons = mainButtons.map((item) => ({
        ...item,
        type: 'default',
      }));

      this.$setState('mainButtons', { buttons });
    },
    inActiveButtons() {
      const buttons = mainButtons.map((item) => ({
        ...item,
      }));

      this.$setState('mainButtons', { buttons });
    },
    onFormEvent(e) {
      // TODO 구분 name 값으로 변경하기
      if (e.type === 'change' && (e.item.name === 'title' || e.item.name === 'div')) {
        alert('재료구분 selectBox 변경');

        reagentStandardForm
          .forms()
          .filter((item) => item.name === 'price' || item.name === 'div')
          .map((item) => ({
            ...item,
            // TODO 변경할 SELECTBOX 값
          }));
      }
    },
  },
};
</script>
