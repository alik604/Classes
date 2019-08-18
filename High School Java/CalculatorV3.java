package term3;
/**
 * @author kali 
 * 
 * known issues list
 * 1. cant do multiple operations is one line
 * 2.
 * 
 */
import java.awt.BorderLayout;
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


//add operation and history bar
//fix "  .   "  // done!!!
//fix ans
public class CalculatorV3 {

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

	// -------------------------------------------- not my work* 
	private static class CalcButton extends JButton {
		public CalcButton(String label) {
			super(label);
			this.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					frame.requestFocusInWindow();
				}
			});
		}
	}

	// --------------------------------------------

	public static void main(String args[]) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setLayout((new GridLayout(8, 5)));
		contentPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(182, 182, 182)),BorderFactory.createMatteBorder(2, 1, 1, 1,contentPane.getBackground())));

		// top.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		top.setFont(new Font("Ubuntu", Font.PLAIN, 16));// change to 13 or 14
		// "Century Gothic"
		top.setEnabled(false);
		top.setDisabledTextColor(Color.black);
		top.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
				contentPane.getBackground()));
		top.setLineWrap(true); // no X axis scroll bar
		top.setWrapStyleWord(true); // will not cut off mid work

		scroll.setBorder(null);
		// scroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		// scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Request Focus
		ActionListener one_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numb = numb + '1';
				top.append("1");
			}
		};
		ActionListener two_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numb = numb + '2';
				top.append("2");
			}
		};
		ActionListener three_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '3';
				top.append("3");
			}
		};
		ActionListener four_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '4';
				top.append("4");
			}
		};
		ActionListener five_listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numb = numb + '5';
				top.append("5");
			}
		};
		ActionListener six_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '6';
				top.append("6");
			}
		};
		ActionListener seven_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '7';
				top.append("7");
			}
		};
		ActionListener eight_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '8';
				top.append("8");
			}
		};
		ActionListener nine_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '9';
				top.append("9");
			}
		};
		ActionListener zero_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '0';
				top.append("0");
			}
		};
		ActionListener multi_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co = ((JButton) e.getSource()).getText();
				OPERATION();
				top.append(co);
			}
		};
		ActionListener divide_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co = ((JButton) e.getSource()).getText();
				OPERATION();
				top.append(co);
			}
		};
		ActionListener minus_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co = ((JButton) e.getSource()).getText();
				OPERATION();
				top.append(co);
			}
		};
		ActionListener plus_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				co = ((JButton) e.getSource()).getText();
				OPERATION();
				top.append(co);
			}
		};
		ActionListener equal_listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (numb == null || numb.isEmpty())
					return;
				EQUAL();
			}
		};
		ActionListener dec_listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb + '.';
				top.append(".");
			}
		};

		JButton one = new CalcButton("1");
		one.addActionListener(one_listener);

		JButton two = new CalcButton("2");
		two.addActionListener(two_listener);

		JButton three = new CalcButton("3");
		three.addActionListener(three_listener);

		JButton four = new CalcButton("4");
		four.addActionListener(four_listener);

		JButton five = new CalcButton("5");
		five.addActionListener(five_listener);

		JButton six = new CalcButton("6");
		six.addActionListener(six_listener);

		JButton seven = new CalcButton("7");
		seven.addActionListener(seven_listener);

		JButton eight = new CalcButton("8");
		eight.addActionListener(eight_listener);

		JButton nine = new CalcButton("9");
		nine.addActionListener(nine_listener);

		JButton zero = new CalcButton("0");
		zero.addActionListener(zero_listener);

		JButton Dec = new CalcButton(".");
		Dec.addActionListener(dec_listener);

		JButton Equal = new CalcButton("=");
		Equal.addActionListener(equal_listener);

		JButton Plus = new CalcButton("+");
		Plus.addActionListener(plus_listener);

		JButton Multi = new CalcButton("*");
		Multi.addActionListener(multi_listener);

		JButton Minus = new CalcButton("-");
		Minus.addActionListener(minus_listener);

		JButton Divide = new CalcButton("/");
		Divide.addActionListener(divide_listener);
		// ------------------------
		JButton Pi = new CalcButton("Pi"); // \u03C0
		Pi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numb = Double.toString(Math.PI);
				top.append("\n" + numb);
			}
		});

		JButton Ce = new CalcButton("Ce");
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

		JButton RootX = new CalcButton("ROOTX");
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

		JButton C = new CalcButton("C");
		C.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numb = "";
			}
		});

		JButton Del = new CalcButton("<-");
		Del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numb = numb.substring(0, numb.length() - 1);
				top.append("\n" + numb);
			}
		});

		JButton Log = new CalcButton("log");
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

		JButton Sin = new CalcButton("sin");
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

		JButton Cos = new CalcButton("cos");
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

		JButton Tan = new CalcButton("tan");
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

		JButton AbsX = new CalcButton("|X|");
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

		JButton TentoThe = new CalcButton("10^X");
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

		JButton XtotheSecound = new CalcButton("X^2");
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

		JButton XtotheY = new CalcButton("X^3");
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

		JButton OneoverX = new CalcButton("1/X");
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

		JButton Mc = new CalcButton("MC");
		Mc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mem = 0;
				top.append("\nmem now is: " + mem);
			}
		});

		JButton Mr = new CalcButton("MR");
		Mr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				x = mem;
				top.append("\nx now is: " + x);
				numb = Double.toString(x);
				top.append("\nnumb now is:" + numb);
			}
		});

		JButton Ms = new CalcButton("MS");
		Ms.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				mem = x;
				numb = Double.toString(mem);
				top.append("\nmem now is:" + numb); // therefoth mem ==numb

			}
		});

		JButton MPlus = new CalcButton("M+");
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

		JButton MMin = new CalcButton("M-");
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

		JButton RootXtotheY = new CalcButton("1/MR");
		RootXtotheY.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = 1 / mem;
				numb = Double.toString(x);
				top.append("\n" + numb + "=");
			}
		});
		/*
		 * .addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * } });
		 */

		JButton PlusorMin = new CalcButton("+/-");
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
		JButton SinMin1 = new CalcButton(" sin¹");
		SinMin1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.asin(x);
				numb = Double.toString(x);
				top.append("\n" + numb + "=");

			}
		});
		JButton CosMin1 = new CalcButton(" cos¹");
		CosMin1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.acos(x);
				numb = Double.toString(x);
				top.append("\n" + numb + "=");

			}
		});
		JButton TanMin1 = new CalcButton(" tan¹");
		TanMin1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					x = Double.parseDouble(numb);
				} catch (Exception i) {
				}
				x = Math.asin(x);
				numb = Double.toString(x);
				top.append("\n" + numb + "=");

			}
		});

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
					zero_listener.actionPerformed(new ActionEvent(zero, 99990,
							null));
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

					if ((e.getModifiers() & KeyEvent.SHIFT_MASK) != 0) {
						multi_listener.actionPerformed(new ActionEvent(Multi,
								99990, null));
					} else {
						eight_listener.actionPerformed(new ActionEvent(eight,
								99990, null));
					}
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

				case KeyEvent.VK_SLASH:
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

				case KeyEvent.VK_MINUS:
					minus_listener.actionPerformed(new ActionEvent(Minus,
							99990, null));
					break;

				case KeyEvent.VK_ENTER:
					equal_listener.actionPerformed(new ActionEvent(Equal,
							99990, null));
					break;
				case KeyEvent.VK_DECIMAL:
					dec_listener.actionPerformed(new ActionEvent(Dec, 99990,
							null));
					break;

				case KeyEvent.VK_EQUALS:
					if ((e.getModifiers() & KeyEvent.SHIFT_MASK) != 0) {
						plus_listener.actionPerformed(new ActionEvent(Plus,
								99990, null));
					} else {
						equal_listener.actionPerformed(new ActionEvent(Equal,
								99990, null));
					}

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

		numb1= Double.parseDouble(numb);
		numb = "";
	}

	public static void EQUAL() {
		if (numb == null || numb.isEmpty())
			return;

		numb2 = Double.parseDouble(numb);

		if (co == null) {
			top.append("\nwhat are you doing???... CO is null");
			return;
		}

		else {
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
			numb = "";	
			numb2 = 0; // unneeded 
			top.append(" = " + x + "\n" + x);
		
		}
		numb1 = x; // the new x
		co = null;
		// top.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		// scroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	} // end of EQUAL();
}
