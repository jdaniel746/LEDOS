#include <SoftwareSerial.h>

const int bluetoothRx = 2;
const int bluetoothTx = 3;
int ledPin1 = 13;
int ledPin2 = 5;
int ledPin3 = 6;
int ledPin4 = 7;
int ledPin5 = 8;
boolean aux = true;
  
SoftwareSerial bluetooth(bluetoothTx, bluetoothRx);

void setup() {
  pinMode(ledPin1, OUTPUT);
  pinMode(ledPin2, OUTPUT);
  pinMode(ledPin3, OUTPUT);
  pinMode(ledPin4, OUTPUT);
  pinMode(ledPin5, INPUT);
  //Setup usb serial connection to computer
  Serial.begin(9600);
  //Setup Bluetooth serial connection to android
  bluetooth.begin(9600);
}

void loop() {
  

  
  if (bluetooth.available() > 0) {
    char inByte = bluetooth.read();
    Serial.print(inByte);
    if(inByte=='1'){
       if(digitalRead(ledPin1)==0){
          digitalWrite(ledPin1,HIGH);
       }else{
          digitalWrite(ledPin1,LOW);
       }
    }
   
     if(inByte=='5'){
       if(digitalRead(ledPin1)==0){
          digitalWrite(ledPin1,HIGH);
       }else{
          digitalWrite(ledPin1,LOW);
       }
    }
    
        if(inByte=='6'){
       if(digitalRead(ledPin1)==0){
          digitalWrite(ledPin1,HIGH);
       }else{
          digitalWrite(ledPin1,LOW);
       }
    }
  }
  
     if(digitalRead(ledPin5)==0){
        if(aux == true){
            bluetooth.println("g");
            Serial.print("if");
            aux = false;
        }

      }else{
        if(aux == false){
           bluetooth.println("h");
           Serial.print("else");
           aux = true;
        }

      }

  //Read from usb serial to bluetooth
  if(Serial.available()) {
  char toSend = (char)Serial.read();
  bluetooth.print(toSend);
  Serial.print(toSend);
  flashLED();
  }
}

void flashLED() {
  digitalWrite(13, HIGH);
  delay(500);
  digitalWrite(13, LOW);
}
