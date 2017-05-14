package com.efd.striketectablet.mmaGlove;

/**
 * Created by erich on 8/22/2016.
 */

import org.apache.commons.lang3.StringUtils;

/**
 *
 *
 * Professional:    4.3% of body weight (3.87)
 * Amateur:    3% of body weight  (2.7)
 * Novice  (>1yr)    2.30% of body weight (2.07)
 * Beginner    1.7% of body weight (1.53)
 *
 * Female => .9 of percentage.
 *
 * @author erich
 *
 */
public enum SkillLevel {

    PROFESSIONAL(.043),
    AMATEUR(.03),
    NOVICE(.023),
    BEGINNER(.017),
    UKNOWN(1);

    private double multiplier;

    private SkillLevel(double multiplier) {
        this.multiplier = multiplier;

    }

    public double adjust(double weight, double adjustment) {

        return (this.multiplier * adjustment) * weight;
    }

    public double adjust(double weight) {

        return this.adjust(weight, 1);
    }

    public static SkillLevel lookUp(String skillLevelName) {

        SkillLevel[] values = SkillLevel.values();

        for (SkillLevel skillLevel : values) {

            if (StringUtils.equalsIgnoreCase(skillLevel.name(), skillLevelName))
                return skillLevel;
        }

        return SkillLevel.UKNOWN;
    }
}