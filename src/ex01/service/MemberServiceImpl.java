package ex01.service;

import java.util.ArrayList;
import java.util.Scanner;

import ex01.DTO.MemberDTO;
import ex01.dao.MemberDAO;

public class MemberServiceImpl implements MemberService {
	private MemberDAO dao;
	public MemberServiceImpl() {
		dao = new MemberDAO();
	}
	public void memberView() {
		System.out.println("회원 보기 기능");
		ArrayList<MemberDTO> members = dao.getMembers();
		if(members.size()==0) {
			System.out.println("등록된 정보가 없습니다!!");
		}else {
			//for(MemberDTO m:members) {}
			for(int i=0 ; i < members.size();i++) {
				MemberDTO m = members.get(i);
				System.out.println("id :" +m.getId());
				System.out.println("pwd :" +m.getPwd());
				System.out.println("name :" +m.getName());
				System.out.println("age :"+ m.getAge());
				System.out.println("------------------");
			}
			}
		}
	public void modify() {
		System.out.println("수정 기능");
		Scanner input = new Scanner(System.in);
		String id, pwd,name;
		int age;
		while(true) {
			System.out.println("수정할 id입력");
			id = input.next();
			MemberDTO m = dao.memberChk(id);
			if(m != null )
				break;
			System.out.println("존재하지 않는 id입니다..");
		}
		System.out.println("수정할 pwd");
		pwd = input.next();
		System.out.println("수정할 name");
		name = input.next();
		System.out.println("수정할 age");
		age = input.nextInt();
		
		MemberDTO dto = new MemberDTO();
		dto.setId(id); dto.setPwd(pwd); dto.setName(name);
		dto.setAge(age);
		//int result = dao.modify(dto);
		int result = dao.modify(id, pwd, name , age);
		
		if(result == 1) {
			System.out.println("수정되었습니다.");
			
		}else {
			System.out.println("수정 실패!!!");
		}
	}

}



