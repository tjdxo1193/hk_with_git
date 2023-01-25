export default function (component) {
  if (component.props?.disabled) {
    return {
      ...component,
      emits: [...(component.emits ?? []), 'disabled'],
      watch: {
        ...(component.watch ?? {}),
        disabled(isDisabled) {
          this.$emit('disabled', isDisabled);
        },
      },
    };
  }
  return component;
}
