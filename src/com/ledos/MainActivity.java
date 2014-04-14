package com.ledos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
  TextView myLabel;
  BluetoothAdapter mBluetoothAdapter;
  BluetoothSocket mmSocket;
  BluetoothDevice mmDevice;
  OutputStream mmOutputStream;
  InputStream mmInputStream;
  Thread workerThread;
  private Button botonPress;
  private Button botonAll;
  private Switch switch1;
  private Switch switch2;
  private Switch switch3;
  private Switch switch4;
  
  byte[] readBuffer;
  int readBufferPosition;
  int counter;
  volatile boolean stopWorker;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_main);

    Button openButton = (Button)findViewById(R.id.BtnOpen);
    Button closeButton = (Button)findViewById(R.id.BtnClose);
    botonPress = (Button)findViewById(R.id.BtnPress);
    botonAll = (Button)findViewById(R.id.BtnAll);
    switch1 = (Switch)findViewById(R.id.switch1);
    switch2 = (Switch)findViewById(R.id.switch2);
    switch3 = (Switch)findViewById(R.id.switch3);
    switch4 = (Switch)findViewById(R.id.switch4);
    
    myLabel = (TextView)findViewById(R.id.Label);

    //Open Button
    openButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        try {
          findBT();
          openBT();
        }
        catch (IOException ex) { }
      }
    });
    
    //press Button
    botonPress.setOnTouchListener(new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                  try {
					sendData("5");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}              
                break;
            case MotionEvent.ACTION_UP:
            	try {
					sendData("5");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                break;
            default:
                break;
            }
            return false;
        }
    });
    
    //All Button
    botonAll.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			if(switch1.isChecked()){
				botonAll.setText("Encender");
				switch1.setChecked(false);
				switch2.setChecked(false);
				switch3.setChecked(false);
				switch4.setChecked(false);
			}else{
				botonAll.setText("Apagar");
				switch1.setChecked(true);
				switch2.setChecked(true);
				switch3.setChecked(true);
				switch4.setChecked(true);
			}
		}
		
	});

    //Close button
    closeButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        try {
          closeBT();
        }
        catch (IOException ex) { }
      }
    });
    
    switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			
				try {
					sendData("1");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	});
    
    switch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			
				try {
					sendData("2");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	});
    
    switch3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			
				try {
					sendData("3");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	});
    
    switch4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			
				try {
					sendData("4");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	});
  }
  
  
 
  void findBT() {
    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    
    if(mBluetoothAdapter == null) {
      myLabel.setText("No bluetooth adapter available");
      Log.d("TAG", "jaja");
    }

    if(!mBluetoothAdapter.isEnabled()) {
      Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      startActivityForResult(enableBluetooth, 0);
    }

    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
    
    Log.d("SIZE", pairedDevices.size() + "");
    
    if(pairedDevices.size() > 0) {
      for(BluetoothDevice device : pairedDevices) {
    	  Log.d("NO ENTRA", "FUCK");
        if(device.getName().equals("BT UART")) {
        	Log.d("ENTRO?", "GAOGKAG");
          mmDevice = device;
          break;
        }
      }
    }
    myLabel.setText("Bluetooth Device Found");
  }

  void openBT() throws IOException {
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService ID
    Log.d("hola-",uuid.toString());
    mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);    
    mmSocket.connect();
    mmOutputStream = mmSocket.getOutputStream();
    mmInputStream = mmSocket.getInputStream();
    beginListenForData();
    myLabel.setText("Bluetooth Opened");
  }

  void beginListenForData() {
    final Handler handler = new Handler(); 
    final byte delimiter = 10; //This is the ASCII code for a newline character

    stopWorker = false;
    readBufferPosition = 0;
    readBuffer = new byte[1024];
    workerThread = new Thread(new Runnable() {
      public void run() {
         while(!Thread.currentThread().isInterrupted() && !stopWorker) {
          try {
            int bytesAvailable = mmInputStream.available();            
            if(bytesAvailable > 0) {
              byte[] packetBytes = new byte[bytesAvailable];
              mmInputStream.read(packetBytes);
              for(int i=0;i<bytesAvailable;i++) {
                byte b = packetBytes[i];
                if(b == delimiter) {
                  byte[] encodedBytes = new byte[readBufferPosition];
                  System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                  final String data = new String(encodedBytes, "US-ASCII");
                  readBufferPosition = 0;
                  Log.d("IN", mmInputStream + " mg =>" + data);
                  handler.post(new Runnable() {
                    public void run() {
                      myLabel.setText(data);
                      
                      if(data.trim().equalsIgnoreCase("g")){
                    	  switch2.setChecked(false);
                      }
                      
                      if(data.trim().equalsIgnoreCase("h")){
                    	  switch2.setChecked(true);
                      }
                    }
                  });
                }
                else {
                  readBuffer[readBufferPosition++] = b;
                }
              }
            }
          } 
          catch (IOException ex) {
            stopWorker = true;
          }
         }
      }
    });

    workerThread.start();
  }

  void sendData(String msg) throws IOException {
    //String msg = myTextbox.getText().toString();
  //  msg += "\n";
   mmOutputStream.write(msg.getBytes());
//    mmOutputStream.write(1);
    Log.d("OUT", mmOutputStream + " mg =>" + msg);
//    mmOutputStream.write('A');
    myLabel.setText("Data Sent");
  }

  void closeBT() throws IOException {
    stopWorker = true;
    mmOutputStream.close();
    mmInputStream.close();
    mmSocket.close();
    myLabel.setText("Bluetooth Closed");
  }

  private void showMessage(String theMsg) {
        Toast msg = Toast.makeText(getBaseContext(),
                theMsg, (Toast.LENGTH_LONG)/160);
        msg.show();
    }
}