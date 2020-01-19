/*
Adafruit Arduino - Lesson 13. DC Motor
*/


int motorPinRight = 3;
int motorPinLeft = 2;
 
void setup() 
{ 
  pinMode(motorPinRight, OUTPUT);
  pinMode(motorPinLeft, OUTPUT);

} 
 
 
void loop() 
{ 
  int speed = 150;
      analogWrite(motorPinRight, speed);
      analogWrite(motorPinLeft, speed);
} 
