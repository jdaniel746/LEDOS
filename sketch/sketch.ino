char input[2];
char inChar;
byte index = 0;
String code = "";

void setup(){

pinMode(8, OUTPUT); // Declaramos que utilizaremos el pin 13 como salida
pinMode(9, OUTPUT); // Declaramos que utilizaremos el pin 13 como salida
pinMode(10, OUTPUT); // Declaramos que utilizaremos el pin 13 como salida
pinMode(11, OUTPUT); // Declaramos que utilizaremos el pin 13 como salida

Serial.begin(9600);
}

void loop(){
  if (Serial.available()>0){
    
    inChar = Serial.read(); // Read a character
    
    code = code + inChar;
    
    Serial.print(code);
    
    if (code.equals("11")){
        digitalWrite(8, HIGH); //Si el valor de input es 1, se enciende el led
    }
    
    if (code.equals("10")){
        digitalWrite(8, LOW); //Si el valor de input es 1, se enciende el led
    }

    if (code.equals("21")){
        digitalWrite(9, HIGH); //Si el valor de input es 1, se enciende el led
    }
    
    if (code.equals("20")){
        digitalWrite(9, LOW); //Si el valor de input es 1, se enciende el led
    }
    
    if (code.equals("31")){
        digitalWrite(10, HIGH); //Si el valor de input es 1, se enciende el led
    }
    
    if (code.equals("30")){
        digitalWrite(10, LOW); //Si el valor de input es 1, se enciende el led
    }
    
    if (code.equals("41")){
        digitalWrite(11, HIGH); //Si el valor de input es 1, se enciende el led
    }
    
    if (code.equals("40")){
        digitalWrite(11, LOW); //Si el valor de input es 1, se enciende el led
    }
    
    if (code.length() == 2) {
      code = "";
    }
    
    /*switch (entero){
      
      case 11: digitalWrite(1, HIGH);
      Serial.println("led 11");
      break;
      
      case 10: digitalWrite(1, LOW);
      Serial.println("led 10");
      break;
      
      case 21: digitalWrite(2, HIGH);
      Serial.println("led 21");
      break;
      
      case 20: digitalWrite(2, LOW);
      Serial.println("led 20");
      break;
      
      case 31: digitalWrite(3, HIGH);
      break;
      
      case 30: digitalWrite(3, LOW);
      break;
      
      case 41: digitalWrite(4, HIGH);
      break;
      
      case 40: digitalWrite(4, LOW);
      break;
    }
*/
  }
}