package lims.api.common.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComboVO {
    private String label;
    private String value;

    public static ComboVO ofValue(String value) {
        ComboVO combo = new ComboVO();
        combo.setLabel(value);
        combo.setValue(value);
        return combo;
    }
}