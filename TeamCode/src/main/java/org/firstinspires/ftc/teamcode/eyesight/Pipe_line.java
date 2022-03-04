package org.firstinspires.ftc.teamcode.eyesight;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Pipe_line extends OpenCvPipeline {
    Telemetry telemetry;
    private BarcodePosition barcodePosition;
    static double PERCENT_COLOR_THRESHOLD = 0.02;
    Team team;
    Mat mat = new Mat();

    public Pipe_line(Telemetry telemetry) {
        this(telemetry, Team.BLUE);
    }


    public  Pipe_line(Telemetry telemetry, Team team) {
        this.telemetry = telemetry;
        this.team = team;
    }

    public Mat processBLUE(Mat input) {
        Rect MIDDLE_ROI = new Rect(
                new Point(426, 0),
                new Point(852, 720));

        Rect RIGHT_ROI = new Rect(
                new Point(852, 0),
                new Point(1278, 720));

        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        Scalar lowHSV = new Scalar(25, 25, 35);
        Scalar highHSV = new Scalar(40, 255, 255);
        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat middle = mat.submat(MIDDLE_ROI);
        Mat right = mat.submat(RIGHT_ROI);

        double middleValue = Core.sumElems(middle).val[0] / MIDDLE_ROI.area() / 255;
        double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;

        middle.release();
        right.release();

        boolean middleBool = middleValue > PERCENT_COLOR_THRESHOLD;
        boolean rightBool = rightValue > 0.005;

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
                new Point(0, 0),
                new Point(852, 720));
        Rect MIDDLE_ROI = new Rect(
                new Point(852, 0),
                new Point(1278, 720));

        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        Scalar lowHSV = new Scalar(25, 25, 35);
        Scalar highHSV = new Scalar(40, 255, 255);

        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat left = mat.submat(LEFT_ROI);
        Mat middle = mat.submat(MIDDLE_ROI);

        double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;
        double middleValue = Core.sumElems(middle).val[0] / MIDDLE_ROI.area() / 255;

        left.release();
        middle.release();

        boolean leftBool = leftValue > 0.005;
        boolean middleBool = middleValue > PERCENT_COLOR_THRESHOLD;

        telemetry.addData("left", leftValue);
        telemetry.addData("middle", middleValue);

        if (middleBool) {
            barcodePosition = BarcodePosition.MIDDLE;
            telemetry.addData("Location", "Middle");
        } else if (leftBool) {
            barcodePosition = BarcodePosition.LEFT;
            telemetry.addData("Location", "Left");
        } else {
            barcodePosition = BarcodePosition.RIGHT;
            telemetry.addData("Location", "Right");
        }

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