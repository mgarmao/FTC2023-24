package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Auto1")
public class Auto1 extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 3600 ;
    static final double     DRIVE_GEAR_REDUCTION    = 12.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.4;
    static final double     TURN_SPEED              = 0.5;

    private DcMotor front_left  = null;
    private DcMotor front_right = null;
    private DcMotor back_left   = null;
    private DcMotor back_right  = null;
    private DcMotor elevator  = null;
    private Servo grabber = null;
    Servo   servo;
    int ServoPosition = 1;
    int elevatorZero=0;
    @Override
    public void runOpMode() {
        front_left   = hardwareMap.get(DcMotor.class, "FrontL0");
        front_right  = hardwareMap.get(DcMotor.class, "BackL2");
        back_left    = hardwareMap.get(DcMotor.class, "FrontR1");
        back_right   = hardwareMap.get(DcMotor.class, "BackR3");
        elevator = hardwareMap.get(DcMotor.class,"elevator");
        elevatorZero =elevator.getCurrentPosition();
        servo = hardwareMap.get(Servo.class, "Servo0");

        front_left.setDirection(DcMotor.Direction.REVERSE);
        back_left.setDirection(DcMotor.Direction.REVERSE);

        front_right.setDirection(DcMotor.Direction.FORWARD);
        back_right.setDirection(DcMotor.Direction.FORWARD);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        front_left.setPower(-0.5);
        front_right.setPower(0.5);
        back_left.setPower(0.5);
        back_right.setPower(- 0.5);

        sleep(1500);
        front_left.setPower(0);
        front_right.setPower(0);
        back_left.setPower(0);
        back_right.setPower(0);
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
}