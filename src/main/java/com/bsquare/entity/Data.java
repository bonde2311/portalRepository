package com.bsquare.entity;

public class Data {

	public static String getMessageBodyString(String otp, String name) {
		return String.format("<!DOCTYPE html>\n" + "<html lang='en'>\n" + "<head>\n" + "    <meta charset='UTF-8'>\n"
				+ "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n"
				+ "    <title>Reset Your Password - BSquare Job Portal</title>\n" + "    <style>\n"
				+ "        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f6f8; margin: 0; padding: 20px; }\n"
				+ "        .container { background: #fff; max-width: 600px; margin: auto; padding: 40px 30px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); }\n"
				+ "        .top-line { height: 6px; background-color: #864af9; border-radius: 6px 6px 0 0; }\n"
				+ "        .logo { text-align: center; margin: 20px 0; }\n"
				+ "        .logo img { width: 150px; height: auto; }\n"
				+ "        h2 { color: #2c3e50; text-align: center; margin-top: 10px; }\n"
				+ "        p { color: #333333; font-size: 16px; line-height: 1.6; margin: 16px 0; }\n"
				+ "        .otp { text-align: center; margin: 30px 0; }\n"
				+ "        .otp span { background-color: #864af9; color: #fff; font-size: 28px; padding: 14px 28px; border-radius: 8px; font-weight: bold; letter-spacing: 2px; display: inline-block; }\n"
				+ "        .footer { font-size: 12px; color: #999999; text-align: center; margin-top: 40px; border-top: 1px solid #e0e0e0; padding-top: 20px; }\n"
				+ "        @media screen and (max-width: 600px) {\n"
				+ "            .container { padding: 20px 15px; }\n"
				+ "            .otp span { font-size: 24px; padding: 12px 20px; }\n" + "        }\n" + "    </style>\n"
				+ "</head>\n" + "<body>\n" + "    <div class='container'>\n" + "        <div class='top-line'></div>\n"
				+ "        <div class='logo'>\n"
				+ "            <img src='https://yourdomain.com/path/to/logo.png' alt='BSquare Job Portal Logo'>\n"
				+ "        </div>\n" + "        <h2>Reset Your Password</h2>\n" + "        <p>Hi %s,</p>\n"
				+ "        <p>We received a request to reset your password for your <strong>BSquare Job Portal</strong> account.</p>\n"
				+ "        <p>Use the following One-Time Password (OTP) to proceed:</p>\n"
				+ "        <div class='otp'>\n" + "            <span>%s</span>\n" + "        </div>\n"
				+ "        <p>This OTP is valid for the next <strong>10 minutes</strong>. Do not share this code with anyone.</p>\n"
				+ "        <p>If you did not request a password reset, you can safely ignore this email.</p>\n"
				+ "        <p>Thank you,<br><strong>Team BSquare Job Portal</strong></p>\n"
				+ "        <div class='footer'>\n"
				+ "            &copy; 2025 BSquare Job Portal. All rights reserved.<br>\n"
				+ "            Designed and Developed By<br>\n" + "			 Durgesh Bagul 🤝 Gaurav Bonde.\n"
				+ "        </div>\n" + "    </div>\n" + "</body>\n" + "</html>", name, otp);
	}
}
