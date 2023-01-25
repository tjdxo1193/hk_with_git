package lims.api.ts.enums;

import lombok.Getter;

@Getter
public enum TestSample {
    TEST_SAMPLE("S0260001"),
    ETC_SAMPLE("S0260002"),
    STORAGE_SAMPLE("S0260003"),
    ADD_SAMPLE("S0260004");

    private final String sampleCode;

    TestSample(String code) {
        sampleCode = code;
    }

    public boolean equals(TestSample sample) {
        return this == sample;
    }
}
