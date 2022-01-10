package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
   public DcMotor leftFront;
   public DcMotor rightFront;
   public DcMotor leftBack;
   public DcMotor rightBack;
    public DcMotor duckWheel;
    public DcMotor intakeMotor;
    public Servo intakeServo;
   int mode;
//   public DcMotor intakeMotor;

   public void initialize(OpMode opMode) {
       leftFront = opMode.hardwareMap.get(DcMotor.class, "lf");
       rightFront = opMode.hardwareMap.get(DcMotor.class, "rf");
       leftBack = opMode.hardwareMap.get(DcMotor.class,  "lb");
       rightBack = opMode.hardwareMap.get(DcMotor.class, "rb");
       duckWheel = opMode.hardwareMap.get(DcMotor.class, "duckwheel");
       intakeMotor = opMode.hardwareMap.get(DcMotor.class, "intakeM");
       intakeServo = opMode.hardwareMap.get(Servo.class, "intakeS");
//       intakeMotor = opMode.hardwareMap.get(DcMotor.class, "intake");

       leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
       leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
       mode = 1;
       if (mode == 0) {
           leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
           rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
           leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
           rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
       } else if(mode == 1) {
           leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
           rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
           leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
           rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       }
       intakeServo.setPosition(0);
   }
}


