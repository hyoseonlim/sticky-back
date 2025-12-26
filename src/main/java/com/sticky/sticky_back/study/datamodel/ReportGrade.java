package com.sticky.sticky_back.study.datamodel;

public enum ReportGrade {
    GRADE_1(1),
    GRADE_2(2),
    GRADE_3(3),
    GRADE_4(4),
    GRADE_5(5);

    private final int value;

    ReportGrade(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ReportGrade fromValue(int value) {
        for (ReportGrade grade : ReportGrade.values()) {
            if (grade.value == value) {
                return grade;
            }
        }
        throw new IllegalArgumentException("Invalid ReportGrade value: " + value);
    }
}
