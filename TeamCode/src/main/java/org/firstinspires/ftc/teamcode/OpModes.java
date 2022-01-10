package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="First OpMode")
public class OpModes extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        Robot robot = new Robot();
        robot.initialize(this);
        waitForStart();
        double forward, turn = 1;
        double factor = 0.85;
        boolean px = false;
        boolean toggle = false;
        int duckwheelToggle = 2;
        while(opModeIsActive()){

            forward = gamepad1.left_trigger - gamepad1.right_trigger -gamepad1.left_stick_y;
            //cause why not
            turn = gamepad1.right_stick_x * 0.9;
            //turning is too sensitive''
            if(gamepad1.y){
                robot.duckWheel.setPower(.5);
            }
            if(gamepad1.right_bumper){
                factor = 0.3;
            }else{
                factor = 0.85;
            }
            forward*=factor;
            turn*=factor;

            if(gamepad2.left_stick_y > 0){
                robot.intakeMotor.setPower(-0.8);
                robot.intakeServo.setPosition(0.1);
                telemetry.addData("Y", 0.1);
            }

            else if(gamepad2.left_stick_y < 0){
                robot.intakeMotor.setPower(0.6);
                robot.intakeServo.setPosition(0.501);
                telemetry.addData("Y", 0.5);
            }

            else{
                robot.intakeMotor.setPower(0);
                robot.intakeServo.setPosition(0.4);
                telemetry.addData("Y", 0.4);
            }

            if (gamepad2.a) {
                robot.intakeServo.setPosition(0.6);
            }

            if(gamepad2.x && duckwheelToggle == 1){
                robot.duckWheel.setPower(0);
                duckwheelToggle = 2;
            }else if(gamepad2.x){
                robot.duckWheel.setPower(.5);
                duckwheelToggle = 1;
            }else if(gamepad2.y && duckwheelToggle == 0){
                robot.duckWheel.setPower(0);
                duckwheelToggle = 2;
            }else if(gamepad2.y){
                robot.duckWheel.setPower(-.5);
                duckwheelToggle = 0;
            }

            double leftPower = (forward + turn);
            double rightPower = (forward - turn);
            double denominator = Math.max(Math.max(Math.abs(leftPower), Math.abs(rightPower)), 1);
            leftPower /= denominator;
            rightPower /= denominator;
            //proper scaling for motor powers in case it exceeds 1

            robot.rightFront.setPower(rightPower);
            robot.rightBack.setPower(rightPower);
            robot.leftBack.setPower(leftPower);
            robot.leftFront.setPower(leftPower);

            telemetry.update();
        }
    }
}
