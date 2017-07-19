package com.efd.striketectablet.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.efd.striketectablet.DTO.PunchDTO;
import com.efd.striketectablet.DTO.TrainingResultComboDTO;
import com.efd.striketectablet.DTO.responsedto.PunchInfoDTO;
import com.efd.striketectablet.DTO.responsedto.PunchLogPlottingDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.CommonUtils;
import com.efd.striketectablet.utilities.EFDConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.efd.striketectablet.customview.LogsLineView.Point.X_COMPARATOR;

/**
 * Created by Super on 4/19/2017.
 */

public class LogsLineView extends View{
    public static class Point {
        public static final Comparator<Point> X_COMPARATOR = new Comparator<Point>() {
            @Override
            public int compare(Point lhs, Point rhs) {
                return (int) (lhs.x * 1000 - rhs.x * 1000);
            }
        };

        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public Point() {
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    private static final String TAG = LogsLineView.class.getSimpleName();

    private float CURVE_STROKE_WIDTH = 2;

    private Context mContext;

    public enum GRAPH_TYPE  {
        VELOCITY,
        ACCELERATION,
        FORCE
    }

    private int[] graphColors = {
        R.color.graph_first, R.color.graph_second, R.color.graph_third, R.color.graph_forth, R.color.graph_fifth };

    private GRAPH_TYPE graph_type;

    private ArrayList<List<PunchLogPlottingDTO>> punchLogs = new ArrayList<>();
    private ArrayList<PunchInfoDTO> punchInfos = new ArrayList<>();

    private ArrayList<ArrayList<Point>> pointsArrayList = new ArrayList<>();
    private ArrayList<ArrayList<Point>> scaledpointsArrayList = new ArrayList<>();

    private ArrayList<Paint> curvePaints = new ArrayList<>();
    private ArrayList<Paint> curvefillPaints = new ArrayList<>();

    private ArrayList<Path> curvePaths = new ArrayList<>();
    private ArrayList<Path> curvefillPaths = new ArrayList<>();

    private Paint borderPaint = new Paint();
    private Paint labelPaint = new Paint();
    private Rect chartRect = new Rect();

    private int LABEL_HEIGHT ;//= 60;

    private float scaleY = 1;
    private float scaleX = 1;

    private int xdivideNum = 10;
    private int ydivideNum = 5;

    private float maxXAxisValue = 500;
    private float maxYAxisValue = 0;

    private int chartHeight, chartWidth;
    private int realchartHeight, realchatWidth;

    private void init(){
        borderPaint.setColor(mContext.getResources().getColor(R.color.punchdetail_selected));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeCap(Paint.Cap.SQUARE);
        borderPaint.setStrokeWidth(CURVE_STROKE_WIDTH);
        borderPaint.setAntiAlias(true);

        labelPaint.setColor(mContext.getResources().getColor(R.color.punchdetail_selected));

        if (!StatisticUtil.is600Dp()){
            LABEL_HEIGHT = 50;
            labelPaint.setTextSize(20f);
        }else {
            LABEL_HEIGHT = 35;
            labelPaint.setTextSize(13f);
        }

        labelPaint.setAntiAlias(true);
    }


    public void setGraphType(GRAPH_TYPE graphType){
        this.graph_type = graphType;
        changeGraph();
    }

    public void setPunchLogs(ArrayList<List<PunchLogPlottingDTO>> punchLogs, ArrayList<PunchInfoDTO> punchInfoDTOs){
        this.punchLogs = punchLogs;
        this.punchInfos = punchInfoDTOs;

        changeGraph();
    }

    public LogsLineView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public LogsLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public  void changeGraph(){
        curvePaints = new ArrayList<>();
        curvefillPaints = new ArrayList<>();

        maxXAxisValue = 0f;
        maxYAxisValue = 0f;

        pointsArrayList = new ArrayList<>();

        for (int i = 0; i < punchLogs.size(); i++){

            ArrayList<Point> points = new ArrayList<>();

            Paint curvepaint = new Paint();
            Paint curvefillpaint = new Paint();

            curvepaint.setStyle(Paint.Style.STROKE);
            curvepaint.setStrokeCap(Paint.Cap.ROUND);
            curvepaint.setStrokeWidth(CURVE_STROKE_WIDTH);
            curvepaint.setAntiAlias(true);

            curvefillpaint.setStyle(Paint.Style.FILL);
            curvefillpaint.setAntiAlias(true);

            if (i < 5){

                curvepaint.setColor(mContext.getResources().getColor(graphColors[i]));
                curvefillpaint.setColor(mContext.getResources().getColor(graphColors[i]));
                curvepaint.setAlpha(255);
                curvefillpaint.setAlpha(50);
            }else {
                curvepaint.setColor(mContext.getResources().getColor(graphColors[0]));
                curvefillpaint.setColor(mContext.getResources().getColor(graphColors[0]));
                curvepaint.setAlpha(255);
                curvefillpaint.setAlpha(50);
            }

            curvePaints.add(curvepaint);
            curvefillPaints.add(curvefillpaint);

            if (graph_type == GRAPH_TYPE.VELOCITY){
                Collections.sort(punchLogs.get(i), LOGSVELOCITY_COMPARATOR);
                maxYAxisValue = Math.max(maxYAxisValue, Float.parseFloat(punchLogs.get(i).get(punchLogs.get(i).size() - 1).getVelocity()));

                for (int j = 0; j < punchLogs.get(i).size(); j++){
                    points.add(new Point(Float.parseFloat(punchLogs.get(i).get(j).getTime()), Float.parseFloat(punchLogs.get(i).get(j).getVelocity())));
                }

            }else if (graph_type == GRAPH_TYPE.ACCELERATION){
                Collections.sort(punchLogs.get(i), LOGSACCELERATION_COMPARATOR);
                maxYAxisValue = Math.max(maxYAxisValue, Float.parseFloat(punchLogs.get(i).get(punchLogs.get(i).size() - 1).getAcceleration()));

                for (int j = 0; j < punchLogs.get(i).size(); j++){
                    points.add(new Point(Float.parseFloat(punchLogs.get(i).get(j).getTime()), Float.parseFloat(punchLogs.get(i).get(j).getAcceleration())));
                }
            }else {
                Collections.sort(punchLogs.get(i), LOGSFORCE_COMPARATOR);
                maxYAxisValue = Math.max(maxYAxisValue, Float.parseFloat(punchLogs.get(i).get(punchLogs.get(i).size() - 1).getImpactMask()));

                for (int j = 0; j < punchLogs.get(i).size(); j++){
                    points.add(new Point(Float.parseFloat(punchLogs.get(i).get(j).getTime()), Float.parseFloat(punchLogs.get(i).get(j).getImpactMask())));
                }
            }

            Collections.sort(punchLogs.get(i), LOGSTIME_COMPARATOR);

            maxXAxisValue = Math.max(maxXAxisValue, Float.parseFloat(punchLogs.get(i).get(punchLogs.get(i).size() - 1).getTime()));

            pointsArrayList.add(points);
        }

        //get scale x
//        this.scaleX = chartWidth / maxXAxisValue;
        this.scaleX = realchatWidth / maxXAxisValue;
        maxYAxisValue = maxYAxisValue * 3 /2.5f;

        this.scaleY = realchartHeight / ( maxYAxisValue);
//        this.scaleY = chartHeight / ( maxYAxisValue);

        Log.e("Super", "max xaxis = " + maxXAxisValue + "     " + maxYAxisValue);

        //get scaled points

        scaledpointsArrayList = new ArrayList<>();

        for (int i = 0; i < pointsArrayList.size(); i++){
            ArrayList<Point> newScaled = new ArrayList<>();
            ArrayList<Point> pointsArray = pointsArrayList.get(i);

            for (int j = 0; j < pointsArray.size(); j++){
                Point p = pointsArray.get(j);
                Point newPoint = new Point();

                newPoint.x = chartRect.left + scaleX * p.x + LABEL_HEIGHT;
                newPoint.y = realchartHeight - scaleY * p.y;

                newScaled.add(newPoint);
            }

            Collections.sort(newScaled, X_COMPARATOR);

            scaledpointsArrayList.add(newScaled);
        }

        invalidate();
    }

    private void buildPath(ArrayList<Path> paths) {

        for (int i = 0; i < paths.size(); i++){
            paths.get(i).reset();

            ArrayList<Point> scaledPoints = scaledpointsArrayList.get(i);

            if (scaledPoints.size() > 1) {
                paths.get(i).moveTo(scaledPoints.get(0).x, scaledPoints.get(0).y);

                for (int j = 1; j < scaledPoints.size(); j++) {
                    paths.get(i).lineTo(scaledPoints.get(j).x, scaledPoints.get(j).y);
                }
            }
        }
    }


    private void drawCurve(Canvas canvas){

        curvePaths.clear();
        curvefillPaths.clear();

        for (int i = 0; i < punchLogs.size(); i++){
            Path curvePath = new Path();
            Path fillPath = new Path();

            curvePaths.add(curvePath);
            curvefillPaths.add(fillPath);
        }

        buildPath(curvePaths);
        buildPath(curvefillPaths);

        for (int i = 0; i < punchLogs.size(); i++){
            int pointSize = scaledpointsArrayList.get(i).size();

            if (pointSize > 1) {
                curvefillPaths.get(i).lineTo(scaledpointsArrayList.get(i).get(pointSize - 1).x, chartRect.bottom - LABEL_HEIGHT);
                curvefillPaths.get(i).lineTo(scaledpointsArrayList.get(i).get(0).x, chartRect.bottom - LABEL_HEIGHT);
                curvefillPaths.get(i).close();
            }

            canvas.drawPath(curvefillPaths.get(i), curvefillPaints.get(i));
            canvas.drawPath(curvePaths.get(i), curvePaints.get(i));
        }
    }

    private void drawGrid(Canvas canvas, int width) {

        int gridCount = EFDConstants.GRID_COUNT - 1;
        float part = (float) width / gridCount;

        for (int i = 1; i < gridCount; i++) {
            float x = chartRect.left + part * i;
            canvas.drawLine(x, chartRect.top, x, chartRect.bottom, borderPaint);
        }
    }

    private void drawLabels(Canvas canvas, int width, int height){

        int intervalX = (int)(maxXAxisValue / xdivideNum);
        int intervalY = (int)(maxYAxisValue / ydivideNum);

        if (intervalX == 0)
            intervalX = 20;
        if (intervalY == 0)
            intervalY = 20;

        //draw xlabel
        int xcount = 1;
        while (intervalX * xcount < maxXAxisValue){
            String text = String.valueOf(intervalX * xcount);

            float textWidth = getTextWidth(labelPaint, text);
            float textHeight = getTextHeight(labelPaint);

            int right = (int)(chartRect.left + scaleX * intervalX * xcount + LABEL_HEIGHT);

            if (!StatisticUtil.is600Dp())
                canvas.drawText(text, (int)(right - textWidth), chartRect.bottom - LABEL_HEIGHT + textHeight + 3, labelPaint);
            else
                canvas.drawText(text, (int)(right - textWidth), chartRect.bottom - LABEL_HEIGHT + textHeight + 1, labelPaint);
            xcount ++;
        }

        //draw last label "milliseconds"
        String millitext = "MILLISECONDS";
        float millitextWidth = getTextWidth(labelPaint, millitext);
        float millitextHeight = getTextHeight(labelPaint);
        int centerX = LABEL_HEIGHT + realchatWidth / 2;

        if (StatisticUtil.is600Dp())
            canvas.drawText(millitext,  (int)(centerX - millitextWidth / 2), chartRect.bottom - LABEL_HEIGHT + 2 * millitextHeight + 3, labelPaint);
        else
            canvas.drawText(millitext,  (int)(centerX - millitextWidth / 2), chartRect.bottom - LABEL_HEIGHT + 2 * millitextHeight + 3, labelPaint);

        //draw xlabel
        int ycount = 1;
        while (intervalY * ycount < maxYAxisValue){
            String text = String.valueOf(intervalY * ycount);

            float textWidth = getTextWidth(labelPaint, text);
            float textHeight = getTextHeight(labelPaint);

            int startY = (int)(chartRect.bottom - LABEL_HEIGHT - scaleY * intervalY * ycount + 10);

            if (!StatisticUtil.is600Dp())
                canvas.drawText(text, (int)(LABEL_HEIGHT - textWidth - 5), startY, labelPaint);
            else
                canvas.drawText(text, (int)(LABEL_HEIGHT - textWidth - 3), startY, labelPaint);

            ycount ++;
        }

        //draw label 0
        float orignwidth = getTextWidth(labelPaint, "0");
        float orignheight = getTextHeight(labelPaint);
        canvas.drawText("0", (int)(LABEL_HEIGHT - orignwidth - 5),chartRect.bottom - LABEL_HEIGHT + orignheight + 3 , labelPaint);

        //draw rotated text
        String rotatedText = "";
        if (graph_type == GRAPH_TYPE.ACCELERATION){
            rotatedText = "ACCELERATION";
        }else if (graph_type == GRAPH_TYPE.VELOCITY){
            rotatedText = "VELOCITY";
        }else {
            rotatedText = "FORCE";
        }

        canvas.save();
        canvas.rotate(-90, realchartHeight / 2, realchartHeight / 2);

        float rotatedtextWidth = getTextWidth(labelPaint, rotatedText);
        float rotatedtextHeight = getTextHeight(labelPaint);

        canvas.drawText(rotatedText,  (int)(realchartHeight / 2 - rotatedtextWidth / 2), chartRect.top + rotatedtextHeight - 5, labelPaint);
        canvas.restore();
    }

    public float getTextHeight(Paint textPaint) {
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent) - 2;
    }

