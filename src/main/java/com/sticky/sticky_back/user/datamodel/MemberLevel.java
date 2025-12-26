package com.sticky.sticky_back.user.datamodel;

public enum MemberLevel {
    LEVEL_1(1),
    LEVEL_2(2),
    LEVEL_3(3),
    LEVEL_4(4),
    LEVEL_5(5);

    private final int value;

    MemberLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MemberLevel fromValue(int value) {
        for (MemberLevel level : MemberLevel.values()) {
            if (level.value == value) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid MemberLevel value: " + value);
    }
}
