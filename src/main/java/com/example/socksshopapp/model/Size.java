package com.example.socksshopapp.model;

public enum Size {
    SIZE_35_5(35.5),
    SIZE_36(36),
    SIZE_36_5(36.5),
    SIZE_37(37),
    SIZE_37_5(37.5),
    SIZE_38(38),
    SIZE_38_5(38.5),
    SIZE_39(39);

    private final double size;

    Size(final double newSize) {
        size = newSize;
    }

    public double getSize() {
        return size;
    }
}
