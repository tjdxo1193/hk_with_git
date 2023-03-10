r
<template>
  <ModalBoard
    :show="eSign.show"
    :title="title"
    @ok="onOk"
    @cancel="cancel"
    :okText="okText"
    :top="150"
  >
    <section v-if="isReasonStep" class="e-sign__reason">
      <FormBase v-bind="reason" @enter="onOk" />
    </section>

    <section v-if="isInfoStep" class="e-sign__info">
      <div class="e-sign__info-message">
        {{ $message.eSign.info }}
      </div>
      <div class="e-sign__info-check">
        <Checkbox v-model="state.agree" :label="$message.eSign.agree" :uncheckedValue="false" />
      </div>
      <div>
        <FormBase v-bind="sign" @enter="onOk" />
      </div>
    </section>
  </ModalBoard>
</template>

<script>
import { getterType, mutationType } from '@/const';
import { FormUtil, TokenUtil } from '@/util';

import values from './values/eSign';

export default {
  name: 'ESign',
  data() {
    const { reason, sign } = this.$copy(values);
    return {
      state: {
        agree: false,
      },
      reason: {
        ...reason.static,
        forms: reason.forms(),
      },
      sign: {
        ...sign.static,
        forms: sign.forms(),
      },
      step: this.$props.type,
    };
  },
  methods: {
    init() {
      this.state.agree = false;
      this.reason.forms = values.reason.forms();
      this.sign.forms = values.sign.forms();
      this.$store.commit(mutationType.END_SET_ESIGN_PARAM);
    },
    isAgree() {
      return this.state.agree;
    },
    cancel() {
      this.close();
    },
    close() {
      this.init();
      this.$store.commit(mutationType.HIDE_E_SIGN);
    },
    onOk() {
      if (this.isReasonStep) {
        this.okOnReason();
        return;
      }
      this.okOnInfo();
    },
    okOnReason() {
      if (!FormUtil.existsValue(this.reason.forms, 'reason')) {
        this.$info(this.$message.eSign.pleaseInputReason);
        return;
      }
      this.$store.commit(mutationType.SHOW_E_SIGN, { type: 'info' });
    },
    okOnInfo() {
      if (!this.isAgree()) {
        this.$info(this.$message.eSign.shouldBeAgree);
        return;
      }

      this.sign.forms
        .validate()
        .then(() => (this.isEixstsAsyncFn() ? this.next() : this.close()))
        .catch(() => {});
    },
    next() {
      this.verifyAccount()
        .then(async () => {
          await this.nextRequest();
          await this.close();
        })
        .catch((error) => console.error(error));
    },
    async verifyAccount() {
      try {
        const param = FormUtil.getData(this.sign.forms);

        const isNotEqualsLoginId = TokenUtil.myLoginId() !== param.username;
        if (isNotEqualsLoginId) {
          throw new Error('not equals input login id and my login id.');
        }

        const { data } = await this.$axios.get('/eSign/verification/account', param);

        if (data.result === false) {
          throw new Error('not found matched account.');
        }
      } catch (e) {
        this.$warn(this.$message.error.notMatchedAccount);
        throw Error(e.message);
      }
    },
    async nextRequest() {
      const param = FormUtil.getData(this.reason.forms);
      this.$store.commit(mutationType.READY_SET_ESIGN_PARAM, param);

      await this.eSign
        .asyncFn()
        .then((response) => {
          this.eSign.resolve && this.eSign.resolve(response);
        })
        .catch((error) => {
          this.eSign.reject && this.eSign.reject(error);
        });
    },
    isEixstsAsyncFn() {
      return this.eSign.asyncFn != null;
    },
  },
  computed: {
    eSign() {
      return this.$store.getters[getterType.E_SIGN];
    },
    title() {
      return this.eSign.title ?? '전자 서명';
    },
    okText() {
      return this.eSign.type === 'info' ? '확인' : '다음';
    },
    isReasonStep() {
      return this.eSign.type === 'reason';
    },
    isInfoStep() {
      return this.eSign.type === 'info';
    },
  },
};
</script>

<style lang="scss" scope>
.e-sign {
  position: fixed;
  min-width: 150px;
  left: 50%;
  top: 30%;
  transform: translate(-50%, 0);

  &__info {
    display: flex;
    flex-direction: column;
    align-items: center;

    &-message {
      border: 1px solid rgb(170, 170, 170);
      padding: 30px;
    }

    &-check {
      padding: 12px 0;
    }
  }
}
</style>
