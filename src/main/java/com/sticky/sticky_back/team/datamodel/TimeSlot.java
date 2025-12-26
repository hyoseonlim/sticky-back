package com.sticky.sticky_back.team.datamodel;

public enum TimeSlot {
    SLOT_00_00(0),
    SLOT_01_00(1),
    SLOT_02_00(2),
    SLOT_03_00(3),
    SLOT_04_00(4),
    SLOT_05_00(5),
    SLOT_06_00(6),
    SLOT_07_00(7),
    SLOT_08_00(8),
    SLOT_09_00(9),
    SLOT_10_00(10),
    SLOT_11_00(11),
    SLOT_12_00(12),
    SLOT_13_00(13),
    SLOT_14_00(14),
    SLOT_15_00(15),
    SLOT_16_00(16),
    SLOT_17_00(17),
    SLOT_18_00(18),
    SLOT_19_00(19),
    SLOT_20_00(20),
    SLOT_21_00(21),
    SLOT_22_00(22),
    SLOT_23_00(23);

    private final int hour;

    TimeSlot(int hour) {
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public static TimeSlot fromHour(int hour) {
        for (TimeSlot slot : TimeSlot.values()) {
            if (slot.hour == hour) {
                return slot;
            }
        }
        throw new IllegalArgumentException("Invalid hour: " + hour);
    }
}
