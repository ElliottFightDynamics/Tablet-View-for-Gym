package com.efd.striketectablet.mmaGlove;

/**
 * Created by erich on 8/22/2016.
 */

import android.util.Log;

import com.efd.striketectablet.utilities.EFDConstants;

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
public class EffectivePunchMassCalculator {

    private final double genderAdjustment = .9;

    public double calculatePunchMassEffect(String weight, String skillLevel, String gender) {

        try {
            double percentageTweak = 1;
            SkillLevel lookUp = SkillLevel.lookUp(skillLevel);

            if (StringUtils.isNotBlank(gender) && StringUtils.startsWith(gender.toLowerCase(), "f"))
                percentageTweak = genderAdjustment;

            double parsedWeight = Double.parseDouble(weight);

            return lookUp.adjust(parsedWeight, percentageTweak);
        } catch (Exception e) {
            Log.d("EffPunchMassCalc", "Error calculating: " + e.getMessage());
            return EFDConstants.GUEST_TRAINING_EFFECTIVE_PUNCH_MASS;
        }
    }
}
