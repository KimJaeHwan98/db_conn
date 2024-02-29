package ex01.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import ex01.DTO.MemberDTO;

public class MemberDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	public MemberDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "java" , pwd = "1234";
			con = DriverManager.getConnection(url,id,pwd);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<MemberDTO> getMembers() {
		String sql = "select * from member_test";
		ArrayList<MemberDTO> mem = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next() ) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id")); //컬럼명을 작성한다
				dto.setPwd(rs.getString("pwd")); 
				dto.setName(rs.getString("name")); 
				dto.setAge(rs.getInt("age"));


				mem.add(dto);

			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mem;
	}
	public MemberDTO memberChk (String id) {
		String sql = "select * from member_test where id=?";
		MemberDTO dto = null;
		// boolean bool = false; 방법 2
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next() ) {
				// bool = true; 방법 2
				dto = new MemberDTO();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public int modify(MemberDTO dto) {
		String sql = "update member_test set pwd=?,name=?,age=?,where id=?";
		int result =0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getPwd());
			ps.setString(2, dto.getName());
			ps.setInt(3, dto.getAge());
			ps.setString(4, dto.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int modify(String id, String pwd , String name, int age) {
			System.out.println("매개변수 4개 modify 실행!!!");
			
			String sql = "update member_test set pwd=?,name=?,age=?where id=?";
			int result =0;
			try {
				ps = con.prepareStatement(sql);
				ps.setString(1,pwd);
				ps.setString(2, name);
				ps.setInt(3, age);
				ps.setString(4, id);
				result = ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	}

//데이터베이스 연동 코드.
//에러가 발생하면 파일로 찾아간다. 에러가 발생하면 첫번째줄부터 확인한다.
//준비하다 실행 후 문제가 발생하면 준비과정을 수정해본다.