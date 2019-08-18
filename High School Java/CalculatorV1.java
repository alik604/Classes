package term3;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * @author kali add operation and history bar add 1/mem add 3 evil trig
 *         functions
 */
public class CalculatorV1 {

	// --------------------------------------------
	private static JFrame frame = new JFrame("Calculator");
	private static JTextArea top = new JTextArea();
	private static JScrollPane scroll = new JScrollPane(top);

	private static String co = "";
	private static String numb = "";

	private static double numb1 = 0;
	private static double numb2 = 0;

	private static double x = 0;
	private static double y = 0;

	private static double mem = 0;

	// --------------------------------------------

	public static void main(String args[]) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.white);
		contentPane.setLayout((new GridLayout(8, 5)));
		contentPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createMatteBorder(1, 0, 0, 0, new Color(182, 182, 182)),
				BorderFactory.createMatteBorder(2, 1, 1, 1,
						contentPane.getBackground())));

		top.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		top.setFont(new Font("Ubuntu", Font.PLAIN, 16));// "Century Gothic" //
														// change to 13 or 14
														// later!!!!
		top.setEnabled(false);
		top.setDisabledTextColor(Color.black);
		top.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
				contentPane.getBackground()));
		top.setLineWrap(true); // no X axis scroll bar
		top.setWrapStyleWord(true); // will not cut off mid work

		scroll.setBorder(null);
		scroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		// scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		ActionListener RF = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frame.requestFocus();
			}
		};
		// Request Focus
		ActionListener one_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numb = numb + '1';
				top.append("\n" + numb);
				frame.requestFocus();
			}
		};
		ActionListener two_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numb = numb + '2';
				top.append("\n" + numb);

			}
		};
		ActionListener three_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '3';
				top.append("\n" + numb);
			}
		};
		ActionListener four_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '4';
				top.append("\n" + numb);
			}
		};
		ActionListener five_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numb = numb + '5';
				top.append("\n" + numb);
			}
		};
		ActionListener six_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '6';
				top.append("\n" + numb);
			}
		};
		ActionListener seven_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '7';
				top.append("\n" + numb);
			}
		};
		ActionListener eight_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '8';
				top.append("\n" + numb);
			}
		};
		ActionListener nine_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '9';
				top.append("\n" + numb);
			}
		};
		ActionListener zero_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '0';
				top.append("\n" + numb);
			}
		};
		ActionListener multi_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co = ((JButton) e.getSource()).getText();
				OPERATION();

			}
		};
		ActionListener divide_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co = ((JButton) e.getSource()).getText();
				OPERATION();

			}
		};
		ActionListener minus_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co = ((JButton) e.getSource()).getText();
				OPERATION();

			}
		};
		ActionListener plus_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co = ((JButton) e.getSource()).getText();
				OPERATION();
			}
		};
		ActionListener equal_listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EQUAL();
			}
		};
		ActionListener dec_listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '.';
				top.append("\n" + numb);
			}
		};

		JButton one = new JButton("1");
		one.addActionListener(one_listener);

		JButton two = new JButton("2");
		two.addActionListener(two_listener);

		JButton three = new JButton("3");
		three.addActionListener(three_listener);

		JButton four = new JButton("4");
		four.addActionListener(four_listener);

		JButton five = new JButton("5");
		five.addActionListener(five_listener);

		JButton six = new JButton("6");
		six.addActionListener(six_listener);

		JButton seven = new JButton("7");
		seven.addActionListener(seven_listener);

		JButton eight = new JButton("8");
		eight.addActionListener(eight_listener);

		JButton nine = new JButton("9");
		nine.addActionListener(nine_listener);

		JButton zero = new JButton("0");
		zero.addActionListener(zero_listener);

		JButton Dec = new JButton(".");
		Dec.addActionListener(dec_listener);

		JButton Equal = new JButton("=");
		Equal.addActionListener(equal_listener);

		JButton Plus = new JButton("+");
		Plus.addActionListener(plus_listener);

		JButton Multi = new JButton("*");
		Multi.addActionListener(multi_listener);

		JButton Minus = new JButton("-");
		Minus.addActionListener(minus_listener);

		JButton Divide = new JButton("/");
		Divide.addActionListener(divide_listener);
		// ------------------------
		JButton Pi = new JButton("Pi"); // \u03C0
		Pi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numb = Double.toString(Math.PI);
				top.append("\n" + numb);
			}
		});

		JButton Ce = new JButton("Ce");
		Ce.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				top.setText(null); // or " "
				co = "";
				numb = "";
				numb1 = 0;
				numb2 = 0;
				x = 0;
				y = 0;
				mem = 0;
			}
		});

		JButton RootX = new JButton("ROOTX");
		RootX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				y = Math.sqrt(x);
				numb = Double.toString(y);
				y = 0;
				top.append("\n" + numb);
			}
		});

		JButton C = new JButton("C");
		C.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = "";
			}
		});

		JButton Del = new JButton("<-");
		Del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb.substring(0, numb.length() - 1);
				top.append("\n" + numb);
			}
		});

		JButton Log = new JButton("log");
		Log.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.log10(x);

				numb = Double.toString(x);
				top.append("\n" + numb + "=");
			}
		});

		JButton Sin = new JButton("sin");
		Sin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.sin(x);

				numb = Double.toString(x);
				top.append("\n" + numb + "=");
			}
		});

		JButton Cos = new JButton("cos");
		Cos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.cos(x);
				numb = Double.toString(x);
				top.append("\n" + numb + "=");
			}
		});

		JButton Tan = new JButton("tan");
		Tan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				y = Math.tan(x);

				numb = Double.toString(y);
				y = 0;
				top.append("\n" + numb + "=");
			}
		});

		JButton AbsX = new JButton("|X|");
		AbsX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.abs(x);
				numb = Double.toString(x);
				top.append("\n" + numb + "=");
			}
		});

		JButton TentoThe = new JButton("10^X");
		TentoThe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.pow(10, x);
				numb = Double.toString(x);
				top.append("\n" + numb + "=");
			}
		});

		JButton XtotheSecound = new JButton("X^2");
		XtotheSecound.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.pow(x, 2);
				numb = Double.toString(x);
				top.append("\n" + numb + "=");
			}
		});

		JButton XtotheY = new JButton("X^3");
		XtotheY.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.pow(x, 3);
				numb = Double.toString(x);
				top.append("\n" + numb + "=");
			}
		});

		JButton OneoverX = new JButton("1/X");
		OneoverX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = 1 / x;
				numb = Double.toString(x);
				top.append("\n" + numb + "=");
			}
		});

		JButton Mc = new JButton("MC");
		Mc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mem = 0;
				top.append("\nmem now is: " + mem);
			}
		});

		JButton Mr = new JButton("MR");
		Mr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				x = mem;
				top.append("\nx now is: " + x);
				numb = Double.toString(x);
				top.append("\nnumb now is:" + numb);
			}
		});

		JButton Ms = new JButton("MS");
		Ms.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				mem = x;
				numb = Double.toString(mem);
				top.append("\nnumb now is:" + numb); // therefoth mem ==numb

			}
		});

		JButton MPlus = new JButton("M+");
		MPlus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = x + mem;
				numb = Double.toString(x);
				top.append("\n" + numb + "=");
			}
		});

		JButton MMin = new JButton("M-");
		MMin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = x - mem;
				numb = Double.toString(x);
				top.append("\n" + numb + "=");
			}
		});

		JButton RootXtotheY = new JButton("1/M");
		RootXtotheY.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = x / mem;
				numb = Double.toString(x);
				top.append("\n" + numb + "=");
			}
		});
		// --------------------------------------------------------------------------------------------
		/*
		 * .addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * } });
		 */

		JButton PlusorMin = new JButton("+/-");
		PlusorMin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				// ----
				x = x * -1;
				numb = Double.toString(x);
				// ----
				// if (x > 0)numb = numb+'-';
				top.append("\n" + numb);
			}
		});

		JButton SinMin1 = new JButton(" sin¹");
		JButton CosMin1 = new JButton(" cos¹");
		JButton TanMin1 = new JButton(" tan¹");

		// ///// make object creation is a for loop with array and add listeners
		// ///// Automatically
		seven.addActionListener(RF);
		// ////

		contentPane.add(Mc);
		contentPane.add(Mr);
		contentPane.add(MPlus);
		contentPane.add(MMin);
		contentPane.add(Ms);

		contentPane.add(RootXtotheY);
		contentPane.add(AbsX);
		contentPane.add(SinMin1);
		contentPane.add(CosMin1);
		contentPane.add(TanMin1);

		contentPane.add(RootX);
		contentPane.add(Log);
		contentPane.add(Sin);
		contentPane.add(Cos);
		contentPane.add(Tan);

		contentPane.add(XtotheY);
		contentPane.add(Ce);
		contentPane.add(C);
		contentPane.add(Del);
		contentPane.add(Divide);

		contentPane.add(XtotheSecound);
		contentPane.add(seven);
		contentPane.add(eight);
		contentPane.add(nine);
		contentPane.add(Multi);

		contentPane.add(OneoverX);
		contentPane.add(four);
		contentPane.add(five);
		contentPane.add(six);
		contentPane.add(Minus);

		contentPane.add(TentoThe);
		contentPane.add(one);
		contentPane.add(two);
		contentPane.add(three);
		contentPane.add(Plus);

		contentPane.add(Pi);
		contentPane.add(PlusorMin);
		contentPane.add(zero);
		contentPane.add(Dec);
		contentPane.add(Equal);

		frame.setFocusable(true);
		frame.setFocusTraversalKeysEnabled(false);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_NUMPAD0:
				case KeyEvent.VK_0:

					break;

				case KeyEvent.VK_NUMPAD1:
				case KeyEvent.VK_1:
					one_listener.actionPerformed(new ActionEvent(one, 99990,
							null));
					break;

				case KeyEvent.VK_NUMPAD2:
				case KeyEvent.VK_2:
					two_listener.actionPerformed(new ActionEvent(two, 99990,
							null));
					break;

				case KeyEvent.VK_NUMPAD3:
				case KeyEvent.VK_3:
					three_listener.actionPerformed(new ActionEvent(three,
							99990, null));
					break;

				case KeyEvent.VK_NUMPAD4:
				case KeyEvent.VK_4:
					four_listener.actionPerformed(new ActionEvent(four, 99990,
							null));
					break;

				case KeyEvent.VK_NUMPAD5:
				case KeyEvent.VK_5:
					five_listener.actionPerformed(new ActionEvent(five, 99990,
							null));
					break;

				case KeyEvent.VK_NUMPAD6:
				case KeyEvent.VK_6:
					six_listener.actionPerformed(new ActionEvent(six, 99990,
							null));
					break;

				case KeyEvent.VK_NUMPAD7:
				case KeyEvent.VK_7:
					seven_listener.actionPerformed(new ActionEvent(seven,
							99990, null));
					break;

				case KeyEvent.VK_NUMPAD8:
				case KeyEvent.VK_8:
					eight_listener.actionPerformed(new ActionEvent(eight,
							99990, null));
					break;

				case KeyEvent.VK_NUMPAD9:
				case KeyEvent.VK_9:
					nine_listener.actionPerformed(new ActionEvent(nine, 99990,
							null));
					break;
				case KeyEvent.VK_ADD:
					plus_listener.actionPerformed(new ActionEvent(Plus, 99990,
							null));
					break;
				case KeyEvent.VK_DIVIDE:
					divide_listener.actionPerformed(new ActionEvent(Divide,
							99990, null));
					break;

				case KeyEvent.VK_MULTIPLY:
					multi_listener.actionPerformed(new ActionEvent(Multi,
							99990, null));
					break;

				case KeyEvent.VK_SUBTRACT:
					minus_listener.actionPerformed(new ActionEvent(Minus,
							99990, null));
					break;
				case KeyEvent.VK_ENTER:
				case KeyEvent.VK_EQUALS:
					equal_listener.actionPerformed(new ActionEvent(Equal,
							99990, null));
					break;
				case KeyEvent.VK_DECIMAL:
					dec_listener.actionPerformed(new ActionEvent(Dec, 99990,
							null));
					break;
				}
			}
		});
		frame.setSize(390, 490);
		Toolkit tkit = Toolkit.getDefaultToolkit();
		Dimension dime = tkit.getScreenSize();
		int xPos = (dime.width / 2) - (frame.getWidth() / 2);
		int yPos = (dime.height / 2) - (frame.getHeight() / 2);
		frame.setLocation(xPos, yPos);
		frame.setLocationRelativeTo(null);
		frame.add(contentPane, BorderLayout.SOUTH);
		frame.add(scroll);
		frame.setVisible(true);
	}

	// combine PLUS,MINUS,DIVIDE and MULTI into OPERATION
	public static void OPERATION() {
		frame.requestFocus();
		if (numb == null || numb.isEmpty())
			return;

		x = Double.parseDouble(numb);

		numb = "";
		numb1 = x;
		x = 0;

		top.append("\n" + co + numb1);
	}

	public static void EQUAL() {
		if (numb == null || numb.isEmpty())
			return;

		// top.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		// scroll.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		x = Double.parseDouble(numb);
		numb2 = x;
		switch (co) {
		case "+":
			System.out.println("is PLUS");
			x = numb1 + numb2;
			break;
		case "-":
			System.out.println("is MINUS");
			x = numb1 - numb2;
			break;
		case "*":
			System.out.println("is MULTI");
			x = numb1 * numb2;
			break;
		case "/":
			System.out.println("is DIVIDE");
			x = numb1 / numb2;
			break;
		}
		top.append("\nans is: " + x);
		numb = "";
		numb1 = x; // the new x
		// top.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		// scroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

	}

}
