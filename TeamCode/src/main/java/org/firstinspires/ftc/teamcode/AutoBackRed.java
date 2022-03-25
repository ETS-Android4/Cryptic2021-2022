package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.eyesight.Pipe_line;
//import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="AutoBackRed")

public class AutoBackRed extends LinearOpMode {

    /* Declare OpMode members. */
    Robot robot   = new Robot();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 134.4 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.77953 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.4;
    static final double     TURN_SPEED              = 0.3;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.initialize(this);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.leftFront.getCurrentPosition(),

                robot.rightFront.getCurrentPosition());
        telemetry.update();
        robot.initRedVision(this);
        while (!(isStarted())) {
            telemetry.update();
        }
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        Pipe_line.BarcodePosition pos = robot.dick.getBarcodePosition();
        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        // encoderDrive(DRIVE_SPEED,  -64,  -64, 1);  // S1: Forward 47 Inches with 5 Sec timeout
        //drive_speed - how fast its gonna move
        //turn_speed - when left and right diff
        //left+ right- - turn left
        //left- right+ - turn right
        //left inches - how much left side of wheels should spin
        //right inches - how much right side of wheels should spin
        //go foward = -#
        //timeout - wait how many secs before go into the next command


        //1 block = 30 in --> ~16 in on robot
        //spin wheel
        encoderDrive(DRIVE_SPEED, -65,-65, 1, 0);//forward 1
        encoderDrive(0.1, -40,-40, 1, 250);
        robot.duckWheel.setPower(.5);
        sleep(2000);
        robot.duckWheel.setPower(0);
        encoderDrive(0.2, 10, 10, 1,0);//scoot foward
        encoderDrive(TURN_SPEED, 12,-12, 1, 250); //turn a wee
        encoderDrive(DRIVE_SPEED, 169,169, 3, 250); //f to hub

        encoderDrive(TURN_SPEED, 21,-21, 1, 250); //l 45
        //outtake code to drop freight
        if (pos == Pipe_line.BarcodePosition.LEFT) {
            //top
            robot.extensionServoLeft.setPosition(0.95);
            robot.extensionServoRight.setPosition(0);
            robot.outakeServo3.setPosition(0.17);
            sleep(1000);
            robot.outakeServo3.setPosition(0.03);
            telemetry.addData("position", "left");
        } else if (pos == Pipe_line.BarcodePosition.RIGHT) {
            //middle
            encoderDrive(DRIVE_SPEED,-20,-20,1,250);
            robot.outakeServo3.setPosition(0.03);
            sleep(1000);
            telemetry.addData("position", "right");

        } else {
            //bottom
            robot.extensionServoLeft.setPosition(0.5);
            robot.extensionServoRight.setPosition(0.5);
            robot.outakeServo3.setPosition(0.17);
            sleep(1000);
            robot.outakeServo3.setPosition(0.03);
            telemetry.addData("position", "middle");
        }
        //reset the outtake
        sleep(500);
        robot.extensionServoLeft.setPosition(0.27);
        robot.extensionServoRight.setPosition(1);
        robot.outakeServo3.setPosition(0.33);
        telemetry.addData("rip", "rip");
        telemetry.update();
        sleep(700);
        //conditional to drop at various heights

       // encoderDrive(DRIVE_SPEED, -83,-83, 1, 250); //b .5
        encoderDrive(TURN_SPEED, 23,-23, 1, 250); //l 45
        encoderDrive(DRIVE_SPEED, -100,-100,2,100);
        encoderDrive(DRIVE_SPEED, -100, -100, 3, 250); //f 2


        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS, int wait) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftFront.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightFront.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.leftFront.setTargetPosition(newLeftTarget);
            robot.leftBack.setTargetPosition(newLeftTarget);
            robot.rightFront.setTargetPosition(newRightTarget);
            robot.rightBack.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftFront.setPower(Math.abs(speed));
            robot.leftBack.setPower(Math.abs(speed));
            robot.rightFront.setPower(Math.abs(speed));
            robot.rightBack.setPower(Math.abs(speed));
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftFront.isBusy() || robot.rightFront.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftFront.getCurrentPosition(),
                        robot.rightFront.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftFront.setPower(0);
            robot.leftBack.setPower(0);
            robot.rightFront.setPower(0);
            robot.rightBack.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(wait);   // optional pause after each move
        }
    }
}