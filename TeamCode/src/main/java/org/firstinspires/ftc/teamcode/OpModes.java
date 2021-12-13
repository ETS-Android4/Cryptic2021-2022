package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="First OpMode")
public class OpModes extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        Robot robot = new Robot();
        robot.initialize(this);
        waitForStart();
        double forward, turn, factor =1;
        boolean px = false;
        boolean toggle = false;
        boolean value;
        while(opModeIsActive()){
            forward = -gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;

            if(gamepad1.b){
                factor = 0.5;
                forward*=factor;
                turn*=factor;
            }

            value = gamepad1.x;
            if(value && !px){
                robot.intakeServo.setPower(toggle ? 0:1);
                toggle = !toggle;
            }
            px = value;


            double LeftPower = (forward + turn);
            double RightPower = (forward - turn);
            double denominator = Math.max(Math.max(Math.abs(LeftPower), Math.abs(RightPower)), 1);
            LeftPower /= denominator;
            RightPower /= denominator;
            //proper scaling for motor powers in case it exceeds 1

            robot.rightFront.setPower(RightPower);
            robot.rightBack.setPower(RightPower);
            robot.leftBack.setPower(LeftPower);
            robot.leftFront.setPower(LeftPower);
        }
    }
}
