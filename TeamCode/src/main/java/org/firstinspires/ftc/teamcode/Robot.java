package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.eyesight.Pipe_line;
import org.firstinspires.ftc.teamcode.eyesight.Pipe_lineUtil;

public class Robot {
    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack;
    public DcMotor rightBack;
    public DcMotor duckWheel;
    public Servo extensionServoLeft;
    public Servo extensionServoRight;
    public Servo outakeServo3;
    public Servo outakeServo4;
    public Servo capstone;
    public Servo trapdoor;
    private ConceptTensorFlowObjectDetectionWebcam objDetection;
    public Pipe_lineUtil dick;

    DcMotor intakeMotor;
    Servo intakeServo;
    public Servo transServo;

    public void initialize(LinearOpMode opMode, Pipe_line.Team team) {
        opMode.telemetry = new MultipleTelemetry(opMode.telemetry, FtcDashboard.getInstance().getTelemetry());
        dick = new Pipe_lineUtil(opMode.hardwareMap, "Webcam 1" , opMode.telemetry, team);
        dick.init();

        objDetection = new ConceptTensorFlowObjectDetectionWebcam();
        intakeMotor = opMode.hardwareMap.get(DcMotor.class, "intakeM");
        intakeServo = opMode.hardwareMap.get(Servo.class, "intakeS");
        transServo = opMode.hardwareMap.get(Servo.class, "trans");

        extensionServoLeft = opMode.hardwareMap.get(Servo.class, "osl");
        extensionServoRight = opMode.hardwareMap.get(Servo.class, "osr");
        outakeServo3 = opMode.hardwareMap.get(Servo.class, "outakeS3");
        outakeServo4 = opMode.hardwareMap.get(Servo.class, "outakeS4");

        leftFront = opMode.hardwareMap.get(DcMotor.class, "lf");
        rightFront = opMode.hardwareMap.get(DcMotor.class, "rf");
        leftBack = opMode.hardwareMap.get(DcMotor.class, "lb");
        rightBack = opMode.hardwareMap.get(DcMotor.class, "rb");
        duckWheel = opMode.hardwareMap.get(DcMotor.class, "duckwheel");

        capstone = opMode.hardwareMap.get(Servo.class, "cap");

        trapdoor = opMode.hardwareMap.get(Servo.class,"td");

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.

        //to make robot wheels spin the same way bc the motor inverts the other side's wheels
        //reversing the one side of the robot's wheels direction
        //bc the motor inverts the other side's wheels
        //therefore being able to travel forward
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        //to make robot stop when youre not moving the sticks
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        duckWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



//       leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//       rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//       leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//       rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        capstone.setPosition(0);
        intakeServo.setPosition(0);
        transServo.setPosition(1);
        trapdoor.setPosition(0.6);
        extensionServoLeft.setPosition(0.27);
        extensionServoRight.setPosition(1);
        outakeServo3.setPosition(0.33);
        opMode.telemetry.addLine("inated");
        opMode.telemetry.update();


    }

    public void initialize(LinearOpMode opMode) {
        initialize(opMode, Pipe_line.Team.BLUE);
    }
}
