<template>
  <div class="form-element" :style="styleVariable">
    <template v-if="item._slot">
      <slot v-bind="item" :$$update="updateValue" :$$updateAt="updateValueAt"></slot>
    </template>

    <template v-else-if="item.is">
      <component
        v-bind="{ ...eventListener, ...item }"
        :is="item.is"
        v-model="item.value"
        @input="emitEvent($event, item)"
        @change="emitEvent($event, item)"
        @focus="emitEvent($event, item)"
        @blur="emitEvent($event, item)"
        @click="emitEvent($event, item)"
        @keydown="onKeyDown($event, item)"
        @disabled="onDisabled($event, item)"
        @valid="onValidEvent(item, false)"
        @invalid="onValidEvent(item, true)"
        @loaded="onLoadedOptions($event, item)"
        @initValidate="onInitValidate(item)"
      ></component>
    </template>

    <template v-else>
      {{ item.value }}
    </template>

    <span v-if="item.suffix" class="form-element__suffix">
      {{ item.suffix }}
    </span>
  </div>
</template>

<script>
import { ComponentUtil } from '@/util';

export default {
  name: 'FormElement',
  emits: ['form-event', 'enter'],
  inheritAttrs: false,
  props: {
    item: {
      type: Object,
      default: () => ({}),
      required: true,
      /**
       ** Form Item에는 폼의 다양한 형태를 제어하기 위한 Custom property가 존재합니다.
       **/
      meta: `{
        name: String,       //* form element name
        itemLabel: String,  //* form item label

        _colSpan: Number,
        _rowSpan: Number,
        _blank: Boolean,    //* 빈칸으로 노출할 지 구분합니다.
        _hidden: Boolean,   //* hidden 컬럼인 경우, 렌더링하지 않습니다.
        _slot: Boolean,     //* slot-name이 form-[name]에 해당하는 슬롯으로 렌더링합니다.
        _multi: Boolean,    //* 하나의 Form Item에 여러개의 요소를 사용하는지 여부.
        _multiForms: Array, //* forms객체. _multi가 true인 경우, 이 배열을 사용하여 Form요소를 생성합니다.
        _invalid: Boolean   //* 유효성 검증이 실패할 때 true값이 할당되며, forms의 요소 중 하나라도 true인 경우 forms.validate메서드가 false를 반환합니다.

        ...                 //* 각 컴포넌트의 props
      }`,
    },
    span: {
      type: Number,
      default: 1,
    },
    hide: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      eventListener: ComponentUtil.getEventListener(this.$attrs),
      styleVariable: {
        '--flex': this.$props.span,
        '--display': this.$props.hide ? 'none' : 'flex',
      },
    };
  },
  methods: {
    /**
     ** slot 태그는 v-model 및 v-model과 관련된 이벤트를 수신할 수 없습니다.
     ** 따라서 외부에서 주입된 동적 컴포넌트의 값을 수정하는 메서드를 scopedSlot을 통해 전달합니다.
     ** 보통의 경우, 슬롯으로 컴포넌트를 주입할 때 그 컴포넌트에 v-model을 사용하면 됩니다.
     ** 이 메서드는 Form 내부에서 사용할 때와 같이 일괄적인 형태로 데이터를 핸들링해야하는 경우에 사용합니다.
     */
    updateValue(value) {
      this.$props.item.value = value;
    },
    updateValueAt(key, value) {
      this.$props.item[key] = value;
    },
    emitEvent(event, item) {
      this.$emit('form-event', { type: event.type, originEvent: event, item });
    },
    onValidEvent(item, isInvalid) {
      item._invalid = isInvalid;
    },
    onKeyDown(event, item) {
      const isNotTextarea = item.is !== 'TextareaBase';

      if (isNotTextarea && event.key === 'Enter') {
        this.$emit('enter', { type: event.type, originEvent: event, item });
        /**
         ** Enter를 빠르게 여러번 입력했을 때,
         ** 조회 및 저장이 연이어 일어나는 것을 방지하기 위함
         */
        event.target.blur();
      }
      this.emitEvent(event, item);
    },
    onLoadedOptions({ async, data }, item) {
      if (async) {
        item.elements = data;
      }
    },
    onDisabled(isDisabled, item) {
      item.disabled = isDisabled;
    },
    onInitValidate(item) {
      item._initValidate = false;
    },
  },
};
</script>

<style lang="scss" scope>
.form-element {
  position: relative;
  display: var(--display);
  align-items: center;
  flex: var(--flex);
  width: 100%;
  word-break: break-all;
  min-height: 37px;
  padding: 5px;

  &__suffix {
    position: relative;
    display: flex;
    padding: 0 5px;
  }
}
</style>
