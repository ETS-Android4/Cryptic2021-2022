package org.firstinspires.ftc.teamcode.eyesight;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Pipe_line extends OpenCvPipeline {

    Telemetry telemetry;
    Mat mat = new Mat( );

    public enum BarcodePosition {
        LEFT,
        MIDDLE,
        RIGHT,
    }

    private BarcodePosition barcodePosition;

    static final Rect MIDDLE_ROI = new Rect(
            new Point( 426, 0 ),
            new Point( 852, 720 ) );
    static final Rect RIGHT_ROI = new Rect(
            new Point( 852, 0 ),
            new Point( 1278, 720 ) );

    static double PERCENT_COLOR_THRESHOLD = 0.02;

    public Pipe_line( Telemetry t ) {
        telemetry = t;
    }

    public Mat processFrame( Mat input, String type ) {
        Imgproc.cvtColor( input, mat, Imgproc.COLOR_RGB2HSV );
        Scalar lowHSV;
        Scalar highHSV;

        if( type.equalsIgnoreCase( "duck" ) ) {
            lowHSV = new Scalar( 25, 25, 35 );
            highHSV = new Scalar( 40, 255, 255 );
        } else {
            lowHSV = new Scalar( 40, 50, 70 );
            highHSV = new Scalar( 65, 255, 255 );
        }
        Core.inRange( mat, lowHSV, highHSV, mat );

        Mat middle = mat.submat( MIDDLE_ROI );
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
            telemetry.addData("Location", type + " right");
        } else if (middleBool) {
            barcodePosition = BarcodePosition.MIDDLE;
            telemetry.addData("Location", type + " middle");
        } else {
            barcodePosition = BarcodePosition.LEFT;
            telemetry.addData("Location", type + " left");
        }
        Imgproc.cvtColor( mat, mat, Imgproc.COLOR_GRAY2RGB );

        Scalar elementColor = new Scalar( 255, 0, 0 );
        Scalar notElement = new Scalar( 0, 255, 0 );

        Imgproc.rectangle( mat, RIGHT_ROI, barcodePosition == BarcodePosition.RIGHT ? notElement : elementColor );
        Imgproc.rectangle( mat, MIDDLE_ROI, barcodePosition == BarcodePosition.MIDDLE ? notElement : elementColor );
        return mat;
    }

    @Override
    public Mat processFrame( Mat input ) {

        Mat elementImage = processFrame( input, "element" );
        Mat duckImage = processFrame( input, "duck" );
        double elementValue = Core.sumElems( elementImage ).val[0] / (elementImage.rows( ) * elementImage.cols( )) / 255;
        double duckValue = Core.sumElems( duckImage ).val[0] / (duckImage.rows( ) * duckImage.cols( )) / 255;
        telemetry.update( );
        if( elementValue < duckValue )
            return duckImage;
        return elementImage;
    }

    public BarcodePosition getBarcodePosition( ) {
        return barcodePosition;
    }
}