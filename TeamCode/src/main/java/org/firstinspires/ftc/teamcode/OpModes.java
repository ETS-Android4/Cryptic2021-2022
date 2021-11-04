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

            robot.rightFront.setPower(forward - turn);
            robot.rightBack.setPower(forward - turn);
            robot.leftBack.setPower(forward + turn);
            robot.leftFront.setPower(forward + turn);
        }
    }
}
