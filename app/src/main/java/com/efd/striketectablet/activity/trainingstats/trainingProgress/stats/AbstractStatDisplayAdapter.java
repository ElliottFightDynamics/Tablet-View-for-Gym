package com.efd.striketectablet.activity.trainingstats.trainingProgress.stats;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.efd.striketectablet.activity.trainingstats.charts.stats.IBarchartValue;
import com.efd.striketectablet.activity.trainingstats.charts.stats.StatType;

/**
 * Created by omnic on 8/27/2016.
 */
public abstract class AbstractStatDisplayAdapter implements ITrainingStatDisplayAdapter {

    private View view;

    public AbstractStatDisplayAdapter(View view) {
        this.view = view;
    }

    protected abstract int getTextViewId();

    protected abstract StatType getStatType();

    protected abstract String formatStatValue(float statValue);

    protected abstract int getButtonPressedImage();

    protected abstract int getButtonUnpressedImage();

    protected abstract int getButtonId();

    @Override
    public final void setSelected(StatType currentlySelectedStat) {
        if (currentlySelectedStat.equals(getStatType())) {
            setButtonSelected();
            Log.e("Super", "button selected " + getStatType());
        } else {
            setButtonUnselected();
            Log.e("Super", "button unselected " + getStatType());
        }
    }

    @Override
    public void setValues(IBarchartValue barchartData) {
        float statValue = barchartData.getValue(getStatType());
        TextView textView = getTextDisplay(getTextViewId());
        textView.setText(formatStatValue(statValue));

    }

    private void setButtonSelected() {
        setImage(getButtonId(), getButtonPressedImage());
    }

    private void setButtonUnselected() {
        setImage(getButtonId(), getButtonUnpressedImage());
    }

    private TextView getTextDisplay(int viewId) {
        return (TextView) this.view.findViewById(viewId);
    }

    private void setImage(int elementId, int imageId) {
        ImageView imageView = (ImageView) this.view.findViewById(elementId);
        Drawable drawable = this.view.getResources().getDrawable(imageId);
        imageView.setImageDrawable(drawable);
    }
}