    public float getTextWidth(Paint textPaint, String text) {
        return textPaint.measureText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getDrawingRect(chartRect);

        if (punchLogs != null){
            chartHeight = chartRect.bottom - chartRect.top;
            chartWidth = chartRect.right - chartRect.left;
            realchatWidth = chartWidth - LABEL_HEIGHT;
            realchartHeight = chartHeight - LABEL_HEIGHT;
            drawCurve(canvas);

            canvas.drawLine(LABEL_HEIGHT, chartRect.bottom - LABEL_HEIGHT, chartRect.right, chartRect.bottom - LABEL_HEIGHT, borderPaint);
            canvas.drawLine(LABEL_HEIGHT, chartRect.bottom - LABEL_HEIGHT, LABEL_HEIGHT, chartRect.top, borderPaint);

            drawLabels(canvas, realchatWidth, realchartHeight);
        }
    }

    public ArrayList<List<PunchLogPlottingDTO>> getPunchLogs(){
        return punchLogs;
    }

    public ArrayList<PunchInfoDTO> getPunchInfos(){
        return punchInfos;
    }

    private Comparator<PunchLogPlottingDTO> LOGSTIME_COMPARATOR = new Comparator<PunchLogPlottingDTO>() {
        @Override
        public int compare(PunchLogPlottingDTO lhs, PunchLogPlottingDTO rhs) {
            return (int) (Float.parseFloat(lhs.getTime()) * 1000 - Float.parseFloat(rhs.getTime())* 1000);
        }
    };

    private Comparator<PunchLogPlottingDTO> LOGSVELOCITY_COMPARATOR = new Comparator<PunchLogPlottingDTO>() {
        @Override
        public int compare(PunchLogPlottingDTO lhs, PunchLogPlottingDTO rhs) {
            return (int) (Float.parseFloat(lhs.getVelocity()) * 1000 - Float.parseFloat(rhs.getVelocity())* 1000);
        }
    };

    private Comparator<PunchLogPlottingDTO> LOGSACCELERATION_COMPARATOR = new Comparator<PunchLogPlottingDTO>() {
        @Override
        public int compare(PunchLogPlottingDTO lhs, PunchLogPlottingDTO rhs) {
            return (int) (Float.parseFloat(lhs.getAcceleration()) * 1000 - Float.parseFloat(rhs.getAcceleration())* 1000);
        }
    };

    private Comparator<PunchLogPlottingDTO> LOGSFORCE_COMPARATOR = new Comparator<PunchLogPlottingDTO>() {
        @Override
        public int compare(PunchLogPlottingDTO lhs, PunchLogPlottingDTO rhs) {
            return (int) (Float.parseFloat(lhs.getImpactMask()) * 1000 - Float.parseFloat(rhs.getImpactMask())* 1000);
        }
    };
}

