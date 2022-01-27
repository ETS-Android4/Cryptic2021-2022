package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
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
        double up = 0;
        double spinny = 1;
        double updown0 = 0;
        double updown1 = 0.2;
        double updown2 = 0.55;
        double updown3 = 0.9;
        double spinnyVal = 0.722;
        int intakeServoState=0;
        double sss = 0;
        boolean starts=false;
        boolean changed = true;
        boolean changed2 = false;
        boolean dropTog = false;
        ElapsedTime mytimer = new ElapsedTime();
        boolean outtake_toggle = true;
        while(opModeIsActive()){

            //basic drivetrain functions
            forward = gamepad1.left_trigger - gamepad1.right_trigger -gamepad1.left_stick_y;
            //cause why not
            turn = gamepad1.right_stick_x * 0.9;
            //turning is too sensitive''

            if(gamepad1.right_bumper){
                factor = 0.3;
            }

            else{
                factor = 0.85;
            }

            forward*=factor;
            turn*=factor;

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



            //duckwheel
            if(gamepad2.x){
                robot.duckWheel.setPower(0.55);
            }else if(gamepad2.y){
                robot.duckWheel.setPower(-0.55);
            }else{
                robot.duckWheel.setPower(0);
            }




            //intake
            if(gamepad2.left_stick_y > 0.5){
                robot.intakeMotor.setPower(-0.9);
                robot.intakeServo.setPosition(0);
                telemetry.addData("Y", 0);
            }

            else if(gamepad2.left_stick_y < -0.5){
                robot.intakeMotor.setPower(0.65);
                robot.intakeServo.setPosition(0.3);
                telemetry.addData("Y", 0.3);
            }



            else if (intakeServoState !=1){

                robot.intakeMotor.setPower(0);
                robot.intakeServo.setPosition(0.4);
                telemetry.addData("Y", 0.4);

            }

            if (gamepad2.a) {
                robot.intakeServo.setPosition(0.6);
            }






            //outtake transportation sequence

            if((mytimer.time()>0 && mytimer.time()<2) && (starts)){
                robot.intakeServo.setPosition(1);
            }
            else if((mytimer.time()>1.5 && mytimer.time()<3) && (starts)){
                robot.intakeMotor.setPower(1);
            }
            else if (intakeServoState == 1){
                robot.intakeServo.setPosition(.3);
                robot.intakeMotor.setPower(0);
                intakeServoState = 0;
            }

            if(gamepad2.b){
                mytimer.reset();
                intakeServoState = 1;
                starts = true;
            }



            //outtake stuff
/*
            if(gamepad2.right_bumper){
                up +=1;
                if(up > 3){
                    up = 0;
                }
            }

            else if(gamepad2.a){
                up = 0;
            }

            else if(gamepad2.left_bumper){
                up -=1;
                if(up < 0){
                    up = 3;
                }
            }

            if(up == 0){
                robot.extensionServoLeft.setPosition(updown0);
                robot.extensionServoRight.setPosition(1-updown0);
            }

            else if(up == 1){
                robot.extensionServoLeft.setPosition(updown1);
                robot.extensionServoRight.setPosition(1-updown1);
            }

            else if(up == 2){
                robot.extensionServoLeft.setPosition(updown2);
                robot.extensionServoRight.setPosition(1-updown2);
            }

            else if(up == 3){
                robot.extensionServoLeft.setPosition(updown3);
                robot.extensionServoRight.setPosition(1-updown3);
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

 */
//            if(gamepad2.left_trigger>0.5 && !changed) {
//                if(robot.extensionServoLeft.getPosition() == 0.6) {
//                    robot.extensionServoLeft.setPosition(1);
//                }
//                else {
//                    robot.extensionServoLeft.setPosition(0.6);
//                }
//                changed = true;
//            } else if(!(gamepad2.left_trigger>0.5)) changed = false;

            if(gamepad2.left_trigger>0.5 && !changed) {
                if(sss == 0.6) {
                    robot.extensionServoLeft.setPosition(0.95);
                    robot.extensionServoRight.setPosition(0);
                    robot.outakeServo3.setPosition(0.15);
                    sss = 1;
                }
                else {
                    robot.extensionServoLeft.setPosition(0.27);
                    robot.extensionServoRight.setPosition(1);
                    robot.outakeServo3.setPosition(0.33);
                    sss= 0.6;
                }
                changed = true;


            } else if(!(gamepad2.left_trigger>0.5)) changed = false;

            if (gamepad2.left_bumper && !changed2){
                if(!dropTog) {
                    robot.outakeServo3.setPosition(0.05);
                    dropTog = true;
                }
                else {
                    robot.outakeServo3.setPosition(0.33);
                    dropTog = false;
                }

                changed2 = true;
            } else if(!(gamepad2.left_bumper)) {
                changed2 = false;

            }


            telemetry.addData("left servo",robot.extensionServoLeft.getPosition());
            telemetry.addData("right servo",robot.extensionServoRight.getPosition());
            telemetry.update();

        }
    }
}
