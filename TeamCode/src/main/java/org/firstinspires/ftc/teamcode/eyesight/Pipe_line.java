package org.firstinspires.ftc.teamcode.eyesight;

import com.acmerobotics.dashboard.config.Config;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
@Config
public class Pipe_line extends OpenCvPipeline {
    Telemetry telemetry;
    private BarcodePosition barcodePosition;
    static double PERCENT_COLOR_THRESHOLD = 0.15;
    Team team;
    Mat mat = new Mat();
    public static int hMin = 27;
    public static int hMax = 43;

    public static int sMin = 90;
    public static int sMax = 200;

    public static int vMin = 100;
    public static int vMax = 255;
    public Pipe_line(Telemetry telemetry) {
        this(telemetry, Team.BLUE);
    }


    public  Pipe_line(Telemetry telemetry, Team team) {
        this.telemetry = telemetry;
        this.team = team;
    }

    public Mat processBLUE(Mat input) {
        Rect MIDDLE_ROI = new Rect(
                new Point(200, 250),
                new Point(539, 470));

        Rect RIGHT_ROI = new Rect(
                new Point(739, 250),
                new Point(1078, 470));


        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        Scalar lowHSV = new Scalar(hMin, sMin, vMin);
        Scalar highHSV = new Scalar(hMax, sMax, vMax);
        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat middle = mat.submat(MIDDLE_ROI);
        Mat right = mat.submat(RIGHT_ROI);

        double middleValue = Core.sumElems(middle).val[0] / MIDDLE_ROI.area() / 255;
        double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;

        middle.release();
        right.release();

        boolean middleBool = middleValue > PERCENT_COLOR_THRESHOLD;
        boolean rightBool = rightValue > PERCENT_COLOR_THRESHOLD;

        telemetry.addData("middle", middleValue);
        telemetry.addData("right", rightValue);

        if (rightBool) {
            barcodePosition = BarcodePosition.RIGHT;
            telemetry.addData("Location", "Right");
        } else if (middleBool) {
            barcodePosition = BarcodePosition.MIDDLE;
            telemetry.addData("Location", "Middle");
        } else {
            barcodePosition = BarcodePosition.LEFT;
            telemetry.addData("Location", "Left");
        }
//        Imgproc.cvtColor( mat, mat, Imgproc.COLOR_GRAY2RGB );

        Scalar elementColor = new Scalar(255, 0, 0);
        Scalar notElement = new Scalar(0, 255, 0);

        Imgproc.rectangle(mat, RIGHT_ROI, barcodePosition == BarcodePosition.RIGHT ? notElement : elementColor);
        Imgproc.rectangle(mat, MIDDLE_ROI, barcodePosition == BarcodePosition.MIDDLE ? notElement : elementColor);
        return mat;
    }

    public Mat processRED(Mat input) {
        Rect LEFT_ROI = new Rect(
                new Point(200, 250),
                new Point(539, 470));

        Rect MIDDLE_ROI = new Rect(
                new Point(739, 250),
                new Point(1078, 470));


        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        Scalar lowHSV = new Scalar(hMin, sMin, vMin);
        Scalar highHSV = new Scalar(hMax, sMax, vMax);
        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat middle = mat.submat(MIDDLE_ROI);
        Mat left = mat.submat(LEFT_ROI);

        double middleValue = Core.sumElems(middle).val[0] / MIDDLE_ROI.area() / 255;
        double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;

        middle.release();
        left.release();

        boolean middleBool = middleValue > PERCENT_COLOR_THRESHOLD;
        boolean leftBool = leftValue > PERCENT_COLOR_THRESHOLD;

        telemetry.addData("middle", middleValue);
        telemetry.addData("right", leftValue);

        if (leftBool) {
            barcodePosition = BarcodePosition.LEFT;
            telemetry.addData("Location", "Left");
        } else if (middleBool) {
            barcodePosition = BarcodePosition.MIDDLE;
            telemetry.addData("Location", "Middle");
        } else {
            barcodePosition = BarcodePosition.RIGHT;
            telemetry.addData("Location", "Right");
        }
//        Imgproc.cvtColor( mat, mat, Imgproc.COLOR_GRAY2RGB );

        Scalar elementColor = new Scalar(255, 0, 0);
        Scalar notElement = new Scalar(0, 255, 0);

        Imgproc.rectangle(mat, LEFT_ROI, barcodePosition == BarcodePosition.LEFT ? notElement : elementColor);
        Imgproc.rectangle(mat, MIDDLE_ROI, barcodePosition == BarcodePosition.MIDDLE ? notElement : elementColor);
        return mat;
    }

    @Override
    public Mat processFrame(Mat input) {

//        Mat elementImage = processFrame( input, "element" );
        Mat duckImage;
        if (team == Team.BLUE)
            duckImage = processBLUE(input);
        else
            duckImage = processRED(input);
        return duckImage;
    }

    public BarcodePosition getBarcodePosition() {
        return barcodePosition;
    }

    public enum BarcodePosition {
        LEFT,
        MIDDLE,
        RIGHT,
    }

    public enum Team {
        RED,
        BLUE
    }
}