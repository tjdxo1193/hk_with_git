<template>
  <ModalBase :title="title" :show="show" :top="50" :parameter="parameter" @close="close">
    <template #action>
      <ActionBar :buttons="buttonGroups.buttons" @button-click="onClickButton"></ActionBar>
    </template>

    <FormBase v-bind="list" />
  </ModalBase>
</template>

<script>
import dayjs from 'dayjs';

import { FormUtil } from '@/util';

import values from './values/testLabEventModal';

export default {
  name: 'TestLabEventModal',
  emits: ['close', 'modalReturnDataEvent'],
  props: {
    title: {
      type: String,
      default: '실험실이벤트발생',
    },
    show: {
      type: Boolean,
      default: false,
    },
    width: {
      type: String,
      default: '600px',
    },
    height: {
      type: String,
      default: '400px',
    },
    parameter: {},
  },
  mounted() {
    this.ocrDt = dayjs().format('YYYY-MM-DD');
  },
  data() {
    const { list, buttonGroups } = this.$copy(values);
    return {
      list: {
        ...list.static,
        forms: list.forms(),
      },
      buttonGroups: {
        buttons: buttonGroups.buttons,
      },
      ocrDt: null,
    };
  },
  watch: {
    show() {
      if (this.$props.show) {
        const ocrDt = this.ocrDt;
        FormUtil.setData(this.list.forms, this.$props.parameter);
        FormUtil.setData(this.list.forms, { ocrDt });
      }
    },
  },
  methods: {
    onClickButton({ name }) {
      if (name === 'save') {
        this.dataCheckBeforeSave();
      }
      if (name === 'cancel') {
        this.close();
      }
    },
    dataCheckBeforeSave() {
      return this.list.forms.validate().then(() => {
        this.publish();
      });
    },
    publish() {
      const parameter = FormUtil.getData(this.list.forms);
      const ansIdx = FormUtil.getValue(this.list.forms, 'ansIdx');
      this.$confirm(this.$message.confirm.labEventPublished).then(() => {
        this.$eSign(() => this.$axios.put('/ts/testResultInput/publishEvent', parameter))
          .then(() => {
            this.$info(this.$message.info.saved);
            this.$emit('modalReturnDataEvent', ansIdx);
            this.close();
          })
          .catch(() => {
            this.$error(this.$message.error.createData);
          });
      });
    },
    close() {
      FormUtil.setData(this.list.forms, { crtvMsr: '' });
      this.$emit('close');
    },
  },
};
</script>
