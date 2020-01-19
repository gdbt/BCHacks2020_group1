


int motorPinRight = 3;
int motorPinLeft = 2;
long duration;
int distance;
 
void setup() 
{ 
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  Serial.begin(9600);
  pinMode(motorPinRight, OUTPUT);
  pinMode(motorPinLeft, OUTPUT);
} 
 
 
void loop() 
{ 
  
      digitalWrite(trigPin, LOW);
      delayMicroseconds(2);

      digitalWrite(trigPin, HIGH);
      delayMicroseconds(10);
      digitalWrite(trigPin, LOW);

      duration = pulseIn(echoPin, HIGH);

      distance = duration*0.034/2;

      Serial.print("Distance: ");
      Serial.printIn(distance);

      int leftspeed = 150;
      int rightspeed = 150;
      analogWrite(motorPinRight, rightspeed);
      analogWrite(motorPinLeft, leftspeed);

      if (distance < 40) {
        leftspeed = -100;
        rightspeed = -100;
        analogWrite(motorPinRight, rightspeed);
        analogWrite(motorPinLeft, leftspeed);
        delay(500);

        rightspeed = 100;
        leftspeed = 150;
      }
} 
