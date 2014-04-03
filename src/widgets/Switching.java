package widgets;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JToggleButton;

import Arduino.Arduino;

public class Switching implements Constants {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	
	Arduino arduino = new Arduino();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Switching window = new Switching();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Switching() {
		initialize();
		
		try {
			arduino.ArduinoTX("COM9", 2000 ,9600);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		final JToggleButton led1 = new JToggleButton(LED_1 + OFF);
		led1.setBounds(63, 47, 121, 23);
		frame.getContentPane().add(led1);

		led1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (led1.isSelected()) {
					led1.setText(LED_1 + ON);
					try {
						arduino.SendData("11");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					led1.setText(LED_1 + OFF);
					try {
						arduino.SendData("10");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		final JToggleButton led2 = new JToggleButton(LED_2 + OFF);
		led2.setBounds(63, 90, 121, 23);
		frame.getContentPane().add(led2);

		led2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (led2.isSelected()){
					led2.setText(LED_2 + ON);
					try {
						arduino.SendData("21");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					led2.setText(LED_2 + OFF);
					try {
						arduino.SendData("20");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		final JToggleButton led3 = new JToggleButton(LED_3 + OFF);
		led3.setBounds(63, 134, 121, 23);
		frame.getContentPane().add(led3);

		led3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (led3.isSelected()){
					led3.setText(LED_3 + ON);
					try {
						arduino.SendData("31");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					led3.setText(LED_3 + OFF);
					try {
						arduino.SendData("30");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		final JToggleButton led4 = new JToggleButton(LED_4 + OFF);
		led4.setBounds(63, 180, 121, 23);
		frame.getContentPane().add(led4);

		led4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (led4.isSelected()){
					led4.setText(LED_4 + ON);
					try {
						arduino.SendData("41");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					led4.setText(LED_4 + OFF);
					try {
						arduino.SendData("40");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		final JToggleButton encender = new JToggleButton("ENCENDER");
		encender.setBounds(260, 103, 121, 23);
		frame.getContentPane().add(encender);

		encender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (encender.isSelected()) {
					if (!led1.isSelected())
						led1.doClick();
					if (!led2.isSelected())
						led2.doClick();
					if (!led3.isSelected())
						led3.doClick();
					if (!led4.isSelected())
						led4.doClick();
					
					encender.setText("APAGAR");
				} else {
					if (led1.isSelected())
						led1.doClick();
					if (led2.isSelected())
						led2.doClick();
					if (led3.isSelected())
						led3.doClick();
					if (led4.isSelected())
						led4.doClick();
					
					encender.setText("ENCENDER");
				}
			}
		});
	}
}
