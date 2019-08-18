/**
 * Created by Khizr ali pardhan on 2/29/2016. 
 * this is the java version of a bat file i made, purpose is to give several dislikes to a youtube video. to add to that, i wanted to target multiple videos at once.
 * if the "switch (amplification)" statement defaults, there is not issue, by that i conclude the issues with this program a related to java itself not being able to handle
 * the odd "target" chrome uses ("C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" --profile-directory="Default")
 * ... due to all that this is a broken program, but i am confident the issue is not my fault 
 */

package term2;

import java.util.Scanner;

public class smm {
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		System.out.println("Number for targets?");
		int targets = Integer.parseInt(scan.nextLine());

		String url[] = new String[targets];
		for (int i = 0; i < url.length; i++) {
			System.out.println("target" + (i + 1) + ":");
			url[i] = scan.nextLine();
		}
		//
		System.out.println("Desired  amplification factor?");
		int amplification = scan.nextInt();

		if (amplification > 4 || amplification <= 0) {
			System.out.println("error!!!");
			System.exit(0);
		}

		//
		for (int i = 0; i < url.length; i++) {
			try {
				switch (amplification) {
				case 1:
					// Process q =
					// Runtime.getRuntime().exec("\"/Program Files (x86)/Google/Chrome/Application/chrome.exe \"--profile-directory=Profile1"+
					// url[i]);
					Runtime.getRuntime().exec(
							"\"/Program Files (x86)/Google/Chrome/Application/chrome.exe \""
									+ url[i]);
					break;
				// q.waitFor();
				// dont open next till windows i closed, can be very useful
				case 2:
					Runtime.getRuntime()
							.exec("\"/Program Files (x86)/Google/Chrome/Application/chrome.exe \"--profile-directory=Profile1"
									+ url[i]);
					Runtime.getRuntime()
							.exec("\"/Program Files (x86)/Google/Chrome/Application/chrome.exe\"--profile-directory=Profile2"
									+ url[i]);
					break;
				case 3:
					Runtime.getRuntime()
							.exec("\"/Program Files (x86)/Google/Chrome/Application/chrome.exe \"--profile-directory=Profile1"
									+ url[i]);
					Runtime.getRuntime()
							.exec("\"/Program Files (x86)/Google/Chrome/Application/chrome.exe\"--profile-directory=Profile2"
									+ url[i]);
					Runtime.getRuntime()
							.exec("\"/Program Files (x86)/Google/Chrome/Application/chrome.exe\"--profile-directory=Profile3"
									+ url[i]);
					break;
				case 4:
					Runtime.getRuntime()
							.exec("\"/Program Files (x86)/Google/Chrome/Application/chrome.exe \"--profile-directory=Profile1"
									+ url[i]);
					Runtime.getRuntime()
							.exec("\"/Program Files (x86)/Google/Chrome/Application/chrome.exe\"--profile-directory=Profile2"
									+ url[i]);
					Runtime.getRuntime()
							.exec("\"/Program Files (x86)/Google/Chrome/Application/chrome.exe\"--profile-directory=Profile3"
									+ url[i]);
					Runtime.getRuntime()
							.exec("\"/Program Files (x86)/Google/Chrome/Application/chrome.exe\"--profile-directory=Profile4"
									+ url[i]);
					System.out.println("4");
					break;
				default:
					Runtime.getRuntime().exec(
							"\"/Program Files (x86)/Google/Chrome/Application/chrome.exe \""
									+ url[i]);
					System.out.println("this should not have opened!!!!");
				}

				System.out.println("Google Chrome launched, the url is: "
						+ url[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
// Runtime.getRuntime().exec(new String[] { "Chrome", "http://goo.gl/EsomR0" });