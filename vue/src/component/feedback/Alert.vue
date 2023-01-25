<template>
  <div :class="['modalWrap', alert.show && 'show']">
    <div :class="['modalBase', alert.type]">
      <div>
        <div class="subject">
          <span class="titleIcon">
            <img />
          </span>
          {{ alert.message }}
        </div>

        <div class="modalBtn">
          <ButtonBase v-if="useCancelButton" type="normal" @click="cancel">취소</ButtonBase>
          <ButtonBase @click="ok">확인</ButtonBase>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getterType, mutationType } from '@/const';
import { CommonUtil } from '@/util';

export default {
  name: 'Alert',
  mounted() {
    window.document.addEventListener('keydown', this.onCloseListener, { capture: true });
  },
  beforeUpdate() {
    CommonUtil.clearFocus();
  },
  beforeUnmount() {
    window.document.removeEventListener('keydown', this.onCloseListener);
  },
  methods: {
    cancel() {
      this.close();
    },
    close() {
      this.$store.commit(mutationType.HIDE_ALERT);
    },
    ok() {
      this.isPromise() ? this.next() : this.close();
    },
    next() {
      this.alert.resolve && this.alert.resolve();
      this.close();
    },
    isPromise() {
      return !!this.alert.resolve;
    },
    isConfirm() {
      return this.alert.type === 'confirm';
    },
    isAlert() {
      return this.alert.type !== 'confirm';
    },
    onCloseListener({ key }) {
      if (this.isHide()) {
        return;
      }

      if (this.isEnter(key)) {
        this.ok();
        return;
      }

      if (this.isEsc(key) && this.isAlert()) {
        this.ok();
        return;
      }

      if (this.isEsc(key)) {
        this.cancel();
        return;
      }
    },
    isEnter(key) {
      return key === 'Enter';
    },
    isEsc(key) {
      return key === 'Escape';
    },
    isHide() {
      return !this.alert.show;
    },
  },
  computed: {
    alert() {
      return this.$store.getters[getterType.ALERT];
    },
    useCancelButton() {
      return this.isConfirm();
    },
  },
};
</script>

<style lang="scss" scope>
.modalWrap {
  z-index: -1;
  line-height: normal;

  &.show {
    z-index: 2001;
    opacity: 1;
    transition: opacity 0.2s;
    display: block;
  }

  & .modalBase {
    & img {
      width: 16px;
      height: 16px;
      padding: 8px;
    }

    &.info {
      box-shadow: 0 0 5px 0 rgba(39, 110, 241, 0.9);

      & .titleIcon img {
        background: url('@/assets/images/check.png') no-repeat;
      }
    }

    &.warn {
      box-shadow: 0 0 3px 0 rgba(255, 72, 0, 1);
      border: 1px solid rgba(255, 72, 0, 0.5);

      & .titleIcon img {
        background: url('@/assets/images/warning.png') no-repeat;
      }
    }

    &.error {
      box-shadow: 0 0 3px 0 rgba(255, 42, 0, 1);
      border: 1px solid rgba(255, 42, 0, 0.5);

      & .titleIcon img {
        background: url('@/assets/images/error.png') no-repeat;
      }
    }
  }
}
</style>
