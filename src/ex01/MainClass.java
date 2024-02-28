package ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

class DB_con {
	private Connection con; // 연결과 관련된 정보를 가지고 있다.
	private PreparedStatement ps; //명령을 전달해주는 역할
	private ResultSet rs; //데이터를 저장하는 역할
	public DB_con() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브먼저 로드한다
			System.out.println("오라클 기능 사용가능(드라이브 로드)");
			//18버전 이전 :xe, 19이상 : orcl
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "java";
			String pwd = "1234";
			con = DriverManager.getConnection(url,id,pwd);
			System.out.println("db 연결 성공 :"+con);
			
			
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<MemberDTO> select() {
		String sql = "select * from member_test";
		ArrayList<MemberDTO> arr = new ArrayList<>(); //어레이리스트로 멤버디티오를 저장하겠다. 선언
		System.out.println(sql);
		MemberDTO dto = null;
		try {
			
		ps = con.prepareStatement( sql );
		
		rs= ps.executeQuery(); //위 명령어를 실행해주세요 라는 의미.
		//데이터베이스에서 가져온 ps값을 rs에 저장해준다.
		//맨앞 bof 맨뒤 eof이다.
		//rs는 처음 eof를 알고 있고 rs.next를 만나면 다음 값들을 계속 받고 마지막엔 eof를 만나면 flase도 변하면서 반복을 나가게 된다.
		//rs를 토대로 각각으 ㅣ값을 꺼내 오는 것
		while(rs.next()) {
			
			dto = new MemberDTO();
			dto.setAge(rs.getInt("age"));
			dto.setId(rs.getString("id"));
			dto.setPwd(rs.getString("pwd"));
			dto.setName(rs.getString("name"));
			arr.add(dto);
		}
	
		/*	
		System.out.println(rs.getString("id")); //아이디라는 컬럼 가져오겠다.
		System.out.println(rs.getString("pwd")); 
		System.out.println(rs.getString("name")); 
		System.out.println(rs.getString("age")); 
		System.out.println("-------------------");
		*/
		
		/*
		System.out.println(rs.next());
		System.out.println(rs.next());
		System.out.println(rs.getString("id")); //아이디라는 컬럼 가져오겠다.
		System.out.println(rs.getString("pwd")); 
		System.out.println(rs.getString("name")); 
		System.out.println(rs.getString("age")); 
		
		
		System.out.println(rs.next());
		System.out.println(rs.getString("id")); //아이디라는 컬럼 가져오겠다.
		System.out.println(rs.getString("pwd")); 
		System.out.println(rs.getString("name")); 
		System.out.println(rs.getString("age")); 
		System.out.println(rs.next()); //반복문으로 하나만 사용한다.
		 */
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	public MemberDTO selectOne (String userId) {
		String sql =
				"select * from member_test where id='"+userId+"'";
		System.out.println( sql );
		MemberDTO dto = null;
		try {
		ps = con.prepareStatement( sql );
		rs= ps.executeQuery();
		ArrayList<MemberDTO> arr = new ArrayList<>(); //어레이리스트로 멤버디티오를 저장하겠다. 선언
		System.out.println(sql);
		
		if(rs.next()) { 
			
			dto = new MemberDTO();
			dto.setAge(rs.getInt("age"));
			dto.setId(rs.getString("id"));
			dto.setPwd(rs.getString("pwd"));
			dto.setName(rs.getString("name")); 
			}
		}catch (Exception e) {
			
		}
		return dto;
	}
	public int delete(String delId) {
		String sql = "delete from member_test where id = ?";// ? 나중에 값을 채워넣겠습니다.
		int result = 0;
		try {
			ps= con.prepareStatement(sql);
			ps.setString(1, delId);
			result = ps.executeUpdate();
			//셀릭트를 제외한 메소드는 update로 사용한다.
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		}
	public int insert(MemberDTO dto) {
		String sql = 
		"insert into member_test(id,pwd,name,age) values(?,?,?,?)";
		//test옆에 순서대로 써주면 가독성이 좋다.
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getPwd());
			ps.setString(3, dto.getName());
			ps.setInt(4, dto.getAge());
			
			result = ps.executeUpdate();

		}catch (Exception e) {
			e.printStackTrace();		
			//System.out.println("존재하는ididididid");
			}
		return result;
	}
	}

public class MainClass {
public static void main(String[] args) {
	DB_con db = new DB_con();
	
	Scanner input = new Scanner(System.in);
	int num;
	while(true) {
		System.out.println("1.모든 목록 보기");
		System.out.println("2.특정 사용자 보기");
		System.out.println("3.데이터 추가");
		System.out.println("4.데이터 삭제");
		System.out.println(">>> :");
		num = input.nextInt();
		switch(num) {
		case 1 :
			ArrayList<MemberDTO> arr = db.select();
			System.out.println("---main-----");
			for(MemberDTO dto : arr) {
				System.out.println("id :" + dto.getId());
				System.out.println("pwd :"+ dto.getPwd());
				System.out.println("name :" + dto.getName());
				System.out.println("age :" + dto.getAge());
				System.out.println("----------------------");
			}
			break;
		case 2 :
			System.out.println("검색 id 입력");
			String userId = input.next();
			MemberDTO dto = db.selectOne(userId);
			System.out.println("dto : " +dto);
			if(dto == null)
				System.out.println("존재하지 않는 id입니다!!");
			else {
				System.out.println("--검색결과----");
				System.out.println("id :"+dto.getId());
				System.out.println("pwd :"+dto.getId());
				System.out.println("name :"+dto.getId());
				System.out.println("age :"+dto.getId());
			}
			break;
		case 3 :
			MemberDTO d = new MemberDTO();
			while(true) {
				System.out.println("가입할 id 입력");
				d.setId(input.next());
				MemberDTO dd = db.selectOne(d.getId());
				if(dd==null)
					break;
				System.out.println("존재하는 id..다시 입력..");
			}
			
			System.out.println("가입할 id 입력");
			d.setId(input.next());
			System.out.println("가입할 pwd 입력");
			d.setPwd(input.next());
			System.out.println("가입할 name 입력");
			d.setName(input.next());
			System.out.println("가입할 age 입력");
			d.setAge(input.nextInt());
			
			int res = db.insert( d );
			if(res == 1)
				
				System.out.println("회원가입 성공!!");
			else 
				System.out.println("존재하는 id는 안됨!!!");
			
			
			
			break;
		case 4 :
			System.out.println("삭제 id 입력");
			String delId = input.next();
			int re = db.delete(delId);
			if(re==1) {
				System.out.println("삭제 성공 !!!");
			}else {
				System.out.println("존재하지 않는 아이디 삭제 불가!!!");
			}
			break;
		}
	}
	
	
	
	
	
}
}
//데이터 베이스로 연결하기위한 방법