package com.ise.project.sentiAnalysis;

/**
 * Created by India on 09-May-16.
 */
public enum EnumResult {
    NEGATIVE(-1), NEUTRAL(0), POSITIVE(1);

    private EnumResult value;
    EnumResult(int value) {
        this.value = this;
        
    }
}
