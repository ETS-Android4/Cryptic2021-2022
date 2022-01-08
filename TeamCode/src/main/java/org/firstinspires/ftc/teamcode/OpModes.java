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
        double forward, turn = 1;
        double factor = 0.85;
        boolean px = false;
        boolean toggle = false;
        boolean value;
        boolean oldX = false;
        boolean xToggle = false;
        boolean oldY = false;
        boolean yToggle = false;
        while(opModeIsActive()){

            forward = gamepad1.left_trigger - gamepad1.right_trigger -gamepad1.left_stick_y;
            //cause why not
            turn = gamepad1.right_stick_x * 0.9;
            //turning is too sensitive''
            if(gamepad1.x && !oldX) {
                xToggle = !xToggle;
                if(xToggle) {
                    //spin one direction
                    yToggle = false;
                    robot.duckWheel.setPower(.5);
                } else {
                    //stop spinning
                    robot.duckWheel.setPower(0);
                }
            }

            if(gamepad1.y && !oldY) {
                yToggle = !yToggle;
                if(yToggle) {
                    //spin other direction
                    xToggle = false;
                    robot.duckWheel.setPower(-.5);
                } else {
                    //stop spinning
                    robot.duckWheel.setPower(0);
                }
            }
            gamepad1.x = oldX;
            gamepad1.y = oldY;

            if(gamepad1.right_bumper){
                factor = 0.3;
            }else{
                factor = 0.85;
            }
            forward*=factor;
            turn*=factor;

            value = gamepad1.x;
            if(value && !px){
//                robot.intakeMotor.setPower(toggle ? 0:1);
                toggle = !toggle;
            }
            px = value;


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

        }
    }
}
