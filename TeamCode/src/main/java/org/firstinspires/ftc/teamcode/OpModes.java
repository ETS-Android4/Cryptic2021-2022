package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

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
        int duck = 1;
        double up = 0;
        double spinny = 1;
        double updown0 = 0;
        double updown1 = 0.2;
        double updown2 = 0.55;
        double updown3 = 0.9;
        double spinnyVal = 0.722;
        while(opModeIsActive()){

            forward = gamepad1.left_trigger - gamepad1.right_trigger -gamepad1.left_stick_y;
            //cause why not
            turn = gamepad1.right_stick_x * 0.9;
            //turning is too sensitive''
            if(gamepad1.y){
                robot.duckWheel.setPower(0.5);
            }

            if(gamepad1.right_bumper){
                factor = 0.3;
            }

            else{
                factor = 0.85;
            }

            forward*=factor;
            turn*=factor;

            if(gamepad2.left_stick_y > 0){
                robot.intakeMotor.setPower(-1);
                robot.intakeServo.setPosition(0.10000001);
            }

            else if(gamepad2.left_stick_y < 0){
                robot.intakeMotor.setPower(1);
                robot.intakeServo.setPosition(0.7);
            }

            else{
                robot.intakeMotor.setPower(0);
                robot.intakeServo.setPosition(0.4);
            }


            if (gamepad2.b){
                robot.intakeServo.setPosition(0.9);
            }

//            if(gamepad2.y && up!=3){
//                up +=1;
//            }
//
//            if(gamepad2.left_bumper){
//                up = 0;
//            }
//
//            if(gamepad2.a && up!=0){
//                up -=1;
//            }



            if(gamepad2.right_bumper && up!=3){
                up +=1;
            }

            else if(gamepad2.a){
                up = 0;
            }

            else if(gamepad2.left_bumper && up!=0){
                up -=1;
            }

            if(up == 0){
                robot.outakeServo.setPosition(updown0);
                robot.outakeServo2.setPosition(1-updown0);
            }

            else if(up == 1){
                robot.outakeServo.setPosition(updown1);
                robot.outakeServo2.setPosition(1-updown1);
            }

            else if(up == 2){
                robot.outakeServo.setPosition(updown2);
                robot.outakeServo2.setPosition(1-updown2);
            }

            else if(up == 3){
                robot.outakeServo.setPosition(updown3);
                robot.outakeServo.setPosition(1-updown3);
            }


//            if(gamepad2.x && robot.outakeServo.getPosition() != 0.722){
//                robot.outakeServo3.setPosition(0.722);
//                robot.outakeServo4.setPosition(0.722);
//            }
//
//            else if(gamepad2.x && robot.outakeServo.getPosition() != 0){
//                robot.outakeServo3.setPosition(0);
//                robot.outakeServo4.setPosition(0);
//            }

            if(gamepad2.x){
                spinny+=1;
                if(spinny>2){
                    spinny = 1;
                }
            }

            if(spinny == 1){
                robot.outakeServo3.setPosition(0);
                robot.outakeServo4.setPosition(0);
            }

            else if(spinny == 2){
                robot.outakeServo3.setPosition(spinnyVal);
                robot.outakeServo4.setPosition(1-spinnyVal);
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

        }
    }
}
