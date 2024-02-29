package ex01.main;

import java.util.Scanner;

import ex01.service.MemberService;
import ex01.service.MemberServiceImpl;

public class MainClass {
public static void main(String[] args) {
	Scanner input = new Scanner(System.in);
	MemberService ms = new MemberServiceImpl();
	int num;
	while(true) {
		System.out.println("1.회원보기");
		System.out.println("2.회원수정");
		System.out.println("3.종료");
		System.out.println(">>> :");
		num = input.nextInt();
		switch(num) {
		case 1 :ms.memberView(); break;
		case 2 :ms.modify(); break;
		case 3 : System.out.println("프로그램 종료!!!"); return;
		}
	}
}
}
